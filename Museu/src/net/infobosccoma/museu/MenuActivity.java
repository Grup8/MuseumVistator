package net.infobosccoma.museu;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener {

	private Button btoAudio;
	private MediaPlayer audio1;
	private boolean estat;
	private String pathimage;
	private ImageView myImage;
	private String nom;
	private String cognom;
	public final static String IMATGE = "IMATGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		btoAudio = new Button(this);
		btoAudio.setOnClickListener(this);
		//
		audio1 = MediaPlayer.create(this, R.raw.fausto_papetti__green_eyes);
		//
		audio1.setLooping(true);
		audio1.start();
		estat = true;

		nom = getIntent()
				.getStringExtra(RegistrarUsuariActivity.NOM.toString());
		cognom = getIntent().getStringExtra(
				RegistrarUsuariActivity.COGNOM.toString());
		pathimage = getIntent().getStringExtra(
				RegistrarUsuariActivity.IMATGE.toString());

		TextView nomSec = (TextView) findViewById(R.id.perfilUsuariNom);
		myImage = (ImageView) findViewById(R.id.perfilUsariImage);

		nomSec.setText(nom + ", " + cognom);
		seleccionarimage(pathimage);

	}

	public void onClickPerfil(View view) {
		Intent i = new Intent(MenuActivity.this, VeurePerfilActivity.class);

		i.putExtra("NOM", nom);
		i.putExtra("COGNOM", cognom);
		i.putExtra(IMATGE, pathimage);

		startActivity(i);

	}

	private void seleccionarimage(String pathimage2) {
		if (pathimage != null) {
			File imgFile = new File(pathimage);
			if (imgFile.exists()) {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());

				myImage.setAdjustViewBounds(true);
				myImage.setImageBitmap(myBitmap);

			}
		} else {

			File f = new File("/sdcard/net.infobosccoma.projectemuseu/foto.jpg");
			Bitmap bmp1 = BitmapFactory.decodeFile(f.getAbsolutePath());
			myImage.setImageBitmap(bmp1);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	// onClickRecord

	public void onClickAudio(View view) {
		if (estat) {
			audio1.pause();
			estat = false;
		} else {
			audio1.start();
			estat = true;
		}
	}

	public void onClickRecord(View view) {
		if (estat) {
			audio1.pause();
			estat = false;
		}
		Intent i = new Intent(this, GravacioAudioActivity.class);
		startActivity(i);
	}

	public void onClickImatges(View v) {
		Intent intent = new Intent(getApplicationContext(), FotoActivity.class);
		startActivity(intent);
	}

	public void onClickVideo(View v) {
		if (estat) {
			audio1.pause();
			estat = false;
		}
		Intent intent = new Intent(getApplicationContext(),
				VideoGrabacioActivity.class);
		startActivity(intent);

	}

	public void onDestroy() {
		audio1.stop();
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
