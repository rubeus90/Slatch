
/****************************************
 * 
 * CLASSE UNITE
 * Gere les caractéritistiques des unitées 
 * 
 * 
 * 
 */
public class Unite
{
    // instance variables - replace the example below with your own
    private String aType; // Correspond au type d'unité : Infanterie, Véhicule etc ...
    private int aAttaque; // Correspond à l'attaque au corps à corps
    private int aDeplacement; // Coresspond au déplacement maximum que peut effectuer l'unité
    private int aLvl; // Correspond au niveau de l'unité
    private int aExperience; // Correspond à l'expérience total de l'unité

    /**
     * Constructeur de la classe Unite
     * Prend en paramètre :
     * un string correspondant au type d'unité
     * Un int correspondant au dégat au corps à corps
     * Un int correspondant au déplacement maximum
     */
    public Unite(String pType,int pAttaque,int pDeplacement)
    {
       aType = pType;
       aAttaque = pAttaque;
       aDeplacement = pDeplacement;
       aLvl = 1;
       aExperience = 0;
    }

    
    /**
     * Accesseur qui renvoi la valeur de l'attaque au corps à corps
     * @return aAttaque
     */
    public int getAttaque(){
        return aAttaque;
    }
    
    /**
     * Accesseur qui renvoi l'expérience total de l'unite
     * @return aExperience
     */
    public int getExperience(){
        return aExperience;
    }
    
    /**
     * Accesseur qui renvoi le niveau de l'unite
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
     * Modificateur de l'Experience
     * Permet lorsque l'unite gagne un combat, d'augmenter son experience
     * @param pExperience
     */
    public void setExperience(int pExperience){
        aExperience+=pExeperience;
        // Une fois que l'unite gagne de l'experience, on regarde si elle peut monter de niveau
        upLvl();
    }
    
    /*******
     * Modificateur de l attaque de l'unite
     * Permet lorsque l'unite monte de niveau d'augmenter son attaque
     * @param pAttaque
     */
    private void setAttaque(int pAttaque){
        aAttaque+=pAttaque
    }
    
    /*******
     * Modificateur du deplacement de l'unites
     * Permet lorsque l'unite monte de niveau d'augmenter son deplacement
     * @param pDeplacement
     */
    private void setDeplacement(int pDeplacement){
        aDeplacement+=pDeplacement;
    }
    
    /*******
     * Methode qui permet a une unite de monter de niveau
     * On part du principe que l'unite monte de niveau tous les 1000xp
     */
    private void upLvl(){
        vDiviseur = (aLvl) * 1000; // On calcul l'experience a avoir pour le niveau suivant
        if(aExperience-vDiviseur>1){ // Si l'experience de l'unite est superieur au l'experience pour le niveau suivant, on monte le niveau de l'unite
            aLvl++;
        }
    }
       
}
