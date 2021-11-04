//---------------------------------------------------------------------------
// Methods for working with Packets and Networks
// The ShowError function is the only one that may
// depend on the system; feel free to change it.
//
// Fernando Pardo, november, 2005. Universidad de Valencia.
//---------------------------------------------------------------------------

#include "red.h"

//-------------------------------------------------
// Global functions
//-------------------------------------------------

// Funcion para mostrar un error, modificar segun plataforma
void ShowError(const char *a)
{
 //ShowMessage(a);
 //Application->Terminate();  // esto es segun gustos
 exit(1); // esto es segun gustos
}

// se podria utilizar pow pero esta es mas rapida, creo.
int pot(int base, int exponente)
{
int res,i;
 res=1;
 for (i=0;i<exponente;i++) res=res*base;
 return(res);
}


//-------------------------------------------------
// RED and DEVICE METHODS
//-------------------------------------------------


Device::Device()
{
 MyNode=INIVAL; // any value
 MyBuf=INIVAL;
 Elapsed=0;
 Value=0;
 Busy=false;
 Next=NULL;
 Prev=NULL;
 Paq=NULL;
 LaRed=NULL;
}

void Channel::ChannelIni(class Red *unared)
{
 LaRed=unared;
 Delay=LaRed->ChannelDelay;
 Dev=eChannel;
 Turn=0;
 Transmitted=false;
}

Processor::Processor()
{
 Delay=0;
 Dev=eProcessor;
 PackList=NULL;
 PackLength=0;
 Next=this;
 Prev=this;
}
void Processor::putPacket(class Packet *Pack)
{
class Packet *aux;
 aux=PackList;
 Pack->Next=NULL;
 Pack->Prev=NULL;
 if (aux==NULL)
 {
   PackList=Pack;
 }
 else
 {
   while (aux->Next!=NULL)
   {
    aux=aux->Next;
   }
   Pack->Prev=aux;
   aux->Next=Pack;
 }
}


void CrossSwitch::CrossSwitchIni(class Red *unared)
{
 LaRed=unared;
 Delay=LaRed->CrossDelay;
 Dev=eCrossSwitch;
}

CrossBar::CrossBar(class Red *unared)
{
int i;
 LaRed=unared;
 Output=new CrossSwitch[LaRed->NumBuf+LaRed->NumVirtProcEje];
 for (i=0;i<LaRed->NumBuf+LaRed->NumVirtProcEje;i++)
 {
   Output[i].CrossSwitchIni(LaRed);
   Output[i].MyBuf=i;
 }
}

CrossBar::~CrossBar()
{
 delete [] Output;
}

void FifoFlit::FifoFlitIni(class Red *unared)
{
 LaRed=unared;
 Delay=LaRed->FifoDelay;
 Dev=eFifoFlit;
}

void Buffer::BufferIni(class Red *unared,int buf)
{
int i;
 LaRed=unared;
 Fifo=new FifoFlit[LaRed->NumFlitBuf];
 for (i=0;i<LaRed->NumFlitBuf;i++)
 {
   Fifo[i].FifoFlitIni(LaRed);
   Fifo[i].MyBuf=buf;
 }
 // Next of fifoflits
 for (i=0;i<LaRed->NumFlitBuf-1;i++) Fifo[i].Next=&(Fifo[i+1]);
 // Prev of fifoflits
 for (i=1;i<LaRed->NumFlitBuf;i++) Fifo[i].Prev=&(Fifo[i-1]);
 // Last fifoflit is connected outside the node
 Fifo[LaRed->NumFlitBuf-1].Next=NULL;
 Fifo[0].Prev=NULL;
}

Buffer::~Buffer()
{
 delete[] Fifo;
}

