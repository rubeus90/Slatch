import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Font;

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
    private int aDecaleUniteX=0;
    private int aDecaleUniteY=0;
    private boolean isEvolvable;
    private int pVaffiche;
    private int blessure;
    private boolean check;
    private boolean mort;
    private boolean lvlup;
    private int numeroFleche;
    private int numeroExplosion;
    public Influence[][] mapInfluence;
    /**
     * Constructeur par default de la classe Unite
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
       blessure=0;
       isEvolvable = false;
       aExperienceMax=pType.getXPUP();
       pVaffiche = aPV;
       check=false;
       
       for(TypeAttaque type : TypeAttaque.values()) {
            if(type.getNom().equals(pType.getAttaque())){
                aAttaque = type;
                break;
            }
       }
            
       mapInfluence = Slatch.tabInf.get(this.aType);
       aDegats = aAttaque.getDegats();         
       mort = false;
       lvlup=false;
       dejaAttaque=false;
       dejaDeplacee = false;
    }
    
    /**
     * Constructeur du chargement de Map
     * @param pX pY pJoueur pType
     */
    public Unite(final int pX,final int pY,final int pJoueur, final TypeUnite pType,final int pPV,final int pExperience,final int pLvl,final boolean pDejaAttaque,final boolean pDejaDeplacee,final boolean pIsEvoluable)
    {
       super(pX,pY,pJoueur);
       aType = pType;
       aGain = pType.getGain();
       aPVMax = pType.getPVMax();
       aPV=pPV;
       aDeplacement = pType.getDeplacement();
       aLvl = pLvl;
       isEvolvable = pIsEvoluable;
       
       //Pour remettre les seuils et les dégats comme il faut
       for(int i=1;i<pLvl;i++){
           aPVMax = (int)(aPVMax*aGain);
           aDegats = (int)(aDegats*aGain);
           aExperienceMax = (int)(aExperienceMax*aGain);
       
           if(aGain==2) // Cas de l'ingenieur
                aExperienceMax = (int)(aExperienceMax*aGain*2);
       }
       
       aExperience = pExperience;
       aExperienceMax=pType.getXPUP();
       
       for(TypeAttaque type : TypeAttaque.values()) {          
            if(type.getNom().equals(pType.getAttaque())){
                aAttaque = type;
                break;
            }
       }
       mapInfluence = Slatch.tabInf.get(this.aType);     
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
    
    public int getPVaffiche()    {return pVaffiche;}
    
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
        if(aExperience >= aExperienceMax && aLvl <3){
            isEvolvable = true;
        }
    }
    
    public boolean estUneUnite()
    {
        return true;
    }
    
    
    /*******
    * Methode qui permet a une unite de monter de niveau
    */
    public void upLvl(){
       aLvl++;
       aExperience-=aExperienceMax;
       aPVMax = (int)(aPVMax*aGain);
       aDegats = (int)(aDegats*aGain);
       aPV = (int)(aPV*aGain);
       aExperienceMax = (int)(aExperienceMax*aGain);
       
       if(aGain==2){
           aExperienceMax = (int)(aExperienceMax*aGain*2);
       }
       
       isEvolvable = false;
    }
    
    public int soigner(int soin)
    {
        int vXP; //XP pour l'ingenieur qui vient de soigner
        if(this.aBesoinDeSoins())
        {
            this.aPV += soin;
            vXP = soin;
            if(aPV>aPVMax){
                vXP = aPVMax + soin -aPV ;
                aPV=aPVMax;
            }
            return vXP;
        }
        return 0;
    }
    
    public boolean aBesoinDeSoins()
    {
        return(aPV<aPVMax);
    }
    
    public boolean estLowHP()
    {
        return(this.aPV<this.aPVMax/3);
    }
    
    public int getDiffPV()
    {
        return aPV-aPVMax;
    }
   
    @Override
    public void dessine (final Graphics g, PanelMatrice pPanel) {
        int pPosHautGaucheX = super.getCoordonneeX()*pPanel.getaLargeurCarreau()+aDecaleUniteX;
        int pPosHautGaucheY = super.getCoordonneeY()*pPanel.getaHauteurCarreau()+aDecaleUniteY;
        int pPosBasDroiteX = (super.getCoordonneeX()+1)*pPanel.getaLargeurCarreau()+aDecaleUniteX;
        int pPosBasDroiteY = (super.getCoordonneeY()+1)*pPanel.getaHauteurCarreau()+aDecaleUniteY;
        int pPosMidGaucheX = super.getCoordonneeX()*pPanel.getaLargeurCarreau()+pPanel.getaLargeurCarreau()/2;
        int pPosMidGaucheY = super.getCoordonneeY()*pPanel.getaHauteurCarreau()+pPanel.getaHauteurCarreau()/2;
        
            Image img = Slatch.aImages.get(Slatch.partie.getJoueur(getJoueur()).getFaction()+""+ aType.getImage() + getJoueur());
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
            if(!Slatch.partie.getActivationAnimation())
            {pVaffiche=aPV;}
                int vUnite = pVaffiche%10;
                int vDizaine = pVaffiche/10;
                
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

        
                if(check)
                {
                    int pDegats = blessure;
                    
                    Font font = new Font("Helvetica", Font.BOLD, (Slatch.ihm.getPanel().getWidth()/Slatch.partie.getMap().getLongueur())/2);
                    g.setFont(font);
                    
                    if (pDegats <0)
                    {
                        g.setColor(Color.red);
                        String stringDegats = ""+pDegats;
                        g.drawString(stringDegats, pPosHautGaucheX, pPosMidGaucheY);
                    }
                    else if(pDegats >0)
                    {
                        g.setColor(Color.green);
                        String stringDegats = "+"+pDegats;
                        g.drawString(stringDegats, pPosHautGaucheX, pPosMidGaucheY);
                    }
                   
                }
                
                if(isEvolvable && !dejaAttaque)
                {
                    Image explosion = Slatch.aImages.get("yengevolution");
                    g.drawImage(explosion, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
                }
                
                if(mort)
                {
                    Image explosion = Slatch.aImages.get("explosion"+numeroExplosion);
                    g.drawImage(explosion, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
                }
                
                if(lvlup)
                {
                    Image explosion = Slatch.aImages.get("fleche"+numeroFleche);
                    g.drawImage(explosion, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, pPanel);
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
    public void addDecaleUniteX(final int p) {aDecaleUniteX+=p;}
    public void addDecaleUniteY(final int p) {aDecaleUniteY+=p;}
    public int getDecaleUniteX() {return aDecaleUniteX;}
    public int getDecaleUniteY() {return aDecaleUniteY;}
    public void setCheck(final boolean X) {check=X;}
    public boolean getCheck() { return check;}
    public void setPVaffiche(final int X) {pVaffiche=X;}
    public void setMort(final boolean X) {mort=X;}
    public void setLvlup(final boolean X) {lvlup=X;}
    public void setNumeroExplosion( final int X) {numeroExplosion = X;}
    public void setNumeroFleche( final int X) {numeroFleche = X;}
    public int getExpMax() {return aExperienceMax;}
    //public int getPVaffiche() { return pVaffiche;}
    
    public boolean peutCapturer()
    {
        return this.aType.peutCapturer();
    }
    
    public boolean peutSoigner()
    {
        return this.aType.peutSoigner();
    }
    
    public void setBlessure(final int pBlessure)
    {
        blessure = pBlessure;
    }
    
    
}