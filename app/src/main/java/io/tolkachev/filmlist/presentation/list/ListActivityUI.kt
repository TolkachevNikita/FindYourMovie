package io.tolkachev.filmlist.presentation.list

import android.view.View
import android.widget.ListView
import io.tolkachev.filmlist.App
import io.tolkachev.filmlist.data.saveFilms
import io.tolkachev.filmlist.presentation.search.SearchActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alignParentBottom
import org.jetbrains.anko.alignParentEnd
import org.jetbrains.anko.alignParentRight
import org.jetbrains.anko.button
import org.jetbrains.anko.centerInParent
import org.jetbrains.anko.dip
import org.jetbrains.anko.listView
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange
import org.jetbrains.anko.sdk25.coroutines.onItemLongClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class ListActivityUI(
    private val filmAdapter: FilmsAdapter
) : AnkoComponent<ListActivity> {

    override fun createView(ui: AnkoContext<ListActivity>) = with(ui) {
        relativeLayout {
            var filmList: ListView? = null

            val emptyListView = textView("Список фильмов пуст") {
                textSize = 23f
            }.lparams {
                centerInParent()
            }

            fun toggelEmptyListView(listView: ListView?) {
                emptyListView.visibility = if (listView?.adapter?.count ?: 0 > 0) View.GONE
                else View.VISIBLE
            }

            verticalLayout {
                lparams(width = matchParent)
                filmList = listView {
                    adapter = filmAdapter
                    onFocusChange { _, hasFocus ->
                        if (hasFocus) toggelEmptyListView(filmList)
                    }
                    onItemLongClick { _, _, i, _ ->
                        val film = filmAdapter.getItem(i)
                        val options: List<String> =
                            if (film.user_mark == 0) listOf("Просмотрен", "Удалить")
                            else listOf("Не просмотрен", "Удалить")
                        selector("Film options", options) { _, position ->
                            if (position == 1) {
                                filmAdapter.remove(i)
                                saveFilms(ui.owner.application as App, filmAdapter.getAll())
                                toggelEmptyListView(filmList)
                            } else {
                                filmAdapter.swapMark(i)
                                saveFilms(ui.owner.application as App, filmAdapter.getAll())
                            }
                        }
                        true
                    }
                }.lparams {
                    margin = dip(5)
                    width = matchParent
                }
            }

            button {
                text = "Добавить фильм"
                onClick {
                    ui.owner.startActivityForResult<SearchActivity>(ui.owner.pickFilm)
                }
            }.lparams(matchParent) {
                margin = dip(10)
                alignParentBottom()
            }
        }
    }
}