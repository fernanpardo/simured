/*
 * Packet.java
 * Packet model
 */
package jsimured.red;

public class Packet {
  /** Indica que el paquete llego entero */
  public boolean Transmitted;
  /** Indica si el paquete se encuentra bloqueado */
  public boolean Blocked;
  public int Id; // Packet identifier
  /** Flits del paquete */
  public int Length;
  /** Nodo origen y destino */
  public int Src, Dest;
  /** Ciclos de reloj consumidos */
  public int Cycles;
  /** ciclos en los que esta bloqueado (totales) */
  public int CycBlock;
  /** ciclos en los que esta bloqueado en el proc emisor */
  public int CycBlockEmit;
  /** ciclos en los que esta bloqueado en el proc receptor */
  public int CycBlockRecep;
  /** ciclos que tarda la cabeza en total */
  public int CycHead;
  /** Nodos atravesados en el camino recorrido */
  public int PathNodes;
  /** Color RGB */
  public int Color;
  /** Matriz de Flits */
  public Flit [] ElFlit;
  public Red LaRed;
  public Packet  Next;
  public Packet  Prev;
  // estos??
  public boolean Destruye = false;
  public volatile boolean Terminado = false;
  
  //public int idpaq;
  //public int ciclosActivo; // no estaba en C
  
  /** Creates a new instance of Packet */
  public Packet(Red unared, int longit, int orig, int dest, int packid) {
    int i;
    //Random rand = new Random();
    //Packet aux;
    Next = null;
    Prev = null;
    Id=packid;
    LaRed = unared;
    Transmitted = false;
    Cycles = 0;
    CycBlock = 0;
    CycBlockEmit = 0;
    CycBlockRecep = 0;
    CycHead = 0;
    PathNodes = 0;
    Length = longit;
    Src = orig;
    Dest = dest;
    Blocked = false;
    
    Destruye = false;
    
    ElFlit = new Flit[Length];
    for (i=0;i<Length;i++) ElFlit[i] = new Flit(); // antes se hacia en sendto red

    Color = (unared.random.nextInt(0x100))*0x1000+(unared.random.nextInt(0x100))*0x100+(unared.random.nextInt(0x100));
    LaRed.Nodes[Src].ElProcIn.putPacket(this); // cuidado pues el paquete se est� construyendo y ya se est� usando, deber�a llamarse fuera...
    LaRed.NumPackCreated[0]++;
    LaRed.NumPackCreated[1]++;
    if (LaRed.NumPackDummy==0) {
      LaRed.CreatedFlits[0]=LaRed.CreatedFlits[0]+Length;
      LaRed.CreatedFlits[1]=LaRed.CreatedFlits[1]+Length;
    }
    //ciclosActivo=0; // no estaba en C
  }
  
  
  
