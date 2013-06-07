import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.* ;
import javax.swing.*;

/**
 * Panel speifique a la matrice de jeu :
 * ce panel affiche les terrains, les unites et les differents menus.
 * 
 * @author jonathan
 * @version 1.0
 */
public class PanelMatrice extends JPanel
{     
    // Dimensions en pixel d'une case de matrice
    private int aLargeurCarreau;
    private int aHauteurCarreau;
    
    // Dimensions en pixel du menu pour les actions d'une unite
    private int aMenuActionHautGauche_Xpx;
    private int aMenuActionHautGauche_Ypx;
    private int aMenuActionBasDroite_Xpx;
    private int aMenuActionBasDroite_Ypx;
    
    // Dimensions en pixel du menu de description du terrain et ou de l'unite
    private int aMenuDescriptionHautGauche_Xpx;
    private int aMenuDescriptionHautGauche_Ypx;
    private int aMenuDescriptionBasDroite_Xpx;
    private int aMenuDescriptionBasDroite_Ypx;
    
    // Dimensions en pixel du menu (celui du bouton menu de la barre info)
    private int aMenuHautGauche_Xpx;
    private int aMenuHautGauche_Ypx;
    private int aMenuBasDroite_Xpx;
    private int aMenuBasDroite_Ypx;
    
    // Dimensions en pixel du menu des usines : pour l'achat d'unite
    private int aShopHautGauche_Xpx;
    private int aShopHautGauche_Ypx;
    private int aShopBasDroite_Xpx;
    private int aShopBasDroite_Ypx;
    
    // Coordonnees matricielles d'une unite selectionnee (avant le menu action)
    private int aUniteMemMoteurCaseX;
    private int aUniteMemMoteurCaseY;
    
    // Coordonnees matricielles d'une case selectionnee
    private int aUniteMemMenuCaseX;
    private int aUniteMemMenuCaseY;
    
    // Argent du joueur memorise
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
    
    // Unite que l'on peut acheter (ordonnees)
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
        
        // Dimensions en pixel d'une case de matrice
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();

        //Aucun menu a la creation
        menuUniteAction=false;
        menuUniteDescription=false;
        menuMenu=false;
        menuShop=false;
        
