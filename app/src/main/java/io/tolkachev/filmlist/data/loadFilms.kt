package io.tolkachev.filmlist.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.tolkachev.filmlist.App
import io.tolkachev.filmlist.enitity.FilmInfo
import io.tolkachev.filmlist.enitity.Films
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.Request
import java.net.URLEncoder
import kotlin.coroutines.experimental.CoroutineContext

fun loadFilmsAsync(
    context: CoroutineContext = CommonPool,
    title: String
): Deferred<Films> = async(context) {
    val encodedTitle = URLEncoder.encode(title, "utf-8")
    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/search/movie?query=$encodedTitle&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
        .build()
    val response = HttpClient.newCall(request).execute()
    val obj = JsonParser().parse(response.body()?.string())
    val text = obj.asJsonObject.get("results") ?: null
    val films: Films = Gson().fromJson(text, Films::class.java) ?: Films()
    films
}

fun loadLocalFilms(
    app: App,
    context: CoroutineContext = CommonPool
): Deferred<List<FilmInfo>> = async(context) {
    app.database.filmsDao().getAll()
}