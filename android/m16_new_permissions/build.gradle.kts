import java.util.*

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

val mapkitApiKey = localProperties.getProperty("MAPKIT_API_KEY") ?: ""

extra["mapkitApiKey"] = mapkitApiKey

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}