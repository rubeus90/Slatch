import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

/**
 * Cette classe gere les terrains du jeu, qui comporte les differents types de terrains et
 * les batiments
 *
 *@author Ngoc
 *@version 1.0
 */


public class Terrain extends Entite{
    private TypeTerrain aType;
    private Unite aUnite;
    
    /**
     * Constructeur des terrains et des batiment
     */
    public Terrain(
        final int pX,
        final int pY,
        final int pJoueur,
        final int pPointDeVie) 
    {
        super(pX,pY,pJoueur,pPointDeVie);
    }
    
    /**Retourner l'unite se trouvant sur le terrain
    *@return aUnite
    */
    public Unite getUnite(){
        return aUnite;
    }
    
    /**Attribut une unite à un terrain
    *@return aUnite
    */
    public void setUnite(final Unite pUnite){
        aUnite = pUnite;
    }  
    
    @Override
    public void dessine (final Graphics g) {
        int pPosHautGaucheX = super.getCoordonneeX()*Slatch.ihm.getpanel().getaLargeurCarreau();
        int pPosHautGaucheY = super.getCoordonneeY()*Slatch.ihm.getpanel().getaHauteurCarreau() + Slatch.ihm.getpanel().getDECALAGE_PX_EN_Y();
        int pPosBasDroiteX = (super.getCoordonneeX()+1)*Slatch.ihm.getpanel().getaLargeurCarreau();
        int pPosBasDroiteY = (super.getCoordonneeY()+1)*Slatch.ihm.getpanel().getaHauteurCarreau() + Slatch.ihm.getpanel().getDECALAGE_PX_EN_Y();
        try {
            Image img = ImageIO.read(new File("Images/"+null));
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getpanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        if(super.getSurbrillance()) {
            try {
                Image img = ImageIO.read(new File("Images/5.png"));
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getpanel());
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

