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
        Iterator<Unite> i = l.iterator();
        while(i.hasNext())
        {
            Unite u = i.next();
            GrandeIA.test2(u);
            if(u.getPV()<=0){i.remove();}
        }    
        Slatch.moteur.passeTour();
    }
}
