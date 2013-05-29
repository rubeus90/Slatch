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
        GrandeIA.acheterUnite();
        Iterator<Unite> i = l.iterator();
        
        while(i.hasNext())
        {
            Unite u = i.next();
            if(u!=null){
                if(!u.dejaAttaque()||!u.dejaDeplacee()){GrandeIA.elaborationObjectif(u);}
            }
            if(u.getPV()<=0){i.remove();}
        }    
        //System.out.println(joueur+" Je passe mon tour");
        Slatch.moteur.passeTour();
    }
    
    
}
