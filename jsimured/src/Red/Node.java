package Red;


//import Red.CrossBar;
//import Red.CrossSwitch;

/*
 * Node.java
 *
 * Created on 24 de septiembre de 2004, 11:19
 * Modified may 2012
 */

/**
 *
 * @author  Oscar, Fernando
 */
public class Node {
    private Red LaRed;
    /** Los Buffers NumBuf internos in */
    public miBuffer[] Buf_in; 
    /** Los Buffers NumBuf internos out */
    public miBuffer[] Buf_out;
    public CrossBar ElCrossBar;
    public Processor ElProcOut = new Processor();
    public Processor ElProcIn = new Processor();
    //public Processor ElProcOut; // se debe iniciar con new
    //public Processor ElProcIn;
    
    /** Creates a new instance of Node */
    public Node(Red unared){
        int i;
        LaRed = unared;
        Buf_in = new miBuffer[LaRed.NumBuf+LaRed.NumVirtProcInj]; // Tantos internos como virtuales haya
        Buf_out = new miBuffer[LaRed.NumBuf+LaRed.NumVirtProcEje];
      
        for(i = 0; i < (LaRed.NumBuf+LaRed.NumVirtProcInj); i++){
            Buf_in[i] = new miBuffer(LaRed, i);
        }
        for(i = 0; i < (LaRed.NumBuf+LaRed.NumVirtProcEje); i++){
            Buf_out[i] = new miBuffer(LaRed, i);
        }
        ElCrossBar = new CrossBar(LaRed);
        // Conectamos los buffers de entrada con el CrossBar
        for(i = 0; i <(LaRed.NumBuf+LaRed.NumVirtProcInj); i++){
            Buf_in[i].Fifo[LaRed.NumFlitBuf-1].Next = (CrossSwitch)ElCrossBar.Output[i];
            //Buf_in[i].Fifo[LaRed.NumFlitBuf-1].Next = ElCrossBar.Output[i];
            ElCrossBar.Output[i].Prev =(FifoFlit)Buf_in[i].Fifo[LaRed.NumFlitBuf-1];
            //ElCrossBar.Output[i].Prev =Buf_in[i].Fifo[LaRed.NumFlitBuf-1];
        }
        // Los Switches sobre la marcha (dinamicos)
        // El buffer interno de salida se conecta al procesador
      // ElProcOut = new Processor();
       //ElProcIn = new Processor();
        for(i = 0; i < LaRed.NumVirtProcInj; i++){
            Buf_in[LaRed.NumBuf+i].Fifo[0].Prev = (Processor)ElProcIn;
        }
        for(i = 0; i < LaRed.NumVirtProcEje; i++){
            Buf_out[LaRed.NumBuf+i].Fifo[LaRed.NumFlitBuf-1].Next = (Processor)ElProcOut;
        }
        
        ElProcOut.Prev = (FifoFlit)Buf_out[LaRed.NumBuf].Fifo[LaRed.NumFlitBuf-1];
        ElProcIn.Next = (FifoFlit)Buf_in[LaRed.NumBuf].Fifo[0]; // pero valen el resto tambien
    }
 /*   
 protected void finalize(){
     int i=0, j=0;
      for(i = 0; i < (LaRed.NumBuf+LaRed.NumVirtuals); i++){
            Buf_in[i]= null;
            Buf_out[i] = null;
        }
     Buf_in = null;
     Buf_out = null;
     
     ElProcOut = null;
     ElProcIn = null;
 }
 */
}
