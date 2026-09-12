package org.example.pipe2

import androidx.compose.ui.window.ComposeUIViewController
import org.example.pipe2.apps.student.StudentSetUp
import org.example.pipe2.apps.warden.WardenSetUp


// TODO: Give warden its own default view controller
fun StudentMainViewController() = ComposeUIViewController { StudentSetUp() }
fun WardenMainViewController() = ComposeUIViewController { WardenSetUp() }

// Default for now, can be swapped as needed
fun MainViewController() = StudentMainViewController()