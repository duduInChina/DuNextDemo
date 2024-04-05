plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.datastore"
}

dependencies {
    api(projects.data.datastoreProto)
    api(libs.androidx.dataStore.core)
}
