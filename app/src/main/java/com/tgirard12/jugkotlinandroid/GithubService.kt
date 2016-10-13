package com.tgirard12.jugkotlinandroid


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/search/repositories")
    fun githubSearch(@Query("q") query: String): Call<Search>

}
