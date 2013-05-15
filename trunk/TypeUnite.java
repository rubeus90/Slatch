/*
 * Définit le type de l'unité, servira pour de nombreux tests
 */
public enum TypeUnite
{
    INFANTERIE("infanterie", "1.png","une infanterie"), 
    VEHICULE("vehicule","1.png","un vehicule");

    private String aNom;
    private String aDescription;
    private String aImage;
    
    TypeUnite(final String pNom,final String pDescription,final String pImage){
        aNom=pNom;
        aDescription =pDescription;
        aImage = pImage;
    }
    
    public String getNom(){
        return aNom;
    }
    
    public String getDescription(){
        return aDescription;
    }
    
    public String getImage(){
        return aImage;
    }


}
