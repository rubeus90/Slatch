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
        int x =20, y =16;
        if(Slatch.partie.getTerrain()[x][y].getUnite()==null)
        {
            uia.seDirigerVers(unite, x, y);
        }
        Slatch.moteur.passeTour();
    }
}
