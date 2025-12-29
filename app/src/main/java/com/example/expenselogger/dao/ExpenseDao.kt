package com.example.expenselogger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expenselogger.data.entity.ExpenseEntity

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY transactionDate DESC")
    suspend fun getAllExpenses(): List<ExpenseEntity>
}
