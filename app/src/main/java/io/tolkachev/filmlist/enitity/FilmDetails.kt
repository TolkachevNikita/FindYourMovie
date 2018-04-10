package io.tolkachev.filmlist.enitity

data class FilmDetails(
    val adult: Boolean,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String? = null,
    val runtime: Int,
    val tagline: String
)