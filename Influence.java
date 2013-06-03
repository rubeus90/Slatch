
/**
 * Write a description of class Influence here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Influence
{
    
    public int capture;
    public int defensif;
    public int offensif;
    public int menace;
    public int retraite;

    /**
     * Constructor for objects of class Influence
     */
    public Influence()
    {
        capture=0;
        defensif=0;
        offensif=0;
        menace=0;
        retraite=0;
    }
    
    public Influence(int capture, int defensif, int offensif, int menace, int retraite)
    {
        capture=capture;
        defensif=defensif;
        offensif=offensif;
        menace=menace;
        retraite=retraite;
    }

   
}
