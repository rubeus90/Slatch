import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * @author Ngoc
 * 
 */
public class Campagne implements MouseListener {
    private ArrayList<Map> listeNomPartie;
    private int aNiveau;
    private PanelDialogueCampagne panel;

    public Campagne() {
        aNiveau = 0;
        listeNomPartie = new ArrayList<Map>();

        listeNomPartie.add(Map.NIVEAU1);
        listeNomPartie.add(Map.NIVEAU2);
        listeNomPartie.add(Map.NIVEAU3);
        listeNomPartie.add(Map.NIVEAU4); 
        listeNomPartie.add(Map.NIVEAU5);
        listeNomPartie.add(Map.NIVEAU6);
        listeNomPartie.add(Map.NIVEAU7);
        listeNomPartie.add(Map.NIVEAU8);
        listeNomPartie.add(Map.NIVEAU9);
        listeNomPartie.add(Map.NIVEAU10);
        listeNomPartie.add(Map.NIVEAU11);
        listeNomPartie.add(Map.NIVEAU12);
        listeNomPartie.add(Map.NIVEAU13);
        listeNomPartie.add(Map.NIVEAU14);
        listeNomPartie.add(Map.NIVEAU15);
    }

    public void chargerPartie(int pNiveau) {
        Equipe equipe0 = new Equipe(0);
        Equipe equipe1 = new Equipe(1);
        Equipe equipe2 = new Equipe(2);

        Equipe[] vEquipe = { equipe0, equipe1, equipe2, equipe2, equipe2 };
        int vTourMax;
        vTourMax=99;
        
        
        //Specificite de certains Niveau
        if(aNiveau==2){
           vTourMax=8; 
        }
        
        if(aNiveau==5){
           vEquipe[2] =equipe1;
           vEquipe[3]=equipe1;
        }
        
        if(aNiveau==8){
           vTourMax=20; 
        }
         
        //On cree la partie
        Partie partie = new Partie(vTourMax,listeNomPartie.get(pNiveau), vEquipe);
        Slatch.partie = partie;
        Slatch.moteur = new Moteur();

        Slatch.ihm.passageModePartie();

        if (Slatch.partie.getBrouillard()) {
            Slatch.moteur.Brouillard();
        }

        if (Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()) {
            StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
        }
    }

    public void suite() {
        if(Slatch.partie.getJoueurWin()){
            if (aNiveau < listeNomPartie.size() - 1) {
            aNiveau++;
            listeNomPartie.get(aNiveau).setVerrouille(false);
            createDialogue();
            } else {
                aNiveau = listeNomPartie.size();
                Slatch.ihm.getPanelFrame().removeAll();
                panel = new PanelDialogueCampagne();
                panel.addMouseListener(this);
                Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
                panel.afficheText();
                panel.repaint();
                Slatch.ihm.getPanelFrame().updateUI();
            }
        }
        else{
           createDialogue();
        }
    }

    public void createDialogue() {
        chargerPartie(aNiveau);
        Slatch.ihm.getPanelFrame().removeAll();

        panel = new PanelDialogueCampagne(aNiveau);
        panel.etapeDialogue();
        panel.getTextArea().addMouseListener(this);
        panel.addMouseListener(this);

        Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
        Slatch.ihm.getPanelFrame().updateUI();
    }

    public void fermerDialogue() {
        Slatch.ihm.getPanelFrame().remove(panel);
        Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelmatrice(),
                BorderLayout.CENTER);
        Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelinfo(),
                BorderLayout.NORTH);

        if (aNiveau != 0)
            chargerPartie(aNiveau);

        Slatch.ihm.getPanelFrame().repaint();

    }
    
    public void conditionVictoire(){
        if(aNiveau==2){
            Slatch.partie.setPartieFini(true);
        }
        if(aNiveau==8){
            Slatch.partie.setJoueurActuel(2);
            Slatch.partie.setPartieFini(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (aNiveau < listeNomPartie.size()) {
            panel.etapeDialogue();
            panel.repaint();
            if(panel.getDialogueFinished())
                fermerDialogue();
        } else
            System.exit(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}


