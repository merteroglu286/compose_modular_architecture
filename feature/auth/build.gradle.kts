plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.DAGGER_HILT_ANDROID)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.auth"
}

dependencies {

    domainModule()
    dataModule()
    presentationModule()

    androidx()
    hilt()
    retrofit()
    testDeps()
    testImplDeps()
    debugDeps()

}