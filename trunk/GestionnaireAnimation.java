import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.awt.Point;

/**
 * Classe qui s'occupe des animations grace
 * a un timer qui lance actionPerformed toutes
 * les 50ms
 * 
 * @author Jonathan 
 * @version 1.0
 */
public class GestionnaireAnimation implements ActionListener
{
    private int aNbTick;
    private long beforeTime;
    private long timeDifference;
    private long sleepTime;
    private boolean animationDone;
    //private Stack<Point> chemin;
    private List<Animation> animation;
    //private List<Stack<Point>> aLesChemins; // LISTE DES CHEMINS DES ANIMATIONS A FAIRE
    //private List<Point> aDepart; // LISTE DES DEPARTS DES ANIMATIONS
    private int numeroUnite; // NUMERO DE L'ANIMATION A FAIRE
    //private int animation;
    //private List<Unite> aUnite; // LISTE DES UNITES CONCERNÉES PAR LES ANIMATIONS
    private int avancement;
    private boolean initialisation;
    //private double vitesse;
    private int destination;
    
    public List<Unite> aTricheAffichage;
    /**
     * Constructeur
     */
    public GestionnaireAnimation()
    {
        animationDone = false;
        numeroUnite=0;
        aNbTick = 0;
        avancement=0;
        //aDepart=new ArrayList<Point>(); //Vector
        //aUnite=new ArrayList<Unite>();
        initialisation = false;
        animation = new ArrayList<Animation>();
         aTricheAffichage= new ArrayList<Unite>();
    }
    
    /*public void setChemins(final Stack<Point> pChemin)
    {
        aLesChemins.add(pChemin);
    }
    
    public void setUnite(final Unite pUnite)
    {
        aUnite.add(pUnite);
    }
    
    public void setDepart(final Point pDepart)
    {
        aDepart.add(pDepart);
    }*/
    
    
       public void addAnimation(final Animation pAnimation)
    {
     animation.add(pAnimation);
    }
    
    /**
     * Methode appelee ttes les 50ms
     */
    public void actionPerformed(ActionEvent event) {
        
        long deltaT = System.currentTimeMillis()-beforeTime;
        beforeTime = System.currentTimeMillis();   
        if(deltaT>70) return;
        
        
        if(animation.isEmpty())
        {
            //System.out.println("On s'arrete (pas d'anim a faire)");
            numeroUnite=0;
            aNbTick = 0;
            avancement=0;
            //aLesChemins.clear();
            //aDepart.clear();
            //aUnite.clear();
            Slatch.ihm.timer.stop();
            aTricheAffichage.clear();
            if(Slatch.partie.getBrouillard()){
                Slatch.moteur.Brouillard();
                }
            if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
              Slatch.moteur.passeTour();
            
            return;
        }

        if(animation.get(numeroUnite).aType.equals("deplacement"))
        {
            if(((AnimationDeplacement)animation.get(numeroUnite)).getChemin().isEmpty())
            {
                if(numeroUnite>=animation.size()-1)
                {
                    //System.out.println("On s'arrete (plus d'anim a faire)");
                    numeroUnite=0;
                    aNbTick = 0;
                    avancement=0;
                    /*aLesChemins.clear();
                    aDepart.clear();
                    aUnite.clear();*/
                    animation.clear();
                    aTricheAffichage.clear();
                    Slatch.ihm.timer.stop();
                    if(Slatch.partie.getBrouillard()){
                    Slatch.moteur.Brouillard();
                    }
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                     Slatch.moteur.passeTour();
                    return;
                }
                else
                {
                    numeroUnite++;
                    if(Slatch.partie.getBrouillard()){
                    Slatch.moteur.Brouillard();
                    }
                }
            }
            
            else 
            {
            int destX = (int)((AnimationDeplacement)animation.get(numeroUnite)).getChemin().peek().getX();
            int destY = (int)((AnimationDeplacement)animation.get(numeroUnite)).getChemin().peek().getY();
            
            changerCase(((AnimationDeplacement)animation.get(numeroUnite)).getUnite(),destX,destY,deltaT);
            }
        }
        else if (animation.get(numeroUnite).aType.equals("attaque"))
        {
            Unite attaquant= ((AnimationAttaque)animation.get(numeroUnite)).getAttaquant();
            Unite victime =((AnimationAttaque)animation.get(numeroUnite)).getVictime();
            boolean fin = afficheDegat(attaquant,victime);
            avancement += deltaT;
            if(fin)
            {
                
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    avancement=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {
                    numeroUnite++;
                    
                   }
                
            }
        }
        else if (animation.get(numeroUnite).aType.equals("soin"))
        {
            Unite cible= ((AnimationSoin)animation.get(numeroUnite)).getCible();
            boolean fin = afficheSoin(cible);
            avancement += deltaT;
            if(fin)
            {
                
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    avancement=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {
                    numeroUnite++;
                    
                   }
                
            }
        }
    }
    
