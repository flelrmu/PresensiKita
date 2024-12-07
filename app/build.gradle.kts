plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.presensikita"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.presensikita"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true // Jika Anda juga menggunakan XML di beberapa bagian.
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Jetpack Compose BOM untuk memastikan versi kompatibel
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))

    // Jetpack Compose Core Dependencies
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Jetpack Compose ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha10")

    // Material Design 3 Components
    implementation("androidx.compose.material3:material3:1.1.1")

    // ConstraintLayout untuk XML
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Untuk mendukung ConstraintLayout di XML.

    // AppCompat untuk mendukung srcCompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.0")

    // Lifecycle runtime for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Core AndroidX Libraries
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(libs.material)
    implementation(libs.androidx.activity)

    // Debugging tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Unit Testing Dependencies
    testImplementation("junit:junit:4.13.2")

    // Android Instrumentation Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // UI Testing for Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.11.0") // Versi terbaru
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // Logging

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.compose.material:material-icons-extended:1.5.0")
}
