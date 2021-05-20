package me.loterio.movie.app.features.movies

import me.loterio.movie.app.core.extension.empty

data class Movie(val id: Int, val poster: String) {

    companion object {
        val empty = Movie(0, String.empty())
    }
}
