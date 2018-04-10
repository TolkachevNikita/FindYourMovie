package io.tolkachev.filmlist.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.tolkachev.filmlist.enitity.FilmInfo

@Dao
interface FilmsDao {
    @Query("SELECT * FROM FilmInfo")
    fun getAll(): List<FilmInfo>

    @Insert
    fun insertAll(films: List<FilmInfo>)
}