package online.shop.ui.list

import online.shop.domain.model.ProductModel
import javax.inject.Inject

class ListUiMapper @Inject constructor() {
    operator fun invoke(data: List<ProductModel>): List<ProductPreviewUiModel> =
        data.map {
            ProductPreviewUiModel(
                id = it.id,
                title = it.title,
                category = it.category,
                imgUrl = it.imgUrl
            )
        }
}