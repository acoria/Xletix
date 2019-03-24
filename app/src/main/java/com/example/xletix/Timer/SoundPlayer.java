package com.example.xletix.Timer;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.example.xletix.R;

import static android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION;
import static android.media.AudioAttributes.FLAG_AUDIBILITY_ENFORCED;


public class SoundPlayer {

    private static final String TAG = "SoundPlayer";
	public static final int S1 = R.raw.beepshort;
	public static final int S2 = R.raw.beeplong;
    private static SoundPlayer soundPlayer;
    private Context context;
    private AudioManager audioManager;
    private int initialVolume;
	
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;

	private SoundPlayer(Context context, AudioManager audioManager){
        this.context = context;
        this.audioManager = audioManager;
		initSounds();
        setupAudioManager();
	}
	public static SoundPlayer getInstance(Context context, AudioManager audioManager){
        if(soundPlayer == null) {
            soundPlayer = new SoundPlayer(context,audioManager);
        }else if(soundPlayer.context != context){
            Log.e(TAG,"SoundPlayer is only implemented to handle one context");
        }
        return soundPlayer;
	}

	private void setupAudioManager(){
        this.initialVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
    }

    public void setVolumeForTimerActivity(){
        //double reducedStreamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)*0.8;
        //int streamVolume = (int) reducedStreamVolume;
        //audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, streamVolume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),0);
    }
    public void resetVolume(){
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, initialVolume,0);
    }
	
	/** Populate the SoundPool*/
	private void initSounds() {
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
		soundPoolMap.put( S1, soundPool.load(this.context, R.raw.beepshort, 1) );
		soundPoolMap.put( S2, soundPool.load(this.context, R.raw.beeplong, 1) );
	
	}
	
	/** Play Act_SnowboardStretches given sound in the soundPool */
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
}
