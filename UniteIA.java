import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    private void seDirigerVers(final Entite unite,final Point point)
    {
        Slatch.moteur.remplitPorteeDep((Unite)unite, false);
        Slatch.moteur.deplacement((Unite)unite, (int)point.getX(),(int)point.getY());
    }
    
    private void attaquerUnite(final Entite pUnite,final Point point,final Entite pCible){
        seDirigerVers((Unite)pUnite, point);
        if(pUnite.getCoordonneeX()==(int)point.getX() && pUnite.getCoordonneeY()==(int)point.getY()){
            Slatch.moteur.setuniteA((Unite)pUnite);
            Slatch.moteur.attaque((Unite)pCible);
        }
    }
    
    private void capture(final Entite pUnite,final Point point){
        if(!((Unite)pUnite).seSitue(point)){seDirigerVers((Unite)pUnite, point);}
        if(pUnite.getCoordonneeX()==(int)point.getX() && pUnite.getCoordonneeY()==(int)point.getY()){
            Slatch.moteur.capture((int)point.getX(),(int)point.getY());
        }
    }
    
    private void achat(final Point point,String pType){
        
        for(TypeUnite type : TypeUnite.values()){
            if(type.getNom().equals(pType)){
                Slatch.moteur.creationUnite((int)point.getX(),(int)point.getY(),type);
                break;
            }
        }
    }
    
    public void decrypterObjectif(final Objectif objectif)
    {
        switch(objectif.getMotPrincipal()){
        case "aller" :
             seDirigerVers(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        case "attaquer" :
             attaquerUnite(objectif.getExecutant(),objectif.getCoordonnee(),objectif.getCible());
            break;
        case "capture" :
             capture(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        case "acheter" :
             achat(objectif.getCoordonnee(),objectif.getMotSecondaire());
            break;
        }
    }
}