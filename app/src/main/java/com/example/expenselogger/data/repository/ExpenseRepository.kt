package com.example.expenselogger.data.repository

import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.entity.CategoryEntity
import com.example.expenselogger.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseRepository(
    private val db: AppDatabase
) {

    fun observeExpenses(): Flow<List<ExpenseEntity>> = flow {
        emit(db.expenseDao().getAllExpenses())
    }

    suspend fun addExpense(expense: ExpenseEntity) {
        db.expenseDao().insert(expense)
    }

    fun observeCategories(): Flow<List<CategoryEntity>> = flow {
        emit(db.categoryDao().getAllCategories())
    }

    suspend fun seedCategoriesIfEmpty() {
        if (db.categoryDao().getAllCategories().isEmpty()) {
            listOf(
                "Food",
                "Transport",
                "Shopping",
                "Rent",
                "Entertainment",
                "Other"
            ).forEach {
                db.categoryDao().insert(CategoryEntity(name = it))
            }
        }
    }
}
