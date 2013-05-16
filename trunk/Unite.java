import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/****************************************
 *
 * CLASSE UNITE
 * Gere les caractéritistiques des unitées
 *
 * @author Thibault
 * @version 1.0
 *
 */
public class Unite extends Entite
{
    // instance variables - replace the example below with your own
    private TypeUnite aType; // Correspond au type d'unité : Infanterie, Véhicule etc ...
    private TypeAttaque aAttaque; // Correspond à l'attaque au corps à corps
    private int aVieActuelle; //Correspond aux nombres de points de vie de l'unite
    private int aPorteeDeplacement; // Coresspond au déplacement maximum que peut effectuer l'unité
    private int aLvl; // Correspond au niveau de l'unité
    private int aExperience; // Correspond à l'expérience total de l'unité
    private int aExperienceMax;
    private double aGain; //Compris entre 1 et 2, correspondant au pourcentage d'augmentation des caractéristique à chaque monté de niveau
    private String aTypeDeplacement;
    
    /**
     * Constructeur de la classe Unite
     * Prend en paramètre :
     * un string correspondant au type d'unité
     * Un int correspondant au dégat au corps à corps
     * Un int correspondant au déplacement maximum
     * Un int correspondant au gain de chaque monté de niveau
     */
    public Unite(final int pX,final int pY,final int pJoueur,final int pPointDeVie,final TypeUnite pType,final TypeAttaque pAttaque,final int pDeplacement, final double pGain, final String typeDeplacement)
    {
       super(pX,pY,pJoueur,pPointDeVie);
       aType = pType;
       aAttaque = pAttaque;
       aPorteeDeplacement = pDeplacement;
       aGain = pGain;
       aVieActuelle = pPointDeVie;
       aLvl = 0;
       aExperience = 0;
       aExperienceMax=100;
       aTypeDeplacement = typeDeplacement;
    }

    /**
     * Accesseur qui renvoie la valeur du aTypeDeplacement
     * @return aTypeDeplacement
     */
    public String getTypeDeplacement(){
        return aTypeDeplacement;
    }
    
    /**
     * Accesseur qui renvoie l'attaque au corps à corps
     * @return aAttaque
     */
    public TypeAttaque getAttaque(){
        return aAttaque;
    }
    
    /**
     * Accesseur qui renvoie la valeur du nombre de point de vie de l'unite
     * @return aVie
     */
    public int getVie(){
        return aVieActuelle;
    }
    
    /**
     * Accesseur qui renvoie l'expérience total de l'unite
     * @return aExperience
     */
    public int getExperience(){
        return aExperience;
    }
    
    /**
     * Accesseur qui renvoie le niveau de l'unite
     * @return aLvl
     */
    public int getLvl(){
        return aLvl;
    }
    
    /**
     * Accesseur qui renvoi le deplacement maximum de l'unite
     * @return aPorteeDeplacement
     */
    public int getPorteeDeplacement(){
        return aPorteeDeplacement;
    }
    
    /**
     * Accesseur qui renvoi le type de l'unite
     * @return aType
     */
    public TypeUnite getType(){
        return aType;
    }
    
     /**
     * Methode qui permet l'augmentation ou la diminution de l'experience
     * @param pExperience
     */
    public void addExperience(final int pExperience){
        aExperience+=pExperience;
    }
    
    /*******
     * Methode qui permet l'augmentation ou la diminution de la vie
     * @param pVie
     */
    public void addVie(final int pVie){
        aVieActuelle+=pVie;
    }
    
    
    /*******
     * Methode qui permet a une unite de monter de niveau
     */
    public void upLvl(){
        if(aExperience < aExperienceMax ){ 
            System.out.println("Experience inferieur a "+aExperienceMax);
            return;
        }
        else if(aLvl >=3){
             System.out.println("Niveau superieur ou egal a 3");
             return;
        }
       aLvl++;
       aExperience-=aExperienceMax;
       setPointDeVie((int)(getPointDeVie()*aGain));
       aAttaque.degats= (int)(aAttaque.degats *aGain);
       aPorteeDeplacement = (int)(aPorteeDeplacement*aGain);
    }
    
    @Override
    public void dessine (final Graphics g) {
        int pPosHautGaucheX = super.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheY = super.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
        int pPosBasDroiteX = (super.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (super.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
        try {
            Image img = ImageIO.read(new File("Images/"+aType.getImage()));
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(getSurbrillance()) {
            try {
                Image img = ImageIO.read(new File("Images/5.png"));
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
