package me.loterio.movie.app.features.movies

import android.content.Context
import android.content.Intent
import me.loterio.movie.app.core.platform.BaseActivity

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "INTENT_PARAM_MOVIE"

        fun callingIntent(context: Context, movie: MovieView) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_MOVIE, movie)
            }
    }

    override fun fragment() =
        MovieDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))
}