  public void SendToRed() {
    int i;
    //Packet aux;
    Processor auxproc;
    // get from the processor packet list
    auxproc=LaRed.Nodes[Src].ElProcIn;
    auxproc.PackLength++;
    if (Prev==null) {
      auxproc.PackList=Next;
    } else {
      Prev.Next=Next;
    }
    if (Next!=null) Next.Prev=Prev;
    // put the packet into the processor
    for (i=0;i<Length;i++) {
      //ElFlit[i] = new Flit(i, PutTo(LaRed.Nodes[Src].ElProcIn, i));
        ElFlit[i].Pos=i;
        ElFlit[i].Lugar=PutTo(auxproc,i);  
    }
    // put the packet in the Red list of packets
    Next=null;
    Prev=LaRed.PacketsLast;
    if (LaRed.PacketsLast!=null) {
      LaRed.PacketsLast.Next=this;
      LaRed.PacketsLast=this;
    } else LaRed.PacketsLast=LaRed.Packets=this;   
  }
  
  
  /**
   * Funcion para mover el paquete un ciclo
   */
  public Packet RunCycle() {
    int i;
    int nbuf, j, ncanal, ticket;
    boolean avanza;
    //Processor proc;
    Device disp, aux;
    Cycles++;
    
    //System.out.println("Empieza paquete "+idpaq);
    
    for(i = 0; i < Length; i++){
      // nuevo (FER)
      if (i>0)
      {
            disp=ElFlit[i-1].Lugar;
            if (disp.Dev==Device.eProcessor)
                if (disp.MyNode!=Dest) break; // no hace falta que siga
      }
      //sigue
      
      disp = ElFlit[i].Lugar;
      
      // pone el next del eprocesador de salida a lo que toca
      // if (LaRed.NumVirtuals>1)
      if(disp.Dev == Device.eProcessor){
        if(disp.MyNode!=Dest){
          if(i==0){
            // Busca uno libre
            for(j=0; j<LaRed.NumVirtProcInj; j++){
              aux = (FifoFlit)LaRed.Nodes[disp.MyNode].Buf_in[LaRed.NumBuf+j].Fifo[0];
              if((aux.Busy == false) && (aux.Paq == null)){
                disp.Next = aux;
                // The packet enters the network
                LaRed.NumPackEmit[0]++;
                LaRed.NumPackEmit[1]++;
                if (LaRed.NumPackEmit[0]>=LaRed.NumPackDummyIni) {
                  LaRed.EmiFlits[0]=LaRed.EmiFlits[0]+Length;
                  LaRed.EmiFlits[1]=LaRed.EmiFlits[1]+Length;
                }
                LaRed.CyclesEmit[0]=LaRed.Cycles[0];
                ((Processor)disp).PackLength--;
                //proc=(Processor)disp;
                //proc.PackLength--;
                CycBlockEmit=Cycles-1;
                CycBlock=0;
                Cycles=1;
                break;
              }
            }
          } 
          else{
            //if (ElFlit[i-1].Lugar.Dev == Device.eProcessor) break;  // exit if remaining packet flits are wainting in processor
            if (disp.Next.Paq!=this){
              for(j=0; j <LaRed.NumVirtProcInj; j++){
                aux = (FifoFlit)LaRed.Nodes[disp.MyNode].Buf_in[LaRed.NumBuf+j].Fifo[0];
                if(aux.Paq == this){
                  disp.Next = aux;
                  break;
                }
              }
              if(disp.Next.Paq!= this){
                Red.Error("ERROR en procesador de salida, Dispositivo o paquete nulo.");
              }
            }
          }
        }
      }
      if (disp!= null){
        disp.Elapsed++;
        if((disp.Dev == Device.eChannel) && (LaRed.NumVirtuals > 1) && !(LaRed.PhysChannel))
          if((((Channel)disp).Transmitted) || (((Channel)disp).Turn!= disp.MyBuf%LaRed.NumVirtuals)){
          ((Channel)disp).Transmitted = false;
          disp.Elapsed --; // no avanza el tiempo hasta que obtiene el canal fisico
          }
        if(disp.Next == null){
          // Entering crossbar
          if((disp.Dev == Device.eCrossSwitch) && (i==0)){
            // Cabeza y cross
            // Aqui va el algoritmo de encaminamiento
            switch (LaRed.Routing){
              case 0: // Algoritmo XY en mallas bidireccionales
                disp.Next = LaRed.RoutingOrderDimMesh(disp.MyNode, Dest);
                break;
              case 1: // Algoritmo XY en toros bi o unidireccionales
                disp.Next = LaRed.RoutingOrderDimTorus(disp.MyNode, Dest);// Tiene Bloqueos Mortales
                break;
              case 2: // Algoritmo C.A. de Duato usando XY en mallas
                disp.Next = LaRed.RoutingDuatoOrderDimMesh(disp.MyNode, Dest);
                break;
              case 3: // Completamente adaptativo para mallas
                disp.Next = LaRed.RoutingFullyAdaptativeMesh(disp.MyNode, Dest);
                break;
                
              default: Red.Error("Unknown Routing");
              //default: new Error(this,"Error: Unknown Routing");
            }
            if(disp.Next != null){
              disp.Next.Prev = disp;
              PathNodes++;
            }
          }
          //else new Error(this,"Error, unspected null!!");
          else Red.Error("Unspected null in routing!!");
        }
        // medida de la latencia de la cabeza
        if(i == 0)
          if(disp.MyNode == Dest)
            if(disp.Next != null)
              if(disp.Next.Dev == Device.eProcessor)
                if(disp.Elapsed >= disp.Delay)
                  if(CycHead == 0)
                    CycHead = Cycles - 2; // Ajuste ciclos.
        // Si canal virtual bloqueado le da el turno a otro paquete
        if(disp.Dev == Device.eChannel)
          if((LaRed.NumVirtuals > 1) && !(LaRed.PhysChannel))
            if(disp.Next!= null)
              if(disp.Next.Busy)
                if(((Channel)disp).Turn == disp.MyBuf%LaRed.NumVirtuals){
          nbuf = disp.MyBuf/LaRed.NumVirtuals;
          ticket = ((Channel)disp).Turn; // el que hay por si no hay otro
          for (j = 0; j < LaRed.NumVirtuals; j++){
            ticket = (ticket+1)%LaRed.NumVirtuals;
            ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+ticket;
            if((LaRed.Channels[ncanal].Busy)&&(LaRed.Channels[ncanal].Paq!= null))
              if(LaRed.Channels[ncanal].Next!= null)
                if(LaRed.Channels[ncanal].Next.Busy == false){
              // que no este bloquedao tampoco
              LaRed.Channels[ncanal].Transmitted = true;
              ((Channel)disp).Transmitted = false;
              break;
                }
          }
          for (j=0; j<LaRed.NumVirtuals; j++){
            ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+j;
            LaRed.Channels[ncanal].Turn = ticket;
          }
             }
        
        // condicion de bloqueo
        if(i == 0){
          Blocked = false;
          if(disp.Next == null)
            Blocked = true; // Blocked in switch
          else {
            if ( (disp.Next.Paq!=null) || (disp.Next.Busy) ) {
              if (disp.Elapsed >= disp.Delay)
                Blocked=true;     // Blocked in network
              if (disp.Dev==Device.eProcessor)
                Blocked=true;     // Blocked in processor
            }
          }
          if(Blocked){
            if(disp.Next == null)
              disp.Elapsed =- LaRed.SwitchDelay;
            else
              disp.Elapsed = 0;
          }
          if((disp.Dev == Device.eChannel) && (LaRed.NumVirtuals > 1) && !(LaRed.PhysChannel)){
            if((((Channel)disp).Transmitted) || (((Channel)disp).Turn != disp.MyBuf%LaRed.NumVirtuals))
              Blocked = true; // Blocked in virtual channel
          }
          if(Blocked){
            CycBlock++;
            //if(disp.Dev == Device.eProcessor) CycBlockEmit++;
            if((disp.Dev == Device.eCrossSwitch) && (disp.MyNode == Dest))
              CycBlockRecep++;
          }
        }
        
        // condicion de avance
        avanza = false;
        if(disp.Next != null)
          //  synchronized(disp.Next.Sync)
        {
          if((disp.Elapsed >= disp.Delay) && (disp.Next.Busy == false) && ((disp.Next.Paq == this)||(disp.Next.Paq ==null))){
            avanza = true;
            if((disp.Dev == Device.eChannel) && (LaRed.NumVirtuals > 1) && !(LaRed.PhysChannel)){
              if(((Channel)disp).Turn!= disp.MyBuf%LaRed.NumVirtuals)
                avanza = false;
            }
          }
        }
        if(avanza){
            aux = disp.Next;
            ElFlit[i].Lugar = GetFrom(disp, i);
            ElFlit[i].Lugar = PutTo(aux, i);
        }
      } else{
        //new Error(this,"Error: packets with nulls!!");
        Red.Error("Packets with nulls!!");
      }
    }
    
    // estaba comentado pero lo he descomentado
    if(Transmitted){
      //Calcula();
      Delete();
      return(null); // no estaba
    }
    
    //System.out.println("Termina paquete "+idpaq);
    return(this);
    
  }
  
