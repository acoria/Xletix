package com.example.yolo.xletix

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.acoria.unittimer.unittimer_api.timer.countDownTimer.ICountDownTimer.TimerState
import com.acoria.unittimer.unittimer_api.timer.unitTimer.IUnitTimer
import com.acoria.unittimer.unittimer_api.timer.unitTimer.IUnitTimerObserver
import com.acoria.unittimer.unittimer_api.timer.unitTimer.UnitTimer
import com.acoria.unittimer.unittimer_api.units.IUnit
import com.acoria.unittimer.unittimer_api.units.IUnitProvider
import com.example.yolo.Activities.IActivityScreen
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession
import com.example.yolo.R
import com.example.yolo.Sound.ISoundPlayer
import com.example.yolo.Sound.SoundPlayer
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionFactory
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionTypeXletix
import kotlinx.android.synthetic.main.activity_meditation.*
import kotlinx.android.synthetic.main.activity_timer_display.*

/**
 * Created by Verena on 08.05.2016.
 */
class TimerDisplayActivity : AppCompatActivity(), IUnitTimerObserver, IActivityScreen {
    private lateinit var soundPlayer: ISoundPlayer
    private val unitProvider: IUnitProvider
    private lateinit var timer: IUnitTimer
    private var lastId: String? = null
    private var infoDialogLayout: RelativeLayout? = null
    private var infoDialog: AlertDialog? = null
    private val workoutSession: IWorkoutSession
    private var totalNumberOfUnits = 0
    var currentTrainingUnit: IUnit? = null
    var remainingTime = 0

    //state of the activity
    var activityStateForRestore: CharSequence = STATE_INITIAL

    init {
//        MainUnit.setTestMode(false);
//        workoutSession = RuntimeObjectStorage.getWorkoutSession();
        workoutSession = WorkoutSessionFactory().createBySessionType(WorkoutSessionTypeXletix.barbwireBattle)
        unitProvider = workoutSession.sessionUnitProvider
        unitProvider.reset()
        createTrainingTimer(null, 0)
    }

