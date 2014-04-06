package net.infobosccoma.museu;

import android.graphics.Bitmap;

public class Imatge {
	private String descripcio;
	private Bitmap foto;
	
	public Imatge(Bitmap fotoo,String descripcioo){
		foto = fotoo;
		descripcio = descripcioo;
	}

	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}
	
	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	
	
}
