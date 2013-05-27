import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    private void seDirigerVers(final Entite unite,final Point point)
    {
        Slatch.moteur.remplitPorteeDep((Unite)unite, false);
        Slatch.moteur.deplacement((Unite)unite, (int)point.getX(),(int)point.getY());
    }
    
    private void attaquerUnite(final Entite pUnite,final Point point){
        seDirigerVers((Unite)pUnite, point);
        if(pUnite.getCoordonneeX()==(int)point.getX() && pUnite.getCoordonneeY()==(int)point.getY()){
            Unite pVictime = Slatch.partie.getTerrain()[0][1].getUnite();
            Slatch.moteur.setuniteA((Unite)pUnite);
            Slatch.moteur.attaque((Unite)pVictime);
        }
    }
    
    public void decrypterObjectif(final Objectif objectif)
    {
        switch(objectif.getMotPrincipal()){
        case "aller" :
            seDirigerVers(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        case "attaquer" :
            attaquerUnite(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        }
    }
}