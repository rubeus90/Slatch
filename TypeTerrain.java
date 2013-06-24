import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.lang.Integer;

public enum TypeTerrain {
        FORET("foret", "arbres", "Foret", 2,0,false,false),
        PLAINE("plaine", "plaine", "Plaine", 0,0,false,false),
        MONTAGNE("montagne", "montagne", "Montagne", 3,0,false,false),
        BATIMENT("batiment", "batiment", "Batiment", 2,30,true,false),
        QG("qg","qg","QG",5,40,true,false),
        USINE("usine","caserne","Usine",3,30,true,false),
        
        DESERT("desert","desert","Desert",0,0,false,true),
        CACTUS("cactus","cactus","Cactus",2,0,false,true),
        DUNE("dune","dune","Dune",3,0,false,true),
        
        //eau
        EAU("eau","eau","Eau",1,0,false,false),
        RIVEBAS("rivebas","rivebas","Eau",1,0,false,false),
        RIVEHAUT("rivehaut","rivehaut","Eau",1,0,false,false),
        RIVEDROITE("rivedroite","rivedroite","Eau",1,0,false,false),
        RIVEGAUCHE("rivegauche","rivegauche","Eau",1,0,false,false),
        RIVEBASDROITE("rivebasdroite","rivebasdroite","Eau",1,0,false,false),
        RIVEBASGAUCHE("rivebasgauche","rivebasgauche","Eau",1,0,false,false),
        RIVEHAUTDROITE("rivehautdroite","rivehautdroite","Eau",1,0,false,false),
        RIVEHAUTGAUCHE("rivehautgauche","rivehautgauche","Eau",1,0,false,false),
        
        //Route
        ROUTEHORIZONTAL("routehorizontal","routehorizontale","Route",0,0,false,false),
        ROUTEVERTICAL("routevertical","routeverticale","Route",0,0,false,false),
        VIRAGEDROITEBAS("viragedroitebas","routedroitebas","Virage",0,0,false,false),
        VIRAGEDROITEHAUT("viragedroitehaut","routedroitehaut","Virage",0,0,false,false),
        VIRAGEGAUCHEBAS("viragegauchebas","routegauchebas","Virage",0,0,false,false),
        VIRAGEGAUCHEHAUT("viragegauchehaut","routegauchehaut","Virage",0,0,false,false),
        ROUTETHAUT("routethaut","routeThaut","Route",0,0,false,false),
        ROUTETBAS("routetbas","routeTbas","Route",0,0,false,false),
        ROUTETDROITE("routetdroite","routeTdroite","Route",0,0,false,false),
        ROUTETGAUCHE("routetgauche","routeTgauche","Route",0,0,false,false),
        CARREFOUR("carrefour","routecroisement","Croissement",0,0,false,false),
        
        PONTHORIZONTAL("ponthorizontal","ponthorizontal","Pont",1,0,false,false),
        PONTVERTICAL("pontvertical","pontvertical","Pont",1,0,false,false);
        
        private String aNom;
        private String aImage;
        private String aDescription;
        private int aCouverture;
        public HashMap<String,Integer> aCoutDeplacement;
        static final int bonusCouverture = 10;
        private int aPVMax;
        private boolean aDependanceJoueur;
        private boolean aDesert;
        
       
        TypeTerrain(final String pNom, final String pImage, final String pDescription, final int pCouverture,final int pPVMax,final boolean pDependance,final boolean pDesert){
                aNom = pNom;
                aDependanceJoueur = pDependance;
                aImage = pImage;
                aDescription = pDescription;
                aCouverture = pCouverture;
                aPVMax = pPVMax;
                aDesert=pDesert;
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
        
        public boolean isDesert()
        {
            return aDesert;
        }
        
        /***
         * Accesseur pour l'attribut aCouverture
         * @return aCouverture
         */
        public int getPVMax(){
            return aPVMax;
        }
}
