package net.infobosccoma.museu;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VeurePerfilActivity extends Activity {

	private String pathimage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.veureperfil);
		String nom = getIntent().getStringExtra(
				RegistrarUsuariActivity.NOM.toString());
		String cognom = getIntent().getStringExtra(
				RegistrarUsuariActivity.COGNOM.toString());
		pathimage = getIntent().getStringExtra(
				RegistrarUsuariActivity.IMATGE.toString());

		TextView nomSec = (TextView) findViewById(R.id.secTxtNom);
		TextView cognomSec = (TextView) findViewById(R.id.secTxtCognom);
		ImageView myImage = (ImageView) findViewById(R.id.secImageView);

		nomSec.setText(nom);
		cognomSec.setText(cognom);

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
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit:
			Toast.makeText(this, "Editar perfil seleccionat",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, RegistrarUsuariActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}

		return true;
	}

}
