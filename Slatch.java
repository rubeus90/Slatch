/**
 * @author Jonathan
 * @version 1.0
 */
public class Slatch
{
    private static IHM myIHM;
    private static Slatch mySlatch;
    public Slatch () {
        myIHM = new IHM(800,500,"Partie");
        setSlatch();
    }
    public void setSlatch()
    {
        mySlatch=this;
    }
    public static IHM getIHM() {
        return myIHM;
    }
}
