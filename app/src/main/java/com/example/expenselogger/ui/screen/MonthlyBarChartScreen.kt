package com.example.expenselogger.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expenselogger.data.entity.ExpenseEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyBarChartScreen(
    expenses: List<ExpenseEntity>,
    onBack: () -> Unit
) {
    val monthFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    val monthlyTotals = expenses
        .groupBy {
            Calendar.getInstance().apply { timeInMillis = it.timestamp }
                .let { cal -> "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}" }
        }
        .mapValues { it.value.sumOf { e -> e.amount } }
        .toSortedMap()

    val labels = monthlyTotals.keys.map {
        val parts = it.split("-")
        Calendar.getInstance().apply {
            set(Calendar.YEAR, parts[0].toInt())
            set(Calendar.MONTH, parts[1].toInt())
        }.time.let(monthFormat::format)
    }

    val values = monthlyTotals.values.toList()
    val maxValue = values.maxOrNull()?.takeIf { it > 0 } ?: 1.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Spend") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                val barWidth = size.width / (values.size * 2f)

                values.forEachIndexed { index, value ->
                    val barHeight = (value / maxValue) * size.height

                    drawRect(
                        color = Color(0xFF4F46E5),
                        topLeft = androidx.compose.ui.geometry.Offset(
                            x = index * barWidth * 2 + barWidth / 2,
                            y = size.height - barHeight.toFloat()
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            width = barWidth,
                            height = barHeight.toFloat()
                        )
                    )
                }
            }

            Divider()

            labels.forEachIndexed { index, label ->
                Text("$label: ₹${values[index]}")
            }
        }
    }
}
