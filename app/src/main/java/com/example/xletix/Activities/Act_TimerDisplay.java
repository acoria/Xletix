package com.example.xletix.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xletix.FRM.Units.IUnitProvider;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.Units.ITrainingUnit;
import com.example.xletix.FRM.Units.MainUnit;
import com.example.xletix.R;
import com.example.xletix.RuntimeObjectStorage;
import com.example.xletix.Timer.ITimerObserver;
import com.example.xletix.Timer.SoundPlayer;
import com.example.xletix.Timer.TrainingTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Verena on 08.05.2016.
 */
public class Act_TimerDisplay extends AppCompatActivity implements ITimerObserver, IActivityScreen {

    static final String TAG = "Act_TimerDisplay";
    static final String UNIT_ID = "UnitId";
    static final String REMAINING_TIME = "RemainingTime";
    static final String ACTIVITY_STATE = "ActivityStateForRestore";
    static final String TIMER_STATE = "TimerStateForRestore";

    static final CharSequence STATE_INITIAL = "INITIAL";
    static final CharSequence STATE_RUNNING = "RUNNING";
    static final CharSequence STATE_COMPLETED = "COMPLETED";

    SoundPlayer soundPlayer;
    IUnitProvider unitProvider;
    TrainingTimer timer;
    ITrainingUnit currentTrainingUnit;
    boolean initialCallOfUnit;
    int remainingTime;
    String lastId;
    private RelativeLayout infoDialogLayout;
    private AlertDialog infoDialog;

    @BindView(R.id.exercise_display) TextView exerciseDisplay;
    @BindView(R.id.next_exercise_display) TextView nextExerciseDisplay;
    @BindView(R.id.time_remaining) TextView timerDisplay;

    @BindView(R.id.timer_layout) ConstraintLayout layout;

    @BindView(R.id.button_start_timer) Button btnStartTimer;
    @BindView(R.id.button_unit_skip) Button btnUnitSkip;
    @BindView(R.id.button_unit_back) Button btnUnitBack;
    @BindView(R.id.fab_home) FloatingActionButton fabHome;
    @BindView(R.id.image_info) ImageView imageInfo;
    @BindView(R.id.exercise_image) ImageView exerciseImage;


    //state of the activity
    CharSequence activityStateForRestore = STATE_INITIAL;
    CharSequence timerState;

