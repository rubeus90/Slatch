import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreationMaps extends JPanel implements ActionListener {
	public JFrame frame;
	public JPanel panel;
	public PanelMap panelMap;
	public JPanel panelTerrain;
	private int NbrCaseX;
    private int NbrCaseY;
    private Terrain[][] MATRICE_TEST;
    private Partie partieNew;
    private JButton buttonMontagne;
    private JButton buttonBatiment;
    private JButton buttonForet;
    private JButton buttonRouteVertical;
    private JButton buttonRouteHorizontal;
    private JButton buttonRouteCarrefour;
    private JButton buttonRouteDroiteBas;
    private JButton buttonRouteDroiteHaut;
    private JButton buttonRouteGaucheBas;
    private JButton buttonRouteGaucheHaut;
    private JButton buttonRouteTHaut;
    private JButton buttonRouteTBas;
    private JButton buttonRouteTGauche;
    private JButton buttonRouteTDroite;
    private JButton buttonJoueur1;
    private JButton buttonJoueur2;
    
    private String aID;
    private String aX;
    private String aY;
    private String aJoueur;
	
	public CreationMaps(){
		
		partieNew = new Partie(25,35,"Maps/mapGenere.txt");
		NbrCaseX = partieNew.getLargeur();
        NbrCaseY = partieNew.getHauteur();
        MATRICE_TEST=partieNew.getTerrain();
		
		frame = new JFrame("Creation maps");
		
		panel = new JPanel();
				
		frame.setContentPane(panel);
				
		panelMap = new PanelMap(partieNew);
		panelMap.setPreferredSize(new Dimension(802,524));
		
		panelTerrain = new JPanel();
				
		panel.setLayout(new BorderLayout());
		panel.add(panelMap, BorderLayout.SOUTH);
		panel.add(panelTerrain, BorderLayout.NORTH);
				
				
		JButton buttonMontagne = new JButton("montagne");
		JButton buttonBatiment = new JButton("batiment");
		JButton buttonForet = new JButton("foret");
		JButton buttonRouteVertical = new JButton("Route verticale");
		JButton buttonRouteHorizontal= new JButton("Route horizontale");
		JButton buttonRouteCarrefour= new JButton("Carrefour");
		JButton buttonRouteDroiteBas= new JButton("Route droiteBas");
		JButton buttonRouteDroiteHaut= new JButton("Route droite haut");
		JButton buttonRouteGaucheBas= new JButton("Route gauche bas");
		JButton buttonRouteGaucheHaut= new JButton("Rouche gauche haut");
		JButton buttonRouteTHaut= new JButton("Route en T haut");
		JButton buttonRouteTBas= new JButton("Route en T bas");
		JButton buttonRouteTGauche= new JButton("Route en T gauche");
		JButton buttonRouteTDroite= new JButton("Route en T droite");
		JButton buttonJoueur1 = new JButton("Joueur 1");
		JButton buttonJoueur2 = new JButton("Joueur 2");
				
		panelTerrain.setLayout(new GridLayout(2,7));
		panelTerrain.add(buttonForet);
		panelTerrain.add(buttonMontagne);
		panelTerrain.add(buttonBatiment);
		panelTerrain.add(buttonRouteCarrefour);
		panelTerrain.add(buttonRouteDroiteBas);
		panelTerrain.add(buttonRouteDroiteHaut);
		panelTerrain.add(buttonRouteGaucheBas);
		panelTerrain.add(buttonRouteGaucheHaut);
		panelTerrain.add(buttonRouteHorizontal);
		panelTerrain.add(buttonRouteTBas);
		panelTerrain.add(buttonRouteTDroite);
		panelTerrain.add(buttonRouteTGauche);		
		panelTerrain.add(buttonRouteTHaut);
		panelTerrain.add(buttonRouteVertical);
		panelTerrain.add(buttonJoueur1);
		panelTerrain.add(buttonJoueur2);
		
		buttonMontagne.addActionListener(this);
		buttonBatiment.addActionListener(this);
		buttonForet.addActionListener(this);
		buttonRouteVertical.addActionListener(this);
		buttonRouteHorizontal.addActionListener(this);
		buttonRouteCarrefour.addActionListener(this);
		buttonRouteDroiteBas.addActionListener(this);
		buttonRouteDroiteHaut.addActionListener(this);
		buttonRouteGaucheBas.addActionListener(this);
		buttonRouteGaucheHaut.addActionListener(this);
		buttonRouteTHaut.addActionListener(this);
		buttonRouteTBas.addActionListener(this);
		buttonRouteTGauche.addActionListener(this);
		buttonRouteTDroite.addActionListener(this);
		buttonJoueur1.addActionListener(this);
		buttonJoueur2.addActionListener(this);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	@Override
    public void paintComponent (final Graphics g) 
    {
        panelMap.dessineMatrice(g);
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Joueur 1")
			aJoueur = "1";
			switch(e.getActionCommand()){
			case "montagne": {
				aID = "montagne";
			}
		}
	}    
}
