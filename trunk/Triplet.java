/**
 * Write a description of class Triplet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Triplet implements Comparable
{
    int x;
    int y;
    int d;
    public Triplet(int pD, int pX, int pY)
    {
        x=pX; y=pY; d=pD;
    }
    
    public int compareTo(Object o)
    {
        if(this.d<((Triplet)o).d)
        {
            return 1;
        }
        if(this.d>((Triplet)o).d)
        {
            return -1;
        }
        return 0;
    }
}
