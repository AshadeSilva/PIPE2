package org.example.pipe2.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.pipe2.data.account.LogModel

class LogView : ViewModel() {

    var title by mutableStateOf("No Log Open")
    var log by mutableStateOf<Log?>(null)

    fun createLog(alarmId: String) {
        // note: using viewModelScope for cleaning up CoRoutine for firestore listening
        val newLog = Log(LogModel(alarmId), viewModelScope)
        log = newLog
        title = "Log: $alarmId"
        newLog.updateLog()
    }
    
    fun closeLog(){
        log = null
        title = "No Log Open"
    }
    
    fun updateLogStatus(accountType: AccountView){
        if (accountType == AccountView.Warden){
            createLog("example_alarm")
        } else {
            closeLog()
        }
    }

}
