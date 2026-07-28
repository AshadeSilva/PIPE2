package org.example.pipe2.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.example.pipe2.data.account.Authentication
import org.example.pipe2.logic.LocalAppContext

@Composable
fun LoginPage() {
    val auth = LocalAppContext.current.auth

    Text("Sign in", style = MaterialTheme.typography.headlineMedium)

    // dev cheat buttons to quickly sign in
    devSignIn("warden")
    devSignIn("student")

    // type in email/password
    signInTextField("Name", auth.email, {auth.email = it}, VisualTransformation.None)
    signInTextField("Password", auth.password, {auth.password = it}, PasswordVisualTransformation())

    // submit button
    Button(
        onClick = { auth.signInOrSignUp() },
        modifier = Modifier.fillMaxWidth(),
        enabled = !auth.isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(if (auth.isLoading) "Processing..." else "Log In / Sign Up")
    }
    auth.errorMessage?.let {
        Text(it, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun signInTextField(name: String, getSign: String, setSign: (String) -> Unit, hide: VisualTransformation ) {
    OutlinedTextField(
        value = getSign,
        onValueChange = setSign,
        label = { Text(name) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = hide,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun devSignIn(name: String) {
    val auth = LocalAppContext.current.auth

    Button(
        onClick = {
            auth.email = "$name@imperial.ac.uk"
            auth.password = name
            auth.signInOrSignUp()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(if (auth.isLoading) "Signing in..." else "Log In As $name")
    }
}

