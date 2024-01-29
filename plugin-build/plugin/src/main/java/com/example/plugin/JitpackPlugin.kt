package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

class JitpackPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            extensions.findByType(org.gradle.api.publish.PublishingExtension::class.java)
                ?.apply {
                    publications {
                        create<MavenPublication>("release"){
                            from(components["release"])
                            groupId = "com.github.your github name"
                            artifactId = "your Repository name"
                            version = "Same As Tag"

                            pom {
                                name.set("name")
                                description.set("description")
                            }
                        }
                    }
                }
        }
    }
}