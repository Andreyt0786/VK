package online.shop.ui.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.shop.R
import online.shop.ui.list.ChipsUiModel

@Composable
fun ChipsPicker(
    listCategories: List<ChipsUiModel>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow {
        items(items = listCategories) {
            AssistChip(
                onClick = { onClick.invoke(it.name) },
                label = { Text(text = it.name) },
                leadingIcon = { if (it.selected) ChipIcon() },
                modifier = modifier.padding(end = 5.dp)
            )
        }
    }
}

@Composable
private fun ChipIcon() {
    Icon(
        painter = painterResource(id = R.drawable.baseline_close_24),
        contentDescription = "selected"
    )
}

@Composable
@Preview(showBackground = true)
private fun ChipsPickerPreview() {
    val categories = listOf(
        ChipsUiModel(name = "smartphones", selected = true),
        ChipsUiModel(name = "car", selected = false)
    )

    ChipsPicker(listCategories = categories, onClick = {})
}