package com.example.expenselogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.expenselogger.ui.navigation.AppNavGraph
import com.example.expenselogger.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val expenseViewModel: ExpenseViewModel = viewModel()

            AppNavGraph(
                navController = navController,
                viewModel = expenseViewModel
            )
        }
    }
}
