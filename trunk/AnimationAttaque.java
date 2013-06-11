
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.awt.Point;
/**
 * Write a description of class Animation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationAttaque extends Animation
{
    private int attaquantPVdepart;
    private int attaquantPVfin;
    private int victimePVdepart;
    private int victimePVfin;
    private Unite aAttaquant;
    private Unite aVictime;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationAttaque(final Unite attaquant, final Unite victime)
    {
        aAttaquant = attaquant;
        aVictime = victime;
        aType="attaque";
    }

    
    public void setAttaquant (final Unite att) {aAttaquant = att;}
    public void setVictime (final Unite vic) {aVictime = vic;}
    
    
    public Unite getAttaquant() {return aAttaquant;}
    public Unite getVictime() {return aVictime;}
}
