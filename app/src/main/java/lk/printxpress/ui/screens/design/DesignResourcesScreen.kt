package lk.printxpress.ui.screens.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lk.printxpress.ui.components.PrintXpressBottomBar
import lk.printxpress.ui.components.PrintXpressTopBar
import lk.printxpress.ui.navigation.Screen

@Composable
fun DesignResourcesScreen(
    onNavigate: (String) -> Unit
) {
    Scaffold(
        topBar = {
            PrintXpressTopBar(title = "Print Guidelines & Support")
        },
        bottomBar = {
            PrintXpressBottomBar(
                currentRoute = Screen.DesignResources.route,
                onNavigate = onNavigate
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
            Text(
                text = "Pre-Press & Design Guidelines",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Ensure your artwork is printed with crystal clear resolution and color precision.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 1. File Format Guidelines
            GuidelineCard(
                icon = Icons.Default.PictureAsPdf,
                title = "File Format Guidelines",
                summary = "PDF / X-1a, Vector AI, TIFF, high-res PNG",
                details = "• Preferred format: Press-Ready PDF (PDF/X-1a:2001).\n• Image Resolution: Minimum 300 DPI at 100% output scale.\n• Vector Artwork: Convert all fonts to curves/outlines to avoid missing font issues.\n• Maximum upload file size: 50 MB per artwork file."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Colour Format Information (CMYK vs RGB)
            GuidelineCard(
                icon = Icons.Default.Palette,
                title = "Colour Space: CMYK vs RGB",
                summary = "Always convert design files to CMYK before saving",
                details = "• Commercial digital presses print using CMYK (Cyan, Magenta, Yellow, Black) inks.\n• Computer monitors and phone displays use RGB (Red, Green, Blue).\n• Files submitted in RGB will undergo automatic conversion, which can cause color shifts.\n• For rich deep blacks, use C:30 M:30 Y:30 K:100."
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Bleed and Safe Margins
            GuidelineCard(
                icon = Icons.Default.CropSquare,
                title = "Bleed Margins & Safe Zone",
                summary = "3mm external bleed, 5mm internal safe area",
                details = "• Bleed Area: Extend all background graphics 3mm beyond the final cut line.\n• Safe Zone: Keep critical logos and text at least 5mm inside the trim edge.\n• Trim Line: The exact dimension of your finished printed product."
            )

            Spacer(modifier = Modifier.height(24.dp))

            // FAQs Section
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            FaqItem(
                question = "How long does digital printing take?",
                answer = "Standard digital orders (business cards, flyers, stickers) take 24 to 48 hours. Large format banners and apparel take 2 to 3 business days."
            )
            FaqItem(
                question = "Can I cancel or change an order after placing it?",
                answer = "You can cancel or reschedule an order while its status is Confirmed or Processing. Once printing has commenced on the production press, orders cannot be cancelled."
            )
            FaqItem(
                question = "Do you deliver island-wide across Sri Lanka?",
                answer = "Yes! We deliver across all provinces via registered courier for a flat rate of LKR 450. You can also pick up for free at our Colombo hub."
            )
            FaqItem(
                question = "What if my artwork has low resolution?",
                answer = "Our pre-press team performs a digital file check. If the resolution is below 200 DPI, we will alert you via notifications before printing."
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Customer Support Info (Task C.6)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Customer Support & Print Shop",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Call, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Hotline: +94 11 250 8899 / 077 123 4567", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Email: support@printxpress.lk", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("120 Duplication Road, Kollupitiya, Colombo 03", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Operating Hours: Monday – Saturday, 8:30 AM – 7:00 PM",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun GuidelineCard(
    icon: ImageVector,
    title: String,
    summary: String,
    details: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = summary, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FaqItem(
    question: String,
    answer: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = answer,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
