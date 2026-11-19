# `Contamoto/` — Android app

Kotlin/Compose app, package `com.drox.contamoto`. The real project of the vault; everything else here is coursework or evidence.

**Build** (verified working):
```powershell
cd Contamoto
.\gradlew.bat assembleDebug          # ~2 min
.\gradlew.bat assembleRelease        # ~5 min, runs lintVital
```
Needs **JDK 21** (Gradle 8.11.1). `local.properties` already pins the SDK at `C:\Users\USUARIO\AppData\Local\Android\Sdk`. If a shell has no `JAVA_HOME`, point it at `C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot` first.

**Stack** — Kotlin 2.0.21, AGP 8.7.3, Compose BOM 2024.12.01, Material 3, Nav Compose 2.8.5, kotlinx-serialization 1.7.3, Hilt 2.51.1 + Room 2.6.1 (declared but unused, see below). compileSdk/targetSdk 35, minSdk 26, JVM target 11.

Compose compiler is the Kotlin 2.0 plugin `org.jetbrains.kotlin.plugin.compose` — there is no `composeOptions {}` block.

## Gotchas

- **Annotation processing is wired wrong.** `app/build.gradle.kts` declares the Room and Hilt compilers with `annotationProcessor(...)`, which only processes *Java* sources. All sources are Kotlin (`app/src/main/java/...`, note the `java/` dir holds `.kt` files). Adding `@Entity`/`@Dao`/`@HiltAndroidApp` without first moving those deps to `kapt(...)` (plugin already applied) or KSP will silently generate nothing.
- **Do not trust `gradle/libs.versions.toml`** for Room/Hilt/KSP: it lists room 2.7.1, hilt 2.53.1 and a `ksp` plugin that the build never applies. Real versions are inline in `app/build.gradle.kts`.
- **DI is not initialized.** No `Application` class, no `@HiltAndroidApp`, no `@AndroidEntryPoint`, no `@HiltViewModel`, no `@Entity`/`@Dao` anywhere. Hilt/Room are dead dependencies, not a foundation to build on.
- **No data layer.** `HomeViewModel` / `DashboardViewModel` are plain `ViewModel`s holding hardcoded sample data (`mutableStateOf`, COP amounts) and a `delay(600L)` to fake loading; `AsyncContent` + `SkeletonLoader` render that fake loading state.
- `gradle.properties` sets `org.gradle.configuration-cache=true` — add `--no-configuration-cache` when a build behaves oddly right after editing build scripts.
- Compiling emits deprecation warnings only (`CircularProgressIndicator(progress: Float)`, `Icons.Filled.ArrowBack`, `Icons.Filled.DirectionsBike` → AutoMirrored). Builds are clean; don't treat these as errors.
- If Gradle state is corrupted: `Remove-Item -Recurse -Force Contamoto/.gradle, ~/.gradle/caches/8.11.1`.

## Navigation

Type-safe Kotlin Serialization routes in `ui/navigation/Routes.kt` — every route is `@Serializable`; **never add bare string routes**. Existing: `Home` (start), `Dashboard`, `Ingresos`, `IngresoCategoria(amount: Int)`, `Gastos`, `GastoCategoria(amount: Int)`, `Mantenimiento`, `MantenimientoMonto`, `MantenimientoCategoria(amount: Int)`, `Configuracion`, `Perfil`, `Historial`.

Flow: `MainActivity` → `ContamotoTheme(darkMode, onSetDarkMode)` → `AppNavigation()` → `NavHost(startDestination = Home)` with slide+fade transitions. Width class from `calculateWindowSizeClass` is exposed as `LocalWindowWidthClass` for responsive layouts.

## Theme (`ui/theme/`)

Single source of truth: `Color.kt` (light + dark schemes + `ExtendedColors`: income/expense/warning containers), `Theme.kt` (`ContamotoTheme`, dark scheme, Android 12+ dynamic color, provides `LocalExtendedColors` / `LocalDarkMode` / `LocalSetDarkMode`), `Typography.kt` (`Sora` from the one `res/font/sora.ttf` registered at 3 weights), `Shape.kt` (`AppShapes`), `Spacing.kt` (`Spacing.xs…Spacing.max`), `TopBarTheme.kt`.

Style through `MaterialTheme.colorScheme.*`, `LocalExtendedColors.current.*` and `Spacing.*` — never hardcode colors or dp.

## Layout facts

- 12 screens in `ui/screens/` (one per route) + the 2 ViewModels. `HomeScreen.kt` and `DashboardScreen.kt` are the biggest/most developed.
- `ui/components/`: `AccesoRapidoGrid`, `AppBottomBar`, `AsyncContent`, `CalendarioMensual`, `ContamotoTopBar`, `MetaDiariaCard`, `ResumenCard`, `SkeletonLoader`. `MantenimientoItem` (imported by `HomeScreen`) is defined **inside** `AccesoRapidoGrid.kt`. `ResumenCard` and `AsyncContent` are dead code — nothing calls them.
- `ui/animations/`: `PressScaleBox`, `PressScaleModifier`.
- UI copy and code comments are **Spanish** (Colombian, `$` COP amounts); keep new strings Spanish.
- Tests are the untouched Android stubs (`app/src/test/ExampleUnitTest.kt`, `app/src/androidTest/ExampleInstrumentedTest.kt`) — `gradlew test` proves nothing.

## Lectura eficiente

Estos archivos se releyeron cientos de veces en sesiones anteriores. Usa `grep` para localizar la función y `read` con `offset`/`limit`: `HomeScreen.kt`, `IngresosScreen.kt`, `DashboardScreen.kt`, `MetaDiariaCard.kt`, `AppTopBar.kt`, `AppNavigation.kt`, `AccesoRapidoGrid.kt`, `Color.kt`.
