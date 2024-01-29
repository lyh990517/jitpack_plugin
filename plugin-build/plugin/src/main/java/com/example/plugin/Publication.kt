package com.example.plugin

data class Publication(
    var githubName: String = "",
    var repositoryName: String = "",
    var version: String = "",
    var name: String = "",
    var description: String = ""
) {
    constructor(n: String) : this() {
        name = n
    }
}
