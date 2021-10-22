package com.example.practicaltask.network.model

data class ModelResponseMovieData(

    var desc:String,
    var thumb_url:String,
    var name:String,
    var rating:String,
    var actors: ArrayList<String>,
    var directors: ArrayList<String>,
    var genre: ArrayList<String>
)

