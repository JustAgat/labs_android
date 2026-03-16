package com.example.labs

import retrofit2.Call
import retrofit2.http.GET

/**
 * Лабораторная работа 13: Интерфейс для Retrofit.
 * Описываем запросы к API.
 */
interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
