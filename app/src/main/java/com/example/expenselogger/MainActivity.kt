package com.example.expenselogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expenselogger.ui.navigation.AppNavGraph
import com.example.expenselogger.ui.theme.ExpenseLoggerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseLoggerTheme {
                AppNavGraph()
            }
        }
    }
}
