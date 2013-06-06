import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * @author Ngoc
 * 
 */
public class Campagne {
	private ArrayList<String> listeNomPartie;
	private int aNiveau;

	public Campagne() {
		aNiveau = 0;
		listeNomPartie = new ArrayList<String>();

		listeNomPartie.add("Maps/gagner.txt");
		listeNomPartie.add("Maps/mapTest.txt");
	}

	public void chargerPartie(int pNiveau) {
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
		if (aNiveau < listeNomPartie.size() - 1) {
			aNiveau++;
			chargerPartie(aNiveau);
		}
	}

	public void createDialogue() {
		JFrame frame = Slatch.ihm.getframe();
		frame.remove(Slatch.ihm.getpanelmatrice());
		frame.remove(Slatch.ihm.getpanelinfo());

		PanelDialogueCampagne panel = new PanelDialogueCampagne(aNiveau);

		frame.setContentPane(panel);
		frame.repaint();
		panel.repaint();

	}
}
