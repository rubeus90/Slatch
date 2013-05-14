import java.util.HashMap;
import java.lang.Integer;

/**
 * Cette classe gere les terrains du jeu, qui comporte les differents types de terrains et
 * les batiments
 *
 *@author Ngoc
 *@version 1.0
 */


public class Terrain{
    private String aType;
    private int aCouverture;
    private HashMap<String,Integer> aCoutDeplacement;
    private int aVie;
    
    /**
     * Constructeur des terrains
     */
    public Terrain(String pType, int pCouverture, HashMap<String,Integer> pCoutDeplacement){
        aType = pType;
        aCouverture = pCouverture;
        aCoutDeplacement = pCoutDeplacement;
    }
    
    /**
    *Constructeur des batiments (les batiments ont un point de vie en plus comme attribut par rapport
    *aux terrains
    */
    public Terrain(String pType, int pCouverture, HashMap<String, Integer> pCoutDeplacement, int pVie){
        aType = pType;
        aCouverture = pCouverture;
        aCoutDeplacement = pCoutDeplacement;
        aVie = pVie;
    }
    
    /**Retourner le type du terrain (son nom)
    *
    *@return aType
    */
    public String getType(){
        return aType;
    }
    
    /** Retourner la couverture du terrain, c'est a dire la capacite de chaque type de terrain d'augmenter 
    *ou de diminuer certains proprietes de l'unite qui se trouve sur ce type de terrain
    *
    *@return aCouverture
    */
    public int getCouverture(){
        return aCouverture;
    }
    
    /**Retourner le cout de deplacement du terrain. Ce cout varie selon le type du terrain
    *
    *@return aCoutDeplacement
    */
    public HashMap<String,Integer> getCoutDeplacement(){
        return aCoutDeplacement;
    }   
  
    /**Retourner le point de vie des batiments
    *
    *@return aVie
    */
    public int getVie(){
        return aVie;
    }
    
    /**Modifier le point de vie des batiments lors des attaques
    *
    *@param degat
    */
    public void modifieVie(int degat){
        aVie -= degat;
    }
}

