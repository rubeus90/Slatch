
import java.util.PriorityQueue;
import java.awt.Point;
import java.util.List;

import java.util.ArrayList;

/**
 * Write a description of class GrandeIA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrandeIA
{
    static UniteIA uia = new UniteIA();
   
    
    static void elaborationObjectif(Unite unite)
    {
        Triplet batimentProche=null;
        Cible ennemiProche;
        switch(unite.getType().getNom())
        {
           case "Commando" :
           case "Demolisseur" :
           case "Ingenieur" :
           
                    int X = unite.getCoordonneeX();
                    int Y = unite.getCoordonneeY();
                    if( (Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.BATIMENT || Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.USINE) && Slatch.partie.getTerrain()[X][Y].getJoueur()!=unite.getJoueur() )
                    {
                        uia.decrypterObjectif(new Objectif("capture",null,new Point(X,Y),unite,null));
                        return;
                    }
                    Slatch.moteur.remplitPorteeDep(unite, false);
                    batimentProche = determineBatimentProche(unite);
                    
                    
           default:
           
                    ennemiProche = determineEnnemiProche(unite);
                    
                    if(batimentProche==null) batimentProche = new Triplet(900,-1,-1);
                    else if(batimentProche.d==-1) batimentProche.d = 900;
                    
                    
                    if( (batimentProche.d >= ennemiProche.t.d) && ennemiProche.t.d!=-1)
                    {
                        uia.decrypterObjectif(new Objectif("attaquer", null, new Point(ennemiProche.t.x,ennemiProche.t.y),unite,ennemiProche.u));
                    }
                    else if(batimentProche.d != 900)
                    {
                        uia.decrypterObjectif(new Objectif("capture", null, new Point(batimentProche.x,batimentProche.y),unite,null));
                    }
        
        }
    }
    
    static void acheterUnite()
    {
            Joueur JoueurActuel = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel());
        
            for (Terrain usine : JoueurActuel.getListeUsine())
            {
                int x = usine.getCoordonneeX();
                int y = usine.getCoordonneeY();

                if(JoueurActuel.getArgent()>=700)
                 {
                    uia.decrypterObjectif(new Objectif("acheter","While",new Point(x,y),null,null));                    
                                        
                 }
                else if(JoueurActuel.getArgent()>=450)         
                {
                    uia.decrypterObjectif(new Objectif("acheter","Uml",new Point(x,y),null,null));
                                
                }
                else if(JoueurActuel.getArgent()>=350)         
                {
                    uia.decrypterObjectif(new Objectif("acheter","Distance",new Point(x,y),null,null));
                                
                }
                else if(JoueurActuel.getArgent()>=300)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Tank",new Point(x,y),null,null));
                }
                else if(JoueurActuel.getArgent()>=200)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Demolisseur",new Point(x,y),null,null));
                }
                else if(JoueurActuel.getArgent()>=100)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Commando",new Point(x,y),null,null));   
                }
                else
                {
                  break;   
                }
            }
        
    }
    
    static Triplet determineBatimentProche(Unite unite)
    {
        Slatch.moteur.remplitPorteeDep(unite, false);
        
        Terrain batimentproche;
        List<Terrain> pasNosBatiments= new ArrayList<Terrain>(); //Liste des terrains n'appartenant pas au joueur actuel
        for(int i=0;i<Slatch.partie.getNbrJoueur()+1;i++)
        {
            if(i!=Slatch.partie.getJoueurActuel())
            {
                if(!Slatch.partie.getJoueur(i).getListeUsine().isEmpty())
                pasNosBatiments.addAll(Slatch.partie.getJoueur(i).getListeUsine());
                
                 if(!Slatch.partie.getJoueur(i).getListeBatiment().isEmpty())
                pasNosBatiments.addAll(Slatch.partie.getJoueur(i).getListeBatiment());
            }
        }
        
        Triplet t = new Triplet(-1, -1, -1);
        int x,y;
        for(Terrain batiment : pasNosBatiments)
        {
            x=batiment.getCoordonneeX();
            y=batiment.getCoordonneeY();
            if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                            }
        }
        
        return t;
    }
    
    
    static Cible determineEnnemiProche(Unite unite)
    {
        Unite cible=null;
        Triplet t=new Triplet(-1, -1, -1);
        int X=-1,Y=-1;
        label:for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            if(i!=unite.getJoueur())
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    for(Point p: Moteur.voisins)
                    {
                        int x= (int)p.getX()+u.getCoordonneeX();
                        int y= (int)p.getY()+u.getCoordonneeY();
                        if(Moteur.dansLesBords(x,y))
                        {
                            if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                cible = u;
                            }
                            if(unite.seSitue(new Point(x,y)) || Slatch.moteur.estAPortee(unite, u))
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                cible =u;
                                break label;
                            }
                        }
                    }
                }
            }
        }
         
        return new Cible(new Triplet (t.d,t.x, t.y),cible);
    }
}