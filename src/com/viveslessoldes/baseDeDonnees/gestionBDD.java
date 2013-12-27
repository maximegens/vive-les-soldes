package com.viveslessoldes.baseDeDonnees;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viveslessoldes.divers.Article;
import com.viveslessoldes.divers.Panier;
 
/**
 * Classe RechercheBDD - Géres la table TABLE_RECHERCHE de la base de données de l'application.
 *
 */
public class gestionBDD {
 
	private static final int VERSION_BDD = 1;
	private static final String CVSC_BASE_NAME = "vivelessoldes.db";
	public static final String TABLE_PANIER = "table_Panier";
	public static final String TABLE_ARTICLE = "table_Article";
 
	/** table TABLE_PANIER **/
	public static final String COLUMN_IDPANIER = "IDPANIER";
	public static final int NUM_COLUMN_IDPANIER = 0;
	public static final String COLUMN_NOMPANIER = "NOMPANIER";
	public static final int NUM_COLUMN_NOMPANIER = 1;
	public static final String COLUMN_DATE = "DATE";
	public static final int NUM_COLUMN_DATE = 2;
	public static final String COLUMN_MAGASIN = "MAGASIN";
	public static final int NUM_COLUMN_MAGASIN = 3;
	
	/** table TABLE_ARTICLE **/
	public static final String COLUMN_IDARTICLE = "IDARTICLE";
	public static final int NUM_COLUMN_IDARTICLE = 0;
	public static final String COLUMN_NOMARTICLE = "NOMARTICLE";
	public static final int NUM_COLUMN_NOMARTICLE = 1;
	public static final String COLUMN_PRIXINITIAL = "PRIXINITIAL";
	public static final int NUM_COLUMN_PRIXINITIAL = 2;
	public static final String COLUMN_REMISE = "REMISE";
	public static final int NUM_COLUMN_REMISE = 3;
	public static final String COLUMN_PRIXFINAL = "PRIXFINAL";
	public static final int NUM_COLUMN_PRIXFINAL = 4;
	public static final String COLUMN_POURCENTAGE = "POURCENTAGE";
	public static final int NUM_COLUMN_POURCENTAGE = 5;
	public static final String COLUMN_IDDUPANIER = "IDDUPANIER";
	public static final int NUM_COLUMN_IDDUPANIER = 6;
	
	private SQLiteDatabase bdd;
	private BaseDeDonneeCVSC maBaseSQLite;
 
	public gestionBDD(Context context){
		maBaseSQLite = new BaseDeDonneeCVSC(context, CVSC_BASE_NAME, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertArticle(Article article){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOMARTICLE, article.getNom());
		values.put(COLUMN_PRIXINITIAL, article.getPrixInitial());
		values.put(COLUMN_REMISE, article.getRemise());
		values.put(COLUMN_PRIXFINAL, article.getPrixFinal());
		values.put(COLUMN_POURCENTAGE, article.getPourcentage());
		values.put(COLUMN_IDDUPANIER, 1);
		return bdd.insert(TABLE_ARTICLE, null, values);
	}

	public long insertPanier(Panier panier){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOMPANIER, panier.getNomPanier());
		values.put(COLUMN_DATE, panier.getDate());
		values.put(COLUMN_MAGASIN, panier.getMagasin());
		return bdd.insert(TABLE_PANIER, null, values);
	}
 
