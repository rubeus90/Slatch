import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * Possede presque toutes les methodes propres a la mecanique du jeu, il va travailler de paire avec Partie et IHM
 */
class Moteur
{  
    Unite uniteD; // unite en attente de déplacement
    Unite uniteA; // unite en attente d'attaque
    int[][] tabDep; // matrice de la portée de déplacement qu'il nous reste lorsqu'on se situe sur une case, utilisée par checkPorteeDeplacement
    boolean[][] tabAtt;
    HashMap<String, List<String>> chemins; // contient la liste des chemins pour arriver à la case définie par le premier String
    
    public Moteur()
    {
        tabDep = new int[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        tabAtt= new boolean[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        uniteD = null;
        uniteA = null;
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
    
    public void modeAttaque(int pX, int pY)
    {
        uniteA = Slatch.partie.getTerrain()[pX][pY].getUnite();
        affichePorteeAttaque(uniteA);
    }
    
    public void attaque(Unite pVictime)
    {
        double degatsAtt=0;
        degatsAtt=uniteA.getAttaque().getDegats()*uniteA.getAttaque().efficacite.get(pVictime.getType());
        pVictime.setPointDeVie(pVictime.getPointDeVie() - (int)degatsAtt);
        if(pVictime.getPointDeVie()<=0)
        {
            uniteA.addExperience(60);
            if(uniteA.getExperience()>=100 && uniteA.getLvl()<=3)
            {
                uniteA.upLvl();
            }
            estMort(pVictime);
        }    
        uniteA.attaque(true);
        uniteA=null;
    }
   
    public void estMort(Unite unite)
    {
        Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(null);
        Slatch.ihm.getPanel().dessineTerrain(unite.getCoordonneeX(),unite.getCoordonneeY());
    }

    /*
     * Appelee par l'IHM quand on clique sur une case, cette methode doit generer la liste des coordonnees accessibles par l'unite se trouvant sur la case selectionnee si elle ne s'est pas deja deplacee, et passer cette Liste a l'IHM.
     */
    public void caseSelectionnee(int pX, int pY)
    {
        
        if(Slatch.partie.getTerrain()[pX][pY].getSurbrillance() && uniteA==null && uniteD == null)
        {
            this.enleverSurbrillance();
            return;
        }
        
        this.enleverSurbrillance();
        
        
        if(uniteA==null && uniteD == null)
        {
            Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
        }
        Unite unite = Slatch.partie.getTerrain()[pX][pY].getUnite();
        if(unite==null) // si aucune unité n'est présente sur la case
        {
            if(uniteD!=null && tabDep[pX][pY]>-1) // si on a sélectioné aucune unité auparavant pour le déplacement
            {
                deplacement(uniteD, chemins.get(pX+","+pY), pX, pY);
            }
            annulerDeplacement();
        }
        else
        {
            if(uniteA==null) // si on a sélectionné aucune unité auparavant pour l'attaque
            {
                if(uniteD==null)
                {   if(Slatch.partie.getJoueurActuel()==unite.getJoueur())
                    {
                        List<String> items= new ArrayList<String>();//on va afficher le menu en créant une liste d'items
                        if(!unite.dejaDeplacee()){items.add("Deplace");}
                        if(uniteProche(unite, pX,pY) && !unite.dejaAttaque()){items.add("Attaque");}
                        if(unite.getType()==TypeUnite.INFANTERIE && Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.BATIMENT && Slatch.partie.getJoueurActuel()!=Slatch.partie.getTerrain()[pX][pY].getJoueur())
                        {
                                items.add("Capture");
                        }
                        Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
                        Slatch.ihm.getPanel().afficheMenu(items, pX, pY);
                    }
                }
                else
                {
                    annulerDeplacement();
                }
            }
            else
            {
                if(unite.getJoueur()!=uniteA.getJoueur() && uniteA.getAttaque().efficacite.containsKey(unite.getType()) && tabAtt[pX][pY]) // si l'unité ciblée n'appartient pas au même joueur que l'attaquant, et que l'attaquant a une attaque qui peut toucher la cible, alors on attaque
                {
                        attaque(unite);
                }
                else
                {
                    if(unite.getJoueur()==uniteA.getJoueur())
                    {
                        annulerAttaque();
                    }
                }
            }
        }
    }
    
    public void modeDeplacement(int pX, int pY)
    {
        uniteD = Slatch.partie.getTerrain()[pX][pY].getUnite();
        affichePorteeDep(uniteD);
    }
    
    public void annulerDeplacement()
    {
        uniteD=null;
    }
    
    public void annulerAttaque()
    {
        uniteA=null;
    }
    
    /*
    * permet de déplacer une unité en fonction du chemin passé en paramètre
    */
    public void deplacement(Unite unite, final List<String> chemin, int pX, int pY)
    {
            String[] t;
            for(int i=0; i<chemin.size(); i++)
            {
                t=chemin.get(i).split(",");
                changerCase(unite, Integer.parseInt(t[0]), Integer.parseInt(t[1]));
                try{
                    Thread.sleep(250/chemin.size()+50);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            unite.deplacee(true);
            changerCase(unite, pX, pY);
    }
    
    private void changerCase(Unite unite, int destX, int destY)
    {
        Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(null);
        Slatch.ihm.getPanel().dessineTerrain(unite.getCoordonneeX(),unite.getCoordonneeY());
        unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
        Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
        Slatch.ihm.getPanel().dessineTerrain(destX,destY);
    }
    
    /*
     * vérifie si une unité se trouve à côté de la case passée en paramètre
     */
    public boolean uniteProche(Unite unite, int pX, int pY)
    {
        if(pX+1<Slatch.partie.getLargeur())
        {
            if(Slatch.partie.getTerrain()[pX+1][pY].getUnite()!=null)
            {
                if(Slatch.partie.getTerrain()[pX+1][pY].getUnite().getJoueur()!=unite.getJoueur())
                {
                    return true;
                }
            }
        }
        if(pY+1<Slatch.partie.getHauteur())
        {                
            if(Slatch.partie.getTerrain()[pX][pY+1].getUnite()!=null)
            {
                if(Slatch.partie.getTerrain()[pX][pY+1].getUnite().getJoueur()!=unite.getJoueur())
                {
                    return true;
                }
            }
        }
        
        if(pX>0)
        {
            if(Slatch.partie.getTerrain()[pX-1][pY].getUnite()!=null)
            {
                if(Slatch.partie.getTerrain()[pX-1][pY].getUnite().getJoueur()!=unite.getJoueur())
                {
                    return true;
                }
            }
        }
        if(pY>0)
        {
            if(Slatch.partie.getTerrain()[pX][pY-1].getUnite()!=null)
            {
                if(Slatch.partie.getTerrain()[pX][pY-1].getUnite().getJoueur()!=unite.getJoueur())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void affichePorteeAttaque(Unite unite)
    {
        int x = unite.getCoordonneeX();
        int y = unite.getCoordonneeY();

        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                tabAtt[i][j] = false;
            }
        }
        
        for(int i=0; i<=unite.getAttaque().aTypePortee.getPorteeMax();i++)
        {
            for(int j=0; j<=unite.getAttaque().aTypePortee.getPorteeMax();j++)
            {
                if(x+i<Slatch.partie.getLargeur() && y+j<Slatch.partie.getHauteur())
                {
                    if(distance(x+i, y+j, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x+i, y+j, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && Slatch.partie.getTerrain()[x+i][y+j].getUnite()!=null)
                    {
                        Slatch.partie.getTerrain()[x+i][y+j].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(x+i,y+j);
                        tabAtt[x+i][y+j] = true;
                    }
                }
                if(x-i>=0 && y-j>=0)
                {
                    if(distance(x-i, y-j, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x-i, y-j, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && Slatch.partie.getTerrain()[x-i][y-j].getUnite()!=null)
                    {
                        Slatch.partie.getTerrain()[x-i][y-j].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(x-i,y-j);
                        tabAtt[x-i][y-j] = true;
                    }
                }
                if(x+i<Slatch.partie.getLargeur() && y-j>=0)
                {
                    if(distance(x+i, y-j, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x+i, y-j, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && Slatch.partie.getTerrain()[x+i][y-j].getUnite()!=null)
                    {
                        Slatch.partie.getTerrain()[x+i][y-j].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(x+i,y-j);
                        tabAtt[x+i][y-j] = true;
                    }
                }
                if(x-i>=0 && y+j<Slatch.partie.getHauteur())
                {
                    if(distance(x-i, y+j, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x-i, y+j, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && Slatch.partie.getTerrain()[x-i][y+j].getUnite()!=null)
                    {
                        Slatch.partie.getTerrain()[x-i][y+j].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(x-i,y+j);
                        tabAtt[x-i][y+j] = true;
                    }
                }
            }
        }
        
    }
    
    public int distance(int dX, int dY, int aX, int aY) // renvoie la distance entre (dX,dY) et (aX,aY)
    {
        return Math.abs(dX-aX) + Math.abs(dY-aY);
    }
    
    public void affichePorteeDep(Unite unite)
    {
        initialiseTabDep(unite.getCoordonneeX(), unite.getCoordonneeY(), unite.getPorteeDeplacement());
        chemins = new HashMap<String, List<String>>(); // instancie la hashmap des chemins
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                chemins.put(i+","+j, new ArrayList<String>()); // instancie chaque chemin
            }
        }
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
                if(Slatch.partie.getTerrain()[i][j].getUnite()!=null)  // quand on a déjà une unité sur la case, on ne peut pas y accéder
                {
                    tabDep[i][j]=300;
                }
                else{tabDep[i][j]=-1;} // au début, on suppose qu'on a 0 en portée de déplacement sur chacune des cases restantes
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
    
        /*
         * Vers la droite
         */
        if(tab[0]+1<Slatch.partie.getLargeur()) // on vérifie qu'on ne dépasse pas un bord
        {
            k= Slatch.partie.getTerrain()[tab[0]+1][tab[1]].getType().aCoutDeplacement.get(unite.getTypeDeplacement().getNom()); // k= coût de déplacement vers une case
            if(porteeDep-k>=0) // si on peut se déplacer sur la case, condition d'arrêt de la récursion
            {
                if(porteeDep- k >tabDep[tab[0]+1][tab[1]]) // évite des appels inutiles
                {
                    int[] t = new int[2];
                    t[0]=tab[0]+1; t[1]=tab[1]; 
                    tabDep[tab[0]+1][tab[1]] = porteeDep -k;// actualise la portée de deplacement restante sur la case correspondante de la matrice tabDep
                    
                    chemins.remove((tab[0]+1)+","+tab[1]); // on supprime l'ancien chemin
                    List<String> memL = new ArrayList<String>(); // on fait une liste intermédiaire
                    for(String s: chemins.get(tab[0]+","+tab[1]))
                    {
                        memL.add(s); // on copie le chemin précédent dedans
                    }
                    chemins.put((tab[0]+1)+","+tab[1],memL); // on définit ce chemin comme celui de la case suivante
                    chemins.get((tab[0]+1)+","+tab[1]).add(tab[0]+","+tab[1]); // on y rajoute l'étape actuelle
                    
                    if(!Slatch.partie.getTerrain()[tab[0]+1][tab[1]].getSurbrillance()) // si la case n'a pas déjà été mise en valeur
                    {
                        Slatch.partie.getTerrain()[tab[0]+1][tab[1]].setSurbrillance(true);// on met la case en question en surbrillance
                        Slatch.ihm.getPanel().dessineTerrain(tab[0]+1,tab[1]);
                    }
                    checkPorteeDeplacement(unite, porteeDep-k, t); // et on appelle à nouveau la procédure en changeant de place et en faisant décroître la portée de déplacement
                }
            }
        }
        /*
         * Vers le bas
         */
        if(tab[1]+1<Slatch.partie.getHauteur())// on vérifie qu'on ne dépasse pas un bord
        {
            k= Slatch.partie.getTerrain()[tab[0]][tab[1]+1].getType().aCoutDeplacement.get(unite.getTypeDeplacement().getNom());
            if(porteeDep-k>=0) // si on peut se déplacer sur la case
            {
                if(porteeDep- k >tabDep[tab[0]][tab[1]+1])
                {
                    int[] t = new int[2];
                    t[0]=tab[0]; t[1]=tab[1]+1;
                    tabDep[tab[0]][tab[1]+1] = porteeDep -k;
                    
                    chemins.remove(tab[0]+","+(tab[1]+1));
                    
                    List<String> memL = new ArrayList<String>();
                    for(String s: chemins.get(tab[0]+","+tab[1]))
                    {
                        memL.add(s);
                    }
                    chemins.put(tab[0]+","+(tab[1]+1),memL);
                    chemins.get(tab[0]+","+(tab[1]+1)).add(tab[0]+","+tab[1]);
                    
                    if(!Slatch.partie.getTerrain()[tab[0]][tab[1]+1].getSurbrillance())
                    {
                        Slatch.partie.getTerrain()[tab[0]][tab[1]+1].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(tab[0],tab[1]+1);
                    }
                    checkPorteeDeplacement(unite, porteeDep-k, t);
                }
            }
        }
        /*
         * Vers la gauche
         */
        if(tab[0]>0)// on vérifie qu'on ne dépasse pas un bord
        {
            k= Slatch.partie.getTerrain()[tab[0]-1][tab[1]].getType().aCoutDeplacement.get(unite.getTypeDeplacement().getNom());
            if(porteeDep-k>=0) // si on peut se déplacer sur la case
            {
                if(porteeDep- k >tabDep[tab[0]-1][tab[1]])
                {
                    int[] t = new int[2];
                    t[0]=tab[0]-1; t[1]=tab[1];
                    tabDep[tab[0]-1][tab[1]] = porteeDep -k;
                    
                    chemins.remove((tab[0]-1)+","+tab[1]);
                    List<String> memL = new ArrayList<String>();
                    for(String s: chemins.get(tab[0]+","+tab[1]))
                    {
                        memL.add(s);
                    }
                    chemins.put((tab[0]-1)+","+tab[1],memL);
                    chemins.get((tab[0]-1)+","+tab[1]).add(tab[0]+","+tab[1]);
                
                    if(!Slatch.partie.getTerrain()[tab[0]-1][tab[1]].getSurbrillance())
                    {
                        Slatch.partie.getTerrain()[tab[0]-1][tab[1]].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(tab[0]-1,tab[1]);
                    }
                    checkPorteeDeplacement(unite, porteeDep-k, t);
                }
            }
        }
        /*
         * Vers le haut
         */
        if(tab[1]>0)// on vérifie qu'on ne dépasse pas un bord
        {
            k= Slatch.partie.getTerrain()[tab[0]][tab[1]-1].getType().aCoutDeplacement.get(unite.getTypeDeplacement().getNom());
            if(porteeDep-k>=0) // si on peut se déplacer sur la case
            {
                if(porteeDep- k >tabDep[tab[0]][tab[1]-1])
                {
                    int[] t = new int[2];
                    t[0]=tab[0]; t[1]=tab[1]-1;
                    tabDep[tab[0]][tab[1]-1] = porteeDep -k;
                    
                    chemins.remove(tab[0]+","+(tab[1]-1));
                    List<String> memL = new ArrayList<String>();
                    for(String s: chemins.get(tab[0]+","+tab[1]))
                    {
                        memL.add(s);
                    }
                    chemins.put(tab[0]+","+(tab[1]-1), memL);
                    chemins.get(tab[0]+","+(tab[1]-1)).add(tab[0]+","+tab[1]);
                    
                    if(!Slatch.partie.getTerrain()[tab[0]][tab[1]-1].getSurbrillance())
                    {
                        Slatch.partie.getTerrain()[tab[0]][tab[1]-1].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(tab[0],tab[1]-1);
                    }
                    checkPorteeDeplacement(unite, porteeDep-k, t);
                }
            }          
        }
    }
    
    public void passeTour()
    {
        Slatch.partie.tourSuivant();
        List<Unite> l = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getListeUnite();
        
        
    }
}
