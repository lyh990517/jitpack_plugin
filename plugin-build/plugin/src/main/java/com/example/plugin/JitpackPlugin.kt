package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class JitpackPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("jitpack")
    }
}