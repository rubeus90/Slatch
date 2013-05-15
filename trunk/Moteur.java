import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * Possede presque toutes les methodes propres a la mecanique du jeu, il va travailler de paire avec Partie et IHM
 */
class Moteur
{  
    Unite uniteD = null; // unite en attente de déplacement
    Unite uniteA = null; // unite en attente d'attaque
    int[][] tabDep;
    HashMap<String, TypeDeplacement> map;
    
    public Moteur()
    {
        tabDep = new int[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        map = new HashMap<String, TypeDeplacement>();
        map.put("pied", TypeDeplacement.PIED);
        map.put("roues", TypeDeplacement.ROUES);
        map.put("chenilles", TypeDeplacement.CHENILLES);
        map.put("naval", TypeDeplacement.NAVAL);
    }
   
    public void enleverSurbrillance()
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(Slatch.partie.getTerrain()[i][j].getSurbrillance())
                {
                Slatch.partie.getTerrain()[i][j].setSurbrillance(false);
                Slatch.ihm.getPanel().dessineTerrain(i,j);
                }
            }
        }
    }
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
            this.enleverSurbrillance();
            Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
            Unite unite = Slatch.partie.getTerrain()[pX][pY].getUnite();
            if(unite==null) // si une unité est présente sur la case
            {
                    if(uniteD==null) // si on a sélectioné aucune unité auparavant pour le déplacement
                    {
                            return;
                    }
                    //deplacement(uniteD, chemin);
            }
            else
            {
                    if(uniteA==null) // si on a sélectionné aucune unité auparavant pour l'attaque
                    {
                            if(uniteD==null)
                            {
                                    List<String> items= new ArrayList<String>();//on va afficher le menu en créant une liste d'items
                                    if(uniteProche(pX,pY)){items.add("Attaque");}
                                    /*if(unite.getType()==TypeUnite.INFANTERIE && Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.BATIMENT)
                                    {
                                            items.add("Assaut");
                                    }*/
                                    items.add("Deplacement");
                                    Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
                                    affichePorteeDep(unite);
                                    Slatch.ihm.getPanel().afficheMenu(items, pX, pY);
                            }
                    }
                    else
                    {
                        if(unite.getJoueur()!=uniteA.getJoueur() && uniteA.getAttaque().efficacite.containsKey(unite.getType())) // si l'unité ciblée n'appartient pas au même joueur que l'attaquant, et que l'attaquant a une attaque qui peut toucher la cible, alors on attaque
                        {
                                attaque(unite, uniteA);
                        }
                    }
            }
    }
   
    /*public void deplacement(Unite unite, List<"String"> chemin)
    {
           
    }*/
    public boolean uniteProche(int pX, int pY) // vérifie si une unité se trouve à côté de la case passée en paramètre
    {
        if(pX<Slatch.partie.getLargeur())
        {
            if(Slatch.partie.getTerrain()[pX+1][pY].getUnite()!=null)
            {return true;}
        }
        if(pY<Slatch.partie.getLargeur())
        {                
            if(Slatch.partie.getTerrain()[pX][pY+1].getUnite()!=null){return true;}
        }
        
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
        return false;
    }
    
    public void affichePorteeDep(Unite unite)
    {
        initialiseTabDep(unite.getCoordonneeX(), unite.getCoordonneeY(), unite.getPorteeDeplacement());
        int[] tab = new int[2];
        tab[0]=unite.getCoordonneeX(); tab[1]=unite.getCoordonneeY();
        checkPorteeDeplacement(unite, unite.getPorteeDeplacement(), tab); 
    }
   
    /*
     * Initialise tabDep afin d'utiliser checkPorteeDeplacement dans des conditions optimales
     */
    private void initialiseTabDep(int x, int y, int portee)
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null){tabDep[i][j]=300;} // quand on a déjà une unité sur la case, on ne peut pas y accéder
                else{tabDep[i][j]=0;} // au début, on suppose qu'on a 0 en portée de déplacement sur chacune des cases restantes
            }
        }
       tabDep[x][y]=portee; // la position de départ de l'unité doit être initialisée avec le nombre de points de déplacement de base de l'unité
    }
        
    /*
     * Procedure recursive qui va déterminer les cases accessibles par une unité en focntion de sa portée de déplacement et de sa position de départ
     */
        public void checkPorteeDeplacement(Unite unite, int porteeDep, int[] tab)
        {
            int k;
                    if(tab[0]+1<Slatch.partie.getLargeur()) // on vérifie qu'on ne dépasse pas un bord
                    {
                        k= Slatch.partie.getTerrain()[tab[0]+1][tab[1]].getType().aCoutDeplacement.get(unite.getTypeDeplacement()); // k= coût de déplacement vers une case
                        if(porteeDep-k>=0) // si on peut se déplacer sur la case, condition d'arrêt de la récursion
                        {
                    if(porteeDep- k >tabDep[tab[0]+1][tab[1]]) // évite des appels inutiles
                            {
                                int[] t = new int[2];
                                t[0]=tab[0]+1; t[1]=tab[1]; 
                                tabDep[tab[0]+1][tab[1]] = porteeDep -k;
                                Slatch.partie.getTerrain()[tab[0]+1][tab[1]].setSurbrillance(true);// on met la case en question en surbrillance
                                Slatch.ihm.getPanel().dessineTerrain(tab[0]+1,tab[1]);
                                checkPorteeDeplacement(unite, porteeDep-k, t); // et on appelle à nouveau la procédure en changeant de place et en faisant décroître la portée de déplacement
                            }
                        }
                    }
                    if(tab[1]+1<Slatch.partie.getHauteur())// on vérifie qu'on ne dépasse pas un bord
                    {
                        k= Slatch.partie.getTerrain()[tab[0]][tab[1]+1].getType().aCoutDeplacement.get(unite.getTypeDeplacement());
                        if(porteeDep-k>=0) // si on peut se déplacer sur la case
                        {
                            if(porteeDep- k >tabDep[tab[0]][tab[1]+1])
                            {
                                int[] t = new int[2];
                                t[0]=tab[0]; t[1]=tab[1]+1;
                                tabDep[tab[0]][tab[1]+1] = porteeDep -k;
                                Slatch.partie.getTerrain()[tab[0]][tab[1]+1].setSurbrillance(true);
                                Slatch.ihm.getPanel().dessineTerrain(tab[0],tab[1]+1);
                                checkPorteeDeplacement(unite, porteeDep-k, t);
                            }
                        }
                    }
                    if(tab[0]>0)// on vérifie qu'on ne dépasse pas un bord
                    {
                        k= Slatch.partie.getTerrain()[tab[0]-1][tab[1]].getType().aCoutDeplacement.get(unite.getTypeDeplacement());
                        if(porteeDep-k>=0) // si on peut se déplacer sur la case
                        {
                            if(porteeDep- k >tabDep[tab[0]-1][tab[1]])
                            {
                                int[] t = new int[2];
                                t[0]=tab[0]-1; t[1]=tab[1];
                                tabDep[tab[0]-1][tab[1]] = porteeDep -k;
                                Slatch.partie.getTerrain()[tab[0]-1][tab[1]].setSurbrillance(true);
                                Slatch.ihm.getPanel().dessineTerrain(tab[0]-1,tab[1]);
                                checkPorteeDeplacement(unite, porteeDep-k, t);
                            }
                        }
                    }
                    if(tab[1]>0)// on vérifie qu'on ne dépasse pas un bord
                    {
                        k= Slatch.partie.getTerrain()[tab[0]][tab[1]-1].getType().aCoutDeplacement.get(unite.getTypeDeplacement());
                        if(porteeDep-k>=0) // si on peut se déplacer sur la case
                        {
                            if(porteeDep- k >tabDep[tab[0]][tab[1]-1])
                            {
                                int[] t = new int[2];
                                t[0]=tab[0]; t[1]=tab[1]-1;
                                tabDep[tab[0]][tab[1]-1] = porteeDep -k;
                                Slatch.partie.getTerrain()[tab[0]][tab[1]-1].setSurbrillance(true);
                                Slatch.ihm.getPanel().dessineTerrain(tab[0],tab[1]-1);
                                checkPorteeDeplacement(unite, porteeDep-k, t);
                            }
                        }
                    }               
                    
                
        }
}
