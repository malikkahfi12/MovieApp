package com.example.movieapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.model.IntroSlider
import com.example.movieapp.preference.PreferenceProvider
import com.example.movieapp.ui.intro.IntroActivity
import com.example.movieapp.ui.main.MainActivity

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
