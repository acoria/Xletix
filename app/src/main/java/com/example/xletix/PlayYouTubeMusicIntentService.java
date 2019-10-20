package com.example.xletix;

import android.app.IntentService;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PlayYouTubeMusicIntentService extends IntentService {
    private static final String TAG = PlayYouTubeMusicIntentService.class.getSimpleName();

    public PlayYouTubeMusicIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent openWorkoutMusicIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeConfig.URL_YOUTUBE_WORKOUT_MUSIC));
        openWorkoutMusicIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(openWorkoutMusicIntent);
    }
}
