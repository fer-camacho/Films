package com.example.films

import retrofit2.Call
import retrofit2.http.GET

interface MyAPI {

    @GET("films")
    fun getFilms(): Call<List<Film>>

    @GET("api")
    fun getAnswer(): Call<Answer>

}
