import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public enum TypeAttaque
{
    OMEGA_SLASH_DE_L_ULTIME_APOLLON(250, "Omega Slash de l'ultime Apollon", "Une série de coups frénétiques qui laisseront tous les ennemis sur le tapis",PorteeAttaque.COURTE), // en attente de créer les vraies attaques
    CANON(15, "canon", "Envoie un obus sur votre ennemi. Efficace contre l'infanterie.",PorteeAttaque.COURTE),
    GROSCANON(20,"groscanon","Envoi un gros obus sur votre ennemi", PorteeAttaque.COURTE),
    FUSIL(10, "fusil", "Envoie une salve de balles sur votre ennemi.",PorteeAttaque.COURTE),
    MORTIER(15, "mortier","Envoi un motier sur votre adversaire",PorteeAttaque.COURTE),
    ROQUETTE(20,"roquette","Envoie des roquettes sur votre ennemi",PorteeAttaque.MOYENNE),
    MISSILE(15,"missile","Envoie des missiles sur votre ennemi.", PorteeAttaque.LONGUE),
    SOIN(5,"soin","Soigne une unite",PorteeAttaque.COURTE);
   
    private int aDegats; // dégâts de base de l'attaque
    private String aNom;
    private String aDescription;
    public PorteeAttaque aTypePortee;
    public HashMap<TypeUnite, Double> efficacite;// on multipliera l'attaque par ce nombre, donc 1 est l'élément neutre, quand c'est plus grand que 1 on a une efficacité plus grande, et réciproquement
   
   
    TypeAttaque(int pDegats, String pNom, String pDescription ,final PorteeAttaque pTypePortee ) // ajouter PorteeAttaque pPortee
    {
        aDegats = pDegats;
        //efficacite.put(TypeUnite.NAVAL, 1.5);
        //efficacite = pEfficacite;
        aNom = pNom;
        aDescription = pDescription;
        efficacite= new HashMap<TypeUnite,Double> ();
        aTypePortee = pTypePortee;
        Scanner fichier = null;
       
       
        try {
                fichier = new Scanner(getClass().getClassLoader().getResource("Config/TypeAttaque.txt").openStream());
                } catch (IOException e) {
                        e.printStackTrace();
                }  
       
        String ligne;
        String[] tab;
        while(fichier.hasNextLine()){
            ligne = fichier.nextLine();
            tab = ligne.split(":");
            if(tab[0].equals(pNom)){
                for(TypeUnite type : TypeUnite.values()) {
                   
                    if(type.getNom().equals(tab[1])) {
                        efficacite.put(type,Double.parseDouble(tab[2]));break;
                    }
                }
            }
        }
        fichier.close();
    }
     /**
     * Accesseur pour l'attribut aNom
     * @return aNom
     */
    public String getNom()
    {
        return this.aNom;
    }
   
    /**
     * Accesseur pour l'attribut aDescription
     * @return aDescription
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    public PorteeAttaque getTypePortee()
    {
        return this.aTypePortee;
    }
   
    /**
     * Accesseur pour l'attribut aDegat
     * @return aDegat
     */
    public int getDegats()
    {
        return aDegats;
    }
   
    /**
     * Mutateur pour l'attribut aDegat
     * @param pDegat
     */
    public void setDegats(int pDegat)
    {
        aDegats=pDegat;
    }
   
    /**
     * Mutateur pour l'attribut aNom
     * @param pNom
     */
    public void setNom(String pNom)
    {
        this.aNom = pNom;
    }
   
    /**
     * Mutateur pour l'attribut aDescription
     * @param pDescription
     */
    public void setDescription(String pDescription)
    {
        this.aDescription = pDescription;
    }


}
