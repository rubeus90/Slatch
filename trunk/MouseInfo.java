import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * Write a description of class Mouse_Info here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MouseInfo implements MouseListener
{
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
        Slatch.ihm.getpanelinfo().coordclickUnite(event.getX(),event.getY());
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        
    }  
}
