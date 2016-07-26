import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

/**
 * Created by emmet on 03/07/2016.
 */

public abstract class Task<C extends ClientContext> extends ClientAccessor<C>{
    public Task(C ctx) {
        super(ctx);
    }
    public abstract boolean activate();
    public abstract void execute();
}
