package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.example.pipe2.logic.EndEvent
import org.example.pipe2.logic.Event
import org.example.pipe2.logic.MessageEvent
import org.example.pipe2.logic.StartEvent
import org.example.pipe2.logic.StatusUpdateEvent
import org.example.pipe2.logic.toStatus

class LogListener (private val alarmId: String) {
    fun listen(): Flow<Event> = Firebase.firestore
        .collection("activeAlarms")
        .document(alarmId)
        .collection("events")
        .orderBy("time")
        .snapshots
        .transform { snapshot ->
            snapshot.documentChanges
                .forEach { change ->
                    val event = change.document.toEvent()
                    if (event != null){
                        emit(event)
                    }
                }
        }
}

fun DocumentSnapshot.toEvent(): Event? {
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
        }
        else -> null
    }

}