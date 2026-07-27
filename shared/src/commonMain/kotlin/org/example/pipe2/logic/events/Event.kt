package org.example.pipe2.logic.events

import dev.gitlive.firebase.firestore.Timestamp

interface Event {
    val sender: String
    val time: Timestamp
}

class StatusUpdateEvent(
    override val sender: String,
    override val time: Timestamp,
    val student: String,
    val status: Status
) : Event

class MessageEvent(
    override val sender: String,
    override val time: Timestamp,
    val recipient: String,
    val contents: String
) : Event

class StartEvent(
    override val sender: String,
    override val time: Timestamp
) : Event

class EndEvent(
    override val sender: String,
    override val time: Timestamp
) : Event