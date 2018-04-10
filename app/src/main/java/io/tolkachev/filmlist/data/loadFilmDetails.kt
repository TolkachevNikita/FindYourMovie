package io.tolkachev.filmlist.data

import com.google.gson.Gson
import io.tolkachev.filmlist.enitity.FilmDetails
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.Request
import kotlin.coroutines.experimental.CoroutineContext

fun loadFilmDetails(
    id: Int,
    context: CoroutineContext = CommonPool
): Deferred<FilmDetails> = async(context) {
    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/movie/${id}?api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
        .build()
    val response = HttpClient.newCall(request).execute()
    val text = response.body()?.string() ?: "{}"
    val details: FilmDetails = Gson().fromJson(text, FilmDetails::class.java)

    details
}
