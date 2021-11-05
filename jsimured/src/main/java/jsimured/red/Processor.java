/*
 * Processor.java
 * Processor injector and ejector model
 */
package jsimured.red;

public class Processor extends Device {
    public Packet PackList;
    public int PackLength;
    /** Creates a new instance of Processor */
    public Processor() {
        Delay = 0;        
        Dev = Device.eProcessor;
        Next = this; // si mismo; sospechoso pues se est� inicializando
        Prev = this; // si mismo; sospechoso pues se est� inicializando
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
