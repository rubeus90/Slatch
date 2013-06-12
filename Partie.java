import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Integer;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class Partie here.
 * 
 * @author (your name) .
 * @version (a version number or a date)
 */
public class Partie
{
    // instance variables - replace the example below with your own
    private int aNbrEquipe;
    public List<Joueur> ListeJoueur;
    private int aRevenuBatiment;
    private int aTourMax;
    private Terrain[][] aTerrain;
    private int aJoueurActuel;
    private int aTour;
    private Map aMap;
    public boolean partieFinie = false;
    private boolean aBrouillard;
    private boolean uneSeulEquipedeJoueur;
    private boolean isCampagne;
    private int aLongueur;
    private int aLargeur;
    private boolean activationAnimation; 
    
    /**
     * Constructeur de MAP d'une nouvelle partie
     */
    public Partie(final int pRevenuBatiment,final int pTourMax, final Map pMap,final boolean pBrouillard,final Faction[] pTabFaction,final Equipe[] pTabEquipe,final boolean[] pTabIA,final boolean activation)
    {
        aMap = pMap;
        isCampagne = false;
        aBrouillard = pBrouillard;
        aJoueurActuel= 1;
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment; 
        activationAnimation=activation;
        initMap(pMap,pTabFaction,pTabEquipe,pTabIA);
        
        ListeJoueur.get(1).benefTour(aRevenuBatiment); 
    }
    
    /**
     * Constructeur de MAP pour mode Campagne
     */
    public Partie(final int pTourMax, final Map pMap,final Equipe[] pTabEquipe)
    {
        aMap= pMap;
         
        //On a active le brouillard, et le boolean campagne + tous les jouers sont des IA sauf le joueur 1 + Paramètre par default
        isCampagne = true;
        aBrouillard = true;
        boolean[] vIA = {false,false,true,true,true};
        aRevenuBatiment = 20;
        aJoueurActuel= 1;
        aTourMax = pTourMax;
        aTour = 1;
        
        //Pour les factions, on se base sur les equipes
        Faction[] pTabFaction ={Faction.HUMAINS,Faction.ROBOTS,Faction.ROBOTS,Faction.ROBOTS}; // tableau par default
        
        for(int i=2;i<4;i++){
            if(pTabEquipe[i].getNumEquipe()==1){ //Si le joueur a des aliés alors ils sont de faction HUMAINS
                pTabFaction[i]=Faction.HUMAINS;
            }
        }  

        initMap(pMap,pTabFaction,pTabEquipe,vIA); 
        
        ListeJoueur.get(1).benefTour(aRevenuBatiment); 
    }
     
    /**
     * Constructeur de chargement d'une sauvegarde d'une Map
     */  
    public Partie(){
        isCampagne = false;
        initMap();
    }
        
