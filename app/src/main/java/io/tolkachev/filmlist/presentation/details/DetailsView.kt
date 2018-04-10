package io.tolkachev.filmlist.presentation.details

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import io.tolkachev.filmlist.enitity.FilmInfo
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.textView

class DetailsView(context: Context) : _LinearLayout(context) {

    private var poster = imageView {

    }.lparams(dip(100), dip(100))

    private var title = textView {

    }

    init {

    }

    fun bind(film: FilmInfo) {
        var releaseDate = ""
        try {
            releaseDate = film.release_date!!.substring(0, 4)
        } catch (e: Exception) {
            Log.e("str", film.release_date)
        }
        title.text = film.title + " (" + releaseDate + ")"
        invalidate()
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = film.poster_path
        Glide.with(context)
            .load(baseUrl + posterUrl)
            .into(poster)
    }
}