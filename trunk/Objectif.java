import java.awt.Point;

/**
 * Write a description of class Objectif here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Objectif
{
    private Entite aCible;
    private Entite aExecutant;
    private TypeObjectif aType;

    /**
     * Constructor for objects of class Objectif
     */
    public Objectif(final Entite pExecutant,final Entite pCible, TypeObjectif pType)
    {
        aCible=pCible;
        aExecutant = pExecutant;
        aType = pType;
    }
    
    public Entite getExecutant(){return aExecutant;}
    public Entite getCible(){return aCible;}
    public TypeObjectif getType(){return aType;}
}
