import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.* ;
import javax.swing.*;

/**
 * Panel specifique a la matrice de jeu :
 * ce panel affiche les terrains, les unites et les differents menus.
 * 
 * @author jonathan
 * @version 1.0
 */
public class PanelMatrice extends JPanel
{     
    private int aLargeurCarreau;
    private int aHauteurCarreau;
    
    private int aMenuActionHautGauche_Xpx;
    private int aMenuActionHautGauche_Ypx;
    private int aMenuActionBasDroite_Xpx;
    private int aMenuActionBasDroite_Ypx;
    
    private int aMenuDescriptionHautGauche_Xpx;
    private int aMenuDescriptionHautGauche_Ypx;
    private int aMenuDescriptionBasDroite_Xpx;
    private int aMenuDescriptionBasDroite_Ypx;
    
    private int aMenuHautGauche_Xpx;
    private int aMenuHautGauche_Ypx;
    private int aMenuBasDroite_Xpx;
    private int aMenuBasDroite_Ypx;
    
    private int aShopHautGauche_Xpx;
    private int aShopHautGauche_Ypx;
    private int aShopBasDroite_Xpx;
    private int aShopBasDroite_Ypx;
    
    private int aUniteMemMoteurCaseX;
    private int aUniteMemMoteurCaseY;
    
    private int aUniteMemMenuCaseX;
    private int aUniteMemMenuCaseY;
    
    private int aArgentMem;
    
    private int aLargeurMenuActionEnCase=3;
    private int aHauteurMenuActionEnCase=4;
    
    private int aLargeurMenuDescriptionEnCase=10;
    private int aHauteurMenuDescriptionEnCase=4;
    
    private int aHauteurShopEnCase;
    private int aLargeurShopEnCase;
    
    private boolean menuUniteAction;
    private boolean menuUniteDescription;
    private boolean menuMenu;
    private boolean menuShop;
    
    private boolean aAttaquePossible=false;
    private boolean aSoinPossible=false;
    private boolean aDeplacePossible=false;
    private boolean aCapturePossible=false;
    private boolean aEvoluePossible=false;
    
    private HashMap<Integer,TypeUnite>  aTabAchat;
    
    private List<String> aListeAction;
    private List<TypeUnite> aListeShop;

    /**
     * Constructeur du panel, creer les listes et definie les dime,
     */
    public PanelMatrice()
    {
        super();
        this.setPreferredSize(new Dimension(800, 500));
        this.setBackground(Color.black);
        
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();

        menuUniteAction=false;
        menuUniteDescription=false;
        menuMenu=false;
        menuShop=false;
        
        aTabAchat= new HashMap<Integer,TypeUnite> ();
        aListeAction= new ArrayList<String>();
        aListeShop= new ArrayList<TypeUnite>();
    }

