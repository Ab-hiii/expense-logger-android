package com.example.expenselogger.util

import java.util.Locale
import java.util.regex.Pattern

object UpiParser {

    private val amountPattern =
        Pattern.compile("₹\\s?([0-9]+(?:\\.[0-9]{1,2})?)")

    private val paidToPattern =
        Pattern.compile("(?:paid to|sent to|payee)\\s+([A-Za-z0-9 .&_-]+)",
            Pattern.CASE_INSENSITIVE)

    fun parseAmount(text: String): Double? {
        val m = amountPattern.matcher(text)
        return if (m.find()) m.group(1)?.toDoubleOrNull() else null
    }

    fun parseMerchant(text: String): String? {
        val m = paidToPattern.matcher(text)
        return if (m.find()) {
            m.group(1)?.trim()?.uppercase(Locale.getDefault())
        } else null
    }
}
