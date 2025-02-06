package com.fuchsia.ebookapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.fuchsia.ebookapp.presentation.nav.Routes
import com.fuchsia.ebookapp.viewModel.MyViewModel

@Composable
fun BookByCategory(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyViewModel = hiltViewModel()
) {

    val state = viewModel.GetAllCategoryState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategory()

    }
    when {
        state.value.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }

        state.value.error != null -> {
            Text(text = state.value.error!!)
        }

        state.value.data != null -> {
            Column(modifier = Modifier.fillMaxSize()) {

                Scaffold { it ->

                    it.calculateTopPadding()

                    Scaffold { innerPadding ->

                        LazyVerticalGrid(
                            GridCells.Fixed(2),
                            modifier = modifier.fillMaxSize()
                        ) {
                            items(state.value.data!!) {
                                itemView(
                                    navController = navController,
                                    imageUrl = it.CategoryImageUrl, name = it.CategoryName
                                )

                            }

                        }
                    }

                }

            }

        }

    }
}


@Composable
fun itemView(
    navController: NavController,
    imageUrl: String = "",
    name: String = ""
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                navController.navigate(Routes.BookByCategoryScreen(category = name))

            },
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(model = imageUrl, contentDescription = null)
            Text(
                text = name,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleMedium,
            )

        }


    }


}
