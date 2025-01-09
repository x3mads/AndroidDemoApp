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
    implementation("com.x3mads.android.xmediator:core:1.86.1")
    implementation("com.x3mads.android.xmediator.mediation:renderer:2.1.1.6.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.0.1.2")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.5.0.1")

    // -- Ad Networks --
    // amazon publisher services
    implementation("com.amazon.android:aps-sdk:9.10.3")
    implementation("com.x3mads.android.xmediator.mediation:aps:9.10.3.0")
    // applovin
    implementation("com.applovin:applovin-sdk:13.0.1")
    implementation("com.ironsource.adapters:applovinadapter:4.3.48")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.0.1.2")
    // chartboost
    implementation("com.chartboost:chartboost-sdk:9.8.2")
    implementation("com.ironsource.adapters:chartboostadapter:4.3.18")
    implementation("com.applovin.mediation:chartboost-adapter:9.8.2.0")
    implementation("com.x3mads.android.xmediator.mediation:chartboost:9.8.1.0")
    // meta audience network
    implementation("com.facebook.android:audience-network-sdk:6.18.0")
    implementation("com.ironsource.adapters:facebookadapter:4.3.48")
    implementation("com.applovin.mediation:facebook-adapter:6.18.0.1")
    implementation("com.x3mads.android.xmediator.mediation:facebook:6.18.0.0")
    // dt exchange
    implementation("com.fyber:marketplace-sdk:8.3.4")
    implementation("com.ironsource.adapters:fyberadapter:4.3.35")
    implementation("com.applovin.mediation:fyber-adapter:8.3.4.0")
    implementation("com.x3mads.android.xmediator.mediation:fyber:8.3.3.0")
    // google ads
    implementation("com.google.android.gms:play-services-ads:23.6.0")
    implementation("com.ironsource.adapters:admobadapter:4.3.48")
    implementation("com.applovin.mediation:google-ad-manager-adapter:23.6.0.1")
    implementation("com.applovin.mediation:google-adapter:23.6.0.1")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:23.6.0.0") //implementation(project(":mediation:google-ads")) // Use example adapter instead
    // hyprmx
    implementation("com.hyprmx.android:HyprMX-SDK:6.4.2")
    implementation("com.ironsource.adapters:hyprmxadapter:4.3.10")
    implementation("com.applovin.mediation:hyprmx-adapter:6.4.2.2")
    implementation("com.x3mads.android.xmediator.mediation:hyprmx:6.4.2.0")
    // inmobi
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:10.8.0")
    implementation("com.ironsource.adapters:inmobiadapter:4.3.28")
    implementation("com.applovin.mediation:inmobi-adapter:10.8.0.0")
    implementation("com.x3mads.android.xmediator.mediation:inmobi:10.8.0.0")
    // ironsource
    implementation("com.ironsource.sdk:mediationsdk:8.5.0")
    implementation("com.applovin.mediation:ironsource-adapter:8.5.0.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:8.5.0.1")
    // meta audience network via admob
    implementation("com.facebook.android:audience-network-sdk:6.18.0")
    implementation("com.google.ads.mediation:facebook:6.18.0.0")
    // mintegral
    implementation("com.mbridge.msdk.oversea:videojs:16.8.61")
    implementation("com.ironsource.adapters:mintegraladapter:4.3.33")
    implementation("com.applovin.mediation:mintegral-adapter:16.8.61.1")
    implementation("com.x3mads.android.xmediator.mediation:mintegral:16.8.51.0")
    // mobilefuse
    implementation("com.mobilefuse.sdk:mobilefuse-sdk-core:1.8.2")
    implementation("com.applovin.mediation:mobilefuse-adapter:1.8.2.1")
    implementation("com.x3mads.android.xmediator.mediation:mobile-fuse:1.8.2.0")
    // moloco
    implementation("com.moloco.sdk.adapters:adapter:3.4.0")
    implementation("com.applovin.mediation:moloco-adapter:3.4.0.0")
    // ogury
    implementation("co.ogury:ogury-sdk:5.8.0")
    implementation("com.ironsource.adapters:oguryadapter:4.3.1")
    implementation("com.applovin.mediation:ogury-presage-adapter:5.8.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ogury:5.8.0.0")
    // pangle
    implementation("com.pangle.global:ads-sdk:6.4.0.5")
    implementation("com.ironsource.adapters:pangleadapter:4.3.32")
    implementation("com.applovin.mediation:bytedance-adapter:6.4.0.5.0")
    implementation("com.x3mads.android.xmediator.mediation:pangle:6.4.0.5.0")
    // tappx
    implementation("com.tappx.sdk.android:tappx-sdk:4.0.8")
    implementation("com.x3mads.android.xmediator.mediation:tappx:4.0.6.3")
    // unity ads
    implementation("com.unity3d.ads:unity-ads:4.12.5")
    implementation("com.ironsource.adapters:unityadsadapter:4.3.46")
    implementation("com.applovin.mediation:unityads-adapter:4.12.5.0")
    implementation("com.x3mads.android.xmediator.mediation:unity:4.12.5.0")
// liftoff monetize
    implementation("com.vungle:vungle-ads:7.4.2")
    implementation("com.ironsource.adapters:vungleadapter:4.3.27")
    implementation("com.applovin.mediation:vungle-adapter:7.4.2.1")
    implementation("com.x3mads.android.xmediator.mediation:vungle:7.4.2.0")

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
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}