plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.x3mads.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.etermax.xmediator.test_app"
        minSdk = 21
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("com.etermax.android.xmediator:core:1.43.0")
    implementation ("com.etermax.android.xmediator.mediation:applovin:12.1.0.1")

    implementation("com.applovin:applovin-sdk:12.1.0")
    implementation("com.applovin.mediation:amazon-tam-adapter:9.9.2.0")
    implementation("com.amazon.android:aps-sdk:9.9.2")
    implementation("com.applovin.mediation:bidmachine-adapter:2.4.2.0")
    implementation("com.applovin.mediation:chartboost-adapter:9.6.1.0")
    implementation("com.applovin.mediation:fyber-adapter:8.2.5.0")
    implementation("com.applovin.mediation:google-adapter:22.6.0.1")
    implementation("com.applovin.mediation:inmobi-adapter:10.6.6.0")
    implementation("com.applovin.mediation:ironsource-adapter:7.7.0.0.0")
    implementation("com.applovin.mediation:vungle-adapter:7.1.0.0")
    implementation("com.applovin.mediation:mintegral-adapter:16.6.11.0")
    implementation("com.applovin.mediation:bytedance-adapter:5.6.0.3.0")
    implementation("com.applovin.mediation:unityads-adapter:4.9.2.0")
    implementation("com.google.android.gms:play-services-base:16.1.0")

    // X3M Updates
    implementation("com.etermax.android.xmediator:core:1.43.0")
    implementation("com.etermax.android.xmediator.mediation:applovin:12.1.0.1")
    implementation("com.etermax.android.xmediator.mediation:aps:9.9.2.0")
    implementation("com.etermax.android.xmediator.mediation:stack:2.4.2.0")
    implementation("com.etermax.android.xmediator.mediation:chartboost:9.6.1.0")
    implementation("com.etermax.android.xmediator.mediation:fyber:8.2.5.1")
    implementation("com.etermax.android.xmediator.mediation:google-ads:22.6.0.0")
    implementation("com.etermax.android.xmediator.mediation:hyprmx:6.2.0.2")
    implementation("com.etermax.android.xmediator.mediation:inmobi:10.6.6.0")
    implementation("com.etermax.android.xmediator.mediation:ironsource:7.7.0.1")
    implementation("com.etermax.android.xmediator.mediation:vungle:7.1.0.1")
    implementation("com.etermax.android.xmediator.mediation:mintegral:16.6.11.0")
    implementation("com.etermax.android.xmediator.mediation:pangle:5.6.0.3.1")
    implementation("com.etermax.android.xmediator.mediation:unity:4.9.1.3")
    implementation("com.google.ads.mediation:facebook:6.16.0.0")
    implementation("com.etermax.android.xmediator.mediation:criteo:5.0.3.0")
    implementation("com.etermax.android.xmediator.mediation:renderer:2.1.1.6.0")
    implementation("com.etermax.android.xmediator.mediation:helium:4.7.1.0")
    implementation("com.chartboost:chartboost-mediation-adapter-google-bidding:4.22.3.0.5")
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:4.9.6.1.1")


    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}