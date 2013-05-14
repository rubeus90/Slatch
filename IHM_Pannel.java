import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;
import java.awt.Color;

/**
 * Panneau de l'IHM (herite de JPanel)
 * 
 * @author MERCANDALLI 
 * @version 01/2013
 */
class IHM_Pannel extends JPanel {
    

    private static int NOMBRE_DE_CASE_X = 16;
    private static int NOMBRE_DE_CASE_Y = 9;
    private static int[][] MATRICE_TEST = new int[NOMBRE_DE_CASE_X][NOMBRE_DE_CASE_Y];
    private static int DECALAGE_PX_EN_Y = 50;
    
    private int hauteurCarte;
    private int largeurCarte;
    private static int vLargeurCarreau;
    private static int vHauteurCarreau;

    /**
     * 
     */
    @Override
    public void paintComponent (final Graphics g) {
        hauteurCarte = this.getHeight()-DECALAGE_PX_EN_Y;
        largeurCarte = this.getWidth();
        vLargeurCarreau = largeurCarte/NOMBRE_DE_CASE_X;
        vHauteurCarreau = hauteurCarte/NOMBRE_DE_CASE_Y;
        
        afficheTest(g);
        afficheBarreInfo(g);
    }    

    /**
     * Methode appelee lors d'un click
     */
    public static void coordclick (String pcoordclick) {
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 je sais pas pourquoi  
        Graphics g = IHM.getMenu1().getGraphics();
        for(int i = 0 ; i < NOMBRE_DE_CASE_X ; i++) {
            for(int j = 0 ; j < NOMBRE_DE_CASE_Y ; j++) {
                // Selection
                if( vLargeurCarreau*i<clickX && clickX<(i+1)*vLargeurCarreau && j*vHauteurCarreau+DECALAGE_PX_EN_Y<clickY && clickY<(j+1)*vHauteurCarreau+DECALAGE_PX_EN_Y) {
                    afficheImageRedim("5.png",vLargeurCarreau*i, j*vHauteurCarreau+DECALAGE_PX_EN_Y,(i+1)*vLargeurCarreau,(j+1)*vHauteurCarreau+DECALAGE_PX_EN_Y,g);
                    
                    // Avertir Moteur
                    // en passant i et j
                    
                }
            }
        }
    }
    
    /**
     * Affiche Test
     */
    private void afficheTest (Graphics g) {
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
    private void afficheMatriceTest(String[][] pStringURL, Graphics g) {
        for(int i=0; i<NOMBRE_DE_CASE_X; i++) {
            for(int j=0; j<NOMBRE_DE_CASE_Y; j++) {
                afficheImageRedim(pStringURL[i][j], vLargeurCarreau*i, vHauteurCarreau*j+DECALAGE_PX_EN_Y, vLargeurCarreau*(i+1), vHauteurCarreau*(j+1)+DECALAGE_PX_EN_Y, g);
            }
        }
    }

    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (Graphics g) {
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
    private static void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            //g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pLargeur, pHauteur, IHM.getMenu1());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, IHM.getMenu1());
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
}


