object Classpaths {
    const val gradleClasspath = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    const val kotlinGradleClasspath =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val navigationGradleClasspath =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"
    const val hiltGradleClasspath =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}