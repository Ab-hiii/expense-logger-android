package com.example.expenselogger.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenselogger.data.entity.ExpenseEntity
import com.example.expenselogger.ui.viewmodel.ExpenseViewModel

@Composable
fun AddExpenseScreen(
    viewModel: ExpenseViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Other") }

    val categories = listOf("Food", "Travel", "Shopping", "Bills", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") },
            modifier = Modifier.fillMaxWidth()
        )

        // CATEGORY SELECT
        Text("Category", style = MaterialTheme.typography.titleSmall)

        categories.forEach { category ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
                Text(category)
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    val amt = amount.toDoubleOrNull() ?: return@Button
                    viewModel.addExpense(
                        ExpenseEntity(
                            amount = amt,
                            note = note,
                            timestamp = System.currentTimeMillis(),
                            category = selectedCategory
                        )
                    )
                    onSave()
                }
            ) {
                Text("Save")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}
