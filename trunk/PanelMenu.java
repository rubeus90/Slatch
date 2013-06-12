import javax.imageio.ImageIO;
import java.io.*;
import java.net.URISyntaxException;
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
    private boolean aMenuCredits;
    private boolean aSousMenuRapide1;
    private boolean aSousMenuRapide2;
    private int aNumeroMap;
    // Attributs des boutons//cb[0] à cb[3] boutons IA | cb[4] à cb[8] boutons Joueur
    private Faction aFaction;
    private int[] aEquipe;
    private String[] aNiveauIA;
    private JTextArea textArea = new JTextArea();  
    private FontMetrics fmBlackOps;
    private FontMetrics fmVisitor;
    private Font fontBlackOps;
    private Font fontVisitor;
    
    
    /*******************************************************************************************************************/
    /***  Parametres de la partie rapide                                                                            /***/
    /*******************************************************************************************************************/
    /***/   Equipe equipe0 = new Equipe(0);
    /***/   Equipe equipe1 = new Equipe(1);
    /***/   Equipe equipe2 = new Equipe(2);
    /***/   Equipe equipe3 = new Equipe(3);
    /***/   Equipe equipe4 = new Equipe(4);
    /***/ 
    /***/   //EQUIPE DES JOUEURS : DANS L'ORDRE : Joueur NEUTRE, Joueur1, Joueur2, Joueur3,Joueur4
    /***/   Equipe[] vEquipe = {equipe0, equipe1, equipe2, equipe3, equipe4};
    /***/ 
    /***/   //POur definir si un Joueur est un IA ou pas : DANS L'ORDRE : Joueur NEUTRE, Joueur1, Joueur2, Joueur3,Joueur4
    /***/   boolean[] vIA = {false,true,true,true,true};
    /***/  Faction[] vFaction = {Faction.NEUTRE,Faction.HUMAINS,Faction.ROBOTS,Faction.HUMAINS,Faction.HUMAINS};
    /***/  boolean dBrouillard = false;
    /*********************************************************************************************************************/
    
    /**
     * 
     */
    
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
        

       
        aEquipe = new int[4];
        for(int i=0;i<4;i++)
        {
            aEquipe[i] = i+1;
        }
        aNiveauIA = new String[4];
        for(int i=0;i<4;i++)
        {
            if(vIA[i+1]==false){aNiveauIA[i] ="Désactivé";}
            else if(vIA[i+1]==true){aNiveauIA[i] ="Facile";}
        }
    }
    
    @Override
    public void paintComponent (final Graphics g) 
    {
        fontBlackOps = Slatch.fonts.get("BlackOps").deriveFont(Font.PLAIN, this.getWidth()/30);
        fontVisitor = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/40);
        fmBlackOps = getFontMetrics(fontBlackOps);
        fmVisitor = getFontMetrics(fontVisitor);
        
        
        afficheImageRedim("wallpaper",0,0,this.getWidth(),this.getHeight(),g);
        Image trait = Slatch.aImages.get("trait");
        
        Image ok = Slatch.aImages.get("boutonok");
        Image retour = Slatch.aImages.get("boutonretour");
        
        g.setFont(fontVisitor);
        
        int hPolice = fmVisitor.getHeight();
        
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
            
            g.drawImage(trait, 0, 0, this.getWidth(), this.getHeight(), this);
            g.drawImage(slatch, this.getWidth()/2-2*vHauteurTitre, this.getHeight()/15, 4*vHauteurTitre,vHauteurTitre, this);
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
        
        else if(aMenuCredits)
        {      
            
        }
        
        else if(aMenuRapide)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            int tCadre = this.getWidth()/100;
            
            Image titrerapide = Slatch.aImages.get("titrerapide");
            Image chargerPartie = Slatch.aImages.get("boutonchargerpartie");
            Image nouvellePartie = Slatch.aImages.get("boutonnouvellepartie");
            g.drawImage(titrerapide, this.getWidth()/6, 33, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            
            if(aSousMenuRapide1)
            {   
               afficheImageRedim("noir80",0, this.getHeight()/4-2*tCadre,this.getWidth(), 3*this.getHeight()/4+2*tCadre,g);

               Image flechegauche = Slatch.aImages.get("flechegauche");
               Image flechedroite = Slatch.aImages.get("flechedroite");
               afficheImageRedim("noir", this.getWidth()/11-tCadre, this.getHeight()/4-tCadre,this.getWidth()/2+tCadre ,3*this.getHeight()/4+tCadre,g);
               //Affichage de la miniature de la carte
               afficheImageRedim(Slatch.ihm.aListeMap.get(aNumeroMap).getFichier(), this.getWidth()/11, this.getHeight()/4,this.getWidth()/2 ,3*this.getHeight()/4,g);
               g.drawImage(flechegauche, this.getWidth()/11, 3*this.getHeight()/4+2*tCadre, this.getHeight()/10,this.getHeight()/10, this);
               g.drawImage(flechedroite, this.getWidth()/2-this.getHeight()/10, 3*this.getHeight()/4+2*tCadre, this.getHeight()/10,this.getHeight()/10, this);
               g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-this.getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
             
               textArea.setPreferredSize(new Dimension(this.getWidth()/3,2*this.getHeight()/3));
               textArea.setOpaque(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFocusable(false);
                textArea.setEditable(false);
                textArea.setMargin(new Insets(this.getHeight()/4,this.getWidth()/2+3*tCadre,3*this.getHeight()/4,tCadre));
                textArea.setForeground(Color.WHITE);
                textArea.setFont(fontVisitor);
                textArea.setText("Nom : "+Slatch.ihm.aListeMap.get(aNumeroMap).getNom()+"\n\n"+
                                "Description : "+Slatch.ihm.aListeMap.get(aNumeroMap).getDescription()+"\n\n"+
                                "Conseil : "+Slatch.ihm.aListeMap.get(aNumeroMap).getConseil()+"\n\n"+
                                "Nombre de Joueurs : "+Slatch.ihm.aListeMap.get(aNumeroMap).getNbrJoueur()+"\n\n"+
                                "Taille : "+Slatch.ihm.aListeMap.get(aNumeroMap).getLongueur()+ " x "+Slatch.ihm.aListeMap.get(aNumeroMap).getLargeur());
                textArea.repaint();
                this.repaint();
                textArea.updateUI();
                this.updateUI();
               
            }
               
            else if(aSousMenuRapide2)
            {
                afficheImageRedim("noir80",0, this.getHeight()/4-2*tCadre,this.getWidth(), this.getHeight()- (this.getHeight()/4),g);
                g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
                
                g.setFont(fontBlackOps);
                g.setColor(Color.white);
                
                int n = Slatch.ihm.aListeMap.get(aNumeroMap).getNbrJoueur();
               
                g.drawString("Equipe",this.getWidth()/20,this.getHeight()/2-aHauteurBouton);
                g.drawString("Faction",this.getWidth()/20,this.getHeight()/2);
                g.drawString("IA",this.getWidth()/20,this.getHeight()/2+aHauteurBouton);
                
                Image on = Slatch.aImages.get("on");
                Image off = Slatch.aImages.get("off");
                
                
                int hR = fmVisitor.getHeight();
                g.setFont(fontVisitor);
                g.setColor(Color.white);
                
                
                
                for(int i = 0; i<n;i++)
                {
                    g.setFont(fontBlackOps);
                    
                    g.drawString("Joueur "+(i+1),(2+2*i)*this.getWidth()/10,this.getHeight()/4+tCadre);
                    
                    g.setFont(fontVisitor);
                    g.getFont().deriveFont(Font.PLAIN, this.getWidth()/35);
                    g.setFont(fontVisitor);
                    g.drawString(""+aEquipe[i],(2+2*i)*this.getWidth()/10+2*this.getHeight()/40,this.getHeight()/2-aHauteurBouton);
                    
                    g.drawString(vFaction[i+1].getNom(),(2+2*i)*this.getWidth()/10,this.getHeight()/2);
                    
                    g.drawString(""+aNiveauIA[i],(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton);
                    
                    //if(vIA[i+1]){g.drawImage(on,(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton-hR/2,this.getHeight()/40,this.getHeight()/40,this);}
                    //else{g.drawImage(off,(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton-hR/2,this.getHeight()/40,this.getHeight()/40,this);}
                }
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
                aMenuPrincipal = false;
                aMenuCredits = true;
                this.repaint();
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
                if(pY> 3*this.getHeight()/4+2*tCadre && pY< 3*this.getHeight()/4+2*tCadre+this.getHeight()/10 && pX>this.getWidth()/11 && pX<this.getWidth()/11+this.getHeight()/10)
                {
                    if(aNumeroMap<=0)
                    {aNumeroMap=Slatch.ihm.aListeMap.size()-1;}
                    else
                    {aNumeroMap=aNumeroMap-1;}
                    this.repaint();
                }
                
                // Clic FlecheDroite
                if(pY>3*this.getHeight()/4+2*tCadre && pY< 3*this.getHeight()/4+2*tCadre + this.getHeight()/10 && pX>this.getWidth()/2-this.getHeight()/10 && pX<this.getWidth()/2)
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
                   this.remove(textArea);
                   aSousMenuRapide2=true;
                   aSousMenuRapide1=false;
                   this.repaint();
                }
                
                // Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    this.remove(textArea);
                    aSousMenuRapide1 = false;
                    this.repaint();
                }
            }
            
            else if(aSousMenuRapide2)
            {
                int hR = fmVisitor.getHeight();
                
                for(int i =0;i<4;i++)
                {
                    //Clic sur l'équipe 
                    int SizeFaction = fmVisitor.stringWidth(""+vFaction[i+1].getNom());
                    int SizeEquipe = fmVisitor.stringWidth(""+aEquipe[i]);
                    int SizeNiveauIA = fmVisitor.stringWidth(""+aNiveauIA[i]);
                    if(pY>this.getHeight()/2-aHauteurBouton-hR && pY<this.getHeight()/2-aHauteurBouton && pX>(2+2*i)*this.getWidth()/10+2*this.getHeight()/40 && pX<(2+2*i)*this.getWidth()/10+2*this.getHeight()/40+SizeEquipe)
                    {
                        if(aEquipe[i]==4)
                        {
                            aEquipe[i]=0;
                        }
                        aEquipe[i]++;
                        

                        this.repaint();
                    }
                //Clic sur la faction
                    if(pY>this.getHeight()/2-hR && pY<this.getHeight()/2 && pX>(2+2*i)*this.getWidth()/10 && pX<(2+2*i)*this.getWidth()/10+SizeFaction)
                    {
                       if(vFaction[i+1].equals(Faction.HUMAINS)){ vFaction[i+1] = Faction.ROBOTS;}
                       else{ vFaction[i+1] = Faction.HUMAINS;}
                       this.repaint();
                    }
                    
                //Clic sur la check box du joueur (IA ou non)
                
                //     if(pY>this.getHeight()/2+aHauteurBouton-hR/2 && pY<this.getHeight()/2+aHauteurBouton-hR/2+this.getHeight()/40 && pX>(2+2*i)*this.getWidth()/10 && pX<(2+2*i)*this.getWidth()/10+this.getHeight()/40)
                //    {
                //         vIA[i+1] = !vIA[i+1];
                //         this.repaint();
                //     }
                    
                //Clic sur la difficulté de l'IA
                if(pY>this.getHeight()/2+aHauteurBouton-hR/2 && pY<this.getHeight()/2+aHauteurBouton && pX>(2+2*i)*this.getWidth()/10 && pX<(2+2*i)*this.getWidth()/10+SizeNiveauIA)
                    {
                       switch(aNiveauIA[i])
                       {
                           case("Désactivé") : aNiveauIA[i]="Facile";vIA[i+1] = true;break;
                           case("Facile") : aNiveauIA[i]="Moyen";break;
                           case("Moyen") : aNiveauIA[i]="Difficile";break;
                           case("Difficile") : aNiveauIA[i]="Légendaire";break;
                           case("Légendaire") : aNiveauIA[i]="Désactivé";vIA[i+1] = false;break;
                       }
                       this.repaint();
                    }
                }
                
                // Clic Bouton OK
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                {
                   for(int i=0;i<4;i++)
                   {
                       switch(aEquipe[i])
                       {
                           case(1) : vEquipe[i+1]=equipe1;break;
                           case(2) : vEquipe[i+1]=equipe2;break;
                           case(3) : vEquipe[i+1]=equipe3;break;
                           case(4) : vEquipe[i+1]=equipe4;break;
                       }
                   }
                    
                    Partie partieRapide = new Partie(20,30,Slatch.ihm.aListeMap.get(aNumeroMap),dBrouillard, vFaction,vEquipe,vIA,true);

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
                    
                    this.repaint();
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
                    this.setLayout(new BorderLayout());
                    

                    this.add(textArea, BorderLayout.NORTH);

                }
                
                //Clic Bouton Charger une Partie
                if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
                {
                    Partie partieRapide = new Partie();
                    Slatch.partie=partieRapide;
                    Moteur moteur = new Moteur();
                    Slatch.moteur=moteur;
                    
                    
                    
                    Slatch.ihm.passageModePartie();
                    //aSousMenuRapide2 = true;
                    //this.repaint();
                }
            }
        } 
    }
    
    
    /**
    * Affiche une image en fond d'ecran
    */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    }
}
