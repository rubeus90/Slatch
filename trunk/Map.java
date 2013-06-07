
/**
 * Enumeration class Map - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Map{
    DOUBLEVAI("DoubleVai", "Une map en double V", "1V1",2,"doublevai", false),
    CHAMPS("Champs","Une map tres petite ou les combats sont intenses !","2V2 - 4",4,"champs",false), 
    HACHEMAP("HacheMap","Il ne faut pas etre une pelle pour gagner","1V1",2,"hacheMap",false), 
    JONATHAN("Jonathan","Map by Jonathan","2V2 - 4",4,"jonathan",false),
    MAPTEST("MapTest","Map de Test","",2,"mapTest",false),
    MAPTEST4("MapTest4","Map de Test","",4,"mapTest4",false),
    PARALLAXE("Parallaxe","Une map de taille moyenne","1V1",2,"Parallaxe",false),
    SELETON("Seleton","Une grande map","3V1",4,"seleton",false),
    NIVEAU1("Mission 1","La carte de la premiere mission de la campagne", "1V1",2,"niveau1",true),
    COULOIR("Couloir","Une map tout en long","1V1",2,"couloir",false");
    
    private String aNom;
    private String aDescription;
    private String aConseil;
    private int aNbrJoueur;
    private String aFichier;
    private boolean aLock;
    
    /**
     * Constructeur des Portees d'attaque
     */
    Map(final String pNom,final String pDescription,final String pConseil,final int pNbrJoueur, final String pFichier,final boolean pLock){
        aNom = pNom;
        aDescription = pDescription;
        aConseil = pConseil;
        aNbrJoueur = pNbrJoueur;
        aFichier = pFichier;
        aLock=pLock;
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
    public String getDescription(){
        return aDescription;
    }
    
    /**
     * Accesseur pour l'attribut aPorteeMin
     * @return aPorteeMin
     */
    public String getConseil(){
        return aConseil;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public int getNbrJoueur(){
        return aNbrJoueur;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public String getFichier(){
        return aFichier;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public boolean isVerrouille(){
        return aLock;
    }
    
    
}
