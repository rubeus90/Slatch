import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.net.URL;

public enum TypeTerrain {
        FORET("foret", "arbres", "une foret", 2,false),
        PLAINE("plaine", "plaine", "une plaine", 1,false),
        MONTAGNE("montagne", "montagne", "une montagne", 3,false),
        BATIMENT("batiment", "batiment", "une ville", 2,true),
        
        //Route
        ROUTEHORIZONTAL("routehorizontal","routehorizontale","une route",1,false),
        ROUTEVERTICAL("routevertical","routeverticale","une route",1,false),
        VIRAGEDROITEBAS("viragedroitebas","routedroitebas","un virage",1,false),
        VIRAGEDROITHAUT("viragedroithaut","routedroitehaut","un virage",1,false),
        VIRAGEGAUCHEBAS("viragegauchebas","routegauchebas","un virage",1,false),
        VIRAGEGAUCHEHAUT("viragegauchehaut","routegauchehaut","un virage",1,false),
        ROUTETHAUT("routethaut","routeThaut","une route en t",1,false),
        ROUTETBAS("routetbas","routeTbas","une route en t",1,false),
        ROUTETDROITE("routetdroite","routeTdroite","une route en t",1,false),
        ROUTETGAUCHE("routetgauche","routeTgauche","une route en t",1,false),
        CARREFOUR("carrefour","routecroisement","un croissemtn",1,false);
        
        
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