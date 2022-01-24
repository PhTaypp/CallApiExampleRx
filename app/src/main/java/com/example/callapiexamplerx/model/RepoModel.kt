package com.example.callapiexamplerx.model

import com.google.gson.annotations.SerializedName

data class Users (
    var login: String? = null,
    var id: Int = 0,
//    @SerializedName("avatar_url")
//    var avatarURL: String? = null
    var avatar_url: String? = null,
)

data class RepoModel(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_result")
    val incompleteResult: Boolean,
    @SerializedName("items")
    val item: List<Item>
)
data class Item(
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: Owner,
//    @SerializedName("license")
//    val license: License,
//    @SerializedName("topics")
//    val topics : List<String>
)
data class Owner(
    @SerializedName("login")
    val loginName: String,
    @SerializedName("avatar_url")
    val avatarUrl : String,
    @SerializedName("id")
    val id: String,

    )
//data class RepoModel(
//    @SerializedName("node_id")
//    val nodeId: Int,
//    @SerializedName("id")
//    val id: String,
//    @SerializedName("full_name")
//    val fullName: String,
//    @SerializedName("owner")
//    val owner: Owner,
////    @SerializedName("license")
////    val license: License,
////    @SerializedName("topics")
////    val topics : List<String>
//)


//data class License(
//    @SerializedName("login")
//    val loginName: String,
//    @SerializedName("avatar_url")
//    val avatarUrl : String,
//    @SerializedName("url")
//    val url : String,
//)