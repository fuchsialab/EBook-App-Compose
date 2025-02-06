package com.fuchsia.ebookapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun TabScreen(navController: NavHostController) {

    val tabs = listOf(
        TabItem("Category", Icons.Default.Category, Icons.Filled.Category),
        TabItem("All Books", Icons.Default.Book, Icons.Filled.Book)
    )

    val pageState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pageState.currentPage, modifier = Modifier.fillMaxWidth()) {

            tabs.forEachIndexed { index, tabItem ->

                Tab(
                    modifier = Modifier.fillMaxWidth(),
                    selected = pageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pageState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (pageState.currentPage == index) tabItem.fillIcon else tabItem.icon,
                                contentDescription = null
                            )
                            Text(text = tabItem.title)
                        }
                    },
                    selectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    unselectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface

                )

            }

        }

        HorizontalPager(state = pageState) { page ->
            when (page) {
                0 -> BookByCategory(navController = navController)
                1 -> AllBookScreen(navController = navController)
            }
        }

    }
}

data class TabItem(
    val title: String, val icon: ImageVector, val fillIcon: ImageVector
)