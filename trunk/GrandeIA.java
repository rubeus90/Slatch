
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
                        uia.decrypterObjectif(new Objectif("capture",null,new Point(X,Y),unite,null));
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
                uia.decrypterObjectif(new Objectif("soigner", null, new Point(cibleSoin.t.x, cibleSoin.t.y),unite,cibleSoin.u));
            }
        }
        else if( (batimentProche.d >= ennemiProche.t.d) && ennemiProche.t.d!=-1)
        {
            uia.decrypterObjectif(new Objectif("attaquer", null, new Point(ennemiProche.t.x,ennemiProche.t.y),unite,ennemiProche.u));
        }
        else if(batimentProche.d != 900)
        {
            uia.decrypterObjectif(new Objectif("capture", null, new Point(batimentProche.x,batimentProche.y),unite,null));
        }
    }
    
    
    
    static void acheterUnite()
    {
            Joueur joueurActuel = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel());
        
            //Choix de l'unite
            int nombreCommando=0;
            int nombreDemolisseur=0;
            int nombreIngenieur=0;
            int nombreTank=0;
            int nombreUml=0;
            int nombreDistance=0;
            int nombreWhile=0;
            
            for (Unite unit : joueurActuel.getListeUnite())
            {
                switch(unit.getType().getNom())
                {
                    case "Commando" :
                                        nombreCommando=nombreCommando+1;
                                        break;
                    case "Demolisseur" :
                                        nombreDemolisseur=nombreDemolisseur+1;
                                        break;
                    case "Ingenieur" :
                                        nombreIngenieur=nombreIngenieur+1;
                                        break;
                    case "Char" :
                                        nombreTank=nombreTank+1;
                                        break;
                    case "Uml" :
                                        nombreUml=nombreUml+1;
                                        break;
                    case "While" :
                                        nombreWhile=nombreWhile+1;
                                        break;
                    case "Distance" :
                                        nombreDistance=nombreDistance+1;
                                        break;                   
                }
            }
            
            PriorityQueue<Triplet> pq = new PriorityQueue<Triplet>();
            for(Terrain usine : joueurActuel.getListeUsine())
            {
                
            }
            
            
            for(Terrain usine : joueurActuel.getListeUsine())
            //if(joueurActuel.getListeUsine().isEmpty()){return;}
            //for(int i=joueurActuel.getListeUsine().size()-1;i>=0;i--)
            {
                //Terrain usine = joueurActuel.getListeUsine().get(i);
                int x = usine.getCoordonneeX();
                int y = usine.getCoordonneeY();

                if(joueurActuel.getArgent()>=700)
                 {
                    uia.decrypterObjectif(new Objectif("acheter","While",new Point(x,y),null,null));                    
                    //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreWhile+" While donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                    nombreWhile=nombreWhile+1;
                 }
                else if(joueurActuel.getArgent()>=450 && nombreUml <1)         
                {
                    uia.decrypterObjectif(new Objectif("acheter","Uml",new Point(x,y),null,null));
                    //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreUml+" Uml donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                    nombreUml=nombreUml+1;
                }
                else if(joueurActuel.getArgent()>=350 && nombreDistance <1)         
                {
                    uia.decrypterObjectif(new Objectif("acheter","Distance",new Point(x,y),null,null));
                   //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreDistance+" Distance donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                   nombreDistance=nombreDistance+1;
                }
                else if(joueurActuel.getArgent()>=300 && nombreTank <1)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Char",new Point(x,y),null,null));
                     //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreTank+" Tank donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                     nombreTank=nombreTank+1;
                }
                else if(joueurActuel.getArgent()>=200 && nombreDemolisseur <3)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Demolisseur",new Point(x,y),null,null));
                    //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreDemolisseur+" Demolisseur donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                    nombreDemolisseur=nombreDemolisseur+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreCommando <3)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Commando",new Point(x,y),null,null));   
                    //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreCommando+" Commando donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                    nombreCommando=nombreCommando+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreIngenieur <1)
                {
                    uia.decrypterObjectif(new Objectif("acheter","Ingenieur",new Point(x,y),null,null));   
                    //System.out.println(joueurActuel.getNumJoueur()+" : J'ai "+nombreCommando+" Commando donc j'en achete 1 JOUR: "+Slatch.partie.getTour());
                    nombreIngenieur=nombreIngenieur+1;
                }
                else
                {
                  break;   
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
    
    static void aWholeNewWorld(Unite unite)
    {
        
    }
}