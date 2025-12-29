package com.example.expenselogger.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.expenselogger.data.entity.ExpenseEntity

@Composable
fun AddExpenseScreen(
    onSave: (ExpenseEntity) -> Unit,
    onCancel: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") }
        )

        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") }
        )

        Button(
            onClick = {
                onSave(
                    ExpenseEntity(
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        note = note
                    )
                )
            }
        ) {
            Text("Save")
        }

        TextButton(onClick = onCancel) {
            Text("Cancel")
        }
    }
}
