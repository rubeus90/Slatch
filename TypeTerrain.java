import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.net.URL;

public enum TypeTerrain {
        FORET("foret", "arbres", "une foret", 2,0,false),
        PLAINE("plaine", "plaine", "une plaine", 1,0,false),
        MONTAGNE("montagne", "montagne", "une montagne", 3,0,false),
        BATIMENT("batiment", "batiment", "une ville", 2,20,true),
        USINE("usine","caserne","une usine",3,20,true),
        
        //Route
        ROUTEHORIZONTAL("routehorizontal","routehorizontale","une route",1,0,false),
        ROUTEVERTICAL("routevertical","routeverticale","une route",1,0,false),
        VIRAGEDROITEBAS("viragedroitebas","routedroitebas","un virage",1,0,false),
        VIRAGEDROITHAUT("viragedroithaut","routedroitehaut","un virage",1,0,false),
        VIRAGEGAUCHEBAS("viragegauchebas","routegauchebas","un virage",1,0,false),
        VIRAGEGAUCHEHAUT("viragegauchehaut","routegauchehaut","un virage",1,0,false),
        ROUTETHAUT("routethaut","routeThaut","une route en t",1,0,false),
        ROUTETBAS("routetbas","routeTbas","une route en t",1,0,false),
        ROUTETDROITE("routetdroite","routeTdroite","une route en t",1,0,false),
        ROUTETGAUCHE("routetgauche","routeTgauche","une route en t",1,0,false),
        CARREFOUR("carrefour","routecroisement","un croissemtn",1,0,false);
        
        
        private String aNom;
        private String aImage;
        private String aDescription;
        private int aCouverture;
        public HashMap<String,Integer> aCoutDeplacement;
        static final int bonusCouverture = 10;
        private int aPVMax;
        private boolean aDependanceJoueur;
        
       
        TypeTerrain(final String pNom, final String pImage, final String pDescription, final int pCouverture,final int pPVMax,final boolean pDependance){
                aNom = pNom;
                aDependanceJoueur = pDependance;
                aImage = pImage;
                aDescription = pDescription;
                aCouverture = pCouverture;
                aPVMax = pPVMax;
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
        
        /***
         * Accesseur pour l'attributaDependanceJoueur
         * @return aDependanceJoueur
         */
        public boolean getDependance(){
            return aDependanceJoueur;
        }
        
        /***
         * Accesseur pour l'attribut aCouverture
         * @return aCouverture
         */
        public int getPVMax(){
            return aPVMax;
        }
}
