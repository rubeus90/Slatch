import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Panneau de l'IHM (herite de JPanel)
 * 
 * @author MERCANDALLI 
 * @version 01/2013
 */
class IHM_Panel extends JPanel 
{
    private int NOMBRE_DE_CASE_X;
    private int NOMBRE_DE_CASE_Y;
    private Terrain[][] MATRICE_TEST;
    private int DECALAGE_PX_EN_Y;
    
    private int hauteurCarte;
    private int largeurCarte;
    private int vLargeurCarreau;
    private int vHauteurCarreau;
    
    private int aLargeurCarreau;
    private int aHauteurCarreau;
    
    public IHM_Panel(final int pDecalageY)
    {
        NOMBRE_DE_CASE_X = Slatch.partie.getLargeur();
        NOMBRE_DE_CASE_Y = Slatch.partie.getHauteur();
        DECALAGE_PX_EN_Y = pDecalageY;
        MATRICE_TEST=Slatch.partie.getTerrain();
    }
    
    /**
     * 
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        hauteurCarte = this.getHeight()-DECALAGE_PX_EN_Y;
        largeurCarte = this.getWidth();
        vLargeurCarreau = largeurCarte/NOMBRE_DE_CASE_X;
        vHauteurCarreau = hauteurCarte/NOMBRE_DE_CASE_Y;
        aLargeurCarreau = vLargeurCarreau;
        aHauteurCarreau = vLargeurCarreau;
        
        //afficheTest(g);
        dessineMatrice(MATRICE_TEST, g);
        afficheBarreInfo(g);
    }    

    /**
     * Methode appelee lors d'un click
     */
    public void coordclick (String pcoordclick) 
    {
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 car la barre de la fenete fait 20 px
        Graphics g = Slatch.ihm.getPanel().getGraphics();
        for(int i = 0 ; i < NOMBRE_DE_CASE_X ; i++) 
        {
            for(int j = 0 ; j < NOMBRE_DE_CASE_Y ; j++) 
            {
                // Selection
                int pPosHautGaucheX = i*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosHautGaucheY = j*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
                int pPosBasDroiteX = (i+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosBasDroiteY = (j+1)*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
                
                
                if(pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) 
                {
                    // Avertir Moteur
                    Slatch.moteur.caseSelectionnee(i,j);
                    Slatch.partie.getTerrain()[i][j].dessine(g);
                }
            }
        }
    }
    
    /**
     * Affiche Test
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        int vLargeurMenuEnCase=3;
        int vHauteurMenuEnCase=6;
        int vHautGauche_X;
        int vHautGauche_Y;
        int vBasDroite_X;
        int vBasDroite_Y;
        Graphics g = Slatch.ihm.getPanel().getGraphics();

        if(pX+vLargeurMenuEnCase+1>NOMBRE_DE_CASE_X && pY+vHauteurMenuEnCase+1>NOMBRE_DE_CASE_Y) 
        {
            //Dessine en haut à gauche
            vHautGauche_X = (pX-vLargeurMenuEnCase)*aLargeurCarreau;
            vHautGauche_Y = (pY-vHauteurMenuEnCase)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            vBasDroite_X = pX*aLargeurCarreau;
            vBasDroite_Y = pY*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }

        else if(pX+vLargeurMenuEnCase+1>NOMBRE_DE_CASE_X) 
        {
            //Dessine en bas à gauche
            vHautGauche_X = (pX-vLargeurMenuEnCase)*aLargeurCarreau;
            vHautGauche_Y = (pY+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            vBasDroite_X = pX*aLargeurCarreau;
            vBasDroite_Y = (pY+vHauteurMenuEnCase+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
        
        else if(pY+vHauteurMenuEnCase+1>NOMBRE_DE_CASE_Y) {
            //Dessine en haut à droite
            vHautGauche_X = (pX+1)*aLargeurCarreau;
            vHautGauche_Y = (pY-vHauteurMenuEnCase)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            vBasDroite_X = (pX+vLargeurMenuEnCase+1)*aLargeurCarreau;
            vBasDroite_Y = pY*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
        
        else {
            // Meun en bas a droite par default
            vHautGauche_X = (pX+1)*aLargeurCarreau;
            vHautGauche_Y = (pY+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            vBasDroite_X = (pX+vLargeurMenuEnCase+1)*aLargeurCarreau;
            vBasDroite_Y = (pY+vHauteurMenuEnCase+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
            
        afficheImageRedim ("noir80.png", vHautGauche_X, vHautGauche_Y, vBasDroite_X, vBasDroite_Y, g);
        g.setColor(Color.white);
        g.drawString(pList.get(0), (vHautGauche_X+vBasDroite_X)/2-3*aLargeurCarreau/2, vHautGauche_Y+aHauteurCarreau/2);
        //g.drawString(pList.get(1), (vHautGauche_X+vBasDroite_X)/2-3*aLargeurCarreau/2, vHautGauche_Y+3*aHauteurCarreau/2);
        //g.drawString(pList.get(2), (vHautGauche_X+vBasDroite_X)/2-3*aLargeurCarreau/2, vHautGauche_Y+5*aHauteurCarreau/2);
        g.drawLine(vHautGauche_X, vHautGauche_Y+(vHauteurMenuEnCase/vHauteurMenuEnCase)*aHauteurCarreau, vBasDroite_X, vHautGauche_Y+(vHauteurMenuEnCase/vHauteurMenuEnCase)*aHauteurCarreau);
        g.drawLine(vHautGauche_X, vHautGauche_Y+(2*vHauteurMenuEnCase/vHauteurMenuEnCase)*aHauteurCarreau, vBasDroite_X, vHautGauche_Y+(2*vHauteurMenuEnCase/vHauteurMenuEnCase)*aHauteurCarreau);
    }

    /**
     * Affiche Test
     */
    private void afficheTest (final Graphics g) {
        for(int i = 0; i < NOMBRE_DE_CASE_X ; i++) {
            for(int j=0 ; j < NOMBRE_DE_CASE_Y ; j++) {
                afficheImageRedim(""+(i%4+1)+".png",vLargeurCarreau*i, vHauteurCarreau*j+DECALAGE_PX_EN_Y, vLargeurCarreau*(i+1), vHauteurCarreau*(j+1)+DECALAGE_PX_EN_Y,g);
                g.setColor(Color.white);
                g.drawRect(vLargeurCarreau*i, j*vHauteurCarreau+DECALAGE_PX_EN_Y, vLargeurCarreau+vLargeurCarreau*i, vHauteurCarreau+j*vHauteurCarreau+DECALAGE_PX_EN_Y);
            }
        }
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void afficheMatrice(final String[][] pStringURL, final Graphics g) {
        for(int i=0; i<NOMBRE_DE_CASE_X; i++) {
            for(int j=0; j<NOMBRE_DE_CASE_Y; j++) {
                afficheImageRedim(pStringURL[i][j], vLargeurCarreau*i, vHauteurCarreau*j+DECALAGE_PX_EN_Y, vLargeurCarreau*(i+1), vHauteurCarreau*(j+1)+DECALAGE_PX_EN_Y, g);
            }
        }
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void dessineMatrice(final Terrain[][] pMatrice, final Graphics g) {
        for(int i=0; i<NOMBRE_DE_CASE_X; i++) {
            for(int j=0; j<NOMBRE_DE_CASE_Y; j++) {
                MATRICE_TEST[i][j].dessine(g);
            }
        }
    }

    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (final Graphics g) {
        afficheImageRedim("4.png",0, 0,this.getWidth(),DECALAGE_PX_EN_Y,g);
        g.setColor(Color.white);
        g.drawString("Tour :"+"1", this.getWidth()/8, DECALAGE_PX_EN_Y/2);
        g.drawString("Argent :"+"1000", this.getWidth()/4, DECALAGE_PX_EN_Y/2);
        g.drawString("SUIVANT", this.getWidth()-this.getWidth()/8, DECALAGE_PX_EN_Y/2);
    }
    

    
    //********************************/
    //**    Méthodes de dessin       */
    //********************************/
    public void dessineTerrain(final int i, final int j)
    {
        Graphics g = this.getGraphics();
        Slatch.partie.getTerrain()[i][j].dessine(g);
    }
    
    /**
     * Affiche une image a la position desiree
     */
    private void afficheImage (final String pURL, final int pPosX, final int pPosY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, pPosX, pPosY, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImagePleinEcran (final String pURL, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public int getDECALAGE_PX_EN_Y() {
        return DECALAGE_PX_EN_Y;
    }
    
    public int getaLargeurCarreau() {
        return aLargeurCarreau;
    }
    
    public int getaHauteurCarreau() {
        return aHauteurCarreau;
    }
}
