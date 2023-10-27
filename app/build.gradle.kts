import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "tony.studenthomework"
    compileSdk = 34

    defaultConfig {
        applicationId = "tony.studenthomework"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "2.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"${properties["BASE_URL"]?.toString() ?: ""}\""
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["nav_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
    testImplementation("junit:junit:4.13.2")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}