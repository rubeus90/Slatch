/**
 * @author rubeus
 *
 */
public class Slatch {

    public static Slatch slatch;
    public static IHM ihm;
    public static Partie partie;
    public static Moteur moteur;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        slatch = new Slatch();
    }
    
    /**
     * @param args
     */
    public Slatch() {
        partie = new Partie(2, 20, 30, null);
        moteur = new Moteur();
        ihm = new IHM(802,524);
    }
}