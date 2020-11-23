object Version {
    const val KOTLIN = "1.3.72"
}

object ProjectConfig {
    const val GRADLE = "com.android.tools.build:gradle:4.0.0"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN}"
}

object AndroidConfig {
    const val COMPILE_SDK = 29
    const val APP_ID = "com.egiwon.architecturestudy"
    const val MIN_SDK = 23
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

object Dependencies {
    private const val LIFECYCLE_VER = "2.2.0"
    private const val KOIN_VER = "2.0.1"
    private const val GLIDE_VER = "4.11.0"
    private const val RXJAVA_VER = "2.2.16"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}"

    const val MATERIAL = "com.google.android.material:material:1.1.0"
    const val CORE_KTX = "androidx.core:core-ktx:1.3.0"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"

    const val LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VER"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VER"

    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:1.1.0"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.2.5"

    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VER"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VER"

    const val KOIN = "org.koin:koin-core:$KOIN_VER"
    const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:$KOIN_VER"

    const val RXJAVA = "io.reactivex.rxjava2:rxjava:$RXJAVA_VER"
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    const val RX_BINDING = "com.jakewharton.rxbinding3:rxbinding:3.1.0"

    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:2.8.2"
}

object TestDependencies {
    const val JUNIT = "junit:junit:4.12"
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.1"
    const val ASSERTJ_CORE = "org.assertj:assertj-core:3.18.0"

    const val MOCKK = "io.mockk:mockk:1.10.2"
    const val ROOM_TEST = "androidx.room:room-testing:${DatabaseDependencies.ROOM_VER}"
}

object NetworkDependencies {
    private const val RETROFIT_VER = "2.7.2"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VER"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VER"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:3.14.2"
    const val RXJAVA_ADAPTER = "com.squareup.retrofit2:adapter-rxjava2:$RETROFIT_VER"
}

object DatabaseDependencies {
    const val ROOM_VER = "2.2.5"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VER"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VER"
    const val ROOM_KTX = "androidx.room:room-ktx:$ROOM_VER"
    const val ROOM_RXJAVA = "androidx.room:room-rxjava2:$ROOM_VER"
}
