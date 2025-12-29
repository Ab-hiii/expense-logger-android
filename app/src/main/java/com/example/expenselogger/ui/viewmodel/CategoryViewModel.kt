package com.example.expenselogger.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenselogger.data.entity.CategoryEntity
import com.example.expenselogger.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories

    fun loadCategories() {
        viewModelScope.launch {
            repository.seedCategoriesIfEmpty()
            repository.observeCategories().collect {
                _categories.value = it
            }
        }
    }
}
