import java.awt.Point;
import java.util.Stack;
public class UniteIA
{
    static private void seDirigerVers(final Entite executant,final Entite cible)
    {
        Unite u = (Unite)executant;
        //Slatch.moteur.remplitPorteeDep(u, false);
        Terrain[][] tab = Slatch.partie.getTerrain();
        int[][] tabDist = Slatch.moteur.tabDist;
        Triplet t = new Triplet(-1,cible.getX(),cible.getY());
        
        /*if(tab[cible.getX()][cible.getY()].getUnite()!=null)
        {
            for(Point p: Moteur.voisins)
            {
                int x = (int)(p.getX())+cible.getX();
                int y = (int)(p.getY())+cible.getY();
                if(Slatch.moteur.dansLesBords(x,y))
                {
                    if(t.d==-1 || tabDist[x][y]<t.d)
                    {
                        t.d=tabDist[x][y];
                        t.x=x;
                        t.y=y;
                    }
                }
            }
            if(t.d<0){return;}
        }*/
        
        /*Quad[] signes = Slatch.moteur.signes;
        
        for(int i=1; 1>0; i++)
        {
           for(int j=0; j<=i; j++)
           {
               for(Quad q: signes)
               {
                   int x = q.a*i+q.b*j+cible.getX();
                   int y = q.a*i+q.b*j+cible.getY();
                   if(Slatch.moteur.dansLesBords(x,y))
                   {
                        if(t.d<0 || tabDist[x][y]<t.d)
                        {
                            t.d=tabDist[x][y];
                            t.x=x;
                            t.y=y;
                        }
                   }   
               }
           }
           if(t.d>0){break;}
        }*/
        
        Slatch.moteur.deplacement(u, t.x,t.y);
        u.deplacee(true);
    }
    
    static private void attaquerUnite(final Entite pUnite,final Entite pCible){
        Unite u = (Unite)pUnite;
        Unite cible = (Unite)pCible;
        if(!Slatch.moteur.estAPortee(u, cible)){seDirigerVers(u, cible); /*System.out.println("Approche tactique");*/}
        if(Slatch.moteur.estAPortee(u, cible) && ((u.getAttaque().aTypePortee.getPorteeMin()==1 && Slatch.moteur.distance(u, cible)==1) || !u.dejaDeplacee())){
            Slatch.moteur.setuniteA(u);
            Slatch.moteur.attaque(cible);
            //System.out.println("C'est bon on attaque, on est des fous, on en peut plus!");
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
        if(!Slatch.moteur.estAuCac(e,c))
        {seDirigerVers(e, c);}
        if(Slatch.moteur.estAuCac(e,c))
        {
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