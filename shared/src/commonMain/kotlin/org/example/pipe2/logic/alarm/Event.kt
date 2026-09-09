package org.example.pipe2.logic.alarm

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.example.pipe2.logic.alarm.Status
import kotlin.time.Instant

interface Event {
    val id: String
    val sender: String
    val time: Instant
    val name: String
    fun printTime(): String {
        val dateTimeFormat = LocalDateTime.Format {
            hour(); char(':'); minute(); char(' ')
            day(); char('/'); monthNumber(); char('/'); yearTwoDigits(2000)
        }
        val localDateTime = time.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.format(dateTimeFormat)
    }

}

class StatusUpdateEvent(
    override val id: String,
    override val sender: String,
    override val time: Instant,
    val student: String,
    val status: Status
): Event {
    override val name = "Status Update"
}

class MessageEvent(
    override val id: String,
    override val sender: String,
    override val time: Instant,
    val recipient: String,
    val contents: String
) : Event {
    override val name = "Message"
}

class StartEvent(
    override val id: String,
    override val sender: String,
    override val time: Instant
) : Event {
    override val name = "Start"
}

class EndEvent(
    override val id: String,
    override val sender: String,
    override val time: Instant
) : Event {
    override val name = "End"
}

class ErrorEvent(
    override val id: String,
    override val sender: String,
    override val time: Instant
) : Event {
    override val name = "Event Could Not Load"
}