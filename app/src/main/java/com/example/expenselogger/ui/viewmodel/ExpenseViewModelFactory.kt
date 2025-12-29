package com.example.expenselogger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExpenseViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        throw IllegalStateException(
            "ExpenseViewModelFactory is disabled during recovery phase"
        )
    }
}
