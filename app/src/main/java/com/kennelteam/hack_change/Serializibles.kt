package com.kennelteam.hack_change

import android.telecom.Call
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import java.sql.Timestamp

@Serializable
data class Error(val error_code: Int, val error_desc: String)

@Serializable
data class Status(val ok: Boolean)

@Serializable
data class LoginInfo (val user_id: Int, val access_token: String)

@Serializable
data class ProfileInfo(val info: UserInfo, val posts: List<Int>)

@Serializable
data class PostTopic(val id: Int, val title: String)

@Serializable
data class UserInfo(val user_id: Int, val nickname: String, val avatar_link: String?)

@Serializable
data class PostExtended(val id: Int, val timestamp: String, val topic: PostTopic, val author: UserInfo, val text: String)

@Serializable
data class Instrument(val id: Int, val name: String, val details: String)

@Serializable
data class AllInstruments(val instruments: List<Instrument>)

@Serializable
data class Posts(val posts: List<PostExtended>)

@Serializable
data class Comment(val timestamp: String, val commenter: UserInfo, val text: String)

@Serializable
data class Comments(val comments: List<Comment>)


