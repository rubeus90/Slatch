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
    private int aVie; //Correspond aux nombres de points de vie de l'unite
    private int aDeplacement; // Coresspond au déplacement maximum que peut effectuer l'unité
    private int aLvl; // Correspond au niveau de l'unité
    private int aExperience; // Correspond à l'expérience total de l'unité
    private int aExperienceMax;
    private double aGain; //Compris entre 1 et 2, correspondant au pourcentage d'augmentation des caractéristique à chaque monté de niveau

    /**
     * Constructeur de la classe Unite
     * Prend en paramètre :
     * un string correspondant au type d'unité
     * Un int correspondant au dégat au corps à corps
     * Un int correspondant au déplacement maximum
     * Un int correspondant au gain de chaque monté de niveau
     */
    public Unite(final int pX,final int pY,final int pJoueur,final int pPointDeVie,final String pNom,final String pImage, final String pDescription,final TypeUnite pType,final TypeAttaque pAttaque,final int pVie,final int pDeplacement, final double pGain)
    {
       super(pX,pY,pJoueur,pPointDeVie,pNom,pImage,pDescription);
       aType = pType;
       aAttaque = pAttaque;
       aDeplacement = pDeplacement;
       aGain = pGain;
       aVie = pVie;
       aLvl = 0;
       aExperience = 0;
       aExperienceMax=100;
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
        return aVie;
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
     * @return aDeplacement
     */
    public int getDeplacement(){
        return aDeplacement;
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
        aVie+=pVie;
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
       aDeplacement = (int)(aDeplacement*aGain);
    }
       
}
