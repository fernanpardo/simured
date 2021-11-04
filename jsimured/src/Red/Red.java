package Red;

/*
 * Red.java
 *
 * Created on 24 de septiembre de 2004, 10:47
 * modified may 2012
 */

//import java.lang.Math.*;
//import java.io.*;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;
//import java.util.concurrent.*;
/**
 *
 * @author  Oscar, Fernando
 */

public  class Red {
  
  public static final int NEG = 0;
  public static final int POS = 1;
  public static final int NC = 2;
  
  public class PacketTr {
    public int Id;
    public int Orig;
    public int Dest;
    public int Len;
    public int DepId;
    PacketTr Next,Prev;
  };
  
  
// Statistics counters
  public int LatLast;           // Latencia del último paquete
  public int Cycles[]=new int[NC];         // Cycles desde el inicio
  public int CyclesEmit[]=new int[NC];     // Total Packet emission time
  public int CyclesCreation[]=new int[NC]; // Total Packet creation time  < CyclesEmit
  public int LatTrans[]=new int[NC];       // Suma de latencias (solo movimiento)
  public double LatTrans2[]=new double[NC];     // power 2 sum of latencies (for std deviation)
  public int LatBlock[]=new int[NC];       // Suma de latencias (durante bloqueos)
  public int LatBlockEmit[]=new int[NC];   // Suma de latencias (durante bloqueos en el p. emisor)
  public int LatBlockRecep[]=new int[NC];  // Suma de latencias (durante bloqueos en el p. receptor)
  public int LatTotal[]=new int[NC];       // La suma de las anteriores
  public int LatHead[]=new int[NC];        // Suma de Latencias de la cabeza
  public int EmiFlits[]=new int[NC];       // Flits emitidos
  public int CreatedFlits[]=new int[NC];   // Flits creados
  public int TransFlits[]=new int[NC];     // Flits transmitidos
  public int NumPackTrans[]=new int[NC];   // Packets Transmitidos completamente
  public int NumPackEmit[]=new int[NC];    // Emitted packets to the net
  public int NumPackCreated[]=new int[NC]; // Created packets>Emitted packets
  public int PathNodes[]=new int[NC];      // Nodos atravesados en los caminos recorridos

  public int NumPackDummy;    // Number of dummy warm up packets Warm-Up
  public int NumPackDummyIni; // Initial Number of dummy warm up packets Warm-Up
  
  /** Tipo de Control de Flujo */
  private int Switching;  // se usa aunque no cambia su valor
  /** Conjunto de Canales */
  public Channel[] Channels;
  public Node[] Nodes;
  public Packet Packets;
  public Packet PacketsLast;
  public PacketTr PackTraces;
  public PacketTr PackTracesLast;
  
  /** Mecanismo de encaminamiento */
  public int Routing;
  /** Indica si se produjo un bloqueo mortal */
  public boolean BloqueoTotal;
  
  //  Topology
  public int NumNodes;
  public int NumBuf;
  public int Dimensions;
  public int NumNodesDim;
  public int NumVirtuals;
  public int NumVirtProcInj;// Number of injection processor channels
  public int NumVirtProcEje;// Number of ejection processor channels
  public int NumFlitBuf;
  public int Directions;
  public int Forwarding;
  public boolean PhysChannel;
  
  // Delays
  public int FifoDelay;
  public int CrossDelay;
  public int ChannelDelay;
  public int SwitchDelay;
  
  //f public Object Terminado;
  //public int Lanzados;
  public int Terminados; // hilos lanzados e hilos terminados
  //
  //f public lanzadorPacket lanzador;
  
  //public int nPaq;
  //f int nHilos;
  
  // Nuevos
  public Random random = new Random(); // si nada, semilla aleatoria, sino aquí pondria la semilla si quiero siempre el mismo resultado
  
