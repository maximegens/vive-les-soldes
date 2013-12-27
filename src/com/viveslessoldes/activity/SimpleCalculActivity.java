package com.viveslessoldes.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viveslessoldes.activity.R;
import com.viveslessoldes.constantes.ConstantesSoldes;
import com.viveslessoldes.divers.Article;
import com.viveslessoldes.divers.CalculPourcentage;

/**
 * Classe permettant de calculer le pourcentage pour un produit.
 *
 */

public class SimpleCalculActivity extends Activity implements View.OnClickListener{

	private EditText prixInitial;
	private TextView remise;
	private TextView remisePourcentage;
	private TextView prixFinal;
	private Button ajoutPanier;
	private Button voirPanier;
	private float prix_initial;
	private float prix_final;
	private int pourcentage;
	private float laRemise;
	private Button buttonCliquer;
	TextView text; 
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_calcul);
		
		Button button05 =  (Button)findViewById(R.id.button05);
		Button button10 =  (Button)findViewById(R.id.button10);
		Button button15 =  (Button)findViewById(R.id.button15);
		Button button20 =  (Button)findViewById(R.id.button20);
		Button button25 =  (Button)findViewById(R.id.button25);
		Button button30 =  (Button)findViewById(R.id.button30);
		Button button35 =  (Button)findViewById(R.id.button35);
		Button button40 =  (Button)findViewById(R.id.button40);
		Button button45 =  (Button)findViewById(R.id.button45);
		Button button50 =  (Button)findViewById(R.id.button50);
		Button button55 =  (Button)findViewById(R.id.button55);
		Button button60 =  (Button)findViewById(R.id.button60);
		Button button65 =  (Button)findViewById(R.id.button65);
		Button button70 =  (Button)findViewById(R.id.button70);
		Button button75 =  (Button)findViewById(R.id.button75);
		Button button80 =  (Button)findViewById(R.id.button80);
		Button button85 =  (Button)findViewById(R.id.button85);
		Button button90 =  (Button)findViewById(R.id.button90);
		Button button95 =  (Button)findViewById(R.id.button95);
		Button buttonPasReduction =  (Button)findViewById(R.id.button_pas_de_reduction);

		this.remise = (TextView)findViewById(R.id.remise);
		this.remisePourcentage = (TextView)findViewById(R.id.remise_pourcentage);
		this.prixFinal = (TextView)findViewById(R.id.prix_final);
		this.prixInitial = (EditText)findViewById(R.id.prix_initial_edittext);
		this.ajoutPanier = (Button)findViewById(R.id.ajout_panier);
		this.voirPanier = (Button)findViewById(R.id.voir_panier);
		
		/** ajout des listeners **/
		button05.setOnClickListener(this);
		button10.setOnClickListener(this);
		button15.setOnClickListener(this);
		button20.setOnClickListener(this);
		button25.setOnClickListener(this);
		button30.setOnClickListener(this);
		button35.setOnClickListener(this);
		button40.setOnClickListener(this);
		button45.setOnClickListener(this);
		button50.setOnClickListener(this);
		button55.setOnClickListener(this);
		button60.setOnClickListener(this);
		button65.setOnClickListener(this);
		button70.setOnClickListener(this);
		button75.setOnClickListener(this);
		button80.setOnClickListener(this);
		button85.setOnClickListener(this);
		button90.setOnClickListener(this);
		button95.setOnClickListener(this);
		buttonPasReduction.setOnClickListener(this);
		
		
		/** definition des methodes listeners **/
		this.voirPanier.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent voirPagePanier = new Intent(SimpleCalculActivity.this, VoirPanierActivity.class);
				startActivity(voirPagePanier);
			}
		});
		
		this.ajoutPanier.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				if((prixInitial.getText().toString() == null) && (prixInitial.getText().toString().trim().length() == 0))
					Toast.makeText(SimpleCalculActivity.this, "Veuillez indiquer un prix", Toast.LENGTH_SHORT).show();
				else if (buttonCliquer == null || remise.getText().toString().equals(ConstantesSoldes.EURO))
					Toast.makeText(SimpleCalculActivity.this, "Veuillez s�lectionner un pourcentage", Toast.LENGTH_SHORT).show();
				else
					showDialog(ConstantesSoldes.DIALOG_NOM_ARTICLE);
			}
		});
		
		/** permet de fermer le clavier lors du clique sur le bouton "ok" **/
		prixInitial.setOnKeyListener(new OnKeyListener(){
	        public boolean onKey(View v, int keyCode, KeyEvent event){
	            if (event.getAction() == KeyEvent.ACTION_DOWN){
	                if (keyCode == KeyEvent.KEYCODE_ENTER){
	                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	                	imm.hideSoftInputFromWindow(prixInitial.getWindowToken(), 0);
	                    return true;
	                }
	            }
	            return false;
	        }
	    });
		
		this.prixInitial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reinitialisationComposant();
			}
		});
        
    }
		
	
	/**
	 * Methode calculant le pourcentage d'une valeur.
	 * @param valeurInitial la valeur de base.
	 * @param pourcentage le pourcentage a appliquer a cette valeur.
	 * @return la valeur auquel a �t� appliqu� le pourcentage.
	 */
	public void calculPrixRemise(float valeurInitial, float lePourcentage){
	
		this.pourcentage = (int)lePourcentage;
		this.prix_initial = valeurInitial;
		
		this.laRemise = CalculPourcentage.calculRemisePourcentage(valeurInitial, pourcentage);
		this.prix_final =  CalculPourcentage.calculPrixAvecPourcentage(valeurInitial, pourcentage);
		String remiseArrondi = CalculPourcentage.calculPrixArrondiToString(this.laRemise, 2);
		String prixArrondi = CalculPourcentage.calculPrixArrondiToString(this.prix_final, 2);
		
		this.remisePourcentage.setText("Remise de "+this.pourcentage+ConstantesSoldes.POURCENTAGE+" :" );
		this.remise.setText(remiseArrondi+ConstantesSoldes.EURO);
		this.prixFinal.setText(prixArrondi+ConstantesSoldes.EURO);
		
	}
	
	/**
	 * Listener sur les boutons.
	 * Applique le pourcentage choisi pour la valeur entr� dans l'EditText.
	 */
	public void onClick(View v) {
		
		if ((prixInitial.getText().toString() != null) && (prixInitial.getText().toString().trim().length() > 0)) {
			
			float valeur = Float.parseFloat((prixInitial.getText().toString()));
			
			/** on remet le bouton de pourcentage en normal si jamais il a d�j� �tait cliqu� auparavant **/
			if(buttonCliquer != null)
				buttonCliquer.setTypeface(null, Typeface.NORMAL);
			
			/** on r�cupere le nouveau bouton de pourcentage **/
			buttonCliquer =  (Button)findViewById(v.getId());
			buttonCliquer.setTypeface(null, Typeface.BOLD);
			
			switch(v.getId()){
				case R.id.button_pas_de_reduction : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_0); break;
				case R.id.button05 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_5);  break;
				case R.id.button10 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_10); break;
				case R.id.button15 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_15); break;
				case R.id.button20 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_20); break;
				case R.id.button25 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_25); break;
				case R.id.button30 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_30); break;
				case R.id.button35 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_35); break;
				case R.id.button40 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_40); break;
				case R.id.button45 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_45); break;
				case R.id.button50 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_50); break;
				case R.id.button55 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_55); break;
				case R.id.button60 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_60); break;
				case R.id.button65 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_65); break;
				case R.id.button70 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_70); break;
				case R.id.button75 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_75); break;
				case R.id.button80 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_80); break;
				case R.id.button85 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_85); break;
				case R.id.button90 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_90); break;
				case R.id.button95 : calculPrixRemise(valeur,ConstantesSoldes.POURCENTAGE_95); break;
			}
		}else
			Toast.makeText(this, "Veuillez indiquer un prix", Toast.LENGTH_SHORT).show();
	}
	
	/** 
	 * Gestion des AlertDialog.
	 */
	  protected Dialog onCreateDialog(int id) {
	        Dialog dlg;
	        switch (id) {
	          case ConstantesSoldes.DIALOG_NOM_ARTICLE :
	          {
	        		LayoutInflater factory = LayoutInflater.from(this);
	        	    final View alertDialogView = factory.inflate(R.layout.alert_dialog_donne_nom_a_article, null);
	        	    final AlertDialog.Builder adb = new AlertDialog.Builder(this);
	        	    
	        		adb.setView(alertDialogView);
	        		adb.setTitle("Nom de l'article");
	        		
	        		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	         
	                    	EditText nomArticle = (EditText)alertDialogView.findViewById(R.id.donne_nom_article_edit_text);
	                    	String nomDeArticle;
	                    	int nb;
							if(nomArticle.getText().toString().equals("")){
	                    		nb = ConstantesSoldes.lesArticles.size()+1;
	                    		nomDeArticle = "Article "+nb;
							}else
	                    		nomDeArticle = nomArticle.getText().toString();
	                    	
	                    	Article article = new Article(prix_initial, laRemise, prix_final, pourcentage,nomDeArticle);
	        				ConstantesSoldes.lesArticles.add(article);
	        				
	        				if(ConstantesSoldes.lesArticles.contains(article))
	        					Toast.makeText(getApplicationContext(), "Article correctement ajout� au panier", Toast.LENGTH_SHORT).show();
	        				else
	        					Toast.makeText(getApplicationContext(), "Erreur article non ajout�, veuillez r�essayer", Toast.LENGTH_SHORT).show();
	        				
	        				/** r�initialisation des composants **/
	        				nomArticle.setText("");
	        				reinitialisationComposant();
	        				
	                  } });
	         
	                adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	
	                  } });

	                dlg = adb.create();
	                break;
	          }
	          default:
	                dlg = null;
	        }
	        return dlg;
	  }
	
	private void reinitialisationComposant(){
		
		prixInitial.setText("");
		remise.setText(ConstantesSoldes.EURO);
		prixFinal.setText(ConstantesSoldes.EURO);
		if(buttonCliquer != null)
			buttonCliquer.setTypeface(null, Typeface.NORMAL);
		
	}
	  
	  
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	

	/**
	 * Methode pour le menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_simple_calcul, menu);
		return true;
	}
}
