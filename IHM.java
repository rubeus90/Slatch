import javax.swing.JFrame;
import java.awt.* ;

/**
 * JFrame est l'IHM du jeu. (herite de JFrame)
 * (Ne definit que le nom de la fenetre, sa taille, si elle est redimenssionable...).
 * 
 * @author Jonathan
 * @version 01
 */
public class IHM  {
    private IHM_Pannel myPanel = new IHM_Pannel();
    private JFrame myFrame;
    
    /**
     * Constructeur qui instancie JFrame du Menu.
     */
    public IHM(final int pTailleX, final int pTailleY, final String pType){
        myFrame = new JFrame("Menu");
        myFrame.setTitle("Menu");
        myFrame.setSize(pTailleX,pTailleY);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setContentPane(myPanel);
        myFrame.setVisible(true);
        Mouse lecteur = new Mouse();
        myFrame.addMouseListener(lecteur);
    }
    
    /**
     * Accesseur du Paneau du Menu.
     */
    public IHM_Panel getmyPanel(){
        return myPanel;
    }
}
