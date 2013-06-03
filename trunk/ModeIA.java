public enum ModeIA
{
    DEPLOIEMENT(5,3,2,4, 1) , ATTAQUE(4, 2, 5, 2, 1), REPLI(0,4, 1, 5,5);
    
    Influence inf;
    
    private ModeIA(int capture, int defensif, int offensif, int menace, int retraite)
    {
        inf = new Influence(capture, defensif, offensif, menace, retraite);
    }
    
    
    public ModeIA checkMode(int joueur)
    {
        switch(this)
        {
            case DEPLOIEMENT: 
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()>=8)
                {
                    return ATTAQUE;
                }
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()<=2)
                {
                    return REPLI;
                }
                break;
            case ATTAQUE:
                if(Slatch.partie.getJoueur(joueur).getListeUnite().size()<=2)
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
