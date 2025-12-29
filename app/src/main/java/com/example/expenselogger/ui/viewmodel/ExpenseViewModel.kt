package com.example.expenselogger.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenselogger.data.entity.ExpenseEntity
import com.example.expenselogger.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            repository.observeExpenses().collect { list ->
                _expenses.value = list
            }
        }
    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
            loadExpenses()   // 🔥 THIS IS THE FIX
        }
    }
}
