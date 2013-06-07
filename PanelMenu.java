import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.* ;
import javax.swing.*;
import javax.swing.JCheckBox;


public class PanelMenu extends JPanel
{  
    private int aMilieu;
    private int aHauteurBouton;
    private boolean aMenuPrincipal;
    private boolean aMenuCampagne;
    private boolean aMenuRapide;
    private boolean aSousMenuRapide1;
    private boolean aSousMenuRapide2;
    private int aNumeroMap;
    private JCheckBox cbJ4; 
    private JCheckBox cbJ4b; 
    private JCheckBox cbJ3; 
    private JCheckBox cbJ3b; 
    private JCheckBox cbJ2; 
    private JCheckBox cbJ2b; 
    private JCheckBox cbJ1; 
    private JCheckBox cbJ1b; 
    private boolean statecbJ1b = false;
    private boolean statecbJ1= false;
    private boolean statecbJ2b= false;
    private boolean statecbJ2= false;
    private boolean statecbJ3b= false;
    private boolean statecbJ3= false;
    private boolean statecbJ4b= false;
    private boolean statecbJ4= false;
    
    public PanelMenu()
    {
        super();
        this.setPreferredSize(new Dimension(800, 550));
        aMenuPrincipal = true;
        aMenuCampagne = false;
        aMenuRapide = false;
        aSousMenuRapide1 = false;
        aSousMenuRapide2 = false;
        aNumeroMap=0;
    }
    
