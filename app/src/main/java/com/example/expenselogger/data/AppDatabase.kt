package com.example.expenselogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expenselogger.data.dao.*
import com.example.expenselogger.data.entity.*

@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class,
        MerchantEntity::class,
        RawTransactionEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun rawTransactionDao(): RawTransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_logger.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
