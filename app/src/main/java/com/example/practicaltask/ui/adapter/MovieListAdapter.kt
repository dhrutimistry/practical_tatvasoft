package com.example.practicaltask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltask.R
import com.example.practicaltask.database.Genre
import com.example.practicaltask.databinding.RawMovieItemBinding
import com.example.practicaltask.viewmodel.DashboardViewModel


class MovieListAdapter(
    private val dashboardViewModel: DashboardViewModel,val list: ArrayList<Genre> = ArrayList()
): RecyclerView.Adapter<MovieListAdapter.UserListViewHolder>(){
    inner class UserListViewHolder(private val rawUserItemBinding: RawMovieItemBinding) :
        RecyclerView.ViewHolder(rawUserItemBinding.root) {

        fun bind(gener: Genre) {
            rawUserItemBinding.dashboardViewModel = dashboardViewModel
            rawUserItemBinding.genre = gener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RawMovieItemBinding.inflate(inflater)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onBindViewHolder(
        holder: UserListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as Genre
            holder.bind(newItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
