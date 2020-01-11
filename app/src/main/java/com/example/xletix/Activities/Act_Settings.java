package com.example.xletix.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


import com.example.xletix.GeneralConfig;
import com.example.xletix.R;
import com.example.xletix.YoutubeConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_Settings extends AppCompatActivity {

    @BindView(R.id.txt_youtube_link) EditText youTubeLink;
    @OnClick(R.id.btn_update_youtube_link) void onUpdateYouTubeLink(){
        getGeneralSharedPreferences().edit().putString(YoutubeConfig.PREFERENCE_ID,youTubeLink.getText().toString()).commit();
    }

    private SharedPreferences getGeneralSharedPreferences() {
        return getSharedPreferences(GeneralConfig.GENERAL_SHARED_PREF_NAME,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_settings);
        setTitle(R.string.titleSettings);
        ButterKnife.bind(this);
        youTubeLink.setText(getGeneralSharedPreferences().getString(YoutubeConfig.PREFERENCE_ID, YoutubeConfig.URL_YOUTUBE_WORKOUT_MUSIC));
    }
}