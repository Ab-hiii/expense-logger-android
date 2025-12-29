package com.example.expenselogger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "merchants")
data class MerchantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
