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
        setCoordonneeX(pNouvX);
        setCoordonneeY(pNouvY);
    }
    
    
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
     * @return aImage
     */
    public Image getImage()
    {
        return this.aImage;
    }
    
    /**
     * Mutateur
     * @param pImage
     */
    public void setImage(final Image pImage)
    {
        this.aImage = pImage;
    }

    /**
     * Accesseur
     * @return aNom
     */
    public String getNom()
    {
        return this.aNom;
    }
    
    /**
     * Mutateur
     * @param pDescription
     */
    public void setDescription(final String pDescription)
    {
        this.aDescription = pDescription;
    }
    
     /**
     * Accesseur
     * @return aDescription
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * Mutateur
     * @param pNom
     */
    public void setNom(final String pNom)
    {
        this.aNom = pNom;
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
}
