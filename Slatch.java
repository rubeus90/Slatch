/**
 * @author Jonathan
 * @version 1.0
 */
public class Slatch
{
    private IHM myIHM;
    public static Slatch mySlatch;
    public Slatch () {
        myIHM = new IHM(800,500,"Partie");
        setSlatch();
    }
    public void setSlatch()
    {
        mySlatch=this;
    }
    public static Slatch getSlatch()
    {
        return mySlatch;
    }
    public IHM getIHM() {
        return myIHM;
    }
}
