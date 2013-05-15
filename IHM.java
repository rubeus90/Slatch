import javax.swing.JFrame;
import java.awt.* ;

/**
 * JFrame est l'IHM du jeu. (herite de JFrame)
 * (Ne definie que le nom de la fenetre, sa taille, si elle est redimenssionable...).
 * 
 * @author Jonathan
 * @version 01
 */
public class IHM  {
    private IHM_Panel panel;
    private JFrame frame;
    
    /**
     * Constructeur qui instancie JFrame du Menu.
     */
    public IHM(final int pTailleX, final int pTailleY, final String pType){
        panel = new IHM_Panel(50);
        frame = new JFrame("SLATCH");
        frame.setTitle("SLATCH");
        frame.setSize(pTailleX,pTailleY);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(myPanel);
        frame.setVisible(true);
        Mouse lecteur = new Mouse();
        frame.addMouseListener(lecteur);
    }
    
    /**
     * Accesseur du Paneau du Menu.
     */
    public IHM_Panel getpanel(){
        return panel;
    }
}
