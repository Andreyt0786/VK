package online.shop.ui.list

data class ProductPreviewUiModel(
    val id: Long,
    val title: String,
    val category: String,
    val imgUrl: String
)

data class ChipsUiModel(val name: String, val selected: Boolean)
