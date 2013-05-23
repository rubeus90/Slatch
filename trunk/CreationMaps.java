import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreationMaps extends JPanel{
	public JFrame frame;
	public JPanel panel;
	public PanelMatrice panelMap;
	public JPanel panelTerrain;
	private int NbrCaseX;
    private int NbrCaseY;
    private Terrain[][] MATRICE_TEST;
    private Partie partie;
	
	public CreationMaps(){
		
		partie = new Partie(20, 30, "Maps/mapGenere.txt");
		
		NbrCaseX = partie.getLargeur();
        NbrCaseY = partie.getHauteur();
        MATRICE_TEST=partie.getTerrain();
		
		frame = new JFrame("Creation maps");
		
		panel = new JPanel();
				
		frame.setContentPane(panel);
				
		panelMap = new PanelMatrice();
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
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private void dessineMatrice(final Terrain[][] pMatrice, final Graphics g, PanelMatrice pPanel) {
        for(int i=0; i<NbrCaseX; i++) {
            for(int j=0; j<NbrCaseY; j++) {
                partie.getTerrain()[i][j].dessine(g, pPanel);
            }
        }
    }
	
	@Override
    public void paintComponent (final Graphics g) 
    {
        dessineMatrice(MATRICE_TEST, g, panelMap);
    }    
}
