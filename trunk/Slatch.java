/**
 * @author rubeus
 *
 */
public class Slatch {

    public static Slatch slatch;
    public static IHM ihm;
    public static Partie partie;
    public static Moteur moteur;
    //private HashMap<String,Image> aImages; // Se trouvera dans Le moteur du jeu quand il y sera avec tout les load
    
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
        partie = new Partie(20, 30, "Maps/doublevai.txt");
        moteur = new Moteur();
        ihm = new IHM(802,524);
    }
    
    private void loadImage()
    {
        
    }
    
    private void loadTerrain()
    {
     /*   try {
            for(TypeTerrain terrain : TypeTerrain.Values())
            {
                for(int i=0;i<5;i++)
                {
                    
                    Image aImage = ImageIO.read(getClass().getClassLoader().getResource("Images/"+ terrain.aType.getImage() + i + ".png"));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}