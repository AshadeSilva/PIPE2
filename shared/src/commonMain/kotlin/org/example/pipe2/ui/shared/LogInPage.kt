package org.example.pipe2.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.LocalAppLogicContext
import org.example.pipe2.logic.user.UserContext
import kotlin.let

@Composable
fun LoginPage(
    userContext: UserContext = LocalAppLogicContext.current.user,
    context: LogInPageContext = viewModel { LogInPageContext(userContext) }
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Sign in", style = MaterialTheme.typography.headlineMedium)

        // dev cheat buttons to quickly sign in
        devSignIn("warden", context)
        devSignIn("student", context)

        // type in email/password
        signInTextField("Name", context.email, {context.email = it}, VisualTransformation.None, true)
        signInTextField("Password", context.password, {context.password = it}, PasswordVisualTransformation(), true)

        // submit button
        Button(
            onClick = { context.signInOrSignUp() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !context.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(if (context.isLoading) "Processing..." else "Log In / Sign Up")
        }

        context.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        // log out button
        Button(
            onClick = { context.signOut() },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Log Out")
        }

        devFirebaseErrorHandler()
    }

}

@Composable
private fun devFirebaseErrorHandler() {
    val log = LocalAppLogicContext.current.alarm
    if (log.error != null) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = "Firestore Error: ${log.error?.message}",
                onValueChange = {},
                readOnly = true,
                label = { Text("System Error") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.error,
                    unfocusedTextColor = MaterialTheme.colorScheme.error,
                    focusedBorderColor = MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = MaterialTheme.colorScheme.error
                )
            )
            Button(
                onClick = { log.crash() },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Report Error (Crash)")
            }
        }
    }
}

@Composable
private fun signInTextField(name: String, getSign: String, setSign: (String) -> Unit, hide: VisualTransformation, enabled: Boolean ) {
    OutlinedTextField(
        value = getSign,
        onValueChange = setSign,
        label = { Text(name) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = hide,
        singleLine = true,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun devSignIn(name: String, context: LogInPageContext) {
//    val context = LocalAppContext.current.loginPage

    Button(
        onClick = {
            context.email = "$name@imperial.ac.uk"
            context.password = name
            context.signInOrSignUp()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = !context.isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(if (context.isLoading) "Signing in..." else "Log In As $name")
    }
}