void Node::NodeIni(class Red *unared)
{
int i;
 LaRed=unared;
 Buf_in=new Buffer[LaRed->NumBuf+LaRed->NumVirtProcInj]; // as many internals as virtuals
 Buf_out=new Buffer[LaRed->NumBuf+LaRed->NumVirtProcEje];
 for (i=0;i<(LaRed->NumBuf+LaRed->NumVirtProcInj);i++)
 {
   Buf_in[i].BufferIni(LaRed,i);
 }
 for (i=0;i<(LaRed->NumBuf+LaRed->NumVirtProcEje);i++)
 {
   Buf_out[i].BufferIni(LaRed,i);
 }
 ElCrossBar=new CrossBar(LaRed);
 // Connection between buffers and Crossbar
 for (i=0;i<(LaRed->NumBuf+LaRed->NumVirtProcInj);i++)
 {
    Buf_in[i].Fifo[LaRed->NumFlitBuf-1].Next=&(ElCrossBar->Output[i]);
    ElCrossBar->Output[i].Prev=&(Buf_in[i].Fifo[LaRed->NumFlitBuf-1]);
 }
 // Switches  are connected dynamically during routing
 // Processor buffers are connected to the processor
 for (i=0;i<LaRed->NumVirtProcInj;i++)
 {
   Buf_in[LaRed->NumBuf+i].Fifo[0].Prev=&ElProcIn;
 }
 for (i=0;i<LaRed->NumVirtProcEje;i++)
 {
   Buf_out[LaRed->NumBuf+i].Fifo[LaRed->NumFlitBuf-1].Next=&ElProcOut;
 }
 ElProcOut.Prev=&(Buf_out[LaRed->NumBuf].Fifo[LaRed->NumFlitBuf-1]);
 ElProcIn.Next=&(Buf_in[LaRed->NumBuf].Fifo[0]);
}

Node::~Node()
{
 delete [] Buf_in;
 delete [] Buf_out;
}

Red::Red(int dimen, int numdim, int numvirt, int numvirtinj, int numvirteje, int numflitbuf, int direcciones, int adelanto, bool fisicos, int routing, int switching, int fiforetraso, int interretraso, int canalretraso, int conmuretraso, int dummypack)
{

int n,d,di,v;
int i,j;
int num;
FifoFlit *aux;

// fich=fopen("latdata.txt","wt");
 Packets=NULL;  // Packet list to NULL
 PacketsLast=NULL;
 PackTraces=NULL;
 PackTracesLast=NULL;
 ResetStatSet(0);
 ResetStatSet(1);
 BloqueoTotal=false;
 NumPackDummy=dummypack;
 NumPackDummyIni=dummypack;
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
 NumNodes=pot(NumNodesDim,Dimensions);
 //  k-ari n-cube bidiretional
 NumBuf=Dimensions*Directions*NumVirtuals;
 Nodes=new Node[NumNodes];
 for (i=0;i<NumNodes;i++) Nodes[i].NodeIni(this);
 Channels=new Channel[NumNodes*NumBuf];
 for (i=0;i<NumNodes*NumBuf;i++) Channels[i].ChannelIni(this);
 // Device initialization
 for (n=0;n<NumNodes;n++)
 {
   Nodes[n].ElProcOut.MyNode=n;
   Nodes[n].ElProcIn.MyNode=n;
   for (i=0;i<(NumBuf+NumVirtProcInj);i++)
   {
     for (j=0;j<NumFlitBuf;j++)
     {
       Nodes[n].Buf_in[i].Fifo[j].MyNode=n;
     }
     if (i<NumBuf)
     {
       Channels[n*NumBuf+i].MyNode=n;
       Channels[n*NumBuf+i].MyBuf=i;
     }
   }
   for (i=0;i<(NumBuf+NumVirtProcEje);i++)
   {
     Nodes[n].ElCrossBar->Output[i].MyNode=n;
     for (j=0;j<NumFlitBuf;j++)
     {
       Nodes[n].Buf_out[i].Fifo[j].MyNode=n;
     }
   }
 }
 // channel and node interconections
 for (n=0;n<NumNodes;n++)
 {
   for (d=0;d<Dimensions;d++)
   {
     for (di=0;di<Directions;di++)
     {
       for (v=0;v<NumVirtuals;v++)
       {
             num=ChannelId(n,d,di,v);
             // kylix 3.0 under Suse 9.0 workaround: (contact borland type error)
             aux=&(Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1]);
             aux->Next=&(Channels[num]);
             //Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1].Next=&(Channels[num]);
             Channels[num].Prev=&(Nodes[n].Buf_out[ChannelId(0,d,di,v)].Fifo[NumFlitBuf-1]);
             Channels[num].Next=&(Nodes[Trans(n,d,di)].Buf_in[ChannelId(0,d,di,v)].Fifo[0]);
             Nodes[Trans(n,d,di)].Buf_in[ChannelId(0,d,di,v)].Fifo[0].Prev=&(Channels[num]);
       }
     }
   }
 }
}

Red::~Red()
{
  delete [] Nodes;
  delete [] Channels;
  // Free packet list
  while(Packets!=NULL)
  {
    delete Packets;
  }
  while(PackTraces!=NULL)
  {
    delete PackTraces;
  }
//  fflush(fich);
//  fclose(fich);
}

