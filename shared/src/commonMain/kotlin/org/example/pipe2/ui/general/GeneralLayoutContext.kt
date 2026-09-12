package org.example.pipe2.ui.general

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.ui.pages.Page
import org.example.pipe2.ui.general.theme.White

class GeneralLayoutContext(
    private val config: AppConfig,
    userContext: UserContext
    ) : ViewModel() {

    // Current screen shown
    var currentPage by mutableStateOf<Page>(Page.LogIn)
    var pages by mutableStateOf<List<Page>>(listOf(Page.LogIn))
    var colour by mutableStateOf<Color>(White)

    init {
        viewModelScope.launch {
            userContext.currentUserFlow.collect {
                if (it == null) {
                    signOut()
                } else {
                    signIn()
                }
            }
        }
    }

    fun signOut(){
        pages = listOf(Page.LogIn)
        colour = White
    }

    fun signIn(){
        pages = config.pages
        colour = config.colour
    }
}