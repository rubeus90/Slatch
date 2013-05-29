import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

public class CreationMaps extends JPanel implements ActionListener,	MouseListener {
	private JFrame frame;
	private JPanel panel;
	private PanelMap panelMap;
	private JPanel panelSelection;
	private JPanel panelJoueurs;

	private JScrollPane listScroller;

	private int NbrCaseX;
	private int NbrCaseY;
	private Terrain[][] MATRICE_TEST;
	private Partie partieNew;
	private DefaultListModel<String> listeTerrain;
	private JList<String> liste;

	private JRadioButton joueur1;
	private JRadioButton joueur2;
	private JRadioButton joueur3;
	private JRadioButton joueur4;
	private JRadioButton joueurNeutre;
	private ArrayList<JRadioButton> listeBoutonJoueur;

	private JButton fini;

	private JTextField largeur;
	private JTextField hauteur;
	private JTextField nbrJoueur;
	private JTextField nomMap;

	private String aLargeur;
	private String aHauteur;
	private String aNbrJoueur;
	private String aJoueur;
	private String aNomMap;

	public CreationMaps() {		
		createDialog();

		aJoueur = "0";
		
		partieNew = new Partie("Maps/mapGenere.txt");
		
		frame = new JFrame("Creation maps");

		panel = new JPanel();

		frame.setContentPane(panel);

		panelMap = new PanelMap(partieNew);
		panelMap.setPreferredSize(new Dimension(800,500));

		panelSelection = new JPanel();
		panelSelection.setPreferredSize(new Dimension(400, 500));

		panel.setLayout(new BorderLayout());
		panel.add(panelMap, BorderLayout.CENTER);
		panel.add(panelSelection, BorderLayout.EAST);

		panelSelection.setLayout(new BorderLayout());
		panelSelection.setBorder(BorderFactory
				.createTitledBorder("Les terrains disponibles:"));
		listeTerrain = new DefaultListModel<String>();
		liste = new JList<String>(listeTerrain);

		listScroller = new JScrollPane(liste);
		listScroller.setBorder(null);
		

		panelJoueurs = new JPanel();
		panelJoueurs.setPreferredSize(new Dimension(400, 80));
		joueurNeutre = new JRadioButton("Joueur neutre");
		joueur1 = new JRadioButton("Joueur 1");
		joueur2 = new JRadioButton("Joueur 2");
		joueur3 = new JRadioButton("Joueur 3");
		joueur4 = new JRadioButton("Joueur 4");

		listeBoutonJoueur = new ArrayList<JRadioButton>();
		listeBoutonJoueur.add(joueurNeutre);
		listeBoutonJoueur.add(joueur1);
		listeBoutonJoueur.add(joueur2);
		listeBoutonJoueur.add(joueur3);
		listeBoutonJoueur.add(joueur4);
		
		for(JRadioButton button : listeBoutonJoueur){
			panelJoueurs.add(button);
			button.setBorder(null);
		}
		
		fini = new JButton("FINI!");
		fini.setPreferredSize(new Dimension(400, 50));
		fini.setBorder(null);

		panelSelection.add(fini, BorderLayout.SOUTH);
		panelSelection.add(listScroller, BorderLayout.CENTER);
		panelSelection.add(panelJoueurs, BorderLayout.NORTH);

		showListeTerrain();

		joueurNeutre.addActionListener(this);
		joueur1.addActionListener(this);
		joueur2.addActionListener(this);
		joueur3.addActionListener(this);
		joueur4.addActionListener(this);
		fini.addActionListener(this);

		liste.addMouseListener(this);
		panelMap.addMouseListener(this);

		frame.pack();
		frame.setVisible(true);
	}

	public void showListeTerrain() {
		listeTerrain.removeAllElements();

		TypeTerrain[] liste = TypeTerrain.values();
		for (int i = 0; i < liste.length; i++) {
			listeTerrain.addElement(liste[i].toString());
		}
	}

