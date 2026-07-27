package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.example.pipe2.logic.events.Event
import dev.gitlive.firebase.firestore.DocumentChange

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
                    emit(change.document.data<Event>())
                }
        }
}
