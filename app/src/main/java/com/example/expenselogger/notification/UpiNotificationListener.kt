package com.example.expenselogger.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.entity.RawTransactionEntity
import com.example.expenselogger.util.UpiParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpiNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.phonepe.app") return

        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        val fullText = "$title $text"

        val amount = UpiParser.parseAmount(fullText)
        val merchant = UpiParser.parseMerchant(fullText)

        val db = AppDatabase.get(applicationContext)

        val raw = RawTransactionEntity(
            source = "PHONEPE",
            rawText = fullText,
            parsedAmount = amount,
            parsedMerchant = merchant,
            parsedDate = System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.rawTransactionDao().insert(raw)
        }
    }
}
