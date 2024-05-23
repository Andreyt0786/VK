package online.shop.ui.details.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProductDetail(title: String, content: String, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Gray)) {
                append("$title: ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append(content)
            }
        },
        modifier = Modifier
    )
}

@Composable
@Preview(showBackground = true)
private fun ComponentPreview() {
    ProductDetail(title = "Жанр", content = "комедия")
}