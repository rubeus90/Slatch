import javax.swing.JFrame;
import java.awt.* ;


/**
 * JFrame du Menu du jeu. (herite de JFrame)
 * (Ne definit que le nom de la fenetre, sa taille, si elle est redimenssionable...).
 * 
 * Permet de lancer "Age of ESIEE Terminal" et "Age of ESIEE Arcade".
 * Creer une JFrame myMenu grace a la classe MenuPanneau qui detaille le
 * contenu de la fenetre Menu.
 * Creer un Clavier grace à la classe Clavier du package pkg_Clavier
 * qui permet d'utiliser le clavier.
 * 
 * Faites "clic droit" sur la classe Game puis "new Game()" pour lancer le jeu.
 * 
 * @author MERCANDALLI PRUDENT
 * @version 01/2013
 * @since   09/2012
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
        myMenu.setSize(1000, 700);
        myMenu.setResizable(false);
        myMenu.setLocationRelativeTo(null);
        myMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
        myMenu.setContentPane(Menu1);    
        myMenu.setVisible(true);
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
