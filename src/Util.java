import java.lang.reflect.Array;
import java.util.*;
import org.powerbot.script.Random;

/**
 * Created by emmet on 21/07/2016.
 */
public class Util {
    /*
        timeout = 1hour in mili
        hashmaps containing item information
        xmlPath for future extensibility in reading items from xml
            TO-DO: another xml path for writing out price updates to for web portal parsing.
     */
    public double timeout = 1000 * 60 * 60;
    protected Map<String,Integer> hides = new HashMap<String, Integer>();
    protected Map<String,Integer> leathers = new HashMap<String, Integer>();
    protected Map<String,Integer> notes = new HashMap<String, Integer>();

    String xmlPath = "C:\\GeCheckerXML";


    Util() {
        hides.put("Green Dragonhide", 1753);
        hides.put("Blue Dragonhide", 1751);
        hides.put("Red Dragonhide", 1749);
        hides.put("Black Dragonhide", 1747);

        leathers.put("Green Dragon Leather", 25605);
        leathers.put("Blue Dragon Leather", 25606);
        leathers.put("Red Dragon Leather", 25607);
        leathers.put("Black Dragon Leather", 25608);

        notes.put("Green Dragonhide", 1754);
        notes.put("Blue Dragonhide", 1752);
        notes.put("Red Dragonhide", 1750);
        notes.put("Black Dragonhide", 1748);

        System.out.println("No of hides: "+ hides.size());

    }
    public int GenerateDelay(int delay)
    {
        return Random.nextInt(delay*-1,delay);
    }

    /**
     *
     * @param curTime = the current system time
     * @param lastTime = the last time the system checked prices
     * @return true if 1 hour +- random Delay has elapsed since lastTime
     */
    public boolean CheckTimeout(double curTime, double lastTime){
        boolean timedOut = false;
        System.out.println("entering checktimeout with curtime: "+curTime+" | lasttime: "+lastTime);
        if ((curTime - (timeout+GenerateDelay(300000)))>=0){timedOut=true;}
        System.out.println("exiting checktimeout with result "+timedOut);
        return timedOut;
    }

    protected void BreakEvent(){

    }
    //sale history. widget 137 -> component 133 -> component 68
    //widget 1638 -> component 23 -> item name
    //              -> component 24 -> price

    protected void Delay(int basetime, int delaytime){
        try{
            //3 mins +- number up to 120 sec
            Thread.sleep(basetime + GenerateDelay(delaytime));
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

}
