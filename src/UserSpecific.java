import java.lang.reflect.*;
import java.net.*;
import java.util.Enumeration;

public class UserSpecific {
    /*
        A class to store variables which will be generated for each individual instance.
        The purpose of this is so that no two instances are exactly the same.
        This will provide useful values to generate more unique instances to avoid detection.
     */

    String Username;
    String HWID;

    long StartTime;
    long UserHash;

    double DelayMulti;

    UserSpecific(String uname){
        Username = uname;
        StartTime = GetStartTime();
        HWID = GetHWID();
        DelayMulti = GetDelayMulti();
        UserHash = GenerateHash();

        PrintUserAttributes();
    }

    /*
        Get the user's machine address to create a unique value
     */
    private String GetHWID(){
        try{
            Enumeration<NetworkInterface> nwInterface = NetworkInterface.getNetworkInterfaces();
            NetworkInterface nis = nwInterface.nextElement();
            byte[] mac = nis.getHardwareAddress();
            if (mac != null){
                return mac.toString();
            }
            else{
                System.out.println("No mac found");
                return null;
            }
        }
        catch (Exception ex) {
            System.out.println("Failed to get mac: " + ex);
            return null;
        }
    }

    private long GetStartTime(){
        return System.currentTimeMillis();
    }

    /*
        Generates a multiplier based on current time.
        e.g curtime = 12345678
            multi = 1.2345678

        This will be used to have more dynamic delay lengths.
        If a delay of 1000ms was generated, it would become 1234ms
     */
    private double GetDelayMulti(){
        String StartString = Long.toString(StartTime);
        StartString = StartString.substring(0,1) + "." + StartString.substring(1,StartString.length());
        return Double.parseDouble(StartString);
    }

    /*
        Generate a unique hash for this user session which is an XOR of HWID and start time
        Here we convert hex mac address to a decimal long value before XOR'ing.
     */
    private long GenerateHash(){
        long hwidVal = Long.parseLong(HWID,16);
        return (hwidVal ^ StartTime);
    }

    private long GetCurrentEpoch(){
        long epoch = System.currentTimeMillis() / 1000L;
        return epoch;
    }

    /*
        Print out the class attribute and value list
        e.g.
            Username : joebloggs
            StartTime : 12345678
            etc..
     */
    private void PrintUserAttributes(){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            String name = null;
            String value = null;

            try {
                name = field.getName();
                value = (String)field.get(this);
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }

            System.out.printf("%s : %s",
                    name,
                    value
                    );
        }
    }
}
