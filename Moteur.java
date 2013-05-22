import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
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
    Point[] voisins = {new Point(0,1), new Point(0,-1),new Point(1,0),new Point(-1,0)};
    Point[] signes = {new Point(1,1), new Point(1,-1),new Point(-1,-1),new Point(-1,1)};
    
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
      
    /**
     * Methode a deplacer dans Moteur Une fois moteur safe
     * Methode qui permet a un ingenieur de soigner une unite
     * @param pUnite
     */
    public void soin(Unite pUnite)
    { 
        int vPV=pUnite.getPointDeVie();
        int vPVMax = pUnite.getPVMax();
        
        if(vPV<vPVMax){ // On verifie que le nbr de PV est inferieur au nbr de PV max
            
           if(vPV+5>vPVMax){ // Si lorsqu'on soigne on depasse le nbr de PV max, alors Vie de l'unite = PVmax
               pUnite.setPointDeVie(vPVMax);
            }
           else{ //Sinon on ajoute 5
               pUnite.setPointDeVie(vPV+5);
           }
            
           //On "grise" l'unite
           uniteA.attaque(true);
           uniteA.deplacee(true);
           uniteA=null; 
        }
    }
    
    /**
     * Methode a deplacer dans Moteur Une fois moteur safe
     * Methode qui permet a un ingenieur de faire evoluer une methode
     * @param pUnite
     */
    public void evoluer(Unite pUnite){
        if(pUnite.getExperience()>Unite.pallierExperience){ //On verifie que l'XP de l'unite est superieur au pallier pour monter d'XP
            pUnite.upLvl();
            
            //Ingenieur grisee
            uniteA.attaque(true);
            uniteA.deplacee(true);
            uniteA=null;
            
            //Unite evoluer grisee
            pUnite.attaque(true);
            pUnite.deplacee(true);
            pUnite=null;
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
        //degatsAtt=(uniteA.getAttaque().getDegats()*uniteA.getAttaque().efficacite.get(pVictime.getType()))*(100-(TypeTerrain.bonusCouverture*Slatch.partie.getTerrain()[pVictime.getCoordonneeX()][pVictime.getCoordonneeY()].getType().getCouverture()))/100;
        degatsAtt = getDegats(uniteA, pVictime);
        //pVictime.setPointDeVie(pVictime.getPointDeVie() - (int)degatsAtt);
        if(faireDegats(pVictime, degatsAtt)) // si la victime meurt
        {
            uniteA.addExperience(Unite.EXPERIENCE_DONNEE_PAR_NIVEAU*pVictime.getLvl());
            estMort(pVictime);
        }    
        else if(distance(uniteA, pVictime)==1) //sinon + si attaque au CAC, on riposte
        {
            degatsAtt= 0.7*getDegats(pVictime, uniteA);
            if(faireDegats(uniteA, degatsAtt))
            {
                uniteA.addExperience(Unite.EXPERIENCE_DONNEE_PAR_NIVEAU*uniteA.getLvl());
                estMort(uniteA);
            }
        }
        uniteA.attaque(true);
        uniteA.deplacee(true);
        uniteA=null;
    }
    
    public boolean faireDegats(Unite cible, double degats) // retourne vrai si la cible meurt
    {
        cible.setPointDeVie(cible.getPointDeVie() - (int)degats);
        if(cible.getPointDeVie()<=0){return true;}else{cible.dessine(Slatch.ihm.getPanel().getGraphics()); return false;}
    }
    
    public double getDegats(Unite a, Unite v) // a= attaquant, v= cible
    {
        return ((a.getAttaque().getDegats()*a.getAttaque().efficacite.get(v.getType()))*(100-(TypeTerrain.bonusCouverture*Slatch.partie.getTerrain()[v.getCoordonneeX()][v.getCoordonneeY()].getType().getCouverture()))/100)*((double)a.getPointDeVie()/(double)a.getPVMax());
    }
   
    public void estMort(Unite unite)
    {
        Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(null);
        Slatch.ihm.getPanel().dessineTerrain(unite.getCoordonneeX(),unite.getCoordonneeY());
        Slatch.partie.getJoueur(unite.getJoueur()).getListeUnite().remove(unite);
        if(Slatch.partie.getJoueur(unite.getJoueur()).getListeUnite().isEmpty())
        {
            System.out.println("Le joueur "+unite.getJoueur()+" est vaincu");
        }
    }

    /**
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
            annulerAttaque();
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
                        if(cibleEnVue(unite) && !unite.dejaAttaque()){items.add("Attaque");}
                        if((unite.getType()==TypeUnite.COMMANDO /*|| unite.getType()==TypeUnite.DEMOLISSEUR*/) && Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.BATIMENT && Slatch.partie.getJoueurActuel()!=Slatch.partie.getTerrain()[pX][pY].getJoueur())
                        {
                                items.add("Capture");
                        }
                        Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
                        if(!items.isEmpty()){Slatch.ihm.getPanel().afficheMenu(items, pX, pY);}
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
                        annulerAttaque();
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
    
    /**
    * permet de déplacer une unité en fonction du chemin passé en paramètre
    * @param unite unite a deplacer
    * @param chemin liste de coordonnees formant le chemin menant vers la destination
    * @param pX abscisse de l'arrivee
    * @param pY ordonnee de l'arrivee
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
    
    /**
     * Deplace une unite vers sa destination
     * @param unite unite a deplacer
     * @param pX abscisse de l'arrivee
     * @param pY ordonnee de l'arrivee
     */
    private void changerCase(Unite unite, int destX, int destY)
    {
        Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(null);
        Slatch.ihm.getPanel().dessineTerrain(unite.getCoordonneeX(),unite.getCoordonneeY());
        unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
        Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
        Slatch.ihm.getPanel().dessineTerrain(destX,destY);
    }
    
    /**
     * Vérifie si une unité se trouve à côté de la case passée en paramètre
     */
    public boolean uniteProche(Unite unite, int pX, int pY)
    {
        for(Point p: voisins)
        {
            int decX = (int)p.getX();
            int decY = (int)p.getY();
            if(pX+decX<Slatch.partie.getLargeur() && pX+decX>=0 && pY+decY<Slatch.partie.getHauteur() && pY+decY>=0)
            {
                if(Slatch.partie.getTerrain()[pX+decX][pY+decY].getUnite()!=null)
                {
                    if(Slatch.partie.getTerrain()[pX+decX][pY+decY].getUnite().getJoueur()!=unite.getJoueur())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Verifie si une unite se trouve a portee de tir de l'unite passee en parametre
     * @param unite unite qui cherche une autre unite a frapper
     * @return true si une unite est a portee de tir, false sinon
     */
    private boolean cibleEnVue(Unite unite)
    {
        int x = unite.getCoordonneeX(), y = unite.getCoordonneeY();
        for(int i=0; i<=unite.getAttaque().aTypePortee.getPorteeMax();i++)
        {
            for(int j=0; j<=unite.getAttaque().aTypePortee.getPorteeMax();j++)
            {
                for(Point p: signes)
                {
                    int decX = (int)p.getX();
                    int decY = (int)p.getY();
                    if(ciblePresente(unite, i*decX,j*decY)){return true;}
                }
            }
        }
        return false;
    }
    
    /**
     * Affiche la portee d'attaque en mettant en surbrillance les cibles potentielles
     * @param unite unite qui cherche une autre unite a frapper
     */
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
                for(Point p: signes)
                {
                    int decX = (int)p.getX();
                    int decY = (int)p.getY();
                    if(ciblePresente(unite, i*decX,j*decY))
                    {
                        Slatch.partie.getTerrain()[x+i*decX][y+j*decY].setSurbrillance(true);
                        Slatch.ihm.getPanel().dessineTerrain(x+i*decX,y+j*decY);
                        tabAtt[x+i*decX][y+j*decY] = true;
                    }
                }
            }
        }
        
    }
    
    /**
     * vérifie si une cible est présente en (x+decX, y+decY)
     */
    private boolean ciblePresente(Unite unite, int decX, int decY)
    {
        int x = unite.getCoordonneeX();
        int y = unite.getCoordonneeY();
        
        if(x+decX<Slatch.partie.getLargeur() && y+decY<Slatch.partie.getHauteur() && x+decX>=0 && y+decY>=0)
        {
            if(distance(x+decX, y+decY, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x+decX, y+decY, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && Slatch.partie.getTerrain()[x+decX][y+decY].getUnite()!=null)
            {
                return (Slatch.partie.getTerrain()[x+decX][y+decY].getUnite().getJoueur()!=Slatch.partie.getJoueurActuel());
            }
        }
        return false;
    }
    
    /**
     * renvoie la distance entre (dX,dY) et (aX,aY)
     */
    public int distance(int dX, int dY, int aX, int aY)
    {
        return Math.abs(dX-aX) + Math.abs(dY-aY);
    }
    
    /**
     * renvoie la distance entre e1 et e2
     */
    public int distance(Entite e1, Entite e2)
    {
        return distance(e1.getCoordonneeX(), e1.getCoordonneeY(), e2.getCoordonneeX(), e2.getCoordonneeY());
    }
    
    /**
     * va appeler les methodes pour afficher la portee de deplacement d'une unite
     * @param unite unite qui a envie de bouger
     */
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
   
    /**
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
        
    /**
     * Procedure recursive qui va déterminer les cases accessibles par une unité en focntion de sa portée de déplacement et de sa position de départ
     */
    public void checkPorteeDeplacement(Unite unite, int porteeDep, int[] tab)
    {
        for(Point p: voisins)
        {
            checkDeplacementCase(unite, porteeDep, tab, (int)p.getX(), (int)p.getY());
        }
    }
    
    private void checkDeplacementCase(Unite unite, int porteeDep, int[] tab, int decX, int decY)
    {
        int k;
        int x = tab[0]+decX;
        int y = tab[1]+decY;
        if(x<Slatch.partie.getLargeur() && x>=0 && y<Slatch.partie.getHauteur() && y>=0) // on vérifie qu'on ne dépasse pas un bord
        {
            k= Slatch.partie.getTerrain()[x][y].getType().aCoutDeplacement.get(unite.getTypeDeplacement().getNom()); // k= coût de déplacement vers une case
            if(porteeDep-k>=0) // si on peut se déplacer sur la case, condition d'arrêt de la récursion
            {
                if(porteeDep- k >tabDep[x][y]) // évite des appels inutiles
                {
                    int[] t = new int[2];
                    t[0]=x; t[1]=y; 
                    tabDep[x][y] = porteeDep -k;// actualise la portée de deplacement restante sur la case correspondante de la matrice tabDep
                    
                    chemins.remove((x)+","+(y)); // on supprime l'ancien chemin
                    List<String> memL = new ArrayList<String>(); // on fait une liste intermédiaire
                    for(String s: chemins.get(tab[0]+","+tab[1]))
                    {
                        memL.add(s); // on copie le chemin précédent dedans
                    }
                    chemins.put((x)+","+(y),memL); // on définit ce chemin comme celui de la case suivante
                    chemins.get((x)+","+(y)).add(tab[0]+","+tab[1]); // on y rajoute l'étape actuelle
                    
                    if(!Slatch.partie.getTerrain()[x][y].getSurbrillance()) // si la case n'a pas déjà été mise en valeur
                    {
                        Slatch.partie.getTerrain()[x][y].setSurbrillance(true);// on met la case en question en surbrillance
                        Slatch.ihm.getPanel().dessineTerrain(x,y);
                    }
                    checkPorteeDeplacement(unite, porteeDep-k, t); // et on appelle à nouveau la procédure en changeant de place et en faisant décroître la portée de déplacement
                }
            }
        }
    }
    
    public void passeTour()
    {
        Slatch.partie.tourSuivant();
        List<Unite> l = Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getListeUnite();
        
        for(Unite u: l)
        {
            u.attaque(false);
            u.deplacee(false);
        }
    }
    
    public boolean estAuJoueurActuel(Unite unite)
    {
        return unite.getJoueur()==Slatch.partie.getJoueurActuel();
    }
    
    public boolean estAuJoueurActuel(int pX, int pY)
    {
        return estAuJoueurActuel(Slatch.partie.getTerrain()[pX][pY].getUnite());
    }
}
