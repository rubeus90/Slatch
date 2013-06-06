/*
 * Définit le type de l'unité, servira pour de nombreux tests
 */
public enum TypeUnite
{
    COMMANDO("Commando", "Capable de capturer un batiment","commando",100,30,40,"fusil",TypeDeplacement.PIED,1.3, "Terrestre",100, true, false),
    DEMOLISSEUR("Demolisseur", "Capable capturer un batiment de faire des degats aux tanks","demolisseur",200,20,30,"mortier",TypeDeplacement.PIED,1.2,"Terrestre",100,true, false), 
    CHAR("Char","un char","char",300,40,60,"canon",TypeDeplacement.CHENILLES,1.3,"Terrestre",110, false, false),
    TANK("While","Unite tres puissante","tank",700,65,40,"groscanon",TypeDeplacement.CHENILLES,1.2, "Terrestre",150, false, false),
    INGENIEUR("Ingenieur","Capable de soigner des unites et de les faire evoluer","ingenieur",100,15,40,"soin",TypeDeplacement.PIED,2.0, "Terrestre",20, true, true),
    DISTANCE("Distance","un vehicule a moyenne distance","distance",350,35,50,"roquette",TypeDeplacement.ROUES,1.3, "Terrestre",110, false, false),
    UML("Uml","Capable de tirer a distance","uml",450,30,40,"missile",TypeDeplacement.CHENILLES,1.3, "Terrestre",110, false, false);

    private String aNom;
    private String aDescription;
    private String aImage;
    private int aPrix;
    private int aPVMax;
    private int aDeplacement;
    private String aAttaque;
    private TypeDeplacement aTypeDeplacement;
    private double aGain;
    public String nomType;
    private int aXPUP;
    private boolean aPeutCapturer;
    private boolean aPeutSoigner;
    
    
    TypeUnite(final String pNom,final String pDescription,final String pImage,final int pPrix,final int pPVMax,final int pDeplacement,final String pAttaque,final  TypeDeplacement pTypeDeplacement,final double pGain, final String pNomType,final int pXPUP, boolean pPeutCapturer, boolean pPeutSoigner){
        aNom=pNom;
        aDescription =pDescription;
        aImage = pImage;
        aPrix = pPrix;
        aPVMax = pPVMax;
        aDeplacement = pDeplacement;
        aAttaque = pAttaque;
        aTypeDeplacement = pTypeDeplacement;
        aGain = pGain;
        this.nomType = pNomType;
        aXPUP= pXPUP;
        this.aPeutCapturer=pPeutCapturer;
        this.aPeutSoigner = pPeutSoigner;
    }
    
    /**
     * Accesseur pour aNom
     * @return aNom
     */
    public String getNom(){
        return aNom;
    }
    
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public String getDescription(){
        return aDescription;
    }
    
    /**
     * Accesseur pour l'attribut aImage
     * @return aImage
     */
    public String getImage(){
        return aImage;
    }
    
    /**
     * Accesseur pour aPrix
     * @return aPrix
     */
    public int getPrix(){
        return aPrix;
    }
    
    /**
     * Accesseur pour aPVMax
     * @return aPVMax
     */
    public int getPVMax(){
        return aPVMax;
    }
    
    /**
     * Accesseur pour aDeplacement
     * @return aDeplacement
     */
    public int getDeplacement(){
        return aDeplacement;
    }
    
    /**
     * Accesseur pour aAttaque
     * @return aAttaque
     */
    public String getAttaque(){
        return  aAttaque;
    }
    
     /**
     * Accesseur pour aTypeDeplacement
     * @return aTypeDeplacement
     */
    public TypeDeplacement getTypeDeplacement(){
        return  aTypeDeplacement;
    }
    
    /**
     * Accesseur pour aDeplacement
     * @return aDeplacement
     */
    public double getGain(){
        return aGain;
    }
    
    /**
     * Accesseur pour aXPUP
     * @return aXPUP
     */
    public int getXPUP(){
        return aXPUP;
    }
    
    
    public boolean peutCapturer()
    {
        return this.aPeutCapturer;
    }

    public boolean peutSoigner()
    {
        return this.aPeutSoigner;
    }
    
    
}
