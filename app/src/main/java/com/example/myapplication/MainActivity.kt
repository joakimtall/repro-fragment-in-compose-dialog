package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.BackHandler
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxWidth().weight(0.3f).background(Color.White)) {
                        var count by remember { mutableStateOf(0) }
                        var backHandlerActive by remember { mutableStateOf(true) }
                        BackHandler(backHandlerActive) {
                            count += 1
                        }
                        Text("In root compose world (outside fragment)\nBack pressed count: $count")
                        Button(onClick = { backHandlerActive = !backHandlerActive }) {
                            Text("Toggle outside fragment back handler active ($backHandlerActive)")
                        }
                    }
                    AndroidViewBinding(
                        factory = FragmentContainerBinding::inflate,
                        modifier = Modifier.weight(0.7f),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}