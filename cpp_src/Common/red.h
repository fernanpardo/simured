//---------------------------------------------------------------------------
// Methods for working with Packets and Networks
// The ShowError function is the only one that may
// depend on the system; feel free to change it.
//
// Fernando Pardo, november, 2005. Universidad de Valencia.
//---------------------------------------------------------------------------
#ifndef FERredH
#define FERredH

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#define INIVAL   999999
#define POS   1
#define NEG   0
//#define NC    2  // Number of sets of counters

enum TypeDev {eFifoFlit,eChannel,eInternal,eProcessor,eCrossSwitch};

int pot(int base, int exponente);
void ShowError(const char *a);

//-----------------------------------------------------------------------------
// LAS CLASES DE LAS RED Y LOS PAQUETES
//-----------------------------------------------------------------------------


//-----------------------------------------------------------------------------
// La red  (Network)
//-----------------------------------------------------------------------------

// Cualquier elemento primario de la red
class Device
{
public:           // All public
 int Elapsed;     // retraso acumulado
 int MyNode;      // Nodo al que pertenece
 Device *Next;
 Device *Prev;
 int Delay;     // retraso propio de ese dispositivo
 int Value;     // Value almacenado
 int MyBuf;     // Numero de canal o buffer
 bool Busy;     // Esta lleno por un flit anterior
 TypeDev Dev;
 class Packet *Paq;
 class Red *LaRed;
 Device();
};

class Processor : public Device
{
public:
 class Packet *PackList;
 int PackLength;
 Processor();
 void putPacket(class Packet *Pack);
};

class Channel : public Device
{
public:
 int Turn;
 bool Transmitted;
 void ChannelIni(class Red *unared);
};


class CrossSwitch : public Device
{
public:
 void CrossSwitchIni(class Red *unared);
};

class CrossBar
{
public:
 class Red *LaRed;
 CrossSwitch *Output; // Uno por cada buffer de salida
 CrossBar(class Red *unared);
 ~CrossBar();
};

class FifoFlit : public Device
{
public:
 void FifoFlitIni(class Red *unared);
};

class Buffer
{
 class Red *LaRed;
public:
 void BufferIni(class Red *unared,int buf);
 FifoFlit *Fifo; // ristra de flits en el buffer
 ~Buffer();
};

class Node
{
 class Red *LaRed;
public:
// Los buffers NumBuf (ultimos) son el in y el out internos
 Buffer *Buf_in;
 Buffer *Buf_out;
 CrossBar *ElCrossBar;
 Processor ElProcOut;
 Processor ElProcIn;
 void NodeIni(class Red *unared);
 ~Node();
};

class Red
{
 int Switching;     // Tipo de control de flujo
public:
 class PacketTr
 {
   public:
   int Id;
   int Orig;
   int Dest;
   int Len;
   int DepId;
   class PacketTr *Next,*Prev;
 };
  //FILE *fich;

 Channel *Channels;    // Conjunto de Canales
 Node *Nodes;      // Conjunto de Nodos
 Packet *Packets;     // Pointer to the beginning of the packet list
 Packet *PacketsLast; // Pointer to the end of the packet list

 PacketTr *PackTraces;  //Pointer to the beginning of the trace packet list 
 PacketTr *PackTracesLast;  //Pointer to the end of the trace packet list 

 // Statistic Counters
 enum {NC=2};            // Number of sets of counters
 int LatLast;            // Latencia del último paquete
 int Cycles[NC];         // Cycles desde el inicio
 int CyclesEmit[NC];     // Total Packet emission time
 int CyclesCreation[NC]; // Total Packet creation time  < CyclesEmit
 int LatTrans[NC];       // Suma de latencias (solo movimiento)
 long double LatTrans2[NC];     // power 2 sum of latencies (for std deviation)
 int LatBlock[NC];       // Suma de latencias (durante bloqueos)
 int LatBlockEmit[NC];   // Suma de latencias (durante bloqueos en el p. emisor)
 int LatBlockRecep[NC];  // Suma de latencias (durante bloqueos en el p. receptor)
 int LatTotal[NC];       // La suma de las anteriores
 int LatHead[NC];        // Suma de Latencias de la cabeza
 int EmiFlits[NC];       // Flits emitidos
 int CreatedFlits[NC];   // Flits creados
 int TransFlits[NC];     // Flits transmitidos
 int NumPackTrans[NC];   // Packets Transmitidos completamente
 int NumPackEmit[NC];    // Emitted packets to the net
 int NumPackCreated[NC]; // Created packets>Emitted packets
 int PathNodes[NC];      // Nodos atravesados en los caminos recorridos

