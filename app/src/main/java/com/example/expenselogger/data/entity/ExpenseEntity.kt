package com.example.expenselogger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val amount: Double,

    val note: String,

    // REQUIRED by ExpenseDao queries
    val timestamp: Long = System.currentTimeMillis(),

    // REQUIRED by ExpenseDao queries
    val categoryId: Long? = null
)
