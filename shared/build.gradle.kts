import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    android {
       namespace = "org.example.pipe2.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_17
       }
       withHostTest {}
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.google.firebase.firestore)
        }
        val androidHostTest by getting {
            kotlin.srcDirs("src/androidTest/kotlin")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.icons.core)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
        }
    }
}

tasks.register("androidTest") {
    group = "verification"
    description = "Runs the Android host tests."
    dependsOn("testAndroidHostTest")
}

tasks.register("iosTest") {
    group = "verification"
    description = "Runs the iOS simulator tests."
    dependsOn("iosSimulatorArm64Test")
}

tasks.register("sharedTest") {
    group = "verification"
    description = "Runs the common tests (via the Android host)."
    dependsOn("testAndroidHostTest")
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}