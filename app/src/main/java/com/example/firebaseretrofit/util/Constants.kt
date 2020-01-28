package com.example.firebaseretrofit.util

import com.example.firebaseretrofit.App
import com.example.firebaseretrofit.R

class Constants {

    class NetworkConnection {
        companion object {
            val NETWORK_NO_CONNECTION = App.getResourses().getString(R.string.no_connection_description)
        }
    }

    class API {
        companion object {
            val BASE_URL_FIREBASE = "https://pontomobileemmanuel.firebaseio.com/"
        }
    }

}