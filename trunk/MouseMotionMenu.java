import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionMenu implements MouseMotionListener {

    @Override
        public void mouseMoved(MouseEvent event) {
            Slatch.ihm.getPanelMenu().coordsurvolBouton(event.getX(),event.getY());
        }
        
    @Override
        public void mouseDragged(MouseEvent event) {

        }
}