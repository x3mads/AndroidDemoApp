plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

android {
    namespace = "com.x3mads.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.x3mads.xmediator.demo"
        minSdk = 23
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
     // Note: Use the picker to select your dependencies https://docs.x3mads.com/metamediation/android/3-Add_x3m_sdk/

    // -- Mediation Libraries --
    implementation("com.x3mads.android.xmediator:core:1.104.1")
    implementation("com.x3mads.android.xmediator.mediation:renderer:2.1.1.6.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.2.0.1")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.8.0.0")

    // -- Ad Networks --
    // amazon publisher services
    implementation("com.amazon.android:aps-sdk:10.0.0")
    implementation("com.unity3d.ads-mediation:aps-adapter:4.3.16")
    implementation("com.applovin.mediation:amazon-tam-adapter:10.0.0.1")
    implementation("com.x3mads.android.xmediator.mediation:aps:10.0.0.0")
    implementation("com.x3mads.android.xmediator.mediation:aps:10.0.0.0")
    // applovin
    implementation("com.applovin:applovin-sdk:13.2.0")
    implementation("com.unity3d.ads-mediation:applovin-adapter:4.3.53")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.2.0.1")
    // chartboost
    implementation("com.chartboost:chartboost-sdk:9.8.3")
    implementation("com.unity3d.ads-mediation:chartboost-adapter:4.3.19")
    implementation("com.applovin.mediation:chartboost-adapter:9.8.3.0")
    implementation("com.x3mads.android.xmediator.mediation:chartboost:9.8.3.0")
    // meta audience network
    implementation("com.facebook.android:audience-network-sdk:6.19.0")
    implementation("com.unity3d.ads-mediation:facebook-adapter:4.3.49")
    implementation("com.applovin.mediation:facebook-adapter:6.19.0.2")
    implementation("com.x3mads.android.xmediator.mediation:facebook:6.19.0.0")
    // dt exchange
    implementation("com.fyber:marketplace-sdk:8.3.7")
    implementation("com.unity3d.ads-mediation:fyber-adapter:4.3.38")
    implementation("com.applovin.mediation:fyber-adapter:8.3.7.0")
    implementation("com.x3mads.android.xmediator.mediation:fyber:8.3.7.0")
    // google ads
    implementation("com.google.android.gms:play-services-ads:24.2.0")
    implementation("com.unity3d.ads-mediation:admob-adapter:4.3.50")
    implementation("com.applovin.mediation:google-ad-manager-adapter:24.2.0.0")
    implementation("com.applovin.mediation:google-adapter:24.2.0.1")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:24.2.0.0")
    // hyprmx
    implementation("com.hyprmx.android:HyprMX-SDK:6.4.2")
    implementation("com.unity3d.ads-mediation:hyprmx-adapter:4.3.12")
    implementation("com.applovin.mediation:hyprmx-adapter:6.4.2.3")
    implementation("com.x3mads.android.xmediator.mediation:hyprmx:6.4.2.0")
    // inmobi
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:10.8.2")
    implementation("com.unity3d.ads-mediation:inmobi-adapter:4.3.29")
    implementation("com.applovin.mediation:inmobi-adapter:10.8.2.0")
    implementation("com.x3mads.android.xmediator.mediation:inmobi:10.8.0.0")
    // ironsource
    implementation("com.unity3d.ads-mediation:mediation-sdk:8.8.0")
    implementation("com.applovin.mediation:ironsource-adapter:8.8.0.0.1")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.8.0.0")
    // mintegral
    implementation("com.mbridge.msdk.oversea:mbridge_android_sdk:16.9.61")
    implementation("com.unity3d.ads-mediation:mintegral-adapter:4.3.40")
    implementation("com.applovin.mediation:mintegral-adapter:16.9.71.0")
    implementation("com.x3mads.android.xmediator.mediation:mintegral:16.9.61.0")
    // mobilefuse
    implementation("com.mobilefuse.sdk:mobilefuse-sdk-core:1.9.0")
    implementation("com.unity3d.ads-mediation:mobilefuse-adapter:4.3.5")
    implementation("com.applovin.mediation:mobilefuse-adapter:1.9.0.0")
    implementation("com.x3mads.android.xmediator.mediation:mobile-fuse:1.9.0.0")
    // moloco
    implementation("com.moloco.sdk.adapters:adapter:3.9.0")
    implementation("com.unity3d.ads-mediation:moloco-adapter:4.3.17")
    implementation("com.applovin.mediation:moloco-adapter:3.9.0.0")
    // ogury
    implementation("co.ogury:ogury-sdk:6.0.1")
    implementation("com.unity3d.ads-mediation:ogury-adapter:4.3.2")
    implementation("com.applovin.mediation:ogury-presage-adapter:6.0.1.1")
    implementation("com.x3mads.android.xmediator.mediation:ogury:6.0.1.0")
    // pangle
    implementation("com.pangle.global:ads-sdk:6.5.0.9")
    implementation("com.unity3d.ads-mediation:pangle-adapter:4.3.38")
    implementation("com.applovin.mediation:bytedance-adapter:6.5.0.8.1")
    implementation("com.x3mads.android.xmediator.mediation:pangle:6.5.0.8.0")
    // bidmachine
    implementation("io.bidmachine:ads:3.2.1")
    implementation("com.unity3d.ads-mediation:bidmachine-adapter:4.3.12")
    implementation("com.applovin.mediation:bidmachine-adapter:3.2.1.0")
    implementation("com.x3mads.android.xmediator.mediation:stack:3.2.1.0")
    // tappx
    implementation("com.tappx.sdk.android:tappx-sdk:4.1.11")
    implementation("com.x3mads.android.xmediator.mediation:tappx:4.1.11.0")
    // unity ads
    implementation("com.unity3d.ads:unity-ads:4.14.2")
    implementation("com.unity3d.ads-mediation:unityads-adapter:4.3.54")
    implementation("com.applovin.mediation:unityads-adapter:4.14.2.0")
    implementation("com.x3mads.android.xmediator.mediation:unity:4.14.2.0")
    // liftoff monetize
    implementation("com.vungle:vungle-ads:7.4.3")
    implementation("com.unity3d.ads-mediation:vungle-adapter:4.3.29")
    implementation("com.applovin.mediation:vungle-adapter:7.4.3.2")
    implementation("com.x3mads.android.xmediator.mediation:vungle:7.4.3.0")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}