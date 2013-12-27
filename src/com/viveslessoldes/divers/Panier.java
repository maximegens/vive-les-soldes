package com.viveslessoldes.divers;

public class Panier {

	private int id;
	private String nomPanier;
	private String date;
	private String magasin;
	
	public Panier() {
		super();
		this.nomPanier = "";
		this.date = "";
		this.magasin = "";
	}

	public Panier(int id, String nomPanier, String date, String magasin) {
		super();
		this.id = id;
		this.nomPanier = nomPanier;
		this.date = date;
		this.magasin = magasin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomPanier() {
		return nomPanier;
	}

	public void setNomPanier(String nomPanier) {
		this.nomPanier = nomPanier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMagasin() {
		return magasin;
	}

	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}

	@Override
	public String toString() {
		return "Panier [id=" + id + ", nomPanier=" + nomPanier + ", date="
				+ date + ", magasin=" + magasin + "]";
	}
	
	
	
}
