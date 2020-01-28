package com.example.firebaseretrofit.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.firebaseretrofit.util.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetWorkService {
    class NetworkInterceptor(context: Context) : Interceptor {
        private var _context: Context = context

        override fun intercept(chain: Interceptor.Chain?): Response {
            if (!isConnected(_context)) {
                throw Exception(Constants.NetworkConnection.NETWORK_NO_CONNECTION)
            }

            val builder: Request.Builder = chain!!.request().newBuilder()
            return chain.proceed(builder.build())
        }

    }

    companion object {
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

            return activeNetwork?.isConnected == true
        }
    }
}