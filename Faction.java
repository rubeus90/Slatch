import java.util.List;

/**
 * Faction désigne un ensemble d'unités qui sont dans le même camp. Elle permet de les retrouver facilement lorsqu'on recrute.
 */
public enum Faction
{
    HUMAINS, ROBOTS, ALIENS;
    List<Unite> listeUnites;
    
    Faction(List<Unite> pUnites)
    {
        listeUnites=pUnites;
    }
}
