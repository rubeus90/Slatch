import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

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
        try {
			partie = new Partie(2, 20, 30, new Scanner(new File("./trunk/map.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        moteur = new Moteur();
        ihm = new IHM(802,524);
    }
}