package com.learetechno.movieapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.learetechno.movieapp.R
import com.learetechno.movieapp.preference.PreferenceProvider
import com.learetechno.movieapp.ui.intro.IntroActivity
import com.learetechno.movieapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preference = PreferenceProvider(this)
        Handler().postDelayed({
            if (preference.getStateIntro()){
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                Intent(applicationContext, IntroActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        },  3000)
    }
}
