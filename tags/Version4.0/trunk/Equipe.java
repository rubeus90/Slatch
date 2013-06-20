import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Ngoc
 *
 */
public class Equipe {
	
// 	private boolean haveUnJoueur;
	private ArrayList<Joueur> listeJoueur;
	private int numEquipe;
	
	public Equipe(final int pNumEquipe){
		numEquipe = pNumEquipe;
		listeJoueur = new ArrayList<Joueur>();
	}
	
	public void addJoueur(Joueur pJoueur){
		listeJoueur.add(pJoueur);
	}
	
	public ArrayList<Joueur> getListeJoueur(){
		return listeJoueur;
	}
	
	public boolean haveUnJoueurHumain(){
		for(Joueur joueur: listeJoueur){
			if(!joueur.estUneIA()){
				return true;
			}
		}
		return false;
	}

	public int getNumEquipe() {
		return numEquipe;
	}

	public void setNumEquipe(int numEquipe) {
		this.numEquipe = numEquipe;
	}
}
