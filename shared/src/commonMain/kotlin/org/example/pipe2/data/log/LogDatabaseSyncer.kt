package org.example.pipe2.data.log

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import org.example.pipe2.data.log.local.LocalLogDB
import org.example.pipe2.data.log.remote.RemoteLogDB
import org.example.pipe2.utils.logDebugE

// at the moment there is one, clearable table that holds the current log. Thats it
// not table for building (which would control the clears/starts)
class LogDatabaseSyncer(private val remote: RemoteLogDB, private val local: LocalLogDB) {
    suspend fun start(alarmId: String) {
        var active = true
        while (active) {
            active = false
            try {
                local.clearLog()
                remote.observe(alarmId).collect { local.appendLog(it) }
            } catch (_: CancellationException) {
                // user has been changed from user context
            } catch (e: Exception) {
                // Handle Firestore errors (like PERMISSION_DENIED) by retrying
                // This prevents the app from crashing and waits for the user to be authenticated
                logDebugE("ASHADEBUG", e)
                delay(1_000)
                active = true
            }
        }
    }
}