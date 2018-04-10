package io.tolkachev.filmlist.presentation.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.tolkachev.filmlist.data.loadFilmDetails
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newSingleThreadContext

class DetailsActivity : AppCompatActivity() {

    private var selectedFilm: Int = 0
    private val background = newSingleThreadContext("film_context")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedFilm = intent.getIntExtra("film_id", 0)
        launch(UI) {
            val filmDetails = loadFilmDetails(selectedFilm, background).await()
            DetailsActivityUI(filmDetails).setContentView(this@DetailsActivity)
        }
    }
}