import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    public void seDirigerVers(Unite unite, int pX, int pY)
    {
        Slatch.moteur.remplitPorteeDep(unite, false);
        Slatch.moteur.deplacement(unite, pX, pY);
    }
    
    public void decrypterObjectif(Unite unite,String bateau)
    {
        String[] s = bateau.split(",");
        
        if(s[0].equals("aller"))
        {
            seDirigerVers(unite,Integer.parseInt(s[1]),Integer.parseInt(s[2]));
        }
    }
}