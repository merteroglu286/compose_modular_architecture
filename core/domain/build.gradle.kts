plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.domain"
}

dependencies {

    kotlinx()
    coroutines()

    testDeps()
    testImplDeps()
    debugDeps()

}