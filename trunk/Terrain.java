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
    
    /**
     * Retourner l'unite se trouvant sur le terrain
     * @return aUnite
     */
    public Unite getUnite(){
        return aUnite;
    }
    
    /**
     * Attribut une unite à un terrain
     * @return aUnite
     */
    public void setUnite(final Unite pUnite){
        aUnite = pUnite;
    }  
    
    /**
     * Retourner le type de terrain
     * @return aType
     */
    public TypeTerrain getType(){
        return aType;
    }
    
    public int getCout(Unite unite)
    {
        return this.aType.aCoutDeplacement.get(unite.getTypeDeplacement().getNom());
    }
    
    @Override
    public void dessine (final Graphics g) {
        int pPosHautGaucheX = super.getCoordonneeX()*Slatch.ihm.getPanel().getaLargeurCarreau();

        int pPosHautGaucheY = super.getCoordonneeY()*Slatch.ihm.getPanel().getaHauteurCarreau();

        int pPosBasDroiteX = (super.getCoordonneeX()+1)*Slatch.ihm.getPanel().getaLargeurCarreau();

        int pPosBasDroiteY = (super.getCoordonneeY()+1)*Slatch.ihm.getPanel().getaHauteurCarreau();

        
            //System.out.println("TERRAIN "+aType.getImage() + getJoueur() + ".png");
            Image img = Slatch.aImages.get(""+ aType.getImage() + getJoueur());
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        
        if(aUnite!=null){
            aUnite.dessine(g);    
        }
        
        
//        try {
//            Image img = ImageIO.read(new File("Images/joueur" + getJoueur() + ".png"));
//            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
//            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
//            }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        
        if(super.getSurbrillance()) {
            
                Image surbrillance = Slatch.aImages.get("5");
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(surbrillance, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
            
        }
        
    }
}

