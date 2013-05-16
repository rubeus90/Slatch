import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;

public enum PorteeAttaque {
    COURTE("courte","ne peut attaquer que les unités etant sur les cases adjacentes",1 ), 
    MOYENNE("moyenne","peut attaquer les unités à moyenne distance",3), 
    LONGUE("longue","peut attaquer les unités à longues distance", 6);
    
    private String aNom;
    private String aDescription;
    private int aPortee;
    
    
    PorteeAttaque(final String pNom,final String pDescription,final int pPortee){
        aNom = pNom;
        aDescription = pDescription;
        aPortee = pPortee;
    }
    
    public String getNom(){
        return aNom;
    }
    
    public int getPortee(){
        return aPortee;
    }
    
    public String getDescription(){
        return aDescription;
    }
}
