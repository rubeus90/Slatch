import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    public void seDirigerVers(Unite unite, Point pP)
    {
        Slatch.moteur.remplitPorteeDep(unite);
        
        boolean fini = false;
        int x = (int)pP.getX(), y =(int)pP.getY();
        Stack<Point> chemin = new Stack<Point>();
        chemin.push(pP);
        while(!fini)
        {
            Point p = Slatch.moteur.pred[x][y];
            x=(int)p.getX();
            y=(int)p.getY();
            if(unite.getCoordonneeX()==x && unite.getCoordonneeY()==y)
            {
                fini = true;
            }
            else
            {
                chemin.push(p);
            }
        }
        int k = chemin.size();
        int l = unite.getPorteeDeplacement();
        while(!chemin.isEmpty())
        {
            Point p = chemin.pop();
            if((l-=Slatch.partie.getTerrain()[(int)p.getX()][(int)p.getY()].getCout(unite))<0)
            {
                break;
            }
            Slatch.moteur.changerCase(unite, (int)p.getX(), (int)p.getY());
            try{
                Thread.sleep(250/k+50);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        unite.deplacee(true);
    }
}