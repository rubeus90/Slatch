import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.Point;

/****************************************
 *
 * CLASSE UNITE
 * Gere les caractéritistiques des unitées
 *
 * @author Thibault
 * @version 1.0
 *
 */
public class Unite extends Entite
{
    private TypeUnite aType; // Correspond au type d'unité : Infanterie, Véhicule etc ...
    private int aLvl; // Correspond au niveau de l'unité
    private TypeAttaque aAttaque;
    private int aDegats;
    private int aDeplacement;
    private int aExperience; // Correspond à l'expérience total de l'unité
    private int aExperienceMax;
    private int aPVMax;
    private int aPV;
    private double aGain; //Compris entre 1 et 2, correspondant au pourcentage d'augmentation des caractéristique à chaque monté de niveau
    private boolean dejaAttaque;
    private boolean dejaDeplacee;
    static final int pallierExperience =20;
    private int aDecaleUniteX=0;
    private int aDecaleUniteY=0;
    private boolean isEvolvable;
   
    /**
     * Constructeur par default de la classe Unite
     * Prend en paramètre :
     * Un int correspondant au coordonné X
     * Un int correspondant au coordonné Y
     * Un int correspondant au Joueur
     * Un TypeUnite
     * @param pX pY pJoueur pType
     */
    public Unite(final int pX,final int pY,final int pJoueur, final TypeUnite pType)
    {
       super(pX,pY,pJoueur);
       aType = pType;
       aGain = pType.getGain();
       aPVMax = pType.getPVMax();
       aPV = pType.getPVMax();
       aDeplacement = pType.getDeplacement();
       aLvl = 1;
       aExperience = 0;
       isEvolvable = false;
       aExperienceMax=pType.getXPUP();
       
       for(TypeAttaque type : TypeAttaque.values()) {
                   
                    if(type.getNom().equals(pType.getAttaque())){
                        aAttaque = type;
                        break;
                    }
                }
            
       aDegats = aAttaque.getDegats();         
       
       dejaAttaque=false;
       dejaDeplacee = false;
    }
    
    /**
     * Constructeur de la classe Unite
     * Prend en paramètre :
     * Un int correspondant au coordonné X
     * Un int correspondant au coordonné Y
     * Un int correspondant au Joueur
     * Un TypeUnite
     * Un int correspondant au point de vie de l'Unite
     * Deux boolean pour savoir si l'unite a été deplacé ou non
     * @param pX pY pJoueur pType
     */
    public Unite(final int pX,final int pY,final int pJoueur, final TypeUnite pType,final int pPV,final int pExperience,final int pLvl,final boolean pDejaAttaque,final boolean pDejaDeplacee)
    {
       super(pX,pY,pJoueur);
       aType = pType;
       aGain = pType.getGain();
       aPVMax = pType.getPVMax();
       if(pPV==0)
        aPV = pType.getPVMax();
       else
        aPV=pPV;
       aDeplacement = pType.getDeplacement();
       if(pLvl == 0){
           aLvl = 1;
       }
       else{
           aLvl = pLvl;
       }
       aExperience = pExperience;
       aExperienceMax=100;
       
       for(TypeAttaque type : TypeAttaque.values()) {
                   
                    if(type.getNom().equals(pType.getAttaque())){
                        aAttaque = type;
                        break;
                    }
                }
            
       aDegats = aAttaque.getDegats();         
       
       dejaAttaque=pDejaAttaque;
       dejaDeplacee = pDejaDeplacee;
    }

     /**
     * Accesseur qui renvoi le type de l'unite
     * @return aType
     */
    public TypeUnite getType(){
        return aType;
    }
    
    /**
     * Accesseur qui renvoie le niveau de l'unite
     * @return aLvl
     */
    public int getLvl(){
        return aLvl;
    }
    
    /**
     * Accesseur pour la valeur de aAttaque
     * @return aAttaque
     */
    public TypeAttaque getAttaque(){
        return aAttaque;
    }
    
    /**
     * Accesseur pour la valeur de aDegats
     * @return aDegats
     */
    public int getDegat(){
        return aDegats;
    }
    
    /**
     * Accesseur pour la valeur de aDeplacement
     * @return aDeplacement
     */
    public int getDeplacement(){
        return aDeplacement;
    }
    
    /**
     * Accesseur qui renvoie l'expérience total de l'unite
     * @return aExperience
     */
    public int getExperience(){
        return aExperience;
    }
    
