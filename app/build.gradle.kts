plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
        compose = false
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
    implementation("com.x3mads.android.xmediator:core:1.109.0")
    implementation("com.x3mads.android.xmediator.mediation:renderer:2.1.1.6.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.2.0.4")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.8.0.3")

    // -- Ad Networks --
    // amazon publisher services
    implementation("com.amazon.android:aps-sdk:11.0.0")
    implementation("com.unity3d.ads-mediation:aps-adapter:4.3.17")
    implementation("com.applovin.mediation:amazon-tam-adapter:11.0.0.0")
    implementation("com.x3mads.android.xmediator.mediation:aps:11.0.0.2")
    implementation("com.x3mads.android.xmediator.mediation:aps:11.0.0.2")
    // applovin
    implementation("com.applovin:applovin-sdk:13.2.0")
    implementation("com.unity3d.ads-mediation:applovin-adapter:4.3.53")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.2.0.4")
    // chartboost
    implementation("com.chartboost:chartboost-sdk:9.8.3")
    implementation("com.unity3d.ads-mediation:chartboost-adapter:4.3.19")
    implementation("com.applovin.mediation:chartboost-adapter:9.8.3.0")
    implementation("com.x3mads.android.xmediator.mediation:chartboost:9.8.3.0")
    // meta audience network
    implementation("com.facebook.android:audience-network-sdk:6.20.0")
    implementation("com.unity3d.ads-mediation:facebook-adapter:4.3.50")
    implementation("com.applovin.mediation:facebook-adapter:6.20.0.0")
    implementation("com.x3mads.android.xmediator.mediation:facebook:6.20.0.0")
    // dt exchange
    implementation("com.fyber:marketplace-sdk:8.3.7")
    implementation("com.unity3d.ads-mediation:fyber-adapter:4.3.38")
    implementation("com.applovin.mediation:fyber-adapter:8.3.7.0")
    implementation("com.x3mads.android.xmediator.mediation:fyber:8.3.7.0")
    // google ads
    implementation("com.google.android.gms:play-services-ads:24.3.0")
    implementation("com.unity3d.ads-mediation:admob-adapter:4.3.51")
    implementation("com.applovin.mediation:google-ad-manager-adapter:24.3.0.1")
    implementation("com.applovin.mediation:google-adapter:24.3.0.1")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:24.3.0.0")
    // hyprmx
    implementation("com.hyprmx.android:HyprMX-SDK:6.4.2")
    implementation("com.unity3d.ads-mediation:hyprmx-adapter:4.3.13")
    implementation("com.applovin.mediation:hyprmx-adapter:6.4.2.3")
    implementation("com.x3mads.android.xmediator.mediation:hyprmx:6.4.2.0")
    // inmobi
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:10.8.3")
    implementation("com.unity3d.ads-mediation:inmobi-adapter:4.3.30")
    implementation("com.applovin.mediation:inmobi-adapter:10.8.3.1")
    implementation("com.x3mads.android.xmediator.mediation:inmobi:10.8.0.0")
    // ironsource
    implementation("com.unity3d.ads-mediation:mediation-sdk:8.8.0")
    implementation("com.applovin.mediation:ironsource-adapter:8.8.0.0.1")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.8.0.3")
    // mintegral
    implementation("com.mbridge.msdk.oversea:mbridge_android_sdk:16.9.71")
    implementation("com.unity3d.ads-mediation:mintegral-adapter:4.3.41")
    implementation("com.applovin.mediation:mintegral-adapter:16.9.71.0")
    implementation("com.x3mads.android.xmediator.mediation:mintegral:16.9.71.0")
    // mobilefuse
    implementation("com.mobilefuse.sdk:mobilefuse-sdk-core:1.9.2")
    implementation("com.unity3d.ads-mediation:mobilefuse-adapter:4.3.7")
    implementation("com.applovin.mediation:mobilefuse-adapter:1.9.2.1")
    implementation("com.x3mads.android.xmediator.mediation:mobile-fuse:1.9.2.0")
    // moloco
    implementation("com.moloco.sdk.adapters:adapter:3.9.0")
    implementation("com.unity3d.ads-mediation:moloco-adapter:4.3.17")
    implementation("com.applovin.mediation:moloco-adapter:3.9.0.1")
    // ogury
    implementation("co.ogury:ogury-sdk:6.0.1")
    implementation("com.unity3d.ads-mediation:ogury-adapter:4.3.2")
    implementation("com.applovin.mediation:ogury-presage-adapter:6.0.1.1")
    implementation("com.x3mads.android.xmediator.mediation:ogury:6.0.1.0")
    // pangle
    implementation("com.pangle.global:ads-sdk:6.5.1.1")
    implementation("com.unity3d.ads-mediation:pangle-adapter:4.3.38")
    implementation("com.applovin.mediation:bytedance-adapter:6.5.0.8.1")
    implementation("com.x3mads.android.xmediator.mediation:pangle:6.5.0.8.0")
    // bidmachine
    implementation("io.bidmachine:ads:3.3.0")
    implementation("com.unity3d.ads-mediation:bidmachine-adapter:4.3.13")
    implementation("com.applovin.mediation:bidmachine-adapter:3.3.0.0")
    implementation("com.x3mads.android.xmediator.mediation:stack:3.3.0.0")
    // tappx
    implementation("com.tappx.sdk.android:tappx-sdk:4.1.11")
    implementation("com.x3mads.android.xmediator.mediation:tappx:4.1.11.0")
    // unity ads
    implementation("com.unity3d.ads:unity-ads:4.15.0")
    implementation("com.unity3d.ads-mediation:unityads-adapter:4.3.55")
    implementation("com.applovin.mediation:unityads-adapter:4.15.0.1")
    implementation("com.x3mads.android.xmediator.mediation:unity:4.15.0.0")
    // liftoff monetize
    implementation("com.vungle:vungle-ads:7.5.0")
    implementation("com.unity3d.ads-mediation:vungle-adapter:4.3.30")
    implementation("com.applovin.mediation:vungle-adapter:7.5.0.2")
    implementation("com.x3mads.android.xmediator.mediation:vungle:7.5.0.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}