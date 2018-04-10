package io.tolkachev.filmlist.enitity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FilmInfo(
    @PrimaryKey
    val id: Int,
    val title: String,
    val original_title: String,
    val poster_path: String,
    val release_date: String? = null,
    var user_mark: Int = 0
)