  public Device PutTo(Device disp, int i){
    int j, ncanal, nbuf, mivirt;
    boolean libre;
    
    
    if(disp.Dev != Device.eProcessor){
        /* comento esto que entiendo era para depurar...
      //   synchronized(disp.Sync){
      if ((disp.Paq==null) || (disp.Paq==this)){
        disp.Busy = true;
        disp.Paq = this;
      } else{
        Red.Error("Error: ha ocurrido lo imposible en PutTo!!");
        disp.Busy = true;
        disp.Paq = this;
        //return(null);
      }*/
      disp.Busy = true;
      disp.Paq = this;
        
    } 
    else{
      disp.Busy = false;
      disp.Paq = null;
    }
    
    disp.Elapsed = 0;
    disp.Value = i+1;
    // anyade el retraso de la conmutacion
    if((disp.Dev == Device.eCrossSwitch) && (i == 0))
      disp.Elapsed =- LaRed.SwitchDelay; //Delay de la conmutacion
    if(disp.Dev == Device.eChannel){
      ((Channel)disp).Transmitted = false;
      mivirt = disp.MyBuf%LaRed.NumVirtuals;
      if((LaRed.NumVirtuals>1) && !(LaRed.PhysChannel)){
        nbuf = (int)1.0*disp.MyBuf/LaRed.NumVirtuals; // que raro
        libre = true;
        for(j= 0; j < LaRed.NumVirtuals; j++){
          ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+j;
          if((LaRed.Channels[ncanal].Busy) && (LaRed.Channels[ncanal].Paq!= null) && (j!= mivirt))
            libre = false;
        }
        if(libre)
          for(j=0; j < LaRed.NumVirtuals; j++){
          ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+j;
          LaRed.Channels[ncanal].Turn = mivirt;
          }
      } else
        ((Channel)disp).Turn = mivirt;
    }
    
    if(disp.Next!= null){
      if(disp.Next.Dev == Device.eCrossSwitch)
        disp = PutTo(disp.Next, i);
      // el tema de los virtuales
      else
        if (disp.Next.Dev == Device.eChannel)
          disp = PutTo(disp.Next, i);
    }
    if (disp.Dev == Device.eProcessor)
      if ((disp.Next.Dev == Device.eProcessor) && (i == Length -1)){
      Transmitted = true; // se acabo
      disp.Paq = null;
      disp.Busy = false;
      }
    // Adelantamiento en los fifos
    if(LaRed.Forwarding == 1) {
      // block detection in fifo when forwarding
      if ( (i==0) &&
          (disp.Dev==Device.eFifoFlit) &&
          (disp.Next.Dev==Device.eFifoFlit) &&
          ((disp.Next.Busy==true) || (disp.Next.Paq!=null)) )
        CycBlock++;
      // forwards the flit
      if((disp.Dev == Device.eFifoFlit) &&
          (disp.Next.Dev == Device.eFifoFlit) &&
          (disp.Next.Busy == false) &&
          ((disp.Next.Paq == this) || (disp.Next.Paq ==null))){
        GetFrom(disp, i);
        disp = PutTo(disp.Next, i);
      }
    }
    return(disp);
  }
  
