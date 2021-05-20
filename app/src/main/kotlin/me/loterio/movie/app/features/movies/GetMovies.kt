package me.loterio.movie.app.features.movies

import me.loterio.movie.app.core.interactor.UseCase
import me.loterio.movie.app.core.interactor.UseCase.None
import javax.inject.Inject

class GetMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, None>() {

    override suspend fun run(params: None) = moviesRepository.movies()
}
