package org.example.pipe2.data.log

import androidx.room3.ColumnTypeConverter
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import org.example.pipe2.logic.alarm.ErrorEvent
import org.example.pipe2.logic.alarm.StatusUpdateEvent
import org.example.pipe2.logic.alarm.StartEvent
import org.example.pipe2.logic.alarm.MessageEvent
import org.example.pipe2.logic.alarm.EndEvent
import org.example.pipe2.logic.alarm.Event
import org.example.pipe2.logic.alarm.toStatus
import kotlin.time.Instant

@Entity(tableName = "Log") // represents a record in a database table
data class EventDetails (
    @PrimaryKey val docId: String,
    val type: String,
    val sender: String,
    val time: Instant,
    val recipient: String?,
    val contents: String?,
    val status: String?,
    val student: String?
) {

    fun toEvent(): Event =
        when (type) {
            "alarm_start" -> StartEvent(docId, sender, time)
            "alarm_end" -> EndEvent(docId, sender, time)
            "message" -> recipient?.let {
                MessageEvent(
                    docId,
                    sender,
                    time,
                    recipient.toString(),
                    contents.toString()
                )
            } ?: ErrorEvent(docId, sender, time)
            "status_update" -> student?.let {
                status?.let {
                    StatusUpdateEvent(
                        docId,
                        sender,
                        time,
                        student,
                        status.toStatus()
                    )
                } ?: ErrorEvent(docId, sender, time)
            } ?: ErrorEvent(docId, sender, time)
            else -> ErrorEvent(docId, sender, time)
        }


}

object RoomConverter {
    @ColumnTypeConverter
    fun fromEpochMillis(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @ColumnTypeConverter
    fun toEpochMillis(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}




