package com.example.yolo.Springboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yolo.meditation.MeditationActivity;
import com.example.yolo.R;
import com.example.yolo.xletix.Act_InitialScreen;
import com.example.yolo.xletix.TimerDisplayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpringboardActivity extends AppCompatActivity {

    @BindView(R.id.button_xletix) ImageButton buttonXletix;
    @BindView(R.id.button_meditation) ImageButton buttonMeditation;
    @BindView(R.id.button_back_stretches) ImageButton buttonBackStretches;
    @BindView(R.id.button_snowboard_intermediate) ImageButton buttonSnowboardIntermediate;
    @BindView(R.id.button_snowboard_stretches) ImageButton buttonSnowboardStretches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_springboard);
        ButterKnife.bind(this);

        buttonXletix.setOnClickListener(v -> openActivity(Act_InitialScreen.class));
        buttonMeditation.setOnClickListener(v -> openActivity(MeditationActivity.class));
        buttonBackStretches.setOnClickListener(v -> showJediToast());
        buttonSnowboardIntermediate.setOnClickListener(v -> openActivity(TimerDisplayActivity.class));
        buttonSnowboardStretches.setOnClickListener(v -> showJediToast());
    }

    private void showJediToast() {
        Toast.makeText(this, "Patience little Jedi", Toast.LENGTH_SHORT).show();
    }

    private void openActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }
}
