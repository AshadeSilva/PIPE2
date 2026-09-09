package org.example.pipe2.logic.alarm


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
// caches the remote collection for the current alarm
class Log {

    val events by   mutableStateOf<MutableList<Event>>(mutableListOf())



}