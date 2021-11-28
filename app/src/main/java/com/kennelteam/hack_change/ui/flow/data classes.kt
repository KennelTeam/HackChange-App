package com.kennelteam.hack_change.ui.flow

data class Post(val id: Int, val theme: String, val userId: String, val text: String)

data class Comment(val id: Int, val userName: String, val text: String)
