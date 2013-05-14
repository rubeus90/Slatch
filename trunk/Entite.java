import java.awt.Image;

/**
 * Classe Entite : Unite ou Terrain
 * @author Jonathan
 * @version 1.0
 */
public class Entite 
{
    private int aCoordonneeX;       //Coordonnee en X dans la matrice du Jeu
    private int aCoordonneeY;       //Coordonnee en Y dans la matrice du Jeu
    private Image aImage;           //Image de l'Entite affichee par l'IHM
    private String aNom;            //Nom de l'Entite
    private String aDescription;    //Description de l'Entite affichee par l'IHM
    private int aPointDeVie;        //Point de vie de l'Entite : 0 par defaut
    private int aJoueur;            //Numero du joueur
    
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
        final int pPointDeVie,
        final String pNom,
        final Image pImage,
        final String pDescription) 
    {
        this.aCoordonneeX = pCoordonneeX;
        this.aCoordonneeY = pCoordonneeY;
        this.aImage = pImage;
        this.aNom = pNom;
        this.aDescription = pDescription;
        this.aPointDeVie = pPointDeVie;
        this.aJoueur = pJoueur;
    }
    
    /**
     * Permet de deplacer une unite en modifiant ses coordonnées
     * @param pNouvX
     * @param pNouvY
     */
    private void deplacer (final int pNouvX, final int pNouvY)
    {
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
    private int getCoordonneeX()
    {
        return this.aCoordonneeX;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeX
     */
    private void setCoordonneeX(final int pCoordonneeX)
    {
        this.aCoordonneeX = pCoordonneeX;
    }
    
    /**
     * Accesseur
     * @return aCoordonneeY
     */
    private int getCoordonneeY()
    {
        return this.aCoordonneeY;
    }
    
    /**
     * Mutateur
     * @param pCoordonneeY
     */
    private void setCoordonneeY(final int pCoordonneeY)
    {
        this.aCoordonneeY = pCoordonneeY;
    }
    
    /**
     * Accesseur
     * @return aImage
     */
    private Image getImage()
    {
        return this.aImage;
    }
    
    /**
     * Mutateur
     * @param pImage
     */
    private void setImage(final Image pImage)
    {
        this.aImage = pImage;
    }

    /**
     * Accesseur
     * @return aNom
     */
    private String getNom()
    {
        return this.aNom;
    }
    
    /**
     * Mutateur
     * @param pDescription
     */
    private void setDescription(final String pDescription)
    {
        this.aDescription = pDescription;
    }
    
     /**
     * Accesseur
     * @return aDescription
     */
    private String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * Mutateur
     * @param pNom
     */
    private void setNom(final String pNom)
    {
        this.aNom = pNom;
    }

    /**
     * Accesseur
     * @return aJoueur
     */
    private int getJoueur()
    {
        return this.aJoueur;
    }
    
    /**
     * Mutateur
     * @param pJoueur
     */
    private void setJoueur(final int pJoueur)
    {
        this.aJoueur = pJoueur;
    }
    
     /**
     * Accesseur
     * @return aPointDeVie
     */
    private int getPointDeVie()
    {
        return this.aPointDeVie;
    }
    
    /**
     * Mutateur
     * @param pPointDeVie
     */
    private void setPointDeVie(final int pPointDeVie)
    {
        this.aPointDeVie = pPointDeVie;
    }
}
