/**
 * @author rubeus
 *
 */
public class Slatch {

	public static IHM ihm;
  public static Partie partie;
  public static Moteur moteur;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
    partie = new Partie();
    moteur = new Moteur();
    ihm = new IHM(800,500,"Partie");
	}
}