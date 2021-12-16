package com.savvynomad.newsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page : Int,
        @Query("apiKey") apiKey: String = "8abd584f9cd549bf8c85d5e0ac7d1f76"
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("qInTitle") query: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = "8abd584f9cd549bf8c85d5e0ac7d1f76"
    ): Response<NewsResponse>
}