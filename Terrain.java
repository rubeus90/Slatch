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
        final int pPVMax,
        final TypeTerrain pType) 
    {
        super(pX,pY,pJoueur,pPVMax);
        aType = pType;
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
    
    public TypeTerrain getType(){
        return aType;
    }
    
    @Override
    public void dessine (final Graphics g) {
        int pPosHautGaucheX = super.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosHautGaucheY = super.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
        int pPosBasDroiteX = (super.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
        int pPosBasDroiteY = (super.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
        try {
            Image img = ImageIO.read(new File("Images/" + aType.getImage()));
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        if(aUnite!=null){
            aUnite.dessine(g);    
        }
        
        if(super.getSurbrillance()) {
            try {
                Image img = ImageIO.read(new File("Images/5.png"));
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}

