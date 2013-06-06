import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * @author Ngoc
 *
 */
public class Campagne{
	private ArrayList<String> listeNomPartie;
	private int aNiveau;
	
	public Campagne(){
		aNiveau = 0;
		listeNomPartie = new ArrayList<String>();
		
		listeNomPartie.add("Maps/gagner.txt");
		listeNomPartie.add("Maps/gagner.txt");
		
		chargerPartie(0);
	}
	
	public void chargerPartie(int pNiveau){
		Equipe equipe0 = new Equipe(0);
        Equipe equipe1 = new Equipe(1);
        Equipe equipe2 = new Equipe(2);
        
        Equipe[] vEquipe = {equipe0, equipe1, equipe2, equipe1, equipe2};
        boolean[] vIA = {true,true,true,true,true};
		
		Partie partie = new Partie(99, listeNomPartie.get(pNiveau), true, vEquipe, vIA);
		System.out.println("coucou j'ai cree une nouvelle partie");		
		Slatch.partie = partie;
		Slatch.moteur = new Moteur();
//		Slatch.ihm.getpanelmatrice().paintImmediately(0,0,Slatch.ihm.getpanelmatrice().getWidth(),Slatch.ihm.getpanelmatrice().getHeight());		
		
		Slatch.ihm.passageModePartie();
        
        
        if(Slatch.partie.getBrouillard()){
            Slatch.moteur.Brouillard();
        }
        
        if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
        {
            StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
        }
	}
	
	public void suite(){
		System.out.println("coucou j'ai appele la suite");
		aNiveau++;
		chargerPartie(aNiveau);
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
			panel.repaint();
		}
		System.out.println("But Here's my number");
		System.out.println("So call me maybe ?");
	}
}
