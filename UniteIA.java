import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    public void seDirigerVers(Unite unite, int pX, int pY)
    {
        Slatch.moteur.remplitPorteeDep(unite, false);
        Slatch.moteur.deplacement(unite, pX, pY);
    }
    
    public void decrypterObjectif(String objectif)
    {
        
    }
}