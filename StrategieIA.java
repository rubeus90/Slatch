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
                    else{iMap[i][j].retraite+=400;}
                    case "batiment": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){iMap[i][j].capture+=100*mode.inf.capture;} 
                    else{iMap[i][j].retraite+=500;}break;
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
        if(Slatch.moteur.getEquipe(unite)!=Slatch.moteur.getJoueurActuel().getEquipe())
        {
            for(int i=0; i<unite.getAttaque().aTypePortee.getPorteeMax(); i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {   
                        if(Moteur.dansLesBords(x+i*q.a+j*q.b, y+i*q.c+j*q.d))
                        {
                            if(ajouterInfluence){
                                if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].menace+=(400-50*i)*inf.menace;}
                            }
                            else
                            {
                                if(i>=unite.getAttaque().aTypePortee.getPorteeMin()){map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].menace-=(400-50*i)*inf.menace;}
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for(int i=0; i<unite.getDeplacement(); i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {   
                        if(ajouterInfluence){
                            if(Moteur.dansLesBords(x+i*q.a+j*q.b, y+i*q.c+j*q.d))
                            {
                                map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].defensif+=(300-50*i)*inf.defensif;
                                map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].retraite+=(300-50*i)*inf.retraite;
                            }
                        }
                        else
                        {
                            if(Moteur.dansLesBords(x+i*q.a+j*q.b, y+i*q.c+j*q.d))
                            {
                                map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].defensif-=(300-50*i)*inf.defensif;
                                map[x+i*q.a+j*q.b][y+i*q.c+j*q.d].retraite-=(300-50*i)*inf.retraite;
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
