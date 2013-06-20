public enum ModeIA
{
    DEPLOIEMENT(5, 5, 2, 2, 1) , ATTAQUE(5, 1, 2, 2, 1), REPLI(1,4, 1, 5,5);
    
    Influence inf;
    
    private ModeIA(int capture, int defensif, int offensif, int menace, int retraite)
    {
        inf = new Influence(capture, defensif, offensif, menace, retraite);
    }
    
    
    public ModeIA checkMode(int joueur)
    {
        int nbTotalBatiments =0;
        for(int i=0; i<=Slatch.partie.getNbrJoueur(); i++)
        {
            nbTotalBatiments+=Slatch.partie.getJoueur(i).getNbreBatiment();
        }
        switch(this)
        {
            case DEPLOIEMENT: 
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()>=4 && Slatch.partie.getJoueur(joueur).getNbreBatiment()>=nbTotalBatiments/(Slatch.partie.getNbrJoueur()*3))
                {
                    return ATTAQUE;
                }
                break;
            case ATTAQUE:
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()<=3)
                {
                    return REPLI;
                }
            case REPLI:
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()>=6)
                {
                    return ATTAQUE;
                }
        }
        return this;
    }
}
