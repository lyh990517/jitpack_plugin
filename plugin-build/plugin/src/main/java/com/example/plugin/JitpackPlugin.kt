package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import java.io.File

class JitpackPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<MyPluginExtension>(
            "Jitpack",
            project.container(Publication::class.java)
        )

        project.afterEvaluate {
            configureMavenPublication(project, extension)
            addJitpackYmlToGit(project)
        }
    }

    private fun configureMavenPublication(project: Project, extension: MyPluginExtension) {
        try {
            extension.myPublication.getByName("release").let { info ->
                project.extensions.findByType(org.gradle.api.publish.PublishingExtension::class.java)
                    ?.apply {
                        publications {
                            create<MavenPublication>("release") {
                                from(project.components["release"])
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addJitpackYmlToGit(project: Project) {
        val jitpackFile = File(project.rootDir, "jitpack.yml")
        if (!jitpackFile.exists() && jitpackFile.createNewFile()) {
            jitpackFile.writeText(jitpackYmlContent())
            gitAddFile(project, jitpackFile)
        }
    }

    private fun gitAddFile(project: Project, file: File) {
        try {
            ProcessBuilder("git", "add", file.absolutePath)
                .directory(project.rootDir)
                .start()
                .waitFor()
        } catch (e: Exception) {
            project.logger.error("Error adding file to Git: ${e.message}")
        }
    }

    private fun jitpackYmlContent(): String = """
        jdk:
          - openjdk17
    """.trimIndent()
}