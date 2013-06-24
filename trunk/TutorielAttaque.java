import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielattaque here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielAttaque extends Tutoriel 
{
	/**
     * Constructor for objects of class Tutorielattaque
     */
    public TutorielAttaque(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutoattaque1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutoattaque2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutoattaque3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutoattaque4",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            
            case 5 :
            afficheImageRedim("tutoattaque5",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            default :
            
            if(aProvenance == "menu")
            {
              Slatch.ihm.passageModeMenuPrincipal();
              Slatch.ihm.getPanelMenu().departMenuTuto();
            
           }
            
        }
    }
}
