pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://android-artifact-registry.x3mads.com/maven") }
        maven { url = uri("https://artifact.bytedance.com/repository/pangle") }
        maven { url = uri("https://artifactory.bidmachine.io/bidmachine") }
        maven { url = uri("https://cboost.jfrog.io/artifactory/chartboost-ads") }
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") }
        google()
        maven { url = uri("https://maven.ogury.co") }
        maven { url = uri("https://repo.maven.apache.org/maven2") }
        mavenCentral()
    }
}

rootProject.name = "X3MDemo"
include(":app")
 