    /**
     * Affiche le decor (appelé lors des repaint : a eviter)
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();
        dessineMatrice(g);
        
        if(menuUniteAction && !Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()) 
        {
            afficheImageRedim ("noir80", aMenuActionHautGauche_Xpx, aMenuActionHautGauche_Ypx, aMenuActionBasDroite_Xpx, aMenuActionBasDroite_Ypx, g);
            
            // Police
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font); 
            
            // Trace les lignes
            for(int vVar=1;vVar<4;vVar++) {
                g.setColor(Color.white);
                g.drawLine(aMenuActionHautGauche_Xpx, aMenuActionHautGauche_Ypx+(vVar*aHauteurMenuActionEnCase/aHauteurMenuActionEnCase)*aHauteurCarreau, aMenuActionBasDroite_Xpx-1, aMenuActionHautGauche_Ypx+(vVar*aHauteurMenuActionEnCase/aHauteurMenuActionEnCase)*aHauteurCarreau);
            }
            
            // Ecrie les boutons en gris
            g.setColor(Color.gray);
            g.drawString("Deplace", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3);
            if(!aListeAction.contains("Attaque") && !aListeAction.contains("Soin")){ g.drawString("Action", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);}
            g.drawString("Capture", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
            g.drawString("Evolue", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
           
            // Ecrie les boutons en vert
            for(int vVar=0;vVar<aListeAction.size();vVar++) {
                g.setColor(Color.green);
                if(aListeAction.get(vVar).equals("Deplace")) {
                    g.drawString("Deplace", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3);
                    aDeplacePossible=true;
                }
                if(aListeAction.get(vVar).equals("Attaque")) {
                    g.drawString("Attaque", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
                    aAttaquePossible=true;
                }else if(aListeAction.get(vVar).equals("Soin")) {
                    g.drawString("Soin", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
                    aSoinPossible=true;
                }
                if(aListeAction.get(vVar).equals("Capture")) {
                    g.drawString("Capture", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                    aCapturePossible=true;
                }
                if(aListeAction.get(vVar).equals("Evolue")) {
                    g.drawString("Evolue", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                    aEvoluePossible=true;
                }
            }
        }
        
        if(menuUniteDescription) 
        {
            redimMenuDescription(aUniteMemMenuCaseX,aUniteMemMenuCaseY);
            
            // Police
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/100);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font);  
            
            afficheImageRedim ("noir80", aMenuDescriptionHautGauche_Xpx, aMenuDescriptionHautGauche_Ypx, aMenuDescriptionBasDroite_Xpx, aMenuDescriptionBasDroite_Ypx, g);
            g.setColor(Color.white);
            Terrain t = Slatch.partie.getTerrain()[aUniteMemMenuCaseX][aUniteMemMenuCaseY];
            if(t.getUnite()!=null)
            {
                String portedep = "Portée Depl = "+t.getUnite().getDeplacement()/10;
                String xp = "XP = "+t.getUnite().getExperience();
                String lvl = "LVL = "+t.getUnite().getLvl();
                String couverture = "Couv = "+ t.getType().getCouverture();

                g.drawString("UNITE "+t.getUnite().getType().getNom()+" : ", aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(portedep, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
                g.drawString(xp, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                g.drawString(lvl, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);

                g.drawString("TERRAIN "+t.getType().getDescription()+" : ", (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(couverture, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
            
                if(t.getType().getDependance()) {
                    String pv = "PVTerrain = "+t.getPV();
                    g.drawString(pv, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                }
            }
            
            else {
                String couverture = "Couv = "+ t.getType().getCouverture();
                
                g.drawString("TERRAIN "+t.getType().getDescription()+" : ",  aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(couverture,  aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
            
                if(t.getType().getDependance()) {
                    String pv = "PVTerrain = "+t.getPV();
                    g.drawString(pv, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                }
            }
        }
        
        if(menuShop && !Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()) 
        {
            afficheImageRedim ("noir80", aShopHautGauche_Xpx, aShopHautGauche_Ypx, aShopBasDroite_Xpx, aShopBasDroite_Ypx, g);

            // Police
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font);  

            String info = "VOICI LE SHOP :";
            g.drawString(info, aShopHautGauche_Xpx+aLargeurCarreau/3, aShopHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
            int i=0;
            for(TypeUnite vType : aListeShop) {
                i++;
                if(aArgentMem<vType.getPrix()) {
                    // Ecrie les boutons en gris
                    g.setColor(Color.gray);
                    g.drawString(vType.getNom()+":  Prix:"+vType.getPrix()+"¤  PV:"+vType.getPVMax()+"  Depl:"+vType.getDeplacement()/10+"  Attaque:"+vType.getAttaque(), aShopHautGauche_Xpx+aLargeurCarreau/3, aShopHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*i);
                }
                else {
                    // Ecrie les boutons en vert
                    g.setColor(Color.green);
                    g.drawString(vType.getNom()+":  Prix"+vType.getPrix()+"¤  PV:"+vType.getPVMax()+"  Depl:"+vType.getDeplacement()/10+"  Attaque:"+vType.getAttaque(), aShopHautGauche_Xpx+aLargeurCarreau/3, aShopHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*i);
                    aTabAchat.put(i,vType);
                }
            }
            g.setColor(Color.gray);
            g.drawLine(aShopHautGauche_Xpx, 0, aShopBasDroite_Xpx, 0);
        }
        
        if(menuMenu) 
        {
            redimMenu();
            
            // Police
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font);  
            
            String charger = "Charger";
            int chargerSize = fm.stringWidth(charger);
            String sauver = "Sauvegarder";
            int sauverSize = fm.stringWidth(sauver);

            afficheImageRedim ("noir80", aMenuHautGauche_Xpx, aMenuHautGauche_Ypx, aMenuBasDroite_Xpx, aMenuBasDroite_Ypx, g);
            g.setColor(Color.white);
            g.drawString(charger, (aMenuBasDroite_Xpx-aMenuHautGauche_Xpx-chargerSize)/2, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
            g.drawString(sauver, (aMenuBasDroite_Xpx-aMenuHautGauche_Xpx-sauverSize)/2, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
            
            g.setColor(Color.gray);
            g.drawLine(aMenuHautGauche_Xpx, 0, aMenuBasDroite_Xpx-1, 0);
        }
        
        if(Slatch.partie.partieFinie) {
            afficheImageRedim ("noir80", 0, 0, this.getWidth(), this.getHeight(), g);
            
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font); 
            g.setColor(Color.white);
            
            int hauteurSize = fm.getHeight();
            String joueur = "Le joueur "+ Slatch.partie.getJoueurActuel() +" a gagné";
            int joueurSize = fm.stringWidth(joueur);
            g.drawString(joueur, this.getWidth()/2 - joueurSize/2, 2*this.getHeight()/(2*hauteurSize));
            
            for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++){
                int decalage=0;
                if(Slatch.partie.getNbrJoueur()==2 && i==1) decalage=-this.getWidth()/5;
                if(Slatch.partie.getNbrJoueur()==2 && i==2) decalage=this.getWidth()/5;
                
                if(Slatch.partie.getNbrJoueur()==3 && i==1) decalage=-this.getWidth()/5;
                if(Slatch.partie.getNbrJoueur()==3 && i==2) decalage=0;
                if(Slatch.partie.getNbrJoueur()==3 && i==3) decalage=this.getWidth()/5;
                
                if(Slatch.partie.getNbrJoueur()==4 && i==1) decalage=-this.getWidth()/3;
                if(Slatch.partie.getNbrJoueur()==4 && i==2) decalage=-this.getWidth()/9;
                if(Slatch.partie.getNbrJoueur()==4 && i==3) decalage=this.getWidth()/9;
                if(Slatch.partie.getNbrJoueur()==4 && i==4) decalage=this.getWidth()/3;
                
                String stat1 = "Statistiques du joueur " + i;
                int stat1Size = fm.stringWidth(stat1);
                String stat2 = "Argent recolte : " + Slatch.partie.ListeJoueur.get(i).getArgentTotal()+"¤";
                int stat2Size = fm.stringWidth(stat2);
                String stat3 = "Argent depense : " + Slatch.partie.ListeJoueur.get(i).getArgentDepense()+"¤";
                int stat3Size = fm.stringWidth(stat3);
                String stat4 = "Batiments Captures: " + Slatch.partie.ListeJoueur.get(i).getCaptureTotal();
                int stat4Size = fm.stringWidth(stat4);
                String stat11 = "Unites Tuées : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteTue();
                int stat11Size = fm.stringWidth(stat11);
                String stat10 = "Unites créées : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteCree();
                int stat10Size = fm.stringWidth(stat10);
                String stat5 = "Unites perdues : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteMort();
                int stat5Size = fm.stringWidth(stat5);
                String stat6 = "Degats infliges : " + Slatch.partie.ListeJoueur.get(i).getDegatTotal();
                int stat6Size = fm.stringWidth(stat6);
                String stat7 = "Degats subis : " + Slatch.partie.ListeJoueur.get(i).getDegatSubit();
                int stat7Size = fm.stringWidth(stat7);
                String stat8 = "Experience Totale : " + Slatch.partie.ListeJoueur.get(i).getExpTotal();
                int stat8Size = fm.stringWidth(stat8);
                String stat9 = "Deplacement Total : " + Slatch.partie.ListeJoueur.get(i).getDeplacementTotal();
                int stat9Size = fm.stringWidth(stat9);
                    
                g.drawString(stat1, this.getWidth()/2 + decalage - stat1Size/2, 4*this.getHeight()/(2*hauteurSize));
                g.drawString(stat2, this.getWidth()/2 + decalage - stat2Size/2, 5*this.getHeight()/(2*hauteurSize));
                g.drawString(stat3, this.getWidth()/2 + decalage - stat3Size/2, 6*this.getHeight()/(2*hauteurSize));
                g.drawString(stat4, this.getWidth()/2 + decalage - stat4Size/2, 7*this.getHeight()/(2*hauteurSize));
                g.drawString(stat5, this.getWidth()/2 + decalage - stat5Size/2, 8*this.getHeight()/(2*hauteurSize));
                g.drawString(stat11, this.getWidth()/2 + decalage - stat11Size/2, 9*this.getHeight()/(2*hauteurSize));
                g.drawString(stat10, this.getWidth()/2 + decalage - stat10Size/2, 10*this.getHeight()/(2*hauteurSize));
                g.drawString(stat6, this.getWidth()/2 + decalage - stat6Size/2, 11*this.getHeight()/(2*hauteurSize));
                g.drawString(stat7, this.getWidth()/2 + decalage - stat7Size/2, 12*this.getHeight()/(2*hauteurSize));
                g.drawString(stat8, this.getWidth()/2 + decalage - stat8Size/2, 13*this.getHeight()/(2*hauteurSize));
                g.drawString(stat9, this.getWidth()/2 + decalage - stat9Size/2, 14*this.getHeight()/(2*hauteurSize));

            }
        }
    }
    
    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (int pX, int pY) 
    {
        int clickX = pX;
        int clickY = pY; 

        for(int i = 0 ; i < Slatch.partie.getLargeur() ; i++) 
        {
            for(int j = 0 ; j < Slatch.partie.getHauteur() ; j++) 
            {
                // Position de la case selectionnee
                int pPosHautGaucheX = i*aLargeurCarreau;
                int pPosHautGaucheY = j*aHauteurCarreau;
                int pPosBasDroiteX = (i+1)*aLargeurCarreau;
                int pPosBasDroiteY = (j+1)*aHauteurCarreau;

                if(pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) 
                {
                	/*
                	 * 
                	 * 
                	 * 
                	 * Recuperer les coordonnees
                	 * 
                	 * 
                	 */
                    if(menuMenu) {
                        // Bouton charger
                        if (0<clickY && clickY<aHauteurCarreau && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx) {
                            Slatch.partie.chargerPartie();
                        }
                        // Bouton sauver
                        else if(aHauteurCarreau<clickY && clickY<2*aHauteurCarreau && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx) {
                            Slatch.partie.sauvegardePartie("Maps/sauvegarde.txt");
                        }
                        else {
                            aUniteMemMenuCaseX=i;
                            aUniteMemMenuCaseY=j;
                            effaceMenu();
                            // Avertir Moteur
                            Slatch.moteur.caseSelectionnee(i,j);
                            this.repaint();
                        }
                        effaceMenuShop();
                    }
                    
