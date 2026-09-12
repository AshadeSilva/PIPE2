package org.example.pipe2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.example.pipe2.apps.student.StudentSetUp
import org.example.pipe2.apps.student.data.getStudentRoomDB
import org.example.pipe2.data.LocalDBCollection

fun initializeFirebase(context: Context) {
    if (FirebaseApp.getApps(context).isEmpty()) {
        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyB5nPduXTw68_TFf7qbyU3dA6M_txDqi_8")
            .setApplicationId("1:300535801602:android:39aab40f503f76ee74841b") // TODO: Replace with Student App ID from Firebase Console
            .setProjectId("pipe1002-220a8")
            .setStorageBucket("pipe1002-220a8.firebasestorage.app")
            .build()

        FirebaseApp.initializeApp(context, options)
    }
}

@Composable
fun FlavorSetUp(context: Context) {
    val localDb = remember { getStudentRoomDB(context) }
    StudentSetUp(localDb)
}

@Composable
fun FlavorSetUp(localDb: LocalDBCollection) {
    StudentSetUp(localDb)
}

@Composable
fun FlavorSetUp() {
    StudentSetUp()
}
