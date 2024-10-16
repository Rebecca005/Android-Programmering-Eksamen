package com.example.pgrexam.screens.order_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.DateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit,
) {
    val orderHistory = viewModel.orderHistory.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Order History")
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackButtonClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back"
                    )
                }
            },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            orderHistory.value.forEach { orderHistory ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp,
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )

                ) {
                    Column(
                        Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {

                        val formattedDate =
                            DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                                .format(orderHistory.date)
                        Text(
                            text = formattedDate, style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        orderHistory.items.forEachIndexed { index, item ->
                            Text(
                                text = "${index + 1}. ${item.title}",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "$ ${orderHistory.items.sumOf { it.price.toBigDecimal() }} (${orderHistory.items.count()} items)",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
