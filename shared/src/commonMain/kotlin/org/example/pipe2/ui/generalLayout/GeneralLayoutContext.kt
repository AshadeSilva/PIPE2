package org.example.pipe2.ui.generalLayout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.user.UserType
import org.example.pipe2.logic.alarm.AlarmState
import org.example.pipe2.logic.alarm.DeactiveState
import org.example.pipe2.ui.theme.White
import org.example.pipe2.utils.logDebug
import kotlin.math.log

class GeneralLayoutContext(private val user: UserContext) : ViewModel() {

    // Current screen shown
    var currentPage by mutableStateOf<Page>(Page.LogIn)

    // Colours and Structure
    var theme by mutableStateOf<AccountTheme>(AccountTheme.None)
    init {
        observeUserChanges()
    }
    private fun observeUserChanges() {
        val userContext = user
        viewModelScope.launch {
            snapshotFlow { userContext.currentUser }.collectLatest { newUser ->
                theme = when (newUser?.type) {
                    UserType.Student -> AccountTheme.Student
                    UserType.Warden -> AccountTheme.Warden
                    else -> AccountTheme.None
                }
                currentPage = theme.pages.first()
                logDebug("ASHADEBUG", "updating UI ${newUser?.email} ${newUser?.type?.type}")
            }
        }
    }

     enum class AccountTheme(val colour: Color, val pages: List<Page>) {
        None(White, listOf(Page.LogIn)),
        Student(org.example.pipe2.ui.theme.Student, listOf(Page.SelfRegister, Page.LogIn)),
        Warden(org.example.pipe2.ui.theme.Warden, listOf(Page.Students, Page.LogIn, Page.LogView))
    }

    var alarmState by mutableStateOf<AlarmState>(DeactiveState())
    fun toggle(){
        alarmState = alarmState.nextState
    }

}