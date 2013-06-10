import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

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
    public static Campagne campagne;
    
    
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
        ihm = new IHM_NEW();
    }
    
    private void loadImage()
    {
        loadAutre();
        loadNiveau();
        loadNumero();
        loadTerrain();
        loadUnite();
        loadBoutonMenu();
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
                        image = ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/plaine/"+ terrain.getImage() + i + ".png"));
                        imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                        aImages.put(""+terrain.getImage() + i,image);
                    }
                    
                }
                else
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/plaine/"+ terrain.getImage() +"0.png"));
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
            
            //Pour les humaines
            for(TypeUnite unite : TypeUnite.values())
            {
                for(int i=1;i<5;i++)
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/unite/humain/" + unite.getImage() + i + ".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put(""+unite.getImage() + i,image);
                    aImages.put("HUMAINS"+unite.getImage() + i,image);
                }
            }
            
            //Pour les robots
            for(TypeUnite unite : TypeUnite.values())
            {
                for(int i=1;i<5;i++)
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/unite/robot/" + unite.getImage() + i + ".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put("ROBOTS"+unite.getImage() + i,image);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadNiveau()
    {
        try {
                Image image;
                Image imageRedim;
                for(int i=1;i<=3;i++){
                   
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/niveau"+i+"HUMAINS.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("niveau"+i+"HUMAINS",image);
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

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/5.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("5",image);
                

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/barreinfo2.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("barreinfo",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/noir80.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("noir80",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/noir.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("noir",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/brouillard.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("brouillard",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/yangattaque.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("yangattaque",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/yindeplacement.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("yindeplacement",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/doublevai.png"));
                //imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("doublevai",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/champs.png"));
                aImages.put("champs",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/hacheMap.png"));
                aImages.put("hacheMap",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/jonathan.png"));
                aImages.put("jonathan",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/mapTest.png"));
                aImages.put("mapTest",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/mapTest4.png"));
                aImages.put("mapTest4",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/Parallaxe.png"));
                aImages.put("Parallaxe",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/seleton.png"));
                aImages.put("seleton",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/noir.png"));
                aImages.put("noir",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/wallpaper.png"));
                aImages.put("wallpaper",image);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadBoutonMenu()
    {
        try {
                Image image;
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutoncampagne.png"));
                aImages.put("boutoncampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/slatch.png"));
                aImages.put("slatch",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonrapide.png"));
                aImages.put("boutonrapide",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutontutoriel.png"));
                aImages.put("boutontutoriel",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonmapcreator.png"));
                aImages.put("boutonmapcreator",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutoncredits.png"));
                aImages.put("boutoncredits",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonok.png"));
                aImages.put("boutonok",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonretour.png"));
                aImages.put("boutonretour",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonchargercampagne.png"));
                aImages.put("boutonchargercampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonchargerpartie.png"));
                aImages.put("boutonchargerpartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellecampagne.png"));
                aImages.put("boutonnouvellecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellepartie.png"));
                aImages.put("boutonnouvellepartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/titrecampagne.png"));
                aImages.put("titrecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/titrerapide.png"));
                aImages.put("titrerapide",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/flechegauche.png"));
                aImages.put("flechegauche",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/flechedroite.png"));
                aImages.put("flechedroite",image);
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
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/pvUnite"+i+".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put("pvUnite"+i,image);
                    
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/pvDizaine"+i+".png"));
                    imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    aImages.put("pvDizaine"+i,image);
                }
                
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}