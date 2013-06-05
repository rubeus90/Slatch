import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.net.URL;

public enum TypeTerrain {
        FORET("foret", "arbres", "Foret", 2,0,false),
        PLAINE("plaine", "plaine", "Plaine", 1,0,false),
        MONTAGNE("montagne", "montagne", "Montagne", 3,0,false),
        BATIMENT("batiment", "batiment", "Batiment", 2,30,true),
        QG("qg","qg","QuartierGeneral",5,40,true),
        USINE("usine","caserne","Usine",3,30,true),
        
        //eau
        EAU("eau","eau","Eau",1,0,false),
        RIVEBAS("rivebas","rivebas","Eau",1,0,false),
        RIVEHAUT("rivehaut","rivehaut","Eau",1,0,false),
        RIVEDROITE("rivedroite","rivedroite","Eau",1,0,false),
        RIVEGAUCHE("rivegauche","rivegauche","Eau",1,0,false),
        RIVEBASDROITE("rivebasdroite","rivebasdroite","Eau",1,0,false),
        RIVEBASGAUCHE("rivebasgauche","rivebasgauche","Eau",1,0,false),
        RIVEHAUTDROITE("rivehautdroite","rivehautdroite","Eau",1,0,false),
        RIVEHAUTGAUCHE("rivehautgauche","rivehautgauche","Eau",1,0,false),
        
        //Route
        ROUTEHORIZONTAL("routehorizontal","routehorizontale","Route",1,0,false),
        ROUTEVERTICAL("routevertical","routeverticale","Route",1,0,false),
        VIRAGEDROITEBAS("viragedroitebas","routedroitebas","Virage",1,0,false),
        VIRAGEDROITEHAUT("viragedroitehaut","routedroitehaut","Virage",1,0,false),
        VIRAGEGAUCHEBAS("viragegauchebas","routegauchebas","Virage",1,0,false),
        VIRAGEGAUCHEHAUT("viragegauchehaut","routegauchehaut","Virage",1,0,false),
        ROUTETHAUT("routethaut","routeThaut","Route",1,0,false),
        ROUTETBAS("routetbas","routeTbas","Route",1,0,false),
        ROUTETDROITE("routetdroite","routeTdroite","Route",1,0,false),
        ROUTETGAUCHE("routetgauche","routeTgauche","Route",1,0,false),
        CARREFOUR("carrefour","routecroisement","Croissement",1,0,false);
        
        
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
        
        public boolean dependDuJoueur()
        {
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