void Red::InsertTracePack(int depid, int org, int dest, int len, int id)
{
PacketTr *aux;
 aux=new PacketTr();
 aux->Id=id;
 aux->Orig=org;
 aux->Dest=dest;
 aux->Len=len;
 aux->DepId=depid;
 aux->Next=NULL;
 aux->Prev=PackTracesLast;
 if (PackTracesLast==NULL)
   PackTraces=PackTracesLast=aux;
 else
 {
   PackTracesLast->Next=aux;
   PackTracesLast=aux;
 }
}

void Red::LaunchTracePack(int depid)
{
PacketTr *aux,*tmp;
 aux=PackTraces;
 while (aux!=NULL)
 {
   if (aux->DepId==depid)
   {
     new Packet(this,aux->Len,aux->Orig,aux->Dest,aux->Id);
     tmp=aux->Next;
     DeleteTracePack(aux);
     aux=tmp;
   }
   else
   {
     aux=aux->Next;
   }
 }
}

void Red::DeleteTracePack(PacketTr *pack)
{
 // Remove from trace packet list
 if (pack==PackTracesLast) PackTracesLast=pack->Prev;
 if (pack==PackTraces)
 {
   PackTraces=pack->Next;
 }
 else
 {
   pack->Prev->Next=pack->Next;
 }
 if (pack->Next!=NULL) pack->Next->Prev=pack->Prev;
 delete pack;
}


// Channel number from position
int Red::ChannelId(int numnodo, int dimcanal, int direccion, int numvirt)
{
  if ( (numnodo<NumNodes) &&
       (dimcanal<Dimensions) &&
       (direccion<Directions) &&
       (numvirt<NumVirtuals) )
         return((((numnodo)*Dimensions+dimcanal)*Directions+direccion)*NumVirtuals+numvirt);
  //ShowError("Error: Channel out of range in ChannelId");
  return(-1);
}


void Red::ResetStatSet(int set)
{
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

// Basic function to run one cycle in the network.
void Red::RunCycle()
{
Packet *aux,*tmp;
//Device *dev;
Processor *proc;
int i;
    if (NumPackDummy==0)
    {
      Cycles[0]++;
      Cycles[1]++;
    }
    // Pone los canales a transmitido=false
    if (NumVirtuals>1)
      for (i=0;i<NumNodes*NumBuf;i++) Channels[i].Transmitted=false;

    // Moves packets from packet buffer to processor, if free
    for (i=0;i<NumNodes;i++)
    {
      proc=&(Nodes[i].ElProcIn);
      aux=proc->PackList;
      while ( (aux!=NULL) && (proc->PackLength<(NumVirtProcInj+6)) )
      {
           tmp=aux->Next;
           aux->SendToRed();
           aux=tmp;
      }
    }

    // Packet movement
    aux=Packets;
    BloqueoTotal=true;
    while (aux!=NULL)
    {
        tmp=aux->Next;     // necesario pues el paquete puede desaparecer
        // ejecuta y/o borra paquete
        aux=aux->RunCycle();
        if (aux!=NULL)
        {
          if (aux->Blocked==false)
            BloqueoTotal=false;
        }
        else
        {
          BloqueoTotal=false; // si es que salio alguno hay vida aun...
        }
        aux=tmp;
    }
}

// Funcion para saber el nodo adyacente en la dim y dir que se le pasen
int Red::Trans(int act, int Dim, int di)
{
int res,i,coor;
int NumDim,NodDim;
 NumDim=Dimensions;
 NodDim=NumNodesDim;
 res=0;
 for (i=0; i<NumDim; i++)
 {
   coor=(act/pot(NodDim,i))%NodDim;
   if (i==Dim) res=res+((coor+NodDim+2*di-1)%NodDim)*pot(NodDim,i);
   else res=res+coor*pot(NodDim,i);
 }
 return(res);
}

//----------------------------------------------------------
// Routing Algorithms
//----------------------------------------------------------

// Solo sirve para bidireccional
// Selecciona aleatoriamente cualquier virtual libre
Device* Red::RoutingOrderDimMesh(int nodo,int Dest)
{
int offset,offvoy,offdest;
int dim;
int iniv,v,virt;
Device *res;
 res=NULL;
 if (nodo==Dest)
 {
   iniv=rand()%NumVirtProcEje;
   for (v=0;v<NumVirtProcEje;v++)
   {
     virt=(iniv+v)%NumVirtProcEje;
     res=&(Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0]);
     if (res!=NULL) if (res->Prev==NULL) return(res);
   }
   return(NULL);
 }
 dim=0;
 do
 {
   offvoy=(nodo/pot(NumNodesDim,dim))%NumNodesDim;
   offdest=(Dest/pot(NumNodesDim,dim))%NumNodesDim;
   offset=offdest-offvoy;
   dim++;
 }
 while ( (dim<Dimensions) && (offset==0) );
 dim--;
 iniv=rand()%NumVirtuals;
 for (v=0;v<NumVirtuals;v++)
 {
   virt=(iniv+v)%NumVirtuals;
   if (offset>0)
     res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
   else if (offset<0)
     res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
   else ShowError("Error routing XYmalla");
   if (res->Prev==NULL) return(res);   // esta condicion es mejor que la de Busy
 }
 return(NULL);
}

