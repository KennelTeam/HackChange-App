package com.kennelteam.hack_change

import android.content.Context
import android.util.Log
import java.lang.Exception
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Networker {
    companion object {
        private val jsonFormat = Json { ignoreUnknownKeys = true }
        private lateinit var requestQueue: RequestQueue
        private const val serverAddress: String = "http://18.133.117.201:5000/"

        fun setup(context: Context) {
            requestQueue = Volley.newRequestQueue(context)
        }

        private fun process_login(response: String,
                  succsess_callback: (id: Int, access_token: String) -> Unit,
                  fail_callback: (e: Error) -> Unit) {
            try {
                val data = jsonFormat.decodeFromString<LoginInfo>(response)
                succsess_callback(data.user_id, data.access_token)
            } catch (e: Exception) {
                fail_callback(Error(-1, "Error while parsing json"))
            }
        }

        fun register(login: String, password: String,
                     onRegister: (id: Int, accessToken: String) -> Unit,
                     onFail: (e: Error) -> Unit) {
            sendRequest("register", mapOf("nickname" to login, "password" to password),
                { process_login(it, onRegister, onFail) }, onFail)
        }

        fun login(login: String, password: String,
          onLogin: (id: Int, access_token: String) -> Unit,
          onFail: (e: Error) -> Unit) {

            sendRequest("login", mapOf("nickname" to login, "password" to password),
                { process_login(it, onLogin, onFail) }, onFail)
        }

        fun getProfile(profileId: Int, setInfo: (user: UserInfo, postIds: List<Int>) -> Unit,
                onFail: (e: Error) -> Unit) {
            sendRequest("getProfile", mapOf("user_id" to profileId.toString()), {
                try {
                    val data = jsonFormat.decodeFromString<ProfileInfo>(it)
                    setInfo(data.info, data.posts)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun setMyInfo(nickname: String? = null, avatar_link: String? = null, onFail: (e: Error) -> Unit) {
            val params: MutableMap<String, String> = mutableMapOf()
            if (nickname != null) {
                params.set("nickname", nickname)
            }
            if (avatar_link != null) {
                params.set("avatar_link", avatar_link)
            }

            if (params.isNotEmpty()) {
                sendRequest("setMyInfo", params.toMap(), {}, onFail)
            }
        }

        fun getPost(post_id: Int, onSuccess: (post: PostExtended) -> Unit,
                    onFail: (e: Error) -> Unit) {
            sendRequest("getPost", mapOf("post_id" to post_id.toString()), {
                try {
                    val data = jsonFormat.decodeFromString<PostExtended>(it)
                    onSuccess(data)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun getAllInstruments(onSuccess: (post: List<Instrument>) -> Unit,
                              onFail: (e: Error) -> Unit) {
            sendRequest("allInstruments", emptyMap<String, String>(), {
                try {
                    val data = jsonFormat.decodeFromString<AllInstruments>(it)
                    onSuccess(data.instruments)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun addTopic(instrument_id: Int, title: String, onFail: (e: Error) -> Unit) {
            sendRequest("addTopic", mapOf("instrument_id" to instrument_id.toString(),
                "title" to title), {}, onFail)
        }

        fun getTopicsByInstrument(instrument_id: Int,
                                  onSuccess: (comments: List<PostTopic>) -> Unit,
                                  onFail: (e: Error) -> Unit) {
            sendRequest("topicsByInstrument", mapOf("instrument_id" to instrument_id.toString()), {
                try {
                    val data = jsonFormat.decodeFromString<Topics>(it)
                    onSuccess(data.topics)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun postsByTopic(topic_id: Int, onSuccess: (posts: List<PostExtended>) -> Unit,
                         onFail: (e: Error) -> Unit) {
            sendRequest("postsByTopic", mapOf("topic_id" to topic_id.toString()), {
                try {
                    val data = jsonFormat.decodeFromString<Posts>(it)
                    onSuccess(data.posts)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun addPost(topic_id: Int, text: String, onFail: (e: Error) -> Unit) {
            sendRequest("addPost", mapOf("topic_id" to topic_id.toString(), "text" to text),
                {}, onFail)
        }

        fun addComment(post_id: Int, text: String, onFail: (e: Error) -> Unit) {
            sendRequest("addComment", mapOf("post_id" to post_id.toString(), "text" to text),
                {}, onFail)
        }

        fun getCommentsByPost(post_id: Int, onSuccess: (comments: List<Comment>) -> Unit,
                              onFail: (e: Error) -> Unit) {
            sendRequest("commentsByPost", mapOf("post_id" to post_id.toString()), {
                try {
                    val data = jsonFormat.decodeFromString<Comments>(it)
                    onSuccess(data.comments)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        fun subscribe(user_id: Int, onFail: (e: Error) -> Unit) {
            sendRequest("subscribe", mapOf("user_id" to user_id.toString()), {}, onFail)
        }

        fun unsubscribe(user_id: Int, onFail: (e: Error) -> Unit) {
            sendRequest("unsubscribe", mapOf("user_id" to user_id.toString()), {}, onFail)
        }

        fun getSubscriptionsPosts(onSuccess: (posts: List<PostExtended>) -> Unit,
                                  onFail: (e: Error) -> Unit) {
            sendRequest("mySubscriptionsPosts", emptyMap(), {
                try {
                    val data = jsonFormat.decodeFromString<Posts>(it)
                    onSuccess(data.posts)
                } catch (e: Exception) {
                    onFail(Error(-1, e.message!!))
                }
            }, onFail)
        }

        // contract: succsess_callback receives valid json string
        private fun sendRequest(page: String, params: Map<String, String>,
                                succsess_callback: (a: String) -> Unit,
                                fail_callback: (e: Error) -> Unit) {
            var totalUrl = page

            totalUrl += "?"

            if (page != "login" && page != "register") {
                if (AccessTokenManager.hasToken()) {
                    totalUrl += "access_token=" + AccessTokenManager.get_token()

                    if (params.size > 0) {
                        totalUrl += "&"
                    }
                } else {
                    fail_callback(Error(-2, "Not authorized action"))
                }

            }

            var left = params.size
            params.forEach {
                totalUrl += it.key + "=" + it.value
                left -= 1
                if (left != 0) {
                    totalUrl += "&"
                }
            }

            sendRequest(totalUrl) {
                if (it == null) {
                    fail_callback(Error(0, "Null response"))
                } else {
                    try {
                        val status = jsonFormat.decodeFromString<Status>(it)
                        if (status.ok) {
                            succsess_callback(it)
                        } else {
                            val error = jsonFormat.decodeFromString<Error>(it)
                            fail_callback(error)
                        }
                    } catch (e: Exception) {
                        fail_callback(Error(-1, "Error while parsing response"))
                    }
                }
            }
        }

        private fun sendRequest(url: String, callback: (a: String?) -> Unit) {
            try {
                //Log.i("Test!!!", context!!.toString())
                //context!!.
                val resultUrl = serverAddress + url
                val stringRequest = StringRequest(Request.Method.GET, resultUrl,
                    { response ->
                        callback(response)
                        Log.i("Test!", response)
                    }, { callback(null)
                    Log.i("Test!!! - error", it.toString())})
                //Log.i("Test!!!", "data: " + data)
                Log.i("Test!!! - request", stringRequest.toString())
                requestQueue.add(stringRequest)
            } catch (e: android.os.NetworkOnMainThreadException) {
                Log.i("Test!!!", "error: " + e.stackTraceToString())
            }
        }
    }
}
