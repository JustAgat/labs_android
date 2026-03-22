package com.example.labs

import retrofit2.http.GET

/**
 * Лабораторная работа 13 & 15: Интерфейс для Retrofit с поддержкой Coroutines.
 */
interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
