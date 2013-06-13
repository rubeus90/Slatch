import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.* ;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.*;

import javax.swing.Timer;

/**
 * JFrame est l'IHM du jeu. (herite de JFrame)
 * (Ne definie que le nom de la fenetre, sa taille, si elle est redimenssionable...).
 * 
 * @author Jonathan
 * @version 01
 */
public class IHM_NEW  {
    private JPanel panel;
    private PanelInfo panelInfo;
    private PanelMatrice panelMatrice;
    private PanelMenu panelMenu;
    private JFrame frame;
    private GestionnaireAnimation animation;
    static Timer timer;
    private PanelDialogueCampagne panelDialogueCampagne;
    public List<Map> aListeMap;
    
    /**
     * Constructeur qui instancie JFrame du Menu.
     */
    public IHM_NEW(){
        // Creation des animations
        animation = new GestionnaireAnimation();
        // Creation du timer pour les animations
        timer = new Timer(25, animation);
        //timer.start();

        // Creation de la fenetre : frame
        aListeMap=new ArrayList<Map>();
        for( Map carte : Map.values() )
        {
            aListeMap.add(carte);
        }
        frame = new JFrame("SLATCH");
        frame.setTitle("SLATCH");

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        frame.setContentPane(panel);
        
        //panelInfo = new PanelInfo();
        //panelMatrice = new PanelMatrice(); 
        panelMenu = new PanelMenu();
        
        aListeMap=new ArrayList<Map>();
        for( Map carte : Map.values() )
        {
            aListeMap.add(carte);
        }

        
        panel.setLayout(new BorderLayout());
        //panel.add(panelInfo, BorderLayout.NORTH);
        //panel.add(panelMatrice, BorderLayout.CENTER);     
        panel.add(panelMenu, BorderLayout.CENTER);  
       
        //MouseMatrice lecteurMatrice = new MouseMatrice();
        //panelMatrice.addMouseListener(lecteurMatrice);
        //MouseInfo lecteurInfo = new MouseInfo();
        //panelInfo.addMouseListener(lecteurInfo);
        MouseMenu lecteurMenu = new MouseMenu();
        panelMenu.addMouseListener(lecteurMenu);

        frame.pack();
        frame.setVisible(true);
        panelMenu.setVisible(true);
        
        //panelMatrice.setVisible(true);
        //panelInfo.setVisible(true);
    }
    
    /**
     * Accesseur du Paneau du Menu.
     */
    public PanelInfo getpanelinfo() {
        return panelInfo;
    }
    
    /**
     * Accesseur du Paneau du Menu.
     */
    public PanelMatrice getpanelmatrice() {
        return panelMatrice;
    }
    
    public JFrame getframe() {
        return frame;
    }
    
    /**
     * Accesseur du Paneau du Menu.
     */
    public PanelMatrice getPanel() {
        return panelMatrice;
    }
    
    public PanelMenu getPanelMenu() {
        return panelMenu;
    }
    
    public GestionnaireAnimation getAnimation() {
        return animation;
    }
    
    public void passageModePartie(){
        panelInfo = new PanelInfo();
        panelMatrice = new PanelMatrice();
        MouseMatrice lecteurMatrice = new MouseMatrice();
        panelMatrice.addMouseListener(lecteurMatrice);
        MouseInfo lecteurInfo = new MouseInfo();
        panelInfo.addMouseListener(lecteurInfo);
        
        if(panelMenu != null)
            panel.remove(panelMenu);
        
        panel.add(panelMatrice, BorderLayout.CENTER);
        panel.add(panelInfo, BorderLayout.NORTH);

        panelMenu.setVisible(false);
        panelMatrice.setVisible(true);
        panelInfo.setVisible(true);
        panel.repaint();
        panelMatrice.repaint();
        frame.pack();        
    }
    
    public void passageModeMenuPrincipal(){
        panelMenu = new PanelMenu();
        MouseMenu lecteurMenu = new MouseMenu();
        panelMenu.addMouseListener(lecteurMenu);
           
        if(panelInfo != null)
            panel.remove(panelInfo);
        if(panelMatrice != null)
            panel.remove(panelMatrice);
            
        panel.add(panelMenu, BorderLayout.CENTER);

        panelMenu.setVisible(true);
        panelMatrice.setVisible(false);
        panelInfo.setVisible(false);
        panel.repaint();
        panelMatrice.repaint();
        frame.pack();        
    }
    
    public JPanel getPanelFrame(){
        return panel;
    }
}
