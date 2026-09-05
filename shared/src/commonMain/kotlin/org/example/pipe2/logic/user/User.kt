package org.example.pipe2.logic.user

import org.example.pipe2.data.account.UserDetails

// users as logical objects, should be ready to use by UI
data class User(
    val uid: String,
    val username: String = "",
    val building: String = "",
    val email: String = "",
    val type: UserType = UserType.None
)

fun UserDetails.toUser(): User {
    return User(
        uid = this.uid,
        email = this.email ?: "",
        username = this.username ?: "",
        building = this.building ?: "",
        type = when (this.type) {
            "warden" -> UserType.Warden
            "student" -> UserType.Student
            else -> UserType.None
        }
    )
}

enum class UserType(val type: String) {
    Student("student"),
    Warden("warden"),
    None("no user type")
}
