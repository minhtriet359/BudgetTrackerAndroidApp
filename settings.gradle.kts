pluginManagement {
    repositories {
        google()
        jcenter()
        //mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url =uri("https://www.jitpack.io") }
        google()
        jcenter()
    }
}

rootProject.name = "Project2"
include(":app")
 