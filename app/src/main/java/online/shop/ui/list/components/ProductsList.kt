package online.shop.ui.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import online.shop.ui.list.ProductPreviewUiModel


@Composable
fun ProductsList(
    data: List<ProductPreviewUiModel>,
    showLoadingProgressBar: Boolean = false,
    onClickItemEvent: (Long) -> Unit = {},
    onListEndEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = data, key = { it.id }) {
                ItemListProduct(item = it, onClickItemEvent = onClickItemEvent)
            }
        }

        if (showLoadingProgressBar) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filterNotNull()
            .onEach { lastIndex ->
                if (lastIndex == listState.layoutInfo.totalItemsCount - 3) {
                    onListEndEvent.invoke()
                }
            }
            .launchIn(this)
    }
}


@Composable
@Preview(showBackground = true)
private fun ProductsListComponentPreview() {
    val data = listOf(
        ProductPreviewUiModel(
            id = 1,
            title = "Test",
            category = "Test Cat",
            imgUrl = "",
        ),
        ProductPreviewUiModel(
            id = 2,
            title = "Test 2",
            category = "Test Cat",
            imgUrl = "",
        ),
        ProductPreviewUiModel(
            id = 3,
            title = "Test 3",
            category = "Test Cat",
            imgUrl = "",
        )
    )
    ProductsList(data = data, onListEndEvent = {})
}