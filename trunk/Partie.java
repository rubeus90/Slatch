import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Integer;
import java.net.URL;
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
    private Scanner aMap;
    private Terrain[][] aTerrain;
    private int aJoueurActuel;
    private int aTour;
    

    /**
     * Constructor for objects of class Partie
     */
    public Partie(final int pRevenuBatiment,final int pTourMax, final String pMap)
    {
        //Dans le cas ou le fichier map n'existe pas
        aJoueurActuel= 1;
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment;
        
        
         //            aMap = new Scanner(new File(pMap));
        try {
			aMap = new Scanner(getClass().getClassLoader().getResource(pMap).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}        
        
        initMap();
    } 
    
    /**
     * Methode qui permet le chargement d'une carte depuis un fichier texte et créé les Joueurs
     * 
     */
    private void initMap(){
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
        
        int vX, vY, vJoueur;
        String vId;
        String ligne = "";
        String tab[] = null;

        int vBatimentJoueur[] = new int[aNbrJoueur+1]; //aNbrJoueur +1 pour prendre en compte le jouer Neutre

        //On initialise le tableau de batiment à 0 pour chaque joueur
        for(int i=0; i<aNbrJoueur; i++){
            vBatimentJoueur[i] = 0;
        }
        
        List<Unite> lUnite = new ArrayList<Unite>();
        
        //On lit le fichier et on l'analyse
        while(aMap.hasNextLine()){
            ligne = aMap.nextLine();
            tab = ligne.split(":");
            vId = tab[0];
            vX = Integer.parseInt(tab[1]);
            vY = Integer.parseInt(tab[2]);   
            vJoueur = Integer.parseInt(tab[3]);
            
            System.out.println(vId);
            
            switch(vId){
                case "foret": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, 0, TypeTerrain.FORET); break;
                case "montagne": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, 0, TypeTerrain.MONTAGNE); break;
                case "batiment":
                    aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, 10, TypeTerrain.BATIMENT); 
                    vBatimentJoueur[vJoueur]+=1;
                    break;
                case "commando": 
                    Unite vcommando = new Unite(vX,vY,vJoueur,20,TypeUnite.COMMANDO,TypeAttaque.CANON,4,1.0, TypeDeplacement.PIED);
                    lUnite.add(vcommando);
                    aTerrain[vX][vY].setUnite(vcommando); 
                    break;
                case "demolisseur": 
                    Unite demolisseur = new Unite(vX,vY,vJoueur,20,TypeUnite.DEMOLISSEUR,TypeAttaque.CANON,3,1.0, TypeDeplacement.PIED);
                    lUnite.add(demolisseur);
                    aTerrain[vX][vY].setUnite(demolisseur); 
                    break;
                case "tank":
                    Unite vtank = new Unite(vX,vY,vJoueur,65,TypeUnite.TANK,TypeAttaque.CANON,4,1.0, TypeDeplacement.CHENILLES);
                    lUnite.add(vtank);
                    aTerrain[vX][vY].setUnite(vtank); 
                    break;
                case "char":
                    Unite vchar = new Unite(vX,vY,vJoueur,40,TypeUnite.CHAR,TypeAttaque.CANON,6,1.0, TypeDeplacement.CHENILLES);
                    lUnite.add(vchar);
                    aTerrain[vX][vY].setUnite(vchar); 
                    break;
                case "ingenieur":
                    Unite ingenieur = new Unite(vX,vY,vJoueur,15,TypeUnite.INGENIEUR,TypeAttaque.CANON,4,1.0, TypeDeplacement.CHENILLES);
                    lUnite.add(ingenieur);
                    aTerrain[vX][vY].setUnite(ingenieur); 
                    break;
                case "distance":
                    Unite distance = new Unite(vX,vY,vJoueur,35,TypeUnite.DISTANCE,TypeAttaque.CANON,5,1.0, TypeDeplacement.CHENILLES);
                    lUnite.add(distance);
                    aTerrain[vX][vY].setUnite(distance); 
                    break;
                case "uml":
                    Unite uml = new Unite(vX,vY,vJoueur,30,TypeUnite.UML,TypeAttaque.CANON,5,1.0, TypeDeplacement.CHENILLES);
                    lUnite.add(uml);
                    aTerrain[vX][vY].setUnite(uml); 
                    break;
            default: aTerrain[vX][vY] = new Terrain(vX, vY, 0, 0, TypeTerrain.PLAINE);
            }
        }
        
        ListeJoueur = new ArrayList<Joueur>();
        Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
        ListeJoueur.add(JoueurNeutre);
        
        //Ajout des joueur dans l'arrayList
        for(int i=1;i<=aNbrJoueur;i++)
        {
            ListeJoueur.add(new Joueur(i,Faction.HUMAINS,vBatimentJoueur[i]));
            ListeJoueur.get(i).benefTour(aRevenuBatiment);
        }
        ListeJoueur.get(1).benefTour(aRevenuBatiment);
        for(Unite vUniteActuel : lUnite){
        	int vJ = vUniteActuel.getJoueur();
            ListeJoueur.get(vJ).getListeUnite().add(vUniteActuel);
        }
    }
    
    public void tourSuivant(){
        if(aJoueurActuel == aNbrJoueur)
        {
            aJoueurActuel = 1;
            aTour++;
        }
        else
            aJoueurActuel++;
        
        ListeJoueur.get(aJoueurActuel).benefTour(aRevenuBatiment);            
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
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public Terrain[][] getTerrain()
    {
        return aTerrain;
    }
    
    /**
     * Accesseur qui renvoi le joueur actuel
     * @return aJoueurActuel
     */
    public int getJoueurActuel(){
        return aJoueurActuel;
    }
    
    /**
     * Accesseur qui prend en parametre un id de joueur et renvoi un joueur
     * @return aJoueur
     */
    public Joueur getJoueur(final int pJoueur){
        return  ListeJoueur.get(pJoueur);
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
    
    /**
     * Mutateur qui modifie la valeur de l'attribut aJoueurActuel
     * @param pJoueurActuel
     */
    public void setJoueurActuel(final int pJoueurActuel){
        aJoueurActuel = pJoueurActuel;
    }
    
}
