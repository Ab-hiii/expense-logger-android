package com.example.expenselogger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expenselogger.data.entity.RawTransactionEntity

@Dao
interface RawTransactionDao {

    @Insert
    suspend fun insert(transaction: RawTransactionEntity)

    @Query("SELECT * FROM raw_transactions ORDER BY receivedAt DESC")
    suspend fun getAll(): List<RawTransactionEntity>

    @Query("""
        UPDATE raw_transactions
        SET parsedAmount = :amount,
            parsedMerchant = :merchant
        WHERE id = :id
    """)
    suspend fun updateParsedFields(
        id: Int,
        amount: Double?,
        merchant: String?
    )
}
