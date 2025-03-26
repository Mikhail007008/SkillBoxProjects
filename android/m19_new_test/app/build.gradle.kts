plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"


}

android {
    namespace = "com.example.compose"
    compileSdk = 35

    testOptions {
        animationsDisabled = true
    }

    defaultConfig {
        applicationId = "com.example.compose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    tasks.withType<AbstractTestTask> {
        // Disable unit tests for release build type (Robolectric limitations)
        if (name == "testReleaseUnitTest") {
            enabled = false
        }
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE",
                "META-INF/NOTICE",
                "META-INF/LICENSE-notice.md"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    val composeBom = platform("androidx.compose:compose-bom:2025.03.00")
    implementation(composeBom)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation(libs.moshi)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.coil.compose)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("io.mockk:mockk:1.13.5")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.paging:paging-testing:3.3.6")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.1.0")
    testImplementation("androidx.paging:paging-common:3.3.6")
    implementation ("androidx.compose.ui:ui:1.4.0")
    androidTestImplementation ("org.mockito:mockito-android:4.5.1") // или актуальная версия
    testImplementation ("org.mockito:mockito-core:4.5.1") // или актуальная версия
    androidTestImplementation ("io.mockk:mockk-android:1.13.4") // или актуальная версия
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-params:5.10.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.7.8")
    testImplementation ("org.mockito:mockito-core:5.10.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.0.0")
    androidTestImplementation ("androidx.test:runner:1.6.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:rules:1.6.1")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.7.8")

}
