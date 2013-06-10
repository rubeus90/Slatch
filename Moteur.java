import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.awt.Point;
import java.util.PriorityQueue;

/**
 * Possede presque toutes les methodes propres a la mecanique du jeu, il va travailler de paire avec Partie et IHM
 * Test
 */
class Moteur
{  
    Unite uniteD; // unite en attente de déplacement
    Unite uniteA; // unite en attente d'attaque
    boolean[][] tabAtt;
    private boolean aModeEvoluer;
    
    int[][] tabDist;
    Point[][] pred;
    
    static Point[] voisins = {new Point(0,1), new Point(0,-1),new Point(1,0),new Point(-1,0)};
    static Quad[] signes = {new Quad(0,1,-1,1), new Quad(0,-1,1,-1),new Quad(1,-1,0,1),new Quad(-1,1,0,-1)};// Permettra de parcourir le rayon de portée d'une unité
    
    
    public Moteur()
    {
        tabDist = new int[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        pred = new Point[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        tabAtt= new boolean[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
        aModeEvoluer=false;
        uniteD = null;
        uniteA = null;
    }
    
    
    /******************************************************************************************************************************************************
     *                                                La toute puissance des Modes                                                                        *
     ******************************************************************************************************************************************************/
    
     /**
     * Passe en mode attaque, permet de memoriser l'unite qui attaque, et d'afficher sa portee
     */
    public void modeAttaque(final int pX,final int pY)
    {
        uniteA = getUnite(pX,pY);
        affichePorteeAttaque(uniteA, false);
    }
     
     /**
     * Permet de passer en mode evoluer
     */
    public void setModeEvoluer(){
        aModeEvoluer = true;
    }
    
    /**
     * Passe en mode soin, ressemble un peu au mode attaque
     */
    public void modeSoin(final int pX,final int pY)
    {
        uniteA = getUnite(pX,pY);
        affichePorteeAttaque(uniteA, true);
    }
    
    /**
     * Permet de passer en mode deplacement
     */
    public void modeDeplacement(final int pX,final int pY)
    {
        uniteD = getUnite(pX,pY);
        affichePorteeDep(uniteD);
    }
    
    /******************************************************************************************************************************************************
     *                                                La meilleur defense, c'est l'attaque                                                                *
     ******************************************************************************************************************************************************/
    
     /**
     * Annule l'attaque
     */
    public void annulerAttaque()
    {
        uniteA=null;
    }
    
     /**
     * permet a uniteA d'attaquer pVictime
     */
    public void attaque(final Unite pVictime)
    { 
        double degatsAtt=0;
        degatsAtt = getDegats(uniteA, pVictime);
        
        if(faireDegats(pVictime, degatsAtt)) // si la victime meurt
        {
            //Pour les statistiques si le nombre de degat est superieur a nombre de point de vie de l'unite
            uniteA.addExperience(degatsAtt+pVictime.getPV());
            getJoueur(uniteA).addExpTotal(degatsAtt+pVictime.getPV());
            getJoueur(uniteA).addExpTotal(degatsAtt+pVictime.getPV());
            getJoueur(pVictime).addExpTotal(degatsAtt+pVictime.getPV());
            getJoueur(uniteA).addNbrUniteTue();
            
            estMort(pVictime,uniteA);
            
        }
        else if(getJoueur(uniteA).getFaction() == Faction.ROBOTS && uniteA.getType() == TypeUnite.KAMIKAZE) //Si l'unite Attaquant est un Kamikaze
        {
            estMort(uniteA,pVictime);
        }
        else if(distance(uniteA, pVictime)==1 && pVictime.getAttaque().aTypePortee.getPorteeMin()==1) //sinon + si attaque au CAC, on riposte
        {
            //Pour les statistiques pour une attaque normal
            uniteA.addExperience(degatsAtt);
            getJoueur(uniteA).addExpTotal(degatsAtt);
            getJoueur(uniteA).addDegatTotal(degatsAtt);
            getJoueur(pVictime).addDegatSubit(degatsAtt);
            
            degatsAtt= 0.7*getDegats(pVictime, uniteA);
            
            //Add XP a l'unite qui contre-attaque
            pVictime.addExperience(degatsAtt);
            getJoueur(pVictime).addExpTotal(degatsAtt);
            
            //Stat
            getJoueur(uniteA).addDegatSubit(degatsAtt);
            getJoueur(pVictime).addDegatTotal(degatsAtt);
            
            if(faireDegats(uniteA, degatsAtt))
            {
                //Si l'unite qui attaque meurt pendant l'attaque,
                getJoueur(pVictime).addNbrUniteTue();
                
                estMort(uniteA,pVictime);
            }
        }
        uniteA.attaque(true);
        uniteA.deplacee(true);
        uniteA=null;
    }
    
    /**
     * Endommage la cible de degats points de degats, et retourne true si elle meurt
     */
    private boolean faireDegats(final Unite cible,final double degats) // retourne vrai si la cible meurt
    {
        cible.setPV(cible.getPV() - (int)degats);
        if(cible.getPV()<=0){
            return true;
        }
        else{
            repaint(); 
            return false;
        }
    }
    
    /**
     * Contient la formule de calcul des degats
     */
    private double getDegats(final Unite a,final Unite v) // a= attaquant, v= cible
    {
        int degatA = a.getDegat();
        double efficaciteA = a.getAttaque().efficacite.get(v.getType());
        double bonusTerrain = (100-(TypeTerrain.bonusCouverture*Slatch.partie.getTerrain()[v.getX()][v.getY()].getType().getCouverture()))/100.0;
        double maluspvA = (double)a.getPV()/(double)a.getPVMax(); 
        
        double degat = degatA*efficaciteA*bonusTerrain*maluspvA;
        
        /*((a.getAttaque().getDegats()*a.getAttaque().efficacite.get(v.getType()))
        *(100-(TypeTerrain.bonusCouverture*Slatch.partie.getTerrain()[v.getX()][v.getY()].getType().getCouverture()))/100)
         *((double)a.getPV()/(double)a.getPVMax())*/   
        return degat;
    }
    
    /**
     * Vérifie si une unité se trouve à côté de la case passée en paramètre
     */
    public boolean uniteProche(final Unite unite,final int pX,final int pY)
    {
        for(Point p: voisins)
        {
            int decX = (int)p.getX();
            int decY = (int)p.getY();
            if(dansLesBords(pX+decX,pY+decY))
            {
                if(getUnite(pX+decX,pY+decY)!=null)
                {
                    if(getNumJoueur(pX+decX,pY+decY)!=unite.getJoueur());
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
    public boolean cibleEnVue(final Unite unite,final boolean soin)
    {
        for(int i=1; i<=unite.getAttaque().aTypePortee.getPorteeMax(); i++)
        {
            for(int j=1; j<=i; j++)
            {
                for(Quad q: signes)
                {      
                    if(ciblePresente(unite, i*q.a+j*q.b,i*q.c+j*q.d, soin)){return true;}
                }
            }
        }
        return false;
    }
    
    /**
     * Affiche la portee d'attaque en mettant en surbrillance les cibles potentielles
     * @param unite unite qui cherche une autre unite a frapper
     */
    public void affichePorteeAttaque(final Unite unite,final boolean soin)
    {
        int x = unite.getX();
        int y = unite.getY();

        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                tabAtt[i][j] = false;
            }
        }
        
        for(int i=1; i<=unite.getAttaque().aTypePortee.getPorteeMax();i++)
        {
            for(int j=1; j<=i;j++)
            {
                for(Quad q: signes)
                {
                    int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                    if(dansLesBords(x+a+b, y+c+d))
                    {
                        if(ciblePresente(unite,a+b,c+d, soin))
                        {
                            Slatch.partie.getTerrain()[x+a+b][y+c+d].setSurbrillance(true);
                            repaint();
                            tabAtt[x+a+b][y+c+d] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Retourne true si pC est a portee d'attaque de pA
     */
    public boolean estAPortee(Unite pA, Unite pC) // nous dit si pC est à portee de tir de PA
    {
        /*
        int d=distance(pA, pC);
        return d<=pA.getAttaque().aTypePortee.getPorteeMax() && d>=pA.getAttaque().aTypePortee.getPorteeMin();*/
        return estAPortee(pA, pC.getX(), pC.getY());
    }
    
    /**
     * Retourne true si la case de coordonnees x, y est a portee d'attaque de pA
     */
    public boolean estAPortee(Unite pA, int x, int y)
    {
        int d=distance(pA.getX(), pA.getY(), x, y);
        return d<=pA.getAttaque().aTypePortee.getPorteeMax() && d>=pA.getAttaque().aTypePortee.getPorteeMin();
    }
    
    /**
     * Vérifie si une cible est présente en (x+decX, y+decY)
     */
    private boolean ciblePresente(final Unite unite,final int decX,final int decY,final boolean soin)
    {
        int x = unite.getX();
        int y = unite.getY();
        
        if(dansLesBords(x+decX,y+decY))
        {
            if(distance(x+decX, y+decY, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && distance(x+decX, y+decY, x,y)<=unite.getAttaque().aTypePortee.getPorteeMax() && distance(x+decX, y+decY, x,y)>=unite.getAttaque().aTypePortee.getPorteeMin() && getUnite(x+decX,y+decY)!=null)
            {
                boolean pasAuJoueurActuel = getEquipe(getNumJoueur(x+decX,y+decY)) != getJoueurActuel().getEquipe().getNumEquipe();
                return (pasAuJoueurActuel^(soin && (getUnite(x+decX,y+decY).aBesoinDeSoins()||pasAuJoueurActuel)))&&!(unite.dejaDeplacee() && distance(x+decX, y+decY, x,y)>=2);
            }
        }
        return false;
    }
    
    /******************************************************************************************************************************************************
     *                                                  Liste des methodes pour Sparadrap                                                                 *
     ******************************************************************************************************************************************************/
     
    /**
     * Vérifie si l'unite passee en parametre a une unite alliee adjacente qui a besoin de soins
     */
    private boolean cibleSoignable(final Unite pUnite)
    {
        int vX;
        int vY;
        for(Point p: voisins)
        {
            vX=(int)p.getX()+pUnite.getX();
            vY=(int)p.getY()+pUnite.getY();
            if(dansLesBords(vX,vY))
            {
                Unite u = getUnite(vX,vY);
                if(u!= null)
                {
                    if(u.aBesoinDeSoins() && getEquipe(pUnite.getJoueur())==getEquipe(u.getJoueur()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Methode a deplacer dans Moteur Une fois moteur safe
     * Methode qui permet a un ingenieur de soigner une unite
     * @param pUnite
     */
    public void soin(final Unite pUnite)
    { 
        int vLocal = pUnite.soigner(uniteA.getDegat());
        if(vLocal!=0){  
           uniteA.addExperience(vLocal);
           getJoueur(uniteA).addSoinTotal(vLocal);
           uniteA.attaque(true);
           uniteA.deplacee(true);
           uniteA=null; 
        }
    }
     
    /******************************************************************************************************************************************************
     *                                                      Suivons le mouvement!                                                                         *
     ******************************************************************************************************************************************************/
     
     /**
    * permet de déplacer une unité en fonction du chemin passé en paramètre
    * @param unite unite a deplacer
    * @param pX abscisse de l'arrivee
    * @param pY ordonnee de l'arrivee
    */
    public void deplacement(Unite unite,final int pX,final int pY)
    {
        boolean fini = false;
        boolean geez = false;
        boolean init=true;
        int x = pX, y =pY;
        int X=unite.getCoordonneeX(), Y=unite.getCoordonneeY();
        Stack<Point> stack = new Stack<Point>();
        Slatch.ihm.getPanel().paintImmediately(0,0,Slatch.ihm.getPanel().getWidth(),Slatch.ihm.getPanel().getHeight());
        if(pred[x][y]!=null && unite.getType().getDeplacement()>=tabDist[x][y]){stack.push(new Point(pX,pY));unite.deplacee(true); Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setPV(Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].getType().getPVMax());X=pX;Y=pY;init=false;if(unite.getAttaque().aTypePortee.getPorteeMin()>1){unite.attaque(true);}}
        while(!fini)
        {
            Point p = pred[x][y];
            if(p!=null)
            {
                x=(int)p.getX();
                y=(int)p.getY();
                if(unite.getCoordonneeX()==x && unite.getCoordonneeY()==y)
                {
                    fini = true;
                }
                else
                {
                    if(unite.getType().getDeplacement()>=tabDist[x][y])
                    {//System.out.println("X= "+X+" et Y= "+Y);
                        if(!geez)
                        {
                            if(getUnite(x,y)==null){
                                stack.push(p);
                                    
                            unite.deplacee(true); 
                            geez = true;Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setPV(Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].getType().getPVMax());
                                 
                                    if(init)
                                    {
                                     X=(int)p.getX();
                                     Y=(int)p.getY();
                                     init=false;
                                    }
                        }
                        }
                        else
                        {
                            stack.push(p);
                            if(init)
                            {
                             X=(int)p.getX();
                             Y=(int)p.getY();
                             init=false;
                            }
                        }
                    }
                }
            }
            else{break;}
        }
            int pPosHautGaucheX = X*Slatch.ihm.getPanel().getaLargeurCarreau();
            int pPosBasDroiteY = (Y+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
            int pPosHautGaucheXdepart = (int)unite.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
            int pPosBasDroiteYdepart = (int)(unite.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
            unite.setDecaleUniteX(-(int)(pPosHautGaucheX - pPosHautGaucheXdepart ));
            unite.setDecaleUniteY(-(int)(pPosBasDroiteY - pPosBasDroiteYdepart ));
            
        Point vDepart = new Point(unite.getCoordonneeX(),unite.getCoordonneeY());
        
        Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(null);
        Slatch.partie.getTerrain()[X][Y].setUnite(unite);
        unite.setCoordonneeX(X);
        unite.setCoordonneeY(Y);
        
        Unite vUnite = unite;
        Stack<Point> vChemin = stack;
        double vVitesse = 0;
        AnimationDeplacement animation = new AnimationDeplacement(vChemin,vDepart,vUnite,vVitesse);
            Slatch.ihm.getAnimation().addDeplacement(animation);
        
           if(!getJoueurActuel().estUneIA())
        {
            Slatch.ihm.getAnimation().start();
        }
        
        
    }
    
     /**
     * Annule le deplacement
     */
    public void annulerDeplacement()
    {
        uniteD=null;
    }
    
    /**
     * Remplit le tableau qui contient les portees de deplacement pour l'unite en cours
     */
    public void remplitPorteeDep(final Unite unite,final boolean bool)
    {
        this.initialiseTabDist(unite);
        this.algoDeplacement(unite, bool);
        
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(getUnite(i,j)!=null)
                {
                    if(getNumJoueur(i,j) == unite.getJoueur())
                    {
                        tabDist[i][j] = -2;
                    }
                }
            }
        }
    }
    
    /**
     * Va appeler les methodes pour afficher la portee de deplacement d'une unite
     * @param unite unite qui a envie de bouger
     */
    private void affichePorteeDep(final Unite unite)
    {
        this.remplitPorteeDep(unite, true);
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(tabDist[i][j]>0)
                {
                    Slatch.partie.getTerrain()[i][j].setSurbrillance(true);
                    repaint();
                }
            }
        }
    }
    
    /**
     * Initialise tabDist afin d'utiliser algoDeplacement dans des conditions optimales
     */
    private void initialiseTabDist(final Unite unite)
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(getUnite(i,j)!=null)  // quand on a déjà une unité sur la case, on ne peut pas y accéder
                {
                    if(getNumJoueur(i,j)!= unite.getJoueur())
                    {
                        tabDist[i][j] = -2;
                    }
                    else
                    {
                        tabDist[i][j] = -1;
                    }
                }
                else{tabDist[i][j] = -1;} // au début, on suppose qu'on a une distance infinie représentée par -1 sur chacune des cases restantes
                pred[i][j]=null;
            }
        }
        tabDist[unite.getX()][unite.getY()]=-2;
        pred = new Point[Slatch.partie.getLargeur()][Slatch.partie.getHauteur()];
    }
    
    /**
     * L'algorithme qui permet de trouver les cases atteignables, en calculant par la meme le plus court chemin
     */
    public void algoDeplacement(final Unite unite,final boolean porteeComptee)
    {
        PriorityQueue<Triplet> pq = new PriorityQueue<Triplet>();
        pq.add(new Triplet(0,unite.getX(),unite.getY()));
        while(!pq.isEmpty())
        {
            Triplet t = pq.poll();
            for(Point p: voisins)
            {
                int x = t.x+(int)p.getX();
                int y = t.y+(int)p.getY();
                if(dansLesBords(x,y))
                {
                    int d = t.d+Slatch.partie.getTerrain()[x][y].getCout(unite);
                    
                    if(d<=unite.getType().getDeplacement() || !porteeComptee)
                    {
                        if(d<tabDist[x][y] || tabDist[x][y]==-1)
                        {
                            pred[x][y] = new Point(t.x, t.y);
                            
                            if(enBordureDeDeplacement(unite, t.x, t.y, d) && getUnite(t.x,t.y)!=null) // et unité présente aussi
                            {
                                pred[x][y]= null;
                            }
                            else
                            {
                                tabDist[x][y] = d;
                                pq.offer(new Triplet(d, x, y));
                            }
                        }
                    }
                }
            }
        }
    }
    /******************************************************************************************************************************************************
     *                                                    UML, digivolve toi en...UML!                                                                    *
     ******************************************************************************************************************************************************/
     
    /**
     * Methode qui permet a un ingenieur de faire evoluer une methode
     * @param pUnite
     */
    public void evoluer(final int pX,final int pY)
    {
        uniteA = getUnite(pX,pY);
        uniteA.upLvl();
        uniteA.attaque(true);
        uniteA.deplacee(true);
        uniteA=null;
    }
    
    /******************************************************************************************************************************************************
     *                                                          Capturez les tous!                                                                        *
     ******************************************************************************************************************************************************/
     
     /**
     * Permet a uniteA de capturer le batiment sur lequel elle se trouve
     */
    public void capture(final int pX,final int pY)
    {
        Terrain vBatiment= Slatch.partie.getTerrain()[pX][pY];
        if((uniteA=getUnite(pX,pY))==null){return;};
       
        switch(uniteA.getType())
        {
            case INGENIEUR:
            case COMMANDO: vBatiment.setPV(vBatiment.getPV()-(int)(10.0*modificateurVie(uniteA))); break;
            case DEMOLISSEUR: vBatiment.setPV(vBatiment.getPV()-(int)(15.0*modificateurVie(uniteA)));
        }
        
        if(vBatiment.getPV()<=0)
        {
           if(vBatiment.getType()==TypeTerrain.USINE)
           {
                    getJoueur(vBatiment).getListeUsine().remove(vBatiment);
                    getJoueur(uniteA).getListeUsine().add(vBatiment);
           } 
           if(vBatiment.getType()==TypeTerrain.BATIMENT)
           {
                    getJoueur(vBatiment).getListeBatiment().remove(vBatiment);
                    getJoueur(uniteA).getListeBatiment().add(vBatiment);
           }
           if(vBatiment.getType()==TypeTerrain.QG)
           {
               getJoueur(vBatiment).mourrir();
               Slatch.partie.gagner(getJoueur(vBatiment.getX(),vBatiment.getY())); // On donne a gagner le joueur qui vient de capturer et pas celui qui vient de perdre le QG
           }
           getJoueur(vBatiment).addNbreBatiment(-1);
           vBatiment.setJoueur(uniteA.getJoueur());
           vBatiment.setPV(vBatiment.getType().getPVMax());
           getJoueur(uniteA).addNbreBatiment(1);
           getJoueur(uniteA).addCaptureTotal();
           repaint();
        }
        uniteA.attaque(true);
        uniteA.deplacee(true);
        uniteA=null;
    }
    
    
    /******************************************************************************************************************************************************
     *                                                         Rats Courcis                                                                               *
     ******************************************************************************************************************************************************/
    /**
     * enleve la surbrillance pour tous les éléments de la matrice
     */
    public void enleverSurbrillance()
    {
        for(int i=0; i<Slatch.partie.getLargeur(); i++)
        {
            for(int j=0; j<Slatch.partie.getHauteur(); j++)
            {
                if(Slatch.partie.getTerrain()[i][j].getSurbrillance())
                {
                    Slatch.partie.getTerrain()[i][j].setSurbrillance(false);
                    repaint();
                }
            }
        }
    } 
    
    /**
     * Permet d'obtenir le pourcentage de la vie d'unite divise par 100
     */
    private double modificateurVie(final Unite unite)
    {
        return (double)(pourcentageVie(unite))/100.0;
    }
    
    /**
     * Permet d'obtenir le pourcentage de vie d'unite
     */
    private int pourcentageVie(final Unite unite)
    {
        return (int)((double)(unite.getPV())/(double)(unite.getPVMax())*100); 
    }
    
    /**
     * Renvoie la distance entre (dX,dY) et (aX,aY)
     */
    public int distance(final int dX,final int dY,final int aX,final int aY)
    {
        return Math.abs(dX-aX) + Math.abs(dY-aY);
    }
    
    /**
     * Renvoie la distance entre e1 et e2
     */
    public int distance(final Entite e1,final Entite e2)
    {
        return distance(e1.getX(), e1.getY(), e2.getX(), e2.getY());
    }
    
    /**
     * Verifie si la case passee en parametre se situe en bordure de deplacement
     */
    private boolean enBordureDeDeplacement(Unite unite, int pX, int pY, int d)
    {
        Point p = pred[pX][pY];
        if(p!=null)
        {
            int x = (int)p.getX();
            int y = (int)p.getY();
            
            return (d<=unite.getType().getDeplacement() && tabDist[pX][pY]>unite.getType().getDeplacement());
        }
        return false;
    }
    
    /******************************************************************************************************************************************************
     *                                                         Fonctionnement                                                                             *
     ******************************************************************************************************************************************************/
    
    /**
     * Quand une unite meurt, on la "supprime" du jeu
     */
    private void estMort(final Unite unite, final Unite pUniteVictorieux)
    {
        Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setPV(Slatch.partie.getTerrain()[unite.getX()][unite.getY()].getType().getPVMax());
        Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setUnite(null);
        getJoueur(unite).addNbrUniteMort();
        repaint();
        if(!getJoueur(unite).estUneIA() || unite.getJoueur()!=Slatch.partie.getJoueurActuel())
        {
            getJoueur(unite).getListeUnite().remove(unite);
        }
        
        if(getJoueur(unite).getListeUnite().isEmpty())
        {
            getJoueur(unite).mourrir();
            Slatch.partie.gagner(getJoueur(pUniteVictorieux));
        }
    }
    
    /**
     * Appelee par l'IHM quand on clique sur une case, cette methode doit generer la liste des coordonnees accessibles par l'unite se trouvant sur la case selectionnee si elle ne s'est pas deja deplacee, et passer cette Liste a l'IHM.
     */
    public void caseSelectionnee(final int pX,final int pY)
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
        Unite unite = getUnite(pX,pY);
        if(unite==null) // si aucune unité n'est présente sur la case
        {
            annulerAttaque();
            if(uniteD!=null) // si on a sélectioné aucune unité auparavant pour le déplacement
            {
                deplacement(uniteD, pX, pY);
                Slatch.partie.getTerrain()[pX][pY].setSurbrillance(true);
            }
            else if(Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.USINE && Slatch.partie.getTerrain()[pX][pY].getJoueur()==Slatch.partie.getJoueurActuel()) // Selection d'une USINE
            {
                List<TypeUnite> l = new ArrayList<TypeUnite>();
                for(TypeUnite vType: getJoueurActuel().getFaction().getListe())
                {
                    if(vType.nomType.equals("Terrestre")){l.add(vType);}
                }
                Slatch.ihm.getPanel().shop(l,getJoueurActuel().getArgent(), pX, pY);
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
                        if(!unite.dejaDeplacee()){
                            items.add("Deplace");
                        }
                        
                        if(cibleEnVue(unite, false) && !unite.dejaAttaque())
                        {
                            if(unite.getType()!=TypeUnite.INGENIEUR){items.add("Attaque");}
                        }
                        
                        if(unite.isEvolvable() && !unite.dejaAttaque())
                            {
                                items.add("Evolue");
                            }
                        
                        if(unite.getType()==TypeUnite.INGENIEUR)
                        {
                            if(cibleSoignable(unite) && !unite.dejaAttaque())
                            {
                                items.add("Soin");
                            }
                        }
                        
                        if(!unite.dejaAttaque()&&(unite.getType()==TypeUnite.COMMANDO || unite.getType()==TypeUnite.DEMOLISSEUR || unite.getType()==TypeUnite.INGENIEUR) && (Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.QG || Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.BATIMENT || Slatch.partie.getTerrain()[pX][pY].getType()==TypeTerrain.USINE) && getJoueurActuel().getEquipe()!=getJoueurTerrain(pX,pY).getEquipe())
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
                if(getEquipe(unite)!=getEquipe(uniteA) && uniteA.getAttaque().efficacite.containsKey(unite.getType()) && tabAtt[pX][pY]) // si l'unité ciblée n'appartient pas au même joueur que l'attaquant, et que l'attaquant a une attaque qui peut toucher la cible, alors on attaque
                {
                    attaque(unite);
                }
                else if(unite.getJoueur()==uniteA.getJoueur() && tabAtt[pX][pY] && !aModeEvoluer && uniteA.getType()==TypeUnite.INGENIEUR)
                {
                    soin(unite);
                }
//                 else if(unite.getJoueur()==uniteA.getJoueur() && tabAtt[pX][pY] && aModeEvoluer &&uniteA.getType()==TypeUnite.INGENIEUR)
//                 {
//                     evoluer(unite);
//                 }
                else
                {
                    annulerAttaque();
                }
                aModeEvoluer=false;
            }
        }
    }
    
    
    
    
    /**
     * Deplace une unite vers sa destination
     * @param unite unite a deplacer
     * @param pX abscisse de l'arrivee
     * @param pY ordonnee de l'arrivee
     */
    public Unite changerCase(Unite unite,final int destX,final int destY,final Unite mem)
    {
        int vPasDepl = 8;
        int vThread = 5;
        int pPosHautGaucheX = unite.getX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheY = unite.getY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteX = (unite.getX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (unite.getY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        
        int pPosHautGaucheXdest = destX*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYdest = destY*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXdest = (destX+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYdest = (destY+1)*Slatch.ihm.getPanel().getaHauteurCarreau();

        if(unite.getX() != destX ) {
            if(unite.getX() < destX) {
                //BUG
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);

                for(int i=0; i<Slatch.ihm.getPanel().getaLargeurCarreau() ; i=i+vPasDepl) {
                    getUnite(destX,destY).setDecaleUniteX(-Slatch.ihm.getPanel().getaLargeurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                /*
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    getUnite(destX,destY).setDecaleUniteX(-Slatch.ihm.getPanel().getaLargeurCarreau()+animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                */
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
            else  {
                for(int i=0; i<Slatch.ihm.getPanel().getaLargeurCarreau() ; i=i+vPasDepl) {
                    unite.setDecaleUniteX(-i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                
                /*
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    unite.setDecaleUniteX(-animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                */
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
        
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                getJoueur(unite).addDeplacementTotal(1);
                return ret;
            }
        }
        if(unite.getY() != destY ) {
            if(unite.getY() < destY) {
                //BUG
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                for(int i=0; i<Slatch.ihm.getPanel().getaHauteurCarreau() ; i=i+vPasDepl) {
                    getUnite(destX,destY).setDecaleUniteY(-Slatch.ihm.getPanel().getaHauteurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                /*
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    getUnite(destX,destY).setDecaleUniteY(-Slatch.ihm.getPanel().getaHauteurCarreau()+animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                */
                
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
            else  {
                
                
                for(int i=0; i<Slatch.ihm.getPanel().getaHauteurCarreau() ; i=i+vPasDepl) {
                    unite.setDecaleUniteY(-i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                
                /*
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    unite.setDecaleUniteY(-animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }*/
                
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getX()][unite.getY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                getJoueur(unite).addDeplacementTotal(1);
                return ret;
            }
        }
        return null;
    }
    

     /**
     * Va appeler les methodes pour afficher la portee de deplacement d'une unite
     * @param unite unite qui a envie de bouger
     */
    private void affichePorteeBrouillard(final Unite unite)
    {
        int x = unite.getX();
        int y = unite.getY();
        
        Slatch.partie.getTerrain()[x][y].setBrouillard(false);
        
        Terrain plaine = new Terrain(x, y, 0, TypeTerrain.PLAINE);
        int porteeMax = unite.getDeplacement() / plaine.getCout(unite);
        
        for(int i=1; i<=porteeMax+2;i++)
        {
            for(int j=1; j<=i;j++)
            {
                for(Quad q: signes)
                {
                    int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                    if(dansLesBords(x+a+b, y+c+d))
                    {
                        Slatch.partie.getTerrain()[x+a+b][y+c+d].setBrouillard(false);
                        repaint();
                    }
                }
            }
        } 
    }
    
    public void Brouillard()
    {
        if(!getJoueurActuel().estUneIA() || getJoueurActuel().getEquipe().haveUnJoueurHumain()){           
        
            //On remplit la map de Brouillard
            for(int i=0; i<Slatch.partie.getLargeur(); i++)
            {
                for(int j=0; j<Slatch.partie.getHauteur(); j++)
                {
                    Slatch.partie.getTerrain()[i][j].setBrouillard(true);
                }
            }  
            
            //Pour tous les Joueurs de l'équipe, on enleve le brouillard sur les batiments + la visions des Unites
            for(Joueur vJoueur : getJoueurActuel().getEquipe().getListeJoueur()){
                if(vJoueur.getNumJoueur()!=0){
                   for(Unite vUnite : vJoueur.getListeUnite()){
                        affichePorteeBrouillard(vUnite);
                   }             
                   for(Terrain terrain : vJoueur.getListeBatiment()){
                       Slatch.partie.getTerrain()[terrain.getX()][terrain.getY()].setBrouillard(false);
                   }
                   for(Terrain terrain : vJoueur.getListeUsine()){
                       Slatch.partie.getTerrain()[terrain.getX()][terrain.getY()].setBrouillard(false);
                   }
                }
            }
        }
        else if(getJoueurActuel().estUneIA() && !Slatch.partie.getuneSeulEquipedeJoueur()){
            //On remplit la map de Brouillard
            for(int i=0; i<Slatch.partie.getLargeur(); i++)
            {
                for(int j=0; j<Slatch.partie.getHauteur(); j++)
                {
                    Slatch.partie.getTerrain()[i][j].setBrouillard(true);
                }
            }  
        }
    }
      
    
    
    /**
     * Permet de passer le tour du joueur actuel et de donner la main au suivant
     */
    public void passeTour()
    {
        if(!Slatch.partie.partieFinie)
        {
            Slatch.partie.tourSuivant();
            List<Unite> l = getJoueurActuel().getListeUnite();
            
            for(Unite u: l)
            {
                u.attaque(false);
                u.deplacee(false);
                if(Slatch.partie.getTerrain()[u.getX()][u.getY()].estUnBatimentAuJoueur(u.getJoueur()))
                {
                    u.soigner(8);
                }
            }
            
            if(getJoueurActuel().estUneIA() && Slatch.partie.getJoueurActuel()!=2)
            {
                StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
            }
            else
            {
                if(getJoueurActuel().estUneIA()){AIMaster.joueTour(Slatch.partie.getJoueurActuel());}
            }
            
        }
    }
    
    /**
     * Renvoie true si l'unite appartient au joueur actuel
     */
    public boolean estAuJoueurActuel(final Unite unite)
    {
        return unite.getJoueur()==Slatch.partie.getJoueurActuel();
    }
    
    /**
     * Renvoie true si l'unite qui se situe sur le point (pX,pY) appartient au joueur actuel
     */
    public boolean estAuJoueurActuel(final int pX,final int pY)
    {
        return estAuJoueurActuel(getUnite(pX,pY));
    }
    
    /**
     * Cree une unite du type voulue sur la case voulue
     */
    public void creationUnite(final int pX,final int pY, final TypeUnite pType){
        int vNumJoueur = Slatch.partie.getJoueurActuel();
        Joueur vJoueur = Slatch.partie.getJoueur(vNumJoueur);
        
        if(vJoueur.getArgent()>=pType.getPrix() && getUnite(pX,pY)==null)
        {
            vJoueur.addArgent(-pType.getPrix());
            vJoueur.setArgentDepense(pType.getPrix());
            Unite creation = new Unite(pX,pY,vNumJoueur,pType);
            creation.deplacee(true);
            creation.attaque(true);
            Slatch.partie.getTerrain()[pX][pY].setUnite(creation);
            vJoueur.getListeUnite().add(creation);
            vJoueur.addNbrUniteCree();
            repaint();
            
            if(getBrouillard()){
                Brouillard();
            }
        }
       
    }
    
    /**
     * Renvoie true si pC sera a portee apres deplacement de pA
     */
    public boolean seraAPortee(Unite pA, Unite pC) // à supposer que tabDist est rempli
    {
        
        return seraAPortee(pA, pC.getX(), pC.getY());
    }
    
    /**
     * Renvoie true si la case (pX,pY) sera a portee apres deplacement de pA
     */
    public boolean seraAPortee(Unite pA, int pX, int pY)
    {
        if(pA.getAttaque().aTypePortee.getPorteeMin()==1)
        {
            for(Point p: voisins)
            {
                int x = (int)(p.getX())+pX;
                int y = (int)(p.getY())+pY;
                
                if(dansLesBords(x,y))
                {
                    if(tabDist[x][y]>0 && tabDist[x][y]<= pA.getDeplacement() && Slatch.partie.getTerrain()[x][y].getUnite()==null)
                    {
                        return true;
                    }
                }
            }
        }
        return this.estAPortee(pA, pX, pY);
    }
    
    /**
     * Renvoie true si x et y ne depassent pas respectivement la largeur et la hauteur de la carte
     */
    static boolean dansLesBords(final int x,final int y)
    {
        return(x>=0 && y>=0 && x<Slatch.partie.getLargeur() && y<Slatch.partie.getHauteur());
    }
    
    /**
     * un p'tit setteur pour uniteA
     */
    public void setuniteA(Unite pUnite){
        uniteA = pUnite;
    }
    
    /*****************
     * 
     * METHODEs SIMPLIFICATRICEs
     * 
     * *************/
     
    private void repaint()
    {
         Slatch.ihm.getPanel().repaint();
         Slatch.ihm.getpanelinfo().repaint();
    }
    
    /****
     * ENSEMBLE DE METHODE QUI RETOURNE DES INDICES DE JOUEUR
     */ 
    private int getNumJoueur(final int pX,final int pY){
        return Slatch.partie.getTerrain()[pX][pY].getUnite().getJoueur();
    }
    
    private int getNumJoueurActuel(){
        return Slatch.partie.getJoueurActuel();
    }
    
    /****
     * ENSEMBLE DE METHODE QUI RETOURNE DES UNITES
     */ 
    private Unite getUnite(final int pX, final int pY){
       return Slatch.partie.getTerrain()[pX][pY].getUnite();
    }
   
    
    /****
     * ENSEMBLE DE METHODE QUI RETOURNE DES JOUEURS
     */
    public Joueur getJoueur(final Unite pUnite){
        return Slatch.partie.getJoueur(pUnite.getJoueur());
    }
    
    public Joueur getJoueur(final Terrain pTerrain){
        return Slatch.partie.getJoueur(pTerrain.getJoueur());
    }
    
    public Joueur getJoueurTerrain(final int pX,final int pY){
        return Slatch.partie.getJoueur(Slatch.partie.getTerrain()[pX][pY].getJoueur());
    }
    
    public Joueur getJoueur(final int pX,final int pY){
        return Slatch.partie.getJoueur(Slatch.partie.getTerrain()[pX][pY].getUnite().getJoueur());
    }
    
    public Joueur getJoueurActuel(){
        return Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel());
    }
    
    /****
     * ENSEMBLE DE METHODE QUI RETOURNE l'equipe d'un joueur
     */
    public int getEquipe(final int pJoueur){
        return Slatch.partie.getJoueur(pJoueur).getEquipe().getNumEquipe();
    }
    
    public int getEquipe(final Unite pUnite){
        return getJoueur(pUnite).getEquipe().getNumEquipe();
    }
    
    private boolean getBrouillard(){
        return Slatch.partie.getBrouillard();
    }
}
