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
    val sender: String
    val time: Timestamp
    val name: String
    fun printTime(): String {
        val dateTimeFormat = LocalDateTime.Format {
            hour(); char(':'); minute(); char(' ')
            day(); char('/'); monthNumber(); char('/'); yearTwoDigits(2000)
        }
        val instant = Instant.fromEpochSeconds(time.seconds, time.nanoseconds)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.format(dateTimeFormat)
    }

}

class StatusUpdateEvent(
    override val sender: String,
    override val time: Timestamp,
    val student: String,
    val status: Status
): Event {
    override val name = "Status Update"
}

class MessageEvent(
    override val sender: String,
    override val time: Timestamp,
    val recipient: String,
    val contents: String
) : Event {
    override val name = "Message"
}

class StartEvent(
    override val sender: String,
    override val time: Timestamp
) : Event {
    override val name = "Start"
}

class EndEvent(
    override val sender: String,
    override val time: Timestamp
) : Event {
    override val name = "End"
}

class ErrorEvent(
    override val sender: String,
    override val time: Timestamp
) : Event {
    override val name = "Event Could Not Load"
}