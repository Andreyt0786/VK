package online.shop.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductsRs(

    @SerializedName("products")
    val products: List<ProductInfoRs>,

    @SerializedName("total")
    val total: Long,

    @SerializedName("skip")
    val skip: Long,

    @SerializedName("limit")
    val limit: Long
)

data class ProductInfoRs(

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("price")
    val price: Long? = null,

    @SerializedName("discountPercentage")
    val discountPercentage: Double? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("brand")
    val brand: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("thumbnail")
    val imgUrl: String? = null,

    @SerializedName("images")
    val imagesList: List<String>?
)