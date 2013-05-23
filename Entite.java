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
    private int aPV; //Point de vie actuelle de l'entite
    private int aJoueur;            //Numero du joueur
    private boolean aSurbrillance;            //Numero du joueur
    
    /**
     * Constructeur d'une Entite
     * @param pCoordonneeX
     * @param pCoordonneeY
     * @param pJoueur
     */
    public Entite(final int pCoordonneeX,final int pCoordonneeY,final int pJoueur) 
    {
        this.aCoordonneeX = pCoordonneeX;
        this.aCoordonneeY = pCoordonneeY;
        this.aJoueur = pJoueur;
        this.aSurbrillance=false;
    }
       
    public abstract void dessine(final Graphics g, PanelMatrice pPanel);
    
    
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
     * @return pCoordonneeY
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
     * @return aSurbrillance
     */
    public boolean getSurbrillance()
    {
        return this.aSurbrillance;
    }
    
    /**
     * Mutateur
     * @param pSurbrillance
     */
    public void setSurbrillance(final boolean pSurbrillance)
    {
        this.aSurbrillance = pSurbrillance;
    }
}
