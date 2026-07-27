package org.example.pipe2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.example.pipe2.ui.contexts.LocalAppContext

@Composable
fun BoxScope.LogInPage() {
    val auth = LocalAppContext.current.auth
    
    Column (
        modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text("Sign in", style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = {
                auth.email = "warden@imperial.ac.uk"
                auth.password = "warden"
                auth.signInOrSignUp()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(if (auth.isLoading) "Signing in..." else "Log In As Warden")
        }

        Button(
            onClick = {
                auth.email = "student@imperial.ac.uk"
                auth.password = "student"
                auth.signInOrSignUp()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(if (auth.isLoading) "Signing in..." else "Log In As Student")
        }

        OutlinedTextField(
            value = auth.email,
            onValueChange = { auth.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        OutlinedTextField(
            value = auth.password,
            onValueChange = { auth.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )

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

        // Display error message
        auth.errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}

