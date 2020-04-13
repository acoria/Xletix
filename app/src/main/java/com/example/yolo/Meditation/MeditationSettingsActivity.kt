package com.example.yolo.Meditation

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.yolo.GeneralConfig
import com.example.yolo.R
import com.example.yolo.YoutubeConfig
import kotlinx.android.synthetic.main.activity_meditation_settings.*

class MeditationSettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_meditation_settings)
        setTitle(R.string.titleSettings)
        txt_youtube_link.setText(getGeneralSharedPreferences().getString(YoutubeConfig.PREFERENCE_ID_YOUTUBE_MEDITATION_LINK, YoutubeConfig.URL_YOUTUBE_MEDITATION_MUSIC))
    }

    private fun getGeneralSharedPreferences(): SharedPreferences{
        return getSharedPreferences(GeneralConfig.GENERAL_SHARED_PREF_NAME, 0)
    }
}