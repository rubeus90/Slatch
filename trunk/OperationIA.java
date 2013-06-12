import java.awt.Point;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.List;
public class OperationIA
{
    static Influence[][] map;
    static void joueUnite(Unite unite, Influence[][] pMap)
    {
        map = pMap;
        Slatch.moteur.remplitPorteeDep(unite, false);
        adapteMap(unite);
        
        
        Entite cible;
        if(unite.estLowHP())
        {
            cible= trouverBonneCase(unite, new Influence(0,20, 1, -4, 4));
        }
        else if(unite.peutSoigner())
        {
            cible= trouverBonneCase(unite, new Influence(3,6, 0, -2, 2));
        }
        else if(unite.peutCapturer())
        {
            cible= trouverBonneCase(unite, new Influence(15,1, 50, -2, 1));
        }
        else // l'unité peut attaquer
        {
            cible= trouverBonneCase(unite, new Influence(0,5, 1500, -5, 5));
        }
       
        //cible= trouverBonneCase(unite, new Influence(1,0, 0, 0, 0));
        //cible= trouverBonneCase(unite, new Influence(0,0, 1, 0, 0));
        if(cible == null){return;}
        int x=cible.getX();
        int y= cible.getY();
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, false);
        
        Unite u= Slatch.partie.getTerrain()[x][y].getUnite();
        
