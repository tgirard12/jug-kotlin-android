package com.tgirard12.jugkotlinandroid;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {

    @GET("/search/repositories")
    Call<Search> githubSearch(@Query("q") String query);

}
