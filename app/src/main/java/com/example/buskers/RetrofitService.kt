package com.example.buskers

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>


    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("post/all/")
    fun getAllPosts(): Call<ArrayList<Post>>

    @Multipart
    @POST("post/create/")
    fun uploadPost(
        @Part image : MultipartBody.Part,
        @Part ("content")requestBody : RequestBody
    ):Call<Post>

    @GET("post/mylist/")
    fun getUserPostList():Call<ArrayList<Post>>

    @DELETE("post/{pk}/delete/")
    fun deletePost(
        @Path("pk") pk: Int
    ):Call<Post>

    @Multipart
    @PUT("post/{pk}/update/")
    fun updatePost(
        @Path("pk") pk: Int,
        @Part image: MultipartBody.Part,
        @Part ("content") requestBody: RequestBody
    ):Call<Post>

    @GET("user/profile/all/")
    fun getProfileList():Call<ArrayList<Profile>>

    @POST("user/profile/create/")
    @FormUrlEncoded
    fun uploadProfile(
        @Field("username") username: String
    ):Call<Profile>

    @GET("user/profile/{owner}/")
    fun getProfile(
        @Path("owner") owner: String
    ):Call<Profile>

    @DELETE("user/profile/{owner}/delete/")
    fun deleteProfile(
        @Path("owner") owner: String
    ):Call<Profile>

    @Multipart
    @PUT("user/profile/{owner}/update/")
    fun updateProfile(
        @Path("owner") owner: String,
        @Part image: MultipartBody.Part
    ):Call<Profile>
}