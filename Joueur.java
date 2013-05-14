/**
 * Un joueur possede un numero de joueur (aNumJoueur), une faction (aFaction)
 * 
 * @author Mathieu Scala 
 * @version 14/05/13
 */
public class Joueur
{
    private int aNumJoueur; //Correspond au numéro du joueur lors d'une partie.
    private Faction aFaction; //Correspond a la faction du joueur lors d'une partie.
    private int aNbreBatiment; //Correspond aux nombres de batiment que possede un joueur. 
    private int aArgent; //Correspond a l'argent que possede le joueur.

    /**
     * Créer un joueur
     * Initialise le numero du joueur, la faction du joueur et son nombre de batiment avec les valeurs mise en parametre et l'argent du joueur a 0.
     * @Param : le numero du joueur (entier)
     *          la faction (Faction)
     *          le nombre de batiment au depart (entier)
     */ 
    public Joueur(final int pNumJoueur,final Faction pFaction,final int pNbreBatiment)
    {
        aNumJoueur = pNumJoueur;
        aFaction = pFaction;
        aArgent = 0;
        aNbreBatiment = pNbreBatiment;
    }

    /**
     * ACCESSEUR & MODIFICATEUR
     */
     
    int getNumJoueur()      {return aNumJoueur;}
    Faction getFaction()    {return aFaction;}   
    int getNbreBatiment()   {return aNbreBatiment;}    
    int getArgent()         {return aArgent;}
    
    
    void setNumJoueur(final int pNum) {aNumJoueur = pNum;}
    void setFaction(final Faction pFaction) {aFaction = pFaction;}
    void setNbreBatiment(final int pNbre) {aNbreBatiment = pNbre;}
    void setArgent(final int pArgent) {aArgent = pArgent;}
}
