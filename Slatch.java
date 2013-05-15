/**
 * @author rubeus
 *
 */
public class Slatch {

	public static IHM myIHM;
    public static Partie myPartie;
    public static Moteur myMoteur;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		myPartie = new Partie();
        myMoteur = new Moteur();
        myIHM = new IHM(800,500,"Partie");
	}
}