package com.example.areeb_task.data.repository

import com.example.areeb_task.domian.repositories.UserRepository
import com.example.areeb_task.domian.model.UserData
import com.example.areeb_task.data.remote.GithubReposService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: GithubReposService) : UserRepository {


    override suspend fun getUserData(): List<UserData> {
        val users = mutableListOf<Deferred<UserData>>()
        coroutineScope {
            api.getAllRepositories().map { userModelItem ->
                users.add(async { api.getUserData(userModelItem.owner.login, userModelItem.name) })
            }
        }
        return users.awaitAll()
    }

}