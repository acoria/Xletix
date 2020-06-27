package com.example.yolo.xletix;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yolo.FRM.WorkoutSessions.IWorkoutSession;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionIterator;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionProvider;
import com.example.yolo.FRM.WorkoutSessions.WorkoutSessionIterator;
import com.example.yolo.YoutubeConfig;
import com.example.yolo.R;
import com.example.yolo.RuntimeObjectStorage;
import com.example.yolo.FRM.WorkoutSessions.IWorkoutSessionFactory;
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionFactory;
import com.example.yolo.xletix.WorkoutSessions.WorkoutSessionProvider;

public class Act_InitialScreen extends AppCompatActivity {

    private boolean myWorkout = true;
    private AlertDialog infoDialog;
    private RelativeLayout infoDialogLayout;
    //    private boolean myWorkout = false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        setTitle(R.string.xletix);
        addWorkoutSessionButtons((LinearLayout) findViewById(R.id.lay_workout_buttons));
        configureButtons();
    }

    private void configureButtons() {
        findViewById(R.id.button_training_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.trainingsplan_s, R.string.trainingsplan_s);
            }
        });
        findViewById(R.id.button_training_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.trainingsplan_m, R.string.trainingsplan_m);
            }
        });
        findViewById(R.id.button_training_l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.trainingsplan_l, R.string.trainingsplan_l);
            }
        });
        findViewById(R.id.button_ausdauer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrainingplan(R.drawable.ausdauer, R.string.ausdauer);
            }
        });
        findViewById(R.id.button_youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeApp();
            }
        });
        findViewById(R.id.button_youtube_workout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeWorkoutMusic();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings :
                openSettingsActivity();
                break;
        }
        return true;
    }

    private void openSettingsActivity() {
        startActivity(new Intent(this, Act_Settings.class));
    }

    private void openYouTubeWorkoutMusic() {
        Intent openWorkoutMusicIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeConfig.URL_YOUTUBE_WORKOUT_MUSIC));
        startActivity(openWorkoutMusicIntent);
//        startService(new Intent(this, PlayYouTubeMusicIntentService.class));
    }

    private void openYouTubeApp() {
        Intent openYouTubeAppIntent = getPackageManager().getLaunchIntentForPackage(YoutubeConfig.PACKAGE_NAME_YOUTUBE_MUSIC);
        if(openYouTubeAppIntent != null){
            startActivity(openYouTubeAppIntent);
        }else{
            Toast.makeText(this, "Error when trying to open YouTubeMusic app", Toast.LENGTH_SHORT).show();
        }

    }

    private void showTrainingplan(int imageRescource, int title){
        if (infoDialog == null) {
            infoDialogLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_image_info, null);
            infoDialog = new AlertDialog.Builder(this)
                    .setView(infoDialogLayout)
                    .create();
        }
        infoDialog.setTitle(title);
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
