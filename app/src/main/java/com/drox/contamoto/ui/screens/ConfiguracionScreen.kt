package com.drox.contamoto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.components.AppBottomBar
import com.drox.contamoto.ui.navigation.Perfil
import com.drox.contamoto.ui.theme.LocalDarkMode
import com.drox.contamoto.ui.theme.LocalSetDarkMode
import com.drox.contamoto.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(navController: NavController) {
    val isDarkMode = LocalDarkMode.current
    val setDarkMode = LocalSetDarkMode.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                title = { Text("Configuración", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg)
                .padding(top = Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            SectionHeader("Perfil y Cuenta")
            SettingsCard {
                SettingsOption(
                    icon = Icons.Default.Person,
                    title = "Editar perfil",
                    onClick = { navController.navigate(Perfil) }
                )
                Divider()
                SettingsOption(
                    icon = Icons.Default.DirectionsBike,
                    title = "Documentos del vehículo",
                    onClick = { }
                )
            }

            SectionHeader("Preferencias")
            SettingsCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconBox(icon = Icons.Default.DarkMode, contentDescription = "Modo oscuro", tint = MaterialTheme.colorScheme.primary, bg = MaterialTheme.colorScheme.primaryContainer)
                    Spacer(Modifier.width(Spacing.lg))
                    Text("Modo oscuro", modifier = Modifier.weight(1f), fontSize = 16.sp)
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { setDarkMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            checkedThumbColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
                Divider()
                SettingsOption(
                    icon = Icons.Default.Notifications,
                    title = "Notificaciones",
                    onClick = { }
                )
            }

            Spacer(Modifier.height(Spacing.sm))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Versión 2.4.1 (Build 82)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("© 2024 Mototaxi Express S.A.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(Spacing.lg))
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(horizontal = Spacing.sm)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        content = content
    )
}

@Composable
private fun SettingsOption(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon = icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, bg = MaterialTheme.colorScheme.primaryContainer)
        Text(title, modifier = Modifier.weight(1f).padding(start = Spacing.lg), fontSize = 16.sp)
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun IconBox(icon: ImageVector, contentDescription: String, tint: Color, bg: Color) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = contentDescription, tint = tint, modifier = Modifier.size(22.dp))
    }
}

@Composable
private fun Divider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = Spacing.lg),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}
