/**
 * @author Jonathan
 * @version 1.0
 */
public class Slatch
{
    private static IHM myIHM;
    public Slatch () {
        myIHM = new IHM(800,500,"Partie");
    }
    public static IHM getIHM() {
        return myIHM;
    }
}
