package lk.printxpress.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lk.printxpress.data.model.OrderStatus
import lk.printxpress.ui.theme.StatusCancelled
import lk.printxpress.ui.theme.StatusCompleted
import lk.printxpress.ui.theme.StatusConfirmed
import lk.printxpress.ui.theme.StatusPrinting
import lk.printxpress.ui.theme.StatusProcessing
import lk.printxpress.ui.theme.StatusReady

@Composable
fun StatusBadge(
    status: OrderStatus,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (status) {
        OrderStatus.CONFIRMED -> Pair(StatusConfirmed.copy(alpha = 0.12f), StatusConfirmed)
        OrderStatus.PROCESSING -> Pair(StatusProcessing.copy(alpha = 0.15f), Color(0xFFD97706))
        OrderStatus.PRINTING -> Pair(StatusPrinting.copy(alpha = 0.15f), StatusPrinting)
        OrderStatus.READY_FOR_PICKUP -> Pair(StatusReady.copy(alpha = 0.15f), StatusReady)
        OrderStatus.COMPLETED -> Pair(StatusCompleted.copy(alpha = 0.15f), StatusCompleted)
        OrderStatus.CANCELLED -> Pair(StatusCancelled.copy(alpha = 0.15f), StatusCancelled)
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.displayName,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
