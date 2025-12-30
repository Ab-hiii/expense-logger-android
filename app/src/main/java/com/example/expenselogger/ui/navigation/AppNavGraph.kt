package com.example.expenselogger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.repository.ExpenseRepository
import com.example.expenselogger.ui.screen.AddExpenseScreen
import com.example.expenselogger.ui.screen.ExpenseListScreen
import com.example.expenselogger.ui.viewmodel.ExpenseViewModel
import com.example.expenselogger.ui.viewmodel.ExpenseViewModelFactory

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ✅ CORRECT method based on your AppDatabase
    val database = AppDatabase.getInstance(context)
    val repository = ExpenseRepository(database.expenseDao())

    val viewModel: ExpenseViewModel = viewModel(
        factory = ExpenseViewModelFactory(repository)
    )

    NavHost(
        navController = navController,
        startDestination = Routes.EXPENSE_LIST
    ) {

        composable(Routes.EXPENSE_LIST) {
            ExpenseListScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(Routes.ADD_EXPENSE)
                }
            )
        }

        composable(Routes.ADD_EXPENSE) {
            AddExpenseScreen(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
