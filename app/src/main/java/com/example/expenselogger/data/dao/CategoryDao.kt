package com.example.expenselogger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expenselogger.data.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>
}
