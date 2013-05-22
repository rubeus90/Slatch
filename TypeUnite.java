/*
 * Définit le type de l'unité, servira pour de nombreux tests
 */
public enum TypeUnite
{
    COMMANDO("commando", "une infanterie","commando",100), 
    //DEMOLISSEUR("demolisseur", "une infanterie","demolisseur"), 
    //CHAR("char","un char","char"),
    TANK("tank","un tank","tank",500),
    //INGENIEUR("ingenieur","un ingenieur formidable","ingenieur"),
    //DISTANCE("distance","un vehicule a moyenne distance","distance"),
    UML("uml","Ultmiate missile launcher","uml",300);

    private String aNom;
    private String aDescription;
    private String aImage;
    private int aPrix;
    
    TypeUnite(final String pNom,final String pDescription,final String pImage,final int pPrix){
        aNom=pNom;
        aDescription =pDescription;
        aImage = pImage;
        aPrix = pPrix;
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

}
