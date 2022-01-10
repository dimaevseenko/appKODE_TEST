package net.format_tv.test.models

data class User(
    val id: String,
    val avatarUrl: String,
    val firstName: String,
    val lastName: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: String,
    val phone: String
)

class Users: ArrayList<User>()

data class RUsersResult(
    val items: Users
)