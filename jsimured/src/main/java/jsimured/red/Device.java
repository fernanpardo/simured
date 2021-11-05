/*
 * Device.java
 * Device model (Device defines all elements in the network model)
 */
package jsimured.red;

public class Device {
    public static final int INIVAL = 999999; // valor absurdo para inicializar
    /** Tipo de dispositivo Cola Fifo*/
    public static final int eFifoFlit = 1; 
    /** Tipo de dispositivo Canal */
    public static final int eChannel = 2;
    /** Tipo de dispositivo interno */
    public static final int eInternal = 3;
    /* Tipo de dispositivo procedador */
    public static final int eProcessor = 4;
    /** Tipo de dispositivo CrossSwitch */
    public static final int eCrossSwitch = 5; 
   /** Retraso acumulado */
    public int Elapsed; 
    /** Nodo al que pertenece */
    //public volatile int MyNode;
    public int MyNode; // a ver que pasa sin volatile (FER)
    /** Apuntara al dispositivo siguiente */
    public Device Next;
    /** APuntara al dispositivo anterior */
    public Device Prev;
    /** Retraso propio del dispositivo */
    public int Delay;
    /** Valor almacenado */
    public int Value;
    /** Numero de canal o de buffer */
    public int MyBuf;
    /** Indica si esta ocupado por un flit anterior */
    public boolean Busy;
    /** Indica el tipo de dispositivo */
    public volatile int Dev;
    /** Paquetes asociado */
    public Packet Paq;
    /** Red asociada */
    public Red LaRed;
    /** Synchro lock for device */
    //public Object Sync; // quito estas cosas de sincronizacion
        /** Creates a new instance of Device */
    public Device() {
        MyNode = INIVAL; // inicializamos con valores absurdos
        MyBuf = INIVAL;
        Elapsed = 0;
        Value = 0;
        Busy = false;
        Next = null;
        Prev = null;
        Paq = null;
        LaRed = null; // Ya se lo asigna la propia red
        //Sync = new Object();
    }
}
