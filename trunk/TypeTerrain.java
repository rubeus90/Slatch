import java.util.HashMap;
import java.lang.Integer;

public enum TypeTerrain {
	FORET("foret", "1.png", "une foret", 2), 
	PLAINE("plaine", "2.png", "une plaine", 1), 
	MONTAGNE("montagne", "3.png", "une montagne", 3);
	
	private String aNom;
	private String aImage;
	private String aDescription;
	private int aCouverture;
	private HashMap<String,Integer> aCoutDeplacement;
	
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