    @OnClick(R.id.button_start_timer) void onStartTimer() {
        timerState = timer.getState();
        if (timerState == TrainingTimer.STATE_RUNNING) {
            timer.pauseTimer();
        } else if (timerState == TrainingTimer.STATE_PAUSED) {
            soundPlayer.setVolumeForTimerActivity();
            timer.resumeTimer();
        } else if (timerState == TrainingTimer.STATE_INITIAL || timerState == TrainingTimer.STATE_STOPPED) {
            soundPlayer.setVolumeForTimerActivity();
            timer.startTimer();
        } else {
            Log.e(TAG, "onResume: unexpected timer stated");
        }
    }
    @OnClick(R.id.button_unit_skip) void onButtonSkipUnit(){
        skipUnit();
    }
    @OnClick(R.id.button_unit_back) void onButtonBack(){
        backOneUnit();
    }
    @OnClick(R.id.fab_home) void onFabHome(){
        final Intent intent = new Intent(this, Act_InitialScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public Act_TimerDisplay(){
        MainUnit.setTestMode(false);
        IWorkoutSession workoutSession = RuntimeObjectStorage.getWorkoutSession();
        //this.setTitle("");
        this.unitProvider = workoutSession.getSessionUnitProvider();
        this.unitProvider.reset();
        this.timer = new TrainingTimer(1); //once every second
        this.timer.registerObserver(this);
        this.currentTrainingUnit = unitProvider.getSuccessor();
    }


    /***************************** Timer *****************************/
    public void  onNextImpulse(){
        activityStateForRestore = STATE_RUNNING;

        if(initialCallOfUnit){
            initialCallOfUnit = false;
            initializeFromNewUnit();

            remainingTime -= 1;
        }else if(remainingTime >= 1){

            setTimerDisplay(remainingTime);

            //do not play first 2 beeps (for 2, 1 second remaining) when "exercise" == change
            if(currentTrainingUnit.getLength() > 3 && (remainingTime == 2 || remainingTime == 1)){
                soundPlayer.playSound(SoundPlayer.S1);
            }
            remainingTime -= 1;

        }else if(remainingTime < 1){
            soundPlayer.playSound(SoundPlayer.S2);
            setTimerDisplay(0);

            //get next exercise
            if(unitProvider.hasSuccessor()){
                //while there is another unit
                currentTrainingUnit = unitProvider.getSuccessor();
                initialCallOfUnit = true;
            }else{
                //timer finished
                activityStateForRestore = STATE_COMPLETED;
                timer.stopTimer();
                soundPlayer.resetVolume();
            }

        }
    }
    public void onTimerStopped(){
        if(activityStateForRestore == STATE_COMPLETED){
            setActivityDisplayToCompleted();
        }
    }
    public void onTimerPaused(){
        setActivityDisplayToPaused();
    }
    public void onTimerResumed(){
        setActivityDisplayToStarted();
    }
    public void onTimerStarted(){
        activityStateForRestore = STATE_RUNNING; // Button pressed -> change activity state to be saved for restoring bundle
        initialCallOfUnit = true;

        setActivityDisplayToStarted();
        turnScreenIntoButton();
    }

    private void turnScreenIntoButton() {
        //makes the entire screen Act_SnowboardStretches button
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timer.pauseTimer();
                return false;
            }
        });
    }

    private void backOneUnit(){
        timer.stopTimer();
        if(unitProvider.hasPredecessor()){
            currentTrainingUnit = unitProvider.getPredecessor();
            initializeFromNewUnit();
            setActivityDisplayToPaused();
        }else{
            Log.e(TAG, "btn back: predecessor unexpectedly empty");
        }

    }
    private void skipUnit(){
        timer.stopTimer();
        if(unitProvider.hasSuccessor()){
            //while there is another exercise
            currentTrainingUnit = unitProvider.getSuccessor();
            initializeFromNewUnit();
            setActivityDisplayToPaused();
        }else{
            setActivityDisplayToCompleted();
        }
    }
    private void initializeFromNewUnit() {
        remainingTime = currentTrainingUnit.getLength();
        setUnitDisplaysFromCurrentUnit();

        btnStartTimer.setVisibility(View.VISIBLE); //show button

        //check if back/skip button should be hidden
        if (!unitProvider.hasPredecessor()) {
            btnUnitBack.setVisibility(View.INVISIBLE);
        } else {
            btnUnitBack.setVisibility(View.VISIBLE);
        }
        if (!unitProvider.hasSuccessor()) {
            btnUnitSkip.setVisibility(View.INVISIBLE);
            nextExerciseDisplay.setVisibility(View.INVISIBLE);
        } else {
            btnUnitSkip.setVisibility(View.VISIBLE);
            nextExerciseDisplay.setVisibility(View.VISIBLE);
        }
        if (activityStateForRestore == STATE_COMPLETED) {
            fabHome.show();
        } else {
            fabHome.hide();
        }
        if (currentTrainingUnit.getInfoImage() == 0) {
            imageInfo.setVisibility(View.INVISIBLE);
            exerciseImage.setOnClickListener(null);
        } else {
            imageInfo.setVisibility(View.VISIBLE);

            if (infoDialog == null) {
                infoDialogLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_image_info, null);
                infoDialog = new AlertDialog.Builder(this)
                        .setView(infoDialogLayout)
                        .create();
            }
            imageInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoDialog.show();
                }
            });

            ImageView image = infoDialogLayout.findViewById(R.id.image_info);
            image.setImageDrawable(ContextCompat.getDrawable(getActivity(), currentTrainingUnit.getInfoImage()));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoDialog.cancel();
                }
            });
        }
    }

    private void setActivityDisplayToInitial(){
        btnStartTimer.setVisibility(View.VISIBLE); //show button
        fabHome.hide();
        btnStartTimer.setText("Start");
    }
    private void setActivityDisplayToCompleted(){
        btnStartTimer.setVisibility(View.INVISIBLE); //hide button
        fabHome.show();
        setTextfield("Done", exerciseDisplay);
        timerDisplay.setText("0:00");
    }
    private void setActivityDisplayToPaused(){
        btnStartTimer.setVisibility(View.VISIBLE); //show button
        btnStartTimer.setText("Resume");
    }
    private void setActivityDisplayToStarted(){
        btnStartTimer.setVisibility(View.VISIBLE); //show button
        btnStartTimer.setText("Pause");
    }

    private void setTextfield(String text, TextView textfield){
        textfield.setText(text);
    }
    private void setUnitDisplaysFromCurrentUnit(){

        //set unit
        setTextfield(currentTrainingUnit.getTitle(), exerciseDisplay);

        //set preview
        if(unitProvider.hasSuccessor()){
            ITrainingUnit trainingUnit = unitProvider.getSneakSuccessor();
            setTextfield(trainingUnit.getTitle(), nextExerciseDisplay);
        }
        //set time
        if(remainingTime < 1){
            //to avoid showing -1sec when screen was turned at 0:00
            timerDisplay.setText("0:00");
        }else{
            setTimerDisplay(remainingTime);
        }
    }
    private void setTimerDisplay(int remainingTime){
        int minutes = remainingTime / 60;
        int seconds     = remainingTime % 60;
        timerDisplay.setText(String.format("%d:%02d", minutes, seconds));

    }
    private void setVolumeControls(){
        setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundPlayer = SoundPlayer.getInstance(this,am);
    }

    /************************ Activity states *************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_display);
        ButterKnife.bind(this);

		/*set button to resume when recreated
		if (savedInstanceState != null) {...}*/
        setVolumeControls();
        initializeFromNewUnit();
    }

    //called before onStop()
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        soundPlayer.resetVolume();
        outState.putString(UNIT_ID, currentTrainingUnit.getId());
        outState.putInt(REMAINING_TIME, remainingTime);
        outState.putCharSequence(ACTIVITY_STATE, activityStateForRestore);
        timerState = timer.getState();
        outState.putCharSequence(TIMER_STATE, timerState);
        if(timerState == TrainingTimer.STATE_RUNNING){ //if timer was started
            timer.pauseTimer();
        }

    }

    //called before onStart()
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        setVolumeControls();
        // Restore state members from saved instance
        lastId = inState.getString(UNIT_ID);
        remainingTime = inState.getInt(REMAINING_TIME);
        activityStateForRestore = inState.getCharSequence(ACTIVITY_STATE);
        timerState = inState.getCharSequence(TIMER_STATE);


        if(timerState == TrainingTimer.STATE_RUNNING){  //timer was running

            //set iterator to stored key
            currentTrainingUnit = unitProvider.getUnitById(lastId);
            unitProvider.resetByTrainingUnit(currentTrainingUnit);

            setActivityDisplayToPaused();
            setUnitDisplaysFromCurrentUnit();

        }else if(activityStateForRestore == STATE_COMPLETED){ //activity was finished
            setActivityDisplayToCompleted();

        }else if(activityStateForRestore == STATE_INITIAL){ //timer was not started
            setActivityDisplayToInitial();
        }else{
            Log.e(TAG,"restore:unexpected state");
        }
    }
    protected void onPause() {
        super.onPause();
        soundPlayer.resetVolume();
        if(activityStateForRestore == TrainingTimer.STATE_RUNNING){ //if timer was started
            timer.pauseTimer();
        }

    }
    @Override
    //called when the device is turned, see also manifest -> android:configChanges="orientation"
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //if e.g. images are used, which have to be turned, this has to be done manually here
        //since the configuration "orientation" is now handled manually
    }
    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControls();
    }

    @Override
    public AppCompatActivity getActivity(){
        return this;
    }

}
