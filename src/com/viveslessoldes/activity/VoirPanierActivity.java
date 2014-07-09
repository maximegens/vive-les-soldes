package com.viveslessoldes.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.viveslessoldes.baseDeDonnees.gestionBDD;
import com.viveslessoldes.constantes.ConstantesSoldes;
import com.viveslessoldes.divers.Article;
import com.viveslessoldes.divers.CalculPourcentage;
import com.viveslessoldes.divers.Panier;

/**
 * Classe permettant d'afficher le contenu du panier en cours.
 *
 */
public class VoirPanierActivity extends Activity{

	private float prixSansRemiseFloat;
	private float remiseTotalFloat;
	private float prixAPayerFloat;
	private TextView prixSansRemise;
	private TextView remiseTotale;
	private TextView prixAPayer;
	private TableLayout tl;
	private TableRow tr;
	private Button viderPanier;
	private Button enregistrerPanier;
	
	@Override
	public void onBackPressed() {
		Intent retourAcceuil = new Intent(VoirPanierActivity.this, SimpleCalculActivity.class);
		startActivity(retourAcceuil);
		finish();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voir_panier);
		
		final gestionBDD Bdd = new gestionBDD(this);
		this.tl = (TableLayout) findViewById(R.id.details_panier);
		this.prixSansRemise = (TextView) findViewById(R.id.prix_sans_remise);
		this.remiseTotale = (TextView) findViewById(R.id.remise_totale);
		this.prixAPayer = (TextView) findViewById(R.id.prix_a_payer);
		this.viderPanier = (Button) findViewById(R.id.vider_panier);
		this.enregistrerPanier = (Button) findViewById(R.id.enregistrer_panier);
		this.prixSansRemiseFloat = 0;
	    this.remiseTotalFloat = 0;
	    this.prixAPayerFloat = 0;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);
		
        for (Article article : ConstantesSoldes.lesArticles) {
            tr = new TableRow(this);
            
            /** calcul des prix finaux **/
            this.prixSansRemiseFloat += article.getPrixInitial();
        	this.remiseTotalFloat += article.getRemise();
        	this.prixAPayerFloat += article.getPrixFinal();
            
    		/** construction des items pour le tableau **/
            tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            for(int i = 0; i <= 4; i++)
            	tr.addView(generateTextView(article, layoutParams,i));
            tl.addView(tr, layoutParams);
            
        }
        
        String prixInitialArrondi = CalculPourcentage.calculPrixArrondiToString(this.prixSansRemiseFloat, 2);
        String remiseTotaleArrondi = CalculPourcentage.calculPrixArrondiToString(this.remiseTotalFloat, 2);
		String prixAPayerArrondi = CalculPourcentage.calculPrixArrondiToString(this.prixAPayerFloat, 2);
		this.prixSansRemise.setText("Prix final sans remise "+prixInitialArrondi+ConstantesSoldes.ESPACE+ConstantesSoldes.EURO);
		this.remiseTotale.setText("Remise totale de "+remiseTotaleArrondi+ConstantesSoldes.ESPACE+ConstantesSoldes.EURO);
		this.prixAPayer.setText(prixAPayerArrondi+ConstantesSoldes.ESPACE+ConstantesSoldes.EURO);
		
		this.viderPanier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConstantesSoldes.lesArticles.clear();
				Intent rechargementPagePanier = new Intent(VoirPanierActivity.this, VoirPanierActivity.class);
				startActivity(rechargementPagePanier);
				finish();
			}
		});
		
		this.enregistrerPanier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bdd.open();
				
				Panier panier = new Panier();
				Bdd.insertPanier(panier);
				
				int numPanier = Bdd.getCountPanier();
				Log.v("nombre de panier : ", String.valueOf(numPanier));
				
				for (Article article : ConstantesSoldes.lesArticles){
					Log.v("change id panier avant", ""+article.getIdPanier());
					article.setIdPanier(numPanier);
					Log.v("change id panier apres", ""+article.getIdPanier());
					Bdd.insertArticle(article);
				}
				
				Log.v("nombre d'article : ", String.valueOf(Bdd.getCountArticle()));
				ArrayList<Article> lesarticles = new ArrayList<Article>();
				
				for (int i = 1; i<= Bdd.getCountPanier();i++){
					Log.v("Panier : "+i, "num panier dans la base "+Bdd.getPanierById(i).getId());
					lesarticles.clear();
					lesarticles = Bdd.getArticlesByIdPanier(i);
					for (int j = 0; j<lesarticles.size();j++) {
						Log.v("id Article : "+lesarticles.get(j).getId(), lesarticles.get(j).toString());
					}
						
				}
				Bdd.close();
			}
		});
		
	}
	
	/**
	 * Generation de chaque ligne du tableau pour chaque article de la liste des articles.
	 * @param article Article a afficher sur la ligne.
	 * @param ly Les parametres du layouts.
	 * @param position la position de items dans la ligne.
	 * @return Le TextView representant l item de la ligne.
	 */
	public TextView generateTextView(final Article article, LayoutParams ly,int position) {
	    TextView result = new TextView(this);
	    
	    if(position == 0)
	    	result.setText(article.getNom());
	    else if(position == 1)
	    	result.setText(String.valueOf(article.getPrixInitial()));
	    else if(position == 2)
	    	result.setText(article.getPourcentage()+ConstantesSoldes.POURCENTAGE);
	    else if(position == 3){
	    	String remiseArrondi = CalculPourcentage.calculPrixArrondiToString(article.getRemise(), 2);
	    	result.setText(remiseArrondi);
	    }else if(position == 4){
	    	String prixArrondi = CalculPourcentage.calculPrixArrondiToString(article.getPrixFinal(), 2);
	    	result.setText(prixArrondi);
	    	result.setTypeface(null, Typeface.BOLD);
	    }else
	    	result.setText(null);
	    	
	    result.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder supprArticle = new AlertDialog.Builder(VoirPanierActivity.this);
				supprArticle.setTitle("Voulez vous retirer l'article "+article.getNom()+" de votre panier ?");
				supprArticle.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	ConstantesSoldes.lesArticles.remove(article);
		            	Intent rechargementPagePanier = new Intent(VoirPanierActivity.this, VoirPanierActivity.class);
						startActivity(rechargementPagePanier);
						finish();
		            }
		        });
				supprArticle.setNegativeButton("Non", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            }
		        });
		        supprArticle.show();
			}
		});
	    
	    result.setBackgroundColor(Color.LTGRAY);
	    result.setTextColor(Color.DKGRAY);
	    result.setGravity(Gravity.CENTER);
	    result.setPadding(2, 2, 2, 2);
	    result.setLayoutParams(ly);
	    return result;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
