package com.example.yolo.meditation

data class MeditationViewState(
        val timerDisplay: String? = null,
        val timerSliderMin: Int = 0,
        val timerSliderMax: Int = 0,
        val timerProgressPosition: Int = 0
)

sealed class MeditationEvent() {
    object LayoutClickedEvent : MeditationEvent()
    data class TimeChangedByProgressBarEvent(val progressOfBar: Int) : MeditationEvent()
    object ProgressBarOnStartTrackingTouchedEvent : MeditationEvent()
    object ProgressBarOnStopTrackingTouchedEvent : MeditationEvent()
}