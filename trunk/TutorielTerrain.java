import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Write a description of class Tutorielterrain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorielTerrain extends Tutoriel 
{
    

    /**
     * Constructor for objects of class Tutorielterrain
     */
    public TutorielTerrain(final String provenance)
    {
        super(provenance);
        this.setPreferredSize(new Dimension(800, 550));
    }

    public void deroulement(final Graphics g)
    {
        
        switch ( aEtape )
        {
            case 1 :
            afficheImageRedim("tutoterrain1",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 2 :
            afficheImageRedim("tutoterrain2",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 3 :
            afficheImageRedim("tutoterrain3",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 4 :
            afficheImageRedim("tutoterrain4",0,0,this.getWidth(),this.getHeight(),g);
            
            break;
            case 5 :
            afficheImageRedim("tutoterrain5",0,0,this.getWidth(),this.getHeight(),g);
            
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
