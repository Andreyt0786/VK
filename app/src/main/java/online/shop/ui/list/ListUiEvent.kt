package online.shop.ui.list

sealed interface ListUiEvent {
    data object AddPageInData : ListUiEvent
    data class SearchByQuery(val query: String) : ListUiEvent
    data class NavigateToProductDetails(val productId: Long) : ListUiEvent
    data class SelectCategory(val category: String) : ListUiEvent
    data object Reload: ListUiEvent
}