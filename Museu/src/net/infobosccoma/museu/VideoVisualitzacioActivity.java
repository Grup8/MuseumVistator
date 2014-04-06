package net.infobosccoma.museu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class VideoVisualitzacioActivity extends Activity implements SurfaceHolder.Callback {

	private String pathVideo;

	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_video_reproduir);

		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Intent intent = getIntent();
		pathVideo = intent.getStringExtra("PathVideo");

		Toast.makeText(this, pathVideo, Toast.LENGTH_LONG).show();
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		 alertDialog.setMessage(pathVideo);
		 alertDialog.create();
		 alertDialog.show();
		// VideoView myVideoView = (VideoView)findViewById(R.id.v_camara);
		// myVideoView.setVideoPath(pathVideo);

		// myVideoView.setMediaController(new MediaController(this));
		// myVideoView.setVideoPath(pathVideo);
		// myVideoView.requestFocus();
		// myVideoView.start();

	}

	private void playVideo() {
		// doCleanUp();
		try {

			/*
			 * TODO: Set the path variable to a local media file path.
			 */
			// path = "/sdcard/sample.mov";
			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(pathVideo);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepare();
			mMediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

				@Override
				public void onBufferingUpdate(MediaPlayer mp, int percent) {
					Log.d("AudioVideo", "onBufferingUpdate percent:" + percent);

				}
			});
			// mMediaPlayer.setOnCompletionListener(this);
			// mMediaPlayer.setOnPreparedListener(this);
			// mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			
			mMediaPlayer.start();
			if (!mMediaPlayer.isPlaying()){
				mMediaPlayer.stop();
			}

		} catch (Exception e) {
			Log.e("AudioVideo", "error: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	
	@Override
	protected void onResume() {
		super.onResume();

		// Toast.makeText(this, "HOLAAAAA", Toast.LENGTH_LONG).show();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		playVideo();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
