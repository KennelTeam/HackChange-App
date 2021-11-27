package com.kennelteam.hack_change

import android.content.Context
import android.util.Log
import java.lang.Exception

class AccessTokenManager {
    companion object {
        private var _token: String? = null
        private lateinit var context: Context
        private const val filename: String = "token_file"

        fun hasToken(): Boolean {
            return _token != null
        }

        private val token get() = _token!!

        fun clearToken() {
            _token = null
        }

        fun resetToken(token: String) {
            this._token = token
            saveToken()
        }

        fun get_token(): String? {
            return _token
        }

        fun saveToken() {
            if (hasToken()) {
                try {
                    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                        it.write(token.toByteArray())
                    }
                } catch (e: Exception) {
                    Log.i("Test!!!", e.message!!)
                }

            }
        }

        fun setup(context: Context) {
            this.context = context
            try {
                context.openFileInput(filename).bufferedReader().useLines { lines ->
                    lines.forEach {
                        _token = it
                        Log.i("Test!!!", it)
                    }
                }
            } catch (e: Exception) {
                Log.i("Test!!!", e.message!!)
            }

        }
    }

}