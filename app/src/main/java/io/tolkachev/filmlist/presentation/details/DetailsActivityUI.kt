package io.tolkachev.filmlist.presentation.details

import android.graphics.Typeface
import android.widget.TextView
import com.bumptech.glide.Glide
import io.tolkachev.filmlist.enitity.FilmDetails
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.padding
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class DetailsActivityUI(
    private val film: FilmDetails
) : AnkoComponent<DetailsActivity> {

    override fun createView(ui: AnkoContext<DetailsActivity>) = with(ui) {
        scrollView {
            verticalLayout {
                textView {
                    text = film.title
                    textSize = 14f
                    typeface = Typeface.DEFAULT_BOLD
                }
                textView {
                    text = film.original_title
                    textSize = 8f
                    typeface =
                            Typeface.defaultFromStyle(Typeface.ITALIC)
                }
                textView {
                    text = film.release_date?.substring(0, 4) ?: ""
                    textSize = 10f
                    typeface = Typeface.DEFAULT_BOLD
                }
                val img = imageView {
                    setPadding(dip(50), 0, dip(50), 0)
                }
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                    .into(img)
                textView {
                    text = film.overview
                    textSize = dip(8).toFloat()
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    typeface = Typeface.DEFAULT
                }
            }.lparams {
                padding = dip(20)
            }
        }
    }
}