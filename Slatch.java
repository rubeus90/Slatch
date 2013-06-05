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

        Partie partieRapide = new Partie(20,30,"Maps/doublevai.txt",true);
        
//        partie = new Partie("Maps/mapGenere.txt");
//        partie =new Partie("Maps/sauvegarde.txt");
        
//        Tutoriel tuto = new Tutoriel("Maps/tutoriel.txt");
      // Campagne campagne = new Campagne("Maps/mapTest.txt", 1);
        
        partie = partieRapide;
        moteur = new Moteur();
        ihm = new IHM_NEW();
        
        //moteur.Brouillard();
           
        //maps = new CreationMaps("hacheMap");
       
        if(partie.getBrouillard()){
            moteur.Brouillard();
        }
        
        //moteur.Brouillard();
        
        if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA())
        {
            StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
        }
    }
    
    private void loadImage()
    {
        loadAutre();
        loadNiveau();
        loadNumero();
        loadTerrain();
        loadUnite();
        loadBoutonMenu();
       // loadPlaines();
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
    
    private void loadPlaines()
    {
       try {
            Image image;
            Image imageRedim;
            for(int i =0;i<=4;i++)
            {
                
                        //System.out.println("TERRAIN DEPENDANCE "+terrain.getImage() + i);
                        image = ImageIO.read(getClass().getClassLoader().getResource("Images/plaine0" + i + ".png"));
                        imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                        aImages.put("plaine0" + i,image);
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
    
    private void loadNiveau()
    {
        try {
                Image image;
                Image imageRedim;
                for(int i=1;i<=3;i++){
                   
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/niveau"+i+"HUMAINS.png"));
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

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/5.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("5",image);
                

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/barreinfo2.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("barreinfo",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/noir80.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("noir80",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/brouillard.png"));
                imageRedim = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                aImages.put("brouillard",image);
                
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
    
    private void loadBoutonMenu()
    {
        try {
                Image image;
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutoncampagne.png"));
                aImages.put("boutoncampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/slatch.png"));
                aImages.put("slatch",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonrapide.png"));
                aImages.put("boutonrapide",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutontutoriel.png"));
                aImages.put("boutontutoriel",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonmapcreator.png"));
                aImages.put("boutonmapcreator",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutoncredits.png"));
                aImages.put("boutoncredits",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonok.png"));
                aImages.put("boutonok",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonretour.png"));
                aImages.put("boutonretour",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonchargercampagne.png"));
                aImages.put("boutonchargercampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonchargerpartie.png"));
                aImages.put("boutonchargerpartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonnouvellecampagne.png"));
                aImages.put("boutonnouvellecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/boutonnouvellepartie.png"));
                aImages.put("boutonnouvellepartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/titrecampagne.png"));
                aImages.put("titrecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/titrerapide.png"));
                aImages.put("titrerapide",image);
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
    
    public void setPartie(Partie pPartie){
        partie = pPartie;
    }
}