import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;

public enum PorteeAttaque {
    COURTE("courte","ne peut attaquer que les unités etant sur les cases adjacentes",1,1 ), 
    MOYENNE("moyenne","peut attaquer les unités à moyenne distance",1,3), 
    LONGUE("longue","peut attaquer les unités à longues distance",2, 6);
    
    private String aNom;
    private String aDescription;
    private int aPorteeMax;
    private int aPorteeMin;
    
    /**
     * Constructeur des Portees d'attaque
     */
    PorteeAttaque(final String pNom,final String pDescription,final int pPorteeMin, final int pPorteeMax){
        aNom = pNom;
        aDescription = pDescription;
        aPorteeMax = pPorteeMax;
        aPorteeMin = pPorteeMin;
    }
    
    /**
     * Accesseur pour l'attribut aNom
     * @return aNom
     */
    public String getNom(){
        return aNom;
    }
    
    /**
     * Accesseur pour l'attribut aPorteeMax
     * @return aPorteeMax
     */
    public int getPorteeMax(){
        return aPorteeMax;
    }
    
    /**
     * Accesseur pour l'attribut aPorteeMin
     * @return aPorteeMin
     */
    public int getPorteeMin(){
        return aPorteeMin;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public String getDescription(){
        return aDescription;
    }
}