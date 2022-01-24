package com.example.callapiexamplerx.api

interface IApiRetrofit {
    fun apiWithoutAuthenticator(): ApiCoroutines

    fun apiWithAuthenticator(): ApiCoroutines

    fun createApiCoroutines(token: String)
}