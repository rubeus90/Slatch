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
        int x =1, y =1;
        if(Slatch.partie.getTerrain()[x][y].getUnite()==null)
        {
            uia.decrypterObjectif(new Objectif("aller",null,new Point(x,y),unite,null));
        }
        Slatch.moteur.passeTour();
    }
    
    static void test2()
    {
        
    }
}
