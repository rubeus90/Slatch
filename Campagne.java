import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * @author Ngoc
 *
 */
public class Campagne extends Partie{
	private int aNiveau;
	
	public Campagne(final String pMap, final int pNiveau){
		super(pMap);		
		aNiveau = pNiveau;
	}
	
	public void createDialogue(){
		JFrame frame = Slatch.ihm.getframe();
		System.out.println("Hey i just met you");
		
		System.out.println(Slatch.partie.partieFinie);
		
		if(Slatch.partie.partieFinie){
			System.out.println("and this is crazy");
			frame.remove(Slatch.ihm.getpanelmatrice());
			frame.remove(Slatch.ihm.getpanelinfo());
			
			PanelDialogueCampagne panel = new PanelDialogueCampagne(aNiveau);
			
			frame.setContentPane(panel);
			frame.repaint();
		}
	}
}
