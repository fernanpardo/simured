/*
 * Channel.java
 * Channel model under Device
 *  @param Red the associated network red
 */
package jsimured.red;

public class Channel extends Device{
    /** Indica el turno de espera */
    public int Turn;
    /** Indica si ha sido transmitido*/
    public boolean Transmitted;
    /** Creates a new instance of Channel
     *@param Red La red asociada
     */
    public Channel(Red unared){
        LaRed = unared;
        Delay  = LaRed.ChannelDelay;
        Dev = Device.eChannel;
        Turn = 0;
        Transmitted = false;
    }
    /** Destructor ? */ 
    /*
    protected void finalize(){
        try{
        super.finalize();
        }
        catch (Throwable e){
            Error("Error en finalize Channel");
        }
    }*/
}
