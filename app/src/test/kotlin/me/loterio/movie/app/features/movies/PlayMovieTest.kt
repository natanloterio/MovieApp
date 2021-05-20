package me.loterio.movie.app.features.movies

import me.loterio.movie.app.AndroidTest
import me.loterio.movie.app.core.navigation.Navigator
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PlayMovieTest : AndroidTest() {

    private lateinit var playMovie: PlayMovie

    private val context = context()

    @MockK private lateinit var navigator: Navigator

    @Before fun setUp() {
        playMovie = PlayMovie(context, navigator)
    }

    @Test fun `should play movie trailer`() {
        val params = PlayMovie.Params(VIDEO_URL)

        runBlocking { playMovie.run(params) }

        verify(exactly = 1) { navigator.openVideo(context, VIDEO_URL) }
    }

    companion object {
        private const val VIDEO_URL = "https://www.youtube.com/watch?v="
    }
}
