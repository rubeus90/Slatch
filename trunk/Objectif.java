import java.awt.Point;

/**
 * Write a description of class Objectif here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Objectif
{
    private String aMotPrincipal;
    private String aMotSecondaire;
    private Point aCoordonnee;
    private Entite aExecutant;

    /**
     * Constructor for objects of class Objectif
     */
    public Objectif(final String pMotPrincipal,final String pMotSecondaire,final Point pCoordonnee,final Entite pExecutant)
    {
        aMotPrincipal = pMotPrincipal;
        aMotSecondaire = pMotSecondaire;
        aExecutant = pExecutant;
        aCoordonnee = pCoordonnee;
    }

    public String getMotPrincipal(){return aMotPrincipal;}
    public String getMotSecondaire(){return aMotSecondaire;}
    public Point getCoordonnee(){return aCoordonnee;}
    public Entite getExecutant(){return aExecutant;}
}