// Sirve para unidireccional y bidireccional pero tiene bloqueos mortales
Device* Red::RoutingOrderDimTorus(int nodo,int Dest)
{
int offset,offvoy,offdest;
int dim;
int iniv,v,virt;
Device *res;
 res=NULL;
 if (nodo==Dest)
 {
   iniv=rand()%NumVirtProcEje;
   for (v=0;v<NumVirtProcEje;v++)
   {
     virt=(iniv+v)%NumVirtProcEje;
     res=&(Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0]);
     if (res!=NULL) if (res->Prev==NULL) return(res);
   }
   return(NULL);
 }
 dim=0;
 do
 {
   offvoy=(nodo/pot(NumNodesDim,dim))%NumNodesDim;
   offdest=(Dest/pot(NumNodesDim,dim))%NumNodesDim;
   offset=offdest-offvoy;
   dim++;
 }
 while ( (dim<Dimensions) && (offset==0) );
 dim--;
 if (offset>NumNodesDim/2)       offset=offset-NumNodesDim;
 else if (offset<-NumNodesDim/2) offset=offset+NumNodesDim;
 iniv=rand()%NumVirtuals;
 for (v=0;v<NumVirtuals;v++)
 {
   virt=(iniv+v)%NumVirtuals;
   if (Directions>1) // bidireccional
   {
     if (abs(offset)==NumNodesDim/2)
     {
         res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
         if (res->Prev!=NULL) res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
     }
     else if (offset>0)
     {
       //if (offset<NumNodesDim/2)
         res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
       //else
       //  res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
     }
     else if (offset<0)  // negativo
     {
       //if ((-offset)<NumNodesDim/2)
         res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
       //else
       //  res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
     }
     else ShowError("Error routing XYToro");
   }
   else // unidireccional
   {
       res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,0,virt)].Fifo[0]);
   }
   //Muestra(IntToStr(Cycles)+" Cabecera: Nodo "+IntToStr(nodo)+", tomo dimension "+IntToStr(dim)+" con offset "+IntToStr(offset));
   if (res->Prev==NULL) return(res);
 }
 return(NULL);
}

// Algoritmo Completamente Adaptativo de Duato para mallas,
// necesita al menos 2 canales virtuales, si solo hay uno es el XY normal
Device* Red::RoutingDuatoOrderDimMesh(int nodo,int Dest)
{
int offset,offvoy,offdest;
int iniv,v,virt;
int inidim,d,dim,deterdim;
Device *res; //,*ultimres;
 if (nodo==Dest)
 {
   iniv=rand()%NumVirtProcEje;
   for (v=0;v<NumVirtProcEje;v++)
   {
     virt=(iniv+v)%NumVirtProcEje;
     res=&(Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0]);
     if (res!=NULL) if (res->Prev==NULL) return(res);
   }
   return(NULL);
 }
 if (NumVirtuals<2)
 {
   return(RoutingOrderDimMesh(nodo,Dest));
 }
 dim=0;
 do
 {
   offvoy=(nodo/pot(NumNodesDim,dim))%NumNodesDim;
   offdest=(Dest/pot(NumNodesDim,dim))%NumNodesDim;
   offset=offdest-offvoy;
   dim++;
 }
 while ( (dim<Dimensions) && (offset==0) );
 dim--;
 deterdim=dim;  //dimension que toca segun XY
 inidim=rand()%Dimensions;
 //inidim=0; // va igual
 for (d=0;d<Dimensions;d++)
 {
   dim=(inidim+d)%Dimensions;
   offvoy=(nodo/pot(NumNodesDim,dim))%NumNodesDim;
   offdest=(Dest/pot(NumNodesDim,dim))%NumNodesDim;
   offset=offdest-offvoy;
   iniv=rand()%NumVirtuals;
   for (v=0;v<NumVirtuals;v++)
   {
     virt=(iniv+v)%NumVirtuals;
     if (offset>0)
       res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
     else if (offset<0)
       res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
     else res=NULL;
     //Muestra(IntToStr(Cycles)+" Cabecera: Nodo "+IntToStr(nodo)+", dimension "+IntToStr(dim)+", virtual "+IntToStr(virt)+", offset "+IntToStr(offset));
     if (res!=NULL)
       if (res->Prev==NULL)
	   {
         if (dim!=deterdim)
         {
           if (virt!=0) return(res);
         }
         else return(res);
	   }
   }
 }
 return(NULL);
}

