package org.example.pipe2.logic

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.LogModel
import org.example.pipe2.utils.logDebug

class LogUpdater (private val listener: LogModel): ViewModel() {
    val events = mutableStateListOf<Event>()

    fun updateLog() {
        viewModelScope.launch {
            listener.listen().collect {
                events.add(it)
            }
        }
    }
}