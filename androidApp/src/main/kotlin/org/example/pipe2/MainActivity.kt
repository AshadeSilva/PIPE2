package org.example.pipe2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.example.pipe2.data.account.local.DummyLocalAccountDB
import org.example.pipe2.data.account.local.RoomAccountDB
import org.example.pipe2.data.getAccountDB
import org.example.pipe2.ui.generalLayout.SetUp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        if (FirebaseApp.getApps(this).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApiKey("AIzaSyB5nPduXTw68_TFf7qbyU3dA6M_txDqi_8")
                .setApplicationId("1:300535801602:android:ae75b8029aa5d40574841b")
                .setProjectId("pipe1002-220a8")
                .setStorageBucket("pipe1002-220a8.firebasestorage.app")
                .build()

            FirebaseApp.initializeApp(this, options)
        }

        val db = getAccountDB(applicationContext)
        setContent {
            SetUp(RoomAccountDB(db))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    SetUp()
}