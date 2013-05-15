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
    public void mouseClicked(MouseEvent e) {
       //IHM_Pannel.clikcount(e.getClickCount()); pour le double click
       Slatch.myIHM.getmyPanel().coordclick(e.getX()+","+e.getY());
    }
  
    @Override
    public void mouseEntered(MouseEvent event) {
 
    }
       
    @Override
    public void mouseExited(MouseEvent event) {

    }
  
    @Override
    public void mousePressed(MouseEvent event) {
          
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }  
}