 int Routing;       // Mecanismo de encaminamiento
 bool BloqueoTotal; // Indica si se produjo un bloqueo mortal

 int NumNodes;      // Numero de nodos
 int NumBuf;        // Numero de buffers por nodo
 int Dimensions;    // Numero de dimensiones
 int NumNodesDim ;  // Numero de nodos por dimension
 int NumVirtuals;   // Numero de canales virtuales
 int NumVirtProcInj;// Number of injection processor channels
 int NumVirtProcEje;// Number of ejection processor channels
 int NumFlitBuf;    // Numero de flits en el buffer
 int Directions;    // 1=unidireccional, 2=bidireccional
 int Forwarding;    // 0= no se adelantan los flits en el fifo, 1= si
 bool PhysChannel;  // Indica si los canales virtuales son fisicos en realidad

 int FifoDelay;   // Retraso del fifo
 int CrossDelay;  // Retraso del crossbar
 int ChannelDelay;  // Retraso del canal
 int SwitchDelay;  // Retraso en la conmutacion

 int NumPackDummy;     // Number of dummy warm up packets Warm-Up
 int NumPackDummyIni;  // Initial number of dummy warm up packets Warm-Up

 Red(int dimen, int numdim, int numvirt, int numvirtinj, int numvirteje, int numflitbuf, int direcciones, int adelanto, bool fisicos, int routing, int switching, int fiforetraso, int interretraso, int canalretraso, int conmuretraso, int dummypacket);
 ~Red();
 void RunCycle(); // Calcula un nuevo ciclo
 void ResetStatSet(int set);
 int ChannelId(int numnodo, int dimcanal, int direccion, int numvirt);
 int Trans(int act, int Dim, int di); // devuelve el nodo adyacente de act en la dimension Dim sensido di
 Device* RoutingOrderDimMesh(int nodo,int Dest);
 Device* RoutingOrderDimTorus(int nodo,int Dest);
 Device* RoutingDuatoOrderDimMesh(int nodo,int Dest);
 Device* RoutingFullyAdaptiveMesh(int nodo,int Dest);
 void InsertTracePack(int depid, int org, int dest, int len, int id);
 void LaunchTracePack(int depid);
 void DeleteTracePack(PacketTr *pack);
};


//-----------------------------------------------------------------------------
// PACKET
//-----------------------------------------------------------------------------

class Flit
{
public:
 int Pos; // posicion del flit en el paquete
 Device *Lugar;  // donde se encuentra ese flit
};

class Packet
{
public:
 bool Transmitted; // Indica que el paquete llego entero
 bool Blocked;     // Indica si el paquete se encuentra bloqueado
 int Id;           // Packet Id
 int Length;       // Flits del paquete
 int Src,Dest;      // nodo origen y destino
 int Cycles;        // ciclos de reloj consumidos
 int CycBlock;      // ciclos en los que esta bloqueado totales
 int CycBlockEmit;  // ciclos en los que esta bloqueado en el proc emisor
 int CycBlockRecep; // ciclos en los que esta bloqueado en el proc receptor
 int CycHead;       // ciclos que tarda la cabeza en total
 int PathNodes;     // Nodos atravesados en los caminos recorridos
 int Color;         // Color RGB
 Flit *ElFlit;      // matriz de flits
 class Red *LaRed;
 Packet *Next;
 Packet *Prev;
 Packet(class Red *unared, int longit, int orig, int dest, int packid); // genera un paquete
 ~Packet();
 Packet* RunCycle(); // avanza el paquete un ciclo
 Device* PutTo(Device* disp, int i);
 Device* GetFrom(Device* disp, int i);
 void SendToRed(void);
};
//-----------------------------------------------------------------------------
#endif
