package org.example.pipe2.logic.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Student(): User {
    override var uid by mutableStateOf("")
    override var username by mutableStateOf("")
    override var building by mutableStateOf("")
    override var email by mutableStateOf("")

    constructor(_uid: String, _username: String, _building: String, _email: String): this() {
        uid = _uid
        username = _username
        building = _building
        email = _email
    }
}
