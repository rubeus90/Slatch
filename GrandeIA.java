
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

        int X = unite.getCoordonneeX();
        int Y = unite.getCoordonneeY();
        
        if( (Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.BATIMENT || Slatch.partie.getTerrain()[X][Y].getType()==TypeTerrain.USINE) && Slatch.partie.getTerrain()[X][Y].getJoueur()!=unite.getJoueur() )
        {
            uia.decrypterObjectif(new Objectif("capture",null,new Point(X,Y),unite,null));
            return;
        }
        
        Slatch.moteur.remplitPorteeDep(unite, false);
        Triplet batimentProche = determineBatimentProche(unite);
        Triplet ennemiProche = determineEnnemiProche(unite);
        Triplet cible;
        
        
        
        if( (batimentProche.d > ennemiProche.d) && ennemiProche.d!=-1)
        {
            Unite ennemi = Slatch.partie.getTerrain()[ennemiProche.x][ennemiProche.y].getUnite();
            cible = determineVoisinProcheEnnemi(unite,ennemiProche);
            uia.decrypterObjectif(new Objectif("attaquer", null, new Point(cible.x,cible.y),unite,ennemi));
        }
        else if(batimentProche.d!=-1)
        {
            cible = batimentProche;
            uia.decrypterObjectif(new Objectif("capture", null, new Point(cible.x,cible.y),unite,null));
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
    
    static Triplet determineEnnemiProche2(Unite unite)
    {
        Triplet t = new Triplet(-1, -1, -1);
        Unite cible=null;
        label:for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            if(i!=unite.getJoueur())
            {
                
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                        int x= u.getCoordonneeX();
                        int y=u.getCoordonneeY();
                            if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                cible=u;
                            
                            }
                    
                }
            }
        }
        
        return t;
    }
    
    static Triplet determineEnnemiProche(Unite unite)
    {
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
                        
                        X= u.getCoordonneeX();
                        Y= u.getCoordonneeY();
                        if(Moteur.dansLesBords(x,y))
                        {
                            if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                            }
                            if(unite.seSitue(new Point(x,y)))
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                break label;
                                /*uia.decrypterObjectif(new Objectif("attaquer", null, new Point(t.x,t.y), unite,u));
                                return new Point(x,y);*/
                            }
                        }
                    }
                }
            }
        }
        
        return new Triplet (t.d,X,Y);
    }
    
    static Triplet determineVoisinProcheEnnemi(Unite unite,Triplet cible)
    {
        Unite u = Slatch.partie.getTerrain()[cible.x][cible.y].getUnite();
        Slatch.moteur.remplitPorteeDep(unite, false);
        Triplet t=new Triplet(-1, -1, -1);
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
                            }
                            if(unite.seSitue(new Point(x,y)))
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                break;
                                /*uia.decrypterObjectif(new Objectif("attaquer", null, new Point(t.x,t.y), unite,u));
                                return new Point(x,y);*/
                            }
                        }
                    }
                    
        return t;
    }
    
    static void test(Unite unite)
    {
        int x =15, y =4;
        //if(Slatch.partie.getTerrain()[x][y].getUnite()==null)
        //{
            uia.decrypterObjectif(new Objectif("acheter","Commando",new Point(x,y),null,null));
        //}
        Slatch.moteur.passeTour();
        /*Salut les conflits, fuck you*/
    }
    
    static void test2(Unite unite)
    {
        if(!unite.dejaDeplacee() && !unite.dejaAttaque() )
        {
            
            Terrain batimentproche;
            List<Terrain> pasNosBatiment= new ArrayList<Terrain>();
            for(int i=0;i<Slatch.partie.getNbrJoueur()+1;i++)
            {
                if(i!=Slatch.partie.getJoueurActuel())
                {
                    if(!Slatch.partie.getJoueur(i).getListeUsine().isEmpty())
                    pasNosBatiment.addAll(Slatch.partie.getJoueur(i).getListeUsine());
                    
                     if(!Slatch.partie.getJoueur(i).getListeBatiment().isEmpty())
                    pasNosBatiment.addAll(Slatch.partie.getJoueur(i).getListeBatiment());
                }
            }
            
            Slatch.moteur.remplitPorteeDep(unite, false);
            Triplet t = new Triplet(-1, -1, -1);
            int x,y;
            for(Terrain batiment : pasNosBatiment)
            {
                x=batiment.getCoordonneeX();
                y=batiment.getCoordonneeY();
                if(unite.getCoordonneeX()==x && unite.getCoordonneeY()==y)
                {
                    uia.decrypterObjectif(new Objectif("capture",null,new Point(x,y),unite,null));
                    break;
                }
                
                if((Slatch.moteur.tabDist[x][y]<t.d || t.d==-1)&& Slatch.moteur.tabDist[x][y]>0 && Slatch.partie.getTerrain()[x][y].getUnite() ==null)
                                {
                                    t.d = Slatch.moteur.tabDist[x][y];
                                    t.x = x;
                                    t.y = y;
                                }
            }
         
            
            if(!(pasNosBatiment.isEmpty())&&(!(t.x==-1) || !(t.y==-1))){
                
                if(unite.getCoordonneeX()==t.x && unite.getCoordonneeY()==t.y)
                {
                    uia.decrypterObjectif(new Objectif("capture",null,new Point(t.x,t.y),unite,null));
                }
                else if(Slatch.partie.getTerrain()[t.x][t.y].getUnite()==null )
                {
                    
                    uia.decrypterObjectif(new Objectif("capture",null,new Point(t.x,t.y),unite,null));
                    
                }
            }
            if(!unite.dejaAttaque() || !unite.dejaDeplacee())
            {test2uniteProcheAdverse(unite);}
        }
        //if(pwin!=null && !unite.seSitue(pwin))
        //{
            //uia.decrypterObjectif(new Objectif("aller", null, pwin, unite,null));
        //}
        //Slatch.moteur.passeTour();
    }
    
    
    static void test2uniteProcheAdverse(Unite unite)
    {        
        Slatch.moteur.remplitPorteeDep(unite, false);
        //Slatch.moteur.tabDist[unite.getCoordonneeX()][unite.getCoordonneeY()] = -1;
        Triplet t = new Triplet(-1, -1, -1);
        Unite cible=null;
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
                                cible=u;
                            }
                            if(unite.seSitue(new Point(x,y)))
                            {
                                t.d = Slatch.moteur.tabDist[x][y];
                                t.x = x;
                                t.y = y;
                                cible=u;
                                break label;
                                /*uia.decrypterObjectif(new Objectif("attaquer", null, new Point(t.x,t.y), unite,u));
                                return new Point(x,y);*/
                            }
                        }
                    }
                }
            }
        }
        
         
        if(t.d!=-1)
        {
            //System.out.println(t.x+" "+t.y+" "+t.d);
             uia.decrypterObjectif(new Objectif("attaquer", null, new Point(t.x,t.y), unite,cible));
            //return new Point(t.x,t.y);
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
                else if(JoueurActuel.getArgent()>=350)         
                {
                    uia.decrypterObjectif(new Objectif("acheter","Uml",new Point(x,y),null,null));
                                
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
}
