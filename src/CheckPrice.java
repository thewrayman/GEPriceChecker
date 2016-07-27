/**
 * Created by emmet on 21/07/2016.
 */
import org.powerbot.script.rt6.ClientContext;

import java.util.Map;


public class CheckPrice extends Task<ClientContext> {
    public CheckPrice(ClientContext ctx){
        super(ctx);
    }
    private int buyprice = 20000;
    private int sellprice = 10;

    Util myUT = new Util();
    GrandExchange ge = new GrandExchange(ctx);



    @Override
    public boolean activate(){
        /*
            If we have reached the timeout - 1 hour  +- 5 minutes since last check.
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
            myUT.Delay(1000,600);

            //buy the dhides and the leathers
            BuyItemList(myUT.hides);
            SellItemList(myUT.hides);
            BuyItemList(myUT.leathers);
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
                myUT.Delay(5000,1000);
            }
            else{
                System.out.println("Initial buy failed, retrying");
                ge.close();
                ge.open();
                ge.buy(entry.getValue(),1,buyprice);
                myUT.Delay(2000,1000);
            }
        }
        if(!ge.collectToInventory()){
            System.out.println("collect failed, retrying");
            myUT.Delay(5000,1000);
            ge.collectToInventory();
        }
        myUT.Delay(6000,1200);
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
        myUT.Delay(6000,1200);
    }
}
