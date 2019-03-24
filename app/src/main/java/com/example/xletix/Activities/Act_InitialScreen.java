package com.example.xletix.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.xletix.FRM.WorkoutSessions.IWorkoutSession;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionProvider;
import com.example.xletix.FRM.WorkoutSessions.WorkoutSessionIterator;
import com.example.xletix.R;
import com.example.xletix.RuntimeObjectStorage;
import com.example.xletix.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.xletix.WorkoutSessions.WorkoutSessionFactory;
import com.example.xletix.WorkoutSessions.WorkoutSessionProvider;

public class Act_InitialScreen extends AppCompatActivity {

    private boolean myWorkout = true;
    private AlertDialog infoDialog;
    private RelativeLayout infoDialogLayout;
    //    private boolean myWorkout = false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        addWorkoutSessionButtons((LinearLayout) findViewById(R.id.lay_workout_buttons));
        configureTrainingButtons();
    }

    private void configureTrainingButtons() {
        findViewById(R.id.button_training_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.trainingsplan_m);
            }
        });
        findViewById(R.id.button_training_l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.trainingsplan_l);
            }
        });
    }
    private void showTrainingplan(int imageRescource){
        if (infoDialog == null) {
            infoDialogLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_image_info, null);
            infoDialog = new AlertDialog.Builder(this)
                    .setView(infoDialogLayout)
                    .create();
        }
        infoDialog.show();
        ImageView image = infoDialogLayout.findViewById(R.id.image_info);
        image.setImageDrawable(ContextCompat.getDrawable(this, imageRescource));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.cancel();
            }
        });

    }

    private void addWorkoutSessionButtons(LinearLayout layout) {
        IWorkoutSessionFactory workoutSessionFactory;
        IWorkoutSessionProvider workoutSessionProvider;
        int index = 0;

        workoutSessionFactory = new WorkoutSessionFactory();
        workoutSessionProvider = new WorkoutSessionProvider(workoutSessionFactory);

        IWorkoutSessionIterator workoutSessionIterator = new WorkoutSessionIterator(workoutSessionProvider);

        while(workoutSessionIterator.hasNext()){
            final IWorkoutSession workoutSession = workoutSessionIterator.getNext();
            MaterialButton button = new MaterialButton(this);
            button.setText(workoutSession.getName());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onWorkoutSessionPick(workoutSession);
                }
            });
            layout.addView(button, index++);
        }
    }

    private void onWorkoutSessionPick(IWorkoutSession workoutSession){
        RuntimeObjectStorage.setWorkoutSession(workoutSession);
        showConfigureExercise();
    }
    private void showConfigureExercise(){
        startActivity(new Intent(this, Act_ConfigureExercises.class));
    }
}
