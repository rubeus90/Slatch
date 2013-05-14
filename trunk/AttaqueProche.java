import java.util.HashMap;
/**
 * Gere les attaques au corps a corps entre les unites.
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
     * Les Attaques possèdent un Nom, une Description, et des rapports de force.
     */
    
    public AttaqueProche(String pNom, String pDescription)
    {
        this.aNom = pNom;
        this.aDescription = pDescription;
        aTabMalusBonus = new HashMap<String,Integer>();
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
    }
    
    public void setDescriptionAP(String pDescription)
    {
        this.aDescription = pDescription;
    }
   
    /**
     * 
     */
    
    public void setMalusBonus(String pType, int pPuissance)
    {
        aTabMalusBonus.put(pType,pPuissance);
    }
    
    public void getMalusBonus(String pType)
    {
        aTabMalusBonus.get(pType);
    }
}
