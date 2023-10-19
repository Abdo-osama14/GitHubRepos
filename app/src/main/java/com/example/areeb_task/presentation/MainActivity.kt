package com.example.areeb_task.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.areeb_task.R
import com.example.areeb_task.utill.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var repoAdapter: UserAdapter
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewAdapter()
        viewModel=ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.getUsersData()

        viewModel.usersRepoData.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    repoAdapter.differRepoList.submitList(it.data?.toList())
                }
                is Resource.Error -> {
                    hideLoading()
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    private fun setupViewAdapter() {
        repoAdapter = UserAdapter()
        rvUsersRepos.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
    private fun hideLoading(){
        progressBar.isVisible=false
    }
    private fun showLoading(){
        progressBar.isVisible=true
    }
}