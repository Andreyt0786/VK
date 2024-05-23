package online.shop.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import online.shop.App
import online.shop.di.injectedViewModel
import online.shop.ui.list.components.ChipsPicker
import online.shop.ui.list.components.ProductsList
import online.shop.ui.list.components.SearchText
import online.shop.ui.theme.ErrorScreen

@Composable
fun ListScreenHolder(navController: NavController? = null) {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.listViewModelFactory.create()
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actions
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect {
                when (it) {
                    is ListScreenUiAction.Navigate -> {
                        navController!!.navigate(it.route)
                    }
                }
            }
    }

    ListScreen(state = state, viewModel::obtainEvent)
}

@Composable
private fun ListScreen(state: ListScreenUiState, obtainEvent: (ListUiEvent) -> Unit) {
    when (state) {
        is ListScreenUiState.Content -> MainState(state = state, obtainEvent = obtainEvent)
        is ListScreenUiState.Error -> ErrorScreen(onClickButton = { obtainEvent.invoke(ListUiEvent.Reload) })
        is ListScreenUiState.Loading -> LinearProgressIndicator()
    }
}

@Composable
private fun MainState(state: ListScreenUiState.Content, obtainEvent: (ListUiEvent) -> Unit) {
    Scaffold(
        topBar = {
            Column {
                SearchText(
                    value = state.query,
                    onSearchText = { obtainEvent.invoke(ListUiEvent.SearchByQuery(query = it)) })
                ChipsPicker(
                    listCategories = state.categoriesChips,
                    onClick = { obtainEvent.invoke(ListUiEvent.SelectCategory(it)) },
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    ) { paddingValue ->
        ProductsList(
            data = state.items,
            showLoadingProgressBar = state.loadingNewPage,
            onClickItemEvent = { obtainEvent.invoke(ListUiEvent.NavigateToProductDetails(productId = it)) },
            onListEndEvent = { obtainEvent.invoke(ListUiEvent.AddPageInData) },
            modifier = Modifier.padding(paddingValue)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ListScreenPreviewLoading() {
    ListScreen(state = ListScreenUiState.Loading, obtainEvent = {})
}

@Composable
@Preview(showBackground = true)
private fun ListScreenPreviewData() {
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
    val categories = listOf(
        ChipsUiModel(name = "smartphones", selected = true),
        ChipsUiModel(name = "car", selected = false)
    )


    ListScreen(
        state = ListScreenUiState.Content(items = data, categoriesChips = categories),
        obtainEvent = {})
}

@Composable
@Preview(showBackground = true)
private fun ListScreenPreviewError() {
    ListScreen(state = ListScreenUiState.Error, obtainEvent = {})
}
