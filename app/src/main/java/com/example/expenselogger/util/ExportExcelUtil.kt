package com.example.expenselogger.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.expenselogger.data.entity.ExpenseEntity
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object ExportExcelUtil {

    fun exportExpenses(
        context: Context,
        expenses: List<ExpenseEntity>,
        label: String = "expenses"
    ) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Expenses")

        val header = sheet.createRow(0)
        listOf("Date", "Time", "Amount", "Category", "Note")
            .forEachIndexed { index, title ->
                header.createCell(index).setCellValue(title)
            }

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        expenses.forEachIndexed { index, expense ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(dateFormat.format(Date(expense.timestamp)))
            row.createCell(1).setCellValue(timeFormat.format(Date(expense.timestamp)))
            row.createCell(2).setCellValue(expense.amount)
            row.createCell(3).setCellValue(expense.category)
            row.createCell(4).setCellValue(expense.note)
        }

        val safeLabel = label.lowercase().replace(" ", "_")
        val file = File(context.cacheDir, "$safeLabel.xlsx")
        val outputStream = FileOutputStream(file)

        workbook.write(outputStream)
        outputStream.close()
        workbook.close()

        shareFile(context, file)
    }

    private fun shareFile(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share expenses")
        )
    }
}
