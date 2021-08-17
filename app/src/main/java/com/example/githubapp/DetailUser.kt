package com.example.githubapp

data class DetailUser(
    var avatarUrl: String? = null,
    var company: String? = null,
    var followers: Int = 0,
    var following: Int = 0,
    var location: String? = null,
    var login: String? = null,
    var name: String? = null,
    var publicRepos: Int = 0,
)