// Completamente adaptativo para mallas, naturalmente presenta bloqueos mortales
Device* Red::RoutingFullyAdaptiveMesh(int nodo, int Dest)
{
int offset,offvoy,offdest;
int iniv,v,virt;
int inidim,d,dim;
Device *res;
 if (nodo==Dest)
 {
   iniv=rand()%NumVirtProcEje;
   for (v=0;v<NumVirtProcEje;v++)
   {
     virt=(iniv+v)%NumVirtProcEje;
     res=&(Nodes[nodo].Buf_out[NumBuf+virt].Fifo[0]);
     if (res!=NULL) if (res->Prev==NULL) return(res);
   }
   return(NULL);
 }
 inidim=rand()%Dimensions;
 //inidim=0; // va igual
 for (d=0;d<Dimensions;d++)
 {
   dim=(inidim+d)%Dimensions;
   offvoy=(nodo/pot(NumNodesDim,dim))%NumNodesDim;
   offdest=(Dest/pot(NumNodesDim,dim))%NumNodesDim;
   offset=offdest-offvoy;
   iniv=rand()%NumVirtuals;
   for (v=0;v<NumVirtuals;v++)
   {
     virt=(iniv+v)%NumVirtuals;
     if (offset>0)
     {
       res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,POS,virt)].Fifo[0]);
     }
     else if (offset<0)
     {
         res=&(Nodes[nodo].Buf_out[ChannelId(0,dim,NEG,virt)].Fifo[0]);
     }
     else res=NULL;;
     //Muestra(IntToStr(Cycles)+" Cabecera: Nodo "+IntToStr(nodo)+", dimension "+IntToStr(dim)+", virtual "+IntToStr(virt)+", offset "+IntToStr(offset));
     if (res!=NULL) if (res->Prev==NULL) return(res);
   }
 }
 return(NULL);
}


//-------------------------------------------------
// PACKET METHODS
//-------------------------------------------------

Packet::Packet(class Red *unared, int longit, int orig, int dest,int packid)
{
 Next=NULL;
 Prev=NULL;
 Id=packid;
 LaRed=unared;
 Transmitted=false;
 Cycles=0;
 CycBlock=0;
 CycBlockEmit=0;
 CycBlockRecep=0;
 CycHead=0;
 PathNodes=0;
 Length=longit;
 Src=orig;
 Dest=dest;
 Blocked=false;
 ElFlit=new Flit[Length];
 Color=(rand()%0x100)*0x10000+(rand()%0x100)*0x100+(rand()%0x100);
 // put the packet in the packet list of proccesor:
 LaRed->Nodes[Src].ElProcIn.putPacket(this);
 LaRed->NumPackCreated[0]++;
 LaRed->NumPackCreated[1]++;
 if (LaRed->NumPackDummy==0)
 {
   LaRed->CreatedFlits[0]=LaRed->CreatedFlits[0]+Length;
   LaRed->CreatedFlits[1]=LaRed->CreatedFlits[1]+Length;
 }
}

Packet::~Packet()
{
 delete [] ElFlit;
 // Remove from packet list
 if (this==LaRed->PacketsLast) LaRed->PacketsLast=Prev;
 if (this==LaRed->Packets)
 {
   LaRed->Packets=this->Next;
 }
 else
 {
   Prev->Next=Next;
 }
 if (Next!=NULL) Next->Prev=Prev;
 // Generate dependent packets
 if (Id>=0) LaRed->LaunchTracePack(Id);
 // Update counters
 LaRed->NumPackTrans[0]++;
 LaRed->NumPackTrans[1]++;
 if (LaRed->NumPackDummy>0)
 {
   LaRed->NumPackDummy--;
 }
 else
 {
   Cycles=Cycles-2; // ajuste latencia
   LaRed->LatLast=Cycles;
   for (int i=0;i<LaRed->NC;i++)
   {
     LaRed->LatTotal[i]=LaRed->LatTotal[i]+Cycles;
     LaRed->LatBlock[i]=LaRed->LatBlock[i]+CycBlock;
     LaRed->LatBlockEmit[i]=LaRed->LatBlockEmit[i]+CycBlockEmit;
     LaRed->LatBlockRecep[i]=LaRed->LatBlockRecep[i]+CycBlockRecep;
     LaRed->LatTrans[i]=LaRed->LatTrans[i]+Cycles;
     LaRed->LatTrans2[i]=LaRed->LatTrans2[i]+Cycles*Cycles;
     LaRed->LatHead[i]=LaRed->LatHead[i]+CycHead;
     LaRed->PathNodes[i]=LaRed->PathNodes[i]+PathNodes;
     LaRed->TransFlits[i]=LaRed->TransFlits[i]+Length;
   }
 }
}

