package me.loterio.movie.app.features.login

import android.content.Context
import android.content.Intent
import me.loterio.movie.app.core.platform.BaseActivity

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()
}
