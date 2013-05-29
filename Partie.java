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
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Partie
{
    // instance variables - replace the example below with your own
    private int aNbrJoueur;
    public List<Joueur> ListeJoueur;
    private int aRevenuBatiment;
    private int aLargeur;
    private int aHauteur;
    private int aTourMax;
    private Scanner aMap;
    private Terrain[][] aTerrain;
    private int aJoueurActuel;
    private int aTour;
    public boolean partieFinie = false;
    

    /**
     * Constructeur d'une nouvelle partie
     */
    public Partie(final int pRevenuBatiment,final int pTourMax, final String pMap)
    {
        try {
            aMap = new Scanner(getClass().getClassLoader().getResource(pMap).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }        
        
        initMap(false);
        aMap.close();
        
        //Dans le cas ou le fichier map n'existe pas
        aJoueurActuel= 1;
        aTourMax = pTourMax;
        aTour = 1;
        aRevenuBatiment = pRevenuBatiment;
        ListeJoueur.get(1).benefTour(aRevenuBatiment);
    }
    
    /**
     * Constructeur de chargement d'une sauvegarde d'une Map
     * 
     */
    public Partie(final String pMap){
         try {
            aMap = new Scanner(getClass().getClassLoader().getResource(pMap).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        initMap(true);
        aMap.close();
    }
    
    /**
     * Methode qui permet le chargement d'une carte depuis un fichier texte et créé les Joueurs
     * iniMap pour nouvelle partie
     */
    private void initMap(final boolean isCharged){
        
        aLargeur = Integer.parseInt(aMap.nextLine());
        aHauteur = Integer.parseInt(aMap.nextLine());
        aNbrJoueur = Integer.parseInt(aMap.nextLine());
        aJoueurActuel = Integer.parseInt(aMap.nextLine());
        aTourMax = Integer.parseInt(aMap.nextLine());
        aTour = Integer.parseInt(aMap.nextLine());
        aRevenuBatiment = Integer.parseInt(aMap.nextLine());
        
        int[] vArgent =new int[aNbrJoueur+1];
        for(int i=1;i<=aNbrJoueur;  i++){
            vArgent[i]=Integer.parseInt(aMap.nextLine());
        }
  
        aTerrain = new Terrain[aLargeur][aHauteur];
        
        //On rempli la carte de plaine 
        for(int i=0; i<aLargeur; i++){
            for(int j=0; j<aHauteur; j++){
                aTerrain[i][j] = new Terrain(i, j, 0, TypeTerrain.PLAINE);
            }
        }       
        
        int vX, vY, vJoueur, vPV,vLvl,vExperience, vIntDejaDeplacee, vIntDejaAttaque;
        boolean vDejaDeplacee=false;
        boolean vDejaAttaque=false;
        String vId;
        String ligne = "";
        String tab[] = null;

        int vBatimentJoueur[] = new int[aNbrJoueur+1]; //aNbrJoueur +1 pour prendre en compte le jouer Neutre

        //On initialise le tableau de batiment à 0 pour chaque joueur
        for(int i=0; i<aNbrJoueur; i++){
            vBatimentJoueur[i] = 0;
        }
        
        List<Unite> lUnite = new ArrayList<Unite>();
        List<Terrain> lUsine = new ArrayList<Terrain>();
        List<Terrain> lBatiment = new ArrayList<Terrain>();
        
        //On lit le fichier et on l'analyse
        while(aMap.hasNextLine()){
            ligne = aMap.nextLine();
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
                case "Tank":
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
        Joueur JoueurNeutre = new Joueur(0,Faction.NEUTRE,0); //Sert a occuper la place 0 dans la liste pour que le numero du joueur coresponde au numero dans la liste
        ListeJoueur.add(JoueurNeutre);
        
        //Ajout des joueur dans l'arrayList
        for(int i=1;i<=aNbrJoueur;i++)
        {
            ListeJoueur.add(new Joueur(i,Faction.HUMAINS,vBatimentJoueur[i]));
            if(isCharged)
                ListeJoueur.get(i).setArgent(vArgent[i]);
        }
        
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
    }
    
    public void tourSuivant(){
        if(aJoueurActuel == aNbrJoueur)
        {
            aJoueurActuel = 1;
            aTour++;
        }
        else
            aJoueurActuel++;
        
        ListeJoueur.get(aJoueurActuel).benefTour(aRevenuBatiment);
        Slatch.ihm.getpanelinfo().paintImmediately(0,0,Slatch.ihm.getpanelinfo().getWidth(),Slatch.ihm.getpanelinfo().getHeight());
    }
    
    public void gagner(final int pJoueur){
       this.partieFinie=true;
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
        return aHauteur;
    }
    
    /**
     * Accesseur qui renvoi la valeur de la largeur du plateau
     * @return aLargeur 
     */
    public int getLargeur()
    {
        return aLargeur;
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
        return aNbrJoueur;
    }
    
    /**
     * Accesseur qui renvoi le nombre de jouer
     * @return aNbrJoueur
     */
    public Terrain[][] getTerrain()
    {
        return aTerrain;
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
    
    /**
     * Mutateur qui modifie la valeur des revenues par batiment
     * @param pRevenuBatiment
     */
    public void setRevenuBatiment(final int pRevenuBatiment)
    {
        aRevenuBatiment = pRevenuBatiment;
    }
    
    /**
     * Mutateur qui modifie la valeur de l'attribut aJoueurActuel
     * @param pJoueurActuel
     */
    public void setJoueurActuel(final int pJoueurActuel){
        aJoueurActuel = pJoueurActuel;
    }
    
    public void sauvegardePartie() {
        try {
            File file = new File(getClass().getClassLoader().getResource("Maps/sauvegarde.txt").toURI());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            fw.write("");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(""+ aLargeur);
            bw.newLine();
            bw.write(""+ aHauteur);
            bw.newLine();
            bw.write(""+ aNbrJoueur);
            bw.newLine();
            bw.write(""+ aJoueurActuel);
            bw.newLine();
            bw.write(""+ aTourMax);
            bw.newLine();
            bw.write(""+ aTour);
            bw.newLine();
            bw.write(""+ aRevenuBatiment);
            bw.newLine();
            for(Joueur joueur: ListeJoueur){
                if(joueur.getNumJoueur() != 0){
                    bw.write(""+joueur.getArgent());
                    bw.newLine();
                }
            }
            
            for(int i = 0; i<aLargeur; i++){
                for(int j = 0; j<aHauteur; j++){
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
                        if(unite.dejaAttaque())
                            string2 += "1"+ ":";
                        else
                            string2 += "0"+ ":";
                        
                        if(unite.dejaDeplacee())
                            string2 += "0";
                        else
                            string2 += "1";
                        bw.write(string2);
                        bw.newLine();
                    }
                }
            }
            
            bw.close();
        } catch (URISyntaxException | IOException e) {
            System.out.println("Probleme d'ecriture dans le fichier sauvegarde");
            e.printStackTrace();
        }
    }
    
    public void chargerPartie(){
        Partie partie = new Partie("Maps/sauvegarde.txt");   
    }
}
