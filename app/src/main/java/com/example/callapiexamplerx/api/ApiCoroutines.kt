package com.example.callapiexamplerx.api

import com.example.callapiexamplerx.model.RepoModel
import com.example.callapiexamplerx.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCoroutines {

    interface GitHubService {
        // get User
        @GET("users")
        fun listRepos(): Call<List<Users>>

        // getRepoExam
        @GET("/search/repositories")
        suspend fun listRepo(
            @Query("q") q: String,
            @Query("sort") sort: String
        ): Response<RepoModel>

        // getRepoExam
        @GET("/search/repositories")
        suspend fun listRepo1(
            @Query("q") q: String,
            @Query("sort") sort: String
        ): Call<RepoModel>
    }


}