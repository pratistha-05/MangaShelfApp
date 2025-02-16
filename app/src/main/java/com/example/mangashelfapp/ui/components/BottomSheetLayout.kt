package com.example.mangashelfapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mangashelfapp.util.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayout(
  isVisible: Boolean,
  onDismissRequest: () -> Unit,
  onSortOptionSelected: (SortOption) -> Unit,
  selectedSortOption: SortOption
) {
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  if (isVisible) {
    ModalBottomSheet(
      onDismissRequest = onDismissRequest,
      sheetState = bottomSheetState
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        SortOption.values().forEach { option ->
          TextButton(
            onClick = {
              onSortOptionSelected(option)
              onDismissRequest()
            },
            colors = ButtonDefaults.textButtonColors(
              contentColor = if (selectedSortOption == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
          ) {
            Text(
              text = option.displayText(),
              fontWeight = if (selectedSortOption == option) FontWeight.Bold else FontWeight.Normal
            )
          }
        }
      }
    }
  }
}
