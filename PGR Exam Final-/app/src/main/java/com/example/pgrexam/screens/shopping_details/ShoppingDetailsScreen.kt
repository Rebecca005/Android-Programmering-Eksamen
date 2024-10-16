package com.example.pgrexam.screens.shopping_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pgrexam.screens.RatingBar
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingDetailsScreen(
    viewModel: ShoppingDetailsViewModel,
    onBackButtonClick: () -> Unit,
    onCartButtonClick: () -> Unit,
    onFavoriteButtonClick: () -> Unit,
) {
    val itemState = viewModel.selectedItem.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    val inBag = viewModel.inBag.collectAsState()
    val favorited = viewModel.favorited.collectAsState()

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    CircularProgressIndicator()
    val item = itemState.value
    if (item == null) {
        Text(text = "Failed to get item details")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back"
                        )
                    }
                },
                title = {
                    Text(text = "Product Details")
                },
                actions = {
                    IconButton(onClick = { onFavoriteButtonClick() }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Items"
                        )
                    }

                    IconButton(onClick = { onCartButtonClick() }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Go to cart"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            if (inBag.value) {
                Button(onClick = {
                    viewModel.updateBag(item.id)
                }) {
                    Text(text = "Added")
                    Icon(
                        imageVector = Icons.Default.Check, contentDescription = "Add to Bag"
                    )
                }
            } else {
                Button(onClick = {
                    viewModel.updateBag(item.id)
                }) {
                    Text(text = "Add to Bag")
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = "Add to Bag"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())

        ) {
            AsyncImage(
                modifier = Modifier.aspectRatio(16f / 12),
                model = item.image,
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                contentDescription = "Image of ${item.title}"
            )

            Column(
                Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.category.capitalize(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price: $ ${item.price} ",
                    style = MaterialTheme.typography.titleSmall
                )

                IconButton(
                    onClick = { viewModel.updateFavorite(item.id) },
                    modifier = Modifier
                        .align(Alignment.End)

                ) {
                    if (favorited.value) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Remove from Favorites"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add to Favorites"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description.capitalize(),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(text = item.rating.rate.toString())
                    val rating = item.rating.rate.roundToInt()

                    repeat(rating) {
                        Icon(
                            imageVector = Icons.Filled.Star, contentDescription = ""
                        )
                    }
                    repeat(5 - rating) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline, contentDescription = ""
                        )
                    }
                    Text(text = "${item.rating.count} reviews")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var rating_1 by rememberSaveable {
                    mutableDoubleStateOf(0.0)
                }
                Text(
                    text = "Have you bought this product?",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "⭐️ Leave us a rating ⭐️",
                    style = MaterialTheme.typography.bodySmall
                )
                RatingBar(
                    modifier = Modifier
                        .size(20.dp),
                    rating = rating_1,
                    starsColor = Color.Black
                ) {
                    rating_1 = it
                }
            }
        }
    }
}
