package com.example.areeb_task.data.repository

import com.example.areeb_task.domian.repositories.UserRepository
import com.example.areeb_task.domian.model.UserData
import com.example.areeb_task.data.remote.GithubReposService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: GithubReposService) : UserRepository {


    override suspend fun getUserData(): List<UserData> =
        api.getAllRepositories().dropLast(50).map { userModelItem ->
            api.getUserData(userModelItem.owner.login,userModelItem.name)
       }

}