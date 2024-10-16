package com.example.pgrexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.screens.bag_list.BagListScreen
import com.example.pgrexam.screens.bag_list.BagListViewModel
import com.example.pgrexam.screens.favorite_list.FavoriteListScreen
import com.example.pgrexam.screens.favorite_list.FavoriteListViewModel
import com.example.pgrexam.screens.order_history.OrderHistoryScreen
import com.example.pgrexam.screens.order_history.OrderHistoryViewModel
import com.example.pgrexam.screens.shopping_details.ShoppingDetailsScreen
import com.example.pgrexam.screens.shopping_details.ShoppingDetailsViewModel
import com.example.pgrexam.screens.shopping_list.ShoppingListScreen
import com.example.pgrexam.screens.shopping_list.ShoppingListViewModel
import com.example.pgrexam.ui.theme.PGRExamTheme

class MainActivity : ComponentActivity() {
    private val _shoppingListViewModel: ShoppingListViewModel by viewModels()
    private val _bagListViewModel: BagListViewModel by viewModels()
    private val _shoppingDetailsViewModel: ShoppingDetailsViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private val _favoriteListViewModel: FavoriteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ShoppingRepository.initializeDatabase(applicationContext)

        setContent {
            PGRExamTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "shoppingListScreen"
                ) {
                    //Adding new destinations by using composable(), and giving the destination a new name('route')
                    composable(route = "shoppingListScreen") {
                        ShoppingListScreen(viewModel = _shoppingListViewModel,
                            onItemClick = { itemId ->
                                navController.navigate("shoppingDetailsScreen/${itemId}")
                            },
                            onCartClick = { navController.navigate("bagListScreen") },
                            onOrderHistoryClick = { navController.navigate("orderHistoryScreen") },
                            onFavoriteClick = { navController.navigate("favoriteListScreen") })
                    }
                    composable(
                        route = "shoppingDetailsScreen/{itemId}",
                        arguments = listOf(navArgument(name = "itemId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1

                        LaunchedEffect(itemId) {
                            _shoppingDetailsViewModel.setSelectedItem(itemId)
                        }

                        ShoppingDetailsScreen(viewModel = _shoppingDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onCartButtonClick = { navController.navigate("bagListScreen") },
                            onFavoriteButtonClick = { navController.navigate("favoriteListScreen") })
                    }
                    composable(route = "bagListScreen") {
                        LaunchedEffect(Unit) {
                            _bagListViewModel.loadItems()
                        }
                        BagListScreen(viewModel = _bagListViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onItemClick = { itemId ->
                                navController.navigate("shoppingDetailsScreen/$itemId")
                            })
                    }
                    composable(route = "favoriteListScreen") {
                        LaunchedEffect(Unit) {
                            _favoriteListViewModel.loadFavoriteItems()
                        }
                        FavoriteListScreen(viewModel = _favoriteListViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onFavoriteClick = { itemId ->
                                navController.navigate("shoppingDetailsScreen/$itemId")
                            })
                    }
                    composable(route = "orderHistoryScreen") {
                        LaunchedEffect(Unit) {
                            _orderHistoryViewModel.loadOrderHistory()
                        }
                        OrderHistoryScreen(
                            viewModel = _orderHistoryViewModel,
                            onBackButtonClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}