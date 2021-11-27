package com.kennelteam.hack_change

import android.content.Context
import android.util.Log
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Networker {
    companion object {
        private lateinit var requestQueue: RequestQueue

        fun setup(context: Context) {
            requestQueue = Volley.newRequestQueue(context)
        }

        fun sendRequest(url: String, callback: (a: String?) -> Unit) {
            try {
                //Log.i("Test!!!", context!!.toString())
                //context!!.
                val resultUrl = "http://18.133.117.201:5000" + "/" + url
                val stringRequest = StringRequest(Request.Method.GET, resultUrl,
                    { response -> callback(response) }, { callback(null) })
                //Log.i("Test!!!", "data: " + data)

                requestQueue.add(stringRequest)
            } catch (e: android.os.NetworkOnMainThreadException) {
                Log.i("Test!!!", "error: " + e.stackTraceToString())
            }


        }
    }
}
