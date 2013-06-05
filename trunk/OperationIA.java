import java.awt.Point;
public class OperationIA
{
    static boolean[][] valide = new boolean[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
    static void joueUnite(Unite unite, Influence[][] map)
    {
        Slatch.moteur.remplitPorteeDep(unite, false);
        adapteMap(unite, map);
        
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
                p= trouverBonneCase(unite, map, new Influence(1,5, 1, -4, 5));
            }
            else if(unite.peutSoigner())
            {
                p= trouverBonneCase(unite, map, new Influence(3,3, 1, -4, 2));
            }
            else if(unite.peutCapturer())
            {
                p= trouverBonneCase(unite, map, new Influence(4,3, 4000, -3, 1));
            }
            else // l'unité peut attaquer
            {
                p= trouverBonneCase(unite, map, new Influence(2,3, 5000, -3, 1));
            }
            
            int x= (int)(p.getX());
            int y= (int)(p.getY());
            
            
            Unite u= Slatch.partie.getTerrain()[x][y].getUnite();
            
            
            if(u!=null)
            {
                for(Point voisin: Moteur.voisins)
                {
                    int vx= (int)(voisin.getX())+x;
                    int vy= (int)(voisin.getY())+y;
                    
                    if(Moteur.dansLesBords(vx, vy))
                    {
                        if(Slatch.partie.getTerrain()[vx][vy].getUnite()==null)
                        {
                            if(Slatch.moteur.tabDist[vx][vy]<t.d || t.d==-1)
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
        
        //System.out.println(unite+" se dirige vers ("+x+","+y+")");
        Point pwin=new Point(x,y);
        Unite u= Slatch.partie.getTerrain()[cx][cy].getUnite();
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, false);
        if(Slatch.partie.getTerrain()[cx][cy].estCapturable()&&unite.peutCapturer()&&u==null)
        {
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
                UniteIA.decrypterObjectif(new Objectif("attaquer", null, pwin,unite, u));
            }
        }
        else
        {
            UniteIA.decrypterObjectif(new Objectif("aller", null, pwin,unite, null));
        }
        
        StrategieIA.spreadInfluence(unite,StrategieIA.iMap, true);
    }
    
    static void adapteMap(Unite unite, Influence[][] map)
    {
        StrategieIA.spreadInfluence(unite,map, false);
        for(int i=0; i<Slatch.partie.getNbrJoueur(); i++)
        {
            if(Slatch.partie.getJoueur(i).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe())
            {
                for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                {
                    int x=0,y=0;
                    if(unite.getAttaque().efficacite.containsKey(u.getType()))
                    {
                        if(Slatch.moteur.estAPortee(unite, u)){x+= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*20.0);}
                        x+= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5.0);
                    }
                    
                    if(u.getAttaque().efficacite.containsKey(unite.getType()))
                    {
                        y+= (int)(u.getAttaque().efficacite.get(unite.getType()).doubleValue()*5.0);
                    }
                    
                    Influence inf = new Influence(0,0,x,y,0);
                    StrategieIA.spreadInfluence(u, map, true, inf);
                }
            }
        }
    }
    
    
    static Point trouverBonneCase(Unite unite, Influence[][] map, Influence pond)
    {
        int memi =0, memj =0;
        int mems=0;
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                int s = sommePonderee(map[i][j], pond);
                if(s>mems && valide[i][j])
                {
                    mems =s;
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
