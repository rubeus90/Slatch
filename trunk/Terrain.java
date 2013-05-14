import java.util.HashMap;
import java.lang.Integer;

/**
 * Cette classe gere les terrains du jeu, qui comporte les differents types de terrains et
 * les batiments
 *
 *@author Ngoc
 */


public class Terrain{
    private String aType;
    private int aCouverture;
    private HashMap<String,Integer> aCoutDeplacement;
    
    /**
     * Constructeur de la classe Terrain
     */
    public Terrain(String pType, int pCouverture, HashMap<String,Integer> pCoutDeplacement){
        aType = pType;
        aCouverture = pCouverture;
        aCoutDeplacement = pCoutDeplacement;
    }
    
    /**Retourner le type du terrain (son nom)
    *
    *@return aType
    */
    public String getType(){
        return aType;
    }
    
    /** Retourner la couverture du terrain, c'est à dire la capacite de chaque type de terrain d'augmenter ou de diminuer 
    *certains proprietes de l'unite qui se trouve sur ce type de terrain
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
}

