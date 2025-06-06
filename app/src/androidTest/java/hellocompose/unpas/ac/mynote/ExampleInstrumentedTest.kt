package hellocompose.unpas.ac.mynote

import android.os.Bundle
import androidx.activity.ComponentActivity import androidx.activity.compose.setContent import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize import androidx.compose.foundation.layout.padding import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint import id.ac.unpas.mynote.ui.screens.Main
import id.ac.unpas.mynote.ui.theme.MyNoteTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge ()
        setContent {
            MyNoteTheme {
                Scaffold(modifier = Modifier fillMaxSize()) { innerPadding -›
                    Main(Modifier-padding(innerPadding))
                }
            }
        }
    }
}