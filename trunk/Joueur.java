import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
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
    private boolean aVivant;
    private List<Terrain> aListeBatiment;
    private List<Terrain> aListeUsine;
    private int aArgentTotal; // Sert aux statistiques de fin de partie, represente l'argent total que le joueur a gagne
    private int aArgentDepense;
    private int aNbrUniteMort;
    private int aNbrUniteTue;
    private int aNbrUniteCree;
    private double aDegatTotal;
    private double aDegatSubit;
    private int aExpTotal;
    private int aCaptureTotal;
    private int aDeplacementTotal;
    private int aSoinTotal;
    
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
        aVivant=true;
        aArgent = 0;
        aArgentTotal = 0;
        aArgentDepense = 0;
        aNbrUniteCree = 0;
        aNbrUniteMort = 0;
        aNbrUniteTue = 0;
        aDegatTotal=0;
        aDegatSubit=0;
        aExpTotal=0;
        aCaptureTotal=0;
        aDeplacementTotal=0;
        aSoinTotal=0;
        aNbreBatiment = pNbreBatiment;
        aListeUnite = new ArrayList<Unite>();
        aListeUsine = new ArrayList<Terrain>();
        aListeBatiment = new ArrayList<Terrain>();
    }

    /**
     * ACCESSEUR & MODIFICATEUR
     */
    public int getNumJoueur()          {return aNumJoueur;}
    public Faction getFaction()        {return aFaction;}   
    public int getNbreBatiment()        {return aNbreBatiment;}    
    public int getArgent()             {return aArgent;}
    public int getArgentTotal() {return aArgentTotal;}
    public int getArgentDepense() {return aArgentDepense;}
    public int getNbrUniteMort() {return aNbrUniteMort;}
    public int getNbrUniteCree() {return aNbrUniteCree;}
    public int getDegatTotal() {return (int)aDegatTotal;}
    public int getDegatSubit() {return (int)aDegatSubit;}
    public int getNbrUniteTue() {return aNbrUniteTue;}
    public int getExpTotal() {return aExpTotal;}
    public int getSoinTotal() {return aSoinTotal;}
    public int getDeplacementTotal() {return aDeplacementTotal;}
    public int getCaptureTotal() {return aCaptureTotal;}
    public List<Unite> getListeUnite()   {return aListeUnite;}
    public List<Terrain> getListeUsine()   {return aListeUsine;}
    public List<Terrain> getListeBatiment()   {return aListeBatiment;}
    public boolean estUneIA()                  {return IA;}
    public boolean isAlive()                  {return aVivant;}
    
    public void setNumJoueur(final int pNum) {aNumJoueur = pNum;}
    public void setFaction(final Faction pFaction) {aFaction = pFaction;}
    public void addNbreBatiment(final int pNbre) {aNbreBatiment += pNbre;}
    public void addDegatSubit(final double pDegat) {aDegatSubit += pDegat;}
    public void addDegatTotal(final double pDegat) {aDegatTotal += pDegat;}
    public void addExpTotal(final double pExp) {aExpTotal += (int)pExp;}
    public void addSoinTotal(final int pSoinTotal){ aSoinTotal+=pSoinTotal;}
    public void addDeplacementTotal(final int pDeplacement) {aDeplacementTotal+=pDeplacement;}
    public void addCaptureTotal() {aCaptureTotal ++;}
    public void addNbrUniteCree() {aNbrUniteCree++;}
    public void addNbrUniteTue() {aNbrUniteTue++;}
    public void setArgent(final int pArgent){
        aArgent = pArgent;
        aArgentTotal = pArgent;
    }
    
    public void setArgentDepense(final int pArgentDepense){
        aArgentDepense+=pArgentDepense;
    }
    
    public void addArgent(final int pArgent){
        aArgent += pArgent;
        if(pArgent > 0){
            aArgentTotal += pArgent;
        }
    }
    
    public void addNbrUniteMort(){
        aNbrUniteMort++;
    }
    
    public void setIA(final boolean X)      {IA=X;}
    
    public void benefTour(final int pRevenuBatiment)
    {
        int gain = aNbreBatiment*pRevenuBatiment;
        aArgent+=gain;
        aArgentTotal += gain;
    }
    
    public void mourrir() {
        aVivant=false;
        
        Iterator<Terrain> i = aListeBatiment.iterator();
        while(i.hasNext())
        {
            Terrain vTerrain = i.next();
            i.remove();
        }
        
        Iterator<Terrain> l = aListeUsine.iterator();
        while(i.hasNext())
        {
            Terrain vUsine = l.next();
            l.remove();
        }
        
        Iterator<Unite> m = aListeUnite.iterator();
        while(m.hasNext())
        {
            Unite vUnite = m.next();
            Slatch.partie.getTerrain()[vUnite.getCoordonneeX()][vUnite.getCoordonneeY()].setUnite(null);
            m.remove(); 
        }
    }
    

    
    
}
