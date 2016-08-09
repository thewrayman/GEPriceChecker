import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
//the rt6 package is for RS3. For OSRS scripts, you would use the rt4 package.
import org.powerbot.script.rt6.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(
        name="Dragonhide price checker",
        description="Periodically checks hide tanning profitability"
)
public class MainScript extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();
    public static double lastChecked = 0;
    Util myUT = new Util();
    UserSpecific User = new UserSpecific("name goes here");

    @Override
    public void start() {
        System.out.println("Starting script");
        taskList.addAll(Arrays.asList(new CheckPrice(ctx)));
    }

    @Override
    public void poll() {
        System.out.println("Polling");
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
        //3 mins +- number up to 120 sec
        myUT.Delay(180000,120000);
    }
}