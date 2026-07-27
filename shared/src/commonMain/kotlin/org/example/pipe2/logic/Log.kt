package org.example.pipe2.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.LogListener
import org.example.pipe2.logic.events.Event

class Log (private val listener: LogListener): ViewModel() {
    val events: MutableList<Event> = mutableListOf()

    fun updateLog() {
        viewModelScope.launch {
            listener.listen().collect { events.add(it) } }
    }
}