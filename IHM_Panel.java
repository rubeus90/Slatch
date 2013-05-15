import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;
import java.awt.Color;
import java.util.HashMap;

/**
 * Panneau de l'IHM (herite de JPanel)
 * 
 * @author MERCANDALLI 
 * @version 01/2013
 */
class IHM_Panel extends JPanel {
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
    
    public IHM_Panel(final int pDecalageY){
        NOMBRE_DE_CASE_X = 16;//Slatch.myPartie.getLargeur();
        NOMBRE_DE_CASE_Y = 9;//Slatch.myPartie.getHauteur();
        DECALAGE_PX_EN_Y = pDecalageY;
        
        MATRICE_TEST = new Terrain[NOMBRE_DE_CASE_X][NOMBRE_DE_CASE_Y];
        
        for(int i = 0 ; i < NOMBRE_DE_CASE_X ; i++) {
            for(int j = 0 ; j < NOMBRE_DE_CASE_Y ; j++) {
                Terrain test;
                test=new Terrain(i, j, 0, 0,TypeTerrain.PLAINE);
                MATRICE_TEST[i][j]=test;
            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void paintComponent (final Graphics g) {
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
    public void coordclick (String pcoordclick) {

     
        
        
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 car la barre de la fenete fait 20 px
        Graphics g = Slatch.ihm.getPanel().getGraphics();
        for(int i = 0 ; i < NOMBRE_DE_CASE_X ; i++) {
            for(int j = 0 ; j < NOMBRE_DE_CASE_Y ; j++) {
                // Selection
                
                int pPosHautGaucheX = i*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosHautGaucheY = j*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
                int pPosBasDroiteX = (i+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosBasDroiteY = (j+1)*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
                
                
                if(  pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) {
                    MATRICE_TEST[i][j].setSurbrillance(true);
                    MATRICE_TEST[i][j].dessine(g);
                    // Avertir Moteur
                    //Slatch.moteur.caseSelectionnee(i,j);
                }
                
                
                
                /*
                if(  j*vHauteurCarreau+DECALAGE_PX_EN_Y<clickY && clickY<(j+1)*vHauteurCarreau+DECALAGE_PX_EN_Y && vLargeurCarreau*i<clickX && clickX<(i+1)*vLargeurCarreau) {
                    MATRICE_TEST[i][j].setSurbrillance(true);
                    MATRICE_TEST[i][j].dessine(g);
                    // Avertir Moteur
                    //Slatch.moteur.caseSelectionnee(i,j);
                }
                */
                
            }
        }
    }
    
    /**
     * Affiche Test
     */
    private void afficheMenu(final int pX, final int pY, final String pS) {
        if(pX<NOMBRE_DE_CASE_X/2) {
            // Meun à droite
            Graphics g = Slatch.ihm.getPanel().getGraphics();
        }
        else {
            // Meun à droite
            Graphics g = Slatch.ihm.getPanel().getGraphics();
        }
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
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
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



