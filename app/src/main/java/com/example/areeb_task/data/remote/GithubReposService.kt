package com.example.areeb_task.data.remote

import com.example.areeb_task.domian.model.UserData
import com.example.areeb_task.domian.model.UserModelItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubReposService {

    @GET("repositories")
    suspend fun getAllRepositories():List<UserModelItem>



    @GET("repos/{name}/{repo}")
    suspend fun getUserData(
        @Path("name")
        name : String,
        @Path("repo")
        repo : String
    ) : UserData

}