import javax.imageio.ImageIO;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.* ;
import java.awt.Font;
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
    //private Faction aFaction;
    private int[] aIntEquipe;
    private String[] aNiveauIA;
    private String aStrBrouillard;
    private String aStrAnimation;
    private boolean[] aIA;
    private Equipe[] aEquipe;
    private Faction[] aFaction;
    private boolean dBrouillard;
    private boolean aAnimation;
    private JTextArea textArea = new JTextArea();  
    private FontMetrics fmBlackOps;
    private FontMetrics fmVisitor;
    private Font fontBlackOps;
    private Font fontVisitor;
   
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
        aIntEquipe = new int[4];
        aNiveauIA = new String[4];
        aIA = new boolean[5]; // 4 joueurs + 1 joueurs neutres
        aEquipe = new Equipe[5]; // 4 joueurs + 1 joueurs neutres
        aFaction = new Faction[5]; // 4 joueurs + 1 joueurs neutres
        
        //Valeur par default dans le menu de parametre d'une nouvelle partie pour l'IA
        aIA[0]=false;
        aNiveauIA[0] ="Desactive";
        aIA[1]=false;
        aNiveauIA[1] ="Moyen";
        aIA[2]=true;
        aNiveauIA[2] ="Moyen";
        aIA[3]=true;
        aNiveauIA[3] ="Moyen";
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
        
        //Valeur par default de la valeurs du brouillard
        aStrBrouillard ="Active";
        dBrouillard=true;
        
        //Valeur par default de la valeurs du brouillard
        aStrAnimation ="Active";
        aAnimation = true;
    }
    
    @Override
    public void paintComponent (final Graphics g) 
    {
        fontBlackOps = Slatch.fonts.get("BlackOps").deriveFont(Font.PLAIN, this.getWidth()/30);
        fontVisitor = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/55);
        Font fontVisitorbis = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/70);
        fmBlackOps = getFontMetrics(fontBlackOps);
        fmVisitor = getFontMetrics(fontVisitor);
        
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
            
            g.drawImage(titrecampagne, 0, 0, this.getWidth(), this.getHeight()*2/5, this);
            g.drawImage(nouvelleCampagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton,aHauteurBouton+10, this);
            g.drawImage(chargerCampagne, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton,aHauteurBouton+10, this);
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
            g.drawImage(titrerapide, 0, 0, this.getWidth(), this.getHeight()*2/5, this);
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
                
                Image on = Slatch.aImages.get("on");
                Image off = Slatch.aImages.get("off");
                
                int hR = fmVisitor.getHeight();
                g.setColor(Color.white);
                
                   
                for(int i = 0; i<n;i++)
                {
                    g.setFont(fontBlackOps);
                    
                    g.drawString("Joueur "+(i+1),(3+2*i)*this.getWidth()/10,this.getHeight()/4+tCadre);
                    
                    g.setFont(fontVisitor);
                    
                    g.drawString(""+aIntEquipe[i],(3+2*i)*this.getWidth()/10+2*this.getHeight()/40,this.getHeight()/2-aHauteurBouton);
                    
                    g.drawString(aFaction[i+1].getNom(),(3+2*i)*this.getWidth()/10,this.getHeight()/2);
                    
                    g.drawString(""+aNiveauIA[i],(3+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton);

                    //if(vIA[i+1]){g.drawImage(on,(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton-hR/2,this.getHeight()/40,this.getHeight()/40,this);}
                    //else{g.drawImage(off,(2+2*i)*this.getWidth()/10,this.getHeight()/2+aHauteurBouton-hR/2,this.getHeight()/40,this.getHeight()/40,this);}
                }
                g.drawString(""+aStrBrouillard,(3)*this.getWidth()/10,this.getHeight()/2+2*aHauteurBouton);
                g.drawString(""+aStrAnimation,(7)*this.getWidth()/10,this.getHeight()/2+2*aHauteurBouton);
            }
            
            else
            {
                
                g.drawImage(nouvellePartie, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/4, 6*aHauteurBouton, aHauteurBouton+10, this);
                g.drawImage(chargerPartie, this.getWidth()/2-3*aHauteurBouton, 33+ this.getHeight()/2, 6*aHauteurBouton, aHauteurBouton+10, this);
                
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
                    
                    int SizeFaction = fmVisitor.stringWidth(""+aFaction[i+1].getNom());
                    int SizeEquipe = fmVisitor.stringWidth(""+aIntEquipe[i]);
                    int SizeNiveauIA = fmVisitor.stringWidth(""+aNiveauIA[i]);
                    
                    //Clic sur l'équipe 
                    if(pY>this.getHeight()/2-aHauteurBouton-hR && pY<this.getHeight()/2-aHauteurBouton && pX>(3+2*i)*this.getWidth()/10+2*this.getHeight()/40 && pX<(3+2*i)*this.getWidth()/10+2*this.getHeight()/40+SizeEquipe)
                    {
                        if(aIntEquipe[i]==4)
                        {
                            aIntEquipe[i]=0;
                        }
                        aIntEquipe[i]++;
                        

                        this.repaint();
                    }
                    
                    //Clic sur la faction
                    if(pY>this.getHeight()/2-hR && pY<this.getHeight()/2 && pX>(3+2*i)*this.getWidth()/10 && pX<(3+2*i)*this.getWidth()/10+SizeFaction)
                    {
                       if(aFaction[i+1].equals(Faction.HUMAINS)){ aFaction[i+1] = Faction.ROBOTS;}
                       else{ aFaction[i+1] = Faction.HUMAINS;}
                       this.repaint();
                    }
                                        
                    //Clic sur la difficulté de l'IA
                    if(pY>this.getHeight()/2+aHauteurBouton-hR && pY<this.getHeight()/2+aHauteurBouton && pX>(3+2*i)*this.getWidth()/10 && pX<(3+2*i)*this.getWidth()/10+SizeNiveauIA)
                    {
                           switch(aNiveauIA[i])
                           {
                               case("Desactive") : aNiveauIA[i]="Facile";aIA[i+1] = true;break;
                               case("Facile") : aNiveauIA[i]="Moyen";aIA[i+1] = true;break;
                               case("Moyen") : aNiveauIA[i]="Difficile";aIA[i+1] = true;break;
                               case("Difficile") : aNiveauIA[i]="Legendaire";aIA[i+1] = true;break;
                               case("Legendaire") : aNiveauIA[i]="Desactive";aIA[i+1] = false;break;
                           }
                           this.repaint();
                    }
      
                }
                int SizeBrouillard = fmVisitor.stringWidth(""+aStrBrouillard);
                int SizeAnimation = fmVisitor.stringWidth(""+aStrAnimation);
                
                //Click sur le brouillard
                if(pY>this.getHeight()/2+2*aHauteurBouton-hR && pY<this.getHeight()/2+2*aHauteurBouton && pX>(3)*this.getWidth()/10 && pX<(3)*this.getWidth()/10+SizeBrouillard)
                {
                   if(aStrBrouillard.equals("Active")){aStrBrouillard="Desactive";dBrouillard=false;}
                   else{aStrBrouillard="Active"; dBrouillard=true;}
                   this.repaint();
                }
                
                //Click sur les animations
                if(pY>this.getHeight()/2+2*aHauteurBouton-hR && pY<this.getHeight()/2+2*aHauteurBouton && pX>(7)*this.getWidth()/10 && pX<(7)*this.getWidth()/10+SizeAnimation)
                {
                   if(aStrAnimation.equals("Active")){aStrAnimation="Desactive";aAnimation=false;}
                   else{aStrAnimation="Active"; aAnimation=true;}
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
                    
                   Partie partieRapide = new Partie(50,30,Slatch.ihm.aListeMap.get(aNumeroMap),dBrouillard, aFaction,aEquipe,aIA,aAnimation);

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
