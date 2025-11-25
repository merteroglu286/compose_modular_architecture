plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.merteroglu286.datastore"
}

dependencies {

    dataStore()

    kotlinx()

    testDeps()
    testImplDeps()
    debugDeps()

}