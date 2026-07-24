package org.example.pipe2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.account.Authentication

@Composable
fun BoxScope.LogInPage() {
    val auth: Authentication = viewModel { Authentication() }
    
    Column (
        modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text("Who are You?", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        
        OutlinedTextField(
            value = auth.email,
            onValueChange = { auth.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = auth.password,
            onValueChange = { auth.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { auth.signInOrSignUp() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !auth.isLoading
        ) {
            Text(if (auth.isLoading) "Processing..." else "Log In / Sign Up")
        }

        // Display error message
        auth.errorMessage?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}

