import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;

/**
 * Panneau de l'IHM (herite de JPanel)
 * 
 * @author MERCANDALLI 
 * @version 01/2013
 */
class IHM_Pannel extends JPanel {
    public static void clikcount(int pcoucou) {
        System.out.println(pcoucou);
    }

    /**
     */
    @Override
    public void paintComponent(final Graphics g) {
        afficheImagePleinEcran("1.png",g);
        afficheImageRedim("2.png",5, 5,100,100,g);
    }    

    /**
     * Affiche une image a la position desiree
     */
    private void afficheImage(final String pURL, final int pPosX, final int pPosY, final Graphics g) {
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
    private void afficheImageRedim(final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX, pPosBasDroiteY, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImagePleinEcran(final String pURL, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 

    /**
     * Timer
     * @param pTemps (int temps du Thread.sleep : temps d'attente).
     */
    private synchronized void threadSleep(final int pTemps) {
         try {Thread.sleep(pTemps);} 
         catch (InterruptedException e) {e.printStackTrace();}
    }
}



