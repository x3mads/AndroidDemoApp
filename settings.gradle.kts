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
        maven(url = "https://mavenrepo.etermax.com/artifactory/repo")
        google()
        mavenCentral()
        maven (
            url= "https://cboost.jfrog.io/artifactory/chartboost-ads/"
        )
        maven (
            url = "https://cboost.jfrog.io/artifactory/chartboost-mediation"
        )
        maven (
            url ="https://android-sdk.is.com/"
        )
        maven (
            url ="https://artifact.bytedance.com/repository/pangle"
        )
        maven (
            url ="https://artifactory.appodeal.com/appodeal"
        )
    }
}

rootProject.name = "X3MDemo"
include(":app")
 