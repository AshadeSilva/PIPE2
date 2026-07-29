package org.example.pipe2.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.LogModel

class Log (private val listener: LogModel, private val scope: CoroutineScope) {
    var title = "No Log Open"
    val events = mutableStateListOf<Event>()

    // firebase errors get thrown a lot. We catch and display on signin page
    var lastError by mutableStateOf<Throwable?>(null)
    private var job: Job? = null


    fun updateLog() {
        // catch firebase errors
        job?.cancel()
        job = scope.launch {
            try {
                lastError = null
                listener.listen().collect {
                    events.add(it)
                }
            } catch (e: Exception) {
                lastError = e
            }
        }
    }

    // let android app crash so that data is send to crashlytics
    fun triggerCrash() {
        lastError?.let { throw it }
    }
}