        aTabAchat= new HashMap<Integer,TypeUnite> ();
        aListeAction= new ArrayList<String>();
        aListeShop= new ArrayList<TypeUnite>();
    } // FIN PanelMatrice

    /**
     * Affiche le decor (appelé lors des repaint : a eviter)
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        // Dimensions en pixel d'une case de matrice
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();
        
        // Dessine la matrice (Terrain + Unite)
        dessineMatrice(g);
                
        // Menu d'action d'une unite non IA
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
                }
                else if(aListeAction.get(vVar).equals("Soin")) {
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
        } // FIN menuUniteAction
        
        // Menu de description du terrain et de l'unite en bas de l'ecran 
        if(menuUniteDescription) 
        {
            // Adapte la taille du menu (s'il y a une unite ou non)
            redimMenuDescription(aUniteMemMenuCaseX,aUniteMemMenuCaseY);
            
            // Police
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/100);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font);  
            
            afficheImageRedim ("noir80", aMenuDescriptionHautGauche_Xpx, aMenuDescriptionHautGauche_Ypx, aMenuDescriptionBasDroite_Xpx, aMenuDescriptionBasDroite_Ypx, g);
            g.setColor(Color.white);
            Terrain t = Slatch.partie.getTerrain()[aUniteMemMenuCaseX][aUniteMemMenuCaseY];
            
            // S'il y a une unite et pas de brouillard : on affiche la description de l'unite, on affiche la description du terrain
            if(t.getUnite()!=null && !t.getBrouillard())
            {
                String portedep = "Portée Depl = "+t.getUnite().getDeplacement()/10;
                String xp = "XP = "+t.getUnite().getExperience()+"   PV = "+t.getUnite().getPV()+"/"+t.getUnite().getPVMax();
                String lvl = "LVL = ";
                String couverture = "Couv = "+ t.getType().getCouverture();
                String coord = "X = "+aUniteMemMenuCaseX+"   Y ="+aUniteMemMenuCaseY;
                String titre = t.getUnite().getType().getNom()+" : ";
                String titreTerrain=t.getType().getDescription()+" : ";
                
                g.drawString(titre, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(portedep, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
                g.drawString(xp, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                g.drawString(lvl, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                
                int tailleIcon = aHauteurCarreau -5;
                //System.out.println("niveau"+t.getUnite().getLvl()+""+Slatch.partie.getJoueur(t.getUnite().getJoueur()).getFaction().getNom());
                afficheImageRedim ("niveau"+t.getUnite().getLvl()+""+Slatch.partie.getJoueur(t.getUnite().getJoueur()).getFaction().getNom(), (aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(lvl), (aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3)-(2*tailleIcon/3), (aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(lvl)+tailleIcon, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3+(tailleIcon/3), g);
                
                afficheImageRedim (t.getUnite().getType().getImage()+""+t.getUnite().getJoueur(), (aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titre),(aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)-(2*tailleIcon/3) ,(aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titre)+tailleIcon, (aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)+(1*tailleIcon/3), g);
                afficheImageRedim (t.getType().getImage()+""+t.getJoueur(), ((aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titreTerrain),(aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)-(2*tailleIcon/3) ,((aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titreTerrain)+tailleIcon, (aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)+(1*tailleIcon/3), g);
                
                g.drawString(titreTerrain, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(couverture, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
                g.drawString(coord, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                
                if(t.getType().getDependance()) {
                    String pv = "PVTerrain = "+t.getPV();
                    g.drawString(pv, (aLargeurMenuDescriptionEnCase/2)*aLargeurCarreau + aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                }
            }
            // Sinon : on affiche pas la description de l'unite, on affiche la description du terrain
            else {
                String couverture = "Couv = "+ t.getType().getCouverture();
                String coord = "X = "+aUniteMemMenuCaseX+"   Y ="+aUniteMemMenuCaseY;
                String titre = t.getType().getDescription()+" : ";
                int tailleIcon = aHauteurCarreau - 5;
                g.drawString(t.getType().getDescription()+" : ",  aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(couverture,  aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
                g.drawString(coord, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);

                afficheImageRedim (t.getType().getImage()+""+t.getJoueur(), (aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titre),(aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)-(2*tailleIcon/3) ,(aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3)+fm.stringWidth(titre)+tailleIcon, (aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0)+(1*tailleIcon/3), g);
                if(t.getType().getDependance()) {
                    String pv = "PVTerrain = "+t.getPV();
                    g.drawString(pv, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                }
            }
        } // FIN menuUniteDescription
        
        // Menu pour l'achat d'unite
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
        } // FIN menuShop
        
        // Menu du bouton menu
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
        } // FIN menuMenu
        
        // Si la partie est finie : Ecran de fin de partie
        if(Slatch.partie.partieFinie) {
            // Fond d'ecran de fin de partie
            afficheImageRedim ("noir80", 0, 0, this.getWidth(), this.getHeight(), g);
            
            // Choix de la police de char
            Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font); 
            g.setColor(Color.white);
            
            int hauteurSize = fm.getHeight();
            String joueur = "Le joueur "+ Slatch.partie.getJoueurActuel() +" a gagné";
            int joueurSize = fm.stringWidth(joueur);
            g.drawString(joueur, this.getWidth()/2 - joueurSize/2, 2*this.getHeight()/(2*hauteurSize));
            
            // Affiche les statistiques
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
                String stat13 = "Score : " + Slatch.partie.ListeJoueur.get(i).getScore();
                int stat13Size = fm.stringWidth(stat13);
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
                String stat12 = "Soin donne : " + Slatch.partie.ListeJoueur.get(i).getSoinTotal();
                int stat12Size = fm.stringWidth(stat12);
                String stat7 = "Degats subis : " + Slatch.partie.ListeJoueur.get(i).getDegatSubit();
                int stat7Size = fm.stringWidth(stat7);
                String stat8 = "Experience Totale : " + Slatch.partie.ListeJoueur.get(i).getExpTotal();
                int stat8Size = fm.stringWidth(stat8);
                String stat9 = "Deplacement Total : " + Slatch.partie.ListeJoueur.get(i).getDeplacementTotal();
                int stat9Size = fm.stringWidth(stat9);
                    
                g.drawString(stat1, this.getWidth()/2 + decalage - stat1Size/2, 4*this.getHeight()/(2*hauteurSize));
                g.drawString(stat13, this.getWidth()/2 + decalage - stat13Size/2, 5*this.getHeight()/(2*hauteurSize));
                g.drawString(stat2, this.getWidth()/2 + decalage - stat2Size/2, 6*this.getHeight()/(2*hauteurSize));
                g.drawString(stat3, this.getWidth()/2 + decalage - stat3Size/2, 7*this.getHeight()/(2*hauteurSize));
                g.drawString(stat4, this.getWidth()/2 + decalage - stat4Size/2, 8*this.getHeight()/(2*hauteurSize));
                g.drawString(stat5, this.getWidth()/2 + decalage - stat5Size/2, 9*this.getHeight()/(2*hauteurSize));
                g.drawString(stat11, this.getWidth()/2 + decalage - stat11Size/2, 10*this.getHeight()/(2*hauteurSize));
                g.drawString(stat10, this.getWidth()/2 + decalage - stat10Size/2, 11*this.getHeight()/(2*hauteurSize));
                g.drawString(stat6, this.getWidth()/2 + decalage - stat6Size/2, 12*this.getHeight()/(2*hauteurSize));
                g.drawString(stat12, this.getWidth()/2 + decalage - stat12Size/2, 13*this.getHeight()/(2*hauteurSize));
                g.drawString(stat7, this.getWidth()/2 + decalage - stat7Size/2, 14*this.getHeight()/(2*hauteurSize));
                g.drawString(stat8, this.getWidth()/2 + decalage - stat8Size/2, 15*this.getHeight()/(2*hauteurSize));
                g.drawString(stat9, this.getWidth()/2 + decalage - stat9Size/2, 16*this.getHeight()/(2*hauteurSize));
            }
        } // FIN partieFinie
    }
    
    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (int pX, int pY) 
    {
        int clickX = pX;
        int clickY = pY; 

        // Determine la case matricielle du click
        for(int i = 0 ; i < Slatch.partie.getLargeur() ; i++) 
        {
            for(int j = 0 ; j < Slatch.partie.getHauteur() ; j++) 
            {
                // Position de la case selectionnee
                int pPosHautGaucheX = i*aLargeurCarreau;
                int pPosHautGaucheY = j*aHauteurCarreau;
                int pPosBasDroiteX = (i+1)*aLargeurCarreau;
                int pPosBasDroiteY = (j+1)*aHauteurCarreau;
                
                // Si la partie est finie
                if(Slatch.partie.partieFinie) {
                    Slatch.campagne.suite();
                }
                // Si la partie n'est pas fine
                else if(pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) 
                {
                    /*
                     * 
                     * Recuperer les coordonnees
                     * 
                     */
                    
                    // Si le menu est la...
                    if(menuMenu) {
                        // Bouton charger
                        if (0<clickY && clickY<aHauteurCarreau && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx) {
                            Slatch.partie.chargerPartie("sauvegarde");
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
                    
                    // Si le menu d'action d'une unite est la...
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
                                Slatch.moteur.evoluer(aUniteMemMoteurCaseX,aUniteMemMoteurCaseY);
                                //Slatch.moteur.setModeEvoluer(true);
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
                    
                    // Si le menu d'achat d'une unite est la (et pas le menu action)...
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
                    
                    // S'il n'y a ni menu d'achat ni menu d'action
                    else
                    {
                        if(aUniteMemMenuCaseX==i && aUniteMemMenuCaseY==j && menuUniteDescription)
                            menuUniteDescription = false;
                        else if (!menuUniteDescription) {
                            menuUniteDescription = true;
                        }
                        aUniteMemMenuCaseX=i;
                        aUniteMemMenuCaseY=j;
                        redimMenuDescription(i,j);
                        // Avertir Moteur
                        Slatch.moteur.caseSelectionnee(i,j);
                        this.repaint();
                    }
                }
            } // FIN for
        } // FIN for
    } // FIN coordclickUnite
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void dessineMatrice(final Graphics g) {
        // Dessine la matrice de terrain
        for(int i=0; i<Slatch.partie.getLargeur(); i++) {
            for(int j=0; j<Slatch.partie.getHauteur(); j++) {
                Slatch.partie.getTerrain()[i][j].dessine(g, this);
                //afficheInfoIA(i,j,g);
                //afficheInfoTerrain(i,j,g);
            }
        }
    } // FIN dessineMatrice
    
    /**
     * Methode DEBUG : Affiche les info du terrain
     */
    private void afficheInfoTerrain (int i, int j, Graphics g) {  
        int t=Slatch.partie.getTerrain()[i][j].getType().getCouverture();
        
        int pPosHautGaucheX = i*aLargeurCarreau;
        int pPosHautGaucheY = j*aHauteurCarreau;
        int pPosBasDroiteX = (i+1)*aLargeurCarreau;
        int pPosBasDroiteY = (j+1)*aHauteurCarreau;
        
        afficheImageRedim ("noir80", pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX, pPosBasDroiteY, g);
        
        Font font = new Font("Serif", Font.PLAIN, this.getWidth()/150);
        g.setFont(font);
        FontMetrics fm=getFontMetrics(font); 
        int h=fm.getHeight()-1;
        
        // Modifie la coleur en foction de la couverture
        if(t==0)               {g.setColor(Color.blue);}
        else if (t<=1)       {g.setColor(Color.green);}
        else if (t<=4)       {g.setColor(Color.orange);}
        else                    {g.setColor(Color.red);}
        g.drawString(" Couv : "+t, pPosHautGaucheX, pPosBasDroiteY+2*h-aHauteurCarreau);
        
        // Trace les lignes
        g.setColor(Color.gray);
        g.drawLine(pPosHautGaucheX, pPosHautGaucheY, pPosHautGaucheX, pPosBasDroiteY);
        g.drawLine(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX, pPosHautGaucheY);
    } // FIN afficheInfoTerrain
    
    /**
     * Methode DEBUG : Affiche les info de l'IA
     */
    private void afficheInfoIA (int i, int j, Graphics g) {  
        int joueurAv; 

        // Commence a l'afficher au tour 2 min
        if(Slatch.partie.getJoueurActuel()!=1)
            joueurAv = Slatch.partie.getJoueurActuel()-1;
        else
            joueurAv = Slatch.partie.getNbrJoueur();
            
        // Affichage des Influence
        if(Slatch.partie.getJoueur(joueurAv).estUneIA() && Slatch.partie.getTour()!=1)
        {
            // Recuperation des Influence
            int ca =OperationIA.map[i][j].capture;
            int de =OperationIA.map[i][j].defensif;
            int of =OperationIA.map[i][j].offensif;
            int me =OperationIA.map[i][j].menace;
            int re =OperationIA.map[i][j].retraite;
            
            int pPosHautGaucheX = i*aLargeurCarreau;
            int pPosHautGaucheY = j*aHauteurCarreau;
            int pPosBasDroiteX = (i+1)*aLargeurCarreau;
            int pPosBasDroiteY = (j+1)*aHauteurCarreau;
            
            afficheImageRedim ("noir80", pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX, pPosBasDroiteY, g);
            
            Font font = new Font("Serif", Font.PLAIN, this.getWidth()/150);
            g.setFont(font);
            FontMetrics fm=getFontMetrics(font); 
            int h=fm.getHeight()-1;
            
            g.setColor(Color.white);

            int p;
            
            p=ca;
            // Modifie la coleur en foction de la valeur
            if(p==0)               {g.setColor(Color.blue);}
            else if (p<=100)       {g.setColor(Color.green);}
            else if (p<=500)       {g.setColor(Color.orange);}
            else                    {g.setColor(Color.red);}
            g.drawString(" c "+ca, pPosHautGaucheX, pPosBasDroiteY+h-aHauteurCarreau);
            
            p=de;
            // Modifie la coleur en foction de la valeur
            if(p==0)               {g.setColor(Color.blue);}
            else if (p<=100)       {g.setColor(Color.green);}
            else if (p<=500)       {g.setColor(Color.orange);}
            else                    {g.setColor(Color.red);}
            g.drawString(" d "+de, pPosHautGaucheX, pPosBasDroiteY+2*h-aHauteurCarreau);
            
            p=of;
            // Modifie la coleur en foction de la valeur
            if(p==0)               {g.setColor(Color.blue);}
            else if (p<=100)       {g.setColor(Color.green);}
            else if (p<=500)       {g.setColor(Color.orange);}
            else                    {g.setColor(Color.red);}
            g.drawString(" o "+of, pPosHautGaucheX, pPosBasDroiteY+3*h-aHauteurCarreau);
            
            p=me;
            // Modifie la coleur en foction de la valeur
            if(p==0)               {g.setColor(Color.blue);}
            else if (p<=100)       {g.setColor(Color.green);}
            else if (p<=500)       {g.setColor(Color.orange);}
            else                    {g.setColor(Color.red);}
            g.drawString(" m "+me, pPosHautGaucheX, pPosBasDroiteY+4*h-aHauteurCarreau);
            
            p=re;
            // Modifie la coleur en foction de la valeur
            if(p==0)               {g.setColor(Color.blue);}
            else if (p<=100)       {g.setColor(Color.green);}
            else if (p<=500)       {g.setColor(Color.orange);}
            else                    {g.setColor(Color.red);}
            g.drawString(" r "+re, pPosHautGaucheX, pPosBasDroiteY+5*h-aHauteurCarreau);
            
            // Trace les lignes
            g.setColor(Color.gray);
            g.drawLine(pPosHautGaucheX, pPosHautGaucheY, pPosHautGaucheX, pPosBasDroiteY);
            g.drawLine(pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX, pPosHautGaucheY);
        }
    } // FIN afficheInfoIA
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    } // FIN afficheImageRedim
    
    private void effaceMenuUniteAction()
    {
        aMenuActionHautGauche_Xpx=0;
        aMenuActionHautGauche_Ypx=0;
        aMenuActionBasDroite_Xpx=0;
        aMenuActionBasDroite_Ypx=0;
        menuUniteAction = false;
        this.repaint();
    } // FIN effaceMenuUniteAction
    
    public void effaceMenuUniteDescription()
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
    } // FIN afficheMenu
    
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
    } // FIN shop
    
    /**
     * Redim le Menu du bouton menu
     */
    public void redimMenu() {
        aMenuHautGauche_Xpx = 0;
        aMenuHautGauche_Ypx = 0;
        aMenuBasDroite_Xpx = 4*aLargeurCarreau;
        aMenuBasDroite_Ypx = 2*aHauteurCarreau;
    } // FIN redimMenu
    
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
    } // FIN redimMenuAction
    
    /**
     * Affiche le menu avec les descriptions
     */
    public void redimMenuDescription(final int pX, final int pY) 
    {
        if(Slatch.partie.getTerrain()[pX][pY].getUnite()==null || Slatch.partie.getTerrain()[pX][pY].getBrouillard()) aLargeurMenuDescriptionEnCase=5;
        if(Slatch.partie.getTerrain()[pX][pY].getUnite()!=null && !Slatch.partie.getTerrain()[pX][pY].getBrouillard())  aLargeurMenuDescriptionEnCase=10;
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
    } // FIN redimMenuDescription
    
    public int getaLargeurCarreau() {return aLargeurCarreau;}
    public int getaHauteurCarreau() {return aHauteurCarreau;}
    public void setMenuUniteAction(final boolean X){menuUniteAction=X;}
    public void setMenuUniteDescription(final boolean X){menuUniteDescription=X;}
    public void setMenuShop(final boolean X){menuShop=X;}
    public void setMenu(final boolean X){menuMenu=X;}
}
