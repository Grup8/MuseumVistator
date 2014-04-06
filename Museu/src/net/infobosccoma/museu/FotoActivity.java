package net.infobosccoma.museu;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FotoActivity extends Activity {

	private GridView gridview;
	private ArrayList<Imatge> llistaImatge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto);

		
		gridview = (GridView) findViewById(R.id.gridView1);
		
		//creu la arraylist amb l'objecte imatge i crido l'adapter
		carregarFotos();
		//ImageAdapter adapter = new ImageAdapter(getApplicationContext(),llistaImatge);
		gridview.post(new Runnable() {
			
			@Override
			public void run() {
				gridview.setAdapter(new ImageAdapter(getApplicationContext(),llistaImatge));
				
			}
		});
		//gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DialogActivity dialog = new DialogActivity();
				
				Bitmap bmp = llistaImatge.get(position).getFoto();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				
				Bundle bundle = new Bundle();
				bundle.putByteArray("foto", byteArray);
				bundle.putString("descripcioFoto", llistaImatge.get(position).getDescripcio());
				
				dialog.setArguments(bundle);
				dialog.show(getFragmentManager(), "DialogFotoText");

			}

		});

	}

	private void carregarFotos(){
		llistaImatge = new ArrayList<Imatge>();
		
		Bitmap jugador = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		Bitmap thepower = BitmapFactory.decodeResource(getResources(), R.drawable.thepower);
		Bitmap theroad = BitmapFactory.decodeResource(getResources(), R.drawable.theroad);
		Bitmap waiting = BitmapFactory.decodeResource(getResources(), R.drawable.waiting);
		Bitmap chaplin = BitmapFactory.decodeResource(getResources(), R.drawable.chaplin);
		Bitmap paperships = BitmapFactory.decodeResource(getResources(), R.drawable.paperships);
		Bitmap marching = BitmapFactory.decodeResource(getResources(), R.drawable.marching);
		Bitmap raycharles = BitmapFactory.decodeResource(getResources(), R.drawable.raycharles);
		Bitmap pet = BitmapFactory.decodeResource(getResources(), R.drawable.pet);
		Bitmap rallycar = BitmapFactory.decodeResource(getResources(), R.drawable.rallycar);
		Bitmap runner = BitmapFactory.decodeResource(getResources(), R.drawable.runner);
		Bitmap waterplane = BitmapFactory.decodeResource(getResources(), R.drawable.waterplane);
		
		
		llistaImatge.add(new Imatge(jugador, "Thinking about my next move"));
		llistaImatge.add(new Imatge(theroad, "The Road "));
		llistaImatge.add(new Imatge(paperships, "Paper Ships"));
		llistaImatge.add(new Imatge(waiting, "Time Pass"));
		llistaImatge.add(new Imatge(marching, "left! right! left! right!..."));
		llistaImatge.add(new Imatge(raycharles, "Hit the road jack and don't you come back no more.."));
		llistaImatge.add(new Imatge(chaplin, "Charles Chaplin"));
		llistaImatge.add(new Imatge(pet, "The Power"));
		llistaImatge.add(new Imatge(rallycar, "The rally car"));
		llistaImatge.add(new Imatge(runner, "The street runner"));
		llistaImatge.add(new Imatge(waterplane, "Beach..."));
		llistaImatge.add(new Imatge(thepower, "The Power"));
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}


}
