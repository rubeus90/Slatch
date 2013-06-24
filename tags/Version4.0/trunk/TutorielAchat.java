import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielachat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielAchat extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielachat
     */
    public TutorielAchat(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutoachat1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutoachat2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutoachat3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutoachat4",0,0,this.getWidth(),this.getHeight(),g);
            
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
