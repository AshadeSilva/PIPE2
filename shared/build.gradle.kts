import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
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

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(project.dependencies.platform(libs.firebase.bom))
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
//            implementation(libs.androidx.room3.runtime) // was swapped to api version
            api(libs.androidx.room3.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.androidx.room3.sqlite.wrapper) // optional sqlite wrapper
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
    add("kspAndroid", libs.androidx.room3.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room3.compiler)
    add("kspIosArm64", libs.androidx.room3.compiler)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}