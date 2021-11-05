/*
 * FifoFlit.java
 * Flit inside Fifo model
 */
package jsimured.red;

public class FifoFlit extends Device {
    
    /** Creates a new instance of FifoFlit */
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
