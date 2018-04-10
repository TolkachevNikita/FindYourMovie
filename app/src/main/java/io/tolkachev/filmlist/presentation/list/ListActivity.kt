package io.tolkachev.filmlist.presentation.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.tolkachev.filmlist.App
import io.tolkachev.filmlist.data.loadFilmInfo
import io.tolkachev.filmlist.data.loadLocalFilms
import io.tolkachev.filmlist.data.saveFilms
import io.tolkachev.filmlist.enitity.Films
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.setContentView

class ListActivity : AppCompatActivity() {

    val pickFilm = 100
    var filmList = Films()
    var adapter = FilmsAdapter(filmList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Сохраненные фильмы"
        ListActivityUI(adapter).setContentView(this)

        launch(UI) {
            val films = loadLocalFilms(application as App).await()
            if (films.isNotEmpty()) {
                filmList.addAll(films)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickFilm && resultCode == Activity.RESULT_OK) {
            if (data == null) return
            val film = data.getIntExtra("film_id", 0)
            launch(UI) {
                val filmInfo = loadFilmInfo(film).await()
                saveFilms(application as App, adapter.getAll())
                adapter.add(filmInfo)
            }
        }
    }
}