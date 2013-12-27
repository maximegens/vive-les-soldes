package com.viveslessoldes.divers;

import com.viveslessoldes.constantes.ConstantesSoldes;

/**
 *	Classe Article, Cette classe defini un article, un vetement, blouson, jean etc 
 *	ainsi que toutes les caracteristiques de cet article.
 */
public class Article {

	private int id;
	

	private float prixInitial;
	private float remise;
	private float prixFinal;
	private int pourcentage;
	private String nom;
	private String magasin;
	private int idPanier;
	
	public Article(){}
	


	@Override
	public String toString() {
		return "Article [id=" + id + ", prixInitial=" + prixInitial
				+ ", remise=" + remise + ", prixFinal=" + prixFinal
				+ ", pourcentage=" + pourcentage + ", nom=" + nom
				+ ", magasin=" + magasin + ", idPanier=" + idPanier + "]";
	}



	public Article(float prixInitial,float remise,float prixFinal, int pourcentage,String nom) {
		this.prixInitial = prixInitial;
		this.remise = remise;
		this.prixFinal = prixFinal;
		this.pourcentage = pourcentage;
		this.nom = nom;
		this.magasin = ConstantesSoldes.VIDE;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public float getPrixInitial() {
		return prixInitial;
	}



	public void setPrixInitial(float prixInitial) {
		this.prixInitial = prixInitial;
	}



	public float getRemise() {
		return remise;
	}



	public void setRemise(float remise) {
		this.remise = remise;
	}



	public float getPrixFinal() {
		return prixFinal;
	}



	public void setPrixFinal(float prixFinal) {
		this.prixFinal = prixFinal;
	}



	public int getPourcentage() {
		return pourcentage;
	}



	public void setPourcentage(int pourcentage) {
		this.pourcentage = pourcentage;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getMagasin() {
		return magasin;
	}



	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}



	public int getIdPanier() {
		return idPanier;
	}



	public void setIdPanier(int idPanier) {
		this.idPanier = idPanier;
	}

	
	
}
