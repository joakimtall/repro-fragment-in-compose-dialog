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
import androidx.compose.ui.Alignment
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    var showBox by remember { mutableStateOf(false) }
                    var showDialog by remember { mutableStateOf(false) }
                    Button(onClick = { showBox = true }) {
                        Text(text = "Show fragment in box (back pressed works here)")
                    }
                    Button(onClick = { showDialog = true }) {
                        Text(text = "Show fragment in dialog (back pressed does not work)")
                    }
                    if (showBox) {
                        Box {
                            Column(
                                modifier = Modifier
                                    .background(Color.Gray)
                            ) {
                                Button(onClick = { showBox = false }) {
                                    Text(text = "Close")
                                }
                                AndroidViewBinding(
                                    factory = FragmentContainerBinding::inflate,
                                    modifier = Modifier.size(300.dp, 150.dp),
                                )
                            }
                        }
                    }
                    if (showDialog) {
                        Dialog(onDismissRequest = {}) {
                            Column(
                                modifier = Modifier.background(Color.Gray),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Button(onClick = { showDialog = false }) {
                                    Text(text = "Close")
                                }
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