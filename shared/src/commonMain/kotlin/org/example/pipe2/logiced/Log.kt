package org.example.pipe2.logiced

import org.example.pipe2.logic.Event

// caches the remote collection for the current alarm
interface Log {

    // eventually authentication here too?

    // read only, real-time log of events
    val events: List<Event>

    fun updateLog() // listens for event to add to events

    // error handling: if firebase sends an error, store in lastError
    var lastError: Exception?

    // let android app crash so that data is sent to crashlytics
    fun triggerCrash() {
        lastError?.let { throw it }
    }


}