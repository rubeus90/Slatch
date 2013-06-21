 import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    static private void seDirigerVers(final Entite executant,final Entite cible)
    {
        Unite u = (Unite)executant;
        StrategieIA.spreadInfluence(u,StrategieIA.iMap, false);
        Slatch.moteur.deplacement(u, cible.getX(),cible.getY());
        StrategieIA.spreadInfluence(u,StrategieIA.iMap, true);
        u.deplacee(true);
    }
    
    static private void attaquerUnite(final Entite pUnite,final Entite pCible){
        Unite u = (Unite)pUnite;
        Unite cible = (Unite)pCible;
        if(!Slatch.moteur.estAPortee(u, cible)){seDirigerVers(u, cible);}
        if(Slatch.moteur.estAPortee(u, cible) && ((u.getAttaque().aTypePortee.getPorteeMin()==1 && Slatch.moteur.distance(u, cible)==1) || !u.dejaDeplacee())){
            Slatch.moteur.setuniteA(u);
            Slatch.moteur.attaque(cible);
            if(u.getPV()<=0)
            {
                StrategieIA.spreadInfluence(u, StrategieIA.iMap, false);
            }
            if(cible.getPV()<=0)
            {
                StrategieIA.spreadInfluence(cible, StrategieIA.iMap, false);
            }
        }
    }
    
    static private void capture(final Entite executant,final Entite pCible){
        Unite u= (Unite) executant;
        Point p = new Point(pCible.getX(), pCible.getY());
        if(!u.seSitue(p))
        {seDirigerVers(u, pCible);}
        if(u.seSitue(p)){
            Slatch.moteur.capture(pCible.getX(),pCible.getY());
        }
        u.attaque(true);
        u.deplacee(true);
    }
    
    static private void achat(Entite batiment,Entite pU){
        Slatch.moteur.creationUnite(batiment.getX(),batiment.getY(),((Unite)pU).getType());
    }
    
    static private void soigner(Entite executant, Entite cible)
    {
        Unite e = (Unite)executant;
        Unite c = (Unite)cible;
        //System.out.println("Je rentre dans le soin!");
        if(!Slatch.moteur.estAuCac(e,c))
        {seDirigerVers(e, c);}
        if(Slatch.moteur.estAuCac(e,c))
        {
            //System.out.println("Soin intensif!");
            Slatch.moteur.setuniteA(e);
            Slatch.moteur.soin(c);
        }
        e.attaque(true);
        e.deplacee(true);
    }
    
    static private void evoluer(Entite executant)
    {
        Slatch.moteur.evoluer(executant.getX(), executant.getY());
    }
    
    static void decrypterObjectif(final Objectif objectif)
    {
        switch(objectif.getType()){
        case ALLER :
             seDirigerVers(objectif.getExecutant(),objectif.getCible());
            break;
        case ATTAQUER :
             attaquerUnite(objectif.getExecutant(),objectif.getCible());
            break;
        case CAPTURER :
             capture(objectif.getExecutant(),objectif.getCible());
            break;
        case ACHETER :
             achat(objectif.getExecutant(),objectif.getCible());
            break;
        case SOIGNER :
         soigner(objectif.getExecutant(),objectif.getCible());
            break;
        case EVOLUER :
         evoluer(objectif.getExecutant());
        }
    }
}