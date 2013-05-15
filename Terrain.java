import java.util.HashMap;
import java.lang.Integer;
import java.awt.Image;

/**
 * Cette classe gere les terrains du jeu, qui comporte les differents types de terrains et
 * les batiments
 *
 *@author Ngoc
 *@version 1.0
 */


public class Terrain extends Entite{
    private String aType;
    private int aCouverture;
    private Unite aUnite;
    private HashMap<String,Integer> aCoutDeplacement;
    
    /**
     * Constructeur des terrains et des batiment
     */
    public Terrain(
        final int pX,
        final int pY,
        final int pJoueur,
        final int pPointDeVie,
        final String pNom,
        final String pImage,
        final String pDescription,
        final String pType, 
        final int pCouverture, 
        final HashMap<String,Integer> pCoutDeplacement) 
    {
        super(pX,pY,pJoueur,pPointDeVie,pNom,pImage,pDescription);
        aType = pType;
        aCouverture = pCouverture;
        aCoutDeplacement = pCoutDeplacement;
    }
    
    /**Retourner le type du terrain (son nom)
    *@return aType
    */
    public String getType(){
        return aType;
    }
    
    /**Retourner l'unite se trouvant sur le terrain
    *@return aUnite
    */
    public Unite getUnite(){
        return aUnite;
    }
    
    /** Retourner la couverture du terrain, c'est a dire la capacite de chaque type de terrain d'augmenter 
    *ou de diminuer certains proprietes de l'unite qui se trouve sur ce type de terrain 
    **@return aCouverture
    */
    public int getCouverture(){
        return aCouverture;
    }
    
    /**Attribut une unite à un terrain
    *@return aUnite
    */
    public void setUnite(final Unite pUnite){
        aUnite = pUnite;
    }
    
    /**Retourner le cout de deplacement du terrain. Ce cout varie selon le type du terrain
    *@return aCoutDeplacement
    */
    public HashMap<String,Integer> getCoutDeplacement(){
        return aCoutDeplacement;
    }  
}

