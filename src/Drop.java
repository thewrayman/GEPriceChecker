import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

/**
 * Created by emmet on 03/07/2016.
 */
public class Drop extends Task<ClientContext> {
    private int logId = 1511;

    public Drop(ClientContext ctx){
        super(ctx);
    }
    @Override
    public boolean activate(){
        /*
        if the inventory isn't full,
        if there is a tree near by,
        is the player is idle
         */
        return ctx.backpack.select().count() == 28;
    }
    @Override
    public void execute(){
        for (Item i : ctx.backpack.id(logId)){
            i.interact("Drop");
        }
    }
}
