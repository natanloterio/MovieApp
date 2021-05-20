package me.loterio.movie.app.features.movies

import me.loterio.movie.app.core.interactor.UseCase
import me.loterio.movie.app.features.movies.GetMovieDetails.Params
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieDetails, Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)

    data class Params(val id: Int)
}
