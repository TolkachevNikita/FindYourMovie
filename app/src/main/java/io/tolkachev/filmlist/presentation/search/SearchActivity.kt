package io.tolkachev.filmlist.presentation.search

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.LinearLayout
import io.tolkachev.filmlist.data.loadFilmsAsync
import io.tolkachev.filmlist.enitity.Films
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.jetbrains.anko.singleLine

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Поиск"

        val films = Films()

        val linearLayout = LinearLayout(this).apply {
            backgroundColor = Color.WHITE
            orientation = LinearLayout.VERTICAL
        }
        val filmsView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchResultAdapter(films)
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        val searchFilm = EditText(this).apply {
            hint = "текст запроса"
            padding = dip(10)
            maxLines = 1
        }

        searchFilm.textChangedListener {
            var filmsJob: Job? = null
            this.afterTextChanged {
                filmsJob?.cancel()
                films.clear()
                if (it?.isBlank() == false) {
                    filmsJob = launch(UI) {
                        val asyncContext = newSingleThreadContext("loading_films")
                        val job = loadFilmsAsync(
                            asyncContext,
                            searchFilm.text.toString()
                        )
                        films.addAll(job.await())
                    }
                    filmsJob?.join()
                    filmsView.adapter.notifyDataSetChanged()
                } else {
                    filmsView.adapter.notifyDataSetChanged()
                }
            }
        }

        linearLayout.addView(searchFilm)
        linearLayout.addView(filmsView)
        setContentView(linearLayout)
    }
}
