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
    private Entite aCible;

    /**
     * Constructor for objects of class Objectif
     */
    public Objectif(final String pMotPrincipal,final String pMotSecondaire,final Point pCoordonnee,final Entite pExecutant,final Entite pCible)
    {
        aMotPrincipal = pMotPrincipal;
        aMotSecondaire = pMotSecondaire;
        aExecutant = pExecutant;
        aCible = pCible;
        aCoordonnee = pCoordonnee;
    }

    public String getMotPrincipal(){return aMotPrincipal;}
    public String getMotSecondaire(){return aMotSecondaire;}
    public Point getCoordonnee(){return aCoordonnee;}
    public Entite getCible(){return aCible;}
    public Entite getExecutant(){return aExecutant;}
}
