import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * @author Ngoc
 * 
 */
public class Campagne implements MouseListener {
    private List<Map> listeNomPartie;
    private int aNiveau;
    private PanelDialogueCampagne panel;

    public Campagne(final List<Map> pListe ) {
        aNiveau = 2;
        listeNomPartie = pListe;        
    }

	public void chargerPartie(int pNiveau) {
		Equipe equipe0 = new Equipe(0);
		Equipe equipe1 = new Equipe(1);
		Equipe equipe2 = new Equipe(2);

		Equipe[] vEquipe = { equipe0, equipe1, equipe2, equipe2, equipe2 };
		Faction[] vFaction = { Faction.NEUTRE, Faction.HUMAINS, Faction.ROBOTS,
				Faction.ROBOTS, Faction.ROBOTS };
		int vTourMax;
		vTourMax = 99;

		// Specificite de certains Niveau
		if (aNiveau == 2) {
			vTourMax = 8;
		}

		if (aNiveau == 5) {
			vEquipe[2] = equipe1;
			vEquipe[3] = equipe1;
			vFaction[2] = Faction.HUMAINS;
			vFaction[3] = Faction.HUMAINS;
		}

		if (aNiveau == 8) {
			vTourMax = 20;
		}

		if (aNiveau == 11) {
			vFaction[1] = Faction.ROBOTS;
		}

		if (aNiveau == 15) {
			vEquipe[2] = equipe1;
			vEquipe[3] = equipe1;
			vFaction[2] = Faction.ROBOTS;
			vFaction[3] = Faction.HUMAINS;
		}

		// On cree la partie
		Partie partie = new Partie(vTourMax, listeNomPartie.get(pNiveau),
				vEquipe, vFaction);
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
		if (Slatch.partie.getJoueurGagnant().getNumJoueur() == 1) {
			if (aNiveau < listeNomPartie.size() - 1) {
				aNiveau++;
				listeNomPartie.get(aNiveau).setVerrouille(false);
				createDialogue();
			} else { // ecran fin de campagne
				finirCampagne();
			}
		} else {
			createDialogue();
		}
	}

	public void createDialogue() {
		sauvegardeCampagne();
		chargerPartie(aNiveau);
		Slatch.ihm.getPanelFrame().removeAll();

		panel = new PanelDialogueCampagne(aNiveau);
		panel.etapeDialogue();
		panel.getTextArea().addMouseListener(this);
		panel.addMouseListener(this);

		Slatch.ihm.getPanelFrame().add(panel, BorderLayout.CENTER);
		Slatch.ihm.getPanelFrame().updateUI();
		Slatch.ihm.getPanelFrame().repaint();
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

	public void conditionVictoire() {
		if (aNiveau == 2) {
		    Slatch.partie.setJoueurGagnant(1);
			Slatch.partie.setPartieFini(true);
		}
		if (aNiveau == 8) {
			Slatch.partie.setJoueurGagnant(2);
			Slatch.partie.setPartieFini(true);
		}
	}

	public void sauvegardeCampagne() {
		String home = System.getProperty("user.home");
		String path = home + "/.slatch/config/sauvegardeCampagne.txt";
		if(aNiveau != listeNomPartie.size())
			Slatch.ihm.aNiveau = this.aNiveau;
		else
			Slatch.ihm.aNiveau = 0;

		String niveauDebloque = null;

		try {
			Scanner scanner = new Scanner(new File(home
					+ "/.slatch/config/sauvegardeCampagne.txt"));
			if (scanner.hasNextLine()) {
				scanner.nextLine();
				niveauDebloque = scanner.nextLine();
				scanner.close();
			}
			
			if(niveauDebloque != null){
				if(Integer.parseInt(niveauDebloque)<=aNiveau)
					niveauDebloque = String.valueOf(aNiveau);
			}
			else
				niveauDebloque = "0";

			PrintWriter out;
			out = new PrintWriter(new FileWriter(path));
			out.flush();
			out.println(aNiveau);
			out.println(niveauDebloque);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void chargerCampagne(final int lvl) {

		aNiveau = lvl;
		createDialogue();

	}

	public void finirCampagne() {
		aNiveau = listeNomPartie.size();
		panel.setFini();
		panel.repaint();
		Slatch.ihm.getPanelFrame().repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// A supprimer apres
		if (e.getX() >= panel.getWidth() * 9 / 10
				&& e.getY() <= panel.getHeight() * 9 / 10) {
			finirCampagne();
			sauvegardeCampagne();
		} else {
			if (aNiveau < listeNomPartie.size()) {
				panel.etapeDialogue();
				panel.repaint();
				if (panel.getDialogueFinished())
					fermerDialogue();
				else if (e.getX() >= panel.getWidth() / 80
						&& e.getY() >= panel.getHeight() / 50
						&& e.getX() <= panel.getWidth() / 4 + panel.getWidth()
								/ 80
						&& e.getY() <= panel.getHeight() / 10
								+ panel.getHeight() / 50) {
					fermerDialogue();
				}
			} else {
				if (e.getX() >= 2 * panel.getWidth() / 6
						&& e.getY() >= panel.getHeight() / 2
						&& e.getX() <= panel.getWidth() / 3 + 2
								* panel.getWidth() / 6
						&& e.getY() <= panel.getHeight() / 80 * 15
								+ panel.getHeight() / 2)
					Slatch.ihm.passageModeMenuPrincipal();
			}
		}
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
