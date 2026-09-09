package org.example.pipe2.data.log.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.utils.logDebug
import kotlin.time.Instant

class FirebaseLogDB : RemoteLogDB {

    override fun observe(alarmId: String): Flow<EventDetails> {

        try {
            return Firebase.firestore
                .collection("activeAlarms")
                .document(alarmId)
                .collection("events")
                .orderBy("time")
                .snapshots
                .transform { snapshot ->
                    snapshot.documentChanges
                        .forEach { change ->
                            if (change.document.exists) {
                                emit(change.document.toEventDetails())
                            }
                        }
                }
        } catch (e: Exception) {
            logDebug("ASHADEBUG", "from firebase log db")
            throw e
        }
    }

    private fun DocumentSnapshot.toEventDetails(): EventDetails {
        try {

            return EventDetails(
                this.id,
                this.get("type"),
                this.get("sender"),
                this.get<Timestamp>("time").toInstant(),
                this.get("recipient"),
                this.get("contents"),
                this.get("status"),
                this.get("student")
            )
        } catch (e: Exception) {
            logDebug("ASHADEBUG", "from firebase log db toEventDetails")
            logDebug("ASHADEBUG", e.message.toString())
            throw e
        }


    }

    fun Timestamp.toInstant(): Instant =
        Instant.fromEpochSeconds(
            this.seconds,
            this.nanoseconds.toLong()
        )

    // other way: Timestamp(epochSecond, nano)
}