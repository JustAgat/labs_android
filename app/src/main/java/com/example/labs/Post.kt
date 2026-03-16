package com.example.labs

import com.google.gson.annotations.SerializedName

/**
 * Лабораторная работа 13: Модель данных для поста.
 * Поля соответствуют структуре JSON от https://jsonplaceholder.typicode.com/posts
 */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @SerializedName("body")
    val content: String // Используем SerializedName, если имя в JSON отличается от нашего
)