void Packet::SendToRed(void)
{
int i;
Processor *auxproc;
 // get from the processor packet list
 auxproc=&(LaRed->Nodes[Src].ElProcIn);
 auxproc->PackLength++;
 if (Prev==NULL)
 {
    auxproc->PackList=Next;
 }
 else
 {
   Prev->Next=Next;
 }
 if (Next!=NULL) Next->Prev=Prev;
 // put the packet into the processor
 for (i=0;i<Length;i++)
 {
   ElFlit[i].Pos=i;
   ElFlit[i].Lugar=PutTo(auxproc,i);
 }
 // put the packet in the Red list of packets
 Next=NULL;
 Prev=LaRed->PacketsLast;
 if (LaRed->PacketsLast!=NULL)
 {
   LaRed->PacketsLast->Next=this;
   LaRed->PacketsLast=this;
 }
 else LaRed->PacketsLast=LaRed->Packets=this;
}

// Puts a flit into a device
Device* Packet::PutTo(Device* disp, int i)
{
int j,ncanal,nbuf,mivirt;
bool libre;
 if (disp->Dev!=eProcessor)
 {
   disp->Busy=true;
   disp->Paq=this;
 }
 else
 {
   disp->Busy=false;
   disp->Paq=NULL;
 }
 disp->Elapsed=0;
 disp->Value=i+1;
 // anyade el retraso de la conmutacion
 if ( (disp->Dev==eCrossSwitch) && (i==0) )
   disp->Elapsed=-LaRed->SwitchDelay;   // Delay de la conmutacion
 if (disp->Dev==eChannel)
 {
     ((Channel*)disp)->Transmitted=false;
     mivirt=disp->MyBuf%LaRed->NumVirtuals;
     if ( (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
     {
       nbuf=disp->MyBuf/LaRed->NumVirtuals;
       libre=true;
       for (j=0;j<LaRed->NumVirtuals;j++)
       {
         ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+j;
         if ( (LaRed->Channels[ncanal].Busy) && (LaRed->Channels[ncanal].Paq!=NULL) && (j!=mivirt) )
           libre=false;
       }
       if (libre)
         for (j=0;j<LaRed->NumVirtuals;j++)
         {
           ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+j;
           LaRed->Channels[ncanal].Turn=mivirt;
         }
     }
     else
       ((Channel*)disp)->Turn=mivirt;
 }
 if (disp->Next!=NULL)
 {
   if (disp->Next->Dev==eCrossSwitch)
     disp=PutTo(disp->Next,i);
   // el tema de los virtuales
   else if (disp->Next->Dev==eChannel)
     disp=PutTo(disp->Next,i);
 }
 if (disp->Dev==eProcessor)
   if ( (disp->Next->Dev==eProcessor) && (i==Length-1) )
   {
            Transmitted=true;  //se acabo;
            disp->Paq=NULL;
            disp->Busy=false;
   }
 // Fifo forward
 if (LaRed->Forwarding==1)
 {
   // block detection in fifo when forwarding
   if ( (i==0) &&
        (disp->Dev==eFifoFlit) &&
        (disp->Next->Dev==eFifoFlit) &&
        ((disp->Next->Busy==true) || (disp->Next->Paq!=NULL)) )
           CycBlock++;
   // forward flit
   if ( (disp->Dev==eFifoFlit) &&
        (disp->Next->Dev==eFifoFlit) &&
        (disp->Next->Busy==false) &&
        ((disp->Next->Paq==this)||(disp->Next->Paq==NULL)) )
   {
     GetFrom(disp,i);
     disp=PutTo(disp->Next,i);
   }
 }
 return(disp);
}

// GetFrom un flit del dispositivo
Device* Packet::GetFrom(Device* disp, int i)
{
int nbuf,j,ncanal,ticket;
 if ( (disp->Dev==eCrossSwitch) || (disp->Dev==eChannel) ) GetFrom(disp->Prev,i);
 disp->Busy=false;
 disp->Value=0;
 if (disp->Dev==eChannel)
 {
            if ( (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
            {
               nbuf=disp->MyBuf/LaRed->NumVirtuals;
               ticket=((Channel*)disp)->Turn;
               for (j=0;j<LaRed->NumVirtuals;j++)
               {
                 ticket=(ticket+1)%LaRed->NumVirtuals;
                 ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+ticket;
                 if ( (LaRed->Channels[ncanal].Busy) && (LaRed->Channels[ncanal].Paq!=NULL) )
                 {
                    LaRed->Channels[ncanal].Transmitted=true;
                    break;
                 }
               }
               for (j=0;j<LaRed->NumVirtuals;j++)
               {
                 ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+j;
                 LaRed->Channels[ncanal].Turn=ticket;
               }
            }
 }
 if (i==Length-1) // el ultimo flit
 {
   disp->Paq=NULL; // libera el disp para otro paquete
   if (disp->Prev!=NULL)
     if (disp->Prev->Dev==eCrossSwitch)
     {
       disp->Prev=NULL;
     }
   if (disp->Dev==eCrossSwitch)
   {
              disp->Next=NULL;  // Pone a null el crossbar para el ste paquete
   }
 }
 return((Device *)NULL);  // por devolver algo
}

// Funcion basica para mover el paquete un ciclo
Packet* Packet::RunCycle()
{
int i;
int nbuf,j,ncanal,ticket;
bool avanza;
Device *disp,*aux;
  Cycles++;
  for (i=0;i<Length;i++)
  {
    if (i>0)
    {
      disp=ElFlit[i-1].Lugar;
      if (disp->Dev==eProcessor)
        if (disp->MyNode!=Dest) break; // no hace falta que siga
    }
    disp=ElFlit[i].Lugar;
    // pone el next del eprocesador de salida a lo que toca
    // if (LaRed->NumVirtuals>1)
    if (disp->Dev==eProcessor)
    {
      if (disp->MyNode!=Dest)
      {
        if (i==0) //busca uno libre
        {
          for (j=0;j<LaRed->NumVirtProcInj;j++)  
          {
            aux=&(LaRed->Nodes[disp->MyNode].Buf_in[LaRed->NumBuf+j].Fifo[0]);
            if ( (aux->Busy==false)  && (aux->Paq==NULL) )
            {
              disp->Next=aux;
              // The packet enters the network
              LaRed->NumPackEmit[0]++;
              LaRed->NumPackEmit[1]++;
              if (LaRed->NumPackEmit[0]>=LaRed->NumPackDummyIni)
              {
                LaRed->EmiFlits[0]=LaRed->EmiFlits[0]+Length;
                LaRed->EmiFlits[1]=LaRed->EmiFlits[1]+Length;
              }
              LaRed->CyclesEmit[0]=LaRed->Cycles[0];
              ((Processor*)disp)->PackLength--;
              CycBlockEmit=Cycles-1;
              CycBlock=0;
              Cycles=1;
              break;
            }
          }
        }
        else
        {
          if (disp->Next->Paq!=this)
          {
            for (j=0;j<LaRed->NumVirtProcInj;j++)
            {
              aux=&(LaRed->Nodes[disp->MyNode].Buf_in[LaRed->NumBuf+j].Fifo[0]);
              if (aux->Paq==this)
              {
                disp->Next=aux;
                break;
              }
            }
            if (disp->Next->Paq!=this) ShowError("WRONG!!");
          }
        }
      }
    }
    if (disp!=NULL)
    {
      disp->Elapsed++;
      if ( (disp->Dev==eChannel) && (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
        if ( (((Channel *)disp)->Transmitted) || (((Channel *)disp)->Turn!=disp->MyBuf%LaRed->NumVirtuals) )
        {
          ((Channel *)disp)->Transmitted=false;
          disp->Elapsed--;  // no avanza el tiempo hasta que obtiene el canal fisico
        }
      if (disp->Next==NULL) // Seguramente entra en el crossbar
      {
        if ( (disp->Dev==eCrossSwitch) && (i==0) )   // cabeza y cross
        {
          // Aqui va el algoritmo de encaminamiento
          switch (LaRed->Routing)
          {
            case 0: // Algoritmo XY en mallas bidireccionales
              disp->Next=LaRed->RoutingOrderDimMesh(disp->MyNode,Dest);
              break;
            case 1: // Algoritmo XY en toros bi o unidireccionales
              disp->Next=LaRed->RoutingOrderDimTorus(disp->MyNode,Dest); // Tiene bloqueos mortales!!
              break;
            case 2: // Algoritmo C.A. de Duato usando XY en mallas
              disp->Next=LaRed->RoutingDuatoOrderDimMesh(disp->MyNode,Dest);
              break;
            case 3: // Completamente adaptativo para mallas
              disp->Next=LaRed->RoutingFullyAdaptiveMesh(disp->MyNode,Dest);
              break;
            default: ShowError("Error: Unknown routing.");
          }
          if (disp->Next!=NULL)
          {
            disp->Next->Prev=disp;
            PathNodes++;
          }
        }
        else ShowError("Error, unspected null!!");
      }
      //medida de la latencia de la cabeza
      if (i==0)
        if (disp->MyNode==Dest)
          if (disp->Next!=NULL)
            if (disp->Next->Dev==eProcessor)
              if (disp->Elapsed >= disp->Delay)
                if (CycHead==0) CycHead=Cycles-2;  // Ajuste ciclos.
      // Si canal virtual bloqueado le da el turno a otro paquete
      if (disp->Dev==eChannel)
        if ( (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
          if (disp->Next!=NULL)
            if (disp->Next->Busy)
              if ( ((Channel*)disp)->Turn==disp->MyBuf%LaRed->NumVirtuals )
              {
                nbuf=disp->MyBuf/LaRed->NumVirtuals;
                ticket=((Channel*)disp)->Turn; // el que hay por si no hay otro
                for (j=0;j<LaRed->NumVirtuals;j++)
                {
                  ticket=(ticket+1)%LaRed->NumVirtuals;
                  ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+ticket;
                  if ( (LaRed->Channels[ncanal].Busy) && (LaRed->Channels[ncanal].Paq!=NULL) )
                    if (LaRed->Channels[ncanal].Next!=NULL)
                      if (LaRed->Channels[ncanal].Next->Busy==false)   // que no este bloqueado tampoco
                      {
                        LaRed->Channels[ncanal].Transmitted=true;
                        ((Channel*)disp)->Transmitted=false;
                        break;
                      }
                }
                for (j=0;j<LaRed->NumVirtuals;j++)
                {
                  ncanal=disp->MyNode*LaRed->NumBuf+nbuf*LaRed->NumVirtuals+j;
                  LaRed->Channels[ncanal].Turn=ticket;
                }
              }
      // condicion de bloqueo:
      if (i==0)
      {
        Blocked=false;
        if (disp->Next==NULL) Blocked=true;   // Blocked in switch
        else
        {
          if ( (disp->Next->Paq!=NULL) || (disp->Next->Busy) )
          {
            if (disp->Elapsed >= disp->Delay)
              Blocked=true;     // Blocked in network
            if (disp->Dev==eProcessor)
              Blocked=true;     // Blocked in processor
          }
        }
        if (Blocked)
        {
          if (disp->Next==NULL) disp->Elapsed=-LaRed->SwitchDelay;
          else disp->Elapsed=0;
        }

        if ( (disp->Dev==eChannel) && (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
        {
          if ( (((Channel *)disp)->Transmitted) || (((Channel *)disp)->Turn!=disp->MyBuf%LaRed->NumVirtuals) )
            Blocked=true;  // Blocked in vitual channel
        }
        if (Blocked)
        {
          CycBlock++;
//          if (disp->Dev==eProcessor) CycBlockEmit++;
          if ( (disp->Dev==eCrossSwitch) && (disp->MyNode==Dest) ) CycBlockRecep++;
        }
      }
      // condicion de avance:
      avanza=false;
      if (disp->Next!=NULL)
        if ( (disp->Elapsed >= disp->Delay) && (disp->Next->Busy==false) &&
             ((disp->Next->Paq==this)||(disp->Next->Paq==NULL)) )
        {
            avanza=true;
            if ( (disp->Dev==eChannel) && (LaRed->NumVirtuals>1) && !(LaRed->PhysChannel) )
            {
              if ( ((Channel *)disp)->Turn!=disp->MyBuf%LaRed->NumVirtuals ) avanza=false;
            }
         }
      if (avanza)
      {
          aux=disp->Next;
          ElFlit[i].Lugar=GetFrom(disp,i);
          ElFlit[i].Lugar=PutTo(aux,i);
      }
    }
    else  // error
    {
      ShowError("Error: packets with Nulls!!");
    }
  }
  if (Transmitted)
  {
    delete this;
    return(NULL);
  }
  return(this);
}


