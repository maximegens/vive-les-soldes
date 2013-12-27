package com.viveslessoldes.divers;

/**
 *	Classe qui se charge des calcules sur les pourcentages, tel que le calcul de la remise ou du pourcentage lui meme. 
 *
 */
public class CalculPourcentage {
	
	
	/**
	 * Methode calculant la remise d'une valeur avec un pourcentage.
	 * @param valeurInitial la valeur de base.
	 * @param pourcentage le pourcentage a appliquer a cette valeur.
	 * @return la remise de ce pourcentage.
	 */
	public static float calculRemisePourcentage(float valeurInitial, float pourcentage){
		return valeurInitial*(pourcentage/100);
	}
	
	/**
	 * Methode calculant le pourcentage d'une valeur.
	 * @param valeurInitial la valeur de base.
	 * @param pourcentage le pourcentage a appliquer a cette valeur.
	 * @return la valeur auquel a été appliqué le pourcentage.
	 */
	public static float calculPrixAvecPourcentage(float valeurInitial, float pourcentage){
		return valeurInitial-(valeurInitial*(pourcentage/100));
	}
	
	/**
	 * Methode retournant une valeur en String.
	 * @param valeur la valeur de base.
	 * @return la valeur en String.
	 */
	public static String valeurToString(float valeur){
		return String.valueOf(valeur);
	}

	
	/**
	 * Methode calculant l'arrondi à deux decimals.
	 * @param valeur la valeur a arrondir.
	 * @param nbDecimal le nombre de decimal a arrondir.
	 * @return la valeur arrondi a deux decimals en string.
	 */
	public static String calculPrixArrondiToString(float valeur,int nbDecimal){
		return String.format("%."+nbDecimal+"f",valeur);
	}
	
}
