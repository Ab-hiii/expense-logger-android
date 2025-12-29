package com.example.expenselogger.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object ShareUtil {

    fun shareFile(
        context: Context,
        file: File,
        mimeType: String
    ) {
        val uri = FileProvider.getUriForFile(
            context,
            "com.example.expenselogger.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Share file")
        )
    }
}