                    if(menuUniteAction && !Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
                    {
                        if( aMenuActionHautGauche_Ypx<clickY && clickY<aMenuActionBasDroite_Ypx && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )//Si tu es dans menu
                        {
                            //Action a differencier
                            
                            //Bouton 1 : Deplace
                            if(aDeplacePossible && (aMenuActionHautGauche_Ypx<clickY && clickY<(aMenuActionHautGauche_Ypx+aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )) 
                            {
                                Slatch.moteur.modeDeplacement(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                aDeplacePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 2 : Attaque
                            if(aAttaquePossible && (aMenuActionHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+2*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeAttaque(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                aAttaquePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 2 : Soin
                            if(aSoinPossible && (aMenuActionHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+2*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeSoin(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                aSoinPossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 3 : Capture
                            if(aCapturePossible && (aMenuActionHautGauche_Ypx+2*aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+3*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )
                            {
                                Slatch.moteur.capture(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                aCapturePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 4 : Evolue
                            if(aEvoluePossible && (aMenuActionHautGauche_Ypx+3*aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+4*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )
                            {
                                Slatch.moteur.evolue(aUniteMemMoteurCaseX,aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                aEvoluePossible=false;
                                this.repaint();
                            }
                        }
                        else 
                        {
                            aUniteMemMenuCaseX=i;
                            aUniteMemMenuCaseY=j;
                            effaceMenuUniteAction();
                            // Avertir Moteur
                            Slatch.moteur.caseSelectionnee(i,j);
                            this.repaint();
                        }
                    }
                    else if(menuShop && !Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()) {
                        if (aShopHautGauche_Ypx<clickY && clickY<aShopBasDroite_Ypx && aShopHautGauche_Xpx<clickX && clickX<aShopBasDroite_Xpx) {
                            for(int v=1; v<aListeShop.size()+1;v++) {
                                if(aTabAchat.get(v)!=null && (aShopHautGauche_Ypx+v*aHauteurCarreau<clickY && clickY<aShopHautGauche_Ypx+(v+1)*aHauteurCarreau)) {
                                    Slatch.moteur.creationUnite(aUniteMemMenuCaseX,aUniteMemMenuCaseY,aTabAchat.get(v));
                                    effaceMenuShop();
                                    this.repaint();
                                    Slatch.ihm.getpanelinfo().repaint();
                                }
                            }
                        }
                        else 
                        {
                            aUniteMemMenuCaseX=i;
                            aUniteMemMenuCaseY=j;
                            effaceMenuShop();
                            // Avertir Moteur
                            Slatch.moteur.caseSelectionnee(i,j);
                            this.repaint();
                        }
                    }
                    
                    else
                    {
                        if(aUniteMemMenuCaseX==i && aUniteMemMenuCaseY==j && menuUniteDescription)
                            menuUniteDescription = false;
                        else if (!menuUniteDescription) {
                            menuUniteDescription = true;
                        }
                        
                        aUniteMemMenuCaseX=i;
                        aUniteMemMenuCaseY=j;
                        Slatch.moteur.caseSelectionnee(i,j);
                        this.repaint();
                    }
                }
            }
        }
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void dessineMatrice(final Graphics g) {
        //afficheImageRedim("2.png",0, 0,this.getWidth(),this.getHeight(),g);
        
        for(int i=0; i<Slatch.partie.getLargeur(); i++) {
            for(int j=0; j<Slatch.partie.getHauteur(); j++) {
                Slatch.partie.getTerrain()[i][j].dessine(g, this);
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
    
    private void effaceMenuUniteAction()
    {
        aMenuActionHautGauche_Xpx=0;
        aMenuActionHautGauche_Ypx=0;
        aMenuActionBasDroite_Xpx=0;
        aMenuActionBasDroite_Ypx=0;
        menuUniteAction = false;
        this.repaint();
    }
    
    private void effaceMenuUniteDescription()
    {
        aMenuDescriptionHautGauche_Xpx=0;
        aMenuDescriptionHautGauche_Ypx=0;
        aMenuDescriptionBasDroite_Xpx=0;
        aMenuDescriptionBasDroite_Ypx=0;
        menuUniteDescription = false;
        this.repaint();
    }
    
    private void effaceMenuShop()
    {
        aShopHautGauche_Xpx=0;
        aShopHautGauche_Ypx=0;
        aShopBasDroite_Xpx=0;
        aShopBasDroite_Ypx=0;
        aTabAchat= new HashMap<Integer,TypeUnite> ();
        menuShop=false;
        aListeShop.clear();
        this.repaint();
    }
    
    private void effaceMenu()
    {
        aMenuHautGauche_Xpx=0;
        aMenuHautGauche_Ypx=0;
        aMenuBasDroite_Xpx=0;
        aMenuBasDroite_Ypx=0;
        menuMenu=false;
        this.repaint();
    }
    
    /**
     * Affiche les 2 menus qui s'affichent lors d'une selection d'une unite
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        aUniteMemMoteurCaseX = pX;
        aUniteMemMoteurCaseY = pY;
        aListeAction=pList;
        menuUniteAction = true;
        redimMenuAction(pX, pY);
    }
    
    /**
     * Methode appelee par moteur lors du click sur un shop
     */
    public void shop(final List<TypeUnite> pList, final int pArgentDispo, final int pX, final int pY) {
        menuShop=true;
        aArgentMem=pArgentDispo;
        aListeShop=pList;
        aHauteurShopEnCase=(Slatch.partie.getHauteur()-aHauteurMenuDescriptionEnCase-1);
        aLargeurShopEnCase=(Slatch.partie.getLargeur()/2-1);

        if(pX<Slatch.partie.getLargeur()/2) 
        {
            //Dessine a droite
            aShopHautGauche_Xpx = (Slatch.partie.getLargeur()-aLargeurShopEnCase)*aLargeurCarreau;
            aShopHautGauche_Ypx = 0;
            aShopBasDroite_Xpx = Slatch.partie.getLargeur()*aLargeurCarreau;
            aShopBasDroite_Ypx = aHauteurShopEnCase*aHauteurCarreau;
        }
        else 
        {
            //Dessine a gauche
            aShopHautGauche_Xpx = 0;
            aShopHautGauche_Ypx = 0;
            aShopBasDroite_Xpx = (Slatch.partie.getLargeur()/2-1)*aLargeurCarreau;
            aShopBasDroite_Ypx = aHauteurShopEnCase*aHauteurCarreau;
        }
    }
    
    public void redimMenu() {
        aMenuHautGauche_Xpx = 0;
        aMenuHautGauche_Ypx = 0;
        aMenuBasDroite_Xpx = 4*aLargeurCarreau;
        aMenuBasDroite_Ypx = 2*aHauteurCarreau;
    }
    
    /**
     * Affiche le menu avec les boutons
     */
    public void redimMenuAction(final int pX, final int pY) 
    {
        if(pX+aLargeurMenuActionEnCase+1>Slatch.partie.getLargeur() && pY+aHauteurMenuActionEnCase+1>Slatch.partie.getHauteur()) 
        {
            //Dessine en haut à gauche
            aMenuActionHautGauche_Xpx = (pX-aLargeurMenuActionEnCase)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY-aHauteurMenuActionEnCase)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = pY*aHauteurCarreau;
        }

        else if(pX+aLargeurMenuActionEnCase+1>Slatch.partie.getLargeur()) 
        {
            //Dessine en bas à gauche
            aMenuActionHautGauche_Xpx = (pX-aLargeurMenuActionEnCase)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = (pY+aHauteurMenuActionEnCase+1)*aHauteurCarreau;
        }
        
        else if(pY+aHauteurMenuActionEnCase+1>Slatch.partie.getHauteur()) {
            //Dessine en haut à droite
            aMenuActionHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY-aHauteurMenuActionEnCase)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = (pX+aLargeurMenuActionEnCase+1)*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = pY*aHauteurCarreau;
        }
        
        else {
            // Meun en bas a droite par default
            aMenuActionHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = (pX+aLargeurMenuActionEnCase+1)*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = (pY+aHauteurMenuActionEnCase+1)*aHauteurCarreau;
        }
        
    }
    
    /**
     * Affiche le menu avec les descriptions
     */
    public void redimMenuDescription(final int pX, final int pY) 
    {
        if(Slatch.partie.getTerrain()[aUniteMemMenuCaseX][aUniteMemMenuCaseY].getUnite()==null && aLargeurMenuDescriptionEnCase==10) aLargeurMenuDescriptionEnCase=5;
        if(Slatch.partie.getTerrain()[aUniteMemMenuCaseX][aUniteMemMenuCaseY].getUnite()!=null)  aLargeurMenuDescriptionEnCase=10;
        if(pX<Slatch.partie.getLargeur()/2) 
        {
            // En bas a droite
            aMenuDescriptionHautGauche_Xpx = (Slatch.partie.getLargeur() - aLargeurMenuDescriptionEnCase)*aLargeurCarreau;
            aMenuDescriptionHautGauche_Ypx = (Slatch.partie.getHauteur() - aHauteurMenuDescriptionEnCase)*aHauteurCarreau;
            aMenuDescriptionBasDroite_Xpx = (Slatch.partie.getLargeur())*aLargeurCarreau;
            aMenuDescriptionBasDroite_Ypx = Slatch.partie.getHauteur()*aHauteurCarreau;
        }
        else {
            // En bas a gauche
            aMenuDescriptionHautGauche_Xpx = 0;
            aMenuDescriptionHautGauche_Ypx = (Slatch.partie.getHauteur() - aHauteurMenuDescriptionEnCase)*aHauteurCarreau;
            aMenuDescriptionBasDroite_Xpx = (aLargeurMenuDescriptionEnCase)*aLargeurCarreau;
            aMenuDescriptionBasDroite_Ypx = Slatch.partie.getHauteur()*aHauteurCarreau;
        }
    }
    
    public int getaLargeurCarreau() {return aLargeurCarreau;}
    public int getaHauteurCarreau() {return aHauteurCarreau;}
    public void setMenuUniteAction(final boolean X){menuUniteAction=X;}
    public void setMenuUniteDescription(final boolean X){menuUniteDescription=X;}
    public void setMenuShop(final boolean X){menuShop=X;}
    public void setMenu(final boolean X){menuMenu=X;}
    
}
