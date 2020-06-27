package com.example.yolo.meditation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.CountDownTimer
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimer
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimerObserver
import com.example.yolo.R
import com.example.yolo.Sound.ISoundPlayer
import com.example.yolo.Sound.SoundPlayer

class MeditationViewModel(private val soundPlayer: ISoundPlayer): ViewModel(), ICountDownTimerObserver {

    private val sliderConverter = 60
    private val maxTime = 20 * 60 //20min
    private lateinit var timer: ICountDownTimer
    private var remainingTime = 15 * 60 //15min

    init {
        setupTimer()
    }

    private var _viewStateLiveData = MutableLiveData<MeditationViewState>()

    val viewState: LiveData<MeditationViewState>
        get() = _viewStateLiveData

    private var currentViewState =
            MeditationViewState(timerSliderMax = maxTime / sliderConverter, timerProgressPosition = (maxTime - remainingTime) / sliderConverter)
        set(value) {
            field = value
            _viewStateLiveData.value = value
        }

    fun onEvent(event: MeditationEvent){
        when(event){
            is MeditationEvent.LayoutClickedEvent -> {
                onLayoutClicked()
            }
            is MeditationEvent.ProgressBarOnStartTrackingTouchedEvent -> {
                onProgressBarOnStartTrackingTouchedEvent()
            }
            is MeditationEvent.ProgressBarOnStopTrackingTouchedEvent -> {
                onProgressBarOnStopTrackingTouchedEvent()
            }
            is MeditationEvent.TimeChangedByProgressBarEvent -> {
                onTimeChangedByProgressBarEvent(event.progressOfBar)
            }
        }
    }

    private fun onTimeChangedByProgressBarEvent(progressOfBar: Int) {
        //reversed so the max time is to the left
        remainingTime = maxTime - progressOfBar * sliderConverter
        //TODO: Result
    }

    private fun onProgressBarOnStopTrackingTouchedEvent() {
        timer.initializeWithNewLength(remainingTime)
    }

    private fun onProgressBarOnStartTrackingTouchedEvent() {
        timer.stopTimer()
    }

    private fun onLayoutClicked() {
        if (remainingTime > 0) {
            timer.toggleState()
        }
    }

    private fun setupTimer() {
        val countDownTimer = CountDownTimer(1) //once every second
        countDownTimer.initializeWithNewLength(remainingTime)
        countDownTimer.registerObserver(this)
        timer = countDownTimer
    }

    private fun formatTime(remainingTime: Int): String {
        return let { timer.getFormattedTime(remainingTime) } ?: ""
    }

    private fun playSound() {
        when (remainingTime) {
            0 -> soundPlayer.playSound(SoundPlayer.GONG_LONG)
            4 * 60 + 30, 9 * 60 ->                 //9min remaining
                soundPlayer.playSound(SoundPlayer.GONG_SHORT)
        }
    }

    //TODO: will no longer be needed when state is created
    fun determineTimerText(): String{
        if (remainingTime > 0) {
            if(timer.getState() === ICountDownTimer.TimerState.PAUSED){
                return R.string.paused.toString()
            }else{
                return formatTime(remainingTime)
            }
        } else {
            return R.string.finished.toString()
        }
    }

    override fun onNextImpulse(remainingTime: Int) {
        this.remainingTime = remainingTime
        //TODO: Result
        playSound()
    }

    override fun onTimerPaused(timerState: ICountDownTimer.TimerState) {
        //TODO: Result
    }

    override fun onTimerResumed(timerState: ICountDownTimer.TimerState) {
        //TODO: Result
    }

    override fun onTimerStarted(timerState: ICountDownTimer.TimerState) {
        //TODO: Result
    }

    override fun onTimerStopped(timerState: ICountDownTimer.TimerState) {
        //not needed?
    }
}