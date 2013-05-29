import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.* ;
import javax.swing.*;

public class PanelMenu extends JPanel
{  
    private int aMilieu;
    private int aHauteurBouton;
    private boolean aMenuPrincipal;
    private boolean aMenuCampagne;
    private boolean aMenuRapide;
    private boolean aSousMenuRapide1;
    private boolean aSousMenuRapide2;
    
    public PanelMenu()
    {
        super();
        this.setPreferredSize(new Dimension(800, 550));
        aMenuPrincipal = true;
        aMenuCampagne = false;
        aMenuRapide = false;
        aSousMenuRapide1 = false;
        aSousMenuRapide2 = false;
    }
    
    @Override
    public void paintComponent (final Graphics g) 
    {
        
        afficheImageRedim("plaine0",0,0,this.getWidth(),this.getHeight(),g);
        
        if(aMenuPrincipal)
        {
            aHauteurBouton = this.getHeight()/10;  
            int vHauteurTitre = this.getHeight()/8;
            
            Image slatch = Slatch.aImages.get("slatch");
            Image campagne = Slatch.aImages.get("boutoncampagne");
            Image rapide = Slatch.aImages.get("boutonrapide");
            Image tutoriel = Slatch.aImages.get("boutontutoriel");
            Image mapcreator = Slatch.aImages.get("boutonmapcreator");
            Image credits = Slatch.aImages.get("boutoncredits");
            
            g.drawImage(slatch, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(campagne, this.getWidth()/2-2*aHauteurBouton, 33+ this.getHeight()/4, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(rapide, this.getWidth()/2-2*aHauteurBouton, 33+ 3*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(tutoriel, this.getWidth()/2-2*aHauteurBouton, 33+ 4*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(mapcreator, this.getWidth()/2-2*aHauteurBouton, 33+ 5*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(credits, this.getWidth()/2-2*aHauteurBouton, 33+ 6*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
        }
        else if(aMenuCampagne)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            
            Image titrecampagne = Slatch.aImages.get("titrecampagne");
            Image campagne = Slatch.aImages.get("boutoncampagne");
            
            g.drawImage(titrecampagne, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(campagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(campagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(campagne, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/12, this);
        }
        else if(aMenuRapide)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            
            Image titrerapide = Slatch.aImages.get("titrerapide");
            Image campagne = Slatch.aImages.get("boutoncampagne");//A Remplacer par retour
            g.drawImage(titrerapide, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(campagne, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/12, this);//A Remplacer par retour
            
            if(aSousMenuRapide1)
            {
                g.drawImage(campagne, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/12, this);//A Remplacer par OK
                
            }
            
            else if(aSousMenuRapide2)
            {
                g.drawImage(campagne, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/12, this);//A Remplacer par OK
            }
            
            else
            {
                
                g.drawImage(campagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton, this);
                g.drawImage(campagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton, this);
                
            }
        }
        
    }
    
    public void coordsurvolBouton(int pX, int pY)
    {

    }
    
    public void coordclickBouton(int pX, int pY)
    {
        if(aMenuPrincipal)
        {
            //Clic Bouton Campagne
            if(pY>33+ this.getHeight()/4 && pY<33+this.getHeight()/4+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                System.out.println("Campagne");
                aMenuCampagne = true;
                aMenuPrincipal = false;
                this.repaint();
            }
       
            //Clic Bouton Partie Rapide
            if(pY>33+ 3*this.getHeight()/8 && pY<33+ 3*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                System.out.println("Partie Rapide");
                aMenuRapide = true;
                aMenuPrincipal = false;
                this.repaint();
                //Slatch.ihm.ecranJeu();
            }
            
            //Clic Bouton Tutoriel
            if(pY>33+ 4*this.getHeight()/8 && pY<33+ 4*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                System.out.println("Tutoriel");
            }
            
            //Clic Bouton Map Creator
            if(pY>33+ 5*this.getHeight()/8 && pY<33+ 5*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                System.out.println("Map Creator");
            }
            
            //Clic Bouton Credits
            if(pY>33+ 6*this.getHeight()/8 && pY<33+ 6*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                System.out.println("Crédits");
            }
        }
        else if(aMenuCampagne)
        {
            // Clic Bouton Retour
            if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
            {
                aMenuPrincipal = true;
                aMenuCampagne = false;
                this.repaint();
            }
            
            // Clic Bouton Nouvelle Campagne
            if(pY>33+ this.getHeight()/4 && pY<33+ this.getHeight()/4+aHauteurBouton && this.getWidth()/2-3*aHauteurBouton<pX && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                
            }
            
            //Clic Bouton Charger une Campagne
            if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                
            }
            
        }
        else if(aMenuRapide)
        {  
            if(aSousMenuRapide1)
            {
                // Clic Bouton OK
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                {
                    System.out.println("Yaaaaaaaaaaaahouuuuuuuuuu");
//                     partie = new Partie(20,30,"Maps/doublevai.txt");
//                     moteur = new Moteur();
//                     Slatch.ihm.ecranJeu();
                }
                
                // Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    aSousMenuRapide1 = false;
                    this.repaint();
                }
            }
            
            else if(aSousMenuRapide2)
            {
                // Clic Bouton OK
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {

                }
                
                // Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    aSousMenuRapide2 = false;
                    this.repaint();
                }
            }
            
            else
            {
                // Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    aMenuPrincipal = true;
                    aMenuRapide = false;
                    this.repaint();
                }
                
                // Clic Bouton Nouvelle Partie
                if(pY>33+ this.getHeight()/4 && pY<33+ this.getHeight()/4+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
                {
                    aSousMenuRapide1 = true;
                    this.repaint();
                }
                
                //Clic Bouton Charger une Partie
                if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
                {
                    aSousMenuRapide2 = true;
                    this.repaint();
                }
            }
            
        } 
    }
    
    /**
     * Affichage du sous menu de Partie Rapide
     */
    public void affMenu1(final Graphics g)
    {
        Font font = new Font("Serif", Font.BOLD, 25*this.getWidth()/1000);
        g.setFont(font);
        FontMetrics fm=getFontMetrics(font);
        
        g.drawString("", 1, 33);
    }
    
    /**
    * Affiche une image en fond d'ecran
    */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    }
}
