package com.example.practicaltask.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicaltask.database.Movies
import com.example.practicaltask.databinding.RawMoviesItemBinding
import com.example.practicaltask.ui.ActivityDetail
import com.example.practicaltask.utils.listeners.setSafeOnClickListener
import com.example.practicaltask.viewmodel.DashboardViewModel


class MoviesListAdapter(val context: Context,
    private val dashboardViewModel: DashboardViewModel,
    val mListMovie: ArrayList<Movies> = ArrayList()
): RecyclerView.Adapter<MoviesListAdapter.UserListViewHolder>(){
    inner class UserListViewHolder(private val rawUserItemBinding: RawMoviesItemBinding) :
        RecyclerView.ViewHolder(rawUserItemBinding.root) {

        fun bind(movies: Movies) {
            rawUserItemBinding.dashboardViewModel = dashboardViewModel
            rawUserItemBinding.movie = movies
            Glide
                .with(context)
                .load(movies.thumb_url)
                .into(rawUserItemBinding.image)

            rawUserItemBinding.image.setSafeOnClickListener {
                var intent = Intent(context,ActivityDetail::class.java)
                intent.putExtra("name",movies.name)
                intent.putExtra("actor",movies.actors)
                intent.putExtra("director",movies.directors)
                intent.putExtra("img",movies.thumb_url)
                intent.putExtra("rating",movies.rating)
                intent.putExtra("desc",movies.desc)
                intent.putExtra("genre",movies.genres)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK 
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RawMoviesItemBinding.inflate(inflater)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(mListMovie[position])
    }

    override fun onBindViewHolder(
        holder: UserListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as Movies
            holder.bind(newItem)
        }
    }

    override fun getItemCount(): Int {
        return mListMovie.size
    }

}
