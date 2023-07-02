@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "org.eu.best.pa"
    compileSdk = 33

    defaultConfig {
        applicationId = "org.eu.best.pa"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            isShrinkResources = false
            multiDexEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            multiDexEnabled = true

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
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.cardview)
    implementation(libs.browser)
    implementation(libs.material)
    implementation(libs.okhttp)
    implementation(libs.json)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.glide)
    implementation(libs.swiperefreshlayout)
    implementation(libs.security.crypto.ktx)
    implementation(libs.customactivityoncrash)
}