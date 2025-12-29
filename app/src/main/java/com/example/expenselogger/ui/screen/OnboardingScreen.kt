package com.example.expenselogger.ui.screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenselogger.util.OnboardingPrefs

@Composable
fun OnboardingScreen(
    onDone: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Welcome to ExpenseLogger",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "This app tracks your expenses and can auto-detect UPI payments using notifications."
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                )
            }
        ) {
            Text("Enable Notification Access")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                OnboardingPrefs.markDone(context)
                onDone()
            }
        ) {
            Text("Continue")
        }
    }
}
