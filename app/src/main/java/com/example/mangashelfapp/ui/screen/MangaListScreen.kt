package com.example.mangashelfapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.ui.components.UiState
import com.example.mangashelfapp.ui.components.BottomSheetLayout
import com.example.mangashelfapp.ui.components.MangaItem
import com.example.mangashelfapp.ui.viewmodel.MangaViewModel
import com.example.mangashelfapp.util.SortOption
import kotlinx.coroutines.launch

@Composable
fun MangaListScreen(innerPadding: PaddingValues,
  navController: NavController,  viewModel: MangaViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsState()

  when (uiState) {
    is UiState.Loading -> {
      CircularProgressIndicator()
    }

    is UiState.Success -> {
      val mangas = (uiState as UiState.Success).mangas
      MangaGroupedByYear(innerPadding,mangas = mangas,viewModel,navController)
    }

    is UiState.Error -> {
      val errorMessage = (uiState as UiState.Error).message
      Text("Error: $errorMessage", color = Color.Red)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaGroupedByYear(
  innerPadding: PaddingValues,
  mangas: List<Manga>,
  viewModel: MangaViewModel,
  navController: NavController,
) {
  val groupedMangas = mangas.groupBy { it.year }
  val years = groupedMangas.keys.sorted()
  val coroutineScope = rememberCoroutineScope()
  val listState = rememberLazyListState()
  var selectedIndex by remember { mutableStateOf(0) }
  var sortOption by remember { mutableStateOf(SortOption.NONE) }
  var isBottomSheetVisible by remember { mutableStateOf(false) }

  val sortedMangas = remember(sortOption, mangas) {
    viewModel.getSortedMangas(mangas, sortOption)
  }

  // Mapping year to the start index of each group in the LazyColumn
  val yearStartIndices = remember(sortedMangas) {
    val indices = mutableMapOf<Int, Int>()
    var index = 0
    groupedMangas.keys.sorted().forEach { year ->
      indices[year] = index
      index += groupedMangas[year]?.size ?: 0 + 1
    }
    indices
  }

  // Track the visible year by detecting the header or first manga item in each year group
  val visibleYear by remember {
    derivedStateOf {
      val firstVisibleItemIndex = listState.firstVisibleItemIndex
      var currentIndex = 0
      for (year in years) {
        val mangasInYear = groupedMangas[year]?.size ?: 0
        if (firstVisibleItemIndex >= currentIndex && firstVisibleItemIndex < currentIndex + mangasInYear + 1) {
          return@derivedStateOf year
        }
        currentIndex += mangasInYear + 1
      }
      null
    }
  }

  // Auto switch tab when scrolling

  LaunchedEffect(years) {
    if (years.isNotEmpty() && selectedIndex >= years.size) {
      selectedIndex = 0
    }
  }


  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(innerPadding)
  ) {
    IconButton(onClick = { isBottomSheetVisible = true }) {
      Icon(
        imageVector = Icons.Default.Sort,
        contentDescription = "Sort Options",
        tint = MaterialTheme.colorScheme.primary
      )
    }

    if (sortOption == SortOption.NONE && years.isNotEmpty()) {
      ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 8.dp
      ) {
        years.forEachIndexed { index, year ->
          Tab(
            selected = selectedIndex == index,
            onClick = {
              selectedIndex = index
              val targetIndex = yearStartIndices[year] ?: 0
              coroutineScope.launch {
                listState.animateScrollToItem(targetIndex)
              }
            },
            text = { Text(year.toString()) }
          )
        }
      }
    }

    LazyColumn(state = listState) {
      if (sortOption == SortOption.NONE) {
        years.forEach { year ->
          item {
            Text(
              text = "Year $year",
              style = MaterialTheme.typography.titleMedium,
              modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
            )
          }
          items(groupedMangas[year] ?: emptyList()) { manga ->
            MangaItem(manga = manga, onClick ={
              navController.navigate("manga_detail/${manga.id}")
            })
          }
        }
      } else {
        items(sortedMangas) { manga ->
          MangaItem(manga = manga, onClick={
            navController.navigate("manga_detail/${manga.id}")
          })
        }
      }
    }

    BottomSheetLayout(
      isVisible = isBottomSheetVisible,
      onDismissRequest = { isBottomSheetVisible = false },
      onSortOptionSelected = { selectedSortOption ->
        sortOption = selectedSortOption
      },
      selectedSortOption = sortOption
    )
  }
}
