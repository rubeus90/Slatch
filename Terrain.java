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

    protected TypeTerrain aType;
    protected int aPVMax;
    protected int aPV;
    protected Unite aUnite;
    
    /**
     * Constructeur des terrains et des batiment
     */
    public Terrain(final int pX,final int pY,final int pJoueur,final TypeTerrain pType) 
    {
        super(pX,pY,pJoueur);
        aType = pType;
        aPVMax = pType.getPVMax();
        aPV = pType.getPVMax();
    }
    
    public Terrain(final int pX,final int pY,final int pJoueur,final TypeTerrain pType,final int pPV) 
    {
        super(pX,pY,pJoueur);
        aType = pType;
        aPVMax = pType.getPVMax();
        aPV = pPV;
    }
    
    
    /**
     * Retourner l'unite se trouvant sur le terrain
     * @return aUnite
     */
    public Unite getUnite(){
        return aUnite;
    }
    
    /**
     * Retourner l'unite se trouvant sur le terrain
     * @return aUnite
     */
    public int getPV(){
        return aPV;
    }
    
    public void setPV(final int pPV){
        aPV=pPV;
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
        return this.aType.aCoutDeplacement.get(unite.getType().getTypeDeplacement().getNom());
    }
    
    @Override
    public void dessine (final Graphics g, PanelMatrice pPanel) {
        int pPosHautGaucheX = super.getCoordonneeX()*pPanel.getaLargeurCarreau();

        int pPosHautGaucheY = super.getCoordonneeY()*pPanel.getaHauteurCarreau();

        int pPosBasDroiteX = (super.getCoordonneeX()+1)*pPanel.getaLargeurCarreau();

        int pPosBasDroiteY = (super.getCoordonneeY()+1)*pPanel.getaHauteurCarreau();

        
            //System.out.println("TERRAIN "+aType.getImage() + getJoueur() + ".png");
            Image img = Slatch.aImages.get(""+ aType.getImage() + getJoueur());
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
        
        if(aUnite!=null){
            aUnite.dessine(g, pPanel);    
        }
        
        
//        try {
//            Image img = ImageIO.read(new File("Images/joueur" + getJoueur() + ".png"));
//            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
//            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
//            }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        
        if(super.getSurbrillance()) {
            
                Image surbrillance = Slatch.aImages.get("5");
                //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
                g.drawImage(surbrillance, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
            
        }
        
    }
    
    public boolean estUnBatiment()
    {
        return (this.aType == TypeTerrain.BATIMENT || this.aType == TypeTerrain.USINE);
    }

    
    public boolean estUnBatimentAuJoueur(int joueur)
    {
        return (this.estUnBatiment() && this.appartientAuJoueur(joueur));
    }
}

