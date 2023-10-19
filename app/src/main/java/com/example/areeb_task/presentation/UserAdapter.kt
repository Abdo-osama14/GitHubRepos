package com.example.areeb_task.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.areeb_task.R
import com.example.areeb_task.domian.model.UserData
import kotlinx.android.synthetic.main.repo_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback=object :DiffUtil.ItemCallback<UserData>(){
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.full_name==newItem.full_name
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem==newItem
        }
    }
    val differRepoList= AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.repo_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userRepo = differRepoList.currentList[position]
        holder.itemView.apply {
            tvRepo.text=userRepo.name
            tvOwner.text=userRepo.owner.login
            Glide.with(this).load(userRepo.owner.avatar_url).into(ivUserImage)
            val date=formatDate(userRepo.created_at)
            tvDate.text=date
        }

    }

    override fun getItemCount(): Int {
        return differRepoList.currentList.size
    }
    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val currentDate = Date()

        val sixMonthsInMillis = 6L * 30L * 24L * 60L * 60L * 1000L
        val diffInMillis = currentDate.time - date.time

        return if (diffInMillis > sixMonthsInMillis) {
            val outputFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
            outputFormat.format(date)
        } else {
            val diffInMonths = (diffInMillis / (30L * 24L * 60L * 60L * 1000L)).toInt()
            "$diffInMonths months ago"
        }
    }
}