import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class TutorielDeplacement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielDeplacement extends Tutoriel 
{
    

    /**
     * Constructor for objects of class TutorielDeplacement
     */
    public TutorielDeplacement(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutodeplacement1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutodeplacement2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutodeplacement3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutodeplacement4",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 5 :
            afficheImageRedim("tutodeplacement5",0,0,this.getWidth(),this.getHeight(),g);
            
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
