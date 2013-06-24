
/**
 * Write a description of class AnimationSoin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationSoin extends Animation
{
    private int ciblePV;
    private Unite aCible;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationSoin(final Unite cible,final int pvC)
    {
        aCible = cible;
        ciblePV=pvC;
        aType="soin";
    }

    
    public void setCible (final Unite att) {aCible = att;}
    public void setPVcible(final int X) {ciblePV=X;}
    
    public Unite getCible() {return aCible;}
    public int getPVcib() {return ciblePV;}
}
