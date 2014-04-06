package net.infobosccoma.museu;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrarUsuariActivity extends Activity {

	private TextView txtCognom, txtNom;
	private ImageView imatge;
	private static final int CAMERA_APP_CODE = 100;
	private File tempImageFile;
	public final static String NOM = "NOM";
	public final static String COGNOM = "COGNOM";
	public final static String IMATGE = "IMATGE";
	public static String imagePath, path;
	public static boolean fotoOK;
	private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 232;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrarusuari);
		fotoOK = false;
		
		txtNom = (TextView) findViewById(R.id.txtNom);
		txtCognom = (TextView) findViewById(R.id.txtCognom);
		imatge = (ImageView) findViewById(R.id.yourFaceImage);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	public void btnEntrar(View v) {

		if (txtNom.length() > 0 && txtCognom.length() > 0 && fotoOK) {
			Intent i = new Intent(this, MenuActivity.class);

			String nom1 = txtNom.getText().toString();
			String cognom1 = txtCognom.getText().toString();

			i.putExtra("NOM", nom1);
			i.putExtra("COGNOM", cognom1);
			i.putExtra(IMATGE, path);

			startActivity(i);
			

			Toast.makeText(this, "Registre completat", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			Toast.makeText(this, "Camps incomplets", Toast.LENGTH_SHORT).show();
		}

	}

	public void btnTakePhoto(final View v) {

		String[] addPhoto = new String[] { "Camera", "Gallery" };
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.Select));

		dialog.setItems(addPhoto, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

				if (id == 0) {
					try {
						ferUnaFoto(v);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (id == 1) {
					SeleccionarDesdeGallary(v);
				}
			}
		});

		dialog.setNeutralButton("Cancel",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});
		dialog.show();

	}

	public void ferUnaFoto(View view) throws IOException {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		tempImageFile = crearFitxer();
		takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(tempImageFile));

		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(tempImageFile));

		startActivityForResult(takePictureIntent, CAMERA_APP_CODE);
		path = null;
	}

	private void SeleccionarDesdeGallary(View v) {

		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, YOUR_SELECT_PICTURE_REQUEST_CODE);

	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_APP_CODE) {
			if (resultCode == RESULT_OK) {
				try {

					imatge.setImageBitmap(Media.getBitmap(getContentResolver(),
							Uri.fromFile(tempImageFile)));
					imatge.setAdjustViewBounds(true);
					fotoOK = true;
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} else if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE
				&& resultCode == RESULT_OK && data != null) {

			Uri pickedImage = data.getData();
			String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(pickedImage, filePath,
					null, null, null);
			cursor.moveToFirst();

			imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
			
			path = imagePath;
			imatge.setImageBitmap(BitmapFactory.decodeFile(imagePath));
			imatge.setAdjustViewBounds(true);
			cursor.close();
			fotoOK = true;

		}
	}

	private File crearFitxer() {

		String imageFileName = "foto" + ".jpg";
		File path = new File(Environment.getExternalStorageDirectory(),
				this.getPackageName());

		if (!path.exists())
			path.mkdirs();

		return new File(path, imageFileName);
	}
	public void onDestroy(View view){
		finish();
	}

}
