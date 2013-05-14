import java.util.HashMap;
/**
 * Gère les attaques au corps à corps entre les unités.
 * 
 * @author Haynner
 * @version 14/05/13
 */
public class AttaqueProche
{
    private String aNom;
    private HashMap<String,Integer> aTabMalusBonus;
    private String aDescription;

    /**
     * Constructeur.
     */
    public AttaqueProche(String pNom, String pDescription)
    {
        this.aNom = pNom;
        this.aDescription = pDescription;
        return;
    }
    
    /**
     * Accesseurs et Mutateurs.
     */
    public String getNomAP()
    {
        return this.aNom;
    }
    
    public String getDescriptionAP()
    {
        return this.aDescription;
    }
    
    public void setNomAP(String pNom)
    {
        this.aNom = pNom;
        return;
    }
    
    public void setDescriptionAP(String pDescription)
    {
        this.aDescription = pDescription;
        return;
    }
   
}
