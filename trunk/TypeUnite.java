/*
 * Définit le type de l'unité, servira pour de nombreux tests
 */
public enum TypeUnite
{
    COMMANDO("commando", "une infanterie","commando",100,20,40,"fusil",TypeDeplacement.PIED,1.0, "Terrestre"),
    //DEMOLISSEUR("demolisseur", "une infanterie","demolisseur",200,20,30,"fusil",TypeDeplacement.PIED,1.0), 
    //CHAR("char","un char","char",300,40,60,"canon",TypeDeplacement.PIED,1.0),
    TANK("tank","un tank","tank",500,65,40,"canon",TypeDeplacement.CHENILLES,1.0, "Terrestre"),
    INGENIEUR("ingenieur","un ingenieur formidable","ingenieur",150,15,40,"soin",TypeDeplacement.PIED,1.0, "Terrestre"),
    //DISTANCE("distance","un vehicule a moyenne distance","distance",350,35,50,"roquette",TypeDeplacement.ROUES,1.0),
    UML("uml","Ultimate missile launcher","uml",300,30,50,"missile",TypeDeplacement.CHENILLES,1.0, "Terrestre");

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
    
    
    TypeUnite(final String pNom,final String pDescription,final String pImage,final int pPrix,final int pPVMax,final int pDeplacement,final String pAttaque,final  TypeDeplacement pTypeDeplacement,final double pGain, final String pNomType){
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
    

}
