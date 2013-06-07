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
public class Campagne implements MouseListener{
	private ArrayList<String> listeNomPartie;
	private int aNiveau;
	private PanelDialogueCampagne panel;

	public Campagne() {
		aNiveau = 0;
		listeNomPartie = new ArrayList<String>();

		listeNomPartie.add("Maps/niveau1.txt");
	}

	public void chargerPartie(int pNiveau) {
		Equipe equipe0 = new Equipe(0);
		Equipe equipe1 = new Equipe(1);
		Equipe equipe2 = new Equipe(2);

		Equipe[] vEquipe = { equipe0, equipe1, equipe2, equipe1, equipe2 };
		boolean[] vIA = { true, true, false, true, true };

		Partie partie = new Partie(99, listeNomPartie.get(pNiveau), true, vEquipe, vIA);
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
		if (aNiveau < listeNomPartie.size() - 1) {
			aNiveau++;
			createDialogue();
		}
		else{
			aNiveau = listeNomPartie.size();
			Slatch.ihm.getPanelFrame().removeAll();
			panel = new PanelDialogueCampagne();
			panel.addMouseListener(this);
			Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
			panel.repaint();
			Slatch.ihm.getPanelFrame().updateUI();
		}
	}

	public void createDialogue() {
		chargerPartie(aNiveau);
		Slatch.ihm.getPanelFrame().removeAll();
		
		panel = new PanelDialogueCampagne(aNiveau);
		panel.addMouseListener(this);

		Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
		Slatch.ihm.getPanelFrame().updateUI();
	}
	
	public void fermerDialogue(){
		Slatch.ihm.getPanelFrame().remove(panel);
		Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelmatrice(), BorderLayout.CENTER);
		Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelinfo(), BorderLayout.NORTH);
		
		if(aNiveau != 0)
			chargerPartie(aNiveau);		
		
		Slatch.ihm.getPanelFrame().repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(aNiveau < listeNomPartie.size())
			fermerDialogue();
		else
			System.exit(0);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
