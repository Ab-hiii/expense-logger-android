package com.example.expenselogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.repository.ExpenseRepository
import com.example.expenselogger.ui.screen.AddExpenseScreen
import com.example.expenselogger.ui.screen.ExpenseListScreen
import com.example.expenselogger.ui.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.get(this)
        val repository = ExpenseRepository(db)
        val expenseViewModel = ExpenseViewModel(repository)

        setContent {

            // simple screen switch
            var showAddExpense by remember { mutableStateOf(false) }

            if (showAddExpense) {
                AddExpenseScreen(
                    viewModel = expenseViewModel,
                    onDone = { showAddExpense = false }
                )
            } else {
                ExpenseListScreen(
                    viewModel = expenseViewModel,
                    onAddExpense = { showAddExpense = true }
                )
            }
        }
    }
}
