plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

val composeVersion = "1.2.1"

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    namespace = "io.lostimagin4tion.recipeforyou"

    defaultConfig {
        applicationId = namespace!!
        minSdk = 28
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        project.base.archivesName.set("RecipeForYou-$versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("./keystores/debug.keystore")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }

//        create("release") {
//            val properties = Properties().also {
//                it.load(file("./signing.properties").inputStream())
//            }
//            storeFile = file("./keystores/release.keystore")
//            storePassword = properties.getProperty("password")
//            keyAlias = properties.getProperty("alias")
//            keyPassword = properties.getProperty("password")
//        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

//        getByName("release") {
//            isMinifyEnabled = false
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("release")
//        }
    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    // Enforce correct kotlin version for all dependencies
    implementation(enforcedPlatform(kotlin("bom")))

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")

    implementation(kotlin("reflect"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")

    // ============================================================================================
    // CAREFUL WHEN UPDATING COMPOSE RELATED DEPENDENCIES - THEY CAN USE DIFFERENT COMPOSE VERSION!
    // ============================================================================================

    // Main Compose dependencies
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    // Material You
    implementation("androidx.compose.material3:material3:1.0.0-beta02")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    // compose activity
    implementation("androidx.activity:activity-compose:1.5.1")
    // view model support
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // compose constraint layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Accompanist
    val accompanistVersion = "0.25.1"
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:1.3.2")

    // Navigation Component (with Compose)
    implementation("androidx.navigation:navigation-compose:2.5.2")

    // Paging (with Compose)
    implementation("androidx.paging:paging-compose:1.0.0-alpha16")

    // Coroutines
    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    // Moshi
    val moshiVersion = "1.13.0"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    // Retrofit 2
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // OkHttp
    val okHttpVersion = "4.9.3"
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // Dagger 2
    val daggerVersion = "2.42"
    implementation("com.google.dagger:dagger-android:$daggerVersion")
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Markdown support (Markwon)
    val markwonVersion = "4.6.2"
    implementation("io.noties.markwon:core:$markwonVersion")
    implementation("io.noties.markwon:image-coil:$markwonVersion")

    // Compose material dialogs (color picker)
    implementation("io.github.vanpra.compose-material-dialogs:color:0.8.1-rc")

    /**
     * Test frameworks & dependencies
     */
    allTestsImplementation(kotlin("test-junit"))

    // Robolectric (run android tests on local host)
    testRuntimeOnly("org.robolectric:robolectric:4.8.1")

    allTestsImplementation("androidx.test:core-ktx:1.4.0")
    allTestsImplementation("androidx.test:runner:1.4.0")
    allTestsImplementation("androidx.test.ext:junit-ktx:1.1.3")

    // since we need to connect to test db instance
    val postgresDriverVersion = "42.3.6"
    testRuntimeOnly("org.postgresql:postgresql:$postgresDriverVersion")
    androidTestRuntimeOnly("org.postgresql:postgresql:$postgresDriverVersion")

    // manual json parsing when filling test instance
    implementation("com.google.code.gson:gson:2.9.0")

    // MockK
    testImplementation("io.mockk:mockk:1.12.4")
}

fun DependencyHandler.allTestsImplementation(dependencyNotation: Any) {
    testImplementation(dependencyNotation)
    androidTestImplementation(dependencyNotation)
}

tasks.register<Exec>("launchTestInstance") {
    commandLine("../taiga-test-instance/launch-taiga.sh")
}

tasks.register<Exec>("stopTestInstance") {
    commandLine("../taiga-test-instance/stop-taiga.sh")
}

tasks.withType<Test> {
    dependsOn("launchTestInstance")
    finalizedBy("stopTestInstance")
}
