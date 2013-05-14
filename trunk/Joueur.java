/**
 * Un joueur possede un numero de joueur (aNumJoueur), une faction (aFaction)
 * 
 * @author Mathieu Scala 
 * @version 14/05/13
 */
public class Joueur
{
    private int aNumJoueur;
    private Faction aFaction;
    private int aNbreBatiment;
    private int aArgent;

    /**
     * Créer un joueur
     * Initialise le numero du joueur, la faction du joueur et son nombre de batiment avec les valeurs mise en parametre et l'argent du joueur a 0.
     * @Param : le numero du joueur (entier)
     *          la faction (Faction)
     *          le nombre de batiment au depart (entier)
     */ 
    public Joueur(int pNumJoueur, Faction pFaction, pNbreBatiment)
    {
        aNumJoueur = pNumJoueur;
        aFaction = pFaction;
        aArgent = 0;
        aNbreBatiment = pNbreBatiment;
    }

    /**
     * ACCESSEUR
     */
    int getNumJoueur()      {return aNumJoueur;}
    Faction getFaction()    {return aFaction;}   
    int getNbreBatiment()   {return aNbreBatiment;}    
    int getArgent()         {return aArgent;}
    
    
    /**
     * MODIFICATEUR
     */
    void setNumJoueur(int pNum) {aNumJoueur = pNum;}
    void setFaction(Faction pFaction) {aFaction = pFaction;}
    void setNbreBatiment(int pNbre) {aNbreBatiment = pNbre;}
    void setArgent(int pArgent) {aArgent = pArgent;}
}
