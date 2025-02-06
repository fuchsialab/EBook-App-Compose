package com.fuchsia.ebookapp.presentation.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.fuchsia.ebookapp.presentation.nav.Routes
import com.fuchsia.ebookapp.viewModel.MyViewModel

@Composable
fun AllBookScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyViewModel = hiltViewModel()
) {
    val state = viewModel.GetAllBookState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBooks()

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
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(state.value.data!!) {
                        itemBookView(
                            navController = navController,
                            imageUrl = it.BookImageUrl,
                            name = it.BookName,
                            author = it.BookAuthor,
                            category = it.BookCategory,
                            url = it.BookUrl
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun itemBookView(
    imageUrl: String = "",
    name: String = "",
    author: String = "",
    category: String = "",
    url: String = "",
    navController: NavController

) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                navController.navigate(Routes.PdfViewScreen(pdfUrl = url))
            },
    ) {

        Row(modifier = Modifier.fillMaxSize(),
            ) {
            AsyncImage(
                model = imageUrl, contentDescription = null,
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                ) {

                Text(
                    text = name, style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Author: $author",
                    style = MaterialTheme.typography.titleMedium)
                Text(text = "Category: $category",
                    style = MaterialTheme.typography.titleMedium)

            }
        }


    }


}