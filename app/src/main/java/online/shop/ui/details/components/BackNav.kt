package online.shop.ui.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.shop.R

@Composable
fun BackNav(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
        contentDescription = "back arrow search",
        tint = Color.Blue,
        modifier = modifier
            .size(32.dp)
            .clickable { onNavigateBack.invoke() }
    )
}

@Composable
@Preview(showBackground = true)
private fun ComponentPreview() {
    BackNav(onNavigateBack = {})
}