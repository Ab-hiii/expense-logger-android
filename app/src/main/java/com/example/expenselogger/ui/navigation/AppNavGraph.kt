package com.example.expenselogger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expenselogger.ui.screen.AddExpenseScreen
import com.example.expenselogger.ui.screen.ExpenseListScreen
import com.example.expenselogger.viewmodel.ExpenseViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: ExpenseViewModel
) {
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
                onSave = {
                    viewModel.addExpense(it)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}
