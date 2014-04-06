package net.infobosccoma.museu;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PrevisualitzacioCamera extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private Camera camera;
	private Context context;

	public PrevisualitzacioCamera(Context context, Camera camera) {
		super(context);
		this.context = context;
		this.camera = camera;
		// configurar el SurfaceHolder
		holder = getHolder();
		holder.addCallback(this);
		// est� deprecated, per� cal en versions anteriors a Android 3.0
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		if (holder.getSurface() == null) {
			// preview surface does not exist
			//Dialeg.mostrarDialeg(context, "Previsualitzaci�", "No hi ha cap zona de previsualitzaci� disponile");
			return;
		}

		// aturar la previsualitzaci� abans de fer canvis
		try {
			camera.stopPreview();
		} catch (Exception e) {

		}

		// establir la mida de previsualitzaci� i redimensionar o rotar
		// iniciar la previsualitzaci� amb els nous canvis
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
			//Dialeg.mostrarDialeg(context, "Previsualitzaci�", "Error iniciant la previsualitzaci� de la c�mera: " + e.getMessage());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// connectar la c�mera amb la vista
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (IOException e) {
			//Dialeg.mostrarDialeg(context, "Previsualitzaci�", "Error mentre es configurava la previsualitzaci�");
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.release();
	}
}
