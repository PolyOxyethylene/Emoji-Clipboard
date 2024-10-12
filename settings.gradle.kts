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
        google()
        mavenCentral()
        maven {
            this.url = java.net.URI("https://jitpack.io")
        }
    }
}

rootProject.name = "Emoji Clipboard"
include(":app")
