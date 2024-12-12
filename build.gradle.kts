// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.4" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}

configurations.all {
    resolutionStrategy {
        // Forzar a usar la versión 23.0.0 de org.jetbrains:annotations
        force("org.jetbrains:annotations:23.0.0")
        // Excluir la dependencia duplicada de com.intellij:annotations
        exclude(group = "com.intellij", module = "annotations")
    }
}