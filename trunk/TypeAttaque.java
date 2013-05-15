import java.util.HashMap;
public enum TypeAttaque
{
	OMEGA_SLASH_DE_L_ULTIME_APPOLON(250, null, "Omega Slash de l'ultime Appolon", "Une série de coups frénétiques qui laisseront tous les ennemis sur le tapis"); // en attente de créer les vraies attaques
	
	int degats; // dégâts de base de l'attaque
	HashMap<TypeUnite, Integer> efficacite; // pourcentage qui permettra de calculer les dégâts finaux
	String nom;
	String description;
	
	TypeAttaque(int pDegats, HashMap<TypeUnite, Integer> pEfficacite, String pNom, String pDescription)
	{
		degats = pDegats;
		efficacite = pEfficacite;
		nom = pNom;
		description = pDescription;
	}
}
