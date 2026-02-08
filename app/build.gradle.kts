import com.android.build.api.dsl.ViewBinding

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "iti.student.foodo"
    compileSdk {
        version = release(36)
    }
    defaultConfig {
        applicationId = "iti.student.foodo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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

    viewBinding.enable = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // ViewPager
    implementation(libs.viewpager2)
    implementation(libs.dotsindicator)

    // Lottie
    implementation(libs.lottie)

    // Toast
    implementation(libs.motiontoast)

    // Shimmer

    implementation(libs.shimmer)

    // Retrofit
    implementation(libs.retrofit)

    // RX with retrofit
    implementation(libs.adapter.rxjava3)

    // Gson
    implementation(libs.gson.converter)
    implementation(libs.gson)

    // Glide
    implementation(libs.glide)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services)
    implementation(libs.googleid)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // RX with Room
    implementation(libs.room.rxjava3)

    // RX Java
    implementation(libs.rxjava)
    implementation(libs.rxjava3.rxandroid)

    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}