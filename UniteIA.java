import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    static private void seDirigerVers(final Entite executant,final Point point)
    {
        Unite u = (Unite)executant;
        Slatch.moteur.remplitPorteeDep(u, false);        
        Slatch.moteur.deplacement(u, (int)point.getX(),(int)point.getY());
    }
    
    static private void attaquerUnite(final Unite pUnite,final Point point,final Unite pCible){
        if(!Slatch.moteur.estAPortee(pUnite, pCible)){seDirigerVers(pUnite, point);}
        if(Slatch.moteur.estAPortee(pUnite, pCible) && ((pUnite.getAttaque().aTypePortee.getPorteeMin()==1 && Slatch.moteur.distance(pUnite, pCible)==1) || !pUnite.dejaDeplacee())){
            Slatch.moteur.setuniteA(pUnite);
            Slatch.moteur.attaque(pCible);
        }
    }
    
    static private void capture(final Entite executant,final Point point){
        Unite u= (Unite) executant;
        if(!u.seSitue(point))
        {seDirigerVers(u, point);}
        if(u.seSitue(point)){
            Slatch.moteur.capture((int)point.getX(),(int)point.getY());
        }
        u.attaque(true);
        u.deplacee(true);
    }
    
    static private void achat(final Point point,String pType){
        for(TypeUnite type : TypeUnite.values()){
            if(type.getNom().equals(pType)){
                Slatch.moteur.creationUnite((int)point.getX(),(int)point.getY(),type);
                break;
            }
        }
    }
    
    static private void soigner(Unite executant, Unite cible, Point point)
    {
        if(!executant.seSitue(point))
        {seDirigerVers(executant, point);}
        if(executant.seSitue(point))
        {
            Slatch.moteur.setuniteA(executant);
            Slatch.moteur.soin(cible);
        }
        executant.attaque(true);
        executant.deplacee(true);
    }
    
    static private void evoluer(Unite executant, Unite cible, Point point)
    {
        if(!executant.seSitue(point))
        {seDirigerVers(executant, point);}
        
        if(executant.seSitue(point))
        {
            Slatch.moteur.setuniteA(executant);
            Slatch.moteur.evoluer(cible);
        }
        
        executant.attaque(true);
        executant.deplacee(true);
    }
    
    static void decrypterObjectif(final Objectif objectif)
    {
        switch(objectif.getMotPrincipal()){
        case "aller" :
             seDirigerVers(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        case "attaquer" :
             attaquerUnite((Unite)objectif.getExecutant(),objectif.getCoordonnee(),(Unite)objectif.getCible());
            break;
        case "capture" :
           
             capture(objectif.getExecutant(),objectif.getCoordonnee());
            break;
        case "acheter" :
             achat(objectif.getCoordonnee(),objectif.getMotSecondaire());
            break;
        case "soigner" :
         soigner((Unite)objectif.getExecutant(),(Unite)objectif.getCible(), objectif.getCoordonnee()); break;
         
        case "evoluer" :
         evoluer((Unite)objectif.getExecutant(),(Unite)objectif.getCible(), objectif.getCoordonnee());
        }
    }
}