package me.loterio.movie.app.features.movies

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import me.loterio.movie.app.R
import me.loterio.movie.app.core.exception.Failure
import me.loterio.movie.app.core.exception.Failure.NetworkConnection
import me.loterio.movie.app.core.exception.Failure.ServerError
import me.loterio.movie.app.core.extension.*
import me.loterio.movie.app.core.platform.BaseFragment
import me.loterio.movie.app.features.movies.MovieFailure.NonExistentMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_MOVIE = "param_movie"

        fun forMovie(movie: MovieView?) = MovieDetailsFragment().apply {
            arguments = bundleOf(PARAM_MOVIE to movie)
        }
    }

    @Inject
    lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { movieDetailsAnimator.postponeEnterTransition(it) }

        with(movieDetailsViewModel) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            movieDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_MOVIE) as MovieView).id)
        } else {
            movieDetailsAnimator.scaleUpView(moviePlay)
            movieDetailsAnimator.cancelTransition(moviePoster)
            moviePoster.loadFromUrl((requireArguments()[PARAM_MOVIE] as MovieView).poster)
        }
    }

    override fun onBackPressed() {
        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        if (moviePlay.isVisible())
            movieDetailsAnimator.scaleDownView(moviePlay)
        else
            movieDetailsAnimator.cancelTransition(moviePoster)
    }

    private fun renderMovieDetails(movie: MovieDetailsView?) {
        movie?.let {
            with(movie) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
                    it.toolbar.title = title
                }
                movieSummary.text = summary
                movieCast.text = cast
                movieDirector.text = director
                movieYear.text = year.toString()
                moviePlay.setOnClickListener { movieDetailsViewModel.playMovie(trailer) }
            }
        }
        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
        movieDetailsAnimator.scaleUpView(moviePlay)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentMovie -> {
                notify(R.string.failure_movie_non_existent); close()
            }
            else -> {
                notify(R.string.failure_server_error); close()
            }
        }
    }
}
