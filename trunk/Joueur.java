import java.util.List;
import java.util.ArrayList;
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
    private List<Unite> aListeUnite;
    private boolean IA;
    private List<Terrain> aListeUsine;
    private double aArgentTotal; // Sert aux statistiques de fin de partie, represente l'argent total que le joueur a gagne
    private int aNbrUniteMort;
    private int aNbrUniteCree;
    
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
        IA=false;
        aArgent = 0;
        aArgentTotal = 0;
        aNbrUniteMort = 0;
        aNbreBatiment = pNbreBatiment;
        aListeUnite = new ArrayList<Unite>();
        aListeUsine = new ArrayList<Terrain>();
    }

    /**
     * ACCESSEUR & MODIFICATEUR
     */
    public int getNumJoueur()          {return aNumJoueur;}
    public Faction getFaction()        {return aFaction;}   
    public int getNbreBatiment()        {return aNbreBatiment;}    
    public int getArgent()             {return aArgent;}
    public double getArgentTotal() {return aArgentTotal;}
    public int getNbrUniteMort() {return aNbrUniteMort;}
    public int getNbrUniteCree() {return aNbrUniteCree;}
    public List<Unite> getListeUnite()   {return aListeUnite;}
    public List<Terrain> getListeUsine()   {return aListeUsine;}
    public boolean estUneIA()                  {return IA;}
    
    public void setNumJoueur(final int pNum) {aNumJoueur = pNum;}
    public void setFaction(final Faction pFaction) {aFaction = pFaction;}
    public void addNbreBatiment(final int pNbre) {aNbreBatiment += pNbre;}
    public void addNbrUniteCree() {aNbrUniteCree++;}
    public void setArgent(final int pArgent){
        aArgent = pArgent;
        aArgentTotal = pArgent;
    }
    public void addArgent(final int pArgent){
        aArgent += pArgent;
        if(pArgent > 0){
            aArgentTotal += pArgent;
        }
    }
    
    public void addNbrUniteMort(){
        aNbrUniteMort++;
        //System.out.println("Le joueur " + aNumJoueur + " a perdu une Unite");
    }
    
    public void setIA(final boolean X)      {IA=X;}
    
    public void benefTour(final int pRevenuBatiment)
    {
        int gain = aNbreBatiment*pRevenuBatiment;
        aArgent+=gain;
        aArgentTotal += gain;
    }
    

    
    
}
