package com.example.yolo.meditation

import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimer

data class MeditationViewState(
        val timerDisplay: String? = null,
        val timerDisplayResource: Int? = null,
        val timerSliderMin: Int = 0,
        val timerSliderMax: Int = 0,
        val timerProgressPosition: Int = 0
)

sealed class MeditationEvent() {
    object InitializeEvent : MeditationEvent()
    object LayoutClickedEvent : MeditationEvent()
    data class TimeChangedByProgressBarEvent(val progressOfBar: Int) : MeditationEvent()
    object ProgressBarOnStartTrackingTouchedEvent : MeditationEvent()
    object ProgressBarOnStopTrackingTouchedEvent : MeditationEvent()
}

sealed class MeditationResult() {
    object InitializeResult : MeditationResult()
    object NewTimerCycleResult : MeditationResult()
    data class NewTimerStateResult(val timerState: ICountDownTimer.TimerState) : MeditationResult()
    data class ProgressBarChangedResult(val progressOfBar: Int)  : MeditationResult()
}