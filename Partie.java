import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;

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
        chargerMap();
    }
    
    public void chargerMap(){
    	aLargeur = Integer.parseInt(aMap.nextLine());
		aHauteur = Integer.parseInt(aMap.nextLine());
		aTerrain = new Terrain[aLargeur][aHauteur];
		
		for(int i=0; i<aLargeur; i++){
			for(int j=0; j<aHauteur; j++){
				aTerrain[i][j] = new Terrain(i, j, 0, 0, TypeTerrain.PLAINE);
			}
		}		
		
		int id, x, y;
		String ligne = "";
		String tab[] = null;
		
		while(aMap.hasNextLine()){
			
			ligne = aMap.nextLine();
			tab = ligne.split(":");
			id = Integer.parseInt(tab[0]);
			x = Integer.parseInt(tab[1]);
			y = Integer.parseInt(tab[2]);			
			
			switch(id){
			case 1: aTerrain[x][y] = new Terrain(x, y, 0, 0, TypeTerrain.FORET); break;
			case 2: aTerrain[x][y] = new Terrain(x, y, 0, 0, TypeTerrain.MONTAGNE); break;
			default: aTerrain[x][y] = new Terrain(x, y, 0, 0, TypeTerrain.PLAINE);
			}
		}
				
		aTerrain[30][10].setUnite(new Unite(30,10,1,100,TypeUnite.INFANTERIE,TypeAttaque.OMEGA_SLASH_DE_L_ULTIME_APOLLON,7,1.0, "pied"));
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
    public int getHauteur()
    {
        return aHauteur;
    }
    
    /**
     * Accesseur qui renvoi la valeur de la largeur du plateau
     * @return aLargeur 
     */
    public int getLargeur()
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
