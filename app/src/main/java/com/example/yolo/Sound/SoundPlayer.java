package com.example.yolo.Sound;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.yolo.R;
import com.example.yolo.Sound.ISoundPlayer;

import static android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION;
import static android.media.AudioAttributes.FLAG_AUDIBILITY_ENFORCED;


public class SoundPlayer implements ISoundPlayer {

    private static final String TAG = "SoundPlayer";
	public static final int BEEP_SHORT = R.raw.beepshort;
	public static final int BEEP_LONG = R.raw.beeplong;
	public static final int GONG_LONG = R.raw.gong_long;
	public static final int GONG_SHORT = R.raw.gong_short;
    private AudioManager audioManager;
    private int initialVolume;
	
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;

	public SoundPlayer(Context context){
		initSounds(context);
        setupAudioManager(context);
        setVolumeForActivity();
	}

	@Override
	public void resetVolume(){
		audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, initialVolume,0);
	}

	@Override
	public void playSound(int soundID) {
		//if(soundPool == null || soundPoolMap == null){
		//	initSounds(context);
		//}
		// whatever in the range = 0.0 to 1.0
		float volume = 0.2f;
		// play sound with same right and left volume, with Act_SnowboardStretches priority of 1,
		// zero repeats (i.e play once), and Act_SnowboardStretches playback rate of 1f
		soundPool.play(soundPoolMap.get(soundID), volume, volume, 1, 0, 1f);
	}

	private void setupAudioManager(Context context){
		Activity activity = (Activity) context;
		activity.setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
		audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		initialVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
    }

    private void setVolumeForActivity(){
        //double reducedStreamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)*0.8;
        //int streamVolume = (int) reducedStreamVolume;
        //audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, streamVolume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),0);
    }

	
	/** Populate the SoundPool*/
	private void initSounds(Context context) {
		//arguments: simultaneous streams of 2 / type STREAM_MUSIC / sample-rate converter quality
		//soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setFlags(FLAG_AUDIBILITY_ENFORCED)
                .setContentType(CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
		//soundPool = new SoundPool(2, AudioManager.STREAM_NOTIFICATION, 100);
		soundPoolMap = new HashMap<Integer, Integer>(1);    
		soundPoolMap.put(BEEP_SHORT, soundPool.load(context, R.raw.beepshort, 1) );
		soundPoolMap.put(BEEP_LONG, soundPool.load(context, R.raw.beeplong, 1) );
		soundPoolMap.put(GONG_SHORT, soundPool.load(context, R.raw.gong_short, 1) );
		soundPoolMap.put(GONG_LONG, soundPool.load(context, R.raw.gong_long, 1) );
	}

}
