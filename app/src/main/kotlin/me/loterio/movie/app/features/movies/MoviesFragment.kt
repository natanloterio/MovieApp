package me.loterio.movie.app.features.movies

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.loterio.movie.app.R
import me.loterio.movie.app.core.exception.Failure
import me.loterio.movie.app.core.exception.Failure.NetworkConnection
import me.loterio.movie.app.core.exception.Failure.ServerError
import me.loterio.movie.app.core.extension.failure
import me.loterio.movie.app.core.extension.invisible
import me.loterio.movie.app.core.extension.observe
import me.loterio.movie.app.core.extension.visible
import me.loterio.movie.app.core.navigation.Navigator
import me.loterio.movie.app.core.platform.BaseFragment
import me.loterio.movie.app.features.movies.MovieFailure.ListNotAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(moviesViewModel) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        moviesAdapter.clickListener = { movie, navigationExtras ->
            navigator.showMovieDetails(requireActivity(), movie, navigationExtras)
        }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        moviesViewModel.loadMovies()
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.collection = movies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
