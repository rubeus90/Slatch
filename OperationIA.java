public class OperationIA
{
    static void joueUnite(Unite unite, Influence[][] map)
    {
        adapteMap(unite, map);
    }
    
    static void adapteMap(Unite unite, Influence[][] map)
    {
        StrategieIA.spreadInfluence(unite,map, false);
        if(unite.estLowHP())
        {
            changerMap(new Influence(1,3,1,5,5), map);
        }
        else if(unite.getType()!=TypeUnite.INGENIEUR)
        {
            for(int i=0; i<Slatch.partie.getNbrJoueur(); i++)
            {
                if(Slatch.partie.getJoueur(i).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe())
                {
                    for(Unite u: Slatch.partie.getJoueur(i).getListeUnite())
                    {
                        int x=0,y=0;
                        if(unite.getAttaque().efficacite.containsKey(u.getType()))
                        {
                            if(Slatch.moteur.estAPortee(unite, u)){x= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5.0);}
                            x= (int)(unite.getAttaque().efficacite.get(u.getType()).doubleValue()*5.0);
                        }
                        
                        if(u.getAttaque().efficacite.containsKey(unite.getType()))
                        {
                            y= (int)(u.getAttaque().efficacite.get(unite.getType()).doubleValue()*5.0);
                        }
                        
                        Influence inf = new Influence(0,0,x,y,0);
                        StrategieIA.spreadInfluence(u, map, true, inf);
                    }
                }
            }
        }
    }
    
    
    static void changerMap(Influence inf, Influence[][] map)
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                map[i][j].defensif+=100*Slatch.partie.getTerrain()[i][j].getType().getCouverture()*inf.defensif;
                switch(Slatch.partie.getTerrain()[i][j].getType().getNom())
                {
                    case "usine": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){map[i][j].capture+=100*inf.capture;}
                    else{map[i][j].retraite+=400;}
                    case "batiment": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){map[i][j].capture+=200*inf.capture;} 
                    else{map[i][j].retraite+=500;}break;
                    case "foret": break;
                    case "montagne": break;
                    case "qg": if(Slatch.moteur.getJoueurTerrain(i,j).getEquipe()!=Slatch.moteur.getJoueurActuel().getEquipe()){map[i][j].capture+=500*inf.capture;}break;
                    default:
                }
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null)
                {
                    StrategieIA.spreadInfluence(Slatch.partie.getTerrain()[i][j].getUnite(), map, true, inf);
                }
            }
        }
    }
}
