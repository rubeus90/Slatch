import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe qui s'occupe des animations grace
 * a un timer qui lance actionPerformed toutes
 * les 100 ms
 * 
 * @author Jonathan 
 * @version 1.0
 */
public class Animation implements ActionListener
{
    private int aNbTick;
    private long beforeTime;
    private long timeDifference;
    private long sleepTime;
    private boolean animationDone;

    /**
     * Constructeur
     */
    public Animation()
    {
        animationDone = false;
        aNbTick = 0;
    }
    
    /**
     * Methode appelee ttes les 100ms
     */
    public void actionPerformed(ActionEvent event) {
        //System.out.println(aNbTick);
        aNbTick++;
        
        beforeTime = System.currentTimeMillis();        
        while(animationDone == false){              
            // TODO Faire anime
            
            // TODO END
            timeDifference = System.currentTimeMillis() - beforeTime;
            sleepTime = 4 - timeDifference;         
            if(sleepTime < 0)       
                sleepTime = 2;              
            try{                        
                Thread.sleep(sleepTime);        
            }           
            catch(Exception e){             
            }
            beforeTime = System.currentTimeMillis();    
        }
        animationDone = false;
    }
}
