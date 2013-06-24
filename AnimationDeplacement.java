import java.util.Stack;
import java.awt.Point;
/**
 * Write a description of class Animation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationDeplacement extends Animation
{
    private Stack<Point> aChemin; // CHEMIN DE ANIMATION 
    private Point aDepart; // DEPART DE ANIMATION
    private Unite aUnite;
    private double aVitesse;
    
    /**
     * Constructor for objects of class Animation
     */
    public AnimationDeplacement(final Stack<Point> chemin, final Point depart, final Unite unite)
    {
        aChemin = chemin;
        aType = "deplacement";
        aDepart = depart;
        aUnite = unite;
        aVitesse = 1/5.0;
    }

    public void setChemin(final Stack<Point> chemin){aChemin = chemin;}
    public void setDepart(final Point depart){aDepart = depart;}
    public void setUnite(final Unite unite){aUnite = unite;}
    public void setVitesse(final double vitesse){aVitesse = vitesse;}
    
    public Stack<Point> getChemin(){ return aChemin;}
    public Point getDepart() {return aDepart;}
    public Unite getUnite() {return aUnite;}
    public double getVitesse() {return aVitesse;}
    
    
}
