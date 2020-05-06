package com.example.movieapp.preference

import android.content.Context

class PreferenceProvider(context: Context) {
    val PREFERENCE_NAME = "SharedPreference"
    val PREF_INTRO_VIEW = "IntroView"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getStateIntro():Boolean{
        return preference.getBoolean(PREF_INTRO_VIEW, false)
    }

    fun setStateIntro(state:Boolean){
        val editor = preference.edit()
        editor.putBoolean(PREF_INTRO_VIEW, state)
        editor.apply()
    }
}