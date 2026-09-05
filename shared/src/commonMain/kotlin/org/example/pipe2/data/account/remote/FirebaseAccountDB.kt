package org.example.pipe2.data.account.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.map
import org.example.pipe2.data.account.UserDetails


class FirebaseAccountDB: RemoteAccountDB {
    override fun observe(uid: String) =
        Firebase.firestore
            .collection("users")
            .document(uid)
            .snapshots
            .map { docSnap ->
                if (docSnap.exists) {
                    docSnap.toDetails()
                } else {
                    null
                }
            }

    private fun DocumentSnapshot.toDetails() = UserDetails(
        uid = this.get("uid"),
        username = this.get("username"),
        building = this.get("building"),
        email = this.get("email"),
        type = this.get("account_type")
    )
}
