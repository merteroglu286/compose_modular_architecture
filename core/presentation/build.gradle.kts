plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.presentation"
}

dependencies {

    domainModule()

    androidx()

    testDeps()
    testImplDeps()
    debugDeps()

}