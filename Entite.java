import java.awt.Image;
import java.awt.Graphics;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Classe Entite : Unite ou Terrain
 * @author Jonathan
 * @version 1.0
 */
public abstract class Entite 
{
    private int aCoordonneeX;       //Coordonnee en X dans la matrice du Jeu
    private int aCoordonneeY;       //Coordonnee en Y dans la matrice du Jeu
    private int aPointDeVie;        //Point de vie de l'Entite : 0 par defaut
    private int aJoueur;            //Numero du joueur
    private boolean aSurbrillance;            //Numero du joueur
    
    /**
     * Constructeur d'une Entite
     * @param pCoordonneeX
     * @param pCoordonneeY
     * @param pJoueur
     * @param pPointDeVie
     * @param pNom
     * @param pImage
     * @param pDescription
     */
    public Entite(
        final int pCoordonneeX,
        final int pCoordonneeY,
        final int pJoueur,
        final int pPointDeVie) 
    {
        this.aCoordonneeX = pCoordonneeX;
        this.aCoordonneeY = pCoordonneeY;
        this.aPointDeVie = pPointDeVie;
        this.aJoueur = pJoueur;
        this.aSurbrillance=false;
    }
    
    /**
     * Permet de deplacer une unite en modifiant ses coordonnées
     * @param pNouvX
     * @param pNouvY
     */
    private void deplacer (final int pNouvX, final int pNouvY)
    {
        setCoordonneeX(pNouvX);
        setCoordonneeY(pNouvY);
    }
    
    public abstract void dessine (final Graphics g);
    
    
    //***********************************/
    //***   Accesseurs  &  Mutateurs  ***/
    //***********************************/
    
    /**
     * Accesseur
     * @return aCoordonneeX
     */
    public int getCoordonneeX()
    {
        return this.aCoordonneeX;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeX
     */
    public void setCoordonneeX(final int pCoordonneeX)
    {
        this.aCoordonneeX = pCoordonneeX;
    }
    
    /**
     * Accesseur
     * @return aCoordonneeY
     */
    public int getCoordonneeY()
    {
        return this.aCoordonneeY;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeY
     */
    public void setCoordonneeY(final int pCoordonneeY)
    {
        this.aCoordonneeY = pCoordonneeY;
    }
    
        /**
     * Accesseur
     * @return aJoueur
     */
    public int getJoueur()
    {
        return this.aJoueur;
    }
    
    /**
     * Mutateur
     * @param pJoueur
     */
    public void setJoueur(final int pJoueur)
    {
        this.aJoueur = pJoueur;
    }
    
     /**
     * Accesseur
     * @return aPointDeVie
     */
    public int getPointDeVie()
    {
        return this.aPointDeVie;
    }
    
    /**
     * Mutateur
     * @param pPointDeVie
     */
    public void setPointDeVie(final int pPointDeVie)
    {
        this.aPointDeVie = pPointDeVie;
    }
    
     /**
     * Accesseur
     * @return aPointDeVie
     */
    public boolean getSurbrillance()
    {
        return this.aSurbrillance;
    }
    
    /**
     * Mutateur
     * @param pPointDeVie
     */
    public void setSurbrillance(final boolean pSurbrillance)
    {
        this.aSurbrillance = pSurbrillance;
    }
}
