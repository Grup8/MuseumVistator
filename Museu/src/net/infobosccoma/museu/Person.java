package net.infobosccoma.museu;


public class Person {

	private int id;
	private String nom, cognom, image;

	public Person(){}
	
	public Person(String nom, String cognom, String image) {
		super();
		this.nom = nom;
		this.cognom = cognom;
		this.image = image;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom() {
		return cognom;
	}

	public void setCognom(String cognom) {
		this.cognom = cognom;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", nom=" + nom + ", cognom=" + cognom
				+ ", image=" + image + "]";
	}

	
}
