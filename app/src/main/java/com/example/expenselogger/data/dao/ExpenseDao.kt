package com.example.expenselogger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expenselogger.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query(
        "SELECT * FROM expenses WHERE categoryId = :categoryId ORDER BY timestamp DESC"
    )
    fun getExpensesByCategory(categoryId: Long): Flow<List<ExpenseEntity>>
}
