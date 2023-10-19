package com.example.areeb_task.domian.repositories

import com.example.areeb_task.domian.model.UserData
import com.example.areeb_task.domian.model.UserModel
import com.example.areeb_task.domian.model.UserModelItem
import retrofit2.Response

interface UserRepository {
    suspend fun getUserData(): List<UserData>?
}