package com.example.plugin

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

open class MyPluginExtension(
    val myPublication: NamedDomainObjectContainer<Publication>,
) {
    fun setJitpack(action: Action<NamedDomainObjectContainer<Publication>>) {
        action.execute(myPublication)
    }
}