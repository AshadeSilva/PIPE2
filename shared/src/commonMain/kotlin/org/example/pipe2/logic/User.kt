package org.example.pipe2.logic

import androidx.compose.ui.graphics.Color
import org.example.pipe2.ui.generalLayout.Page
import org.example.pipe2.utils.logDebug

sealed interface User {
//    TODO(): user details don't update automatically
    abstract var uid: String
    abstract var username: String
    abstract var building: String
    abstract var email: String

    fun updateDetails(details: DBResult) {
        uid = details.uid
        email = details.email ?: ""
        username = details.username ?: ""
        building = details.building ?: ""
        logDebug("ASHADEBUG", "user details: $username")
    }
}