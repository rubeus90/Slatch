
/**
 * Write a description of class Moteur here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Moteur
{
    public void attaque(Unite pAttaquant, Unite pVictime)
    {
        double degatsAtt=0;
        degatsAtt=pAttaquant.getAttaque().degats*pAttaquant.getAttaque().efficacite.get(pVictime.getType());
        pVictime.addVie((int)-degatsAtt);
        if(pVictime.getVie()<=0)
        {
            pAttaquant.addExperience(60);
            if(pAttaquant.getExperience()>=100 && pAttaquant.getLvl()<=3)
            {
                pAttaquant.upLvl();
            }
            estMort(pVictime);
        }    
        
    }
    
    public void estMort(Unite pUnite)
    {
        //Need le tableau Unite[][] de Partie
    }
    
}
