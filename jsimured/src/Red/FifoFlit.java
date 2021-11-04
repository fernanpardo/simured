package Red;

/*
 * FifoFlit.java
 *
 * Created on 24 de septiembre de 2004, 11:26
 * Modified may 2012
 */

/**
 *
 * @author  Oscar, Fernando
 */
public class FifoFlit extends Device {
    
    /** Creates a new instance of FifoFlit */
    //public FifoFlit(Red unared, int buf){
    public FifoFlit(Red unared){
      LaRed = unared;
      Delay = LaRed.FifoDelay;
      Dev = Device.eFifoFlit;
      //MyBuf = buf; // Ahora lo hago fuera por compatibilidad
    }
    /*
     protected void finalize(){
        try{
        super.finalize();
        }
        catch (Throwable e){
            Error("Error en finalize FifoFlit");
        }
    }
    * 
    */
}
