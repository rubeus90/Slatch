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
            uia.decrypterObjectif(new Objectif("acheter","Commando",new Point(x,y),null,null));
        //}
        Slatch.moteur.passeTour();
    }
    
    static void test2()
    {
        
    }
}
