package com.example.areeb_task.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.areeb_task.domian.model.UserData
import com.example.areeb_task.domian.repositories.UserRepository
import com.example.areeb_task.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repoRepository: UserRepository):ViewModel() {

    private val _usersRepoData= MutableLiveData<Resource<List<UserData>>>()
    val usersRepoData:LiveData<Resource<List<UserData>>> = _usersRepoData

    fun getUsersData()=viewModelScope.launch {
        _usersRepoData.value = Resource.Loading()
        val usersData = repoRepository.getUserData()
        _usersRepoData.value=handleGetUsersDataResponse(usersData)
    }

   private fun handleGetUsersDataResponse(usersList: List<UserData>?) : Resource<List<UserData>> {
       return if (usersList?.isNotEmpty() == true){
           Resource.Success(usersList)
       } else{
           Resource.Error("Filed To Download Data")
       }

   }
}