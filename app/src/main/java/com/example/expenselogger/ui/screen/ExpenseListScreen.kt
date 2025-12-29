package com.example.expenselogger.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenselogger.data.entity.ExpenseEntity
import com.example.expenselogger.ui.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel,
    onAddExpense: () -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense) {
                Text("+")
            }
        }
    ) { paddingValues ->

        if (expenses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No expenses yet. Tap + to add one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseItem(expense)
                }
            }
        }
    }
}

@Composable
private fun ExpenseItem(expense: ExpenseEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "₹${expense.amount}",
                style = MaterialTheme.typography.titleMedium
            )
            expense.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
