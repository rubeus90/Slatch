import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.net.URL;

public enum TypeTerrain {
        FORET("foret", "arbres", "d'une foret", 2,0,false),
        PLAINE("plaine", "plaine", "d'une plaine", 1,0,false),
        MONTAGNE("montagne", "montagne", "d'une montagne", 3,0,false),
        BATIMENT("batiment", "batiment", "d'un batiment", 2,30,true),
        QG("qg","qg","d'un quartier general",5,40,true),
        USINE("usine","caserne","d'une usine",3,30,true),
        
        //eau
        EAU("eau","eau","de l'eau",1,0,false),
        RIVEBAS("rivebas","rivebas","de l'eau",1,0,false),
        RIVEHAUT("rivehaut","rivehaut","de l'eau",1,0,false),
        RIVEDROITE("rivedroite","rivedroite","de l'eau",1,0,false),
        RIVEGAUCHE("rivegauche","rivegauche","de l'eau",1,0,false),
        RIVEBASDROITE("rivebasdroite","rivebasdroite","de l'eau",1,0,false),
        RIVEBASGAUCHE("rivebasgauche","rivebasgauche","de l'eau",1,0,false),
        RIVEHAUTDROITE("rivehautdroite","rivehautdroite","de l'eau",1,0,false),
        RIVEHAUTGAUCHE("rivehautgauche","rivehautgauche","de l'eau",1,0,false),
        
        //Route
        ROUTEHORIZONTAL("routehorizontal","routehorizontale","d'ne route",1,0,false),
        ROUTEVERTICAL("routevertical","routeverticale","d'une route",1,0,false),
        VIRAGEDROITEBAS("viragedroitebas","routedroitebas","d'un virage",1,0,false),
        VIRAGEDROITEHAUT("viragedroitehaut","routedroitehaut","d'un virage",1,0,false),
        VIRAGEGAUCHEBAS("viragegauchebas","routegauchebas","d'un virage",1,0,false),
        VIRAGEGAUCHEHAUT("viragegauchehaut","routegauchehaut","d'un virage",1,0,false),
        ROUTETHAUT("routethaut","routeThaut","d'une route",1,0,false),
        ROUTETBAS("routetbas","routeTbas","d'une route",1,0,false),
        ROUTETDROITE("routetdroite","routeTdroite","d'une route",1,0,false),
        ROUTETGAUCHE("routetgauche","routeTgauche","d'une route",1,0,false),
        CARREFOUR("carrefour","routecroisement","d'un croissement",1,0,false);
        
        
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
