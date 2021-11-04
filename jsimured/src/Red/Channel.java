package Red;

/*
 * Channel.java
 *
 * Created on 24 de septiembre de 2004, 10:43
 * Modified may 2012
 */

/**
 *
 * @author  Oscar, Fernando
 */
/**
 * Clase canal, define el dispositivo Canal
 * Hereda de Device 
 *  @param Red La red asociada
 */
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
