/**
 * Created by emmet on 21/07/2016.
 */
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.Random;
import sun.applet.Main;

import java.util.Map;


public class CheckPrice extends Task<ClientContext> {
    public CheckPrice(ClientContext ctx){
        super(ctx);
    }
    private double timeout = 1000 * 60 * 60;
    private int buyprice = 20000;
    private int sellprice = 10;
                        //1 hour
    Util myUT = new Util();
    GrandExchange ge = new GrandExchange(ctx);



    @Override
    public boolean activate(){
        /*
        if the inventory isn't full,
        if there is a tree near by,
        is the player is idle
         */
        double curTime =  System.currentTimeMillis();
        System.out.println("Checking activate at.. "+ curTime);
        return  myUT.CheckTimeout(curTime, MainScript.lastChecked);
    }
    @Override
    public void execute(){
        System.out.println("Execute..");
        //reuses the tree objects found in activate

        if(ge.open()){
            myUT.delay(1000,600);

            //buy the dhides and the leathers
            BuyItemList(myUT.hides);
            myUT.delay(5000,1200);
            SellItemList(myUT.hides);
            myUT.delay(5000,1200);
            BuyItemList(myUT.leathers);
            myUT.delay(5000,1200);
            SellItemList(myUT.leathers);

        }
        System.out.println("Finished price check! Pausing..");
        MainScript.lastChecked = System.currentTimeMillis();
    }

    //method to purchase hides and leather by passing in the hashmap for their list
    //for each item passed in the map, it will put in an offer of 20k for 1 each
    private void BuyItemList(Map<String,Integer> map){
            System.out.println("buying list..");
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            System.out.println("\nbuying "+entry.getKey());
            if(ge.buy(entry.getValue(),1,buyprice)){
                System.out.println("finished buying");
                myUT.delay(5000,1000);
            }
            else{
                System.out.println("Initial buy failed, retrying");
                ge.close();
                ge.open();
                ge.buy(entry.getValue(),1,buyprice);
                myUT.delay(5000,1000);
            }
        }
        if(!ge.collectToInventory()){
            System.out.println("collect failed, retrying");
            myUT.delay(5000,1000);
            ge.collectToInventory();
        }
    }

    //method to sell the hides and leather in the inventory based on the map passed in
    //for each item, it will try to sell it for 10gp ea
    private void SellItemList(Map<String,Integer> map){
        //for each hide
        System.out.println("entering sell list");
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            //here we pass the id and the name to the sell function
            System.out.println("selling "+entry.getKey());
            ge.sell(entry.getValue(),entry.getKey() + 1,1,sellprice);
        }
        //collect all of the hides to inventory
        ge.collectToInventory();
    }
}
