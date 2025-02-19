package com.example.planplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planplate.ui.components.RecipeStep
import com.example.planplate.ui.theme.PlanPlateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanPlateTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Pasta Recipe",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp)
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f)),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(4) { index ->
                                when (index) {
                                    0 -> RecipeStep(
                                        stepNumber = 1,
                                        instruction = "Fill a large pot with water and add a generous pinch of salt",
                                        timerSeconds = null
                                    )

                                    1 -> RecipeStep(
                                        stepNumber = 2,
                                        instruction = "Bring water to a rolling boil",
                                        timerSeconds = 300
                                    )

                                    2 -> RecipeStep(
                                        stepNumber = 3,
                                        instruction = "Add pasta and cook until al dente",
                                        timerSeconds = 480
                                    )

                                    3 -> RecipeStep(
                                        stepNumber = 4,
                                        instruction = "Drain pasta and serve with your favorite sauce",
                                        timerSeconds = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}