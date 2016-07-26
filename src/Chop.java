import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

/**
 * Created by emmet on 03/07/2016.
 */
public class Chop extends Task<ClientContext> {
    private int[] treeIDs = {38616,38627,58006, 38783};

    public Chop(ClientContext ctx){
        super(ctx);
    }
    @Override
    public boolean activate(){
        /*
        if the inventory isn't full,
        if there is a tree near by,
        is the player is idle
         */
        System.out.println("Activate..");
        return ctx.backpack.select().count() < 28
                && !ctx.objects.select().id(treeIDs).isEmpty()
                && ctx.players.local().animation() == -1;
    }
    @Override
    public void execute(){
        System.out.println("Execute..");
        //reuses the tree objects found in activate
        GameObject tree = ctx.objects.nearest().poll();
        if (tree.inViewport()){
            tree.interact("Chop");
        }
        else{
            ctx.movement.step(tree);
            ctx.camera.turnTo(tree);
        }
    }
}
