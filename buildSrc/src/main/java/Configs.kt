object Configs {
    const val applicationId = "com.example.movieapp"
    const val compileSdkVersion = 31
    const val minSdkVersion = 30
    const val targetSdkVersion = 32
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val versionCode = calculateVersionCode()
    val versionName = calculateVersionName()
    private val versionMajor = 1
    private val versionMinor = 0
    private val versionPatch = 0

    private fun calculateVersionCode(): Int = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100

    private fun calculateVersionName(): String = "${versionMajor}.${versionMinor}.${versionPatch}"
}