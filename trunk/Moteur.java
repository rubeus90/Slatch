/*
 * Possede presque toutes les methodes propres a la mecanique du jeu, il va travailler de paire avec Partie et IHM
 */
public class Moteur
{
    Unite uniteD = null; // unite en attente de déplacement
    Unite uniteA = null; // unite en attente d'attaque
    
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
    
   
   /*
	 * Appelee par l'IHM quand on clique sur une case, cette methode doit generer la liste des coordonnees accessibles par l'unite se trouvant sur la case selectionnee si elle ne s'est pas deja deplacee, et passer cette Liste a l'IHM.
	 */
	public void caseSelectionnee(int pX, int pY)
	{
		Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
		Unite unite = Slatch.partie.getTerrain()[pX][pY].getUnite();
		if(unite==null) // si une unité est présente sur la case
		{
			if(uniteD==null) // si on a sélectioné aucune unité auparavant pour le déplacement
			{
				return;
			}
			deplacement(uniteD, pX, pY);
		}
		else
		{
			if(uniteA==null) // si on a sélectionné aucune unité auparavant pour l'attaque
			{
				if(uniteD==null)
				{
					List<String> items= new ArrayList<String>;//on va afficher le menu en créant une liste d'items
					if(uniteProche(pX,pY)){items.add("Attaque");}
					if(unite.getType()==TypeUnite.INFANTERIE && Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.BATIMENT)
					{
						items.add("Assaut");
					}
					items.add("Deplacement");
				}
			}
			if(unite.getJoueur()!=uniteA.getJoueur() && uniteA.getAttaque().efficacite.containsKey(unite.TypeUnite)) // si l'unité ciblée n'appartient pas au même joueur que l'attaquant, et que l'attaquant a une attaque qui peut toucher la cible, alors on attaque
			{
				attaque(unite, uniteA);
			}
		}
	}
	
	public void deplacement(Unite unite, int pX, int pY)
	{
		
	}
	
	public boolean uniteProche(int pX, int pY)
	{
		if(Slatch.partie.getTerrain()[pX+1][pY].getUnite()!=null || Slatch.partie.getTerrain()[pX][pY+1].getUnite()!=null){return true;}
		if(pX>0)
		{
			if(Slatch.partie.getTerrain()[pX-1][pY].getUnite()!=null)
			{
				return true;
			}
		}
		if(pY>0)
		{
			if(Slatch.partie.getTerrain()[pX][pY-1].getUnite()!=null){return true;}
		}
	}
    
}
