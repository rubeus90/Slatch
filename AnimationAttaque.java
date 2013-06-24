/**
 * Write a description of class Animation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationAttaque extends Animation
{
    
    private int attaquantPVfin;
    
    private int victimePVfin;
    private Unite aAttaquant;
    private Unite aVictime;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationAttaque(final Unite attaquant, final Unite victime,final int pvA,final int pvV)
    {
        aAttaquant = attaquant;
        aVictime = victime;
        attaquantPVfin=pvA;
        victimePVfin=pvV;
        aType="attaque";
    }

    
    public void setAttaquant (final Unite att) {aAttaquant = att;}
    public void setVictime (final Unite vic) {aVictime = vic;}
    
    
    public Unite getAttaquant() {return aAttaquant;}
    public Unite getVictime() {return aVictime;}
    public int getPVatt() {return attaquantPVfin;}
    public int getPVvic() {return victimePVfin;}
}
