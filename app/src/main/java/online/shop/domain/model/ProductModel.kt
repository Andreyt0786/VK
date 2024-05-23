package online.shop.domain.model

data class ProductModel(
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double,
    val rating: Double,
    val brand: String,
    val category: String,
    val imgUrl: String,
    val imagesList: List<String>
)