	@Override
	public void paintComponent(final Graphics g) {
		panelMap.dessineMatrice(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joueurNeutre)
			aJoueur = "0";
		else if (e.getSource() == joueur1)
			aJoueur = "1";
		else if (e.getSource() == joueur2)
			aJoueur = "2";
		else if (e.getSource() == joueur3)
			aJoueur = "3";
		else if (e.getSource() == joueur4)
			aJoueur = "4";
		else if (e.getSource() == fini){
			genererMap();
			System.exit(0);
		}

		for (int i = 0; i < listeBoutonJoueur.size(); i++) {
			listeBoutonJoueur.get(i).setSelected(false);
		}
		
		if(e.getSource() != fini){
			JRadioButton boutonClicked = (JRadioButton) e.getSource();
			boutonClicked.setSelected(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String ID = liste.getSelectedValue();
		if (ID != "USINE" && ID != "BATIMENT" && ID != "QG") {
			aJoueur = "0";
			for (JRadioButton button : listeBoutonJoueur) {
				button.setEnabled(false);
			}
		} else {
			for(JRadioButton button: listeBoutonJoueur)
				button.setEnabled(true);
			for(int i=Integer.parseInt(aNbrJoueur) + 1; i<=4; i++)
				listeBoutonJoueur.get(i).setEnabled(false);
		}

		if (e.getSource() == panelMap) {
			Point point = panelMap.coordclickUnite(e.getX(), e.getY());
			Integer pX = (int) point.getX();
			Integer pY = (int) point.getY();

			if(ID != null){
				partieNew.setCarreauTerrain(pX,pY,new Terrain(pX, pY,
						Integer.parseInt(aJoueur), TypeTerrain.valueOf(ID)));
				panelMap.repaint();
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

	public void genererMap() {
		partieNew.sauvegardePartie("Maps/mapGenere.txt");
	}

	public void initialiseMap() {		
		try {
			File file = new File(getClass().getClassLoader()
					.getResource("Maps/mapGenere.txt").toURI());
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			fw.write("");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("" + aLargeur);
			bw.newLine();
			bw.write("" + aHauteur);
			bw.newLine();
			bw.write("" + aNbrJoueur);
			bw.newLine();
			bw.write("0");
			bw.newLine();
			bw.write("0");
			bw.newLine();
			bw.write("0");
			bw.newLine();
			bw.write("0");
			bw.newLine();
			for (int i = 0; i < Integer.parseInt(aNbrJoueur); i++) {
				bw.write("0");
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public void createDialog() {
		
		largeur = new JTextField(3);
		hauteur = new JTextField(3);
		nbrJoueur = new JTextField(2);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.add(new JLabel("Entrez la largeur de la carte: "));
		panel.add(largeur);
		panel.add(new JLabel("Entrez la hauteur de la carte: "));
		panel.add(hauteur);
		panel.add(new JLabel("Entrez le nombre de joueur (<= 4): "));
		panel.add(nbrJoueur);

		int resultat = JOptionPane.showConfirmDialog(null, panel,
				"Entrez les caracteristiques de la carte",
				JOptionPane.OK_CANCEL_OPTION);
		
		if (resultat == JOptionPane.OK_OPTION) {
			aLargeur = largeur.getText();
			aHauteur = hauteur.getText();
			
			
			if(aLargeur.equals(""))
				aLargeur = "32";
			if(aHauteur.equals(""))
				aHauteur = "18";
			
			String stringNbrJoueur = nbrJoueur.getText();
			if(stringNbrJoueur.equals(""))
				stringNbrJoueur = "2";
			
			Integer nbr = Integer.parseInt(stringNbrJoueur);
			if(nbr > 4)
				nbr = 4;
			aNbrJoueur = nbr.toString();
			initialiseMap();
		}							
			
		else {
			System.exit(0);
		}
	}
}
