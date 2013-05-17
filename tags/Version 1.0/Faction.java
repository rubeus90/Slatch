import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Faction désigne un ensemble d'unités qui sont dans le même camp. Elle permet de les retrouver facilement lorsqu'on recrute.
 */
public enum Faction
{
   HUMAINS("HUMAINS"), 
   ROBOTS("ROBOTS"), 
   ALIENS("ALIENS"), 
   NEUTRE("NEUTRE");
   
    public String aNom;
    private List<TypeUnite> aListeUnites;
    
    Faction(final String pNom)
    {
        Scanner fichier = null;
        aListeUnites = new ArrayList<TypeUnite>();
        aNom=pNom;
        try {
            fichier = new Scanner(new File("Faction.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        String ligne;
        String[] tab;
        
        while(fichier.hasNextLine()){
            ligne = fichier.nextLine();
            tab = ligne.    split(":");
            if(tab[0].equals(pNom)){                
                for(TypeUnite type : TypeUnite.values()) {
                    if(type.getNom() == tab[1]) {
                        aListeUnites.add(type);break;
                    }
                }
            }
        }

    }
    
    /**
     * Accesseur pour l'attribut aListeUnites contenant la liste des Unite
     * @return aListeUnites
     */
    public List<TypeUnite> getListe()
    {
        return aListeUnites;
    }
    
    /**
     * Accesseur pour l'attribut aNom
     * @return aNom
     */
    public String getNom()
    {
        return aNom;
    }
}
