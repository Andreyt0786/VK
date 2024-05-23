package online.shop.domain.usecase

import online.shop.domain.model.ProductModel
import online.shop.domain.repo.NetworkRepo
import javax.inject.Inject

class SearchByQueryUsecase @Inject constructor() {

    suspend operator fun invoke(
        repo: NetworkRepo,
        selectedCategory: String?,
        query: String,
    ): List<ProductModel> {
        val data = if (query.isEmpty().not())
            repo.getProductsPagingBySearchQuery(query = query.lowercase())
        else
            repo.getAllProductsPaging()

        return if (selectedCategory != null) {
            data.filter { it.category == selectedCategory }
        } else {
            data
        }
    }
}