import java.util.List;
import java.util.Iterator;
/**
 * Write a description of class AIMaster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AIMaster
{
    static void joueTour(int joueur)
    {
        List<Unite> l = Slatch.partie.getJoueur(joueur).getListeUnite();
        for(Unite u: l)
        {
            GrandeIA.test2(u);
        }    
        Slatch.moteur.passeTour();
    }
}