  public Device GetFrom(Device disp, int i){
    int nbuf, j, ncanal, ticket;
    
    if((disp.Dev == Device.eCrossSwitch) || (disp.Dev == Device.eChannel))
      GetFrom(disp.Prev, i);
    disp.Busy = false;
    disp.Value = 0;
    if(disp.Dev == Device.eChannel){
      if((LaRed.NumVirtuals>1) && !(LaRed.PhysChannel)){
        nbuf = (int)1.0*disp.MyBuf/LaRed.NumVirtuals;
        ticket = ((Channel)disp).Turn;
        for(j = 0; j < LaRed.NumVirtuals; j++){
          ticket = (ticket+1)%LaRed.NumVirtuals;
          ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+ticket;
          if((LaRed.Channels[ncanal].Busy) && (LaRed.Channels[ncanal].Paq!=null)){
            LaRed.Channels[ncanal].Transmitted=true;
            break;
          }
        }
        for(j=0; j<LaRed.NumVirtuals; j++){
          ncanal = disp.MyNode*LaRed.NumBuf+nbuf*LaRed.NumVirtuals+j;
          LaRed.Channels[ncanal].Turn = ticket;
        }
      }
    }
    if(i == Length-1){
      // El ultimo flit
      
      disp.Paq = null; // Libera el disp para otro paquete
      if(disp.Prev!= null)
        if(disp.Prev.Dev == Device.eCrossSwitch){
        disp.Prev = null;
        }
      if(disp.Dev == Device.eCrossSwitch){
        disp.Next = null; // Pone a null el crossbar para este paquete
      } 
    }
    return((Device)null); // Por devolver algo
  }
  
  
  
  /**
   * Funcion para eliminar el paquete
   */
  void Delete(){
    // Remove from packet list
    if (this==LaRed.PacketsLast) LaRed.PacketsLast=Prev;
    if (this==LaRed.Packets) {
      LaRed.Packets=Next;
    } else {
      Prev.Next=Next;
    }
    if (Next!=null) Next.Prev=Prev;
    // Generate dependent packets
    if (Id>=0) LaRed.LaunchTracePack(Id);
    // Update counters
    LaRed.NumPackTrans[0]++;
    LaRed.NumPackTrans[1]++;
    if (LaRed.NumPackDummy>0) {
      LaRed.NumPackDummy--;
    } else {
      Cycles=Cycles-2; // ajuste latencia
      LaRed.LatLast=Cycles;
      for (int i=0;i<Red.NC;i++) {
        LaRed.LatTotal[i]=LaRed.LatTotal[i]+Cycles;
        LaRed.LatBlock[i]=LaRed.LatBlock[i]+CycBlock;
        LaRed.LatBlockEmit[i]=LaRed.LatBlockEmit[i]+CycBlockEmit;
        LaRed.LatBlockRecep[i]=LaRed.LatBlockRecep[i]+CycBlockRecep;
        LaRed.LatTrans[i]=LaRed.LatTrans[i]+Cycles;
        LaRed.LatTrans2[i]=LaRed.LatTrans2[i]+Cycles*Cycles;
        LaRed.LatHead[i]=LaRed.LatHead[i]+CycHead;
        LaRed.PathNodes[i]=LaRed.PathNodes[i]+PathNodes;
        LaRed.TransFlits[i]=LaRed.TransFlits[i]+Length;
      }
    }
    
  }
}
