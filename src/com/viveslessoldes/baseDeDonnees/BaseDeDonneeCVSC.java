package com.viveslessoldes.baseDeDonnees;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe BaseDeDonneeCVSC - Géres la base de donnée de l'application.
 *
 */
public class BaseDeDonneeCVSC extends SQLiteOpenHelper {

		// Nom des tables
		public static final String TABLE_PANIER = "table_Panier";
		public static final String TABLE_ARTICLE = "table_Article";

		// Description des colonnes de la table TABLE_PANIER
		public static final String COLUMN_IDPANIER = "IDPANIER";
		public static final String COLUMN_NOMPANIER = "NOMPANIER";
		public static final String COLUMN_DATE = "DATE";
		public static final String COLUMN_MAGASIN = "MAGASIN";

		// Description des colonnes TABLE_ARTICLE
		public static final String COLUMN_IDARTICLE = "IDARTICLE";
		public static final String COLUMN_NOMARTICLE = "NOMARTICLE";
		public static final String COLUMN_PRIXINITIAL = "PRIXINITIAL";
		public static final String COLUMN_REMISE = "REMISE";
		public static final String COLUMN_PRIXFINAL = "PRIXFINAL";
		public static final String COLUMN_POURCENTAGE = "POURCENTAGE";
		public static final String COLUMN_IDDUPANIER = "IDDUPANIER";

		private static final String CREATE_TABLE_PANIER = "CREATE TABLE "
				+ TABLE_PANIER + " (" 
				+ COLUMN_IDPANIER + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ COLUMN_NOMPANIER + " TEXT NOT NULL, " 
				+ COLUMN_DATE + " TEXT NOT NULL, "
				+ COLUMN_MAGASIN +" TEXT NOT NULL"
				+");";
		
		private static final String CREATE_TABLE_ARTICLE = "CREATE TABLE "
				+ TABLE_ARTICLE + " (" 
				+ COLUMN_IDARTICLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ COLUMN_NOMARTICLE + " TEXT NOT NULL, " 
				+ COLUMN_PRIXINITIAL + " REAL NOT NULL, "
				+ COLUMN_REMISE + " REAL NOT NULL, "
				+ COLUMN_PRIXFINAL + " REAL NOT NULL, "
				+ COLUMN_POURCENTAGE + " INTEGER NOT NULL, "
				+ COLUMN_IDDUPANIER + " INTEGER NOT NULL "
				+");";

		public BaseDeDonneeCVSC(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_PANIER);
			db.execSQL(CREATE_TABLE_ARTICLE);
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
			//comme ça lorsque je change la version les id repartent de 0
			db.execSQL("DROP TABLE " + CREATE_TABLE_PANIER + ";");
			db.execSQL("DROP TABLE " + CREATE_TABLE_ARTICLE + ";");
			onCreate(db);
		}
}
