package com.drox.contamoto.ui.theme

import androidx.compose.ui.graphics.Color

// ── Light scheme ──────────────────────────────────────────
val LightPrimary = Color(0xFF3949AB)
val LightOnPrimary = Color.White
val LightPrimaryContainer = Color(0xFFDEE0FF)
val LightOnPrimaryContainer = Color(0xFF001064)

val LightSecondary = Color(0xFF6F76B7)
val LightOnSecondary = Color.White
val LightSecondaryContainer = Color(0xFFE2E0FF)
val LightOnSecondaryContainer = Color(0xFF282D6F)

val LightTertiary = Color(0xFFE65C00)
val LightOnTertiary = Color.White
val LightTertiaryContainer = Color(0xFFFFDCCB)
val LightOnTertiaryContainer = Color(0xFF2C1500)

val LightError = Color(0xFFC62828)
val LightOnError = Color.White
val LightErrorContainer = Color(0xFFFFDAD6)
val LightOnErrorContainer = Color(0xFF410002)

val LightBackground = Color(0xFFF5F2FB)
val LightOnBackground = Color(0xFF1A1C20)
val LightSurface = Color(0xFFFFFBFE)
val LightOnSurface = Color(0xFF1A1C20)
val LightSurfaceVariant = Color(0xFFE3E1EC)
val LightOnSurfaceVariant = Color(0xFF45464F)
val LightOutline = Color(0xFF767680)
val LightOutlineVariant = Color(0xFFC6C5D0)

// ── Dark scheme ────────────────────────────────────────────
val DarkPrimary = Color(0xFFBBC3FF)
val DarkOnPrimary = Color(0xFF001A6B)
val DarkPrimaryContainer = Color(0xFF1E3093)
val DarkOnPrimaryContainer = Color(0xFFDEE0FF)

val DarkSecondary = Color(0xFFC4C3FF)
val DarkOnSecondary = Color(0xFF2D3372)
val DarkSecondaryContainer = Color(0xFF444B9A)
val DarkOnSecondaryContainer = Color(0xFFE2E0FF)

val DarkTertiary = Color(0xFFFFB86B)
val DarkOnTertiary = Color(0xFF4A2800)
val DarkTertiaryContainer = Color(0xFF663700)
val DarkOnTertiaryContainer = Color(0xFFFFDCCB)

val DarkError = Color(0xFFFFB4AB)
val DarkOnError = Color(0xFF690005)
val DarkErrorContainer = Color(0xFF93000A)
val DarkOnErrorContainer = Color(0xFFFFDAD6)

val DarkBackground = Color(0xFF121212)
val DarkOnBackground = Color(0xFFE3E2E6)
val DarkSurface = Color(0xFF1A1B1F)
val DarkOnSurface = Color(0xFFE3E2E6)
val DarkSurfaceVariant = Color(0xFF45464F)
val DarkOnSurfaceVariant = Color(0xFFC6C5D0)
val DarkOutline = Color(0xFF90909A)
val DarkOutlineVariant = Color(0xFF45464F)

// ── Semantic extended colors ───────────────────────────────
data class ExtendedColors(
    val income: Color,
    val onIncome: Color,
    val incomeContainer: Color,
    val onIncomeContainer: Color,
    val expense: Color,
    val onExpense: Color,
    val expenseContainer: Color,
    val onExpenseContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val amarillo: Color,
)

val LightExtendedColors = ExtendedColors(
    income = Color(0xFF2E7D32),
    onIncome = Color.White,
    incomeContainer = Color(0xFFA5D6A7),
    onIncomeContainer = Color(0xFF002106),
    expense = Color(0xFFC62828),
    onExpense = Color.White,
    expenseContainer = Color(0xFFFFCDD2),
    onExpenseContainer = Color(0xFF410002),
    warning = Color(0xFFE65C00),
    onWarning = Color.White,
    warningContainer = Color(0xFFFFF3E0),
    onWarningContainer = Color(0xFF2C1500),
    amarillo = Color(0xFFF9A825),
)

val DarkExtendedColors = ExtendedColors(
    income = Color(0xFF81C784),
    onIncome = Color(0xFF003910),
    incomeContainer = Color(0xFF1B5E20),
    onIncomeContainer = Color(0xFFA5D6A7),
    expense = Color(0xFFEF9A9A),
    onExpense = Color(0xFF690005),
    expenseContainer = Color(0xFF93000A),
    onExpenseContainer = Color(0xFFFFCDD2),
    warning = Color(0xFFFFB74D),
    onWarning = Color(0xFF462A00),
    warningContainer = Color(0xFF723500),
    onWarningContainer = Color(0xFFFFF3E0),
    amarillo = Color(0xFFF9A825),
)
