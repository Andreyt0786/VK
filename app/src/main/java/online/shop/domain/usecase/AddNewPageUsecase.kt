package online.shop.domain.usecase

import online.shop.domain.model.ProductModel
import online.shop.domain.repo.NetworkRepo
import online.shop.ui.list.ProductPreviewUiModel
import javax.inject.Inject

class AddNewPageUsecase @Inject constructor() {

    suspend operator fun invoke(
        repo: NetworkRepo,
        items: List<ProductPreviewUiModel>,
        query: String,
        selectedCategory: String?
    ): List<ProductModel> {
        val data = if (query.isEmpty()) {
            repo.getNewPage(skipValue = items.size.toLong())
        } else {
            repo.getNewPage(
                skipValue = items.size.toLong(),
                query = query
            )
        }
        if (selectedCategory.isNullOrEmpty().not()) {
            data.filter { it.category == selectedCategory }
        }

        return data
    }
}