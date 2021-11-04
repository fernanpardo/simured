package Red;

/*
 * miBuffer.java
 *
 * Created on 24 de septiembre de 2004, 11:25
 * Modified may 2012
 */
/**
 *
 * @author  Oscar, Fernando
 */

public class miBuffer {
    private Red LaRed;
    public FifoFlit[] Fifo; // Ristra de flits en el buffer
    

    /** Creates a new instance of miBuffer */
    public miBuffer(Red unared, int buf){
    int i;
        LaRed = unared;
        Fifo = new FifoFlit[LaRed.NumFlitBuf];
        for(i = 0; i < LaRed.NumFlitBuf; i++){
            Fifo[i] = new FifoFlit(LaRed);
            Fifo[i].MyBuf = buf;
        }
        // Definir los next de los fifoflits
        for(i = 0; i < LaRed.NumFlitBuf-1; i++){
            Fifo[i].Next = Fifo[i+1];
        }
        // Definir los Prev de los fifoflits
        for(i = 1; i < LaRed.NumFlitBuf; i++){
            Fifo[i].Prev = Fifo[i-1];
        }
        // el ultimo fifoflit se conecta fuera en el nodo o red
        Fifo[LaRed.NumFlitBuf-1].Next = null;
        Fifo[0].Prev = null;
    }
    /*
    protected void finalize(){
        int i = 0;
         for(i = 0; i < LaRed.NumFlitBuf; i++)
            Fifo[i] = null;
        Fifo = null;
    }*/
}
