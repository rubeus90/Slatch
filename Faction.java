import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Faction désigne un ensemble d'unités qui sont dans le même camp. Elle permet de les retrouver facilement lorsqu'on recrute.
 */
public enum Faction
{
   HUMAINS("HUMAINS"), ROBOTS("ROBOTS"), ALIENS("ALIENS");
   
    public String aNom;
    private List<String> aListeUnites;
    
    Faction(final String pNom)
    {
        Scanner fichier = null;
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
			tab = ligne.split(":");
			if(tab[0].equals(pNom)){
				aListeUnites.add(tab[1]);
			}
		}

    }
    
    public List<String> getListe()
    {
        return aListeUnites;
    }
}