    /**
     * Deplace une unite vers sa destination
     * @param unite unite a deplacer
     * @param pX abscisse de l'arrivee
     * @param pY ordonnee de l'arrivee
     */
    public void changerCase(Unite unite,final int destX,final int destY,long deltaT)
    {
        /*
         ************************************************************************************************** 
         * 
         * CE METHODE PEUT ETRE SIMPLIFIER CAR ON SEPARAIT LES DIFFERENT CAR CAR ON AVAIS DES PROBLEMES D'AFFICHAGE MAIS NOUS AVONS TROUVÉ UNE AUTRE SOLUTION
         * MAIS LA METHODE N'A PAS ENCORE ETÉ SIMPLIFIÉ
         * 
         **************************************************************************************************  
         */
        Point vDepart = ((AnimationDeplacement)animation.get(numeroUnite)).getDepart();
        double vitesse = ((AnimationDeplacement)animation.get(numeroUnite)).getVitesse();
        int vPasDepl = 8;
        int vThread = 5;
        int pPosHautGaucheX = unite.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheY = unite.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteX = (unite.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (unite.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        
        int pPosHautGaucheXdest = destX*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYdest = destY*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXdest = (destX+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYdest = (destY+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        
        int pPosHautGaucheXdepart = (int)vDepart.getX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYdepart = (int)vDepart.getY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXdepart = (int)(vDepart.getX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYdepart = (int)(vDepart.getY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        //System.out.println("-------------------------------------------------------");
        //System.out.println("DELTA "+deltaT);
        //System.out.println("VITESSE "+(int)deltaT*(AnimationDeplacement)animation.get(numeroUnite)).getVitesse());
        
        int dX=destX-(int)vDepart.getX();
        int dY=destY-(int)vDepart.getY();
        if(dX!=0 ||dY!=0)
        {
                if(!initialisation)
                {
                    if(dY!=0){
                        avancement=pPosBasDroiteYdepart;
                        destination=pPosBasDroiteYdest;
                    }
                    else if(dX!=0){
                        avancement=pPosHautGaucheXdepart;
                        destination=pPosHautGaucheXdest;
                    }
                    initialisation = true;
                }
                
                unite.addDecaleUniteX(dX*(int)(deltaT*vitesse));
                avancement+=dX*(int)(deltaT*vitesse);
                unite.addDecaleUniteY(dY*(int)(deltaT*vitesse));
                avancement+=dY*(int)(deltaT*vitesse);
                
                //System.out.println(dX+" "+dY);
                if(dY<0 || dX<0){
                     Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdepart-pPosHautGaucheXdest,pPosBasDroiteYdepart-pPosHautGaucheYdest);
                     Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                else if(dY>0 || dX>0){
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdepart,pPosHautGaucheYdepart,pPosBasDroiteXdest-pPosHautGaucheXdepart,pPosBasDroiteYdest-pPosHautGaucheYdepart);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                
                
                if(avancement>destination && (dY>0 || dX>0)) //QUAND l'IMAGE EST ARRIVEE A LA CASE FINAL
                {   
                    //System.out.println(avancement + " et "+ destX);
                    unite.setDecaleUniteX(-(int)(pPosHautGaucheX - pPosHautGaucheXdest ));
                    unite.setDecaleUniteY(-(int)(pPosBasDroiteY - pPosBasDroiteYdest ));
                    ((AnimationDeplacement)animation.get(numeroUnite)).setDepart(new Point(destX,destY));
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    //Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                }
                else if(avancement<destination && (dY<0 || dX<0))
                {
                    //System.out.println(avancement + " et "+ destX);
                    unite.setDecaleUniteX(-(int)(pPosHautGaucheX - pPosHautGaucheXdest ));
                    unite.setDecaleUniteY(-(int)(pPosBasDroiteY - pPosBasDroiteYdest ));
                    ((AnimationDeplacement)animation.get(numeroUnite)).setDepart(new Point(destX,destY));
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    //Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                }
                
                return;
            }  
        
        initialisation=false;
        avancement=0;
        destination=0;
        
        ((AnimationDeplacement)animation.get(numeroUnite)).getChemin().pop();
    }
    
    public boolean afficheDegat(final Unite attaquant,final Unite victime)
    {
        int pPosHautGaucheXatt = attaquant.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYatt = attaquant.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXatt = (attaquant.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYatt = (attaquant.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        
        int pPosHautGaucheXvic = victime.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYvic = victime.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXvic = (victime.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYvic = (victime.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        if(avancement<1000)
        {
            attaquant.setCheck(true);
            victime.setCheck(true);
            attaquant.setBlessure(   ((AnimationAttaque)animation.get(numeroUnite)).getPVatt()  -   attaquant.getPVaffiche()     );
            victime.setBlessure(     ((AnimationAttaque)animation.get(numeroUnite)).getPVvic()  -   victime.getPVaffiche()      );
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXvic,pPosHautGaucheYvic,pPosBasDroiteXvic-pPosHautGaucheXvic,pPosBasDroiteYvic-pPosHautGaucheYvic);
            return false;
        }
        else 
        {
            attaquant.setCheck(false);
            victime.setCheck(false);
            attaquant.setPVaffiche(((AnimationAttaque)animation.get(numeroUnite)).getPVatt());
            victime.setPVaffiche(((AnimationAttaque)animation.get(numeroUnite)).getPVvic());
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXvic,pPosHautGaucheYvic,pPosBasDroiteXvic-pPosHautGaucheXvic,pPosBasDroiteYvic-pPosHautGaucheYvic);
            return true;
        }
    }
    
    public boolean afficheSoin(final Unite cible)
    {
        int pPosHautGaucheXatt = cible.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYatt = cible.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXatt = (cible.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYatt = (cible.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
       

        if(avancement<1000)
        {
            cible.setCheck(true);
            
            cible.setBlessure(   ((AnimationSoin)animation.get(numeroUnite)).getPVcib()  -   cible.getPVaffiche()     );
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            return false;
        }
        else 
        {
            cible.setCheck(false);
            cible.setPVaffiche(((AnimationSoin)animation.get(numeroUnite)).getPVcib());
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            return true;
        }
    }
    
    public void start()
    {
        Slatch.ihm.timer.start();
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
    private Joueur getJoueur(final Unite pUnite){
        return Slatch.partie.getJoueur(pUnite.getJoueur());
    }
}
