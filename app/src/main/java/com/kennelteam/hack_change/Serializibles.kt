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
data class ProfileInfo(val am_i_subscribed: Boolean, val subscribers_count: Int,
                       val info: UserInfo, val posts: List<Int>)

@Serializable
data class PostTopic(val topic_id: Int, val title: String)

@Serializable
data class UserInfo(val user_id: Int, val nickname: String)

@Serializable
data class PostExtended(val post_id: Int, val timestamp: String, val topic: PostTopic, val author: UserInfo, val text: String)

@Serializable
data class Instrument(val instrument_id: Int, val name: String, val details: String)

@Serializable
data class AllInstruments(val instruments: List<Instrument>)

@Serializable
data class Topics(val topics: List<PostTopic>)

@Serializable
data class Posts(val posts: List<PostExtended>)

@Serializable
data class Comment(val comment_id: Int, val timestamp: String, val commenter: UserInfo, val text: String)

@Serializable
data class Comments(val comments: List<Comment>)


