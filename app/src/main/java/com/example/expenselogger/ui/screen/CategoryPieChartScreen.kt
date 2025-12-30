package com.example.expenselogger.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expenselogger.data.entity.ExpenseEntity
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPieChartScreen(
    expenses: List<ExpenseEntity>,
    onBack: () -> Unit
) {
    // ✅ Handle Android system back
    BackHandler {
        onBack()
    }

    val categoryTotals = expenses
        .groupBy { it.category }
        .mapValues { it.value.sumOf { e -> e.amount } }

    val total = categoryTotals.values.sum().takeIf { it > 0 } ?: 1.0

    val colors = listOf(
        Color(0xFF4F46E5),
        Color(0xFF22C55E),
        Color(0xFFF97316),
        Color(0xFFEC4899),
        Color(0xFF14B8A6)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Category Chart") },
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
                    .height(240.dp)
            ) {
                var startAngle = -90f

                categoryTotals.entries.forEachIndexed { index, entry ->
                    val sweep = (entry.value / total * 360f).toFloat()

                    this.drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true
                    )

                    startAngle += sweep
                }
            }

            Divider()

            categoryTotals.entries.forEachIndexed { index, entry ->
                val percent = ((entry.value / total) * 100).roundToInt()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(colors[index % colors.size])
                    }

                    Text("${entry.key}: ₹${entry.value} ($percent%)")
                }
            }
        }
    }
}
