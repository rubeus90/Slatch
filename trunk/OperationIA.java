import java.awt.Point;
import java.util.Arrays;
public class OperationIA
{
    static boolean[][] valide = new boolean[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
    static Influence[][] map;
    static void joueUnite(Unite unite, Influence[][] pMap)
    {
        map = pMap;
        Slatch.moteur.remplitPorteeDep(unite, false);
        adapteMap(unite);
        valide = new boolean[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                valide[i][j]=true;
            }
        }
       
        Triplet t = new Triplet(-1,-1,-1);
        Point p = null;
        while(t.d==-1)
        {            
            if(unite.estLowHP())
            {
                p= trouverBonneCase(unite, new Influence(0,5, 1, -4, 5));
            }
            else if(unite.peutSoigner())
            {
                p= trouverBonneCase(unite, new Influence(3,6, 0, -2, 2));
            }
            else if(unite.peutCapturer())
            {
                p= trouverBonneCase(unite, new Influence(10,1, 10, -2, 1));
            }
            else // l'unité peut attaquer
            {
                p= trouverBonneCase(unite, new Influence(0,5, 150, -20, 5));
            }
            
            //p= trouverBonneCase(unite, new Influence(0,0, 1, 0, 0));
            //p= trouverBonneCase(unite, new Influence(1,0, 0, 0, 0));
            
            int x= (int)(p.getX());
            int y= (int)(p.getY());
            if(x==-1 || y==-1){return;}
            Unite u= Slatch.partie.getTerrain()[x][y].getUnite();
        
            if(u!=null && u!=unite)
            {
                for(Point voisin: Moteur.voisins)
                {
                    int vx= (int)(voisin.getX())+x;
                    int vy= (int)(voisin.getY())+y;
                    if(Moteur.dansLesBords(vx, vy))
                    {
                        if(Slatch.partie.getTerrain()[vx][vy].getUnite()==null)
                        {
                            if(Slatch.moteur.tabDist[vx][vy]<t.d || (t.d==-1&&Slatch.moteur.tabDist[vx][vy]!=-1))
                            {
                                t.x=vx;
                                t.y=vy;
                                t.d=Slatch.moteur.tabDist[vx][vy];
                            }
                        }
                    }
                }
            }
            else
            {
                t.d= Slatch.moteur.tabDist[x][y];
                t.x=x;
                t.y=y;
            }
            valide[x][y]=false;
        }
        int x=t.x;
        int y= t.y;
        
        int cx = (int)(p.getX());
        int cy = (int)(p.getY());
        
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, false);
        
        Point pwin=new Point(x,y); // coordonnees du point d'arrivée
        Unite u= Slatch.partie.getTerrain()[cx][cy].getUnite();
        
        if(Slatch.partie.getTerrain()[cx][cy].estCapturable()&&unite.peutCapturer()&&(u==null || u==unite))
        {
            //if(unite==Slatch.moteur.getJoueurActuel().getListeUnite().get(0) && Slatch.partie.getJoueurActuel()==1){System.out.println(unite +" du joueur "+unite.getJoueur()+ " a reçu l'ordre de capturer le point "+x+" "+y);}
            UniteIA.decrypterObjectif(new Objectif("capture", null, pwin,unite, null));
        }
        else if(u!=null)
        {
            if(Slatch.moteur.getEquipe(u)==Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                if(unite.peutSoigner()){UniteIA.decrypterObjectif(new Objectif("soigner", null, pwin,unite, u));}
                else
                {
                    UniteIA.decrypterObjectif(new Objectif("aller", null, pwin,unite, null));
                }
            }
            else
            {
                //System.out.println(unite+" va attaquer "+u);
                UniteIA.decrypterObjectif(new Objectif("attaquer", null, pwin,unite, u));
            }
        }
        else
        {
            UniteIA.decrypterObjectif(new Objectif("aller", null, pwin,unite, null));
        }
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, true);
    }
    
    static void adapteMap(Unite unite)
    {
        StrategieIA.spreadInfluence(unite,map, false);
        for(int i=0; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            if(Slatch.partie.getJoueur(i).getEquipe().getNumEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    int uX = u.getCoordonneeX();
                    int uY = u.getCoordonneeY();
                    
                    if(Slatch.partie.getTerrain()[uX][uY].getType()==TypeTerrain.QG && Slatch.partie.getTerrain()[uX][uY].appartientAuJoueur(Slatch.partie.getJoueurActuel()))
                    {
                        map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= 5000;
                    }
                    int x=0,y=0;
                    if(unite.getAttaque().efficacite.containsKey(u.getType()))
                    {
                        if(Slatch.moteur.seraAPortee(unite, u))
                        {
                            map[u.getCoordonneeX()][u.getCoordonneeY()].offensif+= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*500.0*StrategieIA.mode.inf.offensif*(double)(u.getPVMax())/(double)(u.getPV()));
                        }
                        x+= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*1.0);
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
                    /*if((Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()]<=unite.getDeplacement() && Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()]>=0 && t.getUnite()==null))
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=2000;
                    }*/
                    if(t.getUnite()==unite)
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=5000;
                    }
                    map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=5000/Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()];
                }
                
                for(Terrain t: Slatch.partie.getJoueur(i).getListeUsine())
                {
                    /*if((Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()]<=unite.getDeplacement() && Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()]>=0 && t.getUnite()==null))
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=2000;
                    }*/
                    if(t.getUnite()==unite)
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=5000;
                    }
                    else
                    {
                        map[t.getCoordonneeX()][t.getCoordonneeY()].capture+=5000/Slatch.moteur.tabDist[t.getCoordonneeX()][t.getCoordonneeY()];
                    }
                }
            }
        }
    }
    
    
    static Point trouverBonneCase(Unite unite, Influence pond)
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
    }
    
    static int sommePonderee(Influence inf, Influence pond)
    {
        return (inf.capture*pond.capture+inf.offensif*pond.offensif+inf.defensif*pond.defensif+inf.menace*pond.menace+inf.retraite*pond.retraite);
    }
}
