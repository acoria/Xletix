package com.example.yolo.xletix;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yolo.Activities.IActivityScreen;
import com.example.yolo.FRM.Units.IUnitProvider;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.Units.IUnit;
import com.example.yolo.FRM.Units.MainUnit;
import com.example.yolo.R;
import com.example.yolo.Sound.ISoundPlayer;
import com.example.yolo.Timer.UnitTimer.IUnitTimer;
import com.example.yolo.Timer.UnitTimer.IUnitTimerObserver;
import com.example.yolo.Sound.SoundPlayer;
import com.example.yolo.Timer.UnitTimer.UnitTimer;
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionFactory;
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionTypeXletix;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Verena on 08.05.2016.
 */
public class Act_TimerDisplay extends AppCompatActivity implements IUnitTimerObserver, IActivityScreen {

    static final String TAG = "Act_TimerDisplay";
    static final String UNIT_ID = "UnitId";
    static final String REMAINING_TIME = "RemainingTime";
    static final String ACTIVITY_STATE = "ActivityStateForRestore";
    static final String TIMER_STATE = "TimerStateForRestore";

    static final String STATE_INITIAL = "INITIAL";
    static final String STATE_RUNNING = "RUNNING";
    static final String STATE_COMPLETED = "COMPLETED";

    private ISoundPlayer soundPlayer;
    private IUnitProvider unitProvider;
    private IUnitTimer timer;
    private String lastId;
    private RelativeLayout infoDialogLayout;
    private AlertDialog infoDialog;
    private IWorkoutSession workoutSession;
    private int totalNumberOfUnits;

    IUnit currentTrainingUnit;
    int remainingTime;


    @BindView(R.id.exercise_display) TextView exerciseDisplay;
    @BindView(R.id.next_exercise_display) TextView nextExerciseDisplay;
    @BindView(R.id.time_remaining) TextView timerDisplay;

    @BindView(R.id.timer_layout) ConstraintLayout layout;

    @BindView(R.id.button_start_timer) Button btnStartTimer;
    @BindView(R.id.button_unit_skip) Button btnUnitSkip;
    @BindView(R.id.button_unit_back) Button btnUnitBack;
    @BindView(R.id.fab_home) FloatingActionButton fabHome;
    @BindView(R.id.image_info) ImageView imageInfo;


    //state of the activity
    CharSequence activityStateForRestore = STATE_INITIAL;

    @OnClick(R.id.button_start_timer) void onStartTimer() {
        timer.toggleState();
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
//        workoutSession = RuntimeObjectStorage.getWorkoutSession();
        workoutSession = new WorkoutSessionFactory().createBySessionType(WorkoutSessionTypeXletix.barbwireBattle);

        this.unitProvider = workoutSession.getSessionUnitProvider();
        this.unitProvider.reset();
        createTrainingTimer(null, 0);
    }

    private void createTrainingTimer(IUnit unitToInitializeWith, int timeToInitializeWith){
        //once every second
        if(unitToInitializeWith == null) {
            timer = new UnitTimer(1, unitProvider);
            timer.initialize();
        }else{
            timer = new UnitTimer(1, unitProvider);
            timer.initializeFromUnit(unitToInitializeWith, timeToInitializeWith);
        }
        timer.registerObserver(this);
    }

    private void setupSound() {
        soundPlayer = new SoundPlayer(this);
    }

    private void render(){
        setUnitDisplaysFromCurrentUnit();
        toggleButtonVisibility();
    }
    private void playSound(){
        //do not play first 2 beeps (for 2, 1 second remaining) when "exercise" == change
        if(remainingTime >=1 && (currentTrainingUnit.getLength() > 3 && (remainingTime == 2 || remainingTime == 1))){
            soundPlayer.playSound(SoundPlayer.BEEP_SHORT);
        }else if(remainingTime < 1){
            soundPlayer.playSound(SoundPlayer.BEEP_LONG);
        }
    }

    @Override
    public void onTimerInitialized(IUnit currentUnit, int unitLength) {
        this.remainingTime = unitLength;
        this.currentTrainingUnit = currentUnit;
        render();
    }

    /***************************** Timer
     * @param remainingTime*****************************/
    public void onNextImpulse(IUnit currentUnit, int remainingTime) {
        activityStateForRestore = STATE_RUNNING;
        this.remainingTime = remainingTime;
        this.currentTrainingUnit = currentUnit;

        render();
        playSound();
    }
    public void onTimerStopped(){
        render();
    }
    public void onTimerPaused(){
        render();
    }
    public void onTimerResumed(){
        render();
    }
    public void onTimerStarted(){
        activityStateForRestore = STATE_RUNNING; // Button pressed -> change activity state to be saved for restoring bundle
        render();
        turnScreenIntoButton();
    }

    @Override
    public void onTimerFinished() {
        activityStateForRestore = STATE_COMPLETED;
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
        timer.backOneUnit();
    }
    private void skipUnit(){
        timer.skipUnit();
    }
    private void initializeFromNewUnit(IUnit newTrainingUnit) {
        currentTrainingUnit = newTrainingUnit;
        render();
    }

