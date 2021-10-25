package com.example.practicaltask.ui.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.database.Genre
import com.example.practicaltask.database.Movies
import com.example.practicaltask.databinding.RawMovieItemBinding
import com.example.practicaltask.viewmodel.DashboardViewModel


class GenreListAdapter(val context: Context,
                       private val dashboardViewModel: DashboardViewModel, val list: ArrayList<Genre> = ArrayList(), val appDatabase:AppDatabase
): RecyclerView.Adapter<GenreListAdapter.UserListViewHolder>(){
    var mListMovie = ArrayList<Movies>()
    inner class UserListViewHolder(private val rawUserItemBinding: RawMovieItemBinding) :
        RecyclerView.ViewHolder(rawUserItemBinding.root) {

        fun bind(gener: Genre,position: Int) {
            rawUserItemBinding.dashboardViewModel = dashboardViewModel
            rawUserItemBinding.genre = gener
            mListMovie.clear()

            mListMovie.addAll(appDatabase.moviesDao().getAll(list[position].genreName.toString()))

            rawUserItemBinding.recyclerviewMovie.adapter = MoviesListAdapter(context,dashboardViewModel,mListMovie)
            Log.d("mlist", "$position - ${mListMovie.size} - $mListMovie")
            Handler(Looper.getMainLooper()).postDelayed({

            }, 1000)

//            dashboardViewModel.movieListAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RawMovieItemBinding.inflate(inflater)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(list[position],position)
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
            holder.bind(newItem,position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
