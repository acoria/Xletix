package com.example.yolo.meditation

import androidx.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimer
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimerObserver
import com.example.yolo.R
import com.example.yolo.Sound.SoundPlayer
import com.example.yolo.YoutubeConfig
import kotlinx.android.synthetic.main.activity_meditation.*

class MeditationActivity : AppCompatActivity() {

    private lateinit var viewModel: MeditationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)
        setTitle(R.string.meditation)
        viewModel = MeditationViewModel(SoundPlayer(this))
        //TODO: find out why this shows as nullable
        viewModel.viewState.observe(this, Observer { render(it!!) })
        viewModel.onEvent(MeditationEvent.InitializeEvent)

        layout_meditation.setOnClickListener {
            viewModel.onEvent(MeditationEvent.LayoutClickedEvent)
        }
        button_youtube.setOnClickListener { openYouTubeMeditationMusic() }
    }

    private fun openYouTubeMeditationMusic() {
        val openWorkoutMusicIntent = Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeConfig.URL_YOUTUBE_MEDITATION_MUSIC))
        startActivity(openWorkoutMusicIntent)
    }

    private fun setupSlider(viewState: MeditationViewState) {
        timer_slider.min = viewState.timerSliderMin
        timer_slider.max = viewState.timerSliderMax
        timer_slider.progress = viewState.timerProgressPosition
        timer_slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                viewModel.onEvent(MeditationEvent.TimeChangedByProgressBarEvent(progress))
//                render()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                viewModel.onEvent(MeditationEvent.ProgressBarOnStartTrackingTouchedEvent)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.onEvent(MeditationEvent.ProgressBarOnStopTrackingTouchedEvent)
            }
        })
    }

    private fun render(viewState: MeditationViewState) {
        setTimerDisplay(viewState)
        setupSlider(viewState)
    }

    private fun setTimerDisplay(viewState: MeditationViewState) {
        if (viewState.timerDisplay != null) {
            timer_display.text = viewState.timerDisplay
        } else if (viewState.timerDisplayResource != null) {
            timer_display.setText(viewState.timerDisplayResource)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> openSettingsActivity()
        }
        return true
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, MeditationSettingsActivity::class.java))
    }
}