// App module plugins: Android, Kotlin, KSP (for Room), and Navigation Safe Args.
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // KSP processes Room annotations and generates Database/DAO implementation.
    alias(libs.plugins.ksp)
    // Generates type-safe navigation arguments (e.g. movieId) for NavController.
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.example.inshorts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.inshorts"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Read TMDB API key from local.properties and expose as BuildConfig.TMDB_API_KEY.
        // This keeps the key out of source control; each developer sets their own key locally.
        val localProperties = Properties().apply {
            rootProject.file("local.properties").takeIf { it.exists() }?.reader(Charsets.UTF_8)?.use { load(it) }
        }
        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"${localProperties.getProperty("TMDB_API_KEY", "")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Enable ViewBinding to access views by generated binding classes (no findViewById).
    buildFeatures {
        viewBinding = true
        // Generate BuildConfig so we can read TMDB_API_KEY from local.properties at build time.
        buildConfig = true
    }
}

dependencies {
    // --- Core Android ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // --- MVVM: ViewModel and lifecycle; Fragment KTX for viewModels() delegate ---
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.fragment.ktx)

    // --- Networking: Retrofit + OkHttp + Gson (per project spec) ---
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)

    // --- Local DB: Room for offline-first; KSP generates code from @Dao / @Database ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // --- Navigation: single-activity with fragments and type-safe args ---
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // --- Image loading: Coil for TMDB poster/backdrop URLs ---
    implementation(libs.coil)

    // --- Coroutines: async and Flow for DB and network (per project spec) ---
    implementation(libs.kotlinx.coroutines.android)

    // --- Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}