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
    private static IHM_Pannel Menu1 = new IHM_Pannel();
    private JFrame myPanel;
    
    /**
     * Constructeur qui instancie JFrame du Menu.
     */
    public IHM(final int pTailleX, final int pTailleY, final String pType){
        myPanel = new JFrame("Menu");
        myPanel.setTitle("Menu");
        myPanel.setSize(pTailleX,pTailleY);
        myPanel.setResizable(false);
        myPanel.setLocationRelativeTo(null);
        myPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myPanel.setContentPane(Menu1);
        myPanel.setVisible(true);
        Mouse lecteur = new Mouse();
        myPanel.addMouseListener(lecteur);
    }
    
    /**
     * Accesseur du Panneau du Menu.
     */
    public static IHM_Pannel getMenu1(){
        return Menu1;
    }
}

