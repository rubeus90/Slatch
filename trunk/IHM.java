import javax.swing.JFrame;
import java.awt.* ;


/**
 * JFrame est l'IHM du jeu. (herite de JFrame)
 * (Ne definit que le nom de la fenetre, sa taille, si elle est redimenssionable...).
 * 
 * @author Jonathan
 * @version 01
 */
public class IHM extends JFrame {
    public static IHM_Pannel Menu1 = new IHM_Pannel();
    private JFrame myMenu;
    private static Menu newMenu;
    
    /**
     * Constructeur qui instancie JFrame du Menu.
     */
    public IHM(){
        myMenu = new JFrame("Menu");
        myMenu.setTitle("Menu");
        myMenu.setSize(800,500);
        myMenu.setResizable(false);
        myMenu.setLocationRelativeTo(null);
        myMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
        myMenu.setContentPane(Menu1);    
        myMenu.setVisible(true);
        Mouse lecteur = new Mouse();
        myMenu.addMouseListener(lecteur);
    }
    
    /**
     * Accesseur du Panneau du Menu.
     */
    public static IHM_Pannel getMenu1(){
        return Menu1;
    }
    
    /**
     * Permet d'etre (compilee et) executee sans Bluej :
     * cette methode lance le jeu.
     */
    public static void main( String[] pArgs ) {
        newMenu = new Menu();
    }
}

