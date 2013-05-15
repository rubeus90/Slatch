import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Write a description of class Partie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Partie
{
    // instance variables - replace the example below with your own
    private int aNbrJoueur;
    private int aRevenuBatiment;
    private int aLargeur;
    private int aHauteur;
    private int aTourMax;
    private int aTour;
    private Scanner aMap;
    private Terrain[][] aTerrain;
    

    /**
     * Constructor for objects of class Partie
     */
    public Partie(final int pNbrJoueur,final int pRevenuBatiment,final int pTourMax, final Scanner pMap)
    {
        aNbrJoueur = pNbrJoueur;
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment;
        aMap = pMap;
    }
    
    public void chargerMap(){
    	aLargeur = aMap.nextLine();
		aHauteur = aMap.nextLine();
		aTerrain = new Terrain[aLargeur][aHauteur];
		
		Terrain plaine = TypeTerrain.PLAINE;
		
		for(int i=0; i<aLargeur; i++){
			for(int j=0; j<aHauteur; j++){
				aTerrain[i][j] = new Terrain(i, j, plaine.getJoueur(), plaine.getPointDeVie(), plaine.getNom(), plaine.getImage(), plaine.getDescription(), plaine.getCouverture(), plaine.getCoutDeplacement);
			}
		}
		
		
    }

    
    /*************************************************************
     * 
     * 
     *  ACCESSEUR
     * 
     * 
     * *************************************************************/
     
    /**
     * Accesseur qui renvoi la valeur de la hauteur du plateau
     * @return aHauteur 
     */
    public int getLargeur()
    {
        return aHauteur;
    }
    
    /**
     * Accesseur qui renvoi la valeur de la largeur du plateau
     * @return aLargeur 
     */
    public int getLongueur()
    {
        return aLargeur;
    }
    
    /**
     * Accesseur qui renvoi la valeur le tour actuel du plateau
     * @return aTour 
     */
    public int getTour()
    {
        return aTour;
    }
    
    /**
     * Accesseur qui renvoi la valeur du nombre de tour maximum du plateau
     * @return aTourMax 
     */
    public int getTourMax()
    {
        return aTourMax;
    }
    
    /**
     * Accesseur qui renvoi les revenus par batiments
     * @return aRevenuBatiment
     */
    public int getRevenuBatiment()
    {
        return aRevenuBatiment;
    }
    
    /**
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public int getNbrJoueur()
    {
        return aNbrJoueur;
    }
    
    /**
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public Terrain[][] getTerrain()
    {
        return aTerrain;
    }
    
    /*************************************************************
     * 
     * 
     *  MODIFICATEUR
     * 
     * 
     * *************************************************************/
     
     /**
     * Modificateur qui modifie la valeur du tour actuel
     * @param pTour
     */
    public void setTour(final int pTour)
    {
        aTour = pTour;
    }
    
    /**
     * Modificateur qui modifie la valeur du tour actuel
     * @param pTour
     */
    public void setRevenuBatiment(final int pRevenuBatiment)
    {
        aRevenuBatiment = pRevenuBatiment;
    }
         
    
}
