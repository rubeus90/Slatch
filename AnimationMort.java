
/**
 * Write a description of class AnimationMort here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationMort extends Animation
{

    private Unite aCible;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationMort(final Unite cible)
    {
        aCible = cible;
        aType="mort";
    }

    
    public void setCible (final Unite att) {aCible = att;}
    
    public Unite getCible() {return aCible;}
}
