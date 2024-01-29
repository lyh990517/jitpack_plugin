package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

class JitpackPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val publicationContainer = project.container(Publication::class.java)
        val extension = project.extensions.create(
            "Jitpack",
            MyPluginExtension::class.java, publicationContainer
        )
        project.afterEvaluate {
            val info = extension.myPublication.getByName("release")
            println(info)
            extensions.findByType(org.gradle.api.publish.PublishingExtension::class.java)
                ?.apply {
                    publications {
                        create<MavenPublication>("release") {
                            from(components["release"])
                            groupId = info.githubName
                            artifactId = info.repositoryName
                            version = info.version

                            pom {
                                name.set(info.name)
                                description.set(info.description)
                            }
                        }
                    }
                }
        }
    }
}