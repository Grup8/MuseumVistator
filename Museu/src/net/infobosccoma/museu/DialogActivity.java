package net.infobosccoma.museu;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogActivity extends DialogFragment {
//
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View v = inflater.inflate(R.layout.fragment_dialog, null);
		// la foto
		ImageView foto = (ImageView) v.findViewById(R.id.dialogFoto);
		// que es mostri l'imatge sense ajustar
		foto.setAdjustViewBounds(true);
		// el text de la foto
		TextView fotoDescripcio = (TextView) v.findViewById(R.id.descripcio);

		// perque no carregi la imatge original, per OutOfMemory
		BitmapFactory.Options options = new BitmapFactory.Options();
		// per agafar el height,width a true;
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(getArguments().getByteArray("foto"), 0, getArguments().getByteArray("foto").length,options);
		
		// faig la reduccio aqui
		options.inSampleSize = calcularInSampleSize(options,534 , 800);
		// un cop agafat el height, width i manipulats..
		options.inJustDecodeBounds = false;
		
		// recupero la foto que s'ha clicat passantli els options.
		Bitmap fotoMostrarDialog = BitmapFactory.decodeByteArray(getArguments().getByteArray("foto"), 0, getArguments().getByteArray("foto").length,
				options);
		
		//asigno la foto i descripcio
		foto.setImageBitmap(fotoMostrarDialog);
		fotoDescripcio.setText(getArguments().getString("descripcioFoto"));

		builder.setView(v).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		return builder.create();
	}

	public static int calcularInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		
		// alçada i ampladade la imatge
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		int heightRatio, widthRatio;

		if (height > reqHeight || width > reqWidth) {
			heightRatio = Math.round((float) height / (float) reqHeight);
			widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		
		return inSampleSize;
	}

}