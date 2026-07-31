package org.example.pipe2.oldLogic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.logic.Student
import org.example.pipe2.logic.UserContext
import org.example.pipe2.logic.Warden
import org.example.pipe2.ui.generalLayout.Page
import org.example.pipe2.ui.theme.White
import org.example.pipe2.utils.logDebug

class UIContext(private val user: UserContext) : ViewModel() {

    // app structure depending on user type
    var theme by mutableStateOf<AccountTheme>(AccountTheme.None)

    // bars used depend on alarm on/off
    var state by mutableStateOf<AlarmState>(DeactiveState())
    fun toggle(){
        state = state.nextState
    }

    init {
        viewModelScope.launch {
            snapshotFlow { user.currentUser }.collectLatest {

                theme = when (user.currentUser) {
                    is Student -> AccountTheme.Student
                    is Warden -> AccountTheme.Warden
                    else -> AccountTheme.None
                }
                logDebug("ASHADEBUG", "UIContext: user changed")
            }
        }
    }

    enum class AccountTheme(val colour: Color, val pages: List<Page>) {
        None(White, listOf(Page.LogIn)),
        Student(org.example.pipe2.ui.theme.Student, listOf(Page.SelfRegister, Page.LogIn)),
        Warden(org.example.pipe2.ui.theme.Warden, listOf(Page.Students, Page.LogIn, Page.LogView))
    }


}