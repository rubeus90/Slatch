
/**
 * Write a description of class AnimationEvolution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationEvolution extends Animation
{
     private Unite aCible;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationEvolution(final Unite cible)
    {
        aCible = cible;
        aType="evolution";
    }

    
    public void setCible (final Unite att) {aCible = att;}
    
    public Unite getCible() {return aCible;}
}
