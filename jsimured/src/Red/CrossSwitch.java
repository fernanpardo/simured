package Red;

/*
 * CrossSwitch.java
 *
 * Created on 27 de septiembre de 2004, 17:09
 * Modified may 2012
 */

/**
 *
 * @author  Oscar, Fernando
 * Clase CrossSwitch, que hereda de Device
 */
public class CrossSwitch extends Device{
    
    /** Creates a new instance of CrossSwitch
     * @param Red La red asociada
     */
    public CrossSwitch(Red unared){
        LaRed = unared;
        Delay = LaRed.CrossDelay;
        Dev = Device.eCrossSwitch;
        //MyBuf = i; ahora esta en el crossbar
    }
}
