package me.loterio.movie.app.features.movies

import android.content.Context
import me.loterio.movie.app.core.exception.Failure
import me.loterio.movie.app.core.functional.Either
import me.loterio.movie.app.core.functional.Either.Right
import me.loterio.movie.app.core.interactor.UseCase
import me.loterio.movie.app.core.interactor.UseCase.None
import me.loterio.movie.app.core.navigation.Navigator
import me.loterio.movie.app.features.movies.PlayMovie.Params
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlayMovie
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator
) : UseCase<None, Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        navigator.openVideo(context, params.url)
        return Right(None())
    }

    data class Params(val url: String)
}
