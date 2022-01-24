package com.example.callapiexamplerx.api

import com.example.callapiexamplerx.printLog
import com.example.callapiexamplerx.utils.Constant
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiRetrofit : IApiRetrofit{
    companion object {
        const val timeOut = 30L
        val level = HttpLoggingInterceptor.Level.NONE
    }
    private lateinit var retrofitCoroutines: Retrofit
    private val retrofitWithoutAuthenticator: Retrofit by lazy {
        val baseUrl = Constant.URL_GITHUB
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun createHttpClient(token: String? = null): OkHttpClient {
        val logging = HttpLoggingInterceptor(ApiLogger())
        logging.level = level
        return token?.let {


            /**
             * create Http Client with authenticator token
             */
            OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }.build()
        } ?: kotlin.run {
            /**
             * create Http Client without authenticator token
             */
            OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }.build()
        }
    }
    class ApiLogger : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                        .create().toJson(JsonParser().parse(message))
                    printLog(message, prefix = " - ApiLogger")
                } catch (m: JsonSyntaxException) {
                    printLog(message, prefix = " - ApiLogger")
                }
            } else {
                printLog(message, prefix = " - ApiLogger")
            }
        }
    }

    override fun apiWithoutAuthenticator(): ApiCoroutines {
        TODO("Not yet implemented")
    }

    override fun apiWithAuthenticator(): ApiCoroutines {
        TODO("Not yet implemented")
    }

    override fun createApiCoroutines(token: String) {
        TODO("Not yet implemented")
    }
}