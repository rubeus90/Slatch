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
   
    static Point[] signes = {new Point(1,1), new Point(1,-1),new Point(-1,-1),new Point(-1,1), new Point(0,1), new Point(0,-1), new Point(-1,0), new Point(1,0)};
    
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
            int x= (int)pwin.getX();
            int y=(int)pwin.getY();
            
            int xobj=x;
            int yobj=y;
            
            for(Point p: Moteur.voisins)
            {
                xobj = x+(int)p.getX();
                yobj = y+(int)p.getY();
                if(Slatch.partie.getTerrain()[xobj][yobj].getUnite()==null && Slatch.partie.getTerrain()[xobj][yobj].getCout(unite)<unite.getDeplacement())
                {
                    uia.decrypterObjectif(new Objectif("attaquer", null, new Point(x,y), unite, null));
                    break;
                }
            }
        }
        Slatch.moteur.passeTour();
    }
    
    static Point test2uniteProcheAdverse(Unite unite)
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null)  // quand on a déjà une unité sur la case, on ne peut pas y accéder
                {
                    if(Slatch.partie.getTerrain()[i][j].getUnite().getJoueur() != Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].getUnite().getJoueur())
                    {
                        Slatch.moteur.tabDist[i][j] = -1;
                    }
                    else
                    {
                        Slatch.moteur.tabDist[i][j] = -2;
                    }
                }
                else{Slatch.moteur.tabDist[i][j] = -1;} // au début, on suppose qu'on a une distance infinie représentée par -1 sur chacune des cases restantes
            }
        }
        Slatch.moteur.tabDist[unite.getCoordonneeX()][unite.getCoordonneeY()]=-2;
        Slatch.moteur.pred = new Point[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        
        Slatch.moteur.algoDeplacement(unite, false);

        int v=10;
        
        String test;
        Triplet t = new Triplet(-1, -1, -1);
        /*
        for (int i=0;i<Slatch.partie.getLargeur();i++) {
            test="";
            
            for (int j=0;j<Slatch.partie.getHauteur();j++) {
                test+=" "+tabDist[i][j];
            }
            
            System.out.println(test);
        }
        */
        
        /*while(v<=200) {
            for (int i=0;i<Slatch.partie.getLargeur();i++) {
                for (int j=0;j<Slatch.partie.getHauteur();j++) {
                    if(Slatch.partie.getTerrain()[i][j].getUnite()!=null){
                        if(Slatch.moteur.tabDist[i][j]<=v && Slatch.partie.getTerrain()[i][j].getUnite().getJoueur()!=unite.getJoueur()) {
                            System.out.println("1: X"+Slatch.partie.getTerrain()[i][j].getUnite().getCoordonneeX()+" Y"+Slatch.partie.getTerrain()[i][j].getUnite().getCoordonneeY());
                            if(pcoo==1) 
                                return Slatch.partie.getTerrain()[i][j].getUnite().getCoordonneeX();
                            else
                                return Slatch.partie.getTerrain()[i][j].getUnite().getCoordonneeY();
                        }
                    }
                }
            }
            v+=10;
        }*/
        
        for(int i=0;i<Slatch.partie.getLargeur();i++) {
                for(int j=0;j<Slatch.partie.getHauteur();j++) {
                    if(Slatch.partie.getTerrain()[i][j].getUnite()!=null){
                        if(((Slatch.moteur.tabDist[i][j]<t.d && Slatch.moteur.tabDist[i][j]>0) || Slatch.moteur.tabDist[i][j]==-1) && Slatch.partie.getTerrain()[i][j].getUnite().getJoueur()!=unite.getJoueur()) {
                              t.d=Slatch.moteur.tabDist[i][j];
                              t.x = i;
                              t.y =j;
                        }
                    }
                }
        }
         
        if(t.d!=-1)
        {
            return new Point(t.x,t.y);
        }
        return null;
    }
}
