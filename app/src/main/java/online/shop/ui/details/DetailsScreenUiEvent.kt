package online.shop.ui.details

sealed interface DetailsScreenUiEvent {
    data class ShowNewImage(val itemIndex: Int) : DetailsScreenUiEvent
    data object Reload: DetailsScreenUiEvent
}