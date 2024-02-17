buildscript {
    extra.apply {
        set("lifecycle_version", "2.6.2")
        set("nav_version", "2.7.2")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
}