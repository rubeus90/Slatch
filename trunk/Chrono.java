import java.util.TimerTask;
/**
 * Write a description of class Thread here.
 * 
 * @author Jonathan
 * @version (a version number or a date)
 */
public class Chrono extends TimerTask 
{
    private int aSecond = 0;
    private int aMSecond = 0;
    @Override
    public void run() 
    {
        
        aMSecond++;
        
        if(aMSecond==1000) {
            
            aSecond++;
            aMSecond=0;
        }
    }
    
    public void killChrono() 
    {
        this.cancel();
    }
}

