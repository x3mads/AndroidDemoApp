import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

android {
    namespace = "com.x3mads.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.x3mads.xmediator.demo"
        minSdk = 24
        targetSdk = 36
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
    // Note: Use the picker to select your dependencies https://docs.loomit.x3mads.com/docs/sdk/android/?mediators=X3M%2CMAX%2CGoogleAds%2CLevelPlay&networks=aps%2Capplovin%2Cbigoads%2Cchartboost%2Cfacebook%2Cfyber%2Cgoogleads%2Chyprmx%2Cinmobi%2Cironsource%2Cmintegral%2Cmobilefuse%2Cmoloco%2Cogury%2Cpangle%2Cpubmatic%2Cstack%2Ctappx%2Cunityads%2Cverve%2Cvungle%2Cyandex
    // -- Mediation Libraries --
    implementation("com.x3mads.android.xmediator:core:1.172.1")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.6.4.1")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:25.3.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:9.6.0.2")

    // -- Ad Networks --
    // amazon publisher services
    implementation("com.amazon.android:aps-sdk:12.0.2")
    implementation("com.x3mads.android.xmediator.mediation:aps:12.0.2.0")
    implementation("com.applovin.mediation:amazon-tam-adapter:12.0.2.0")
    implementation("com.x3mads.android.xmediator.mediation:aps:12.0.2.0")
    // applovin
    implementation("com.applovin:applovin-sdk:13.6.4")
    implementation("com.google.ads.mediation:applovin:13.6.4.2")
    implementation("com.unity3d.ads-mediation:applovin-adapter:5.9.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.6.4.1")
    // bigo ads
    implementation("com.bigossp:bigo-ads:6.1.0")
    implementation("com.unity3d.ads-mediation:bigo-adapter:5.13.0")
    implementation("com.applovin.mediation:bigoads-adapter:6.1.0.0")
    // chartboost
    implementation("com.chartboost:chartboost-sdk:9.14.1")
    implementation("com.google.ads.mediation:chartboost:9.14.1.0")
    implementation("com.unity3d.ads-mediation:chartboost-adapter:5.10.0")
    implementation("com.applovin.mediation:chartboost-adapter:9.14.1.1")
    implementation("com.x3mads.android.xmediator.mediation:chartboost:9.14.1.0")
    // meta audience network
    implementation("com.facebook.android:audience-network-sdk:6.22.0")
    implementation("com.google.ads.mediation:facebook:6.22.0.1")
    implementation("com.unity3d.ads-mediation:facebook-adapter:5.4.0")
    implementation("com.applovin.mediation:facebook-adapter:6.22.0.0")
    implementation("com.x3mads.android.xmediator.mediation:facebook:6.22.0.0")
    // dt exchange
    implementation("com.fyber:marketplace-sdk:8.4.7")
    implementation("com.google.ads.mediation:fyber:8.4.7.1")
    implementation("com.unity3d.ads-mediation:fyber-adapter:5.10.0")
    implementation("com.applovin.mediation:fyber-adapter:8.4.7.0")
    implementation("com.x3mads.android.xmediator.mediation:fyber:8.4.7.0")
    // google ads
    implementation("com.google.android.gms:play-services-ads:25.3.0")
    implementation("com.unity3d.ads-mediation:admob-adapter:5.9.0")
    implementation("com.applovin.mediation:google-ad-manager-adapter:25.3.0.0")
    implementation("com.applovin.mediation:google-adapter:25.3.0.0")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:25.3.0.0")
    // hyprmx
    implementation("com.hyprmx.android:HyprMX-SDK:6.4.6")
    implementation("com.hyprmx.android:HyprMX-AdMob:6.4.6.0")
    implementation("com.unity3d.ads-mediation:hyprmx-adapter:5.3.0")
    implementation("com.hyprmx.android:HyprMX-Max:6.4.6.0")
    implementation("com.x3mads.android.xmediator.mediation:hyprmx:6.4.6.0")
    // inmobi
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:11.4.1")
    implementation("com.google.ads.mediation:inmobi:11.4.1.2")
    implementation("com.unity3d.ads-mediation:inmobi-adapter:5.9.0")
    implementation("com.applovin.mediation:inmobi-adapter:11.4.1.3")
    implementation("com.x3mads.android.xmediator.mediation:inmobi:11.4.1.1")
    // ironsource
    implementation("com.unity3d.ads-mediation:mediation-sdk:9.6.0")
    implementation("com.google.ads.mediation:ironsource:9.6.0.1")
    implementation("com.applovin.mediation:ironsource-adapter:9.6.0.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:9.6.0.2")
    // mintegral
    implementation("com.mbridge.msdk.oversea:mbridge_android_sdk:17.1.81")
    implementation("com.google.ads.mediation:mintegral:17.1.81.1")
    implementation("com.unity3d.ads-mediation:mintegral-adapter:5.19.0")
    implementation("com.applovin.mediation:mintegral-adapter:17.1.81.0")
    implementation("com.x3mads.android.xmediator.mediation:mintegral:17.1.81.2")
    // mobilefuse
    implementation("com.mobilefuse.sdk:mobilefuse-sdk-core:1.12.0")
    implementation("com.mobilefuse.sdk:mobilefuse-adapter-admob:1.12.0.0")
    implementation("com.unity3d.ads-mediation:mobilefuse-adapter:5.4.0")
    implementation("com.applovin.mediation:mobilefuse-adapter:1.12.0.0")
    implementation("com.x3mads.android.xmediator.mediation:mobile-fuse:1.12.0.0")
    // moloco
    implementation("com.moloco.sdk.adapters:adapter:4.11.1")
    implementation("com.google.ads.mediation:moloco:4.11.1.0")
    implementation("com.unity3d.ads-mediation:moloco-adapter:5.16.0")
    implementation("com.applovin.mediation:moloco-adapter:4.11.1.0")
    implementation("com.x3mads.android.xmediator.mediation:moloco:4.11.1.0")
    // ogury
    implementation("co.ogury:ogury-sdk:6.3.1")
    implementation("com.unity3d.ads-mediation:ogury-adapter:5.5.0")
    implementation("com.applovin.mediation:ogury-presage-adapter:6.3.1.0")
    implementation("com.x3mads.android.xmediator.mediation:ogury:6.3.1.0")
    // pangle
    implementation("com.pangle.global:pag-sdk:8.3.0.4")
    implementation("com.google.ads.mediation:pangle:8.3.0.4.0")
    implementation("com.unity3d.ads-mediation:pangle-adapter:5.24.0")
    implementation("com.applovin.mediation:bytedance-adapter:8.3.0.4.1")
    implementation("com.x3mads.android.xmediator.mediation:pangle:8.3.0.4.1")
    // pubmatic
    implementation("com.pubmatic.sdk:openwrap:5.4.0")
    implementation("com.google.ads.mediation:pubmatic:5.4.0.0")
    implementation("com.applovin.mediation:pubmatic-adapter:5.4.0.0")
    // bidmachine
    implementation("io.bidmachine:ads:3.8.0")
    implementation("com.unity3d.ads-mediation:bidmachine-adapter:5.8.0")
    implementation("com.applovin.mediation:bidmachine-adapter:3.8.0.0")
    implementation("com.x3mads.android.xmediator.mediation:stack:3.8.0.1")
    // tappx
    implementation("com.tappx.sdk.android:tappx-sdk:4.1.20")
    implementation("com.x3mads.android.xmediator.mediation:tappx:4.1.20.1")
    // unity ads
    implementation("com.unity3d.ads:unity-ads:4.20.1")
    implementation("com.google.ads.mediation:unity:4.20.1.0")
    implementation("com.unity3d.ads-mediation:unityads-adapter:5.13.0")
    implementation("com.applovin.mediation:unityads-adapter:4.20.1.0")
    implementation("com.x3mads.android.xmediator.mediation:unity:4.20.1.0")
    // verve
    implementation("net.pubnative:hybid.sdk:3.7.1")
    implementation("com.unity3d.ads-mediation:verve-adapter:5.2.0")
    implementation("com.applovin.mediation:verve-adapter:3.7.1.0")
    // liftoff monetize
    implementation("com.vungle:vungle-ads:7.7.8")
    implementation("com.google.ads.mediation:vungle:7.7.8.1")
    implementation("com.unity3d.ads-mediation:vungle-adapter:5.15.0")
    implementation("com.applovin.mediation:vungle-adapter:7.7.8.1")
    implementation("com.x3mads.android.xmediator.mediation:vungle:7.7.8.0")
    // yandex
    implementation("com.yandex.android:mobileads:8.2.0")
    implementation("com.unity3d.ads-mediation:yandex-adapter:5.11.0")
    implementation("com.applovin.mediation:yandex-adapter:8.2.0.1")
    implementation("com.x3mads.android.xmediator.mediation:yandex:8.2.0.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}