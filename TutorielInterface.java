import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielinterface here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielInterface extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielinterface
     */
    public TutorielInterface(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutointerface1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutointerface2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutointerface3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutointerface4",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 5 :
            afficheImageRedim("tutointerface5",0,0,this.getWidth(),this.getHeight(),g);
            
            break;

            case 6 :
            afficheImageRedim("tutointerface6",0,0,this.getWidth(),this.getHeight(),g);
            
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
