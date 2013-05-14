import java.util.HashMap;
import java.lang.Integer;

/**
 * Cette classe gere les terrains du jeu, qui comporte les differents types de terrains et
 * les batiments
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
    
    public String getType(){
        return aType;
    }
    
    public int getCouverture(){
        return aCouverture;
    }
    
    public HashMap<String,Integer> getCoutDeplacement(){
        return aCoutDeplacement;
    }     
}

