import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelMenu extends JPanel
{  
    private int aHauteurBouton;
    private boolean aMenuPrincipal;
    private boolean aMenuCampagne;
    private boolean aMenuRapide;
    private boolean aMenuTuto;
    private boolean aSousMenuTab1;
    private boolean aSousMenuTab2;
    private boolean aMenuCredits;
    private boolean aSousMenuRapide1;
    private boolean aSousMenuRapide2;
    private boolean aMenuChoixTuto;
    private boolean aMenuParametres; 
    
    private int aNumeroMap;
    
    // Attributs des boutons//cb[0] à cb[3] boutons IA | cb[4] à cb[8] boutons Joueur
    private int[] aIntEquipe;
    private TypeIA[] aNiveauIA;
    private boolean[] aIA;
    private Equipe[] aEquipe;
    private Faction[] aFaction;
    private JTextArea textArea = new JTextArea();  
    private FontMetrics fmBlackOps;
    private FontMetrics fmVisitor;
    private Font fontBlackOps;
    private Font fontVisitor;
    private HashMap<String,String> aImages;
   
   
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
        aMenuChoixTuto = false;
        aMenuParametres = false;
        aMenuTuto = false;
        aSousMenuTab1 = false;
        aSousMenuTab2 = false;
        aNumeroMap=0;
        aIntEquipe = new int[4];
        aNiveauIA = new TypeIA[4];
        aIA = new boolean[5]; // 4 joueurs + 1 joueurs neutres
        aEquipe = new Equipe[5]; // 4 joueurs + 1 joueurs neutres
        aFaction = new Faction[5]; // 4 joueurs + 1 joueurs neutres
        
        //Valeur par default dans le menu de parametre d'une nouvelle partie pour l'IA
        aIA[0]=false;
        aNiveauIA[0] = TypeIA.DESACTIVEE;
        aIA[1]=false;
        aNiveauIA[1] =TypeIA.AGGRESSIVE;
        aIA[2]=true;
        aNiveauIA[2] =TypeIA.AGGRESSIVE;
        aIA[3]=true;
        aNiveauIA[3] =TypeIA.AGGRESSIVE;
        aIA[4]=true;
        
        //Valeur par default dans le menu de parametre d'une nouvelle partie pour les equipes
        for(int i=0;i<4;i++)
        {
            aIntEquipe[i] = i+1;
        }
        
        //Valeur par default des faction
        aFaction[0] = Faction.NEUTRE;
        aFaction[1] = Faction.HUMAINS;
        aFaction[2] = Faction.ROBOTS;
        aFaction[3] = Faction.ROBOTS;
        aFaction[4] = Faction.ROBOTS;
        

       
        
        aImages = new HashMap<String,String>();
        
        aImages.put("boutonchargercampagne","");
        aImages.put("boutonnouvellecampagne","");
        aImages.put("boutonnouvellepartie","");
        aImages.put("boutonchargerpartie","");
        aImages.put("boutondeplacement","");
        aImages.put("boutoncapture","");
        aImages.put("boutonattaque","");
        aImages.put("boutoninterface","");
        aImages.put("boutonterrain","");
        aImages.put("boutonachat","");
        aImages.put("boutonexp","");
        aImages.put("boutontabunites","");
        aImages.put("boutonregles","");
        aImages.put("engrenage","");
        aImages.put("boutoncampagne","");
        aImages.put("boutonrapide","");
        aImages.put("boutontutoriel","");
        aImages.put("boutonmapcreator","");
        aImages.put("boutoncredits","");
    }
    
    public void departMenuTuto()
    {
        aMenuPrincipal = false;
        aMenuCampagne = false;
        aMenuRapide = false;
        aMenuTuto = true;
        aSousMenuRapide1 = false;
        aSousMenuRapide2 = false;
    }
    
    
    
    @Override
    public void paintComponent (final Graphics g) 
    {
        fontBlackOps = Slatch.fonts.get("BlackOps").deriveFont(Font.PLAIN, this.getWidth()/30);
        fontVisitor = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/55);
        Font fontVisitorbis = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/70);
        fmBlackOps = getFontMetrics(fontBlackOps);
        fmVisitor = getFontMetrics(fontVisitor);
        FontMetrics fmVisitorbis;
        fmVisitorbis = getFontMetrics(fontVisitorbis);
        
        afficheImageRedim("wallpaper",0,0,this.getWidth(),this.getHeight(),g);
        Image trait = Slatch.aImages.get("trait");
        
        Image ok = Slatch.aImages.get("boutonok");
        Image retour = Slatch.aImages.get("boutonretour");
        
        g.setFont(fontVisitorbis);
        
        int hPolice = fmVisitor.getHeight();
        
        
        
        if(aMenuPrincipal)
        {
            aHauteurBouton = this.getHeight()/10;  
            int vHauteurTitre = this.getHeight()/8;
            
            g.drawImage(trait, 0, 0, this.getWidth(), this.getHeight(), this);
            g.drawImage(Slatch.aImages.get("slatch"), this.getWidth()/2-2*vHauteurTitre, this.getHeight()/15, 4*vHauteurTitre,vHauteurTitre, this);
            g.drawImage(Slatch.aImages.get("boutoncampagne"+aImages.get("boutoncampagne")), this.getWidth()/2-2*aHauteurBouton, 33+ this.getHeight()/4, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("boutonrapide"+aImages.get("boutonrapide")), this.getWidth()/2-2*aHauteurBouton, 33+ 3*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("boutontutoriel"+aImages.get("boutontutoriel")), this.getWidth()/2-2*aHauteurBouton, 33+ 4*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("boutonmapcreator"+aImages.get("boutonmapcreator")), this.getWidth()/2-2*aHauteurBouton, 33+ 5*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("boutoncredits"+aImages.get("boutoncredits")), this.getWidth()/2-2*aHauteurBouton, 33+ 6*this.getHeight()/8, 4*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("engrenage"+aImages.get("engrenage")), this.getWidth()-aHauteurBouton/2-this.getHeight()/10,aHauteurBouton/2,this.getHeight()/10,this.getHeight()/10, this);            
            
        }
        
        else if(aMenuCampagne)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            
            Image titrecampagne = Slatch.aImages.get("titrecampagne");
            
            g.drawImage(titrecampagne, 0, 0, this.getWidth(), this.getHeight()*2/5, this);
            g.drawImage(Slatch.aImages.get("boutonnouvellecampagne"+aImages.get("boutonnouvellecampagne")), this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton+10, this);
            g.drawImage(Slatch.aImages.get("boutonchargercampagne"+aImages.get("boutonchargercampagne")), this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton+10, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
        }
        
        else if(aMenuCredits)
        {      
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            g.drawImage(Slatch.aImages.get("credits"), 0, 0, this.getWidth(), this.getHeight(), this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
        }
        
        else if(aMenuTuto)
        {
            g.setFont(fontBlackOps);
            g.setColor(Color.white);
            if(aSousMenuTab1)
            {
                int tailleFleche = this.getWidth()/15;
                int marge = this.getWidth()/20;
                afficheImageRedim("noir80",0,0,this.getWidth(), this.getHeight(),g);
                g.drawString("Les Unités",this.getWidth()/2-fmBlackOps.stringWidth("Les Unités")/2,this.getHeight()/8);
                afficheImageRedim("tableauunite1",this.getWidth()/5,this.getHeight()/5,4*this.getWidth()/5, 4*this.getHeight()/5,g);
                g.drawImage(Slatch.aImages.get("flechegauche"), this.getWidth()-2*tailleFleche-2*marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this);
                g.drawImage(Slatch.aImages.get("flechedroite"), this.getWidth()-tailleFleche-marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this);
            }
            else if(aSousMenuTab2)
            {
                int tailleFleche = this.getWidth()/15;
                int marge = this.getWidth()/20;
                afficheImageRedim("noir80",0,0,this.getWidth(), this.getHeight(),g);
                g.drawString("Les Rapports de Force",this.getWidth()/2-fmBlackOps.stringWidth("Les Rapports de Force")/2,this.getHeight()/8);
                afficheImageRedim("tableauunite2",this.getWidth()/5,this.getHeight()/5,4*this.getWidth()/5, 4*this.getHeight()/5,g);
                g.drawImage(Slatch.aImages.get("flechegauche"), this.getWidth()-2*tailleFleche-2*marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this);
                g.drawImage(Slatch.aImages.get("flechedroite"), this.getWidth()-tailleFleche-marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this); 
            }
            else
            {
                int vSize = this.getHeight()/10;
                int vRatio = vSize*(718/159);
                int vGauche = this.getWidth()/2-vRatio-vSize/2;
                int vDroite = this.getWidth()-vGauche-vRatio;
                
                g.drawImage(Slatch.aImages.get("titretutoriel"), 0, 0, this.getWidth(), this.getHeight()*2/5, this);
                g.drawImage(Slatch.aImages.get("boutonregles"+aImages.get("boutonregles")), vGauche, 3*this.getHeight()/10, vRatio, vSize, this);            
                g.drawImage(Slatch.aImages.get("boutondeplacement"+aImages.get("boutondeplacement")), vGauche, 4*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutoncapture"+aImages.get("boutoncapture")), vGauche, 5*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutonattaque"+aImages.get("boutonattaque")), vGauche, 6*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutoninterface"+aImages.get("boutoninterface")), vGauche, 7*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutonterrain"+aImages.get("boutonterrain")), vDroite, 3*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutonachat"+aImages.get("boutonachat")), vDroite, 4*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutonexp"+aImages.get("boutonexp")), vDroite, 5*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(Slatch.aImages.get("boutontabunites"+aImages.get("boutontabunites")), vDroite, 6*this.getHeight()/10, vRatio, vSize, this);
                g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            }
        }
        
        else if(aMenuRapide)
        {
            aHauteurBouton = this.getHeight()/9;
            int vHauteurTitre = this.getHeight()/8;
            int tCadre = this.getWidth()/100;
            
            Image titrerapide = Slatch.aImages.get("titrerapide");
            g.drawImage(titrerapide, 0, 0, this.getWidth(), this.getHeight()*2/5, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            
            if(aSousMenuRapide1)
            {   
               afficheImageRedim("noir80",0, this.getHeight()/4-2*tCadre,this.getWidth(), 3*this.getHeight()/4+2*tCadre,g);
               
               afficheImageRedim("noir", this.getWidth()/11-tCadre, this.getHeight()/4-tCadre,this.getWidth()/2+tCadre ,3*this.getHeight()/4+tCadre,g);
               //Affichage de la miniature de la carte
               afficheImageRedim(Slatch.ihm.aListeMap.get(aNumeroMap).getFichier(), this.getWidth()/11, this.getHeight()/4,this.getWidth()/2 ,3*this.getHeight()/4,g);
               
               Image flechegauche,flechedroite;
               g.drawImage(Slatch.aImages.get("flechegauche"), this.getWidth()/11, 3*this.getHeight()/4+2*tCadre, this.getHeight()/10,this.getHeight()/10, this);
               g.drawImage(Slatch.aImages.get("flechedroite"), this.getWidth()/2-this.getHeight()/10, 3*this.getHeight()/4+2*tCadre, this.getHeight()/10,this.getHeight()/10, this);
               g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-this.getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);

             
               textArea.setPreferredSize(new Dimension(this.getWidth()/3,2*this.getHeight()/3));
               textArea.setOpaque(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFocusable(false);
                textArea.setEditable(false);
                textArea.setMargin(new Insets(this.getHeight()/4,this.getWidth()/2+3*tCadre,3*this.getHeight()/4,tCadre));
                textArea.setForeground(Color.WHITE);
                textArea.setFont(fontVisitorbis);
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
                g.drawString("Brouillard",this.getWidth()/20,this.getHeight()/2+2*aHauteurBouton);
                g.drawString("Animation",10*this.getWidth()/20,this.getHeight()/2+2*aHauteurBouton);
                
                
                int hR = fmVisitor.getHeight();
                g.setColor(Color.white);
                
                   
                for(int i = 0; i<n;i++)
                {
                    g.setFont(fontBlackOps);
                    
                    switch(i){
                        case(0):{g.setColor(Color.blue);break;}
                        case(1):{g.setColor(Color.yellow);break;}
                        case(2):{g.setColor(Color.red);break;}
                        case(3):{g.setColor(Color.green);break;}
                    }
                    
                    g.drawString("Joueur "+(i+1),(2+2*i)*this.getWidth()/10,this.getHeight()/4+tCadre);    
                       
                    g.setColor(Color.white);
                    
                    g.setFont(fontVisitor);
                    
                    g.drawString(""+aIntEquipe[i],(2+2*i)*this.getWidth()/10+2*this.getHeight()/40,this.getHeight()/2-aHauteurBouton);
                    
                    if(aFaction[i+1].getNom()=="humains")
                    {g.drawString("Humain",(2+2*i)*this.getWidth()/10,this.getHeight()/2);}
                    if(aFaction[i+1].getNom()=="robots")
                    {g.drawString("Mecadroïdes",(2+2*i)*this.getWidth()/10,this.getHeight()/2);}
                    
                    g.drawString(""+aNiveauIA[i],(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton);

                    
                }
                String aStrBrouillard;
                String aStrAnimation;
                if(Slatch.ihm.getValBrouillard() == true){aStrBrouillard ="Activé";}
                else{aStrBrouillard ="Désactivé";}
                g.drawString(""+aStrBrouillard,(3)*this.getWidth()/10,this.getHeight()/2+2*aHauteurBouton);
                if(Slatch.ihm.getValAnimation() == true){aStrAnimation ="Activé";}
                else{aStrAnimation ="Désactivé";}
                g.drawString(""+aStrAnimation,(7)*this.getWidth()/10,this.getHeight()/2+2*aHauteurBouton);
            }                      
            else
            {   
                g.drawImage(Slatch.aImages.get("boutonnouvellepartie"+aImages.get("boutonnouvellepartie")), this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton, aHauteurBouton+10, this);
                g.drawImage(Slatch.aImages.get("boutonchargerpartie"+aImages.get("boutonchargerpartie")), this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton, aHauteurBouton+10, this);   
            }       
        }
        else if(aMenuParametres)
        {
            afficheImageRedim("noir80",0, 2*this.getHeight()/7,this.getWidth(), 5*this.getHeight()/7,g);
            g.setColor(Color.white);
            g.drawImage(Slatch.aImages.get("titreparametres"), 0, 0, this.getWidth(), this.getHeight()*2/5, this);
            g.setFont(fontBlackOps);
            g.drawString("Brouillard",this.getWidth()/3,3*this.getHeight()/7);
            g.drawString("Animation",this.getWidth()/3,4*this.getHeight()/7);
            g.setFont(fontVisitor);
            String aStrBrouillard;
            String aStrAnimation;
            if(Slatch.ihm.getValBrouillard() == true){aStrBrouillard ="Activé";}
            else{aStrBrouillard ="Désactivé";}
            g.drawString(""+aStrBrouillard,this.getWidth()/3 + fmBlackOps.stringWidth("Brouillard    "),3*this.getHeight()/7);
            
            if(Slatch.ihm.getValAnimation() == true){aStrAnimation ="Activé";}
            else{aStrAnimation ="Désactivé";}
            
            g.drawString(""+aStrAnimation,this.getWidth()/3 + fmBlackOps.stringWidth("Animation    "),4*this.getHeight()/7);
            g.drawImage(ok, this.getWidth()-10-this.getHeight()/6, this.getHeight()-10-this.getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
            
            g.setFont(fontVisitorbis);
            g.drawString("Cela ne désactive pas le brouillard du mode campagne",this.getWidth()/2-fmVisitorbis.stringWidth("éela ne désactive pas le brouillard du mode campagne")/2,10*this.getHeight()/21);
        }
        
        else if(aMenuChoixTuto){
            aHauteurBouton = this.getHeight()/10;
            g.drawImage(Slatch.aImages.get("choixtuto"), 0, 0, this.getWidth(), this.getHeight()*3/5, this);
            g.drawImage(Slatch.aImages.get("choixtutooui"+aImages.get("choixtutooui")), this.getWidth()/2-2*aHauteurBouton, 33+ 2*this.getHeight()/8, 7/2*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(Slatch.aImages.get("choixtutonon"+aImages.get("choixtutonon")), this.getWidth()/2-2*aHauteurBouton, 33+ 3*this.getHeight()/8, 7/2*aHauteurBouton,aHauteurBouton, this);
            g.drawImage(retour, 10, this.getHeight()-10-getHeight()/12, this.getHeight()/6,this.getHeight()/18, this);
        }  
    }
    
    public void coordclickBouton(int pX, int pY)
    {
        if(aMenuPrincipal)
        {          
            //Clic Bouton Parametres
            if(pY>aHauteurBouton/2 && pY<aHauteurBouton/2+this.getHeight()/10 && pX>this.getWidth()-aHauteurBouton/2-this.getHeight()/10 && pX<this.getWidth()-aHauteurBouton/2)
            {
                aMenuParametres = true;
                aMenuPrincipal = false;
                this.repaint();
            }
            
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
                aMenuTuto = true;
                aMenuPrincipal = false;
                this.repaint();
                //Tutoriel tuto = new TutorielDeplacement("menu");
                //Slatch.ihm.passageModeTuto(tuto);
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
                aMenuCampagne = false;
                aMenuChoixTuto = true;
                this.repaint();
            }
            
            //Clic Bouton Charger une Campagne
            if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                Slatch.campagne = new Campagne(Slatch.ihm.aListeMission);
                Slatch.campagne.chargerCampagne(Slatch.ihm.aNiveau);
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
                    
                    aNumeroMap--;
                    if(aNumeroMap<0)
                        aNumeroMap=Slatch.ihm.aListeMap.size()-1;
                        
                    while(Slatch.ihm.aListeMap.get(aNumeroMap).isVerrouille()){
                        aNumeroMap--;
                        if(aNumeroMap>=Slatch.ihm.aListeMap.size()-1)
                            aNumeroMap=0;
                    }
                    
                    this.repaint();
                }
                
                // Clic FlecheDroite
                if(pY>3*this.getHeight()/4+2*tCadre && pY< 3*this.getHeight()/4+2*tCadre + this.getHeight()/10 && pX>this.getWidth()/2-this.getHeight()/10 && pX<this.getWidth()/2)
                {
                    aNumeroMap++;
                    if(aNumeroMap>=Slatch.ihm.aListeMap.size()-1)
                        aNumeroMap=0;
                        
                    while(Slatch.ihm.aListeMap.get(aNumeroMap).isVerrouille()){
                        aNumeroMap++;
                        if(aNumeroMap>=Slatch.ihm.aListeMap.size()-1)
                            aNumeroMap=0;
                    }
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
                    
                    int SizeFaction = fmVisitor.stringWidth(""+aFaction[i+1].getNom());
                    int SizeEquipe = fmVisitor.stringWidth(""+aIntEquipe[i]);
                    int SizeNiveauIA = fmVisitor.stringWidth(""+aNiveauIA[i]);
                    
                    //Clic sur l'équipe 
                    if(pY>this.getHeight()/2-aHauteurBouton-hR && pY<this.getHeight()/2-aHauteurBouton && pX>(2+2*i)*this.getWidth()/10+2*this.getHeight()/40 && pX<(2+2*i)*this.getWidth()/10+2*this.getHeight()/40+SizeEquipe)
                    {
                        if(aIntEquipe[i]==4)
                        {
                            aIntEquipe[i]=0;
                        }
                        aIntEquipe[i]++;
                        

                        this.repaint();
                    }
                    
                    //Clic sur la faction
                    if(pY>this.getHeight()/2-hR && pY<this.getHeight()/2 && pX>(2+2*i)*this.getWidth()/10 && pX<(2+2*i)*this.getWidth()/10+SizeFaction)
                    {
                       if(aFaction[i+1].equals(Faction.HUMAINS)){ aFaction[i+1] = Faction.ROBOTS;}
                       else{ aFaction[i+1] = Faction.HUMAINS;}
                       this.repaint();
                    }
                                        
                    //Clic sur la difficulté de l'IA
                    if(pY>this.getHeight()/2+aHauteurBouton-hR && pY<this.getHeight()/2+aHauteurBouton && pX>(2+2*i)*this.getWidth()/10 && pX<(2+2*i)*this.getWidth()/10+SizeNiveauIA)
                    {
                           switch(aNiveauIA[i])
                           {
                               case DESACTIVEE: aNiveauIA[i]=TypeIA.AGGRESSIVE;aIA[i+1] = true;break;
                               case AGGRESSIVE : aNiveauIA[i]=TypeIA.REFLECHIE;aIA[i+1] = true;break;
                               case REFLECHIE : aNiveauIA[i]=TypeIA.DESACTIVEE;aIA[i+1] = false;break;
                           }
                           this.repaint();
                    }
      
                }
                String aStrBrouillard;
                String aStrAnimation;
                if(Slatch.ihm.getValBrouillard() == true){aStrBrouillard ="Activé";}
                else{aStrBrouillard ="Désactivé";}
                int SizeBrouillard = fmVisitor.stringWidth(""+aStrBrouillard);
                if(Slatch.ihm.getValAnimation() == true){aStrAnimation ="Activé";}
                else{aStrAnimation ="Désactivé";}
                int SizeAnimation = fmVisitor.stringWidth(""+aStrAnimation);
                
                //Click sur le brouillard
                if(pY>this.getHeight()/2+2*aHauteurBouton-hR && pY<this.getHeight()/2+2*aHauteurBouton && pX>(3)*this.getWidth()/10 && pX<(3)*this.getWidth()/10+SizeBrouillard)
                {
                   if(aStrBrouillard.equals("Activé")){aStrBrouillard="Désactive";Slatch.ihm.setValBrouillard(false);}
                   else{aStrBrouillard="Activé"; Slatch.ihm.setValBrouillard(true);}
                   this.repaint();
                }
                
                //Click sur les animations
                if(pY>this.getHeight()/2+2*aHauteurBouton-hR && pY<this.getHeight()/2+2*aHauteurBouton && pX>(7)*this.getWidth()/10 && pX<(7)*this.getWidth()/10+SizeAnimation)
                {
                   if(aStrAnimation.equals("Activé")){aStrAnimation="Desactivé";Slatch.ihm.setValAnimation(false);}
                   else{aStrAnimation="Activé"; Slatch.ihm.setValAnimation(true);}
                   this.repaint();
                }
                
                // Clic Bouton OK
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                { 
                   Equipe equipe0 = new Equipe(0);
                   Equipe equipe1 = new Equipe(1);
                   Equipe equipe2 = new Equipe(2);
                   Equipe equipe3 = new Equipe(3);
                   Equipe equipe4 = new Equipe(4);
                   aEquipe[0] = equipe0;
                   aEquipe[1] = equipe1;
                   aEquipe[2] = equipe2;
                   aEquipe[3] = equipe3;
                    
                    for(int i=0;i<4;i++)
                   {
                       switch(aIntEquipe[i])
                       {
                           case(1) : aEquipe[i+1]=equipe1;break;
                           case(2) : aEquipe[i+1]=equipe2;break;
                           case(3) : aEquipe[i+1]=equipe3;break;
                           case(4) : aEquipe[i+1]=equipe4;break;
                       }
                   }
                    
                   Partie partieRapide = new Partie(50,Slatch.ihm.aListeMap.get(aNumeroMap),Slatch.ihm.getValBrouillard(), aFaction,aEquipe,aIA,Slatch.ihm.getValAnimation(),aNiveauIA);

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
                    String home = System.getProperty("user.home");                    
                    File file = new File(home + "/.slatch/config/sauvegarde.txt");
                    
                    if(file.exists()){
                        Partie partieRapide = new Partie();
                        Slatch.partie=partieRapide;
                        Moteur moteur = new Moteur();
                        Slatch.moteur=moteur;
                        Slatch.ihm.passageModePartie();
                        if(Slatch.partie.getBrouillard()){
                            moteur.Brouillard();
                        }
                    }                    
                }
            }
        } 
            else if(aMenuCredits)
            {
                
                // Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    aMenuPrincipal = true;
                    aMenuCredits = false;
                    this.repaint();
                }
                
                
            }
            
            else if(aMenuTuto)
            {
                if(aSousMenuTab1)
                {
                    int tailleFleche = this.getWidth()/15;
                    int marge = this.getWidth()/20;
                    //Clic fleche gauche
                    if(pX>this.getWidth()-2*tailleFleche-2*marge && pX<this.getWidth()-2*tailleFleche-2*marge+tailleFleche && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
                    {
                        aSousMenuTab1 = false;
                        this.repaint();
                    }
                    //Clic fleche droite
                    if(pX>this.getWidth()-tailleFleche-marge && pX<this.getWidth()-marge && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
                    {
                        aSousMenuTab2 = true;
                        aSousMenuTab1 = false;
                        this.repaint();
                    }
                    
                }
                else if(aSousMenuTab2)
                {
                    int tailleFleche = this.getWidth()/15;
                    int marge = this.getWidth()/20;
                    //Clic fleche gauche
                    if(pX>this.getWidth()-2*tailleFleche-2*marge && pX<this.getWidth()-2*tailleFleche-2*marge+tailleFleche && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
                    {
                        aSousMenuTab1 = true;
                        aSousMenuTab2 = false;
                        this.repaint();
                    }
                    //Clic fleche droite
                    if(pX>this.getWidth()-tailleFleche-marge && pX<this.getWidth()-marge && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
                    {
                        aSousMenuTab2 = false;
                        this.repaint();
                    }
                }
                
                else
                {
                    int vSize = this.getHeight()/10;
                    int vRatio = vSize*(718/159);
                    int vGauche = this.getWidth()/2-vRatio-vSize/2;
                    int vDroite = this.getWidth()-vGauche-vRatio;
                    
                    //Clic Bouton Règles du Jeu
                    if(pY>3*this.getHeight()/10 && pY<3*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
                    {
                       Tutoriel tuto = new TutorielRegle("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    
                    //Clic Bouton Deplacement
                    if(pY>4*this.getHeight()/10 && pY<4*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
                    {
                        Tutoriel tuto = new TutorielDeplacement("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    //Clic Bouton Capture
                    if(pY>5*this.getHeight()/10 && pY<5*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
                    {
                        Tutoriel tuto = new TutorielCapture("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    //Clic Bouton Attaque
                    if(pY>6*this.getHeight()/10 && pY<6*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
                    {
                        Tutoriel tuto = new TutorielAttaque("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    //Clic Bouton Interface
                    if(pY>7*this.getHeight()/10 && pY<7*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
                    {
                        Tutoriel tuto = new TutorielInterface("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    //Clic Bouton Terrain
                    if(pY>3*this.getHeight()/10 && pY<3*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
                    {
                        Tutoriel tuto = new TutorielTerrain("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    //Clic Bouton Achat
                    if(pY>4*this.getHeight()/10 && pY<4*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
                    {
                        Tutoriel tuto = new TutorielAchat("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    // Bouton Experience & Evolution
                    if(pY>5*this.getHeight()/10 && pY<5*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
                    {
                       Tutoriel tuto = new TutorielEvolution("menu");
                        Slatch.ihm.passageModeTuto(tuto);
                    }
                    // Bouton Tableau des Unites
                    if(pY>6*this.getHeight()/10 && pY<6*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
                    {
                       aSousMenuTab1 = true;
                       this.repaint();
                    }
                    
                     if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                    {
                        aMenuPrincipal = true;
                        aMenuTuto = false;
                        this.repaint();
                    }
                }
                
            }
            else if(aMenuChoixTuto){
                //Clic Bouton Retour
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>10 && pX< 10+this.getHeight()/6)
                {
                    aMenuPrincipal = true;
                    aMenuChoixTuto = false;
                    this.repaint();
                }
                //Bouton Oui
                else if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 2*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+7/2*aHauteurBouton && pY<33+ 2*this.getHeight()/8+aHauteurBouton){
                    aMenuTuto = true;
                    aMenuChoixTuto = false;
                    this.repaint();
                }
                //Bouton Non
                else if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 3*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+7/2*aHauteurBouton && pY<33+ 3*this.getHeight()/8+aHauteurBouton){
                    Slatch.campagne = new Campagne(Slatch.ihm.aListeMission);
                    Slatch.campagne.createDialogue();
                }
            }
            else if(aMenuParametres)
            {
                String aStrBrouillard;
                String aStrAnimation;
                if(Slatch.ihm.getValBrouillard() == true){aStrBrouillard ="Activé";}
                else{aStrBrouillard ="Désactivé";}
                if(Slatch.ihm.getValAnimation() == true){aStrAnimation ="Activé";}
                else{aStrAnimation ="Désactivé";}
                //Click sur le brouillard
                if(pY>3*this.getHeight()/7-fmVisitor.getHeight() && pY<3*this.getHeight()/7  && pX>this.getWidth()/3 + fmBlackOps.stringWidth("Brouillard    ") && pX<this.getWidth()/3 + fmBlackOps.stringWidth("Brouillard    ")+fmVisitor.stringWidth(""+aStrBrouillard))
                {
                   if(aStrBrouillard.equals("Activé")){aStrBrouillard="Desactivé";Slatch.ihm.setValBrouillard(false);}
                   else{aStrBrouillard="Activé"; Slatch.ihm.setValBrouillard(true);}
                   this.repaint();
                }
                
                //Click sur les animations
                if(pY>4*this.getHeight()/7-fmVisitor.getHeight() && pY<4*this.getHeight()/7 && pX>this.getWidth()/3 + fmBlackOps.stringWidth("Animation    ") && pX<this.getWidth()/3 + fmBlackOps.stringWidth("Animation    ")+fmVisitor.stringWidth(""+aStrAnimation))
                {
                   if(aStrAnimation.equals("Activé")){aStrAnimation="Désactivé";Slatch.ihm.setValAnimation(false);}
                   else{aStrAnimation="Activé"; Slatch.ihm.setValAnimation(true);}
                   this.repaint();
                }
                
                if(pY>this.getHeight()-10-getHeight()/12 && pY<this.getHeight()-10-getHeight()/12+this.getHeight()/6 && pX>this.getWidth()-this.getHeight()/6-10 && pX< this.getWidth()-10)
                { 
                    aMenuPrincipal = true;
                    aMenuParametres= false;
                    this.repaint();
                }
            }
    }
    
    public String getImages(String pImage)
    {
        return aImages.get(pImage);
    }
    
    public void setImage(String pImage, String On)
    {
        aImages.put(pImage,On);
    }
    
    public void coordsurvolBouton(int pX, int pY)
    {
        if(aImages.get("boutonchargercampagne")!=""){aImages.put("boutonchargercampagne","");}
        if(aImages.get("boutonnouvellecampagne")!=""){aImages.put("boutonnouvellecampagne","");}
        if(aImages.get("boutonchargerpartie")!=""){aImages.put("boutonchargerpartie","");}
        if(aImages.get("boutonnouvellepartie")!=""){aImages.put("boutonnouvellepartie","");}
        if(aImages.get("boutondeplacement")!=""){aImages.put("boutondeplacement","");}
        if(aImages.get("boutonregles")!=""){aImages.put("boutonregles","");}
        if(aImages.get("boutoncapture")!=""){aImages.put("boutoncapture","");}
        if(aImages.get("boutonattaque")!=""){aImages.put("boutonattaque","");}
        if(aImages.get("boutoninterface")!=""){aImages.put("boutoninterface","");}
        if(aImages.get("boutonterrain")!=""){aImages.put("boutonterrain","");}
        if(aImages.get("boutonachat")!=""){aImages.put("boutonachat","");}
        if(aImages.get("choixtutooui")!=""){aImages.put("choixtutooui","");}
        if(aImages.get("choixtutonon")!=""){aImages.put("choixtutonon","");}
        if(aImages.get("boutontabunites")!=""){aImages.put("boutontabunites","");}
        if(aImages.get("boutoncampagne")!=""){aImages.put("boutoncampagne","");}
        if(aImages.get("boutonrapide")!=""){aImages.put("boutonrapide","");}
        if(aImages.get("boutontutoriel")!=""){aImages.put("boutontutoriel","");}
        if(aImages.get("boutonmapcreator")!=""){aImages.put("boutonmapcreator","");}
        if(aImages.get("boutoncredits")!=""){aImages.put("boutoncredits","");}
        if(aImages.get("boutonexp")!=""){aImages.put("boutonexp","");}
        if(aImages.get("engrenage")!=""){aImages.put("engrenage","");}
        
        
        if(aMenuPrincipal){
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 2*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+4*aHauteurBouton && pY<33+ 2*this.getHeight()/8+aHauteurBouton){
                aImages.put("boutoncampagne","on");
            }
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 3*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+4*aHauteurBouton && pY<33+ 3*this.getHeight()/8+aHauteurBouton){
                aImages.put("boutonrapide","on");
            }
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 4*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+4*aHauteurBouton && pY<33+ 4*this.getHeight()/8+aHauteurBouton){
                aImages.put("boutontutoriel","on");
            }
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 5*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+4*aHauteurBouton && pY<33+ 5*this.getHeight()/8+aHauteurBouton){
                aImages.put("boutonmapcreator","on");
            }
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 6*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+4*aHauteurBouton && pY<33+ 6*this.getHeight()/8+aHauteurBouton){
                aImages.put("boutoncredits","on");
            }
            if(pX>this.getWidth()-aHauteurBouton/2-this.getHeight()/10 && pY>aHauteurBouton/2 && pX<this.getWidth()-aHauteurBouton/2 && pY<aHauteurBouton/2+this.getHeight()/10){
                aImages.put("engrenage","on");
            }
        }
        
        if(aMenuCampagne)
        {
            if(pY>33+ this.getHeight()/4 && pY<33+ this.getHeight()/4+aHauteurBouton && this.getWidth()/2-3*aHauteurBouton<pX && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                aImages.put("boutonnouvellecampagne","on");
            }
            
            if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                aImages.put("boutonchargercampagne","on");
            }
        }
        
        if(aMenuRapide)
        {
            if(pY>33+ this.getHeight()/4 && pY<33+ this.getHeight()/4+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                aImages.put("boutonnouvellepartie","on");
            }
            
            //Clic Bouton Charger une Partie
            if(pY>33+ this.getHeight()/2 && pY<33+ this.getHeight()/2+aHauteurBouton && pX>this.getWidth()/2-3*aHauteurBouton && pX< this.getWidth()/2+ 3*aHauteurBouton)
            {
                aImages.put("boutonchargerpartie","on");
            }
        }
        
        if(aMenuTuto)
        {
            int vSize = this.getHeight()/10;
            int vRatio = vSize*(718/159);
            int vGauche = this.getWidth()/2-vRatio-vSize/2;
            int vDroite = this.getWidth()-vGauche-vRatio;
            
            // Bouton Regles du Jeu
            if(pY>3*this.getHeight()/10 && pY<3*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
            {
                aImages.put("boutonregles","on");
            }
            
            // Bouton Deplacement
            if(pY>4*this.getHeight()/10 && pY<4*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
            {
                aImages.put("boutondeplacement","on");
            }
            // Bouton Capture
            if(pY>5*this.getHeight()/10 && pY<5*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
            {
                aImages.put("boutoncapture","on");
            }
            // Bouton Attaque
            if(pY>6*this.getHeight()/10 && pY<6*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
            {
                aImages.put("boutonattaque","on");
            }
            // Bouton Interface
            if(pY>7*this.getHeight()/10 && pY<7*this.getHeight()/10+vSize && pX>vGauche && pX< vGauche+vRatio)
            {
                aImages.put("boutoninterface","on");
            }
            // Bouton Terrain
            if(pY>3*this.getHeight()/10 && pY<3*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
            {
                aImages.put("boutonterrain","on");
            }
            // Bouton Achat
            if(pY>4*this.getHeight()/10 && pY<4*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
            {
                aImages.put("boutonachat","on");
            }
            // Bouton Expérience & Evolution
            if(pY>5*this.getHeight()/10 && pY<5*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
            {
                aImages.put("boutonexp","on");
            }
            // Bouton Tableau des Unités
            if(pY>6*this.getHeight()/10 && pY<6*this.getHeight()/10+vSize && pX>vDroite && pX< vDroite+vRatio)
            {                
                aImages.put("boutontabunites","on");
            }
        }
        if(aMenuChoixTuto){
            //Bouton Oui
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 2*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+7/2*aHauteurBouton && pY<33+ 2*this.getHeight()/8+aHauteurBouton){
                aImages.put("choixtutooui","on");
            }
            //Bouton Non
            if(pX>this.getWidth()/2-2*aHauteurBouton && pY>33+ 3*this.getHeight()/8 && pX<this.getWidth()/2-2*aHauteurBouton+7/2*aHauteurBouton && pY<33+ 3*this.getHeight()/8+aHauteurBouton){
                aImages.put("choixtutonon","on");
            }
        }
        
        this.repaint();
    }
    
    /**
    * Affiche une image en fond d'ecran
    */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    }
}