	public int updateArticle(int id, Article article){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOMARTICLE, article.getNom());
		values.put(COLUMN_PRIXINITIAL, article.getPrixInitial());
		values.put(COLUMN_REMISE, article.getRemise());
		values.put(COLUMN_PRIXFINAL, article.getPrixFinal());
		values.put(COLUMN_POURCENTAGE, article.getPourcentage());
		values.put(COLUMN_IDDUPANIER, 1);
		return bdd.update(TABLE_ARTICLE, values, COLUMN_IDARTICLE + " = " +id, null);
	}
	
	public int updatePanier(int id, Panier panier){
		ContentValues values = new ContentValues();
		values.put(COLUMN_IDPANIER, panier.getId());
		values.put(COLUMN_NOMPANIER, panier.getNomPanier());
		values.put(COLUMN_DATE, panier.getDate());
		values.put(COLUMN_MAGASIN, panier.getMagasin());
		return bdd.update(TABLE_PANIER, values, COLUMN_IDPANIER + " = " +id, null);
	}
 
	public int removePanierWithID(int id){
		return bdd.delete(TABLE_PANIER, COLUMN_IDPANIER + " = " +id, null);
	}
 
	
	public Article getArticleById(int id){
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_ARTICLE, new String[] {COLUMN_IDARTICLE, COLUMN_NOMARTICLE, COLUMN_PRIXINITIAL,COLUMN_REMISE,COLUMN_PRIXFINAL,COLUMN_POURCENTAGE,COLUMN_IDDUPANIER}, COLUMN_IDARTICLE + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToArticle(c);
	}

	public Panier getPanierById(int id){
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_PANIER, new String[] {COLUMN_IDPANIER, COLUMN_NOMPANIER, COLUMN_DATE,COLUMN_MAGASIN}, COLUMN_IDPANIER + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToPanier(c);
	}
	
	public Article getArticleByIdPanier(int idPanier){
		Log.v("getArticleByIdPanier", "idPanier a recherche : "+idPanier);
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_ARTICLE, new String[] {COLUMN_IDARTICLE, COLUMN_NOMARTICLE, COLUMN_PRIXINITIAL,COLUMN_REMISE,COLUMN_PRIXFINAL,COLUMN_POURCENTAGE,COLUMN_IDDUPANIER}, COLUMN_IDDUPANIER + " LIKE \"" + idPanier +"\"", null, null, null, null);
		return cursorToArticle(c);
	}
	
	public ArrayList<Article> getArticlesByIdPanier(int idPanier){
		Log.v("getArticlesByIdPanier", "idPanier a recherche : "+idPanier);
		//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = bdd.query(TABLE_ARTICLE, new String[] {COLUMN_IDARTICLE, COLUMN_NOMARTICLE, COLUMN_PRIXINITIAL,COLUMN_REMISE,COLUMN_PRIXFINAL,COLUMN_POURCENTAGE,COLUMN_IDDUPANIER}, COLUMN_IDDUPANIER + " LIKE \"" + idPanier +"\"", null, null, null, null);
		return cursorToArticleList(c);
	}
	
	public int getCountArticle(){
		Cursor dataCount = maBaseSQLite.getReadableDatabase().rawQuery("select * from " + TABLE_ARTICLE, null);
		return dataCount.getCount();
	}
	
	public int getCountPanier(){
		Cursor dataCount = maBaseSQLite.getReadableDatabase().rawQuery("select * from " + TABLE_PANIER, null);
		return dataCount.getCount();
	}
 
	private Article cursorToArticle(Cursor c){
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Article article = new Article();
		article.setId(c.getInt(NUM_COLUMN_IDARTICLE));
		article.setNom(c.getString(NUM_COLUMN_NOMARTICLE));
		article.setPrixInitial(c.getFloat(NUM_COLUMN_PRIXINITIAL));
		article.setRemise(c.getFloat(NUM_COLUMN_REMISE));
		article.setPrixFinal(c.getFloat(NUM_COLUMN_PRIXFINAL));
		article.setPourcentage(c.getInt(NUM_COLUMN_POURCENTAGE));
		article.setIdPanier(c.getInt(NUM_COLUMN_IDDUPANIER));
		c.close();
		return article;
	}
	
	private Panier cursorToPanier(Cursor c){
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Panier panier = new Panier();
		panier.setId(c.getInt(NUM_COLUMN_IDPANIER));
		panier.setNomPanier(c.getString(NUM_COLUMN_NOMPANIER));
		panier.setDate(c.getString(NUM_COLUMN_DATE));
		panier.setMagasin(c.getString(NUM_COLUMN_MAGASIN));
		
		c.close();
		return panier;
	}
	
	//Cette méthode permet de convertir un cursor en une Reponse
	private ArrayList<Article> cursorToArticleList(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		int i = 1;
		int nb = c.getCount();
		if (c.getCount() == 0)
			return null;
 
		ArrayList<Article> lesarticles = new ArrayList<Article>();
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un livre
		
		while(i<= nb){
		Article article = new Article();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		article.setId(c.getInt(NUM_COLUMN_IDARTICLE));
		article.setNom(c.getString(NUM_COLUMN_NOMARTICLE));
		article.setPrixInitial(c.getFloat(NUM_COLUMN_PRIXINITIAL));
		article.setRemise(c.getFloat(NUM_COLUMN_REMISE));
		article.setPrixFinal(c.getFloat(NUM_COLUMN_PRIXFINAL));
		article.setPourcentage(c.getInt(NUM_COLUMN_POURCENTAGE));
		article.setIdPanier(c.getInt(NUM_COLUMN_IDDUPANIER));
		lesarticles.add(article);
		
		c.moveToNext();
		i++;
		}
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return lesarticles;
	}
	

}