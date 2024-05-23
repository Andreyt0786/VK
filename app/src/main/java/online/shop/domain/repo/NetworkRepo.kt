package online.shop.domain.repo

import online.shop.domain.model.ProductModel

interface NetworkRepo {

    suspend fun getAllProductsPaging(): List<ProductModel>

    suspend fun getNewPage(skipValue: Long, query: String? = null): List<ProductModel>

    suspend fun getProductById(productId: Long): ProductModel

    suspend fun getProductsPagingBySearchQuery(query: String): List<ProductModel>

    suspend fun getCategories(): List<String>

    suspend fun getProductsByCategory(category: String): List<ProductModel>
}