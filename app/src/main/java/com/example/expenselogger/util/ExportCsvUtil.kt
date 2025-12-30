package com.example.expenselogger.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.expenselogger.data.entity.ExpenseEntity
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object ExportCsvUtil {

    fun exportExpenses(
        context: Context,
        expenses: List<ExpenseEntity>,
        label: String = "expenses"
    ) {
        val safeLabel = label.lowercase().replace(" ", "_")
        val file = File(context.cacheDir, "$safeLabel.csv")
        val writer = FileWriter(file)

        writer.append("Date,Time,Amount,Category,Note\n")

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        expenses.forEach { expense ->
            val date = dateFormat.format(Date(expense.timestamp))
            val time = timeFormat.format(Date(expense.timestamp))

            writer.append(
                listOf(
                    "'$date",
                    time,
                    expense.amount.toString(),
                    expense.category,
                    expense.note.replace(",", " ")
                ).joinToString(",")
            )
            writer.append("\n")
        }

        writer.flush()
        writer.close()

        shareFile(context, file)
    }

    private fun shareFile(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share expenses")
        )
    }
}
