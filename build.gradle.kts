buildscript {
    extra.apply {
        set("lifecycle_version", "2.6.2")
        set("nav_version", "2.7.2")
        set("hilt_version", "2.47")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
}