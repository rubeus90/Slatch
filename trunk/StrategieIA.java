import java.util.List;
import java.util.Iterator;
public class StrategieIA
{
    static Influence[][]iMap;
    static ModeIA mode = ModeIA.DEPLOIEMENT;
    
    static void joueTour(int joueur)
    {
        List<Unite> l = Slatch.partie.getJoueur(joueur).getListeUnite();
        GrandeIA.acheterUnite();
        
        mode = mode.checkMode(joueur);
        
        iMap = new Influence[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                iMap[i][j]=new Influence();
            }
        }
        
        remplirMap();
        Iterator<Unite> i = l.iterator();
        while(i.hasNext())
        {
            Unite u = i.next();
            if(u!=null){
                if(!u.dejaAttaque()||!u.dejaDeplacee()){OperationIA.joueUnite(u, copierMap(iMap));}
            }
            if(u.getPV()<=0){i.remove();}
        }    
        Slatch.moteur.passeTour();
    }
    
    static void remplirMap()
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                iMap[i][j].defensif+=100*Slatch.partie.getTerrain()[i][j].getType().getCouverture()*mode.inf.defensif;
                switch(Slatch.partie.getTerrain()[i][j].getType().getNom())
                {
                    case "usine": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){iMap[i][j].capture+=200*mode.inf.capture;}
                    else{iMap[i][j].retraite+=400*mode.inf.retraite;}
                    case "batiment": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){iMap[i][j].capture+=100*mode.inf.capture; /*System.out.println("("+i+","+j+") : Batiment avec valeur de capture: "+iMap[i][j].capture);*/;} 
                    else{iMap[i][j].retraite+=500*mode.inf.retraite;} break;
                    case "foret": break;
                    case "montagne": break;
                    case "qg": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){iMap[i][j].capture+=500*mode.inf.capture;}break;
                    default:
                }
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null)
                {
                    spreadInfluence(Slatch.partie.getTerrain()[i][j].getUnite(), iMap, true);
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
        int x= unite.getCoordonneeX();
        int y= unite.getCoordonneeY();
        int pm = unite.getAttaque().aTypePortee.getPorteeMax();
        int pu = unite.getDeplacement()/10;
        if(Slatch.moteur.getEquipe(unite)!=Slatch.moteur.getJoueurActuel().getEquipe().getNumEquipe())
        {
            for(int i=0; i<=pm; i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {   
                        int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                        if(Moteur.dansLesBords(x+a+b, y+c+d))
                        {
                            if(ajouterInfluence){
                                if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+a+b][y+c+d].menace+=(60*(pm-i+1))*inf.menace;}
                                //if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+a+b][y+c+d].offensif+=(30*(pm-i+1))*inf.offensif;}
                            }
                            else
                            {
                                if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+a+b][y+c+d].menace-=(60*(pm-i+1))*inf.menace;}
                               // if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+a+b][y+c+d].offensif-=(30*(pm-i+1))*inf.offensif;}
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
                                map[x+a+b][y+c+d].defensif+=(50*(pu-i+1))*inf.defensif;
                                map[x+a+b][y+c+d].retraite+=(50*(pu-i+1))*inf.retraite;
                            }
                        }
                        else
                        {
                            if(Moteur.dansLesBords(x+a+b, y+c+d))
                            {
                                map[x+a+b][y+c+d].defensif-=(50*(pu-i+1))*inf.defensif;
                                map[x+a+b][y+c+d].retraite-=(50*(pu-i+1))*inf.retraite;
                            }
                        }
                    }
                }
            }
        }
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