    /**
     * CHARGEMENT D'UNE NOUVELLE MAP
     * 
     */
    private void initMap(final Map pMap,final Faction[] pTabFaction,final Equipe[] pTabEquipe,final boolean[] pTabIA){
        //A SUPPRIMER DANS UN FUTUR PROCHE ET LOINTAIN
        aLongueur = aMap.getLongueur();
        aLargeur = aMap.getLargeur();
        
        // OUVERTURE DE LA MAP
        String sMap = "Maps/"+pMap.getFichier()+".txt";
      
        try {
            Scanner vScannerMap = new Scanner(getClass().getClassLoader().getResource(sMap).openStream());
 
            //CREATION DE LA MAP
            aTerrain = new Terrain[aMap.getLongueur()][aMap.getLargeur()];
            
            if(pMap.isDesert()){ //Si la map est un map desert, on rempli de desert
                for(int i=0; i<pMap.getLongueur(); i++){
                    for(int j=0; j<pMap.getLargeur(); j++){
                        aTerrain[i][j] = new Terrain(i, j, 0, TypeTerrain.DESERT);
                    }
                } 
            }
            else{ //Sinon On rempli la carte de plaine 
               for(int i=0; i<pMap.getLongueur(); i++){
                    for(int j=0; j<pMap.getLargeur(); j++){
                        aTerrain[i][j] = new Terrain(i, j, 0, TypeTerrain.PLAINE);
                    }
                }
            }
            
            //Declaration de toutes les variables pour la suite
            int vX, vY, vJoueur;
            String vId;
            String ligne = "";
            String tab[] = null;
            
            List<Unite> lUnite = new ArrayList<Unite>();
            List<Terrain> lUsine = new ArrayList<Terrain>();
            List<Terrain> lBatiment = new ArrayList<Terrain>();
    
            int vBatimentJoueur[] = new int[aMap.getNbrJoueur()+1]; //aNbrJoueur +1 pour prendre en compte le jouer Neutre
    
            //On initialise le tableau de batiment à 0 pour chaque joueur
            for(int i=0; i<aMap.getNbrJoueur()+1; i++){
                vBatimentJoueur[i] = 0;
            }
          
            //On lit le fichier et on l'analyse
            while(vScannerMap.hasNextLine()){
                //System.out.println(ligne);
                ligne = vScannerMap.nextLine();
                tab = ligne.split(":");
                vId = tab[0];
                vX = Integer.parseInt(tab[1]);
                vY = Integer.parseInt(tab[2]);   
                vJoueur = Integer.parseInt(tab[3]);
                
                switch(vId){
                    case "foret": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.FORET); break;
                    case "dune": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.DUNE); break;
                    case "cactus": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.CACTUS); break;
                    case "montagne": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.MONTAGNE); break;
                    case "eau" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.EAU); break;
                    case "rivebas" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBAS); break;
                    case "rivehaut" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUT); break;
                    case "rivedroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEDROITE); break;
                    case "rivegauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEGAUCHE); break;
                    case "rivebasdroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBASDROITE); break;
                    case "rivebasgauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBASGAUCHE); break;
                    case "rivehautdroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUTDROITE); break;
                    case "rivehautgauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUTGAUCHE); break;
                    case "routehorizontal": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTEHORIZONTAL); break;
                    case "routevertical": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTEVERTICAL); break;
                    case "viragedroitebas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEDROITEBAS); break;
                    case "viragedroitehaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEDROITEHAUT); break;
                    case "viragegauchebas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEGAUCHEBAS); break;
                    case "viragegauchehaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEGAUCHEHAUT); break;
                    case "routethaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETHAUT); break;
                    case "routetbas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETBAS); break;
                    case "routetdroite": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETDROITE); break;
                    case "routetgauche": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETGAUCHE); break;
                    case "carrefour": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.CARREFOUR); break;
                    case "batiment":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.BATIMENT); 
                        vBatimentJoueur[vJoueur]+=1;
                         lBatiment.add(aTerrain[vX][vY]);
                        break;
                    case "usine":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.USINE); 
                        vBatimentJoueur[vJoueur]+=1;
                        lUsine.add(aTerrain[vX][vY]);
                        break;
                    case "qg":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.QG); 
                        vBatimentJoueur[vJoueur]+=1;
                        lBatiment.add(aTerrain[vX][vY]);
                        break;
                    case "Commando": 
                        Unite vcommando = new Unite(vX,vY,vJoueur,TypeUnite.COMMANDO);
                        lUnite.add(vcommando);
                        aTerrain[vX][vY].setUnite(vcommando); 
                        break;
                    case "Demolisseur": 
                        Unite demolisseur = new Unite(vX,vY,vJoueur,TypeUnite.DEMOLISSEUR);
                        lUnite.add(demolisseur);
                        aTerrain[vX][vY].setUnite(demolisseur); 
                        break;
                    case "While":
                        Unite vtank = new Unite(vX,vY,vJoueur,TypeUnite.TANK);
                        lUnite.add(vtank);
                        aTerrain[vX][vY].setUnite(vtank); 
                        break;
                    case "Char":
                        Unite vchar = new Unite(vX,vY,vJoueur,TypeUnite.CHAR);
                        lUnite.add(vchar);
                        aTerrain[vX][vY].setUnite(vchar); 
                        break;
                    case "Ingenieur":
                        Unite ingenieur = new Unite(vX,vY,vJoueur,TypeUnite.INGENIEUR);
                        lUnite.add(ingenieur);
                        aTerrain[vX][vY].setUnite(ingenieur); 
                        break;
                    case "Kamikaze":
                        Unite kamikaze = new Unite(vX,vY,vJoueur,TypeUnite.KAMIKAZE);
                        lUnite.add(kamikaze);
                        aTerrain[vX][vY].setUnite(kamikaze); 
                        break;
                    case "Distance":
                        Unite distance = new Unite(vX,vY,vJoueur,TypeUnite.DISTANCE);
                        lUnite.add(distance);
                        aTerrain[vX][vY].setUnite(distance); 
                        break;
                    case "Uml":
                        Unite uml = new Unite(vX,vY,vJoueur,TypeUnite.UML);
                        lUnite.add(uml);
                        aTerrain[vX][vY].setUnite(uml); 
                        break;
                    default: aTerrain[vX][vY] = new Terrain(vX, vY, 0, TypeTerrain.PLAINE);
                }
            }
            
            vScannerMap.close();
            
            ListeJoueur = new ArrayList<Joueur>();
            Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0,pTabEquipe[0],false,""); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
            ListeJoueur.add(JoueurNeutre);
        
            //Ajout des joueur dans l'arrayList
            for(int i=1;i<=aMap.getNbrJoueur();i++)
            {
                ListeJoueur.add(new Joueur(i,pTabFaction[i],vBatimentJoueur[i],pTabEquipe[i],pTabIA[i],""));     
            }
            
            //Creation des liste d'unite ,de batiment de d'usine des Joueurs
            for(Unite vUniteActuel : lUnite){
                int vJ = vUniteActuel.getJoueur();
                ListeJoueur.get(vJ).getListeUnite().add(vUniteActuel);
            }
            for(Terrain vUsineActuel : lUsine){
                int vJ = vUsineActuel.getJoueur();
                ListeJoueur.get(vJ).getListeUsine().add(vUsineActuel);
            }
            for(Terrain vBatActuel : lBatiment){
                int vJ = vBatActuel.getJoueur();
                ListeJoueur.get(vJ).getListeBatiment().add(vBatActuel);
            }
            
            isOneEquipeNonIA();
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * CHARGEMENT D'UNE MAP
     * 6 lignes de paramètres
     * 4 lignes pour chaque joueur
     * enfin la MAP
     */
    private void initMap(){
        //A SUPPRIMER DANS UN FUTUR PROCHE ET LOINTAIN
        aLongueur = aMap.getLongueur();
        aLargeur = aMap.getLargeur();
        
        try {
            Scanner vScannerMap = new Scanner(getClass().getClassLoader().getResource("Maps/sauvegarde.txt").openStream());
            String vNom  = vScannerMap.nextLine();  // 1er ligne
            
            for( Map carte : Map.values() )
            {
                if(carte.getNom().equals(vNom)){
                    aMap=carte;
                }
            }
            
            aJoueurActuel = Integer.parseInt(vScannerMap.nextLine()); //2e ligne
            aTourMax = Integer.parseInt(vScannerMap.nextLine()); // 3e ligne
            aTour = Integer.parseInt(vScannerMap.nextLine()); //4e ligne
            aRevenuBatiment = Integer.parseInt(vScannerMap.nextLine()); // 5e ligne
            String vBrouillard = vScannerMap.nextLine(); // 6e ligne
            
            if(vBrouillard.equals("true"))
                aBrouillard=true;
            else
                aBrouillard=false;
            
            //A CHANGER PLUS TARD
            Equipe equipe0 = new Equipe(0);
            Equipe equipe1 = new Equipe(1);
            Equipe equipe2 = new Equipe(2);
            Equipe equipe3 = new Equipe(3);
            Equipe equipe4 = new Equipe(4);
            
            
            boolean[] vIA = new boolean[aMap.getNbrJoueur()+1];
            int[] vArgent =new int[aMap.getNbrJoueur()+1];
            Equipe[] vEquipe = new Equipe[aMap.getNbrJoueur()+1];
            Faction[] vFaction = new Faction[aMap.getNbrJoueur()+1];
            // Boucle des joueurs, un joueur = 4 lignes
            for(int i=1;i<=aMap.getNbrJoueur();  i++){
                String vIntIA= vScannerMap.nextLine(); // 7e ligne
                if(vIntIA.equals("true"))
                    vIA[i]=true;
                else
                    vIA[i]=false;
                    
                String vStringFaction = vScannerMap.nextLine(); // 8e ligne
                if(vStringFaction.equals("HUMAINS")) 
                    vFaction[i]=Faction.HUMAINS;
                else
                    vFaction[i]=Faction.ROBOTS;
                
               switch(Integer.parseInt(vScannerMap.nextLine())){ // 9e ligne
                   case 1: 
                    vEquipe[i]=equipe1;
                    break;
                   case 2 :
                    vEquipe[i]=equipe2;
                    break;
                   case 3:
                    vEquipe[i]=equipe3;
                    break;
                   case 4:
                    vEquipe[i]=equipe4;
                }
                
               vArgent[i]=Integer.parseInt(vScannerMap.nextLine()); //10e ligne
            }
            
            aTerrain = new Terrain[aMap.getLongueur()][aMap.getLargeur()];
            
            //On rempli la carte de plaine 
            for(int i=0; i<aMap.getLongueur(); i++){
                for(int j=0; j<aMap.getLargeur(); j++){
                    aTerrain[i][j] = new Terrain(i, j, 0, TypeTerrain.PLAINE);
                }
            }       
            
            int vX, vY, vJoueur, vPV,vLvl,vExperience, vIntDejaDeplacee, vIntDejaAttaque;
            boolean vDejaDeplacee=false;
            boolean vDejaAttaque=false;
            String vId;
            String ligne = "";
            String tab[] = null;
    
            int vBatimentJoueur[] = new int[aMap.getNbrJoueur()+1]; //aNbrJoueur +1 pour prendre en compte le jouer Neutre
    
            //On initialise le tableau de batiment à 0 pour chaque joueur
            for(int i=0; i<aMap.getNbrJoueur(); i++){
                vBatimentJoueur[i] = 0;
            }
            
            List<Unite> lUnite = new ArrayList<Unite>();
            List<Terrain> lUsine = new ArrayList<Terrain>();
            List<Terrain> lBatiment = new ArrayList<Terrain>();
            
            //On lit le fichier et on l'analyse
            while(vScannerMap.hasNextLine()){
                ligne = vScannerMap.nextLine();
                tab = ligne.split(":");
                vId = tab[0];
                vX = Integer.parseInt(tab[1]);
                vY = Integer.parseInt(tab[2]);   
                vJoueur = Integer.parseInt(tab[3]);
                vPV = Integer.parseInt(tab[4]);
                vExperience = Integer.parseInt(tab[5]);
                vLvl = Integer.parseInt(tab[6]);
                vIntDejaDeplacee = Integer.parseInt(tab[7]);
                vIntDejaAttaque = Integer.parseInt(tab[8]);
          
                if(vIntDejaDeplacee==1)
                    vDejaDeplacee=true;
                else
                    vDejaDeplacee=false;
                
                if(vIntDejaAttaque==1)
                    vDejaAttaque=true;
                else
                    vDejaAttaque=false;
    
                switch(vId){
                    case "foret": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.FORET); break;
                    case "montagne": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.MONTAGNE); break;
                    case "eau" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.EAU); break;
                    case "rivebas" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBAS); break;
                    case "rivehaut" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUT); break;
                    case "rivedroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEDROITE); break;
                    case "rivegauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEGAUCHE); break;
                    case "rivebasdroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBASDROITE); break;
                    case "rivebasgauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEBASGAUCHE); break;
                    case "rivehautdroite" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUTDROITE); break;
                    case "rivehautgauche" : aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.RIVEHAUTGAUCHE); break;
                    case "routehorizontal": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTEHORIZONTAL); break;
                    case "routevertical": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTEVERTICAL); break;
                    case "viragedroitebas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEDROITEBAS); break;
                    case "viragedroitehaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEDROITEHAUT); break;
                    case "viragegauchebas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEGAUCHEBAS); break;
                    case "viragegauchehaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.VIRAGEGAUCHEHAUT); break;
                    case "routethaut": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETHAUT); break;
                    case "routetbas": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETBAS); break;
                    case "routetdroite": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETDROITE); break;
                    case "routetgauche": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.ROUTETGAUCHE); break;
                    case "carrefour": aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.CARREFOUR); break;
                    case "batiment":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.BATIMENT,vPV); 
                        vBatimentJoueur[vJoueur]+=1;
                         lBatiment.add(aTerrain[vX][vY]);
                        break;
                    case "usine":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.USINE,vPV); 
                        vBatimentJoueur[vJoueur]+=1;
                        lUsine.add(aTerrain[vX][vY]);
                        break;
                    case "qg":
                        aTerrain[vX][vY] = new Terrain(vX, vY, vJoueur, TypeTerrain.QG,vPV); 
                        vBatimentJoueur[vJoueur]+=1;
                        lBatiment.add(aTerrain[vX][vY]);
                        break;
                    case "Commando": 
                        Unite vcommando = new Unite(vX,vY,vJoueur,TypeUnite.COMMANDO,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(vcommando);
                        aTerrain[vX][vY].setUnite(vcommando); 
                        break;
                    case "Demolisseur": 
                        Unite demolisseur = new Unite(vX,vY,vJoueur,TypeUnite.DEMOLISSEUR,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(demolisseur);
                        aTerrain[vX][vY].setUnite(demolisseur); 
                        break;
                    case "While":
                        Unite vtank = new Unite(vX,vY,vJoueur,TypeUnite.TANK,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(vtank);
                        aTerrain[vX][vY].setUnite(vtank); 
                        break;
                    case "Char":
                        Unite vchar = new Unite(vX,vY,vJoueur,TypeUnite.CHAR,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(vchar);
                        aTerrain[vX][vY].setUnite(vchar); 
                        break;
                    case "Ingenieur":
                        Unite ingenieur = new Unite(vX,vY,vJoueur,TypeUnite.INGENIEUR,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(ingenieur);
                        aTerrain[vX][vY].setUnite(ingenieur); 
                        break;
                    case "Distance":
                        Unite distance = new Unite(vX,vY,vJoueur,TypeUnite.DISTANCE,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(distance);
                        aTerrain[vX][vY].setUnite(distance); 
                        break;
                    case "Uml":
                        Unite uml = new Unite(vX,vY,vJoueur,TypeUnite.UML,vPV,vExperience,vLvl,vDejaAttaque,vDejaDeplacee);
                        lUnite.add(uml);
                        aTerrain[vX][vY].setUnite(uml); 
                        break;
                    default: aTerrain[vX][vY] = new Terrain(vX, vY, 0, TypeTerrain.PLAINE);
                }
            }
            
            ListeJoueur = new ArrayList<Joueur>();
            Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0,equipe0,false,""); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
            ListeJoueur.add(JoueurNeutre);
        
            //Ajout des joueur dans l'arrayList
            for(int i=1;i<=aMap.getNbrJoueur();i++)
            {
                ListeJoueur.add(new Joueur(i,vFaction[i],vBatimentJoueur[i],vEquipe[i],vIA[i],""));     
                ListeJoueur.get(i).setArgent(vArgent[i]);
            }
            
            //Creation des liste d'unite ,de batiment de d'usine des Joueurs
            for(Unite vUniteActuel : lUnite){
                int vJ = vUniteActuel.getJoueur();
                ListeJoueur.get(vJ).getListeUnite().add(vUniteActuel);
            }
            for(Terrain vUsineActuel : lUsine){
                int vJ = vUsineActuel.getJoueur();
                ListeJoueur.get(vJ).getListeUsine().add(vUsineActuel);
            }
            for(Terrain vBatActuel : lBatiment){
                int vJ = vBatActuel.getJoueur();
                ListeJoueur.get(vJ).getListeBatiment().add(vBatActuel);
            }
            
            isOneEquipeNonIA();
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sauvegardePartie(String pNom) {
        try {
            File file = new File(getClass().getClassLoader().getResource(pNom).toURI());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(""+ aMap.getNom()); //1er ligne
            bw.newLine();
            bw.write(""+ aJoueurActuel); //2e ligne
            bw.newLine();
            bw.write(""+ aTourMax); // 3e ligne
            bw.newLine();
            bw.write(""+ aTour); // 4e ligne
            bw.newLine();
            bw.write(""+ aRevenuBatiment); // 5e ligne
            bw.newLine();
            bw.write(""+ aBrouillard); // 6e ligne
            bw.newLine();
            for(Joueur joueur: ListeJoueur){
                if(joueur.getNumJoueur() != 0){
                    bw.write(""+joueur.estUneIA());
                    bw.newLine();
                    bw.write(""+joueur.getFaction());
                    bw.newLine();
                    bw.write(""+joueur.getEquipe().getNumEquipe());
                    bw.newLine();
                    bw.write(""+joueur.getArgent());
                    bw.newLine();
                }
            }
            
            for(int i = 0; i<aMap.getLargeur(); i++){
                for(int j = 0; j<aMap.getLongueur(); j++){
                    Terrain terrain = aTerrain[i][j];
                    Unite unite = terrain.getUnite();
                    if(terrain.getType().getNom() != "plaine"){
                        String string = "";
                        string += terrain.getType().getNom()+ ":";
                        string += i+ ":";
                        string += j+ ":";
                        string += terrain.getJoueur() + ":";
                        string += "0:0:0:0:0";
                        bw.write(string);                    
                        bw.newLine();
                    }                    
                    
                    if(terrain.getUnite() != null){
                        String string2 = "";
                        string2 += unite.getType().getNom()+ ":";
                        string2 += i+ ":";
                        string2 += j+ ":";
                        string2 += unite.getJoueur() + ":";                        
                        string2 += unite.getPV()+ ":";
                        string2 += unite.getExperience()+ ":";
                        string2 += unite.getLvl()+ ":";
                        string2 += unite.dejaAttaque() ? "1"+":" : "0"+":";
                        string2 += unite.dejaDeplacee() ? "1"+":" : "0"+":";
   
                        bw.write(string2);
                        bw.newLine();
                    }
                }
            }
            
            bw.close();
            fw.close();
        } catch (URISyntaxException | IOException e) {
            System.out.println("Probleme d'ecriture dans le fichier sauvegarde");
            e.printStackTrace();
        }
    }
    
    public void tourSuivant(){
        aJoueurActuel++;
        
        if(aJoueurActuel > aMap.getNbrJoueur()){
            aJoueurActuel=1;
            aTour++;
        }
        
        if(!ListeJoueur.get(aJoueurActuel).isAlive()){// Si le joueur suivant est mort
            while(!ListeJoueur.get(aJoueurActuel).isAlive()){ // On recherche le prochain vivant
                aJoueurActuel ++;
                if(aJoueurActuel > aMap.getNbrJoueur()){
                    aJoueurActuel=1;
                    aTour++;
                }
            }
        }

        if(aBrouillard){
            Slatch.moteur.Brouillard();
            if(!ListeJoueur.get(aJoueurActuel).estUneIA() && !uneSeulEquipedeJoueur){
                Slatch.ihm.getPanel().setPauseTour(true);
            }
        }
        
        ListeJoueur.get(aJoueurActuel).benefTour(aRevenuBatiment);
        Slatch.ihm.getpanelinfo().paintImmediately(0,0,Slatch.ihm.getpanelinfo().getWidth(),Slatch.ihm.getpanelinfo().getHeight());
    }
    
    public void gagner(final Joueur pJoueur){
       int i=1;
       for(Joueur vJoueur: ListeJoueur){
           if(vJoueur.getNumJoueur()!=0){
               if(vJoueur.isAlive()){
                   if(vJoueur.getEquipe()!=pJoueur.getEquipe()){
                        return;
                   }
               }   
           }
           i++;
       }
       partieFinie=true;
    }
    
    /**********
     * ACCESSEURS ET MUTATEURS
     *********/
     
    /**
     * Accesseur qui renvoi la valeur de la hauteur du plateau
     * @return aHauteur 
     */
    public int getHauteur()
    {
        //return aMap.getLargeur();
        return aLargeur;
    }
    
    /**
     * Accesseur qui renvoi la valeur de la largeur du plateau
     * @return aLargeur 
     */
    public int getLargeur()
    {
        //return aMap.getLongueur();
        return aLongueur;
    }
    
    
    /**
     * Accesseur qui renvoi la valeur le tour actuel du plateau
     * @return aTour 
     */
    public int getTour()
    {
        return aTour;
    }
    
    /**
     * Accesseur qui renvoi la valeur du nombre de tour maximum du plateau
     * @return aTourMax 
     */
    public int getTourMax()
    {
        return aTourMax;
    }
    
    /**
     * Accesseur qui renvoi les revenus par batiments
     * @return aRevenuBatiment
     */
    public int getRevenuBatiment()
    {
        return aRevenuBatiment;
    }
    
    /**
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public int getNbrJoueur()
    {
        return aMap.getNbrJoueur();
    }
    
    public boolean getBrouillard(){
        return aBrouillard;
    }
      
    /**
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public Terrain[][] getTerrain()
    {
        return aTerrain;
    }
    
    public Map getMap()
    {
        return aMap;
    }
    
    public void setCarreauTerrain(int i, int j, Terrain pTerrain){
        aTerrain[i][j] = pTerrain;
    }
    
    /**
     * Accesseur qui renvoi le joueur actuel
     * @return aJoueurActuel
     */
    public int getJoueurActuel(){
        return aJoueurActuel;
    }
    
    /**
     * Accesseur qui prend en parametre un id de joueur et renvoi un joueur
     * @return aJoueur
     */
    public Joueur getJoueur(final int pJoueur){
        return  ListeJoueur.get(pJoueur);
    }
    
     /**
     * Mutateur qui modifie la valeur du tour actuel
     * @param pTour
     */
    public void setTour(final int pTour)
    {
        aTour = pTour;
    }
    
    public List<Terrain> getListeBatimentsEnnemis()
    {
        List<Terrain> l = new ArrayList<Terrain>();
        for(Joueur j:ListeJoueur)
        {
            if(j.getEquipe()!=this.ListeJoueur.get(aJoueurActuel).getEquipe())
            {
                l.addAll(j.getListeBatiment());
                l.addAll(j.getListeUsine());
            }
        }
        return l;
    }
    
    public List<Terrain> getListeBatimentsAllies()
    {
        List<Terrain> l = new ArrayList<Terrain>();
        for(Joueur j:ListeJoueur)
        {
            if(j.getEquipe()==this.ListeJoueur.get(aJoueurActuel).getEquipe())
            {
                l.addAll(j.getListeBatiment());
                l.addAll(j.getListeUsine());
            }
        }
        return l;
    }
    
    public List<Unite> getListeUnitesEnnemies()
    {
        List<Unite> l = new ArrayList<Unite>();
        for(Joueur j:ListeJoueur)
        {
            if(j.getEquipe()!=this.ListeJoueur.get(aJoueurActuel).getEquipe())
            {
                l.addAll(j.getListeUnite());
            }
        }
        return l;
    }
    
    public List<Unite> getListeUnitesAlliees()
    {
        List<Unite> l = new ArrayList<Unite>();
        for(Joueur j:ListeJoueur)
        {
            if(j.getEquipe()==this.ListeJoueur.get(aJoueurActuel).getEquipe())
            {
                l.addAll(j.getListeUnite());
            }
        }
        return l;
    }
    
    public List<Entite> getListeUnitesBatiments()
    {
        List<Entite> l = new ArrayList<Entite>();
        for(Joueur j:ListeJoueur)
        {
            l.addAll(j.getListeUnite());
        }
        for(Joueur j:ListeJoueur)
        {
            l.addAll(j.getListeBatiment());
            l.addAll(j.getListeUsine());
        }
        return l;
    }
    
    /**
     * Mutateur qui modifie la valeur des revenues par batiment
     * @param pRevenuBatiment
     */
    public void setRevenuBatiment(final int pRevenuBatiment)
    {
        aRevenuBatiment = pRevenuBatiment;
    }
    
    public boolean getuneSeulEquipedeJoueur(){
        return uneSeulEquipedeJoueur;
    }
    
    /**
     * Mutateur qui modifie la valeur de l'attribut aJoueurActuel
     * @param pJoueurActuel
     */
    public void setJoueurActuel(final int pJoueurActuel){
        aJoueurActuel = pJoueurActuel;
    }
  
   public int getEquipeJoueurNonIA(){
        for(Joueur vJoueur: ListeJoueur){
            if(!vJoueur.estUneIA() && vJoueur.getEquipe().getNumEquipe()!=0){
                return vJoueur.getEquipe().getNumEquipe();
            }
        }
        return 0;
    }
    
    private void isOneEquipeNonIA(){
        for(Joueur vJoueur: ListeJoueur){
            if(!vJoueur.estUneIA()){
                if(vJoueur.getEquipe().getNumEquipe()!=0){
                    //System.out.println(vJoueur.getEquipe().getNumEquipe()+" ? "+ getEquipeJoueurNonIA());
                    if(vJoueur.getEquipe().getNumEquipe()!=getEquipeJoueurNonIA()) {
                        //System.out.println(uneSeulEquipedeJoueur);
                        uneSeulEquipedeJoueur=false;
                        return;
                    }
                }
             }
        }
        uneSeulEquipedeJoueur=true;
        //System.out.println(uneSeulEquipedeJoueur);
    }
    
    public boolean isCampagne(){
        return isCampagne;
    }
    
    public boolean getActivationAnimation(){
        return activationAnimation;
    }
    
    public void setActivationAnimation(final boolean X){
        activationAnimation=X;
    }
    
   /***
    * ICI UN ENSEMBLE DE METHODE DEDIEE UNIQUEMENT AU CREATEUR DE MAP
    * SUPPRIME DANS LA VERSION FINALE
    * 
    */
   
    /**
     * Consctructeur de MAP pour la creation de Map
     */
    public Partie(final String pMap,final String pLargeur,final String pHauteur){
        aLongueur = Integer.parseInt(pLargeur);
        aLargeur = Integer.parseInt(pHauteur);
        
        Scanner vScannerMap;
        
        try {
            vScannerMap = new Scanner(getClass().getClassLoader().getResource(pMap).openStream());
            aTerrain = new Terrain[Integer.parseInt(pLargeur)][Integer.parseInt(pHauteur)];
        
            //On rempli la carte de plaine 
            for(int i=0; i<Integer.parseInt(pLargeur); i++){
                for(int j=0; j<Integer.parseInt(pHauteur); j++){
                    aTerrain[i][j] = new Terrain(i, j, 0, TypeTerrain.PLAINE);
                }
            }  
        
            vScannerMap.close();
            }
            
        catch (IOException e) {
            e.printStackTrace();
        } 
    }
        
    public void sauvegardePartie(final String pNom,final String pLongueur,final String pLargeur, final String pNbrJoueur) {
        try {
            File file = new File(getClass().getClassLoader().getResource(pNom).toURI());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Merci de recopier ces lignes dans l'enum Map");
            bw.newLine();
            bw.write(""+ pNom); //1er ligne
            bw.newLine();
            bw.write(""+ pLongueur); //2e ligne
            bw.newLine();
            bw.write(""+ pLargeur); // 3e ligne
            bw.newLine();
            bw.write(""+ pNbrJoueur); // 3e ligne
            bw.newLine();
            
            bw.write("Garder ces lignes dans le fichier de votre Map");
            bw.newLine();
            for(int i = 0; i<Integer.parseInt(pLongueur); i++){
                for(int j = 0; j<Integer.parseInt(pLargeur); j++){
                    Terrain terrain = aTerrain[i][j];
                    Unite unite = terrain.getUnite();
                    if(terrain.getType().getNom() != "plaine"){
                        String string = "";
                        string += terrain.getType().getNom()+ ":";
                        string += i+ ":";
                        string += j+ ":";
                        string += terrain.getJoueur() + ":";
                        bw.write(string);                    
                        bw.newLine();
                    }                    
                    
                    if(terrain.getUnite() != null){
                        String string2 = "";
                        string2 += unite.getType().getNom()+ ":";
                        string2 += i+ ":";
                        string2 += j+ ":";
                        string2 += unite.getJoueur();                           
                        bw.write(string2);
                        bw.newLine();
                    }
                }
            }            
            bw.close();
            fw.close();
        }
        catch (URISyntaxException | IOException e) {
            System.out.println("Probleme d'ecriture dans le fichier sauvegarde");
            e.printStackTrace();
        }
    }
}
