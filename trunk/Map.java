
/**
 * Enumeration class Map - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Map{
    DOUBLEVAI("DoubleVai", "Une map en double V", "1V1","doublevai",32,18,2,false,false),
    CHAMPS("Champs","Une map tres petite ou les combats sont intenses !","2V2 - 4","champs",12,12,4,false,false), 
    HACHEMAP("HacheMap","Il ne faut pas etre une pelle pour gagner","1V1","hacheMap",20,20,2,false,false), 
    JONATHAN("Jonathan","Map by Jonathan","2V2 - 4","jonathan",48,27,4,false,false),
    MAPTEST("MapTest","Map de Test","","mapTest",32,18,2,false,false),
    MAPTEST4("MapTest4","Map de Test","","mapTest4",32,18,4,false,false),
    PARALLAXE("Parallaxe","Une map de taille moyenne","1V1","Parallaxe",40,15,2,false,false),
    SELETON("Seleton","Une grande map","3V1","seleton",30,30,4,false,false),
    COULOIR("Couloir","Une map tout en long","1V1","couloir",30,13,2,false,false),
    CHAMPSDESERT("Champs","Une map tres petite dans le Desert","2V2 -4","champsdesert",12,12,4,true,false),
    GAGNER("Gagner","Une Map de flemmarde","1V","gagner",10,10,2,false,false),
    FRANMAP("FRANMAP","La carte de la...", "2v2 - 4","franmap",25,25,4,false,false),
    SAHLAHAH("Sahlahah","Bataille pour le desert du milieu", "2v2 - 4","bataillepourledesertdumillieu",32,18,2,true,false),
    NIVEAU1("Mission 1","La carte de la premiere mission de la campagne", "1V1","niveau1",25,15,2,false,true),
    NIVEAU2("Mission 2","La carte de la deuxieme mission de la campagne", "1V1","niveau2",25,13,2,false,true),
    NIVEAU3("Mission 3","La carte de la 3e mission de la campagne", "1V1","niveau3",18,15,2,true,true),
    NIVEAU4("Mission 4","La carte de la 4e mission de la campagne", "1V1","niveau4",18,15,2,true,true),
    NIVEAU5("Mission 5","La carte de la 5e mission de la campagne", "1V1","niveau5",22,18,2,true,true),
    NIVEAU6("Mission 6","La carte de la 6e mission de la campagne", "3V1","niveau6",25,20,4,true,true),
    NIVEAU7("Mission 7","La carte de la 7e mission de la campagne", "1V1","niveau7",26,14,2,false,true),
    NIVEAU8("Mission 8","La carte de la 8e mission de la campagne", "1V1","niveau8",26,14,2,false,true),
    NIVEAU9("Mission 9","La carte de la 9e mission de la campagne", "1V1","niveau9",22,14,2,false,true),
    NIVEAU10("Mission 10","La carte de la 10e mission de la campagne", "1V1","niveau10",28,22,2,false,true),
    NIVEAU11("Mission 11","La carte de la 11e mission de la campagne", "1V1","niveau14",15,15,2,false,true),
    NIVEAU12("Mission 12","La carte de la 12e mission de la campagne", "1V2","niveau12",25,20,3,false,true),
    NIVEAU13("Mission 13","La carte de la 13e mission de la campagne", "1V2","niveau13",25,20,3,false,true),
    NIVEAU14("Mission 14","La carte de la 14e mission de la campagne", "1V1","niveau14",16,16,2,false,true),
    NIVEAU15("Mission 15","La carte de la 15e mission de la campagne", "1V2","niveau15",28,18,3,true,true);
    
    private String aNom;
    private String aDescription;
    private String aConseil;
    private int aNbrJoueur;
    private int aLongueur;
    private int aLargeur;
    private String aFichier;
    private boolean aLock;
    private boolean aDesert;
    
    /**
     * Constructeur des Portees d'attaque
     */
    Map(final String pNom,final String pDescription,final String pConseil, final String pFichier,final int pLongueur,final int pLargeur,final int pNbrJoueur,final boolean pDesert,final boolean pLock){
        aNom = pNom;
        aDescription = pDescription;
        aConseil = pConseil;
        aFichier = pFichier;
        aNbrJoueur = pNbrJoueur;
        aLongueur=pLongueur;
        aLargeur=pLargeur;
        aDesert=pDesert;
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
    public int getLongueur(){
        return aLongueur;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public int getLargeur(){
        return aLargeur;
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
    
     /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public boolean isDesert(){
        return aDesert;
    }
    
    public void setVerrouille(final boolean pLock){
        aLock=pLock;
    }
        
    
    
}
