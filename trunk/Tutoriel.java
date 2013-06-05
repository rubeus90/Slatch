
/**
 * Write a description of class Tutoriel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tutoriel extends Partie
{
    /* ATTRIBUT VENANT DE PARTIE
    private int aNbrJoueur;
    private List<Joueur> ListeJoueur;
    private int aRevenuBatiment;
    private int aLargeur;
    private int aHauteur;
    private int aTourMax;
    private Scanner aMap;
    private Terrain[][] aTerrain;
    private int aJoueurActuel;
    private int aTour;
    */
    
    private int aEtape;
    
    
    /**
     * Constructor for objects of class Tutoriel
     */
    public Tutoriel(final String pMap)
    {
       super(pMap,true);
       aEtape = 1;
    }

    public void deroulement()
    {
        switch(aEtape)
        {
            case 1 :
        }
        
    }
}
