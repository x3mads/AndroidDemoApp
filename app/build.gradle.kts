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
    // Note: Use the picker to select your dependencies https://docs.loomit.x3mads.com/docs/sdk/android/?mediators=X3M%2CMAX%2CGoogleAds%2CLevelPlay&networks=aps%2Capplovin%2Cbigoads%2Cchartboost%2Cfacebook%2Cfyber%2Cgoogleads%2Chyprmx%2Cinmobi%2Cironsource%2Cmintegral%2Cmobilefuse%2Cmoloco%2Cogury%2Cpangle%2Cpubmatic%2Cstack%2Ctappx%2Cunityads%2Cverve%2Cvungle%2Cyandex
    // -- Mediation Libraries --
    implementation("com.x3mads.android.xmediator:core:1.153.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.6.2.3")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:25.2.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:9.4.0.0")

    // -- Ad Networks --
    // amazon publisher services
    implementation("com.amazon.android:aps-sdk:11.1.2")
    implementation("com.applovin.mediation:amazon-tam-adapter:11.1.2.0")
    implementation("com.x3mads.android.xmediator.mediation:aps:11.1.2.0")
    // applovin
    implementation("com.applovin:applovin-sdk:13.6.2")
    implementation("com.google.ads.mediation:applovin:13.6.2.0")
    implementation("com.unity3d.ads-mediation:applovin-adapter:5.5.0")
    implementation("com.x3mads.android.xmediator.mediation:applovin:13.6.2.3")
    // bigo ads
    implementation("com.bigossp:bigo-ads:5.8.2")
    implementation("com.unity3d.ads-mediation:bigo-adapter:5.6.0")
    implementation("com.applovin.mediation:bigoads-adapter:5.8.2.0")
    // chartboost
    implementation("com.chartboost:chartboost-sdk:9.11.0")
    implementation("com.google.ads.mediation:chartboost:9.11.0.1")
    implementation("com.unity3d.ads-mediation:chartboost-adapter:5.3.0")
    implementation("com.applovin.mediation:chartboost-adapter:9.11.0.0")
    implementation("com.x3mads.android.xmediator.mediation:chartboost:9.11.0.0")
    // meta audience network
    implementation("com.facebook.android:audience-network-sdk:6.21.0")
    implementation("com.google.ads.mediation:facebook:6.21.0.2")
    implementation("com.unity3d.ads-mediation:facebook-adapter:5.2.0")
    implementation("com.applovin.mediation:facebook-adapter:6.21.0.0")
    implementation("com.x3mads.android.xmediator.mediation:facebook:6.21.0.0")
    // dt exchange
    implementation("com.fyber:marketplace-sdk:8.4.1")
    implementation("com.google.ads.mediation:fyber:8.4.1.0")
    implementation("com.unity3d.ads-mediation:fyber-adapter:5.2.0")
    implementation("com.applovin.mediation:fyber-adapter:8.4.1.0")
    implementation("com.x3mads.android.xmediator.mediation:fyber:8.4.1.0")
    // google ads
    implementation("com.google.android.gms:play-services-ads:25.2.0")
    implementation("com.unity3d.ads-mediation:admob-adapter:5.8.0")
    implementation("com.applovin.mediation:google-ad-manager-adapter:25.2.0.0")
    implementation("com.applovin.mediation:google-adapter:25.2.0.0")
    implementation("com.x3mads.android.xmediator.mediation:google-ads:25.2.0.0")
    // hyprmx
    implementation("com.hyprmx.android:HyprMX-SDK:6.4.2")
    implementation("com.hyprmx.android:HyprMX-AdMob:6.4.2.2")
    implementation("com.unity3d.ads-mediation:hyprmx-adapter:4.3.13")
    implementation("com.applovin.mediation:hyprmx-adapter:6.4.2.3")
    implementation("com.x3mads.android.xmediator.mediation:hyprmx:6.4.2.0")
    // inmobi
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:11.2.0")
    implementation("com.google.ads.mediation:inmobi:11.2.0.0")
    implementation("com.unity3d.ads-mediation:inmobi-adapter:5.5.0")
    implementation("com.applovin.mediation:inmobi-adapter:11.2.0.0")
    implementation("com.x3mads.android.xmediator.mediation:inmobi:11.2.0.0")
    // ironsource
    implementation("com.unity3d.ads-mediation:mediation-sdk:9.4.0")
    implementation("com.google.ads.mediation:ironsource:9.4.0.0")
    implementation("com.applovin.mediation:ironsource-adapter:9.4.0.0.0")
    implementation("com.x3mads.android.xmediator.mediation:ironsource:9.4.0.0")
    // mintegral
    implementation("com.mbridge.msdk.oversea:mbridge_android_sdk:17.0.31")
    implementation("com.google.ads.mediation:mintegral:17.0.31.0")
    implementation("com.unity3d.ads-mediation:mintegral-adapter:5.3.0")
    implementation("com.applovin.mediation:mintegral-adapter:17.0.31.0")
    implementation("com.x3mads.android.xmediator.mediation:mintegral:17.0.31.0")
    // mobilefuse
    implementation("com.mobilefuse.sdk:mobilefuse-sdk-core:1.11.0")
    implementation("com.mobilefuse.sdk:mobilefuse-adapter-admob:1.11.0.0")
    implementation("com.unity3d.ads-mediation:mobilefuse-adapter:5.3.0")
    implementation("com.applovin.mediation:mobilefuse-adapter:1.11.0.0")
    implementation("com.x3mads.android.xmediator.mediation:mobile-fuse:1.11.0.0")
    // moloco
    implementation("com.moloco.sdk.adapters:adapter:4.7.0")
    implementation("com.google.ads.mediation:moloco:4.7.0.0")
    implementation("com.unity3d.ads-mediation:moloco-adapter:5.8.0")
    implementation("com.applovin.mediation:moloco-adapter:4.7.0.0")
    implementation("com.x3mads.android.xmediator.mediation:moloco:4.7.0.0")
    // ogury
    implementation("co.ogury:ogury-sdk:6.2.2")
    implementation("com.unity3d.ads-mediation:ogury-adapter:5.3.0")
    implementation("com.applovin.mediation:ogury-presage-adapter:6.2.2.0")
    implementation("com.x3mads.android.xmediator.mediation:ogury:6.2.2.0")
    // pangle
    implementation("com.pangle.global:pag-sdk:7.9.1.3")
    implementation("com.google.ads.mediation:pangle:7.9.1.3.0")
    implementation("com.unity3d.ads-mediation:pangle-adapter:5.11.0")
    implementation("com.applovin.mediation:bytedance-adapter:7.9.1.3.0")
    implementation("com.x3mads.android.xmediator.mediation:pangle:7.9.1.2.0")
    // pubmatic
    implementation("com.pubmatic.sdk:openwrap:5.0.0")
    implementation("com.google.ads.mediation:pubmatic:5.0.0.1")
    implementation("com.applovin.mediation:pubmatic-adapter:5.0.0.0")
    // smaato
    implementation("com.smaato.android.sdk:module-core:22.7.2")
    implementation("com.smaato.android.sdk:smaato-sdk:22.7.2")
    implementation("com.smaato.android.sdk:smaato-sdk-in-app-bidding:22.7.2")
    implementation("com.smaato.android.sdk:smaato-sdk-native:22.7.2")
    implementation("com.applovin.mediation:smaato-adapter:22.7.2.3")
    // bidmachine
    implementation("io.bidmachine:ads:3.6.1")
    implementation("com.unity3d.ads-mediation:bidmachine-adapter:5.4.0")
    implementation("com.applovin.mediation:bidmachine-adapter:3.6.1.0")
    implementation("com.x3mads.android.xmediator.mediation:stack:3.6.1.0")
    // tappx
    implementation("com.tappx.sdk.android:tappx-sdk:4.1.18")
    implementation("com.x3mads.android.xmediator.mediation:tappx:4.1.18.0")
    // unity ads
    implementation("com.unity3d.ads:unity-ads:4.17.0")
    implementation("com.google.ads.mediation:unity:4.17.0.0")
    implementation("com.unity3d.ads-mediation:unityads-adapter:5.6.0")
    implementation("com.applovin.mediation:unityads-adapter:4.17.0.0")
    implementation("com.x3mads.android.xmediator.mediation:unity:4.17.0.0")
    // verve
    implementation("net.pubnative:hybid.sdk:3.7.1")
    implementation("com.unity3d.ads-mediation:verve-adapter:5.2.0")
    implementation("com.applovin.mediation:verve-adapter:3.7.1.0")
    // liftoff monetize
    implementation("com.vungle:vungle-ads:7.7.2")
    implementation("com.google.ads.mediation:vungle:7.7.2.0")
    implementation("com.unity3d.ads-mediation:vungle-adapter:5.7.0")
    implementation("com.applovin.mediation:vungle-adapter:7.7.2.0")
    implementation("com.x3mads.android.xmediator.mediation:vungle:7.7.2.0")
    // yandex
    implementation("com.yandex.android:mobileads:7.18.5")
    implementation("com.unity3d.ads-mediation:yandex-adapter:5.8.0")
    implementation("com.applovin.mediation:yandex-adapter:7.18.5.0")
    implementation("com.x3mads.android.xmediator.mediation:yandex:7.18.5.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}