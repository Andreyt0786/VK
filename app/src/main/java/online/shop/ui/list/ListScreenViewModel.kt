package online.shop.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import online.shop.domain.repo.NetworkRepo
import online.shop.domain.usecase.AddNewPageUsecase
import online.shop.domain.usecase.SearchByQueryUsecase
import online.shop.domain.usecase.SelectCategoryUsecase
import online.shop.ui.nav.Routes
import online.shop.util.runSuspendCatching

class ListScreenViewModel @AssistedInject constructor(
    private val networkRepo: NetworkRepo,
    private val uiMapper: ListUiMapper,
    private val addNewPageUsecase: AddNewPageUsecase,
    private val searchByQueryUsecase: SearchByQueryUsecase,
    private val selectCategoryUsecase: SelectCategoryUsecase,
) : ViewModel() {

    private val _state = MutableStateFlow<ListScreenUiState>(ListScreenUiState.Loading)
    val state: StateFlow<ListScreenUiState> = _state

    private val _actions = MutableSharedFlow<ListScreenUiAction>()
    val actions: Flow<ListScreenUiAction> = _actions

    init {
        init()
    }

    fun obtainEvent(event: ListUiEvent) {
        when (event) {
            is ListUiEvent.AddPageInData -> addNewPage()
            is ListUiEvent.NavigateToProductDetails -> navigateToProductDetails(productId = event.productId)
            is ListUiEvent.SearchByQuery -> searchByQuery(event.query)
            is ListUiEvent.SelectCategory -> selectCategory(event.category)
            is ListUiEvent.Reload -> init()
        }
    }

    private fun selectCategory(category: String) {
        when (val currentState = _state.value) {
            is ListScreenUiState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            _state.value = ListScreenUiState.Loading
                            selectCategoryUsecase(
                                repo = networkRepo,
                                categoriesChips = currentState.categoriesChips,
                                category = category
                            )
                        },
                        onSuccess = { data ->
                            _state.value =
                                currentState.copy(
                                    items = uiMapper(data.first),
                                    selectedCategory = if (data.second.firstOrNull { it.selected } != null) category else null,
                                    categoriesChips = data.second.sortedByDescending { it.selected }
                                )
                        },
                        onError = { _state.value = ListScreenUiState.Error }
                    )
                }
            }

            is ListScreenUiState.Error -> {}
            is ListScreenUiState.Loading -> {}
        }
    }

    private fun searchByQuery(query: String) {
        when (val currentState = _state.value) {
            is ListScreenUiState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            _state.value = ListScreenUiState.Loading
                            searchByQueryUsecase.invoke(
                                repo = networkRepo,
                                selectedCategory = currentState.selectedCategory,
                                query = query
                            )
                        },
                        onSuccess = { data ->
                            _state.value =
                                currentState.copy(
                                    items = uiMapper(data),
                                    query = query.lowercase()
                                )
                        },
                        onError = { _state.value = ListScreenUiState.Error }
                    )
                }
            }

            ListScreenUiState.Error -> {}
            ListScreenUiState.Loading -> {}
        }
    }

    private fun navigateToProductDetails(productId: Long) {
        viewModelScope.launch {
            _actions.emit(ListScreenUiAction.Navigate(route = "${Routes.DETAILS.name}?productId=$productId"))
        }
    }

    private fun addNewPage() {
        when (val currentState = _state.value) {
            is ListScreenUiState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            _state.value = currentState.copy(loadingNewPage = true)
                            addNewPageUsecase(
                                repo = networkRepo,
                                items = currentState.items,
                                query = currentState.query,
                                selectedCategory = currentState.selectedCategory
                            )
                        },
                        onSuccess = { data ->
                            _state.value =
                                currentState.copy(items = currentState.items + uiMapper(data))
                        },
                        onError = { _state.value = ListScreenUiState.Error }
                    )
                }
            }

            ListScreenUiState.Error -> {}
            ListScreenUiState.Loading -> {}
        }
    }


    private fun init() {
        viewModelScope.launch {
            runSuspendCatching(
                action = {
                    networkRepo.getAllProductsPaging() to networkRepo.getCategories()
                },
                onSuccess = { dataPair ->
                    _state.value = ListScreenUiState.Content(
                        items = uiMapper(dataPair.first),
                        categoriesChips = dataPair.second.map {
                            ChipsUiModel(
                                name = it,
                                selected = false
                            )
                        }
                    )
                },
                onError = { _state.value = ListScreenUiState.Error }
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ListScreenViewModel
    }
}