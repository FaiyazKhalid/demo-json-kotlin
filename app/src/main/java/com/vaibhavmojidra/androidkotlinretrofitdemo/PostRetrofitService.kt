package com.vaibhavmojidra.androidkotlinretrofitdemo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PostRetrofitService {
    @GET("/posts")
    suspend fun getPosts():Response<Posts>

    @GET("/posts") //https://jsonplaceholder.typicode.com/posts?userId=3
    suspend fun getPostsByUserId(@Query("userId")userId:Int):Response<Posts>

    @GET("/posts/{id}") //https://jsonplaceholder.typicode.com/posts?userId=3
    suspend fun getPostWithId(@Path(value = "id")id:Int):Response<PostItem>
}