package com.example.labs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Слой Data: Репозиторий для работы с сетевыми данными.
 */
class PostRepository {
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    suspend fun fetchPosts(): List<Post> {
        return apiService.getPosts()
    }
}
