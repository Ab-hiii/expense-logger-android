package com.example.expenselogger.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.entity.RawTransactionEntity
import kotlinx.coroutines.launch

class RawTransactionViewModel(
    private val db: AppDatabase
) : ViewModel() {

    private val _rawTransactions =
        mutableStateOf<List<RawTransactionEntity>>(emptyList())

    val rawTransactions: State<List<RawTransactionEntity>> = _rawTransactions

    fun loadRawTransactions() {
        viewModelScope.launch {
            _rawTransactions.value = db.rawTransactionDao().getAll()
        }
    }
}
