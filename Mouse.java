import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Gere la sourie :
 * 
 * @author Jonathan
 * @version 01
 */
public class Mouse implements MouseListener {    
    @Override
    public void mouseClicked(MouseEvent event) {
       //IHM_Pannel.clikcount(e.getClickCount()); pour le double click
    }
  
    @Override
    public void mouseEntered(MouseEvent event) {
 
    }
       
    @Override
    public void mouseExited(MouseEvent event) {

    }
  
    @Override
    public void mousePressed(MouseEvent event) {
 
        Slatch.ihm.getPanel().coordclickAppui(event.getX()+","+event.getY());
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        Slatch.ihm.getPanel().coordclickUnite(event.getX()+","+event.getY());
        
    }  
}
