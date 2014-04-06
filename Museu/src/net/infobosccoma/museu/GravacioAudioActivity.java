package net.infobosccoma.museu;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class GravacioAudioActivity extends Activity {
	private static final String LOG_TAG = "GravacioAudio2Activity";
	private static String mFileName = null;

	private MediaRecorder mRecorder = null;

	private MediaPlayer mPlayer = null;
	private ImageView imgVPlayStop;
	private ImageView imgVRecord;

	boolean pressedRO = true;
	boolean pressedPS = true;

	/**
	 * Constructor
	 */
	public GravacioAudioActivity() {
		mFileName = "gravacioAudio.3gp";
		File path = new File(Environment.getExternalStorageDirectory(), LOG_TAG);
		if (!path.exists())
			path.mkdirs();

		mFileName = new File(path, mFileName).getAbsolutePath();
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		imgVPlayStop = (ImageView) findViewById(R.id.imageview_play_stop);
		imgVRecord = (ImageView) findViewById(R.id.imageview_record_on_off);
		setContentView((R.layout.activity_gravar_audio));

	}

	public void onClickButtonRecord(View view) {

		// CODI HERE

		// Si no sa apretatc, false >>>
		if (pressedRO == true) {
			 startRecording();

//			imgVRecord.setImageResource(R.drawable.logo_record_on_f);
			pressedRO = false;
			// Si sa apretat, true >>>
		} else {
			 stopRecording();
			
//			imgVRecord.setImageResource(R.drawable.logo_record_off_f);
			pressedRO = true;
		}
	}

	public void onClickButtonPlay(View view) {

		// CODI HERE
		if (pressedPS==true) {
			 startPlaying();

//			imgVPlayStop.setImageResource(R.drawable.logo_play);

			pressedPS = false;
			// Si sa apretat, true >>>
		} else {
			 stopPlaying();

//			imgVPlayStop.setImageResource(R.drawable.logo_stop);
			pressedPS = true;
		}
	}

	/**
	 * Iniciar la reproducció
	 */
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	/**
	 * Aturar la reproducció
	 */
	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	/**
	 * Iniciar la gravació
	 */
	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	/**
	 * Aturar la gravació
	 */
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
	
	public void onDestroy(View view){
		finish();
	}
}