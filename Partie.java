import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;
import java.util.List;
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
    private List<Joueur> ListeJoueur;
    private int aRevenuBatiment;
    private int aLargeur;
    private int aHauteur;
    private int aTourMax;
    private int aTour;
    private int batimentJoueur[];
    private Scanner aMap;
    private Terrain[][] aTerrain;
    

    /**
     * Constructor for objects of class Partie
     */
    public Partie(final int pRevenuBatiment,final int pTourMax, final String pMap)
    {
        //Dans le cas ou le fichier map n'existe pas
        try {
            aMap = new Scanner(new File(pMap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        chargerMap();
        
        
        ListeJoueur = new ArrayList<Joueur>();
        Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
        ListeJoueur.add(JoueurNeutre);
        
        //Ajout des joueur dans l'arrayList
        for(int i=1;i<=aNbrJoueur;i++)
        {
            ListeJoueur.add(new Joueur(i,Faction.HUMAINS,batimentJoueur[i]));
        }        
        
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment;
       
    }
    
    /**
     * Methode qui permet le chargement d'une carte depuis un fichier texte
     * 
     */
    public void chargerMap(){
        aLargeur = Integer.parseInt(aMap.nextLine());
        aHauteur = Integer.parseInt(aMap.nextLine());
        aNbrJoueur = Integer.parseInt(aMap.nextLine());
        
        aTerrain = new Terrain[aLargeur][aHauteur];
        
        //On rempli la carte de plaine 
        for(int i=0; i<aLargeur; i++){
            for(int j=0; j<aHauteur; j++){
                aTerrain[i][j] = new Terrain(i, j, 0, 0, TypeTerrain.PLAINE);
            }
        }       
        
        int x, y, joueur;
        String id;
        String ligne = "";
        String tab[] = null;

        batimentJoueur = new int[aNbrJoueur+1]; //aNbrJoueur +1 pour prendre en compte le jouer Neutre

        //On initialise le tableau de batiment à 0 pour chaque joueur
        for(int i=0; i<aNbrJoueur; i++){
            batimentJoueur[i] = 0;
        }
        
        //On lit le fichier et on l'analyse
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
                case "batiment": {
                aTerrain[x][y] = new Terrain(x, y, joueur, 10, TypeTerrain.BATIMENT); 
                batimentJoueur[joueur]+=1;
                break;
            }
            default: aTerrain[x][y] = new Terrain(x, y, joueur, 0, TypeTerrain.PLAINE);
            }
        }
        
        //Ajout d'une unite
        aTerrain[5][5].setUnite(new Unite(5,5,1,100,TypeUnite.INFANTERIE,TypeAttaque.CANON,7,1.0, "pied"));
        aTerrain[25][12].setUnite(new Unite(25,12,2,100,TypeUnite.INFANTERIE,TypeAttaque.CANON,7,1.0, "pied"));
        aTerrain[20][15].setUnite(new Unite(20,15,1,200,TypeUnite.VEHICULE,TypeAttaque.CANON,3,1.0,"chenilles"));
    }

    /**********
     * ACCESSEURS ET MUTATEURS
     *********/
     
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
    
    
     /**
     * Mutateur qui modifie la valeur du tour actuel
     * @param pTour
     */
    public void setTour(final int pTour)
    {
        aTour = pTour;
    }
    
    /**
     * Mutateur qui modifie la valeur des revenues par batiment
     * @param pRevenuBatiment
     */
    public void setRevenuBatiment(final int pRevenuBatiment)
    {
        aRevenuBatiment = pRevenuBatiment;
    }
         
    
}
