package com.example.myapplication

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.myapplication.databinding.FragmentContainerBinding
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmName

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var count by remember { mutableStateOf(0) }
            var backHandlerActive by remember { mutableStateOf(true) }
            BackHandler(backHandlerActive) {
                count += 1
            }
            MyApplicationTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .background(Color.White)) {
                        Text("In compose world (outside fragment)\nBack pressed count: $count")
                        Button(onClick = { backHandlerActive = !backHandlerActive }) {
                            Text("Toggle outside fragment back handler active ($backHandlerActive)")
                        }
                        var text by remember { mutableStateOf("") }
                        val backPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
                        LaunchedEffect(Unit) {
                            while (isActive) {
                                text = backPressedDispatcherOwner?.let { owner ->
                                    owner.onBackPressedDispatcher.getPrivateProperty<OnBackPressedDispatcher, java.util.ArrayDeque<*>>(
                                        "mOnBackPressedCallbacks"
                                    )?.let { backPressedCallbacks ->
                                       backPressedCallbacks.joinToString("\n") {
                                           it::class.jvmName
                                       }
                                    }
                                } ?: "got null"
                                delay(200)
                            }
                        }
                        Text("mOnBackPressedCallbacks: \n$text")
                    }
                    AndroidViewBinding(
                        factory = FragmentContainerBinding::inflate,
                        modifier = Modifier.weight(0.5f),
                    )
                }
            }
        }
    }
}

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R