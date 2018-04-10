package io.tolkachev.filmlist.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.tolkachev.filmlist.data.FilmsDao
import io.tolkachev.filmlist.enitity.FilmInfo

@Database(entities = [FilmInfo::class], version = 1)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}
