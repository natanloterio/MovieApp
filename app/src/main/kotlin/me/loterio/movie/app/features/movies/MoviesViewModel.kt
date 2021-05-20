package me.loterio.movie.app.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.loterio.movie.app.core.interactor.UseCase.None
import me.loterio.movie.app.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    private val _movies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val movies: LiveData<List<MovieView>> = _movies

    fun loadMovies() =
        getMovies(None(), viewModelScope) { it.fold(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        _movies.value = movies.map { MovieView(it.id, it.poster) }
    }
}
