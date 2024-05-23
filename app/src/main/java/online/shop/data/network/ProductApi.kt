package online.shop.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import online.shop.data.network.model.ProductsRs

interface ProductApi {

    @GET("products")
    suspend fun getProductsPaging(
        @Query("skip") skipValue: Long = 0,
        @Query("limit") limitValue: Long = 20
    ): ProductsRs

    @GET("products/search")
    suspend fun getProductsPagingWithQuery(
        @Query("skip") skipValue: Long = 0,
        @Query("limit") limitValue: Long = 20,
        @Query("q") query: String,
    ): ProductsRs

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("skip") skipValue: Long = 0,
        @Query("limit") limitValue: Long = 20,
    ): ProductsRs
}