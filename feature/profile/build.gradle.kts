plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KSP)
    id(BuildPlugins.DAGGER_HILT_ANDROID)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.profile"
}

dependencies {
// Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    domainModule()
    dataModule()
    presentationModule()
    navigatorModule()
    commonModule()

    androidx()
    hilt()
    retrofit()
    testDeps()
    testImplDeps()
    debugDeps()

}