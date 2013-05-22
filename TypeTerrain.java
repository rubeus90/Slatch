import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.net.URL;

public enum TypeTerrain {
        FORET("foret", "arbres", "une foret", 2),
        PLAINE("plaine", "plaine", "une plaine", 1),
        MONTAGNE("montagne", "montagne", "une montagne", 3),
        BATIMENT("batiment", "batiment", "une ville", 2),
        
        //Route
        ROUTEHORIZONTAL("route","routehorizontale","une route",1),
        ROUTEVERTICAL("route","routeverticale","une route",1),
        VIRAGEDROITEBAS("virage","routedroitebas","un virage",1),
        VIRAGEDROITHAUT("virage","routedroitehaut","un virage",1),
        VIRAGEGAUCHEBAS("virage","routegauchebas","un virage",1),
        VIRAGEGAUCHEHAUT("virage","routegauchehaut","un virage",1),
        ROUTETHAUT("carrefour","routeThaut","une route en t",1),
        ROUTETBAS("carrefour","routeTbas","une route en t",1),
        ROUTETDROITE("carrefour","routeTdroite","une route en t",1),
        ROUTETGAUCHE("carrefour","routeTgauche","une route en t",1),
        CARREFOUR("carrefour","routecroissement","un croissemtn",1);
        
        
        private String aNom;
        private String aImage;
        private String aDescription;
        private int aCouverture;
        public HashMap<String,Integer> aCoutDeplacement;
        static final int bonusCouverture = 10;
        private boolean aDependanceJoueur;
        
       
        TypeTerrain(final String pNom, final String pImage, final String pDescription, final int pCouverture,final boolean pDependance){
                aNom = pNom;
                aDependanceJoueur = pDependance;
                aImage = pImage;
                aDescription = pDescription;
                aCouverture = pCouverture;
                aCoutDeplacement = new HashMap<String,Integer> ();
               
                Scanner fichier = null;
               
                try {
                        fichier = new Scanner(getClass().getResource("Config/CoutDeplacement.txt").openStream());
                } catch (IOException e) {
                        e.printStackTrace();
                }
               
                String ligne;
                String[] tab;
               
                while(fichier.hasNextLine()){
                        ligne = fichier.nextLine();
                        tab = ligne.split(",");
                        if(tab[0].equals(pNom)){
                                aCoutDeplacement.put(tab[1],Integer.parseInt(tab[2]));
                        }
                }
                
                fichier.close();
        }
       
        /***
         * Accesseur pour l'attribut aNom
         * @return aNom
         */
        public String getNom(){
                return aNom;
        }
       
        /***
         * Accesseur pour l'attribut aImage
         * @return aImage
         */
        public String getImage(){
                return aImage;
        }
       
        /***
         * Accesseur pour l'attribut aDescription
         * @return aDescription
         */
        public String getDescription(){
                return aDescription;
        }
       
        /***
         * Accesseur pour l'attribut aCouverture
         * @return aCouverture
         */
        public int getCouverture(){
                return aCouverture;
        }
        
        public boolean getDependance(){
            return aDependanceJoueur;
        }
}
