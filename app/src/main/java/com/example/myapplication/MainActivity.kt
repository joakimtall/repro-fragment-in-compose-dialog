package com.example.myapplication

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.myapplication.databinding.FragmentContainerBinding
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.ArrayDeque
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmName

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var count by remember { mutableStateOf(0) }
            BackHandler {
                count += 1
            }
            MyApplicationTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxWidth().weight(0.5f).background(Color.White)) {
                        Text("In compose world (outside fragment)\nBack pressed count: $count")
                        Spacer(modifier = Modifier.height(20.dp))
                        BackPressedChainClassNames()
                    }
                    AndroidViewBinding(
                        factory = FragmentContainerBinding::inflate,
                        modifier = Modifier.weight(0.5f),
                    )
                }
            }
        }
    }

    @Composable
    private fun BackPressedChainClassNames() {
        var onBackPressedChainClassNames by remember { mutableStateOf("") }
        Text("mOnBackPressedCallbacks:\n$onBackPressedChainClassNames")
        val backPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
        LaunchedEffect(Unit) {
            while (isActive) {
                onBackPressedChainClassNames = getBackPressedChainClassNames(backPressedDispatcherOwner)
                delay(200)
            }
        }
    }
}

private fun getBackPressedChainClassNames(backPressedDispatcherOwner: OnBackPressedDispatcherOwner?) =
    backPressedDispatcherOwner?.let { owner ->
        owner.onBackPressedDispatcher.getPrivateProperty<OnBackPressedDispatcher, ArrayDeque<*>>(
            "mOnBackPressedCallbacks"
        )?.let { backPressedCallbacks ->
            backPressedCallbacks.joinToString("\n") {
                it::class.jvmName
            }
        }
    } ?: "got null"

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R