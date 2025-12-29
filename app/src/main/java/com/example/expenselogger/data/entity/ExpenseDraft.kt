package com.example.expenselogger.data.entity

data class ExpenseDraft(
    val rawTransactionId: Int,
    val amount: Double?,
    val merchant: String?,
    val description: String?
)
