import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;

public enum TypeTerrain {
	FORET("foret", "arbres02.png", "une foret", 2), 
	PLAINE("plaine", "plaine02.png", "une plaine", 1), 
	MONTAGNE("montagne", "Montagne01.png", "une montagne", 3);
	
	private String aNom;
	private String aImage;
	private String aDescription;
	private int aCouverture;
	public HashMap<TypeDeplacement,Integer> aCoutDeplacement;
	TypeTerrain(final String pNom, final String pImage, final String pDescription, final int pCouverture){
		aNom = pNom;
		aImage = pImage;
		aDescription = pDescription;
		aCouverture = pCouverture;
		aCoutDeplacement = new HashMap<TypeDeplacement,Integer> ();
		
		Scanner fichier = null;
		
		try {
			fichier = new Scanner(new File("CoutDeplacement.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String ligne;
		String[] tab;
		
		/*while(fichier.hasNextLine()){
			ligne = fichier.nextLine();
			tab = ligne.split(",");
			if(tab[0] == pNom){
				aCoutDeplacement.put(Slatch.moteur.map.get(tab[1]),Integer.parseInt(tab[2]));
			}
		}*/
		switch(pNom)
		{
		    case "foret": aCoutDeplacement.put(TypeDeplacement.PIED,2); break;
		    case "montagne": aCoutDeplacement.put(TypeDeplacement.PIED,3); break;
		    default: aCoutDeplacement.put(TypeDeplacement.PIED,1);
		}
	}
	
	public String getNom(){
		return aNom;
	}
	
	public String getImage(){
		return aImage;
	}
	
	public String getDescription(){
		return aDescription;
	}
	
	public int getCouverture(){
		return aCouverture;
	}
}
