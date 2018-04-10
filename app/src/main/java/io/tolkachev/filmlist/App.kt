package io.tolkachev.filmlist

import android.app.Application
import android.arch.persistence.room.Room
import io.tolkachev.filmlist.data.FilmDatabase

class App : Application() {

    lateinit var database: FilmDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, FilmDatabase::class.java, "films")
            .build()
    }
}