package online.shop.ui.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import online.shop.R


@Composable
fun ImageViewer(imagesUrlList: List<String>, showItemIndex: Int?, onClickItem: (Int) -> Unit) {

    if (showItemIndex != null) {
        SubcomposeAsyncImage(
            model = imagesUrlList[showItemIndex],
            contentDescription = "show image",
            error = { EmptyImage() },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 15.dp, top = 10.dp)
        )
    } else {
        EmptyImage(
            modifier = Modifier.size(width = 300.dp, height = 300.dp)
        )
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(items = imagesUrlList) {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = "image",
                loading = { CircularProgressIndicator() },
                error = {
                    EmptyImage(
                        modifier = Modifier.size(width = 64.dp, height = 64.dp)
                    )
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(width = 84.dp, height = 84.dp)
                    .clip(RoundedCornerShape(20))
                    .clickable { onClickItem.invoke(imagesUrlList.indexOf(it)) }
            )
        }
    }
}

@Composable
private fun EmptyImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.baseline_camera_alt_24),
        contentDescription = "image painter",
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun ImageViewerPreview() {
    ImageViewer(
        imagesUrlList = listOf("https://cdn.dummyjson.com/product-images/71/1.jpg"),
        showItemIndex = 0,
        onClickItem = {})
}