package com.example.expenselogger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "raw_transactions")
data class RawTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: String,
    val rawText: String,
    val parsedAmount: Double?,
    val parsedMerchant: String?,
    val parsedDate: Long?,
    val receivedAt: Long = System.currentTimeMillis()
)
