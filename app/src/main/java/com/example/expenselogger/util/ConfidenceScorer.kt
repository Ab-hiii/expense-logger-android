package com.example.expenselogger.util

object ConfidenceScorer {

    fun score(
        amountParsed: Boolean,
        merchantParsed: Boolean
    ): Double {
        var score = 0.0

        if (amountParsed) score += 0.6
        if (merchantParsed) score += 0.4

        return score.coerceIn(0.0, 1.0)
    }
}
