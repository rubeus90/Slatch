import java.awt.Image;
import java.awt.Graphics;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Classe Entite : Unite ou Terrain
 * @author Jonathan
 * @version 1.0
 */
public class Entite 
{
    private int aCoordonneeX;       //Coordonnee en X dans la matrice du Jeu
    private int aCoordonneeY;       //Coordonnee en Y dans la matrice du Jeu
    private String aURLimage;       //URL de l'Image de l'Entite affichee par l'IHM
    private String aNom;            //Nom de l'Entite
    private String aDescription;    //Description de l'Entite affichee par l'IHM
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
        final int pPointDeVie,
        final String pNom,
        final String pURLimage,
        final String pDescription) 
    {
        this.aCoordonneeX = pCoordonneeX;
        this.aCoordonneeY = pCoordonneeY;
        this.aURLimage = pURLimage;
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
    
    public void dessine (final Graphics g) {
        int pPosHautGaucheX = aCoordonneeX*Slatch.myIHM.getmyPanel().getaLargeurCarreau();
        int pPosHautGaucheY = aCoordonneeY*Slatch.myIHM.getmyPanel().getaHauteurCarreau() + Slatch.myIHM.getmyPanel().getDECALAGE_PX_EN_Y();
        int pPosBasDroiteX = (aCoordonneeX+1)*Slatch.myIHM.getmyPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (aCoordonneeY+1)*Slatch.myIHM.getmyPanel().getaHauteurCarreau() + Slatch.myIHM.getmyPanel().getDECALAGE_PX_EN_Y();
        try {
            Image img = ImageIO.read(new File("Images/"+aURLimage));
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.myIHM.getmyPanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        if(aSurbrillance) {
            try {
                Image img = ImageIO.read(new File("Images/5.png"));
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.myIHM.getmyPanel());
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public String getImage()
    {
        return this.aURLimage;
    }
    
    /**
     * Mutateur
     * @param pImage
     */
    public void setImage(final String pURLimage)
    {
        this.aURLimage = pURLimage;
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
    
     /**
     * Accesseur
     * @return aPointDeVie
     */
    public boolean getaSurbrillance()
    {
        return this.aSurbrillance;
    }
    
    /**
     * Mutateur
     * @param pPointDeVie
     */
    public void setaSurbrillance(final boolean pSurbrillance)
    {
        this.aSurbrillance = pSurbrillance;
    }
}
