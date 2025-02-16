package com.example.mangashelfapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.ui.UiState
import com.example.mangashelfapp.ui.components.MangaItem
import com.example.mangashelfapp.ui.viewmodel.MangaViewModel
import kotlinx.coroutines.launch

@Composable
fun MangaListScreen(viewModel: MangaViewModel = hiltViewModel()) {
  val uiState by viewModel.uiState.collectAsState()

  when (uiState) {
    is UiState.Loading -> {
      CircularProgressIndicator()
    }

    is UiState.Success -> {
      val mangas = (uiState as UiState.Success).mangas
      MangaGroupedByYear(mangas = mangas)
    }

    is UiState.Error -> {
      val errorMessage = (uiState as UiState.Error).message
      Text("Error: $errorMessage", color = Color.Red)
    }
  }
}

@Composable
fun MangaGroupedByYear(mangas: List<Manga>) {
  val groupedMangas = mangas.groupBy { it.year }
  val years = groupedMangas.keys.sorted()
  val coroutineScope = rememberCoroutineScope()
  val listState = rememberLazyListState()
  var selectedIndex by remember { mutableStateOf(0) }


  // Track the visible year
  val visibleYear by remember {
    derivedStateOf {
      val firstVisibleItemIndex = listState.firstVisibleItemIndex
      mangas.getOrNull(firstVisibleItemIndex)?.year
    }
  }

  // Auto switch tab when scrolling
  LaunchedEffect(visibleYear) {
    val visibleYearIndex = years.indexOf(visibleYear)
    if (visibleYearIndex != -1 && selectedIndex != visibleYearIndex) {
      selectedIndex = visibleYearIndex
    }
  }

  Column {
    ScrollableTabRow(
      selectedTabIndex = selectedIndex,
      edgePadding = 8.dp
    ) {
      years.forEachIndexed { index, year ->
        Tab(
          selected = selectedIndex == index,
          onClick = {
            selectedIndex = index
            val targetIndex = mangas.indexOfFirst { it.year == year }

            if (targetIndex != -1) {
              coroutineScope.launch {
                listState.animateScrollToItem(targetIndex)
              }
            }
          },
          text = { Text(year.toString()) }
        )
      }
    }

    LazyColumn(state = listState) {
      items(mangas) { manga ->
        MangaItem(manga = manga)
      }
    }
  }
}
