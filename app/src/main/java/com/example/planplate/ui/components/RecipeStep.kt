package com.example.planplate.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.example.planplate.ui.theme.CompletedGreen


@Composable
fun RecipeStep(
    stepNumber: Int,
    instruction: String,
    timerSeconds: Int? = null,
    modifier: Modifier = Modifier
) {
    var isTimerRunning by remember { mutableStateOf(false) }
    var isStepCompleted by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isStepCompleted -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                isTimerRunning -> MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                else -> MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        color = when {
                            isStepCompleted -> MaterialTheme.colorScheme.secondary
                            isTimerRunning -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (isStepCompleted) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = stepNumber.toString(),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { isStepCompleted = !isStepCompleted },
                    modifier = Modifier
                        .scale(if (isStepCompleted) 1.2f else 1f)
                        .animateContentSize(),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = if (isStepCompleted) CompletedGreen 
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = if (isStepCompleted) Icons.Filled.CheckCircle
                        else Icons.Outlined.CheckCircle,
                        contentDescription = if (isStepCompleted) "Mark as incomplete"
                        else "Mark as complete"
                    )
                }
            }

            Text(
                text = instruction,
                modifier = Modifier.padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            if (timerSeconds != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { isTimerRunning = !isTimerRunning },
                        modifier = Modifier.padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTimerRunning)
                                MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (isTimerRunning) "Stop Timer" else "Start Timer",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    AnimatedVisibility(
                        visible = isTimerRunning,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            CookingTimer(
                                initialTimeSeconds = timerSeconds,
                                onTimerComplete = {
                                    isTimerRunning = false
                                },
                                showControls = false,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
} 