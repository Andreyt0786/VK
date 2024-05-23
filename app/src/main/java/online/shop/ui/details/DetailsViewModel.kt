package online.shop.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import online.shop.domain.repo.NetworkRepo
import online.shop.util.runSuspendCatching

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val productId: Long,
    private val networkRepo: NetworkRepo
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenUiState>(DetailsScreenUiState.Loading)
    val state: StateFlow<DetailsScreenUiState> = _state

    init {
        init(productId = productId)
    }

    fun obtainEvent(event: DetailsScreenUiEvent) {
        when (event) {
            is DetailsScreenUiEvent.ShowNewImage -> showNewItem(event.itemIndex)
            is DetailsScreenUiEvent.Reload -> init(productId = productId)
        }
    }

    private fun showNewItem(itemIndex: Int) {
        when (val currentState = _state.value) {
            is DetailsScreenUiState.Content -> {
                _state.value = currentState.copy(showImageItem = itemIndex)
            }

            is DetailsScreenUiState.Error -> {}
            is DetailsScreenUiState.Loading -> {}
        }
    }

    private fun init(productId: Long) {
        viewModelScope.launch {
            runSuspendCatching(
                action = { networkRepo.getProductById(productId = productId) },
                onSuccess = { productModel ->
                    _state.value = DetailsScreenUiState.Content(
                        item = productModel,
                        showImageItem = if (productModel.imagesList.isEmpty()) null else 0
                    )
                },
                onError = { _state.value = DetailsScreenUiState.Error }
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(productId: Long): DetailsViewModel
    }
}