package lk.printxpress.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lk.printxpress.data.model.OrderStatus
import lk.printxpress.ui.components.CustomTextField
import lk.printxpress.ui.components.PrintXpressTopBar
import lk.printxpress.ui.components.StatusBadge
import lk.printxpress.utils.Formatters
import lk.printxpress.viewmodel.OrderViewModel

@Composable
fun OrderDetailScreen(
    orderId: Long,
    orderViewModel: OrderViewModel,
    onNavigateBack: () -> Unit
) {
    val details by orderViewModel.selectedOrderDetails.collectAsState()
    val actionState by orderViewModel.actionState.collectAsState()

    var showCancelDialog by remember { mutableStateOf(false) }
    var showRescheduleDialog by remember { mutableStateOf(false) }
    var newDateText by remember { mutableStateOf("") }

    LaunchedEffect(orderId) {
        orderViewModel.loadOrderDetails(orderId)
    }

    val orderDetails = details
    if (orderDetails == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading order #$orderId...")
        }
        return
    }

    val order = orderDetails.order
    val status = order.orderStatus

    Scaffold(
        topBar = {
            PrintXpressTopBar(
                title = "Order #${order.orderId}",
                canNavigateBack = true,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            // Status Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Current Status",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = status.displayName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                StatusBadge(status = status)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Visual Progress Stepper (Task C.5)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Production Tracking",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val steps = listOf(
                        OrderStatus.CONFIRMED to "Order Confirmed",
                        OrderStatus.PROCESSING to "Pre-Press & Processing",
                        OrderStatus.PRINTING to "Printing on Press",
                        OrderStatus.READY_FOR_PICKUP to (if (order.deliveryType == "DELIVERY") "Dispatched for Delivery" else "Ready for Pickup"),
                        OrderStatus.COMPLETED to "Completed"
                    )

                    steps.forEachIndexed { index, (stepStatus, stepLabel) ->
                        val isPassed = status.stepIndex >= stepStatus.stepIndex && status != OrderStatus.CANCELLED
                        val isCurrent = status == stepStatus

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Step Circle
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        color = when {
                                            isCurrent -> MaterialTheme.colorScheme.primary
                                            isPassed -> MaterialTheme.colorScheme.secondary
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        },
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isPassed && !isCurrent) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${index + 1}",
                                        color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = stepLabel,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp
                            )
                        }

                        // Connector line
                        if (index < steps.size - 1) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 13.dp)
                                    .width(2.dp)
                                    .height(20.dp)
                                    .background(
                                        if (status.stepIndex > stepStatus.stepIndex && status != OrderStatus.CANCELLED)
                                            MaterialTheme.colorScheme.secondary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                    )
                            )
                        }
                    }

                    if (status == OrderStatus.CANCELLED) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "This order was cancelled.",
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Order Items & Specifications Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ordered Items",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    orderDetails.items.forEach { itemWithProd ->
                        val item = itemWithProd.item
                        val product = itemWithProd.product

                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                text = product?.name ?: "Print Product",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = item.specifications,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Qty: ${item.quantity} units",
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = Formatters.formatCurrency(item.unitPrice * item.quantity),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Artwork File: ${order.artworkFilename}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    if (order.instructions.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Special Instructions: ${order.instructions}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Delivery & Fulfillment Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Fulfillment & Address",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (order.deliveryType == "DELIVERY") "Home Delivery (${Formatters.formatCurrency(order.deliveryFee)})" else "Store Pickup (Free)",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    if (orderDetails.address != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${orderDetails.address.addressLine}, ${orderDetails.address.city} (${orderDetails.address.postalCode})",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Schedule: ${order.deliveryDate}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total Paid", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            text = Formatters.formatCurrency(order.totalAmount),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Task D.7 & E.1: Actions (Cancel, Reschedule, and Demo Advance Status)
            if (status != OrderStatus.CANCELLED && status != OrderStatus.COMPLETED) {
                Text(
                    text = "Order Actions (Task D.7 Rules)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Reschedule Button
                    OutlinedButton(
                        onClick = { showRescheduleDialog = true },
                        enabled = status.canReschedule(),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reschedule")
                    }

                    // Cancel Button
                    Button(
                        onClick = { showCancelDialog = true },
                        enabled = status.canCancel(),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel Order")
                    }
                }

                if (!status.canCancel()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Cancellation disabled: Printing has already started.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Demo Examiner Status Advancement Tool
                OutlinedButton(
                    onClick = { orderViewModel.advanceOrderStatus(order.orderId) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("⚡ Demo: Advance Order to Next Stage", fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Cancel Confirmation Dialog
        if (showCancelDialog) {
            AlertDialog(
                onDismissRequest = { showCancelDialog = false },
                title = { Text("Cancel Order #${order.orderId}?") },
                text = { Text("Are you sure you want to cancel this order? This action cannot be reversed.") },
                confirmButton = {
                    Button(
                        onClick = {
                            orderViewModel.cancelOrder(order.orderId) {
                                showCancelDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Confirm Cancel")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCancelDialog = false }) {
                        Text("Keep Order")
                    }
                }
            )
        }

        // Reschedule Dialog
        if (showRescheduleDialog) {
            AlertDialog(
                onDismissRequest = { showRescheduleDialog = false },
                title = { Text("Reschedule Delivery/Pickup") },
                text = {
                    Column {
                        Text("Enter new requested date:")
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomTextField(
                            value = newDateText,
                            onValueChange = { newDateText = it },
                            label = "New Date",
                            placeholder = "e.g., Oct 20, 2026"
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newDateText.isNotBlank()) {
                                orderViewModel.rescheduleOrder(order.orderId, newDateText) {
                                    showRescheduleDialog = false
                                }
                            }
                        }
                    ) {
                        Text("Save Date")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showRescheduleDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
