plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.yofhi.storyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yofhi.storyapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    testOptions{
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("androidx.annotation:annotation:1.7.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.5")


    implementation ("androidx.camera:camera-camera2:1.4.0-alpha02")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha02")
    implementation ("androidx.camera:camera-view:1.4.0-alpha02")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.paging:paging-runtime-ktx:3.3.0-alpha02")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-paging:2.6.0")
    implementation("androidx.test.espresso:espresso-idling-resource:3.6.0-alpha01")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.test.espresso:espresso-intents:3.5.1")

    ksp("androidx.room:room-compiler:2.6.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.11")
    androidTestImplementation("com.squareup.okhttp3:okhttp-tls:5.0.0-alpha.11")
    androidTestImplementation("androidx.test:runner:1.6.0-alpha04")
    androidTestImplementation("androidx.test:rules:1.6.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha01")

    configurations.all {
        resolutionStrategy {
            force ("androidx.test:rules:1.6.0-alpha01")
            force ("androidx.test:runner:1.6.0-alpha04")
            force ("androidx.test.espresso:espresso-core:3.6.0-alpha01")
        }
    }

}