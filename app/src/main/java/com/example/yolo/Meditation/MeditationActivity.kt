package com.example.yolo.Meditation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.yolo.R
import com.example.yolo.Sound.ISoundPlayer
import com.example.yolo.Sound.SoundPlayer
import com.example.yolo.Timer.CountDownTimer.CountDownTimer
import com.example.yolo.Timer.CountDownTimer.ICountDownTimer
import com.example.yolo.Timer.CountDownTimer.ICountDownTimerObserver
import com.example.yolo.YoutubeConfig
import kotlinx.android.synthetic.main.activity_meditation.*

class MeditationActivity : AppCompatActivity(), ICountDownTimerObserver {

    private val sliderConverter = 60
    private val maxTime = 20 * 60 //20min
    private lateinit var timer: ICountDownTimer
    private lateinit var soundPlayer: ISoundPlayer
    private var remainingTime = 15 * 60 //15min

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)
        setTitle(R.string.meditation)
        layout_meditation.setOnClickListener {
            if (remainingTime > 0) {
                timer.toggleState()
            }
        }
        button_youtube.setOnClickListener { openYouTubeMeditationMusic() }
        setupTimer()
        setupSound()
        setupSlider()
        render()
    }

    private fun openYouTubeMeditationMusic() {
        val openWorkoutMusicIntent = Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeConfig.URL_YOUTUBE_MEDITATION_MUSIC))
        startActivity(openWorkoutMusicIntent)
    }

    private fun setupSlider() {
        timer_slider.min = 0
        timer_slider.max = maxTime / sliderConverter
        timer_slider.progress = (maxTime - remainingTime) / sliderConverter
        timer_slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //reversed so the max time is to the left
                remainingTime = maxTime - progress * sliderConverter
                render()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                timer.stopTimer()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                timer.initializeWithNewLength(remainingTime)
            }
        })
    }

    private fun setupSound() {
        soundPlayer = SoundPlayer(this)
    }

    private fun setupTimer() {
        val countDownTimer = CountDownTimer(1) //once every second
        countDownTimer.initializeWithNewLength(remainingTime)
        countDownTimer.registerObserver(this)
        timer = countDownTimer
    }

    private fun formatTime(remainingTime: Int): String? {
        return timer.getFormattedTime(remainingTime)
    }

    private fun render() {
        setRemainingTimeInDisplay()
        toggleText()
    }

    private fun playSound() {
        when (remainingTime) {
            0 -> soundPlayer.playSound(SoundPlayer.GONG_LONG)
            4 * 60 + 30, 9 * 60 ->                 //9min remaining
                soundPlayer.playSound(SoundPlayer.GONG_SHORT)
        }
    }

    private fun toggleText() {
        if (timer.getState() === ICountDownTimer.TimerState.PAUSED) {
            timer_display.setText(R.string.paused)
        }
    }

    override fun onNextImpulse(remainingTime: Int) {
        this.remainingTime = remainingTime
        render()
        playSound()
    }

    override fun onTimerStarted() {
        render()
    }

    override fun onTimerResumed() {
        render()
    }

    override fun onTimerPaused() {
        render()
    }

    override fun onTimerStopped() {
        //not needed?
    }

    private fun setRemainingTimeInDisplay() {
        if (remainingTime > 0) {
            timer_display.text = formatTime(remainingTime)
        } else {
            timer_display.setText(R.string.finished)
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