plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.navigator"
}

dependencies {

    androidx()

    testDeps()
    testImplDeps()
    debugDeps()

}