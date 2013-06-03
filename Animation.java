import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe qui s'occupe des animations grace
 * a un timer qui lance actionPerformed toutes
 * les 50ms
 * 
 * @author Jonathan 
 * @version 1.0
 */
public class Animation implements ActionListener
{
    private int aNbTick;
    private long beforeTime;
    private long timeDifference;
    private long sleepTime;
    private boolean animationDone;
    
    private int animation;

    /**
     * Constructeur
     */
    public Animation()
    {
        animationDone = false;
        aNbTick = 0;
    }
    
    /**
     * Methode appelee ttes les 50ms
     */
    public void actionPerformed(ActionEvent event) {

        /*beforeTime = System.currentTimeMillis();        
        while(animationDone == false){              
            // TODO Faire anime
            
            // TODO END
            timeDifference = System.currentTimeMillis() - beforeTime;
            
            
            beforeTime = System.currentTimeMillis();    
        }
        animationDone = false;
        */
        //System.out.println(aNbTick);
        aNbTick++;
        animation+=6;
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
        int pPosHautGaucheX = unite.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheY = unite.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteX = (unite.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (unite.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();
        
        int pPosHautGaucheXdest = destX*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheYdest = destY*Slatch.ihm.getPanel().getaHauteurCarreau();
        int pPosBasDroiteXdest = (destX+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteYdest = (destY+1)*Slatch.ihm.getPanel().getaHauteurCarreau();

        if(unite.getCoordonneeX() != destX ) {
            if(unite.getCoordonneeX() < destX) {
                //BUG
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                
                /*
                for(int i=0; i<Slatch.ihm.getPanel().getaLargeurCarreau() ; i=i+vPasDepl) {
                    getUnite(destX,destY).setDecaleUniteX(-Slatch.ihm.getPanel().getaLargeurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                */
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    getUnite(destX,destY).setDecaleUniteX(-Slatch.ihm.getPanel().getaLargeurCarreau()+animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
            else  {
                
                /*
                for(int i=0; i<Slatch.ihm.getPanel().getaLargeurCarreau() ; i=i+vPasDepl) {
                    unite.setDecaleUniteX(-i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                */

                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    unite.setDecaleUniteX(-animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }

                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
        
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                getJoueur(unite).addDeplacementTotal(1);
                return ret;
            }
        }
        if(unite.getCoordonneeY() != destY ) {
            if(unite.getCoordonneeY() < destY) {
                //BUG
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                
                /*
                for(int i=0; i<Slatch.ihm.getPanel().getaHauteurCarreau() ; i=i+vPasDepl) {
                    getUnite(destX,destY).setDecaleUniteY(-Slatch.ihm.getPanel().getaHauteurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                */
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    getUnite(destX,destY).setDecaleUniteY(-Slatch.ihm.getPanel().getaHauteurCarreau()+animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                
                
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
            else  {
                
                /*
                for(int i=0; i<Slatch.ihm.getPanel().getaHauteurCarreau() ; i=i+vPasDepl) {
                    unite.setDecaleUniteY(-i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
                */
               
                animation=0;
                while(animation<Slatch.ihm.getPanel().getaLargeurCarreau()) {
                    unite.setDecaleUniteY(-animation);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                }
                
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=getUnite(destX,destY);
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
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
