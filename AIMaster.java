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
    static int[][]iMap;
    static void joueTour(int joueur)
    {
        List<Unite> l = Slatch.partie.getJoueur(joueur).getListeUnite();
        GrandeIA.acheterUnite();
        
        iMap = new int[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        //remplirMap();
        Iterator<Unite> i = l.iterator();
        while(i.hasNext())
        {
            Unite u = i.next();
            if(u!=null){
                if(!u.dejaAttaque()||!u.dejaDeplacee()){GrandeIA.elaborationObjectif(u);}
            }
            if(u.getPV()<=0){i.remove();}
            if(Slatch.partie.partieFinie){break;}
        }    
        //System.out.println(joueur+" Je passe mon tour");
        Slatch.moteur.passeTour();
    }
    
    static void remplirMap()
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                switch(Slatch.partie.getTerrain()[i][j].getType().getNom())
                {
                    case "usine": if(Slatch.partie.getTerrain()[i][j].getJoueur()==Slatch.partie.getJoueurActuel()){iMap[i][j]+=30;}
                    else{iMap[i][j]+=10;} break;
                    case "batiment": iMap[i][j]+=40; break;
                    case "foret": iMap[i][j]+=20; break;
                    case "montagne": iMap[i][j]+=30; break;
                    case "qg": if(Slatch.partie.getTerrain()[i][j].getJoueur()==Slatch.partie.getJoueurActuel()){iMap[i][j]+=100;} else{iMap[i][j]+=40;}break;
                    default:
                }
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null)
                {
                    spreadInfluence(Slatch.partie.getTerrain()[i][j].getUnite());
                }
            }
        }
    }
    
    static void spreadInfluence(Unite unite)
    {
        for(int i=1; i<unite.getAttaque().aTypePortee.getPorteeMax(); i++)
        {
            
        }
    }
}