    /**
     * Accesseur pour aPV
     * @return aPV
     */
    public int getPV()
    {
        return aPV;
    }
    
    
     /**
     * Accesseur pour aPVMax
     * @return aPVMax
     */
    public int getPVMax()
    {
        return aPVMax;
    }
    
    /**
     * Accesseur qui renvoie la valeur du boolean dejaDeplacee
     * @return dejaDeplacee
     */
    public boolean dejaDeplacee()
    {
        return dejaDeplacee;
    }
    
    /**
     * Accesseur qui renvoie la valeur du boolean dejaAttaque
     * @return dejaAttaque
     */
    public boolean dejaAttaque()
    {
        return dejaAttaque;
    }
    
    /**
     * Mutateur de la valeur du boolean evolvable
     * @return isEvolvable
     */
    public boolean isEvolvable(){
        return isEvolvable;
    }
    
    /**
     * Mutateur
     * @param pPV
     */
    public void setPV(final int pPV)
    {
        aPV = pPV;
    }
    
     /**
     * Mutateur de la valeur du boolean dejaDeplacee
     * @param pBoolean
     */
    public void deplacee(final boolean pBoolean)
    {
        dejaDeplacee = pBoolean;
    }
    
     /**
     * Mutateur de la valeur du boolean dejaAttaque
     * @param pBoolean
     */
    public void attaque(final boolean pBoolean)
    {
        dejaAttaque = pBoolean;
    }
    
    /**
    * Methode qui permet l'augmentation ou la diminution de l'experience
    * @param pExperience
    */
    public void addExperience(final double pExperience){
        aExperience+=(int)pExperience;
        if(aExperience > aExperienceMax ){
            isEvolvable = true;
        }
    }
    
    
    /*******
    * Methode qui permet a une unite de monter de niveau
    */
    public void upLvl(){
        if(!isEvolvable){
            return;
        }
        else if(aLvl >=3){
             return;
        }
       aLvl++;
       aExperience-=aExperienceMax;
       aPVMax = (int)(aPVMax*aGain);
       aDegats = (int)(aDegats*aGain);
       
       if(aExperience < aExperienceMax ){
            isEvolvable = false;
       }
       //Sinon le joueur pourra encore monter de niveau le tour prochain
    }
    
    public boolean soigner(int soin)
    {
        if(this.aBesoinDeSoins())
        {
            this.aPV += soin;
            if(aPV>aPVMax){aPV=aPVMax;}
            return true;
        }
        return false;
    }
    
    public boolean aBesoinDeSoins()
    {
        return(aPV<aPVMax);
    }
   
    @Override
    public void dessine (final Graphics g, PanelMatrice pPanel) {
        int pPosHautGaucheX = super.getCoordonneeX()*pPanel.getaLargeurCarreau()+aDecaleUniteX;
        int pPosHautGaucheY = super.getCoordonneeY()*pPanel.getaHauteurCarreau()+aDecaleUniteY;
        int pPosBasDroiteX = (super.getCoordonneeX()+1)*pPanel.getaLargeurCarreau()+aDecaleUniteX;
        int pPosBasDroiteY = (super.getCoordonneeY()+1)*pPanel.getaHauteurCarreau()+aDecaleUniteY;
        
            Image img = Slatch.aImages.get(""+ aType.getImage() + getJoueur());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);

                int vUnite = aPV%10;
                int vDizaine = aPV/10;
                
                Image unite = Slatch.aImages.get("pvUnite"+vUnite);
                Image dizaine = Slatch.aImages.get("pvDizaine"+vDizaine);
                
                g.drawImage(unite, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
                g.drawImage(dizaine, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
               if(Slatch.partie.getJoueurActuel()==this.getJoueur())
               {
                if(!dejaAttaque)
                {
                    Image yang = Slatch.aImages.get("yangattaque");
                    g.drawImage(yang, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
                }
                if(!dejaDeplacee)
                {
                    Image yin = Slatch.aImages.get("yindeplacement");
                    g.drawImage(yin, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
                }
               }

    }
    
    public boolean seSitue(Point p)
    {
        return (this.aCoordonneeX == (int)p.getX() && this.aCoordonneeY == (int)p.getY());
    }
    
    @Override
    public String toString()
    {
        return this.aType.getNom();
    }
    
    public void setDecaleUniteX(final int p) {aDecaleUniteX=p;}
    public void setDecaleUniteY(final int p) {aDecaleUniteY=p;}
}
