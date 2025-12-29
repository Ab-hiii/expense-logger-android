package com.example.expenselogger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.expenselogger.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExpenseViewModel : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses

    fun addExpense(expense: ExpenseEntity) {
        _expenses.value = _expenses.value + expense
    }
}