    @Override
    public void paintComponent (final Graphics g) 
    {
        
        afficheImageRedim("wallpaper",0,0,this.getWidth(),this.getHeight(),g);
        Image ok = Slatch.aImages.get("boutonok");
        Image retour = Slatch.aImages.get("boutonretour");
        
        Font font = new Font("Helvetica", Font.BOLD, this.getWidth()/50);
        g.setFont(font);
        FontMetrics fm=getFontMetrics(font); 
        
        int hPolice = fm.getHeight();
        
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
            
            g.drawImage(slatch, this.getWidth()/2-2*vHauteurTitre, 33, 4*vHauteurTitre,vHauteurTitre, this);
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
            Image chargerCampagne = Slatch.aImages.get("boutonchargercampagne");
            Image nouvelleCampagne = Slatch.aImages.get("boutonnouvellecampagne");
            
            g.drawImage(titrecampagne, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(nouvelleCampagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(chargerCampagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
        }
        else if(aMenuRapide)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            
            Image titrerapide = Slatch.aImages.get("titrerapide");
            Image chargerPartie = Slatch.aImages.get("boutonchargerpartie");
            Image nouvellePartie = Slatch.aImages.get("boutonnouvellepartie");
            g.drawImage(titrerapide, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            
            if(aSousMenuRapide1)
            {
               Image cadre = Slatch.aImages.get("noir");
               Image map = Slatch.aImages.get(Slatch.ihm.aListeMap.get(aNumeroMap).getFichier());
               int tCadre = this.getWidth()/100;
               
               afficheImageRedim("noir80",0, this.getHeight()/4-2*tCadre,this.getWidth(), this.getHeight()/4+this.getWidth()/3+2*tCadre,g);
               g.setColor(Color.white);
               g.drawString("Nom : " +Slatch.ihm.aListeMap.get(aNumeroMap).getNom(), this.getWidth()/9+this.getWidth()/2,this.getHeight()/3);
               g.drawString("Description : "+Slatch.ihm.aListeMap.get(aNumeroMap).getDescription(),this.getWidth()/9+this.getWidth()/2 , this.getHeight()/3+2*hPolice );
               g.drawString("Conseil : "+Slatch.ihm.aListeMap.get(aNumeroMap).getConseil(),this.getWidth()/9+this.getWidth()/2 ,this.getHeight()/3+4*hPolice );
               g.drawString("Nombre de Joueurs : "+Slatch.ihm.aListeMap.get(aNumeroMap).getNbrJoueur(),this.getWidth()/9+this.getWidth()/2 ,this.getHeight()/3+6*hPolice );
               
               Image flechegauche = Slatch.aImages.get("flechegauche");
               Image flechedroite = Slatch.aImages.get("flechedroite");
               g.drawImage(cadre, this.getWidth()/11-tCadre, this.getHeight()/4-tCadre,this.getWidth()/2+2*tCadre ,this.getWidth()/3+2*tCadre, this);
               g.drawImage(map, this.getWidth()/11, this.getHeight()/4,this.getWidth()/2 ,this.getWidth()/3, this);
               g.drawImage(flechegauche, this.getWidth()/11, this.getHeight()/4 + this.getWidth()/3+tCadre, this.getHeight()/8,this.getHeight()/8+tCadre, this);
               g.drawImage(flechedroite, this.getWidth()/11+this.getWidth()/2-this.getHeight()/8, this.getHeight()/4 + this.getWidth()/3+tCadre, this.getHeight()/8,this.getHeight()/8+tCadre, this);
               g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-this.getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
                
            }
            
            else if(aSousMenuRapide2)
            {
                g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
                this.afficheCheckBox();
            }
            
            else
            {
                
                g.drawImage(nouvellePartie, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton, this);
                g.drawImage(chargerPartie, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton, this);
                
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
                aMenuCampagne = true;
                aMenuPrincipal = false;
                this.repaint();
            }
       
            //Clic Bouton Partie Rapide
            if(pY>33+ 3*this.getHeight()/8 && pY<33+ 3*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                aMenuRapide = true;
                aMenuPrincipal = false;
                this.repaint();
                //Slatch.ihm.ecranJeu();
            }
            
            //Clic Bouton Tutoriel
            if(pY>33+ 4*this.getHeight()/8 && pY<33+ 4*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                
            }
            
            //Clic Bouton Map Creator
            if(pY>33+ 5*this.getHeight()/8 && pY<33+ 5*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                Slatch.maps = new CreationMaps();
            }
            
            //Clic Bouton Credits
            if(pY>33+ 6*this.getHeight()/8 && pY<33+ 6*this.getHeight()/8+aHauteurBouton && pX>this.getWidth()/2-2*aHauteurBouton && pX< this.getWidth()/2+2*aHauteurBouton)
            {
                
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
                Slatch.campagne = new Campagne();
                Slatch.campagne.createDialogue();
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
                int tCadre = this.getWidth()/100;
                // Clic FlecheGauche
                if(pY> this.getHeight()/4 + this.getWidth()/3 +tCadre && pY< this.getHeight()/4 + this.getWidth()/3 + this.getHeight()/8 +tCadre && pX>this.getWidth()/11 && pX<this.getWidth()/11+this.getHeight()/8)
                {
                    if(aNumeroMap<=0)
                    {aNumeroMap=Slatch.ihm.aListeMap.size()-1;}
                    else
                    {aNumeroMap=aNumeroMap-1;}
                    this.repaint();
                }
                
                // Clic FlecheDroite
                if(pY>this.getHeight()/4 + this.getWidth()/3 +tCadre && pY< this.getHeight()/4 + this.getWidth()/3 + this.getHeight()/8 +tCadre && pX>this.getWidth()/11+this.getWidth()/2-this.getHeight()/8 && pX<this.getWidth()/11+this.getWidth()/2)
                {
                    if(aNumeroMap>=Slatch.ihm.aListeMap.size()-1)
                    {aNumeroMap=0;}
                    else
                    {aNumeroMap=aNumeroMap+1;}
                    this.repaint();
                }
                
                // Clic Bouton OK
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                {
                   aSousMenuRapide2=true;
                   aSousMenuRapide1=false;
                   this.repaint();
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
                //if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                {
                   Equipe equipe0 = new Equipe(0);
                   Equipe equipe1 = new Equipe(1);
                   Equipe equipe2 = new Equipe(2);
                   Equipe equipe3 = new Equipe(3);
                   Equipe equipe4 = new Equipe(4);
                    
                    //EQUIPE DES JOUEURS : DANS L'ORDRE : Joueur NEUTRE, Joueur1, Joueur2, Joueur3,Joueur4
                    Equipe[] vEquipe = {equipe0, equipe1, equipe2, equipe3, equipe4};
                    
                    //POur definir si un Joueur est un IA ou pas : DANS L'ORDRE : Joueur NEUTRE, Joueur1, Joueur2, Joueur3,Joueur4
                    boolean[] vIA = {false,false,false,false,false};
                    
                    Partie partieRapide = new Partie(20,30,"Maps/"+Slatch.ihm.aListeMap.get(aNumeroMap).getFichier()+".txt",false,vEquipe,vIA);

                    Slatch.partie=partieRapide;
                    
                    Moteur moteur = new Moteur();
                    Slatch.moteur=moteur;
                    
                    
                    
                    Slatch.ihm.passageModePartie();
                    
                    
                    if(Slatch.partie.getBrouillard()){
                        moteur.Brouillard();
                    }
                    
                    if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    {
                        StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
                    }
                    
                    /*if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    {
                        System.out.println("ICI");
                        AIMaster.joueTour(Slatch.partie.getJoueurActuel());
                    }*/
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
    
    public void afficheCheckBox()
    {
        cbJ4 = 	new JCheckBox("Joueur", statecbJ4);
        cbJ4b = 	new JCheckBox("IA", statecbJ4b);
        cbJ3 = 	new JCheckBox("Joueur", statecbJ3);
        cbJ3b = 	new JCheckBox("IA", statecbJ3b);
        cbJ2 = 	new JCheckBox("Joueur", statecbJ2);
        cbJ3b = 	new JCheckBox("IA", statecbJ3b);
        cbJ1 = 	new JCheckBox("Joueur", statecbJ1);
        cbJ1b = 	new JCheckBox("IA", statecbJ1b);   
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
