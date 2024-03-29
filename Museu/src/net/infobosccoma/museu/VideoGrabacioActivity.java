package net.infobosccoma.museu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

enum Estat {
	GRAVANT, ATURAT
};

public class VideoGrabacioActivity extends Activity {

	private Button btnGravar;
	private MediaRecorder gravador;
	private Camera camera;
	private PrevisualitzacioCamera prev;
	private Estat estatGravacio;
	private String pathFinal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_video_grabacio);

		btnGravar = (Button) findViewById(R.id.btnGravar);
		btnGravar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.btnGravar) {
					if (estatGravacio == Estat.ATURAT) {
						prepararGravacio();
						gravador.start();
						btnGravar.setText(R.string.btnAturarGravacio);
						estatGravacio = Estat.GRAVANT;
					} else {
						aturarGravacio();
						btnGravar.setText(R.string.btnIniciarGravacio);
						estatGravacio = Estat.ATURAT;
					}
				}

			}
		});

		if (teCamera()) {
			camera = getCamera();
			setCameraDisplayOrientation(this, Camera.CameraInfo.CAMERA_FACING_BACK, camera);
			if (camera != null) {
				prev = new PrevisualitzacioCamera(this, camera);
				FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview);
				frameLayout.addView(prev);
			}
		} else {
			// Dialeg.mostrarDialeg(this,
			// getApplicationInfo().name,"El dispositiu no t� c�mera");
		}

		estatGravacio = Estat.ATURAT;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	/*
	 * Comprova si el dispositiu t� c�mera
	 */
	private boolean teCamera() {
		if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Obtenir i obrir l'objecte Camera
	 */
	public Camera getCamera() {
		Camera c = null;

		try {
			c = Camera.open();
		} catch (Exception e) {
			// Dialeg.mostrarDialeg(this, "Error c�mera", e.getMessage());
		}
		return c;
	}

	/*
	 * Obtenir l'orientaci� de la c�mera en funci� de l'orientaci� de la
	 * pantalla
	 */
	private static int getCameraOrientation(Activity activity, int idCamera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(0, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensar l'efecte mirall
		} else { // c�mera de darrera
			result = (info.orientation - degrees + 360) % 360;
		}

		return result;
	}

	/*
	 * Assignar l'orientaci� de la c�mera (previsualitzaci�)
	 */
	public static void setCameraDisplayOrientation(Activity activity, int idCamera, android.hardware.Camera camera) {
		camera.setDisplayOrientation(getCameraOrientation(activity, Camera.CameraInfo.CAMERA_FACING_BACK));
	}

	/*
	 * Configurar els par�metres del gravador
	 */
	private void prepararGravacio() {
		if (gravador != null) {
			aturarGravacio();
		}
		try {
			camera.unlock();
			gravador = new MediaRecorder();
			// quina c�mera utilitza el gravador
			gravador.setCamera(camera);
			// or�gen del v�deo i de l'�udio
			gravador.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			gravador.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			// formats de sortida
			gravador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			// codificaci� del v�deo i de l'�udio
			gravador.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			gravador.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			// establir el nom del fitxer de sortida
			gravador.setOutputFile(getNomFitxer().getAbsolutePath());
			// assignar la previsualitzaci�
			gravador.setPreviewDisplay(prev.getHolder().getSurface());
			// indicar amb quina orientaci� s'ha de gravar
			gravador.setOrientationHint(getCameraOrientation(this, Camera.CameraInfo.CAMERA_FACING_BACK));
			// tot preparat!
			gravador.prepare();
		} catch (Exception e) {
			// Dialeg.mostrarDialeg(this, "Gravaci�",
			// "ERROR en iniciar la gravaci�: " + e.getMessage());
			aturarGravacio();
		}
	}

	/*
	 * Establir el nom del fitxer on es desar� el v�deo
	 */
	private File getNomFitxer() {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "video_" + timeStamp + ".3gpp";
		File path = new File(Environment.getExternalStorageDirectory(), this.getPackageName());
		pathFinal = path.getAbsolutePath().concat("/".concat(imageFileName));
		if (!path.exists())
			path.mkdirs();
		
		return new File(path, imageFileName);
	}

	private void aturarGravacio() {
		if (gravador != null) {
			gravador.reset(); // esborrar la configuraci� del gravador
			gravador.release(); // alliberar els recursos de l'objecte
			gravador = null;
			camera.lock(); // bloquejar la camera
			Intent intent = new Intent(getApplicationContext(),VideoVisualitzacioActivity.class);
			intent.putExtra("PathVideo",pathFinal );
			startActivity(intent);
			finish();
		}
	}

	private void alliberarCamera() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		aturarGravacio();
		alliberarCamera();

		
	}
	public void onDestroy(View view){
		finish();
	}

}
