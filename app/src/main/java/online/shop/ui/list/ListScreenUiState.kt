package online.shop.ui.list

sealed interface ListScreenUiState {
    val loadingNewPage: Boolean
        get() = false

    data object Loading : ListScreenUiState
    data class Content(
        val items: List<ProductPreviewUiModel>,
        val categoriesChips: List<ChipsUiModel>,
        val query: String = "",
        val selectedCategory: String? = null,
        override val loadingNewPage: Boolean = false,
    ) : ListScreenUiState

    data object Error : ListScreenUiState
}