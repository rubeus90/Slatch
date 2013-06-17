import java.util.HashMap;
import javax.imageio.ImageIO;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
    public static HashMap<TypeUnite, Influence[][]> tabInf;
    public static HashMap<String, Font> fonts;
    
    
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
        initialiseMoiLeTableauDInfluence(); 
        ihm = new IHM_NEW();
    }
    
    private void loadImage()
    {
        loadAutre();
        loadNiveau();
        loadNumero();
        loadTerrain();
        loadUnite();
        loadExplosion();
        loadFleche();
        loadBoutonMenu();
        loadFont();
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
                        image = ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/plaine/"+ terrain.getImage() + i + ".png"));
                        aImages.put("TERRE"+terrain.getImage() + i,image);
                        
                        image = ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/desert/"+ terrain.getImage() + i + ".png"));
                        aImages.put("DESERT"+terrain.getImage() + i,image);
                    }       
                }
                else
                {
                    image = terrain.isDesert() ? ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/desert/"+ terrain.getImage() +"0.png")) : ImageIO.read(getClass().getClassLoader().getResource("Images/terrains/plaine/"+ terrain.getImage() +"0.png"));
                    aImages.put(terrain.getImage()+"0",image);
                }
                
                
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadExplosion()
    {
      try {
            Image image;
            
            
            
                for(int i=1;i<10;i++)
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/explosion"+i+".png"));
                    aImages.put("explosion"+ i,image);

                }
   
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadFleche()
    {
      try {
            Image image;
            
            
            
                for(int i=1;i<6;i++)
                {
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/fleche"+i+".png"));
                    aImages.put("fleche"+ i,image);

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
                    aImages.put("HUMAINS"+unite.getImage() + i,image);
                    
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/unite/robot/" + unite.getImage() + i + ".png"));
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
                aImages.put("niveau"+i+"humains",image);
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
                aImages.put("5",image);
                

                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/barreinfo2.png"));
                aImages.put("barreinfo",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/noir80.png"));
                aImages.put("noir80",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/noir.png"));
                aImages.put("noir",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/brouillard.png"));
                aImages.put("brouillard",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/yangattaque.png"));
 
                aImages.put("yangattaque",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/yindeplacement.png"));
                aImages.put("yindeplacement",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/doublevai.png"));
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
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/trait.png"));
                aImages.put("trait",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/campagne/background.png"));
                aImages.put("background",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/campagne/1.png"));
                aImages.put("Darth Vador",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/campagne/2.png"));
                aImages.put("Vador Darth",image);
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
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonchargercampagneon.png"));
                aImages.put("boutonchargercampagneon",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonchargerpartie.png"));
                aImages.put("boutonchargerpartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonchargerpartieon.png"));
                aImages.put("boutonchargerpartieon",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellecampagne.png"));
                aImages.put("boutonnouvellecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellecampagneon.png"));
                aImages.put("boutonnouvellecampagneon",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellepartie.png"));
                aImages.put("boutonnouvellepartie",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/boutonnouvellepartieon.png"));
                aImages.put("boutonnouvellepartieon",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/titrecampagne.png"));
                aImages.put("titrecampagne",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/titrerapide.png"));
                aImages.put("titrerapide",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/flechegauche.png"));
                aImages.put("flechegauche",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/flechedroite.png"));
                aImages.put("flechedroite",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/on.png"));
                aImages.put("on",image);
                
                image = ImageIO.read(getClass().getClassLoader().getResource("Images/menu/off.png"));
                aImages.put("off",image);
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
                    aImages.put("pvUnite"+i,image);
                    
                    image = ImageIO.read(getClass().getClassLoader().getResource("Images/IHM/pvDizaine"+i+".png"));
                    aImages.put("pvDizaine"+i,image);
                }
                
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadFont(){
    	
    	fonts = new HashMap<String, Font>();
    	
    	 try {
             Font blackOps = Font.createFont(Font.TRUETYPE_FONT, new File(getClass()
            		 .getClassLoader().getResource("Config/BlackOps.ttf")
            		 .toURI()));
             
             Font visitor = Font.createFont(Font.TRUETYPE_FONT, new File(getClass()
            		 .getClassLoader().getResource("Config/apl.ttf")
                     .toURI()));
             
             fonts.put("BlackOps", blackOps);
             fonts.put("Visitor", visitor);
         } catch (FontFormatException | IOException | URISyntaxException e) {
             e.printStackTrace();
         }    	 
    }
    
    private void initialiseMoiLeTableauDInfluence()
    {
        tabInf = new HashMap<TypeUnite, Influence[][]>();
        for(TypeUnite type: TypeUnite.values())
        {
            TypeAttaque pyte=null;
            for(TypeAttaque t:TypeAttaque.values())
            {
                if(type.getAttaque().equals(t.getNom()))
                {
                    pyte=t;
                    break;
                }
            }
            int pm = pyte.getTypePortee().getPorteeMax();
            int pu = type.getDeplacement()/10;
            int m = Math.max(pu,pm+1);
            Influence[][] inf = new Influence[m*2+1][m*2+1];
            for(int i=0; i<m*2+1; i++)
            {
                for(int j=0; j<m*2+1; j++)
                {
                    inf[i][j]=new Influence();
                }
            }
            
            for(int i=0; i<=m; i++)
            {
                for(int j=0; j<=i;j++)
                {
                    for(Quad q: Moteur.signes)
                    {  
                        int a = i*q.a, b= j*q.b, c=i*q.c, d = j*q.d;
                        inf[m+a+b][m+c+d].menace=(m+1-i)*5;
                        inf[m+a+b][m+c+d].offensif=(m+1-i)*5;
                        inf[m+a+b][m+c+d].retraite=(m+1-i)*5;
                        inf[m+a+b][m+c+d].defensif=(m+1-i)*5;
                    }
                }
            }
            tabInf.put(type, inf);
        }
    }
}