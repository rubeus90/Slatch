import java.util.HashMap;
import java.lang.Integer;

public enum TypeTerrain {
	FORET("foret", "arbres02.png", "une foret", 2), 
	PLAINE("plaine", "plaine02.png", "une plaine", 1), 
	MONTAGNE("montagne", "Montagne01.png", "une montagne", 3);
	
	private String aNom;
	private String aImage;
	private String aDescription;
	private int aCouverture;
	public HashMap<String,Integer> aCoutDeplacement;
	
	TypeTerrain(final String pNom, final String pImage, final String pDescription, final int pCouverture){
		aNom = pNom;
		aImage = pImage;
		aDescription = pDescription;
		aCouverture = pCouverture;
		aCoutDeplacement = new HashMap<String,Integer> ();
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
