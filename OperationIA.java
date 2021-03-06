import java.util.PriorityQueue;

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
            cible= trouverBonneCase(unite, new Influence(0,0, 0, -4, 15));
        }
        else if(unite.peutSoigner())
        {
           cible= trouverBonneCase(unite, new Influence(1,70, 0, -1, 1));
        }
        else if(unite.peutCapturer())
        {
            cible= trouverBonneCase(unite, new Influence(800,1,20, -1, 1));
        }
        else if(unite.isEvolvable())
        {
            cible= trouverBonneCase(unite, new Influence(0,1, 0, 0, 0));
        }        
        else{// l'unité peut attaquer
            cible= trouverBonneCase(unite, new Influence(0,0, 1, 0, 0));
        }
        
        if(unite.getAttaque().aTypePortee.getPorteeMin()>1 && Slatch.partie.getTerrain()[unite.getX()][unite.getY()].getType()==TypeTerrain.USINE && Slatch.partie.getJoueur(Slatch.partie.getTerrain()[unite.getX()][unite.getY()].getJoueur()).getEquipe() == Slatch.moteur.getJoueurActuel().getEquipe())
        {
            cible= trouverBonneCase(unite, new Influence(0,1, 0, 0, 0));
        }

        if(cible == null){return;}
        int x=cible.getX();
        int y= cible.getY();
        
        
        
        Unite u= Slatch.partie.getTerrain()[x][y].getUnite();
        if(unite.isEvolvable())
        {
            UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.EVOLUER));
        }
        else if(Slatch.partie.getTerrain()[x][y].estCapturable()&&unite.peutCapturer()&&(u==null || u==unite))
        {
            
            UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.CAPTURER));
        }
        else if(u!=null)
        {
            if(Slatch.moteur.getEquipe(u)==Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                if(unite.getType()==TypeUnite.INGENIEUR && u.aBesoinDeSoins())
                {
                    
                    UniteIA.decrypterObjectif(new Objectif(unite, u, TypeObjectif.SOIGNER));
                }
                else
                {
                    
                    UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.ALLER));
                }
            }
            else
            {
                
                UniteIA.decrypterObjectif(new Objectif(unite, u, TypeObjectif.ATTAQUER));
            }
        }
        else
        {
            
            UniteIA.decrypterObjectif(new Objectif(unite, cible, TypeObjectif.ALLER));
        }
        
        
    }
    
    static void adapteMap(Unite unite)
    {
        int[][] tabDist = Slatch.moteur.tabDist;
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
                        map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= pontDerration(5000, tabDist[uX][uY])+5000;
                    }
                    int x=0,y=0;
                    if(unite.getAttaque().efficacite.containsKey(u.getType()))
                    {
                        if(Slatch.moteur.seraAPortee(unite, u))
                        {
                            map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+=(int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5000.0*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV()));
                        }
                        map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= pontDerration((int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*50000.0*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV())), 20*Slatch.moteur.schmurtzDistance(uX, uY));
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
                        if(t.getPV()<t.getType().getPVMax()){map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000/((double)t.getPV()/(double)t.getType().getPVMax());}
                        else{map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000;}
                    }
                }
                
                for(Terrain t: Slatch.partie.getJoueur(i).getListeUsine())
                {
                    if(t.getUnite()==null)
                    {
                        map[t.getX()][t.getY()].capture+=pontDerration(7000,tabDist[t.getX()][t.getY()]);
                    }
                    if(t.getUnite()==unite)
                    {
                        if(t.getPV()<t.getType().getPVMax()){map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000/((double)t.getPV()/(double)t.getType().getPVMax());}
                        else{map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=7000;}
                    }
                }
            }
            if(Slatch.partie.getJoueur(i).getEquipe().getNumEquipe()==Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe() && unite.getType()==TypeUnite.INGENIEUR)
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    if(u.aBesoinDeSoins() && u!=unite)
                    {
                        map[u.getX()][u.getY()].defensif+=pontDerration((int)(5000.0*(double)(u.getPV())/(double)(u.getPVMax())),tabDist[u.getX()][u.getY()]);
                        if(Slatch.moteur.seraAPortee(unite, u))
                        {
                            map[u.getCoordonneeX()][u.getCoordonneeY()].defensif+=pontDerration((int)(5000.0*(double)(u.getPV())/(double)(u.getPVMax())),tabDist[u.getX()][u.getY()]);
                        }
                    }
                }
            }
        }
        if(unite.getAttaque().aTypePortee.getPorteeMin()>1 && Slatch.partie.getTerrain()[unite.getX()][unite.getY()].getType()==TypeTerrain.USINE && Slatch.partie.getTerrain()[unite.getX()][unite.getY()].appartientAuJoueur(Slatch.partie.getJoueurActuel()))
        {
            map[unite.getX()][unite.getY()].defensif-=2000;
        }
    }
    
    static int pontDerration(int x, int y)
    {
        if(y<0 || y>x){return 0;}
        if(y==0){return x;}
        return x/y;
    }
    
    /**
     * Trouve la bonne case
     */
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
    
    /**
     * Achete les unites
     */
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
            if(usine.getUnite()==null){pq.add(new Triplet(-StrategieIA.iMap[usine.getX()][usine.getY()].menace, usine.getX(),usine.getY()));}
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
    
                if(joueurActuel.getArgent()>=100 && nombreCommando<3 && nombreWhile>0)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.COMMANDO), TypeObjectif.ACHETER));
                    nombreCommando=nombreCommando+1;
                }
                else if(joueurActuel.getArgent()>=700 && nombreWhile<3)
                {                 
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.WHILE), TypeObjectif.ACHETER));                    
                    nombreWhile=nombreWhile+1;
                }
                else if(joueurActuel.getArgent()>=450 && nombreUml <3)         
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.UML), TypeObjectif.ACHETER));
                    nombreUml=nombreUml+1;
                }
                else if(joueurActuel.getArgent()>=350 && nombreDistance<2)         
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DISTANCE), TypeObjectif.ACHETER));
                   nombreDistance=nombreDistance+1;
                }
                else if(joueurActuel.getArgent()>=300 && nombreTank <2)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.CHAR), TypeObjectif.ACHETER));
                     nombreTank=nombreTank+1;
                }
                else if(joueurActuel.getArgent()>=200 && nombreDemolisseur <2)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.DEMOLISSEUR), TypeObjectif.ACHETER));
                    nombreDemolisseur=nombreDemolisseur+1;
                }
                else if(joueurActuel.getArgent()>=100 && nombreCommando<3)
                {
                    UniteIA.decrypterObjectif(new Objectif(usine, new Unite(0,0,0,TypeUnite.COMMANDO), TypeObjectif.ACHETER));
                    nombreCommando=nombreCommando+1;
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
            }
        }
        
    }
}
