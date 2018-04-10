package io.tolkachev.filmlist.presentation.list

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import io.tolkachev.filmlist.R
import io.tolkachev.filmlist.enitity.FilmInfo
import io.tolkachev.filmlist.enitity.Films
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.textView

class FilmsAdapter(
    private var filmList: Films
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val film = filmList[position]
        return with(parent!!.context) {
            linearLayout {
                lparams(width = matchParent, height = dip(100))

                val img = imageView {
                    setPadding(dip(5), 0, dip(5), 0)
                }.lparams {
                    width = (parent.width * 0.20).toInt()
                    gravity = Gravity.CENTER
                }

                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                    .into(img)

                textView {
                    text = "${film.title} (${film.release_date?.substring(0, 4)})"
                    textSize = 20f
                    typeface = Typeface.DEFAULT
                    padding = dip(5)
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    width = (parent.width * 0.60).toInt()
                }

                imageView {
                    imageResource = if (film.user_mark > 0) R.drawable.ic_done_black
                    else R.drawable.ic_playlist_play_black
                }.lparams {
                    width = (parent.width * 0.20).toInt()
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    override fun getCount(): Int {
        return filmList.size
    }

    override fun getItem(position: Int): FilmInfo {
        return filmList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    fun add(film: FilmInfo) {
        filmList.add(film)
        notifyDataSetChanged()
    }

    fun swapMark(position: Int) {
        if (filmList[position].user_mark == 0)
            filmList[position].user_mark = 1
        else
            filmList[position].user_mark = 0
        notifyDataSetChanged()
    }

    fun getAll(): Films {
        return filmList
    }

    fun remove(i: Int) {
        filmList.removeAt(i)
        notifyDataSetChanged()
    }
}