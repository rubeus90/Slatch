public enum TypeIA
{
    AGGRESSIVE("Aggressive"), REFLECHIE("Réfléchie"), DESACTIVEE("Désactivée");
    
    String aType;
    private TypeIA(String pS)
    {
        aType = pS;
    }
    
    public void joueTour()
    {
        if(this==AGGRESSIVE)
        {
            AIMaster.joueTour(Slatch.partie.getJoueurActuel());
        }
        else if(this== REFLECHIE)
        {
            StrategieIA.joueTour(Slatch.partie.getJoueurActuel());
        }
    }
    
    @Override
    public String toString()
    {
        return aType;
    }
}
