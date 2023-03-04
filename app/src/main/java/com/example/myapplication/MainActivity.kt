package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.compose.ui.window.Dialog
import com.example.myapplication.databinding.FragmentContainerBinding
import com.example.myapplication.databinding.ReproFragmentBinding
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    var showDialog by remember { mutableStateOf(false) }
                    Button(onClick = { showDialog = true }) {
                        Text(text = "Show dialog")
                    }
                    if (showDialog) {
                        Dialog(onDismissRequest = { showDialog = false }) {
                            Column(
                                modifier = Modifier
                                    .size(300.dp, 300.dp)
                                    .background(Color.White)
                            ) {
                                Text(text = "Dialog", modifier = Modifier.size(300.dp, 150.dp))
                                AndroidViewBinding(
                                    factory = FragmentContainerBinding::inflate,
                                    modifier = Modifier.size(300.dp, 150.dp),
                                )
                            }
                        }
                    }
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