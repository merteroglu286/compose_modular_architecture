plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KSP)
    id(BuildPlugins.DAGGER_HILT_ANDROID)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.navigator"
}

dependencies {

    androidx()

    hilt()

    testDeps()
    testImplDeps()
    debugDeps()

}