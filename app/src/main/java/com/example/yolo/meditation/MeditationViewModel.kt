package com.example.yolo.meditation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.CountDownTimer
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimer
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimerObserver
import com.example.yolo.Lce
import com.example.yolo.R
import com.example.yolo.Sound.ISoundPlayer
import com.example.yolo.Sound.SoundPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MeditationViewModel(private val soundPlayer: ISoundPlayer) : ViewModel(), ICountDownTimerObserver {

    private val sliderConverter = 60
    private val maxTime = 20 * 60 //20min
    private lateinit var timer: ICountDownTimer
    private var remainingTime = 15 * 60 //15min

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _viewStateLiveData = MutableLiveData<MeditationViewState>()

    val viewState: LiveData<MeditationViewState>
        get() = _viewStateLiveData

    private var currentViewState = MeditationViewState()
        set(value) {
            field = value
            _viewStateLiveData.value = value
        }

    init {
        setupTimer()
    }

    fun onEvent(event: MeditationEvent) {
        when (event) {
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
            is MeditationEvent.InitializeEvent -> resultToViewState(Lce.Content(MeditationResult.InitializeResult))
        }
    }

    private fun onTimeChangedByProgressBarEvent(progressOfBar: Int) {
        //reversed so the max time is to the left
        setRemainingTime(maxTime - progressOfBar * sliderConverter)
        resultToViewState(Lce.Content(MeditationResult.ProgressBarChangedResult(progressOfBar)))
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

    override fun onNextImpulse(remainingTime: Int) {
        uiScope.launch {
            setRemainingTime(remainingTime)
            resultToViewState(Lce.Content(MeditationResult.NewTimerCycleResult))
            playSound()
        }
    }

    private fun setRemainingTime(remainingTime: Int) {
        this.remainingTime = remainingTime
    }

    override fun onTimerPaused(timerState: ICountDownTimer.TimerState) {
        resultToViewState(Lce.Content(MeditationResult.NewTimerStateResult(timerState)))
    }

    override fun onTimerResumed(timerState: ICountDownTimer.TimerState) {
        resultToViewState(Lce.Content(MeditationResult.NewTimerStateResult(timerState)))
    }

    override fun onTimerStarted(timerState: ICountDownTimer.TimerState) {
        resultToViewState(Lce.Content(MeditationResult.NewTimerStateResult(timerState)))
    }

    override fun onTimerStopped(timerState: ICountDownTimer.TimerState) {
        resultToViewState(Lce.Content(MeditationResult.NewTimerStateResult(timerState)))
    }

    private fun resultToViewState(result: Lce<out MeditationResult>) {
        currentViewState = when (result) {
            is Lce.Content ->
                when (result.content) {
                    is MeditationResult.NewTimerCycleResult ->
                        currentViewState.copy(
                                timerDisplay = getTimerDisplayByRemainingTime(),
                                timerDisplayResource = getTimerDisplayResourceByRemainingTime()
                        )
                    is MeditationResult.NewTimerStateResult ->
                        currentViewState.copy(
                                timerDisplay = getTimerDisplayByTimerState(result.content.timerState),
                                timerDisplayResource = getTimerDisplayResourceByTimerState(result.content.timerState)
                        )
                    is MeditationResult.ProgressBarChangedResult ->
                        currentViewState.copy(
                                timerDisplay = getTimerDisplayByRemainingTime(),
                                timerDisplayResource = getTimerDisplayResourceByRemainingTime(),
                                timerProgressPosition = getTimerProgressPosition()
                        )
                    is MeditationResult.InitializeResult ->
                        currentViewState.copy(
                                timerDisplay = getTimerDisplayByRemainingTime(),
                                timerDisplayResource = getTimerDisplayResourceByRemainingTime(),
                                timerSliderMax = maxTime / sliderConverter,
                                timerProgressPosition = getTimerProgressPosition()
                        )
                }
            else -> throw NotImplementedError()
        }
    }

    private fun getTimerProgressPosition() = (maxTime - remainingTime) / sliderConverter

    private fun getTimerDisplayResourceByRemainingTime(): Int? {
        if (remainingTime == 0) {
            return R.string.finished
        }
        return null
    }

    private fun getTimerDisplayResourceByTimerState(timerState: ICountDownTimer.TimerState): Int? {
        if (timerState === ICountDownTimer.TimerState.PAUSED) {
            return R.string.paused
        }
        return null
    }

    private fun getTimerDisplayByTimerState(timerState: ICountDownTimer.TimerState): String? {
        if (timerState !== ICountDownTimer.TimerState.PAUSED) {
            return formatTime(remainingTime)
        }
        return null
    }

    private fun getTimerDisplayByRemainingTime(): String? {
        if (remainingTime > 0) {
            return formatTime(remainingTime)
        }
        return null
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}