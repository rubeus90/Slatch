import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielevolution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielEvolution extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielevolution
     */
    public TutorielEvolution(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutoevolution1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutoevolution2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutoevolution3",0,0,this.getWidth(),this.getHeight(),g);
            
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
