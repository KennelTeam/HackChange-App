package com.kennelteam.hack_change

import android.content.Context
import android.util.Log
import java.lang.Exception

class AccessTokenManager {
    companion object {
        private var _token: String? = null
        private var user_id: Int = -1
        private lateinit var context: Context
        private const val tokenFilename: String = "token_file"
        private const val idFilename: String = "id_file"

        fun hasToken(): Boolean {
            return _token != null
        }

        private val token get() = _token!!

        fun clear() {
            _token = null
            user_id = -1
            save()
        }

        fun reset(token: String, id: Int) {
            this._token = token
            this.user_id = id
            save()
        }

        fun get_token(): String? {
            return _token
        }

        fun get_id(): Int {
            return user_id
        }

        fun save() {
            if (hasToken()) {
                try {
                    context.openFileOutput(tokenFilename, Context.MODE_PRIVATE).use {
                        it.write(token.toByteArray())
                    }
                    context.openFileOutput(idFilename, Context.MODE_PRIVATE).use {
                        it.write(user_id.toString().toByteArray())
                    }
                } catch (e: Exception) {
                    Log.i("Test!!!", e.message!!)
                }

            }
        }

        fun setup(context: Context) {
            this.context = context
            try {
                context.openFileInput(tokenFilename).bufferedReader().useLines { lines ->
                    lines.forEach {
                        _token = it
                        Log.i("Test!!! - token", it)
                    }
                }

                context.openFileInput(idFilename).bufferedReader().useLines { lines ->
                    lines.forEach {
                        user_id = it.toInt()
                        Log.i("Test!!! - user_id", it)
                    }
                }
            } catch (e: Exception) {
                Log.i("Test!!! - error", e.message!!)
            }

        }
    }

}