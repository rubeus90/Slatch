import java.awt.Image;

/**
 * Classe Entite : Unite ou Terrain
 * 
 * @author Jonathan
 * @version 00
 */
public class Entite 
{
    int aCoordonneeX;
    int aCoordonneeY;
    Image aImage;
    String aNom;
    String aDescription;
    int aPointDeVie;
    int aJoueur;
    
    /**
     * Constructeur d'un Entite
     * @param pCoordonneeX
     * @param pCoordonneeY
     * @param pJoueur
     * @param pPointDeVie
     * @param pNom
     * @param pImage
     * @param pDescription
     */
    public Entite(
        int pCoordonneeX,
        int pCoordonneeY,
        int pJoueur,
        int pPointDeVie,
        String pNom,
        Image pImage,
        String pDescription) 
    {
        aCoordonneeX = pCoordonneeX;
        aCoordonneeY = pCoordonneeY;
        aImage = pImage;
        aNom = pNom;
        aDescription = pDescription;
        aPointDeVie = pPointDeVie;
        aJoueur = pJoueur;
    }
    
    /**
     * Permet de deplacer une unite en modifiant ses coordonnées
     * @param pNouvX
     * @param pNouvY
     */
    void deplacer (int pNouvX, int pNouvY) {
        setaCoordonneeX(pNouvX);
        setaCoordonneeX(pNouvY);
    }
    
    
    //***********************************/
    //***   Accesseurs  &  Mutateurs  ***/
    //***********************************/
    
    /**
     * Accesseur
     * @return aCoordonneeX
     */
    int getaCoordonneeX() {
        return aCoordonneeX;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeX
     */
    void setaCoordonneeX(int pCoordonneeX) {
        aCoordonneeX = pCoordonneeX;
    }
    
    /**
     * Accesseur
     * @return aCoordonneeY
     */
    int getaCoordonneeY() {
        return aCoordonneeY;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeY
     */
    void setaCoordonneeY(int pCoordonneeY) {
        aCoordonneeY = pCoordonneeY;
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
    
     /**
     * Accesseur
     * @return aPointDeVie
     */
    int getaPointDeVie() {
        return aPointDeVie;
    }
    
    /**
     * Mutateur
     * @param pPointDeVie
     */
    void setaPointDeVie(int pPointDeVie) {
        aPointDeVie = pPointDeVie;
    }
}

