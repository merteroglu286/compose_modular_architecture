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

    domainModule()
    dataModule()
    presentationModule()
    navigatorModule()

    androidx()
    hilt()
    retrofit()
    testDeps()
    testImplDeps()
    debugDeps()

}