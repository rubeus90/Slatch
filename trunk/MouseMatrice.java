import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Gere la sourie :
 * 
 * @author Jonathan
 * @version 01
 */
public class MouseMatrice implements MouseListener {    
    @Override
    public void mouseClicked(MouseEvent event) {
        
    }
  
    @Override
    public void mouseEntered(MouseEvent event) {
    }
       
    @Override
    public void mouseExited(MouseEvent event) {
    }
  
    @Override
    public void mousePressed(MouseEvent event) {
        Slatch.ihm.getPanel().coordclickUnite(event.getX(),event.getY());
        Slatch.ihm.getPanel().setObjectif(false);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        
        
    }  
}
