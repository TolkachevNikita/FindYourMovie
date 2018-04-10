package io.tolkachev.filmlist.data

import io.tolkachev.filmlist.App
import io.tolkachev.filmlist.enitity.FilmInfo
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun saveFilms(
    app: App,
    films: List<FilmInfo>,
    context: CoroutineContext = CommonPool
): Deferred<Unit> = async(context) {
    app.database.filmsDao().insertAll(films)
}