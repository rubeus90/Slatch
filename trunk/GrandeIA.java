
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
        Cible ennemiProche = null;
        Cible cibleSoin = null;
        Slatch.moteur.remplitPorteeDep(unite, false);
        switch(unite.getType().getNom())
        {
           case "Ingenieur" :
                    cibleSoin = determineEnnemiProche(unite, true);
                    if(cibleSoin.u!=null)
                    {
                        break;
                    }
           
           case "Demolisseur" :
           case "Commando":
           
                    int X = unite.getCoordonneeX();
                    int Y = unite.getCoordonneeY();
                    if( (Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.BATIMENT || Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.USINE) && Slatch.partie.getTerrain()[X][Y].getJoueur()!=unite.getJoueur() )
                    {
                        uia.decrypterObjectif(new Objectif(unite, Slatch.partie.getTerrain()[X][Y], TypeObjectif.CAPTURER));
                        return;
                    }
                    Slatch.moteur.remplitPorteeDep(unite, false);
                    batimentProche = determineBatimentProche(unite);
                    
                    
           default:
           
                    ennemiProche = determineEnnemiProche(unite, false);
        }
        
        if(batimentProche==null) batimentProche = new Triplet(900,-1,-1);
                    else if(batimentProche.d==-1) batimentProche.d = 900;
                 
        if(cibleSoin!=null)
        {
            if(cibleSoin.u!=null)
            {
                uia.decrypterObjectif(new Objectif(unite, cibleSoin.u, TypeObjectif.SOIGNER));
            }
        }
        else if( (batimentProche.d >= ennemiProche.t.d) && ennemiProche.t.d!=-1)
        {
            uia.decrypterObjectif(new Objectif(unite,ennemiProche.u, TypeObjectif.ATTAQUER));
        }
        else if(batimentProche.d != 900)
        {
            uia.decrypterObjectif(new Objectif(unite, Slatch.partie.getTerrain()[batimentProche.x][batimentProche.y],TypeObjectif.CAPTURER));
        }
    }
    
    
    
    static void acheterUnite()
    {
        Joueur joueurActuel = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel());
    
        //Choix de l'unite
        int nombreCommando=0;
        int nombreDemolisseur=0;
        int nombreSpec=0;
        int nombreTank=0;
        int nombreUml=0;
        int nombreDistance=0;
        int nombreWhile=0;
        
        int eCommando=0;
        int eDemolisseur=0;
        int eSpec=0;
        int eTank=0;
        int eUml=0;
        int eDistance=0;
        int eWhile=0;
        
        for (Unite unit : Slatch.partie.getListeUnitesEnnemies())
        {
            switch(unit.getType())
            {
                case COMMANDO :
                                    eCommando=eCommando+1;
                                    break;
                case DEMOLISSEUR :
                                    eDemolisseur=eDemolisseur+1;
                                    break;
                case KAMIKAZE :                
                case INGENIEUR :
                                    eSpec=eSpec+1;
                                    break;
                case CHAR :
                                    eTank=eTank+1;
                                    break;
                case UML :
                                    eUml=eUml+1;
                                    break;
                case WHILE :
                                    eWhile=eWhile+1;
                                    break;
                case DISTANCE :
                                    eDistance=eDistance+1;
                                    break;                   
            }
        }
        for(Unite u: joueurActuel.getListeUnite())
        {
            switch(u.getType())
            {
                case COMMANDO :
                                    nombreCommando=nombreCommando+1;
                                    break;
                case DEMOLISSEUR :
                                    nombreDemolisseur=nombreDemolisseur+1;
                                    break;
                case KAMIKAZE :                
                case INGENIEUR :
                                    nombreSpec=nombreSpec+1;
                                    break;
                case CHAR :
                                    nombreTank=nombreTank+1;
                                    break;
                case UML :
                                    nombreUml=nombreUml+1;
                                    break;
                case WHILE :
                                    nombreWhile=nombreWhile+1;
                                    break;
                case DISTANCE :
                                    nombreDistance=nombreDistance+1;
                                    break;                   
            }
        }
        
        PriorityQueue<Triplet> pq = new PriorityQueue<Triplet>();
        for(Terrain usine : joueurActuel.getListeUsine())
        {
            if(usine.getUnite()==null){pq.add(new Triplet(0, usine.getX(),usine.getY()));}
        }
        
        
        if(StrategieIA.mode==ModeIA.DEPLOIEMENT)
        {
            while(!pq.isEmpty())
            {
                Triplet t = pq.poll();
                Terrain usine = Slatch.partie.getTerrain()[t.x][t.y];
                int x = usine.getCoordonneeX();
                int y = usine.getCoordonneeY();
                if(joueurActuel.getArgent()>=100 && nombreCommando<3)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.COMMANDO), TypeObjectif.ACHETER));
                    nombreCommando=nombreCommando+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreSpec <2 && joueurActuel.getFaction()==Faction.HUMAINS)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.INGENIEUR), TypeObjectif.ACHETER));
                    nombreSpec=nombreSpec+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreSpec <2  && joueurActuel.getFaction()==Faction.ROBOTS)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.KAMIKAZE), TypeObjectif.ACHETER));
                    nombreSpec=nombreSpec+1;
                }
                //break;
            }
        }
        else
        {
            while(!pq.isEmpty())
            {
                Triplet t = pq.poll();
                Terrain usine = Slatch.partie.getTerrain()[t.x][t.y];
                int x = usine.getCoordonneeX();
                int y = usine.getCoordonneeY();
    
                if(joueurActuel.getArgent()>=100 && nombreCommando <eDemolisseur/2+1)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.COMMANDO), TypeObjectif.ACHETER));
                    nombreCommando=nombreCommando+1;
                }
                else if(joueurActuel.getArgent()>=450 && nombreUml <eWhile/2+1)         
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.UML), TypeObjectif.ACHETER));
                    nombreUml=nombreUml+1;
                }
                else if(joueurActuel.getArgent()>=700 && (nombreWhile<eDistance+1 || nombreWhile<3))
                {                 
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.WHILE), TypeObjectif.ACHETER));                    
                    nombreWhile=nombreWhile+1;
                }
                else if(joueurActuel.getArgent()>=350 && (nombreDistance <eTank+1 || nombreDistance<2))         
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DISTANCE), TypeObjectif.ACHETER));
                   nombreDistance=nombreDistance+1;
                }
                else if(joueurActuel.getArgent()>=300 && (nombreTank <eCommando/2+1 || nombreTank <2))
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.CHAR), TypeObjectif.ACHETER));
                     nombreTank=nombreTank+1;
                }
                else if(joueurActuel.getArgent()>=200 && (nombreDemolisseur <eWhile+1|| nombreDemolisseur <2))
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DEMOLISSEUR), TypeObjectif.ACHETER));
                    nombreDemolisseur=nombreDemolisseur+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreSpec <3 && joueurActuel.getFaction()==Faction.HUMAINS)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.INGENIEUR), TypeObjectif.ACHETER));
                    nombreSpec=nombreSpec+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreSpec <3  && joueurActuel.getFaction()==Faction.ROBOTS)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.KAMIKAZE), TypeObjectif.ACHETER));
                    nombreSpec=nombreSpec+1;
                }
                else if(joueurActuel.getArgent()>=700)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.WHILE), TypeObjectif.ACHETER));                    
                    nombreWhile=nombreWhile+1;
                }
                //break;
            }
        }
        
    }
    
    static Triplet determineBatimentProche(Unite unite)
    {
        
        
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
    
    static Cible determineEnnemiProche(Unite unite, boolean allie)
    {
        Unite cible=null;
        Triplet t=new Triplet(-1, -1, -1);
        int X=-1,Y=-1;
        label:for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            if(i!=unite.getJoueur()^allie)
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    if(!allie^(u.aBesoinDeSoins()&&allie))
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
        }
        return new Cible(new Triplet (t.d,t.x, t.y),cible);
    }
    
    static Cible chercheUniteASoigner(Unite unite)
    {
        Unite cible = null;
        Triplet t=new Triplet(-1, -1, -1);
        int X=-1,Y=-1;
        label:for(Unite u: Slatch.partie.getJoueur(unite.getJoueur()).getListeUnite())
        {
            if(u.aBesoinDeSoins())
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
        return new Cible(new Triplet (t.d,t.x, t.y),cible);
    }
}