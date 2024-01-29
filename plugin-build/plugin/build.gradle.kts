plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
    `java-library`
    kotlin("jvm") version "1.8.0"
    `maven-publish`
}
gradlePlugin{
    plugins {
        register("jitpack"){
            id = "jitpack.io"
            implementationClass = "com.example.plugin.JitpackPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.2.1")
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}
