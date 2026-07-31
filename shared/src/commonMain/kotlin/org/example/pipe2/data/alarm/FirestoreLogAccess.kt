package org.example.pipe2.data.alarm

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

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
