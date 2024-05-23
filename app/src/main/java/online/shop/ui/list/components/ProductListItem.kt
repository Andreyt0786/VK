package online.shop.ui.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import online.shop.ui.list.ProductPreviewUiModel
import online.shop.R


@Composable
fun ItemListProduct(
    item: ProductPreviewUiModel,
    onClickItemEvent: (Long) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 15.dp, top = 15.dp, end = 15.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(percent = 20)
            )
            .fillMaxWidth()
            .height(104.dp)
            .background(Color.White)
            .clickable { onClickItemEvent.invoke(item.id) }

    ) {
        if (item.imgUrl.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                contentDescription = "image painter",
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .size(width = 64.dp, height = 64.dp)
                    .padding(start = 10.dp)
            )
        } else {
            SubcomposeAsyncImage(
                model = item.imgUrl,
                contentDescription = "image",
                loading = { CircularProgressIndicator() },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(width = 64.dp, height = 64.dp)
                    .clip(RoundedCornerShape(percent = 50))
            )
        }

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
            )
            Text(
                text = item.category,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ItemListProductPreview() {
    val item = ProductPreviewUiModel(
        id = 1,
        title = "Test",
        category = "test cat",
        imgUrl = ""
    )
    ItemListProduct(item = item)
}