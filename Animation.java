import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Write a description of class Animation here.
 * 
 * @author Jonathan 
 * @version (a version number or a date)
 */
public class Animation implements ActionListener
{
    private int aNbTick =0;

    /**
     * Constructor for objects of class Animation
     */
    public Animation()
    {

        
    }
    
    // ttes les 100 ms
    public void actionPerformed(ActionEvent event) {
        //System.out.println(aNbTick);
        aNbTick++;
        /*
        beforeTime = System.currentTimeMillis();        
        while(animationDone == false){              
            jumpCycle();                    
            timeDifference = System.currentTimeMillis() - beforeTime;
            sleepTime = 4 - timeDifference;         
            if(sleepTime < 0)       
                sleepTime = 2;              
            try{                        
                Thread.sleep(sleepTime);        
            }           
            catch(Exception e){             
            }
            beforeTime = System.currentTimeMillis();    
        }
        isJumping = isFalling = animationDone = false;
        */
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
                Unite ret=Slatch.partie.getTerrain()[destX][destY].getUnite();
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                for(int i=0; i<Slatch.ihm.getPanel().getaLargeurCarreau() ; i=i+vPasDepl) {
                    Slatch.partie.getTerrain()[destX][destY].getUnite().setDecaleUniteX(-Slatch.ihm.getPanel().getaLargeurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
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
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=Slatch.partie.getTerrain()[destX][destY].getUnite();
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
        
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
        }
        if(unite.getCoordonneeY() != destY ) {
            if(unite.getCoordonneeY() < destY) {
                //BUG
                Unite ret=Slatch.partie.getTerrain()[destX][destY].getUnite();
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                for(int i=0; i<Slatch.ihm.getPanel().getaHauteurCarreau() ; i=i+vPasDepl) {
                    Slatch.partie.getTerrain()[destX][destY].getUnite().setDecaleUniteY(-Slatch.ihm.getPanel().getaHauteurCarreau()+i);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                    Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);
                    try{Thread.sleep(vThread);}catch(InterruptedException e){e.printStackTrace();}
                }
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
                unite.setDecaleUniteX(0);
                unite.setDecaleUniteY(0);
            
                Unite ret=Slatch.partie.getTerrain()[destX][destY].getUnite();
                Slatch.partie.getTerrain()[unite.getCoordonneeX()][unite.getCoordonneeY()].setUnite(mem);
                unite.setCoordonneeX(destX); unite.setCoordonneeY(destY);
                Slatch.partie.getTerrain()[destX][destY].setUnite(unite);
                
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheXdest,pPosHautGaucheYdest,pPosBasDroiteXdest-pPosHautGaucheXdest,pPosBasDroiteYdest-pPosHautGaucheYdest);
                Slatch.ihm.getPanel().paintImmediately(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY);        
                
                Slatch.partie.getJoueur(unite.getJoueur()).addDeplacementTotal(1);
                return ret;
            }
        }
        return null;
    }
}
