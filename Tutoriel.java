import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * Write a description of class Tutoriel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tutoriel extends JPanel implements MouseListener
{
      
    protected int aEtape;
    protected String aProvenance;
    
    /**
     * Constructor for objects of class Tutoriel
     */
    public Tutoriel(final String provenance)
    {
       aEtape = 1;
       this.addMouseListener(this);
       aProvenance = provenance;
    }
    
    protected void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        int tailleFleche = this.getWidth()/15;
        int marge = this.getWidth()/20;
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        g.drawImage(Slatch.aImages.get("flechegauche"), this.getWidth()-2*tailleFleche-2*marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this);
        g.drawImage(Slatch.aImages.get("flechedroite"), this.getWidth()-tailleFleche-marge,this.getHeight()-tailleFleche-marge,tailleFleche,tailleFleche, this);
    }
    
    @Override
    public void paintComponent(final Graphics g){
        deroulement(g);
    }
    
    abstract public void deroulement(final Graphics g) ; 
    
    @Override
	public void mouseClicked(MouseEvent e) {
	    int tailleFleche = this.getWidth()/15;
	    int marge = this.getWidth()/20;
	    int pX= e.getX();
	    int pY= e.getY();
	    //fleche gauche
	    if(pX>this.getWidth()-2*tailleFleche-2*marge && pX<this.getWidth()-2*tailleFleche-2*marge+tailleFleche && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
	    {
	        aEtape--; 
	    }
	    // fleche droite
	    if(pX>this.getWidth()-tailleFleche-marge && pX<this.getWidth()-marge && pY>this.getHeight()-tailleFleche-marge && pY<this.getHeight()-marge)
	    {
	        aEtape++; 
	    }
	    repaint();
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
