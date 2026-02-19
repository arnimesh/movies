// Top-level build file: declare plugins that subprojects (e.g. app) may apply.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // KSP: used by app module for Room (generates DAO/Database impl at compile time).
    alias(libs.plugins.ksp) apply false
    // Navigation Safe Args: generates type-safe nav direction and argument classes.
    alias(libs.plugins.navigation.safe.args) apply false
}