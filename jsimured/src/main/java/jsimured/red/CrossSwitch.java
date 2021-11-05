/*
 * CrossSwitch.java
 * CrossSwitch model
 */
package jsimured.red;

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
