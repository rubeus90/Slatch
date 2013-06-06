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

		listeNomPartie.add("Maps/gagner.txt");
		listeNomPartie.add("Maps/mapTest.txt");
		listeNomPartie.add("Maps/doublevai.txt");
	}

	public void chargerPartie(int pNiveau) {
		System.out.println("je charge le niveau " + aNiveau);
		Equipe equipe0 = new Equipe(0);
		Equipe equipe1 = new Equipe(1);
		Equipe equipe2 = new Equipe(2);

		Equipe[] vEquipe = { equipe0, equipe1, equipe2, equipe1, equipe2 };
		boolean[] vIA = { true, false, true, true, true };

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
		System.out.println("Je passe au niveau suivant");
		if (aNiveau < listeNomPartie.size() - 1) {
			aNiveau++;
			System.out.println("on sera au niveau " + aNiveau);
			createDialogue();
		}
		else{
			System.out.println("C'est caca on depasse des niveaux existants");
			System.exit(0);
		}
	}

	public void createDialogue() {
		chargerPartie(aNiveau);
		System.out.println("Je cree le dialogue avant le niveau " + aNiveau);
		Slatch.ihm.getPanelFrame().removeAll();
		System.out.println("J'ai remove les 2 paneaux du jeu");
		
		panel = new PanelDialogueCampagne(aNiveau);
		panel.addMouseListener(this);

		Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
		Slatch.ihm.getPanelFrame().updateUI();
		
		System.out.println("C'est bon j'ai fini a creer le dialogue");
	}
	
	public void fermerDialogue(){
		System.out.println("Je ferme le dialogue pour lancer le niveau " + aNiveau);
		Slatch.ihm.getPanelFrame().remove(panel);
		Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelmatrice(), BorderLayout.CENTER);
		Slatch.ihm.getPanelFrame().add(Slatch.ihm.getpanelinfo(), BorderLayout.NORTH);
		System.out.println("je remets les 2 panneaux");
		
		if(aNiveau != 0)
			chargerPartie(aNiveau);		
		
		Slatch.ihm.getPanelFrame().repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		fermerDialogue();
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
