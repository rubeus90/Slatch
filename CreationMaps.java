// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.GridLayout;
// import java.awt.Image;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.ItemEvent;
// import java.awt.event.ItemListener;
// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// 
// import javax.imageio.ImageIO;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.JToggleButton;
// import javax.swing.border.Border;
// 
// public class CreationMaps extends JPanel implements ActionListener {
// 	public JFrame frame;
// 	public JPanel panel;
// 	public PanelMap panelMap;
// 	public JPanel panelSelection;
// 	private int NbrCaseX;
//     private int NbrCaseY;
//     private Terrain[][] MATRICE_TEST;
//     private Partie partieNew;
//     private JButton buttonMontagne;
//     private JButton buttonBatiment;
//     private JButton buttonForet;
//     private JButton buttonRouteVertical;
//     private JButton buttonRouteHorizontal;
//     private JButton buttonRouteCarrefour;
//     private JButton buttonRouteDroiteBas;
//     private JButton buttonRouteDroiteHaut;
//     private JButton buttonRouteGaucheBas;
//     private JButton buttonRouteGaucheHaut;
//     private JButton buttonRouteTHaut;
//     private JButton buttonRouteTBas;
//     private JButton buttonRouteTGauche;
//     private JButton buttonRouteTDroite;
//     
//     private BoutonJoueur joueur1;
//     private BoutonJoueur joueur2;
//     private BoutonJoueur joueur3;
//     private BoutonJoueur joueur4;
// 
//     
//     private String aID;
//     private String aX;
//     private String aY;
//     private String aJoueur;
// 	
// 	public CreationMaps(){
// 		
// 		partieNew = new Partie(25,35,"Maps/mapGenere.txt");
// 		NbrCaseX = partieNew.getLargeur();
//         NbrCaseY = partieNew.getHauteur();
//         MATRICE_TEST=partieNew.getTerrain();
// 		
// 		frame = new JFrame("Creation maps");
// 		
// 		panel = new JPanel();
// 				
// 		frame.setContentPane(panel);
// 				
// 		panelMap = new PanelMap(partieNew);
// 		panelMap.setPreferredSize(new Dimension(800,500));
// 		
// 		panelSelection = new JPanel();
// 		panelSelection.setPreferredSize(new Dimension(400,500));
// 		panelSelection.setBackground(Color.BLACK);
// 				
// 		panel.setLayout(new BorderLayout());
// 		panel.add(panelMap, BorderLayout.CENTER);
// 		panel.add(panelSelection, BorderLayout.EAST);
// 		
// 		panelSelection.setLayout(new BorderLayout());
// 		joueur1 = new BoutonJoueur("Joueur1");
// 		joueur2 = new BoutonJoueur("Joueur2");
// 		joueur3 = new BoutonJoueur("Joueur3");
// 		joueur4 = new BoutonJoueur("Joueur4");
// 		ArrayList<BoutonJoueur> liste = new ArrayList<BoutonJoueur>();
// 		liste.add(joueur1);
// 		liste.add(joueur2);
// 		liste.add(joueur3);
// 		liste.add(joueur4);
// 		
// 		for(BoutonJoueur joueur : liste){
// 			joueur.setImage("OFF");
// 			joueur.addItemListener(new ItemListener() {
// 				   public void itemStateChanged(ItemEvent ev) {
// 				      if(ev.getStateChange()==ItemEvent.SELECTED){
// 				    	  System.out.println("button is selected");
// //				    	  joueur.setImage("ON");
// 				      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
// 				    	  System.out.println("button is not selected");
// //				    	  joueur.setImage("OFF");
// 				      }
// 				   }
// 				});
// 		}
// 		
// 		
// 		
// 				
// 				
// 		JButton buttonMontagne = new JButton("montagne");
// 		JButton buttonBatiment = new JButton("batiment");
// 		JButton buttonForet = new JButton("foret");
// 		JButton buttonRouteVertical = new JButton("Route verticale");
// 		JButton buttonRouteHorizontal= new JButton("Route horizontale");
// 		JButton buttonRouteCarrefour= new JButton("Carrefour");
// 		JButton buttonRouteDroiteBas= new JButton("Route droiteBas");
// 		JButton buttonRouteDroiteHaut= new JButton("Route droite haut");
// 		JButton buttonRouteGaucheBas= new JButton("Route gauche bas");
// 		JButton buttonRouteGaucheHaut= new JButton("Rouche gauche haut");
// 		JButton buttonRouteTHaut= new JButton("Route en T haut");
// 		JButton buttonRouteTBas= new JButton("Route en T bas");
// 		JButton buttonRouteTGauche= new JButton("Route en T gauche");
// 		JButton buttonRouteTDroite= new JButton("Route en T droite");
// 		JButton buttonJoueur1 = new JButton("Joueur 1");
// 		JButton buttonJoueur2 = new JButton("Joueur 2");
// 				
// //		panelSelection.setLayout(new GridLayout(2,7));
// //		panelSelection.add(buttonForet);
// //		panelSelection.add(buttonMontagne);
// //		panelSelection.add(buttonBatiment);
// //		panelSelection.add(buttonRouteCarrefour);
// //		panelSelection.add(buttonRouteDroiteBas);
// //		panelSelection.add(buttonRouteDroiteHaut);
// //		panelSelection.add(buttonRouteGaucheBas);
// //		panelSelection.add(buttonRouteGaucheHaut);
// //		panelSelection.add(buttonRouteHorizontal);
// //		panelSelection.add(buttonRouteTBas);
// //		panelSelection.add(buttonRouteTDroite);
// //		panelSelection.add(buttonRouteTGauche);		
// //		panelSelection.add(buttonRouteTHaut);
// //		panelSelection.add(buttonRouteVertical);
// //		panelSelection.add(buttonJoueur1);
// //		panelSelection.add(buttonJoueur2);
// 		
// 		buttonMontagne.addActionListener(this);
// 		buttonBatiment.addActionListener(this);
// 		buttonForet.addActionListener(this);
// 		buttonRouteVertical.addActionListener(this);
// 		buttonRouteHorizontal.addActionListener(this);
// 		buttonRouteCarrefour.addActionListener(this);
// 		buttonRouteDroiteBas.addActionListener(this);
// 		buttonRouteDroiteHaut.addActionListener(this);
// 		buttonRouteGaucheBas.addActionListener(this);
// 		buttonRouteGaucheHaut.addActionListener(this);
// 		buttonRouteTHaut.addActionListener(this);
// 		buttonRouteTBas.addActionListener(this);
// 		buttonRouteTGauche.addActionListener(this);
// 		buttonRouteTDroite.addActionListener(this);
// 		buttonJoueur1.addActionListener(this);
// 		buttonJoueur2.addActionListener(this);
// 		
// 		frame.pack();
// 		frame.setVisible(true);
// 	}
// 	
// 	
// 	@Override
//     public void paintComponent (final Graphics g) 
//     {
//         panelMap.dessineMatrice(g);
//     }
// 
// 
// 	@Override
// 	public void actionPerformed(ActionEvent e) {
// 		if(e.getActionCommand() == "Joueur 1")
// 			aJoueur = "1";
// 			switch(e.getActionCommand()){
// 			case "montagne": {
// 				aID = "montagne";
// 			}
// 		}
// 	}    
// 	
// 	public class BoutonJoueur extends JToggleButton
// 	{
// 		private Image img;
// 		private String nomButton;
// 
// 		  public BoutonJoueur(String pNom){
// 		    super();
// 		    nomButton = pNom;
// 		    this.setPreferredSize(new Dimension(300, 80));
// 		    this.setBackground(Color.BLACK);
// 		    this.setBorder(null);
// //		    try {
// //			      img = ImageIO.read(getClass().getClassLoader().getResource("Images/BoutonJoueur1ON.png"));
// //			} catch (IOException e) {
// //			      e.printStackTrace();
// //			}
// 		  }
// 
// 		  @Override
// 		  public void paintComponent(Graphics g){		  {
// 			  
// 			  Graphics2D g2d = (Graphics2D)g;
// 			  g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
// 		  	  }
// 		  }
// 		  
// // 		  public void setImage(String etat){
// // 			  try {
// // 				img = ImageIO.read(getClass().getClassLoader().getResource("Images/Bouton" + nomButton + etat + ".png"));
// // 			} catch (IOException e) {
// // 				e.printStackTrace();
// // 			}			  
// // 		  }
// 		  
// 		  public String getNomButton(){
// 			  return nomButton;
// 		  }
// 	}
// }
// 	
// 
