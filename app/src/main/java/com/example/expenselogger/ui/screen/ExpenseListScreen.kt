package com.example.expenselogger.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenselogger.ui.viewmodel.ExpenseViewModel
import com.example.expenselogger.util.ExportCsvUtil
import com.example.expenselogger.util.ExportExcelUtil
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private enum class DateFilter { ALL, TODAY, THIS_MONTH }

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel,
    onAddClick: () -> Unit
) {
    val allExpenses by viewModel.expenses.collectAsState(initial = emptyList())
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedDateFilter by remember { mutableStateOf(DateFilter.ALL) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    var showPieChart by remember { mutableStateOf(false) }
    var showMonthlyChart by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = searchQuery.isNotBlank()) {
        searchQuery = ""
    }

    val categories = remember(allExpenses) {
        allExpenses.map { it.category }.distinct()
    }

    val filteredExpenses = allExpenses
        .filter {
            when (selectedDateFilter) {
                DateFilter.ALL -> true
                DateFilter.TODAY -> isToday(it.timestamp)
                DateFilter.THIS_MONTH -> isThisMonth(it.timestamp)
            }
        }
        .filter {
            selectedCategory == null || it.category == selectedCategory
        }
        .filter {
            searchQuery.isBlank() ||
                    it.note.contains(searchQuery, ignoreCase = true) ||
                    it.amount.toString().contains(searchQuery)
        }

    if (showPieChart) {
        CategoryPieChartScreen(
            expenses = filteredExpenses,
            onBack = { showPieChart = false }
        )
        return
    }

    if (showMonthlyChart) {
        MonthlyBarChartScreen(
            expenses = filteredExpenses,
            onBack = { showMonthlyChart = false }
        )
        return
    }

    val totalAmount = filteredExpenses.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                navigationIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search expenses") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = selectedDateFilter == DateFilter.ALL,
                        onClick = { selectedDateFilter = DateFilter.ALL },
                        label = { Text("All") }
                    )
                    FilterChip(
                        selected = selectedDateFilter == DateFilter.TODAY,
                        onClick = { selectedDateFilter = DateFilter.TODAY },
                        label = { Text("Today") }
                    )
                    FilterChip(
                        selected = selectedDateFilter == DateFilter.THIS_MONTH,
                        onClick = { selectedDateFilter = DateFilter.THIS_MONTH },
                        label = { Text("This Month") }
                    )
                }
            }

            if (categories.isNotEmpty()) {
                item {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        categories.forEach { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = {
                                    selectedCategory =
                                        if (selectedCategory == category) null else category
                                },
                                label = { Text(category) }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Total: ₹$totalAmount",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = {
                        ExportCsvUtil.exportExpenses(context, filteredExpenses)
                    }) {
                        Text("CSV")
                    }

                    OutlinedButton(onClick = {
                        ExportExcelUtil.exportExpenses(context, filteredExpenses)
                    }) {
                        Text("Excel")
                    }

                    OutlinedButton(onClick = { showPieChart = true }) {
                        Text("Pie Chart")
                    }

                    OutlinedButton(onClick = { showMonthlyChart = true }) {
                        Text("Monthly Trend")
                    }
                }
            }

            item { Divider() }

            if (filteredExpenses.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "No expenses found",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Try changing filters or add a new expense",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(filteredExpenses, key = { it.id }) { expense ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    viewModel.deleteExpense(expense)
                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Expense deleted",
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.addExpense(expense)
                                        }
                                    }
                                }
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "₹${expense.amount} • ${expense.category}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (expense.note.isNotBlank()) {
                                Text(expense.note)
                            }
                            Text(
                                formatDate(expense.timestamp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun isToday(ts: Long): Boolean {
    val now = Calendar.getInstance()
    val other = Calendar.getInstance().apply { timeInMillis = ts }
    return now.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
}

private fun isThisMonth(ts: Long): Boolean {
    val now = Calendar.getInstance()
    val other = Calendar.getInstance().apply { timeInMillis = ts }
    return now.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            now.get(Calendar.MONTH) == other.get(Calendar.MONTH)
}

private fun formatDate(ts: Long): String {
    return SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        .format(Date(ts))
}
