import java.util.PriorityQueue;
import java.awt.Point;

/**
 * Write a description of class GrandeIA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrandeIA
{
    static UniteIA uia = new UniteIA();
   
    
    static void test(Unite unite)
    {
        int x =15, y =4;
        //if(Slatch.partie.getTerrain()[x][y].getUnite()==null)
        //{
            uia.decrypterObjectif(new Objectif("acheter","Commando",new Point(x,y),null));
        //}
        Slatch.moteur.passeTour();
    }
    
    static void test2(Unite unite)
    {
        Point pwin = test2uniteProcheAdverse(unite);
        if(pwin!=null)
        {
            uia.decrypterObjectif(new Objectif("aller", null, pwin, unite));
        }
        //Slatch.moteur.passeTour();
    }
    
    static Point test2uniteProcheAdverse(Unite unite)
    {        
        Slatch.moteur.remplitPorteeDep(unite, false);
        //Slatch.moteur.tabDist[unite.getCoordonneeX()][unite.getCoordonneeY()] = -1;
        Triplet t = new Triplet(-1, -1, -1);
        
        for(int i=0; i<Slatch.partie.getNbrJoueur(); i++)
        {
            if(i!=unite.getJoueur())
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    for(Point p: Moteur.voisins)
                    {
                        int x= (int)p.getX()+u.getCoordonneeX();
                        int y= (int)p.getY()+u.getCoordonneeY();
                        if(Moteur.dansLesBords(x,y))
                        {
                            if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                            }
                        }
                    }
                }
            }
        }
        
         
        if(t.d!=-1)
        {
            //System.out.println(t.x+" "+t.y+" "+t.d);
            return new Point(t.x,t.y);
        }
        return null;
    }
}
