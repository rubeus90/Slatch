import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;
import java.util.ArrayList;

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
    private int[] batimentJoueur;
    private Scanner aMap;
    private Terrain[][] aTerrain;
    

    /**
     * Constructor for objects of class Partie
     */
    public Partie(final int pRevenuBatiment,final int pTourMax, final String pMap)
    {
    	try {
			aMap = new Scanner(new File(pMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        chargerMap();
         
        ArrayList<Joueur> ListeJoueur = new ArrayList<Joueur>();
        Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
        ListeJoueur.add(JoueurNeutre);
        int i;
        for(i=1;i<=aNbrJoueur;i++)
        {
            ListeJoueur.add(new Joueur(i,Faction.HUMAINS,batimentJoueur[i]));
        }        
        
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment;
       
    }
    
    public void chargerMap(){
    	aLargeur = Integer.parseInt(aMap.nextLine());
		aHauteur = Integer.parseInt(aMap.nextLine());
		aNbrJoueur = Integer.parseInt(aMap.nextLine());
		
		aTerrain = new Terrain[aLargeur][aHauteur];
		
		for(int i=0; i<aLargeur; i++){
			for(int j=0; j<aHauteur; j++){
				aTerrain[i][j] = new Terrain(i, j, 0, 0, TypeTerrain.PLAINE);
			}
		}		
		
		int x, y, joueur;
		String id;
		String ligne = "";
		String tab[] = null;
		int batimentJoueur[] = new int[aNbrJoueur];
		for(int i=0; i<aNbrJoueur; i++){
			batimentJoueur[i] = 0;
		}
		
		while(aMap.hasNextLine()){
			
			ligne = aMap.nextLine();
			tab = ligne.split(":");
			id = tab[0];
			x = Integer.parseInt(tab[1]);
			y = Integer.parseInt(tab[2]);	
			joueur = Integer.parseInt(tab[3]);
			
			switch(id){
			case "foret": aTerrain[x][y] = new Terrain(x, y, joueur, 0, TypeTerrain.FORET); break;
			case "montagne": aTerrain[x][y] = new Terrain(x, y, joueur, 0, TypeTerrain.MONTAGNE); break;
			/*case "batiment": {
				aTerrain[x][y] = new Terrain(x, y, joueur, 10, TypeTerrain.BATIMENT); 
				batimentJoueur[joueur]+=1;
				break;
			}*/
			default: aTerrain[x][y] = new Terrain(x, y, joueur, 0, TypeTerrain.PLAINE);
			}
		}
				
		aTerrain[5][5].setUnite(new Unite(5,5,1,100,TypeUnite.INFANTERIE,TypeAttaque.CANON,7,1.0, "pied"));
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
    
    /*
     *Retourne un tableau comportant le nombre de batiment par joueur 
     * tab[0] correspond au joueur neutre
     * tab[1] au joueur 1
     * etc ...
     */
    public int[] getBatimentJoueur(){
    	return batimentJoueur;
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
