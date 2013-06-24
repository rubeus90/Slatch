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
    private List<Animation> animation;
    private int numeroUnite; // NUMERO DE L'ANIMATION A FAIRE
    private int avancement;
    private boolean initialisation;
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
        initialisation = false;
        animation = new ArrayList<Animation>();
         aTricheAffichage= new ArrayList<Unite>();
    }
    
    
       public void addAnimation(final Animation pAnimation)
    {
     animation.add(pAnimation);
    }
    
       public void viderAnimation()
    {
     animation.clear();
    }
   
    /**
     * Methode appelee ttes les 50ms
     */
    public void actionPerformed(ActionEvent event) {
        boolean fin;
        Unite cible;
        long deltaT = System.currentTimeMillis()-beforeTime;
        beforeTime = System.currentTimeMillis();   
        if(deltaT>70) return;
        Slatch.ihm.getPanel().setClickOK(false);
        
        if(animation.isEmpty())
        {
            numeroUnite=0;
            aNbTick = 0;
            avancement=0;
            Slatch.ihm.timer.stop();
            Slatch.ihm.getPanel().setClickOK(true);
            aTricheAffichage.clear();
            if(Slatch.partie.getBrouillard()){
                Slatch.moteur.Brouillard();
                }
            if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
              Slatch.moteur.passeTour();
            
            return;
        }

        switch (animation.get(numeroUnite).aType)
        {
          case "deplacement":
            if(((AnimationDeplacement)animation.get(numeroUnite)).getChemin().isEmpty())
            {
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    aNbTick = 0;
                    avancement=0;
                    animation.clear();
                    aTricheAffichage.clear();
                    Slatch.ihm.timer.stop();
                    Slatch.ihm.getPanel().setClickOK(true);
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
            return;
            
            
            
          case "attaque":
        
            Unite attaquant= ((AnimationAttaque)animation.get(numeroUnite)).getAttaquant();
            Unite victime =((AnimationAttaque)animation.get(numeroUnite)).getVictime();
            fin = afficheDegat(attaquant,victime);
            avancement += deltaT;
            if(fin)
            {
                
                
                    avancement=0;
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    Slatch.ihm.getPanel().setClickOK(true);
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {
                    numeroUnite++;
                    
                   }
                
            }
            return;
            
            
            
          case "soin" :
        
            cible= ((AnimationSoin)animation.get(numeroUnite)).getCible();
            fin = afficheSoin(cible);
            avancement += deltaT;
            if(fin)
            {
                    avancement=0;
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    Slatch.ihm.getPanel().setClickOK(true);
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {numeroUnite++;}
                
            }
            return;  
            
            
          case "mort":
            
            cible= ((AnimationMort)animation.get(numeroUnite)).getCible();
            fin = afficheMort(cible);
            avancement += deltaT;
            if(fin)
            {
                
                    avancement=0;
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    Slatch.ihm.getPanel().setClickOK(true);
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {numeroUnite++;}
                
            }
            return;
          case "evolution":
          
            cible= ((AnimationEvolution)animation.get(numeroUnite)).getCible();
            fin = afficheEvolution(cible);
            avancement += deltaT;
            
            
            if(fin)
            {
                    avancement=0;
                if(numeroUnite>=animation.size()-1)
                {
                    numeroUnite=0;
                    animation.clear();
                    Slatch.ihm.timer.stop();
                    Slatch.ihm.getPanel().setClickOK(true);
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    Slatch.moteur.passeTour();
                    
                    return;
                }
                else
                {numeroUnite++;}
                
            }
            return;
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
        Point vDepart = ((AnimationDeplacement)animation.get(numeroUnite)).getDepart();
        double vitesse = ((AnimationDeplacement)animation.get(numeroUnite)).getVitesse();
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
                    
                    unite.setDecaleUniteX(-(int)(pPosHautGaucheX - pPosHautGaucheXdest ));
                    unite.setDecaleUniteY(-(int)(pPosBasDroiteY - pPosBasDroiteYdest ));
                    ((AnimationDeplacement)animation.get(numeroUnite)).setDepart(new Point(destX,destY));
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                }
                else if(avancement<destination && (dY<0 || dX<0))
                {
                    
                    unite.setDecaleUniteX(-(int)(pPosHautGaucheX - pPosHautGaucheXdest ));
                    unite.setDecaleUniteY(-(int)(pPosBasDroiteY - pPosBasDroiteYdest ));
                    ((AnimationDeplacement)animation.get(numeroUnite)).setDepart(new Point(destX,destY));
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
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
    
    public boolean afficheMort(final Unite cible)
    {
        int pPosHautGaucheXatt = cible.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYatt = cible.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXatt = (cible.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYatt = (cible.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
       

        if(avancement<900)
        {
            cible.setMort(true);
            int numero = (int)avancement/90;
            cible.setNumeroExplosion(numero);
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            return false;
        }
        else 
        {
            cible.setMort(false);
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            return true;
        }
    }
    
    public boolean afficheEvolution(final Unite cible)
    {
        int pPosHautGaucheXatt = cible.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYatt = cible.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXatt = (cible.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYatt = (cible.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
       

        if(avancement<900)
        {
            cible.setLvlup(true);
            int numero = (int)avancement/180;
            cible.setNumeroFleche(numero);
            Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXatt,pPosHautGaucheYatt,pPosBasDroiteXatt-pPosHautGaucheXatt,pPosBasDroiteYatt-pPosHautGaucheYatt);
            return false;
        }
        else 
        {
            cible.setLvlup(false);
            cible.setPVaffiche(cible.getPV());
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