    private fun onFabHome() {
        val intent = Intent(this, Act_InitialScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun createTrainingTimer(unitToInitializeWith: IUnit?, timeToInitializeWith: Int) {
        //once every second
        if (unitToInitializeWith == null) {
            timer = UnitTimer(unitProvider, 1)
        } else {
            timer = UnitTimer(unitProvider, 1)
            timer.initializeFromUnit(unitToInitializeWith, timeToInitializeWith)
        }
        timer.registerObserver(this)
    }

    private fun setupSound() {
        soundPlayer = SoundPlayer(this)
    }

    private fun render() {
        setUnitDisplaysFromCurrentUnit()
        toggleButtonVisibility()
    }

    private fun playSound() {
        //do not play first 2 beeps (for 2, 1 second remaining) when "exercise" == change
        if (remainingTime >= 1 && currentTrainingUnit!!.getLength() > 3 && (remainingTime == 2 || remainingTime == 1)) {
            soundPlayer.playSound(SoundPlayer.BEEP_SHORT)
        } else if (remainingTime < 1) {
            soundPlayer.playSound(SoundPlayer.BEEP_LONG)
        }
    }

    override fun onTimerInitialized(currentUnit: IUnit, unitLength: Int) {
        remainingTime = unitLength
        currentTrainingUnit = currentUnit
        render()
    }

    /***************************** Timer
     * @param remainingTime
     */
    override fun onNextImpulse(currentUnit: IUnit, remainingTime: Int) {
        activityStateForRestore = STATE_RUNNING
        this.remainingTime = remainingTime
        currentTrainingUnit = currentUnit
        render()
        playSound()
    }

    fun onTimerStopped() {
        render()
    }

    fun onTimerPaused() {
        render()
    }

    fun onTimerResumed() {
        render()
    }

    fun onTimerStarted() {
        activityStateForRestore = STATE_RUNNING // Button pressed -> change activity state to be saved for restoring bundle
        render()
        turnScreenIntoButton()
    }

    private fun turnScreenIntoButton() {
        //makes the entire screen Act_SnowboardStretches button
        timer_layout.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            timer.pauseTimer()
            false
        }
    }

    private fun backOneUnit() {
        timer.backOneUnit()
    }

    private fun skipUnit() {
        timer.skipUnit()
    }

    private fun initializeFromNewUnit(newTrainingUnit: IUnit) {
        currentTrainingUnit = newTrainingUnit
        render()
    }

    private fun toggleButtonVisibility() {
        toggleStartTimerButton()
        toggleBackSkipButtons()
        toggleInfoButton()
    }

    private fun toggleBackSkipButtons() {
        //check if back/skip button should be hidden
        if (!unitProvider.hasPredecessor()) {
            button_unit_back.visibility = View.INVISIBLE
        } else {
            button_unit_back.visibility = View.VISIBLE
        }
        if (!unitProvider.hasSuccessor()) {
            button_unit_skip.visibility = View.INVISIBLE
            next_exercise_display.visibility = View.INVISIBLE
        } else {
            button_unit_skip.visibility = View.VISIBLE
            next_exercise_display.visibility = View.VISIBLE
        }
    }

    private fun toggleStartTimerButton() {
        when (timer.getState()) {
            TimerState.STOPPED ->                 //new implementation -> is this correct?
                button_start_timer.visibility = View.INVISIBLE //hide button
            TimerState.INITIAL -> {
                button_start_timer.visibility = View.VISIBLE
                button_start_timer.setText(R.string.timer_start)
            }
            TimerState.PAUSED -> {
                button_start_timer.visibility = View.VISIBLE //show button
                button_start_timer.setText(R.string.timer_resume)
            }
            TimerState.RUNNING -> {
                button_start_timer.visibility = View.VISIBLE //show button
                button_start_timer.setText(R.string.timer_pause)
            }
        }
        if (activityStateForRestore === STATE_COMPLETED) {
            button_start_timer.visibility = View.INVISIBLE //hide button
            setTextfield("Done", exercise_display)
            timer_display.setText(R.string.timer_0)
        }
    }

    private fun toggleInfoButton() {
        try {
            val trainingUnit = unitProvider.getSneakSuccessor()
            //show info button for next exercise
            setupInfoButton(trainingUnit)
        } catch (e: ClassCastException) {
            //show info button for this exercise
            setupInfoButton(currentTrainingUnit)
        }
    }

    private fun setupInfoButton(trainingUnit: IUnit?) {
        if (trainingUnit == null) {
            return
        }
        if (trainingUnit.getInfoImage() == 0) {
            image_info.visibility = View.INVISIBLE
            image_info.setOnClickListener(null)
        } else {
            image_info.visibility = View.VISIBLE
            if (infoDialog == null) {
                infoDialogLayout = LayoutInflater.from(this).inflate(R.layout.layout_image_info, null) as RelativeLayout
                infoDialog = AlertDialog.Builder(this)
                        .setView(infoDialogLayout)
                        .create()
            }
            image_info.setOnClickListener { v: View? -> infoDialog!!.show() }
            val image = infoDialogLayout!!.findViewById<ImageView>(R.id.image_info)
            image.setImageDrawable(ContextCompat.getDrawable(activity, trainingUnit.getInfoImage()!!))
            image.setOnClickListener { v: View? -> infoDialog!!.cancel() }
        }
    }

    private fun setTextfield(text: String?, textfield: TextView?) {
        textfield!!.text = text
    }

    private fun setUnitDisplaysFromCurrentUnit() {
        //set unit
        setTextfield(currentTrainingUnit!!.getTitle(), exercise_display)

        //set preview
        if (unitProvider.hasSuccessor()) {
            val trainingUnit = unitProvider.getSneakSuccessor()
            setTextfield(trainingUnit!!.getTitle(), next_exercise_display)
        }
        //set time
//        if(remainingTime < 1){
//            //to avoid showing -1sec when screen was turned at 0:00
//            timerDisplay.setText("0:00");
//        }else{
        setTimerDisplay(remainingTime)
        //        }
    }

    private fun setTimerDisplay(remainingTime: Int) {
        val minutes = remainingTime / 60
        val seconds = remainingTime % 60
        timer_display.text = String.format("%d:%02d", minutes, seconds)
    }

    /************************ Activity states  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_display)
        title = resources.getString(R.string.xletix).toString() + " - " + workoutSession.name
        setupSound()

        button_start_timer.setOnClickListener{timer.toggleState()}
        button_unit_skip.setOnClickListener{skipUnit()}
        button_unit_back.setOnClickListener { backOneUnit() }
        fab_home.setOnClickListener { onFabHome() }

        totalNumberOfUnits = workoutSession.sessionUnitProvider.getNumberOfExercises()
    }

    //called before onStop()
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        soundPlayer.resetVolume()
        outState.putString(UNIT_ID, currentTrainingUnit!!.getId())
        outState.putInt(REMAINING_TIME, remainingTime)
        outState.putCharSequence(ACTIVITY_STATE, activityStateForRestore)
        val timerState = timer.getState()
        outState.putCharSequence(TIMER_STATE, timerState.name)
        if (timerState == TimerState.RUNNING) {
            //if timer was started, pause it
            timer.pauseTimer()
        }
    }

    //called before onStart()
    public override fun onRestoreInstanceState(inState: Bundle) {
        super.onRestoreInstanceState(inState)

        // Restore state members from saved instance
        activityStateForRestore = inState.getCharSequence(ACTIVITY_STATE)
        when (activityStateForRestore.toString()) {
            STATE_RUNNING -> {
                //set iterator to stored key
                createTrainingTimer(unitProvider.getUnitById(inState.getString(UNIT_ID)), inState.getInt(REMAINING_TIME))
            }
        }
        render()
    }

    override fun onPause() {
        super.onPause()
        timer.pauseTimer()
    }

    //called when the device is turned, see also manifest -> android:configChanges="orientation"
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //if e.g. images are used, which have to be turned, this has to be done manually here
        //since the configuration "orientation" is now handled manually
    }

    override fun onResume() {
        super.onResume()
        //        setVolumeControls();
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }

    override fun onNextImpulse(i: Int) {
        remainingTime = i
        render()
    }

    override fun onTimerPaused(timerState: TimerState) {
        setTextfield("Paused", exercise_display)
        render()
    }

    override fun onTimerResumed(timerState: TimerState) {
        render()
    }

    override fun onTimerStarted(timerState: TimerState) {
        render()
    }

    override fun onTimerStopped(timerState: TimerState) {
        setTextfield("Finished", exercise_display)
        render()
    }

    companion object {
        const val TAG = "Act_TimerDisplay"
        const val UNIT_ID = "UnitId"
        const val REMAINING_TIME = "RemainingTime"
        const val ACTIVITY_STATE = "ActivityStateForRestore"
        const val TIMER_STATE = "TimerStateForRestore"
        const val STATE_INITIAL = "INITIAL"
        const val STATE_RUNNING = "RUNNING"
        const val STATE_COMPLETED = "COMPLETED"
    }
}