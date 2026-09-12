package org.example.pipe2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.RoomDBCollection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        initializeFirebase(this)

        setContent {
            FlavorSetUp(applicationContext)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    FlavorSetUp()
}
