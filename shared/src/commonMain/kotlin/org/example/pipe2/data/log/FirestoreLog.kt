package org.example.pipe2.data.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.pipe2.oldLogic.EndEvent
import org.example.pipe2.oldLogic.ErrorEvent
import org.example.pipe2.oldLogic.Event
import org.example.pipe2.logic.Log
import org.example.pipe2.oldLogic.MessageEvent
import org.example.pipe2.oldLogic.StartEvent
import org.example.pipe2.oldLogic.StatusUpdateEvent
import org.example.pipe2.oldLogic.toStatus

// cache the remote version of the current alarm's log
class FirestoreLog (alarmId: String, private val scope: CoroutineScope): Log {

    private val listener: FirestoreLogAccess = FirestoreLogAccess(alarmId)
    private val _events = mutableStateListOf<Event>()
    override val events: List<Event> = _events

    override var lastError by mutableStateOf<Exception?>(null)
    private var job: Job? = null
    init {
        updateLog()
    }
    override fun updateLog() {
        job?.cancel()
        job = scope.launch {
            try {
                lastError = null
                listener.listen().collect {
                    val event = it.toEvent()
                    if (event != null){
                        _events.add(event)
                    }
                }
            } catch (e: Exception) {
                // error handling: if firebase sends an error, store in lastError
                // currently displaying it on screen for dev
                lastError = e
            }
        }
    }

    private fun DocumentSnapshot.toEvent(): Event? {
        val type:String = this.get("type")
        val sender: String = this.get("sender")
        val time: Timestamp = this.get("time")

        return when (type) {
            "alarm_start" -> StartEvent(sender, time)
            "alarm_end" -> EndEvent(sender, time)
            "message" -> MessageEvent(sender, time,
                this.get<String>("recipient"),
                this.get<String>("contents"))
            "status_update" -> this.get<String>("status").toStatus()?.let {
                StatusUpdateEvent(sender, time,
                    this.get<String>("student"),
                    it)
            } ?: ErrorEvent("", time)
            else -> ErrorEvent("", time)
        }
    }

}

