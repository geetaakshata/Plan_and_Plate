package com.example.planplate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun CookingTimer(
    initialTimeSeconds: Int,
    onTimerComplete: () -> Unit,
    showControls: Boolean = true,
    modifier: Modifier = Modifier
) {
    var timeRemaining by remember { mutableStateOf(initialTimeSeconds) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning && timeRemaining > 0) {
            delay(1.seconds)
            timeRemaining--
            if (timeRemaining == 0) {
                onTimerComplete()
                isRunning = false
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(timeRemaining),
            style = MaterialTheme.typography.titleLarge,
            color = when {
                timeRemaining == 0 -> MaterialTheme.colorScheme.error
                timeRemaining <= 60 -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }
        )

        if (showControls) {
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { isRunning = !isRunning },
                    enabled = timeRemaining > 0
                ) {
                    Text(if (isRunning) "Pause" else "Start")
                }

                Button(
                    onClick = {
                        isRunning = false
                        timeRemaining = initialTimeSeconds
                    }
                ) {
                    Text("Reset")
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
} 