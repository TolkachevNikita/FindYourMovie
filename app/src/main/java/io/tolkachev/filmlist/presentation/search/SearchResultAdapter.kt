package io.tolkachev.filmlist.presentation.search

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.tolkachev.filmlist.presentation.details.DetailsActivity
import io.tolkachev.filmlist.presentation.details.DetailsView
import io.tolkachev.filmlist.enitity.Films
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.selector

class SearchResultAdapter(
    private val films: Films
) : RecyclerView.Adapter<ResultItemViewHolder>() {

    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemViewHolder {
        val view = DetailsView(parent.context)
        val width = RecyclerView.LayoutParams.MATCH_PARENT
        val height = parent.dip(100)
        view.layoutParams = RecyclerView.LayoutParams(width, height)
        return ResultItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultItemViewHolder, position: Int) {
        val filmView = holder.view
        val film = films[position]
        filmView.bind(film)
        filmView.onClick {
            val context = it?.context
            val showFilmIntent = Intent(context, DetailsActivity::class.java)
            showFilmIntent.putExtra("film_id", film.id)
            context?.startActivity(showFilmIntent)
        }
        filmView.onLongClick {
            val resultIntent = Intent()
            resultIntent.putExtra("film_id", film.id)
            val activity = filmView.context as Activity
            activity.setResult(Activity.RESULT_OK, resultIntent)
            activity.finish()
        }
    }
}