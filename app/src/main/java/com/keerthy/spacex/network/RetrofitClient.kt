package com.keerthy.spacex.network

import com.google.gson.GsonBuilder
import com.keerthy.spacex.repository.remote.SpaceXInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val TIME_OUT: Long = 10
    private const val RESPONSE_SUCCESS_CODE = 200

    private val gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder().readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS).addInterceptor { chain ->
            val resp = chain.proceed(chain.request())
            // Deal with the response code
            if (resp.code == RESPONSE_SUCCESS_CODE) {
                try {
                    //peeking to check if success
                    resp.peekBody(2048).string()
                } catch (e: Exception) {
                    println("Error parse json")
                }
            } else {
                println(resp)
            }
            resp
        }.build()

    val retrofit: SpaceXInterface by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiConstants.BASE_URL).client(okHttpClient).build()
            .create(SpaceXInterface::class.java)
    }

}