package com.example.xletix.Activities;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.Workouts.IWorkout;
import com.example.xletix.R;
import com.example.xletix.RuntimeObjectStorage;
import com.example.xletix.Workouts.ExerciseDetails;

public class Act_ConfigureExercises extends AppCompatActivity implements IActivityScreen {

	static final String TAG = Act_ConfigureExercises.class.getSimpleName();
	IWorkoutSession workoutSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure_exercises);

		workoutSession = RuntimeObjectStorage.getWorkoutSession();
		setTitle(getResources().getString(R.string.xletix) + " - " + workoutSession.getName());

		//pressing the volume keys on the device will affect the audio stream
		setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);

		//makes the entire screen Act_SnowboardStretches button
		ConstraintLayout layout = findViewById(R.id.exercise_layout);
		layout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showTimer();
				return false;
			}
		});

		addExerciseList();
	}

	//set the list of exercises
	public void addExerciseList(){
		ConstraintLayout constraintLayout;
		IWorkout workout;
		TextView titleView;

		constraintLayout = findViewById(R.id.const_warmup);
		workout = workoutSession.getWorkouts().get(0);
		titleView = findViewById(R.id.title_warmup);
		fillTitle(workout, titleView, "Warm up");
		addWorkoutToConstLayout(constraintLayout, titleView, workout);

		constraintLayout = findViewById(R.id.const_workout_set);
		workout = workoutSession.getWorkouts().get(1);
		titleView = findViewById(R.id.title_workout_set);
		fillTitle(workout, titleView, "Workout");
		addWorkoutToConstLayout(constraintLayout, titleView, workout);

		constraintLayout = findViewById(R.id.const_cooldown);
		workout = workoutSession.getWorkouts().get(2);
		titleView = findViewById(R.id.title_cooldown);
		fillTitle(workout, titleView, "Cool Down");
		addWorkoutToConstLayout(constraintLayout, titleView, workout);

		addWorkoutTime(workoutSession.getSessionUnitProvider().getTotalLength());
	}

	private void fillTitle(IWorkout workout, TextView textView, String title) {
		textView.setText(title + " (" + workout.getReps() + " Reps):");
	}


	private void addWorkoutToConstLayout(ConstraintLayout constLayout, View viewToAttachTo, IWorkout workout) {
		View attachToView = viewToAttachTo;
		for (ExerciseDetails trainingUnitName : workout.getUnitProvider().getTrainingUnitNames()) {
			ConstraintSet set = new ConstraintSet();
			TextView textView = new TextView(this);
			textView.setId(View.generateViewId());
			textView.setText(trainingUnitName.getName());
			textView.setPaddingRelative(0, 0, 0, 10);
			constLayout.addView(textView, 0);
			set.clone(constLayout);
			set.connect(textView.getId(), ConstraintSet.TOP, attachToView.getId(), ConstraintSet.BOTTOM);
			set.connect(textView.getId(), ConstraintSet.START, attachToView.getId(), ConstraintSet.START);
			set.applyTo(constLayout);
			attachToView = textView;
		}
	}

	private void addWorkoutToLayout(LinearLayout linearLayout, IWorkout workout) {

			for(ExerciseDetails trainingUnitName : workout.getUnitProvider().getTrainingUnitNames()){
				TextView textView = new TextView(this);
				textView.setText(trainingUnitName.getName());
				textView.setPaddingRelative(0,0,0,10);
				linearLayout.addView(textView);
		}
	}

	private void addWorkoutTime(int length) {
		double calcLengthInMinutes = (length)/60 +0.5;
		length = (int) calcLengthInMinutes;
		TextView totalTimeView = findViewById(R.id.total_time);
		totalTimeView.setText(getResources().getString(R.string.totalLength)+ " " + length + " " + getResources().getString(R.string.minutes));
	}

	//is called when the button is clicked
	//public void startTimer(View view) {
	public void showTimer() {
		Intent startExercise = new Intent(this, Act_TimerDisplay.class);
		startActivity(startExercise);
	}

	@Override
	public AppCompatActivity getActivity(){
		return this;
	}
}