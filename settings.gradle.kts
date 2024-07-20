pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven { url = uri("https://www.jitpack.io") }

    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()  // Có thể không cần thiết nếu đã thay thế bằng Maven Central
        maven { url = uri("https://www.jitpack.io")}
        gradlePluginPortal()
    }
}

rootProject.name = "Fontend_BookApp_FPoly"
include(":app")
