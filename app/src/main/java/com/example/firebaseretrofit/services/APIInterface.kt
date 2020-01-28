package com.example.firebaseretrofit.services

import android.content.Context
import rx.Observable
import com.example.firebaseretrofit.models.MarkPointRequest
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @POST("MarcacaoPonto.json")
    fun markPointFirebase(@Body markPointRequest: MarkPointRequest): Deferred<MarkPointRequest.Response>

    @Headers("Content-Type: application/json")
    @GET("MarcacaoPonto/{pointID}.json")
    fun getUserPointFirebase(@Path("pointID") pointID: String? ): Deferred<MarkPointRequest>

    @Headers("Content-Type: application/json")
    @GET("MarcacaoPonto.json")
    fun getAllPointFirebase(): Deferred<MarkPointRequest.Response>

    companion object {

        private var instance: APIInterface? = null

        fun getInstance(urlBase: String, context: Context): APIInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
            client.addInterceptor(interceptor)
            client.addInterceptor(NetWorkService.NetworkInterceptor(context))

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlBase)
                .client(client.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            instance = retrofit.create(APIInterface::class.java)

            return instance!!
        }
    }
}