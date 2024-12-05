import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    //noinspection GradleDependency
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    alias(libs.plugins.google.gms.google.services)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {

    namespace = "io.mindset.jagamental"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.mindset.jagamental"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 12
        versionName = "1.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${localProperties["GEMINI_API_KEY"]}\""
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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

    // Markdown
    implementation(libs.compose.markdown)

    // Gemini
    implementation(libs.generativeai)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Shimmer
    implementation(libs.compose.shimmer)

    // Camera X
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.accompanist.permissions)

    // Lottie
    implementation(libs.lottie.compose)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //UI
    implementation(libs.accompanist.systemuicontroller)

    //Chart
    implementation(libs.compose.charts)

    // Firebase & Gms
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.koin.androidx.compose)

    // Room + Paging
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.runtime.livedata)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)

    // OkHttp
    implementation(libs.okhttp)

    // OkHttp logging interceptor
    implementation(libs.logging.interceptor)

    // Converter Gson
    implementation(libs.converter.gson)

    // Datastore
//    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    implementation(libs.androidx.ui.text.google.fonts)
}