    private void toggleButtonVisibility() {
        toggleStartTimerButton();
        toggleBackSkipButtons();
        toggleInfoButton();
    }

    private void toggleBackSkipButtons() {
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
    }

    private void toggleStartTimerButton() {
        switch(timer.getState()){
            case STOPPED:
                //new implementation -> is this correct?
                btnStartTimer.setVisibility(View.INVISIBLE); //hide button
                break;
            case INITIAL:
                btnStartTimer.setVisibility(View.VISIBLE);
                btnStartTimer.setText(R.string.timer_start);
                break;
            case PAUSED:
                btnStartTimer.setVisibility(View.VISIBLE); //show button
                btnStartTimer.setText(R.string.timer_resume);
                break;
            case RUNNING:
                btnStartTimer.setVisibility(View.VISIBLE); //show button
                btnStartTimer.setText(R.string.timer_pause);
                break;
        }
        if(activityStateForRestore == STATE_COMPLETED){
            btnStartTimer.setVisibility(View.INVISIBLE); //hide button
            setTextfield("Done", exerciseDisplay);
            timerDisplay.setText(R.string.timer_0);
        }
    }


    private void toggleInfoButton() {
        try{
            IUnit trainingUnit = unitProvider.getSneakSuccessor();
            //show info button for next exercise
            setupInfoButton(trainingUnit);
        }catch (ClassCastException e){
            //show info button for this exercise
            setupInfoButton(currentTrainingUnit);
        }
    }

    private void setupInfoButton(IUnit trainingUnit) {
        if(trainingUnit == null){return;}
        if (trainingUnit.getInfoImage() == 0) {
            imageInfo.setVisibility(View.INVISIBLE);
            imageInfo.setOnClickListener(null);
        } else {
            imageInfo.setVisibility(View.VISIBLE);

            if (infoDialog == null) {
                infoDialogLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_image_info, null);
                infoDialog = new AlertDialog.Builder(this)
                        .setView(infoDialogLayout)
                        .create();
            }
            imageInfo.setOnClickListener((v) -> infoDialog.show());

            ImageView image = infoDialogLayout.findViewById(R.id.image_info);
            image.setImageDrawable(ContextCompat.getDrawable(getActivity(), trainingUnit.getInfoImage()));
            image.setOnClickListener((v) -> infoDialog.cancel());
        }
    }

    private void setTextfield(String text, TextView textfield){
        textfield.setText(text);
    }
    private void setUnitDisplaysFromCurrentUnit(){
        //set unit
        setTextfield(currentTrainingUnit.getTitle(), exerciseDisplay);

        //set preview
        if(unitProvider.hasSuccessor()){
            IUnit trainingUnit = unitProvider.getSneakSuccessor();
            setTextfield(trainingUnit.getTitle(), nextExerciseDisplay);
        }
        //set time
//        if(remainingTime < 1){
//            //to avoid showing -1sec when screen was turned at 0:00
//            timerDisplay.setText("0:00");
//        }else{
            setTimerDisplay(remainingTime);
//        }
    }
    private void setTimerDisplay(int remainingTime){
        int minutes = remainingTime / 60;
        int seconds     = remainingTime % 60;
        timerDisplay.setText(String.format("%d:%02d", minutes, seconds));

    }

    /************************ Activity states *************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_display);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.xletix) + " - " + workoutSession.getName());
        setupSound();
        totalNumberOfUnits = workoutSession.getSessionUnitProvider().getNumberOfExercises();
    }

    //called before onStop()
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        soundPlayer.resetVolume();
        outState.putString(UNIT_ID, currentTrainingUnit.getId());
        outState.putInt(REMAINING_TIME, remainingTime);
        outState.putCharSequence(ACTIVITY_STATE, activityStateForRestore);
        UnitTimer.TimerState timerState = timer.getState();
        outState.putCharSequence(TIMER_STATE, timerState.getStateName());
        if(timerState == UnitTimer.TimerState.RUNNING){
            //if timer was started, pause it
            timer.pauseTimer();
        }
    }

    //called before onStart()
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // Restore state members from saved instance
        activityStateForRestore = inState.getCharSequence(ACTIVITY_STATE);
//        _CountDownTimer.TimerState timerState = _CountDownTimer.TimerState.findStateByName(inState.getCharSequence(TIMER_STATE).toString());

        switch (activityStateForRestore.toString()){
            case STATE_RUNNING:
                //set iterator to stored key
                lastId = inState.getString(UNIT_ID);
                createTrainingTimer(unitProvider.getUnitById(lastId), inState.getInt(REMAINING_TIME));
                break;
        }
        render();
    }
    protected void onPause() {
        super.onPause();
        timer.pauseTimer();
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
//        setVolumeControls();
    }

    @Override
    public AppCompatActivity getActivity(){
        return this;
    }

}