  /** Creates a new instance of Red */
  public Red(int dimen, int numdim, int numvirt, int numvirtinj, int numvirteje, int numflitbuf, int direcciones, int adelanto, boolean fisicos, int routing, int switching, int fiforetraso, int interretraso, int canalretraso, int conmuretraso, int dummypacket) {
    int n, d, di, v;
    int i, j;
    int num;
    //FifoFlit aux;
    Packets=null;  // Packet list to NULL
    PacketsLast=null;
    PackTraces=null;
    PackTracesLast=null;
    ResetStatSet(0);
    ResetStatSet(1);
    NumPackDummy=dummypacket;
    NumPackDummyIni=dummypacket;
    BloqueoTotal=false;
    Routing=routing;
    Switching=switching;
    FifoDelay=fiforetraso;
    CrossDelay=interretraso;
    ChannelDelay=canalretraso;
    SwitchDelay=conmuretraso;
    PhysChannel=fisicos;
    Forwarding=adelanto;
    NumFlitBuf=numflitbuf;
    NumVirtuals=numvirt;
    NumVirtProcInj=numvirtinj;
    NumVirtProcEje=numvirteje;
    Dimensions=dimen;
    Directions=direcciones;
    NumNodesDim=numdim;
    
    //nPaq = 0; // Inicializamos a 0 el numero de Paquetes
    //f nHilos = 0;// Inicializamos a 0 el numero de Hilos
    //Lanzados = 0;
    //Terminados = 0;
    //f Terminado = new Object(); //  Objeto para realizar la sincronizacion
    //f lanzador = new lanzadorPacket(4);
    
    NumNodes = (int)Math.pow(NumNodesDim, Dimensions);
    
    // Suponemos una red n-cubo k-aria bidireccional
    NumBuf = Dimensions*Directions*NumVirtuals; // Los de salida
    Nodes= new Node[NumNodes];
    
    for(i=0; i < NumNodes; i++){
      Nodes[i] = new Node(this);
    }
    
    Channels = new Channel[NumNodes*NumBuf];
    
    for(i=0; i < NumNodes*NumBuf; i++){
      Channels[i]= new Channel(this);
    }
    
    // Inicializamos dispositivos al nodo que tienen:
    for(n=0; n < NumNodes; n++){
      Nodes[n].ElProcOut.MyNode=n; // no estaba
      Nodes[n].ElProcIn.MyNode=n;
      
      for(i=0; i <(NumBuf+NumVirtProcInj); i++){
        for(j=0; j < NumFlitBuf; j++){
          Nodes[n].Buf_in[i].Fifo[j].MyNode = n;
          //Nodes[n].Buf_out[i].Fifo[j].MyNode = n;
          //Nodes[n].ElCrossBar.Output[i].MyNode = n;
          //Nodes[n].ElProcOut.MyNode = n;
          //Nodes[n].ElProcIn.MyNode = n;
        }
        if(i<NumBuf){
          Channels[n*NumBuf+i].MyNode = n;
          Channels[n*NumBuf+i].MyBuf = i;
        }
      }
      for(i=0; i <(NumBuf+NumVirtProcEje); i++){
        Nodes[n].ElCrossBar.Output[i].MyNode = n;
        for(j=0; j < NumFlitBuf; j++){
          //Nodes[n].Buf_in[i].Fifo[j].MyNode = n;
          Nodes[n].Buf_out[i].Fifo[j].MyNode = n;
          //Nodes[n].ElCrossBar.Output[i].MyNode = n;
          //Nodes[n].ElProcOut.MyNode = n;
          //Nodes[n].ElProcIn.MyNode = n;
        }
      }
    }
    
    //Ahora se interconectan los nodos y los canales
    // channel and node interconections
    for(n=0 ; n<NumNodes; n++){
      for(d=0; d<Dimensions; d++){
        for(di=0; di<Directions; di++){
          for(v=0; v<NumVirtuals; v++){
            num = ChannelId(n,d,di,v);
            // kylix 3.0 under Suse 9.0 workaround: (contact borland type error)
            //aux = Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1];
            //aux.Next = Channels[num];
            Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1].Next=Channels[num];
            Channels[num].Prev = Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1];
            Channels[num].Next = Nodes[Trans(n,d,di)].Buf_in[ChannelId(0,d,di,v)].Fifo[0];
            Nodes[Trans(n,d,di)].Buf_in[ChannelId(0,d,di,v)].Fifo[0].Prev = Channels[num];
          }
        }
      }
    }
  }
  
  // function to print an error and exit application
  public static void Error(String msg)
  {
      PrintWriter bw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.err)));
      bw.println(msg);
      bw.flush();
      System.exit(1);
  }
          
  
  public final void ResetStatSet(int set) { // privado o final para no interferir con subclases, si las hubiera (que no las hay)
    LatLast=0;
    Cycles[set]=0;
    CyclesEmit[set]=0;
    CyclesCreation[set]=0;
    LatTrans[set]=0;
    LatTrans2[set]=0;
    LatBlock[set]=0;
    LatBlockEmit[set]=0;
    LatBlockRecep[set]=0;
    LatTotal[set]=0;
    LatHead[set]=0;
    EmiFlits[set]=0;
    CreatedFlits[set]=0;
    TransFlits[set]=0;
    NumPackTrans[set]=0;
    NumPackEmit[set]=0;
    NumPackCreated[set]=0;
    PathNodes[set]=0;
  }
  
  public void InsertTracePack(int depid, int org, int dest, int len, int id) {
    PacketTr aux;
    aux=new PacketTr();
    aux.Id=id;
    aux.Orig=org;
    aux.Dest=dest;
    aux.Len=len;
    aux.DepId=depid;
    aux.Next=null;
    aux.Prev=PackTracesLast;
    if (PackTracesLast==null)
      PackTraces=PackTracesLast=aux;
    else {
      PackTracesLast.Next=aux;
      PackTracesLast=aux;
    }
  }
  
  void LaunchTracePack(int depid) {
    PacketTr aux,tmp;
    Packet paqaux;
    aux=PackTraces;
    while (aux!=null) {
      if (aux.DepId==depid) {
        paqaux=new Packet(this,aux.Len,aux.Orig,aux.Dest,aux.Id);
        tmp=aux.Next;
        DeleteTracePack(aux);
        aux=tmp;
      } else {
        aux=aux.Next;
      }
    }
  }
  
  void DeleteTracePack(PacketTr pack) {
    // Remove from trace packet list
    if (pack==PackTracesLast) PackTracesLast=pack.Prev;
    if (pack==PackTraces) {
      PackTraces=pack.Next;
    } else {
      pack.Prev.Next=pack.Next;
    }
    if (pack.Next!=null) pack.Next.Prev=pack.Prev;
    //delete pack;
  }
  
  
  /**
   * Funcion para saber el numero de canal segun dimension etc.
   */
  // Channel number from position
  public final int ChannelId(int numnodo, int dimcanal, int direccion, int numvirt){
    if((numnodo<NumNodes) && (dimcanal<Dimensions) && (direccion<Directions) && (numvirt<NumVirtuals))
      return((((numnodo)*Dimensions+dimcanal)*Directions+direccion)*NumVirtuals+numvirt);
    Error("Error: Canal fuera de Rango en ChannelId\n");
    //new Error(this, "Error: Canal fuera de Rango en ChannelId" );
    return(-1);
  }
  
  public final int Trans(int act, int Dim, int di){
    int res, i, coor;
    int NumDim, NodDim;
    NumDim = Dimensions;
    NodDim = NumNodesDim;
    res = 0;
    for (i= 0; i < NumDim ; i++){
      coor =(int)(act/Math.pow(NodDim, i))%NodDim;
      if(i== Dim)
        res =res +(int)((coor+NodDim+2*di-1)%NodDim)*(int)Math.pow(NodDim, i);
      else
        res = res + coor *(int)Math.pow(NodDim, i);
    }
    return (res);
  }
  
  /**
   * Funcion basica para hacer pasar un ciclo,
   * mueve paquetes, etc.
   */
  
  public void RunCycle() throws InterruptedException{
    Packet aux, tmp;
    Processor proc;
    //int Bloqueados;
    
    //Terminados = 0;
    //Lanzados = 0;
    
    
    if (NumPackDummy==0) {
      Cycles[0]++;
      Cycles[1]++;
    }
    
    // Pone los canales a transmitido = false
    if(NumVirtuals > 1)
      for(int i=0; i < NumNodes*NumBuf; i++)
        Channels[i].Transmitted = false;
    
    // Moves packets from packet buffer to processor, if free
    for (int i=0;i<NumNodes;i++) {
      proc=Nodes[i].ElProcIn;
      aux=proc.PackList;
      while ( (aux!=null) && (proc.PackLength<(NumVirtProcInj+6)) ) { // antes sumaba 5 en lugar de 6
        tmp=aux.Next;
        aux.SendToRed();
        aux=tmp;
      }
    }
    
    // mueve paquetes
    // Packet movement
    aux=Packets;
    BloqueoTotal=true;
    while (aux!=null) {
      tmp=aux.Next;     // necesario pues el paquete puede desaparecer
      // ejecuta y/o borra paquete
      aux=aux.RunCycle();
      if (aux!=null) {
        if (aux.Blocked==false)
          BloqueoTotal=false;
      } else {
        BloqueoTotal=false; // si es que salio alguno hay vida aun...
      }
      aux=tmp;
    }    
  }
  
  /**************************************************************/
  /**   MECANISMOS DE ENCAMINAMIENTO (dentro de la clase Red)  **/
  /**************************************************************/
  //----------------------------------------------------------
  // Routing Algorithms
  //----------------------------------------------------------
  
  /** Solo sirve para bidireccional
   * Selecciona aleatoriamente cualquier virtual libre */
  Device RoutingOrderDimMesh(int nodo, int Dest){
    int offset, offvoy, offdest;
    int dim;
    int iniv, v, virt;
    Device res;
    
    res = null;
    //Random random = new Random();
    if(nodo == Dest){
      iniv = random.nextInt(NumVirtProcEje);
      for(v=0; v<NumVirtProcEje; v++){
        virt = (iniv+v)%NumVirtProcEje;
        res = Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0];
        if(res!=null)
          if(res.Prev == null)
              return(res);
      } 
      return(null);
    }
    dim = 0;
    do{
      offvoy =(int) (nodo/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offdest =(int) (Dest/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offset = offdest - offvoy;
      dim++;
    }
    while ((dim<Dimensions) && (offset==0));
    dim--;
    //iniv = Math.abs(random.nextInt())%NumVirtuals;
    iniv = random.nextInt(NumVirtuals);
    for(v= 0; v<NumVirtuals; v++){
      
      virt = (iniv+v)%NumVirtuals;
      if(offset > 0)
        res  = Nodes[nodo].Buf_out[ChannelId(0,dim,POS, virt)].Fifo[0]; // De momento solo gasta un virtual, el 0
      else if (offset<0)
        res = Nodes[nodo].Buf_out[ChannelId(0,dim,NEG, virt)].Fifo[0];// De momento solo gasta un virtual, el 0
      else
        Error("Error Routing XYMalla"); // Habra q cambiar a ShowError?
      if(res.Prev == null)
        return(res);
      
    }
    
    return(null);
    
  }
  
  /** Sirve para unidireccional y bidireccional pero tiene bloqueos
   * mortales */
  Device RoutingOrderDimTorus(int nodo, int Dest){
    int offset, offvoy, offdest;
    int dim;
    int iniv, v, virt;
    Device res;
    res = null;
    //Random random = new Random();
    if(nodo ==Dest){
      //iniv = Math.abs(random.nextInt())%NumVirtuals;
      iniv = random.nextInt(NumVirtProcEje);
      for(v = 0; v<NumVirtProcEje; v++){
        virt = (iniv+v)%NumVirtProcEje;
        res = Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0];
        if(res!=null)
          if(res.Prev == null)
            return(res);
      }
      return(null);
    }
    dim = 0;
    do{
      offvoy =(int) (nodo/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offdest = (int)(Dest/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offset = offdest - offvoy;
      dim++;
    }
    while((dim < Dimensions) && (offset== 0));
    dim--;
    //iniv = Math.abs(random.nextInt())%NumVirtuals;
    iniv = random.nextInt(NumVirtuals);
    for(v=0; v<NumVirtuals; v++){
      virt=(iniv+v)%NumVirtuals;
      if(Directions>1){
        //Bidireccional
        if(offset >0){
          if(offset<NumNodesDim/2)
            res = Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]; //De momento solo gasta un virtual, el 0
          else
            res = Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]; //De momento solo gasta un virtual, el 0
        } else if(offset<0){
          // negativo
          if((-offset)< NumNodesDim/2)
            res = Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]; //De momento solo gasta un virtual, el 0
          else
            res = Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]; //De momento solo gasta un virtual, el 0
        } else
          Error("Error routing XYToro");
      } else{
        // unidireccional
        res = Nodes[nodo].Buf_out[ChannelId(0,dim,0,virt)].Fifo[0];
      }
      if(res.Prev== null)
        return (res);
    }
    return(null);
  }
  
  /** Algoritmo completamente Adaptativo de Duato para mallas
   * necesita al menos 2 canales virtuales, si solo hay uno es el XY normal
   */
  Device RoutingDuatoOrderDimMesh(int nodo, int Dest){
    int offset, offvoy, offdest;
    int iniv, v, virt;
    int inidim, d, dim, deterdim;
    Device res;
    //Random random = new Random();
    
    if (nodo == Dest){
      //iniv = Math.abs(random.nextInt())%NumVirtuals;
      iniv = random.nextInt(NumVirtProcEje);
      for(v=0; v<NumVirtProcEje; v++){
        virt = (iniv+v)%NumVirtProcEje;
        res = Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0];
        if(res!=null)
          if(res.Prev == null)
            return(res);
      }
      return(null);
    }
    if(NumVirtuals<2){
      return(RoutingOrderDimMesh(nodo, Dest));
    }
    dim = 0;
    do {
      offvoy = (int)(nodo/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offdest = (int)(Dest/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offset = offdest - offvoy;
      dim++;
    }
    while((dim<Dimensions) && (offset == 0));
    dim--;
    deterdim = dim; //Dimension que toca segun XY;
    //inidim = Math.abs(random.nextInt())%Dimensions;
    inidim = random.nextInt(Dimensions);
    
    for (d = 0; d<Dimensions; d++){
      dim = (inidim+d)%Dimensions;
      offvoy = (int)(nodo/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offdest = (int)(Dest/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offset = offdest - offvoy;
      //iniv = Math.abs(random.nextInt())%NumVirtuals;
      iniv = random.nextInt(NumVirtuals);
      for(v=0; v<NumVirtuals; v++){
        virt = (iniv+v)%NumVirtuals;
        if(offset>0)
          res = Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0];//De momento solo gasta un virtual, el 0
        else if(offset<0)
          res = Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0];//De momento solo gasta un virtual, el 0
        else
          res = null;
        if(res!= null)
          if(res.Prev == null)
            if(dim!=deterdim){
          if(virt!=0)
            return(res);
            } else
              return(res);
      }
    }
    return(null);
  }
  
  /** Completamente adaptativo para mallas, naturalmente presenta
   * bloqueos mortales
   */
  Device RoutingFullyAdaptativeMesh(int nodo, int Dest){
    int offset, offvoy, offdest;
    int iniv, v, virt;
    int inidim, d, dim;
    Device res;
    //Random random = new Random();
    
    if(nodo == Dest){
      //iniv = Math.abs(random.nextInt())%NumVirtuals;
      iniv = random.nextInt(NumVirtProcEje);
      for(v=0; v<NumVirtProcEje; v++){
        virt = (iniv+v)%NumVirtProcEje;
        res = Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0];
        if(res!=null)
          if(res.Prev==null)
            return(res);
      }
      return(null);
    }
    //inidim = Math.abs(random.nextInt())%Dimensions;
    inidim = random.nextInt(Dimensions);

    for(d=0;d<Dimensions; d++){
      dim = (inidim+d)%Dimensions;
      offvoy = (int)(nodo/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offdest = (int)(Dest/Math.pow(NumNodesDim, dim))%NumNodesDim;
      offset = offdest - offvoy;
      //iniv = Math.abs(random.nextInt())%NumVirtuals;
      iniv = random.nextInt(NumVirtuals);
      for(v=0; v<NumVirtuals; v++){
        virt = (iniv+v)%NumVirtuals;
        if(offset>0){
          res = Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0];
        } else if (offset<0){
          res = Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0];
        } else
          res = null;
        if(res!= null)
          if(res.Prev == null)
            return(res);
      }
    }
    return(null);
  }
  
  /*
  public void Delete(){
    try{
      super.finalize();
    }catch (Throwable e){
      Error("Error en el delete de red");
    }
  }*/
  
}