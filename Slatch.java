/**
 * @author Jonathan
 * @version 1.0
 */
public class Slatch
{
    public static IHM myIHM;
    public static Partie myPartie;
    public static Moteur myMoteur;
    public Slatch () 
    {
        myPartie = new Partie();
        myMoteur = new Moteur();
        myIHM = new IHM(800,500,"Partie");
    }
    
}
