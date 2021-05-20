package me.loterio.movie.app.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.loterio.movie.app.core.platform.BaseViewModel
import me.loterio.movie.app.features.movies.GetMovieDetails.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val playMovie: PlayMovie
) : BaseViewModel() {

    private val _movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()
    val movieDetails: LiveData<MovieDetailsView> = _movieDetails

    fun loadMovieDetails(movieId: Int) =
        getMovieDetails(Params(movieId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleMovieDetails
            )
        }

    fun playMovie(url: String) = playMovie(PlayMovie.Params(url), viewModelScope)

    private fun handleMovieDetails(movie: MovieDetails) {
        _movieDetails.value = MovieDetailsView(
            movie.id, movie.title, movie.poster,
            movie.summary, movie.cast, movie.director, movie.year, movie.trailer
        )
    }
}
