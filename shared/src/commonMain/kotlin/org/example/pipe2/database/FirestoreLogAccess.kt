package org.example.pipe2.database

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.example.pipe2.logic.EndEvent
import org.example.pipe2.logic.ErrorEvent
import org.example.pipe2.logic.Event
import org.example.pipe2.logic.MessageEvent
import org.example.pipe2.logic.StartEvent
import org.example.pipe2.logic.StatusUpdateEvent
import org.example.pipe2.logic.toStatus

// navigate firestore database to the correct alarm collection
class FirestoreLogAccess (private val alarmId: String) {
    fun listen(): Flow<DocumentSnapshot> = Firebase.firestore
        .collection("activeAlarms")
        .document(alarmId)
        .collection("events")
        .orderBy("time")
        .snapshots
        .transform { snapshot ->
            snapshot.documentChanges
                .forEach { change ->
                    emit(change.document)
                }
        }
}
