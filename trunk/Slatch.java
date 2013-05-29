import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * @author rubeus
 *
 */
public class Slatch {
    public static Slatch slatch;
    public static IHM_NEW ihm;
    public static Partie partie;
    public static Moteur moteur;
    public static HashMap<String,Image> aImages; // Se trouvera dans Le moteur du jeu quand il y sera avec tout les load
    public static CreationMaps maps;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        slatch = new Slatch();
    }
    
    /**
     * Constructeur du jeu
     */
    public Slatch()
    {
        aImages=new HashMap<String,Image>();
        loadImage();
        partie = new Partie(20,30,"Maps/doublevai.txt");;
        //partie =new Partie("Maps/sauvegarde.txt");
        moteur = new Moteur();
        ihm = new IHM_NEW();
        
        if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
        {
            AIMaster.joueTour(Slatch.partie.getJoueurActuel());
        }
        
       // maps = new CreationMaps();
    }
    
    private void loadImage()
    {
        loadAutre();
        loadNumero();
        loadTerrain();
        loadUnite();
    }
    
    private void loadTerrain()
    {
        try {
            Image image;
            Image imageRedim;
            for(TypeTerrain terrain : TypeTerrain.values())
            {
                if(terrain.getDependance())
                {
                    for(int i=0;i<5;i++)
                    {
                        //System.out.println("TERRAIN DEPENDANCE "+terrain.getImage() + i);
                        image = ImageIO.read(getClass().getClassLoader().getResource("Images/"+ terrain.getImage() + i + ".png"));
                        imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                        aImages.put(""+terrain.getImage() + i,image);
                    }
                    
                }
                else
                {
                    //System.out.println("TERRAIN NONDEPENDANCE "+terrain.getImage());
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/"+ terrain.getImage() +"0.png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put(terrain.getImage()+"0",image);
                }
                
                
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadUnite()
    {
      try {
            Image image;
            Image imageRedim;
            for(TypeUnite unite : TypeUnite.values())
            {
                for(int i=1;i<5;i++)
                {
                   // System.out.println("SLATCH"+unite.getImage() + i);
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/"+ unite.getImage() + i + ".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put(""+unite.getImage() + i,image);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadAutre()
    {
        try {
                Image image;
                Image imageRedim;

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/5.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("5",image);
                

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/4.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("4",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/noir80.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("noir80",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/yangattaque.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("yangattaque",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/yindeplacement.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("yindeplacement",image);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadNumero()
    {
        try {
                Image image;
                Image imageRedim;
                
                for(int i=0;i<10;i++)
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/pvUnite"+i+".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put("pvUnite"+i,image);
                    
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/pvDizaine"+i+".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put("pvDizaine"+i,image);
                }
                
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}