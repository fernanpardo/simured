package Red;

/*
 * Processor.java
 *
 * Created on 24 de septiembre de 2004, 10:40
 * modified may 2012
 */

/**
 *
 * @author  Oscar, Fernando
 */
public class Processor extends Device {
    public Packet PackList;
    public int PackLength;
    /** Creates a new instance of Processor */
    public Processor() {
        Delay = 0;        
        Dev = Device.eProcessor;
        Next = this; // si mismo; sospechoso pues se está inicializando
        Prev = this; // si mismo; sospechoso pues se está inicializando
        PackList=null;
        PackLength=0;      
    }
    
public void putPacket(Packet Pack)
{
 Packet aux;
 aux=PackList;
 Pack.Next=null;
 Pack.Prev=null;
 if (aux==null)
 {
   PackList=Pack;
 }
 else
 {
   while (aux.Next!=null)
   {
    aux=aux.Next;
   }
   Pack.Prev=aux;
   aux.Next=Pack;
 }
}
/*    
    protected void finalize(){
        try{
        super.finalize();
        }
        catch (Throwable e){
            Red.Error("Error en finalize processor");
        }
    }
 */
}
