import java.util.List;
import java.util.Iterator;
import java.util.Arrays;

public class StrategieIA
{
    static Influence[][]iMap= new Influence[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
    static ModeIA mode = ModeIA.DEPLOIEMENT;
    static void joueTour(int joueur)
    {
        List<Unite> l = Slatch.partie.getJoueur(joueur).getListeUnite();
         
        mode = mode.checkMode(joueur);
        
        remplirMap();
        OperationIA.acheterUnite();
        
        Iterator<Unite> i = l.iterator();
        while(i.hasNext())
        {
            Unite u = i.next();
            if(u!=null){
                if(!u.dejaAttaque()||!u.dejaDeplacee()){OperationIA.joueUnite(u, copierMap(iMap));}
            }
            if(u.getPV()<=0){i.remove();}
        }    
        Slatch.ihm.getAnimation().start();
        if(Slatch.partie.getJoueur(joueur).getListeUnite().isEmpty())
        {
            Slatch.partie.getJoueur(joueur).mourrir();
        }
    }
    
    static void remplirMap()
    {
        Terrain[][] mapTerrain = Slatch.partie.getTerrain();
        Equipe fail = Slatch.moteur.getJoueurActuel().getEquipe();
        System.out.println(Slatch.partie.getLargeur() +" ? "+ Slatch.partie.getMap().getLongueur());
        System.out.println(Slatch.partie.getHauteur() +" ? "+ Slatch.partie.getMap().getLargeur());
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                iMap[i][j]=new Influence();
            }
        }
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                iMap[i][j].defensif+=mapTerrain[i][j].getType().getCouverture()*mode.inf.defensif;
                switch(mapTerrain[i][j].getType())
                {
                    case USINE: if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=fail){if(mapTerrain[i][j].getUnite()==null){iMap[i][j].capture+=6*mode.inf.capture;}}
                    else{iMap[i][j].retraite+=4*mode.inf.retraite;}
                    case BATIMENT: if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=fail){if(mapTerrain[i][j].getUnite()==null){iMap[i][j].capture+=4*mode.inf.capture;}} 
                    else{iMap[i][j].retraite+=5*mode.inf.retraite;} break;
                    case FORET: break;
                    case MONTAGNE: break;
                    case QG: if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=fail){if(mapTerrain[i][j].getUnite()==null){iMap[i][j].capture+=10*mode.inf.capture;}}break;
                    default:
                }
                if(mapTerrain[i][j].getUnite()!=null)
                {
                    spreadInfluence(mapTerrain[i][j].getUnite(), iMap, true);
                }
            }
        }
    }
    
    static void spreadInfluence(Unite unite, Influence[][] map, boolean ajouterInfluence)
    {
        spreadInfluence(unite, map, ajouterInfluence, mode.inf);
    }
    
    static void spreadInfluence(Unite unite, Influence[][] map, boolean ajouterInfluence, Influence inf)
    {
        int x= unite.getX();
        int y= unite.getY();
        //int pm = unite.getAttaque().aTypePortee.getPorteeMax();
        //int pu = unite.getDeplacement()/10;
        Terrain[][] mapTerrain = Slatch.partie.getTerrain();
        int joueurActu= Slatch.partie.getJoueurActuel();
        Influence[][] mapInf = unite.mapInfluence;
        
        if(ajouterInfluence)
        {
            if(Slatch.moteur.getEquipe(unite)!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                for(int i=0; i<mapInf.length; i++)
                {
                    for(int j=0; j<mapInf.length; j++)
                    {
                        if(Moteur.dansLesBords(x+i-(mapInf.length-1)/2, y+j-(mapInf.length-1)/2))
                        {
                            //map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].offensif+=mapInf[i][j].offensif*inf.offensif;
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].menace+=mapInf[i][j].menace*inf.menace;
                        }
                    }
                }
            }
            else
            {
                for(int i=0; i<mapInf.length; i++)
                {
                    for(int j=0; j<mapInf.length; j++)
                    {
                        if(Moteur.dansLesBords(x+i-(mapInf.length-1)/2, y+j-(mapInf.length-1)/2))
                        {
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].defensif+=mapInf[i][j].defensif*inf.defensif;
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].retraite+=mapInf[i][j].retraite*inf.retraite;
                        }
                    }
                }
            }
        }
        else
        {
            if(Slatch.moteur.getEquipe(unite)!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
            {
                for(int i=0; i<mapInf.length; i++)
                {
                    for(int j=0; j<mapInf.length; j++)
                    {
                        if(Moteur.dansLesBords(x+i-(mapInf.length-1)/2, y+j-(mapInf.length-1)/2))
                        {
                            //map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].offensif-=mapInf[i][j].offensif*inf.offensif;
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].menace-=mapInf[i][j].menace*inf.menace;
                        }
                    }
                }
            }
            else
            {
                for(int i=0; i<mapInf.length; i++)
                {
                    for(int j=0; j<mapInf.length; j++)
                    {
                        if(Moteur.dansLesBords(x+i-(mapInf.length-1)/2, y+j-(mapInf.length-1)/2))
                        {
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].defensif-=mapInf[i][j].defensif*inf.defensif;
                            map[x+i-(mapInf.length-1)/2][y+j-(mapInf.length-1)/2].retraite-=mapInf[i][j].retraite*inf.retraite;
                        }
                    }
                }
            }
        }
        
        /*if(Slatch.moteur.getEquipe(unite)!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
        {
            for(int i=0; i<=pm+pu; i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {   
                        int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                        if(Moteur.dansLesBords(x+a+b, y+c+d))
                        {
                            if(ajouterInfluence){
                                if(Slatch.moteur.seraAPortee(unite, x+a+b,y+c+d)){map[x+a+b][y+c+d].menace+=5*inf.menace;if(mapTerrain[x+a+b][y+c+d].getType()==TypeTerrain.QG && mapTerrain[x+a+b][y+c+d].getJoueur()==joueurActu)
                                    {
                                        map[x+a+b][y+c+d].menace+=5*inf.menace;
                                    }
                                }
                                if(i<=3){map[x+a+b][y+c+d].offensif+=(1*(pm+pu-i+1))*inf.offensif;}
                            }
                            else
                            {
                                if(Slatch.moteur.seraAPortee(unite, x+a+b,y+c+d)){map[x+a+b][y+c+d].menace-=5*inf.menace;
                                    if(mapTerrain[x+a+b][y+c+d].getType()==TypeTerrain.QG && mapTerrain[x+a+b][y+c+d].getJoueur()==joueurActu)
                                    {
                                        map[x+a+b][y+c+d].menace-=5*inf.menace;
                                    }
                                }
                                if(i<=3){map[x+a+b][y+c+d].offensif-=(1*(pm+pu-i+1))*inf.offensif;}
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for(int i=0; i<=pu; i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {   
                        int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                        if(ajouterInfluence){
                            if(Moteur.dansLesBords(x+a+b, y+c+d))
                            {
                                map[x+a+b][y+c+d].defensif+=(5*(pu-i+1))*inf.defensif;
                                map[x+a+b][y+c+d].retraite+=(5*(pu-i+1))*inf.retraite;
                            }
                        }
                        else
                        {
                            if(Moteur.dansLesBords(x+a+b, y+c+d))
                            {
                                 map[x+a+b][y+c+d].defensif-=(5*(pu-i+1))*inf.defensif;
                                 map[x+a+b][y+c+d].retraite-=(5*(pu-i+1))*inf.retraite;
                            }
                        }
                    }
                }
            }
        }*/
    }
    
    static Influence[][] copierMap(Influence[][] map)
    {
        Influence[][] ret = new Influence[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                ret[i][j]= new Influence();
                ret[i][j].capture=map[i][j].capture;
                ret[i][j].defensif=map[i][j].defensif;
                ret[i][j].offensif=map[i][j].offensif;
                ret[i][j].menace=map[i][j].menace;
                ret[i][j].retraite=map[i][j].retraite;
            }
        }
        return ret;
    }
    
   
    
}