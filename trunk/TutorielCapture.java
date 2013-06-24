import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielcapture here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielCapture extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielcapture
     */
    public TutorielCapture(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutocapture1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutocapture2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutocapture3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutocapture4",0,0,this.getWidth(),this.getHeight(),g);
            
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
