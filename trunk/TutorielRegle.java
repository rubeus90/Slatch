import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielregle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielRegle extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielregle
     */
    public TutorielRegle(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutoregle1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutoregle2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutoregle3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutoregle4",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            
            case 5 :
            afficheImageRedim("tutoregle5",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            
            case 6 :
            afficheImageRedim("tutoregle6",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            
            case 7 :
            afficheImageRedim("tutoregle7",0,0,this.getWidth(),this.getHeight(),g);
            
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
