import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    private void seDirigerVers(final Unite unite,final int pX,final int pY)
    {
        Slatch.moteur.remplitPorteeDep(unite, false);
        Slatch.moteur.deplacement(unite, pX, pY);
    }
    
    private void attaquerUnite(final Unite pUnite,final int pX,final int pY){
        seDirigerVers(pUnite, pX, pY);
        if(pUnite.getCoordonneeX()==pX && pUnite.getCoordonneeY()==pY){
            Unite pVictime = Slatch.partie.getTerrain()[0][1].getUnite();
            Slatch.moteur.setuniteA(pUnite);
            Slatch.moteur.attaque(pVictime);
        }
    }
    
    public void decrypterObjectif(final Unite unite,final String bateau)
    {
        String[] s = bateau.split(",");
        
        switch(s[0]){
        case "aller" :
            seDirigerVers(unite,Integer.parseInt(s[1]),Integer.parseInt(s[2]));
            break;
        case "attaquer" :
            attaquerUnite(unite,Integer.parseInt(s[1]),Integer.parseInt(s[2]));
            break;
        }
    }
}