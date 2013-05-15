
public enum TypeTerrain {
	FORET(0,0,"foret", "Images/1.png", "une foret", 2, 2), 
	PLAINE(0,0,"plaine", "Images/2.png", "une plaine", 1, 1), 
	MONTAGNE(0,0,"montagne", "Images/3.png", "une montagne", 3, 3);
	
	private int aJoueur;
	private int aPointDeVie;
	private String aNom;
	private String aImage;
	private String aDescription;
	private int aCouverture;
	private int aCoutDeplacement;
	
	TypeTerrain(final int pJoueur, final int pPointDeVie, final String pNom, final String pImage, final String pDescription, final int pCouverture, final int pCoutDeplacement){
		aJoueur = pJoueur;
		aPointDeVie = pPointDeVie;
		aNom = pNom;
		aImage = pImage;
		aDescription = pDescription;
		aCouverture = pCouverture;
		aCoutDeplacement = pCoutDeplacement;
	}
	
	public int getJoueur(){
		return aJoueur;
	}
	
	public int getPointDeVie(){
		return aPointDeVie;
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
	
	public int getCoutDeplacement(){
		return aCoutDeplacement;
	}
}
