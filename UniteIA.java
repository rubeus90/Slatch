import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    private void seDirigerVers(final Entite executant,final Point point)
    {
        Unite u = (Unite)executant;
        //System.out.println(u.getJoueur()+" Je me deplace vers "+point.getX()+","+point.getY());
        Slatch.moteur.remplitPorteeDep(u, false);        
        Slatch.moteur.deplacement(u, (int)point.getX(),(int)point.getY());
    }
    
    private void attaquerUnite(final Unite pUnite,final Point point,final Unite pCible){
        //System.out.println("Case objectif: ("+(int)(point.getX())+","+(int)(point.getY())+") et unité cible en ("+pCible.getCoordonneeX()+","+pCible.getCoordonneeY()+")");
        //System.out.println(pUnite+" est à "+Slatch.moteur.distance(pUnite, pCible)+" cases de "+pCible);
        if(!Slatch.moteur.estAPortee(pUnite, pCible)){seDirigerVers(pUnite, point); System.out.println(pUnite+" se dirige vers "+pCible);}
        //System.out.println(pUnite+" est à "+Slatch.moteur.distance(pUnite, pCible)+" cases de "+pCible);
        if(Slatch.moteur.estAPortee(pUnite, pCible) && (pUnite.getAttaque().aTypePortee.getPorteeMin()==1 || !pUnite.dejaDeplacee())){
            //System.out.println(pUnite+" attaque "+pCible);
            Slatch.moteur.setuniteA(pUnite);
            Slatch.moteur.attaque(pCible);
        }
    }
    
    private void capture(final Entite executant,final Point point){
        Unite u= (Unite) executant;
        if(!u.seSitue(point))
        {seDirigerVers(u, point);}
        if(u.getCoordonneeX()==(int)point.getX() && u.getCoordonneeY()==(int)point.getY()){
            Slatch.moteur.capture((int)point.getX(),(int)point.getY());
        }
        u.attaque(true);
        u.deplacee(true);
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
             attaquerUnite((Unite)objectif.getExecutant(),objectif.getCoordonnee(),(Unite)objectif.getCible());
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