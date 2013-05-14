import java.awt.Image;

/**
 * Cette classe gere les entites du jeu, c'est-a-dire toutes les unites et les terrains
 * 
 * @author Jonathan
 * @version 1.0
 */
public class Entite 
{
    int aCoordonnee_X;
    int aCoordonnee_Y;
    Image aImage;
    String aNom;
    String aDescription;
    int aPointDeVie;
    int aJoueur;
    

    /**
     * Constructeur
     */
    public Entite(
        int pCoordonnee_X,
        int pCoordonnee_Y,
        int pJoueur,
        int pPointDeVie,
        String pNom,
        Image pImage,
        String pDescription) 
    {
        aCoordonnee_X = pCoordonnee_X;
        aCoordonnee_Y = pCoordonnee_Y;
        aImage = pImage;
        aNom = pNom;
        aDescription = pDescription;
        aPointDeVie = pPointDeVie;
        aJoueur = pJoueur;
    }
    
    /**
     * Accesseur
     * @return aCoordonnee_X
     */
    int getaCoordonnee_X() {
        return aCoordonnee_X;
    }
    
    /**
     * Mutateur
     * @param pCoordonnee_X
     */
    void setaCoordonnee_X(int pCoordonnee_X) {
        aCoordonnee_X = pCoordonnee_X;
    }
    
    /**
     * Accesseur
     * @return aCoordonnee_Y
     */
    int getaCoordonnee_Y() {
        return aCoordonnee_Y;
    }
    
    /**
     * Mutateur
     * @param pCoordonnee_Y
     */
    void setaCoordonnee_Y(int pCoordonnee_Y) {
        aCoordonnee_Y = pCoordonnee_Y;
    }
    
    /**
     * Accesseur
     * @return aJoueur
     */
    int getaJoueur() {
        return aJoueur;
    }
    
    /**
     * Mutateur
     * @param pJoueur
     */
    void setaJoueur(int pJoueur) {
        aJoueur = pJoueur;
    }
}