        if(Slatch.partie.getTerrain()[x][y].estCapturable()&&unite.peutCapturer()&&(u==null || u==unite))
        {
            //System.out.println(unite+" en ("+ unite.getX()+","+unite.getY()+") va capturer "+cible+" en ("+cible.getX()+","+cible.getY()+")");
            UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.CAPTURER));
        }
        else if(u!=null)
        {
            if(Slatch.moteur.getEquipe(u)==Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                if(unite.peutSoigner() && u.aBesoinDeSoins())
                {
                    //System.out.println(unite+" en ("+ unite.getX()+","+unite.getY()+") va soigner "+u+" en ("+cible.getX()+","+cible.getY()+")");
                    UniteIA.decrypterObjectif(new Objectif(unite, u, TypeObjectif.SOIGNER));
                }
                else
                {
                    //System.out.println(unite+" en ("+ unite.getX()+","+unite.getY()+") va vers "+u+" en ("+cible.getX()+","+cible.getY()+")");
                    UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.ALLER));
                }
            }
            else
            {
                //System.out.println(unite+" en ("+ unite.getX()+","+unite.getY()+") va attaquer "+u+" en ("+cible.getX()+","+cible.getY()+")");
                UniteIA.decrypterObjectif(new Objectif(unite, u, TypeObjectif.ATTAQUER));
            }
        }
        else
        {
            //System.out.println(unite+" en ("+ unite.getX()+","+unite.getY()+") va vers "+cible+" en ("+cible.getX()+","+cible.getY()+")");
            UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.ALLER));
        }
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, true);
    }
    
    static void adapteMap(Unite unite)
    {
        int[][] tabDist = Slatch.moteur.tabDist;
        if(unite.isEvolvable())
        {
            map[unite.getX()][unite.getY()].defensif+=5001;
        }
        StrategieIA.spreadInfluence(unite,map, false);
        for(int i=0; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            if(Slatch.partie.getJoueur(i).getEquipe().getNumEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe() && !unite.peutSoigner())
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    int uX = u.getCoordonneeX();
                    int uY = u.getCoordonneeY();
                    
                    if(Slatch.partie.getTerrain()[uX][uY].estCapturable() && Slatch.partie.getTerrain()[uX][uY].appartientAuJoueur(Slatch.partie.getJoueurActuel()))
                    {
                        map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= pontDerration(5000, tabDist[uX][uY]);
                    }
                    int x=0,y=0;
                    //map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+=5000;
                    if(unite.getAttaque().efficacite.containsKey(u.getType()))
                    {
                        if(Slatch.moteur.seraAPortee(unite, u))
                        {
                            map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+=(int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5000*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV()));
                        }
                            //map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= pontDerration((int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5000.0*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV())), Slatch.moteur.distance(unite, u));
                            map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= pontDerration((int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*50000.0*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV())), 20*Slatch.moteur.schmurtzDistance(uX, uY));
                        //}
                        //x+= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*1.0);
                    }
                    
                    if(u.getAttaque().efficacite.containsKey(unite.getType()))
                    {
                        y+= (int)(u.getAttaque().efficacite.get(unite.getType()).doubleValue()*1.0);
                    }
                    
                    Influence inf = new Influence(0,0,x,y,0);
                    StrategieIA.spreadInfluence(u, map, true, inf);
                }
                
                for(Terrain t: Slatch.partie.getJoueur(i).getListeBatiment())
                {
                    if(t.getUnite()==null)
                    {
                        map[t.getX()][t.getY()].capture+=pontDerration(5000,tabDist[t.getX()][t.getY()]);
                    }
                    if(t.getUnite()==unite)
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000;
                    }
                    //map[t.getX()][t.getY()].capture+=pontDerration(5000,tabDist[t.getX()][t.getY()]);
                }
                
                for(Terrain t: Slatch.partie.getJoueur(i).getListeUsine())
                {
                    if(t.getUnite()==null)
                    {
                        map[t.getX()][t.getY()].capture+=pontDerration(7000,tabDist[t.getX()][t.getY()]);
                    }
                    if(t.getUnite()==unite)
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000;
                    }
                    /*
                    else
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=5000/Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()];
                    }*/
                    //map[t.getX()][t.getY()].capture+=pontDerration(5000,tabDist[t.getX()][t.getY()]);
                }
            }
            if(Slatch.partie.getJoueur(i).getEquipe().getNumEquipe()==Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe() && unite.getType()==TypeUnite.INGENIEUR)
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    if(u.aBesoinDeSoins() && u!=unite)
                    {
                        map[u.getX()][u.getY()].defensif+=pontDerration((int)(5000.0*(double)(u.getPVMax())/(double)(u.getPV())),tabDist[u.getX()][u.getY()]);
                        if(Slatch.moteur.seraAPortee(unite, u))
                        {
                            map[u.getCoordonneeX()][u.getCoordonneeY()].defensif+=pontDerration((int)(5000.0*(double)(u.getPVMax())/(double)(u.getPV())),tabDist[u.getX()][u.getY()]);
                        }
                    }
                }
            }
        }
    }
    
    static int pontDerration(int x, int y)
    {
        if(y<0 || y>x){return 0;}
        if(y==0){return x;}
        return x/y;
    }
    
    /*static Point trouverBonneCase(Unite unite, Influence pond)
    {
        int memi =-1, memj =-1;
        int mems=0;
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                int s = sommePonderee(map[i][j], pond);
                if(s>mems && valide[i][j])
                {
                    mems=s;
                    memi=i;
                    memj=j;
                }
            }
        }
        return new Point(memi, memj);
    }*/
    
    static Entite trouverBonneCase(Unite unite, Influence pond)
    {
        int memi =-1, memj =-1;
        int mems=0; Entite mem = null;
        
        for(Entite e: Slatch.partie.getListeUnitesBatiments())
        {
            int x = e.getX();
            int y = e.getY(); 
            int s = sommePonderee(map[x][y], pond);
            if(s>mems || mem == null)
            {
                mems=s;
                mem = e;
            }
        }
        return mem;
    }
    
    static int sommePonderee(Influence inf, Influence pond)
    {
        return (inf.capture*pond.capture+inf.offensif*pond.offensif+inf.defensif*pond.defensif+inf.menace*pond.menace+inf.retraite*pond.retraite);
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
        
        for (Unite unit : joueurActuel.getListeUnite())
        {
            switch(unit.getType())
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
                case TANK :
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
            pq.add(new Triplet(-StrategieIA.iMap[usine.getX()][usine.getY()].menace, usine.getX(),usine.getY()));
        }
        
        
        //for(Terrain usine : joueurActuel.getListeUsine())
        while(!pq.isEmpty())
        {
            Triplet t = pq.poll();
            Terrain usine = Slatch.partie.getTerrain()[t.x][t.y];
            int x = usine.getCoordonneeX();
            int y = usine.getCoordonneeY();

            if(joueurActuel.getArgent()>=700)
             {                 
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.TANK), TypeObjectif.ACHETER));                    
                nombreWhile=nombreWhile+1;
             }
            else if(joueurActuel.getArgent()>=450 && nombreUml <1)         
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.UML), TypeObjectif.ACHETER));
                nombreUml=nombreUml+1;
            }
            else if(joueurActuel.getArgent()>=350 && nombreDistance <1)         
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DISTANCE), TypeObjectif.ACHETER));
               nombreDistance=nombreDistance+1;
            }
            else if(joueurActuel.getArgent()>=300 && nombreTank <1)
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.CHAR), TypeObjectif.ACHETER));
                 nombreTank=nombreTank+1;
            }
            else if(joueurActuel.getArgent()>=200 && nombreDemolisseur <3)
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DEMOLISSEUR), TypeObjectif.ACHETER));
                nombreDemolisseur=nombreDemolisseur+1;
            }
            else if(joueurActuel.getArgent()>=100 && nombreCommando <3)
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.COMMANDO), TypeObjectif.ACHETER));
                nombreCommando=nombreCommando+1;
            }
            else if(joueurActuel.getArgent()>=100 && nombreSpec <2 /*&& joueurActuel.getFaction()==Faction.HUMAINS*/)
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.INGENIEUR), TypeObjectif.ACHETER));
                nombreSpec=nombreSpec+1;
            }
            else if(joueurActuel.getArgent()>=100 && nombreSpec <2  && joueurActuel.getFaction()==Faction.ROBOTS)
            {
                UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.KAMIKAZE), TypeObjectif.ACHETER));
                nombreSpec=nombreSpec+1;
            }
            break;
        }
    }
    
    static void acheterUniteAvecStyle()
    {
        Joueur joueurActuel = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel());
        List<Unite> listeEnnemis = Slatch.partie.getListeUnitesEnnemies();
        HashMap<TypeUnite, Integer> map = new HashMap<TypeUnite, Integer>();
        for(TypeUnite t: TypeUnite.values())
        {
            map.put(t, 0);
        }
        for(Unite u: listeEnnemis)
        {
            int mem = map.get(u.getType()).intValue();
            mem++;
            map.put(u.getType(), mem);
        }
    }
}
