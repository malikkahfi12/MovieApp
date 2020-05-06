package com.example.movieapp.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.model.IntroSlider
import com.example.movieapp.preference.PreferenceProvider
import com.example.movieapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlider(
                "Stay at home",
                "Anda dapat mengakses aplikasi ini dimana saja dan kapan saja",
                R.drawable.ic_intro_1
            ),
            IntroSlider(
                "Mobile Phone",
                "Lihat movie yang sedang trend hari ini",
                R.drawable.ic_intro_2
            ),
            IntroSlider(
                "Keep Enjoy",
                "Beri rate untuk aplikasi ini untuk feedback yang lebih baik",
                R.drawable.ic_intro_3
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        intro_slider.adapter = introSliderAdapter
        val preference = PreferenceProvider(this)
        setupIndicators()
        setCurrentIndicator(0)
        intro_slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        next_slider.setOnClickListener {
            if (intro_slider.currentItem + 1 < introSliderAdapter.itemCount) {
                intro_slider.currentItem += 1
            } else {

                // set state menjadi true
                preference.setStateIntro(true)

                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

        skip_intro.setOnClickListener {

            // set state menjadi true
            preference.setStateIntro(true)

            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicator_container.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicator_container.childCount
        for (i in 0 until childCount) {
            val imageView = indicator_container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}
