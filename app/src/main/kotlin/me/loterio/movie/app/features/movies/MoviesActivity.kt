package me.loterio.movie.app.features.movies

import android.content.Context
import android.content.Intent
import me.loterio.movie.app.core.platform.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
