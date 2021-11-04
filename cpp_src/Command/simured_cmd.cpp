#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <sys/time.h>
#include <unistd.h>
#include <sstream>
#include "red.h"

// uncomment to statically link pthreads (does not work anyway)
//#define PTW32_STATIC_LIB    1
#include <pthread.h>

#define MAX_TH_NUM 50


// DEFAULT VALUES
// Network definition
#define dLANG          1 // 0=spanish, 1=english;
#define dDIMENSIONS    2
#define dNODOSDIM      4
#define dNUMVIRT       1
#define dNUMVIRTINJ    1
#define dNUMVIRTEJE    1
#define dALLVIRTEQ     true // from ver 3.1
#define dLONBUFFER     2
#define dNUMDIR        2  // 2=bidir, 1=unidir
#define dFORWARDING    1  // 1=buffer forwarding, 0=no forward
#define dPHYSICAL      false
#define dSWITCH        0  // 0=wormhole
#define dROUTING       0  // 0=xy mesh, 1=xy toro, 2=Duato mesh, 3=fully adapt. mesh.
#define dDELFIFO       1
#define dDELCROSS      1
#define dDELCHANNEL    1
#define dDELSWITCH     1

// Packet
#define dPACKNUM     8000
#define dPACKLEN       64
#define dPACKHEADLEN    0
#define dPACKPROD     0.5  // (flits/node/cycle)
#define dPACKNUMDUMMY   0
// Trace file
#define dUSETRACE       0  // 0=no, 1=yes
#define dTRACENAME      "trace.trc"
#define dNOREADLEN      0  // OverRide packet lengh specified in trace file

// Simulation
#define dJUSTONE       false    
#define dSIMSTOPEMIT   0
// Inner loop variable
#define dSIMUVAR       0   // 0=Productivity (flits/node/cycle)
#define dLAVARINI      0.1
#define dLAVARFIN      0.8
#define dPUNTOS        5
#define dESCALA        0   // 0=linear, 1=log
// Outer loop variable
#define dSIMUVAR2      4   // 0=none, 1=dimensions, 2=nodes/dimension, 3=lon.buffer, 4=virtuals
#define dLAVARINI2     1 
#define dLAVARFIN2     2
#define dPUNTOS2       2
#define dESCALA2       0   // 0=linear, 1=log
#define dFINALSTATS    1   // 1=only final statistics, 0=continuous tracking
#define dSTATPOINTS  100   // points for continuos stat tracking
using namespace std;

int LANG,
DIMENSIONS,   
NODOSDIM,     
NUMVIRT,
NUMVIRTINJ,
NUMVIRTEJE,
ALLVIRTEQ,
LONBUFFER,
NUMDIR,       
FORWARDING,        
SWITCH,       
ROUTING,      
DELFIFO,      
DELCROSS,     
DELCHANNEL,    
DELSWITCH;   
bool PHYSICAL;

int PACKNUM,
PACKLEN,    
PACKHEADLEN,
USETRACE,   
NOREADLEN,
PACKNUMDUMMY; 
double PACKPROD; 
string TRACENAME;

bool JUSTONE;
int SIMSTOPEMIT;
int SIMUVAR,   
PUNTOS,     
ESCALA,     
SIMUVAR2,
PUNTOS2,    
ESCALA2;
double LAVARINI,   
LAVARFIN,   
LAVARINI2,  
LAVARFIN2;

int FINALSTATS,STATPOINTS;
    
Red *LaRedGlobal[MAX_TH_NUM];
int NumThreads;
int NumProcs;
bool SimuStop;
int Continuar;


pthread_t Hilos[MAX_TH_NUM];
struct thread_arg
{
                class Red * LaRedLocal;
                FILE *fich;
                FILE *f;
                int paqlong;
                int cablong;
                int Tope;
                double TasaEmit;
                int *continuar;
                int noleelong;
                int valorval;
};
struct thread_arg thread_arg_array[MAX_TH_NUM];


string FloatToStr(double a)
{
stringstream sf;
string st;
 sf << a;
 sf >> st;
 return(st);
}

string IntToStr(int a)
{
stringstream sf;
string st;
 sf << a;
 sf >> st;
 return(st);
}

void ShowMsg(string a)
{
  fprintf(stderr,"%s\n",a.c_str());
}

void Muestra(string a)
{
  ShowMsg(a);
}

void ShowStats(class Red *LaRedLocal, int set)
{
double latmed;
int realTrans;
string Cyc,PackGen,PackSent,PackRec,ProdGen,ProdSent,ProdRec;
string LatHead,LatHeadNo,LatPack,LatPackNo,StdDev,CycBlock,MeanPathNod,MeanPathCha;
 if (set==0)
   realTrans=LaRedLocal->NumPackTrans[set]-LaRedLocal->NumPackDummyIni;
 else
   realTrans=LaRedLocal->NumPackTrans[set];
 latmed=(double)(LaRedLocal->LatTrans[set])/realTrans;
 Cyc=IntToStr(LaRedLocal->Cycles[set]);
 PackGen=IntToStr(LaRedLocal->NumPackCreated[set]);
 PackSent=IntToStr(LaRedLocal->NumPackEmit[set]);
 PackRec=IntToStr(LaRedLocal->NumPackTrans[set]);
 if (LaRedLocal->Cycles[set])
 {
   if (LaRedLocal->CyclesCreation[set])
       ProdGen=FloatToStr((double)LaRedLocal->CreatedFlits[set]/LaRedLocal->CyclesCreation[set]/LaRedLocal->NumNodes);
   if (LaRedLocal->CyclesEmit[set])
       ProdSent=FloatToStr((double)(LaRedLocal->EmiFlits[set])/LaRedLocal->CyclesEmit[set]/LaRedLocal->NumNodes);
   ProdRec=FloatToStr((double)(LaRedLocal->TransFlits[set])/LaRedLocal->Cycles[set]/LaRedLocal->NumNodes);
   if (realTrans)
   {
     LatHead=FloatToStr((double)(LaRedLocal->LatHead[set])/realTrans);
     LatHeadNo=FloatToStr((double)(LaRedLocal->LatHead[set]-LaRedLocal->LatBlock[set])/realTrans);
     LatPack=FloatToStr(latmed);
     LatPackNo=FloatToStr((double)(LaRedLocal->LatTrans[set]-LaRedLocal->LatBlock[set])/realTrans);
     StdDev=FloatToStr(sqrt((double)(LaRedLocal->LatTrans2[set])/realTrans-latmed*latmed));
     CycBlock=FloatToStr((double)(LaRedLocal->LatBlock[set])/realTrans);
     MeanPathNod=FloatToStr((double)LaRedLocal->PathNodes[set]/realTrans);
     MeanPathCha=FloatToStr((double)LaRedLocal->PathNodes[set]/realTrans-1);
   }
 }
 Muestra("--------------------------------------------");
 if (LANG==0)
 {
   Muestra("Ciclos: "+Cyc);
   Muestra("Paquetes Generados: "+PackGen);
   Muestra("Paquetes Enviados:  "+PackSent);
   Muestra("Paquetes Recibidos: "+PackRec);
   if (LaRedLocal->Cycles[set])
   {
     Muestra("---------------------------------------");
//     if (LaRedLocal->CyclesEmit[set]) Muestra("Productividad Objetivo (flits/ciclo):      "+FloatToStr((double)LaRedLocal->EmiFlits[set]/LaRedLocal->CyclesEmit[set]));
     if (LaRedLocal->CyclesCreation[set])
       Muestra("Tasa  Creación (flits/ciclo/nodo): "+ProdGen);
     //Muestra("Productividad (flits/ciclo):       "+FloatToStr((double)LaRedLocal->EmiFlits[set]/LaRedLocal->Cycles[set]));
     if (LaRedLocal->CyclesEmit[set])
       Muestra("Tasa de  Envio (flits/ciclo/nodo): "+ProdSent);
     Muestra("Tasa Recepción (flits/ciclo/nodo): "+ProdRec);
     if (realTrans)
     {
       Muestra("---------------------------------------");
       Muestra("Latencia Cabeza:                 "+LatHead);
       Muestra("Latencia Cabeza (sin bloqueos):  "+LatHeadNo);
       Muestra("Latencia Paquete:                "+LatPack);
       Muestra("Desviación Estándar:             "+StdDev);
       Muestra("Latencia Paquete (sin bloqueos): "+LatPackNo);
       Muestra("Ciclos de Bloqueo en Red:        "+CycBlock);
       //Muestra("Ciclos de Bloqueo en el Emisor:  "+FloatToStr((double)(LaRedLocal->LatBlockEmit[set])/realTrans));
//       Muestra("----- en el Proc. Receptor:      "+IntToStr(LaRedLocal->LatBlockRecep/LaRedLocal->NumPackTrans));
       //Muestra("Latencia Total del Paquete:      "+FloatToStr((double)(LaRedLocal->LatTotal[set])/realTrans));
       Muestra("--------------------------------");
       Muestra("Camino medio (nodos):        "+MeanPathNod);
       Muestra("Camino medio (canales):      "+MeanPathCha);
     }
   }
 }
 else if (LANG==1) // ingles
 {
   Muestra("Cycles: "+Cyc);
   Muestra("Created  Packets: "+PackGen);
   Muestra("Sent     Packets: "+PackSent);
   Muestra("Received Packets: "+PackRec);
   if (LaRedLocal->Cycles[set])
   {
     Muestra("---------------------------------------");
//     if (LaRedLocal->CyclesEmit[set]) Muestra("Target Productivity (flits/cycle):      "+FloatToStr((double)LaRedLocal->EmiFlits[set]/LaRedLocal->CyclesEmit[set]));
     if (LaRedLocal->CyclesCreation[set])
       Muestra("Creation rate (flits/cycle/node): "+ProdGen);
     //Muestra("Productivity (flits/cycle):       "+FloatToStr((double)LaRedLocal->EmiFlits[set]/LaRedLocal->Cycles[set]));
     if (LaRedLocal->CyclesEmit[set])
       Muestra("Delivery rate (flits/cycle/node): "+ProdSent);
     Muestra("Arrival  rate (flits/cycle/node): "+ProdRec);
     if (realTrans)
     {
       Muestra("---------------------------------------");
       Muestra("Head Latency on the Net:            "+LatHead);
       Muestra("Head Latency on the Net (no locks): "+LatHeadNo);
       Muestra("Packet Latency:                     "+LatPack);
       Muestra("Standard Deviation:                 "+StdDev);
       Muestra("Packet Latency (no locks):          "+LatPackNo);
       Muestra("Lock Cycles on the Net:             "+CycBlock);
//       Muestra("Lock Cycles at the Emitter:         "+FloatToStr((double)(LaRedLocal->LatBlockEmit[set])/realTrans));
//       Muestra("----- en el Proc. Receptor:         "+IntToStr(LaRedLocal->LatBlockRecep/LaRedLocal->NumPackTrans));
//       Muestra("Total Packet Latency:               "+FloatToStr((double)(LaRedLocal->LatTotal[set])/realTrans));
       Muestra("--------------------------------");
       Muestra("Average Path (nodes):      "+MeanPathNod);
       Muestra("Average Path (channels):   "+MeanPathCha);
     }
   }
 }
 Muestra(" ");
}

void PrintStatsToFile(class Red *LaRedLocal, FILE *fich, int valor, int set)
{
int realTrans;
 if (set==0) realTrans=LaRedLocal->NumPackTrans[set]-LaRedLocal->NumPackDummyIni;
 else realTrans=LaRedLocal->NumPackTrans[set];
 if (set==1) LaRedLocal->CyclesEmit[set]=LaRedLocal->Cycles[set];
 if ( (realTrans>0) &&
      (LaRedLocal->CyclesEmit[set]>0) &&
      (LaRedLocal->Cycles[set]>0) && (fich!=NULL) )
 {
       fprintf(fich,"%d;%d;%d;%d;%f;%f;%f;%f;%f;%f;%f;%f\n",
         valor,
         LaRedLocal->Cycles[0],
         LaRedLocal->NumPackEmit[0],
         LaRedLocal->NumPackTrans[0],
         (double)(LaRedLocal->EmiFlits[set])/LaRedLocal->CyclesEmit[set]/LaRedLocal->NumNodes,
         (double)(LaRedLocal->TransFlits[set])/LaRedLocal->Cycles[set]/LaRedLocal->NumNodes,
         (double)(LaRedLocal->LatHead[set])/realTrans,
         (double)(LaRedLocal->LatHead[set]-LaRedLocal->LatBlock[set])/realTrans,
         (double)(LaRedLocal->LatTrans[set])/realTrans,
         (double)(LaRedLocal->LatTrans[set]-LaRedLocal->LatBlock[set])/realTrans,
         (double)(LaRedLocal->LatBlock[set])/realTrans,
         (double)(LaRedLocal->PathNodes[set])/realTrans);
 }
}

// Funcion que simula un conjunto de paquetes con las condiciones que se le pasan
void Simula(class Red *LaRedLocal, FILE *fich,FILE *f,int paqlong,int cablong,int Tope,double TasaEmit,int *continuar,int noleelong,int valorval)
{
int orig,dest;
int CicloFich,FinFich,milongit,CicloPack,IdPack;
int SucRead;
char CicloTxt[100],LongId1[100],LongId2[100];
int packpunt;
float Tasaux;    // paquete/ciclo
char linea[100];
bool contsimu;
int myCycles;
time_t t;

 srand(((unsigned) time(&t))%RAND_MAX);
  //Dibuja(LaRedLocal);
  //TasaEmit=TasaEmit*LaRedLocal->NumNodes/paqlong; // solo consideramos datos
  if (fich!=NULL)
  {
    // first trace pass to see dependent packets
    Tope=0;
    FinFich=0;
    while (!FinFich)
    {
            CicloPack=-1;
            CicloFich=-1;
            IdPack=-1;
            if (fgets(linea,150,fich)!=NULL)
            {
              if (linea[0]!='#')  // comentario
              {
                milongit=paqlong+cablong;
                SucRead=sscanf(linea,"%s %d %d %s %s",CicloTxt,&orig,&dest,LongId1,LongId2);
                if (noleelong) milongit=paqlong+cablong;
                if (SucRead<3) FinFich=1;
                else FinFich=0;
                if (sscanf(CicloTxt,"%d",&CicloFich)!=1)
                  if (sscanf(&CicloTxt[1],"%d",&CicloPack)!=1)
                    FinFich=1;
                if (SucRead==4)
                {
                  if (sscanf(LongId1,"%d",&milongit)!=1)
                    if (sscanf(&LongId1[1],"%d",&IdPack)!=1)
                      FinFich=1;
                }
                else if (SucRead==5)
                {
                  if (sscanf(&LongId2[1],"%d",&IdPack)!=1)
                    FinFich=1;
                }
              }
            }
            else
            {
              FinFich=1;
            }
            if (!FinFich)
            {
              if (CicloFich>=0) Tope++;
              if (CicloPack>=0)
              {
                Tope++;
                // Creates dependent packet
                LaRedLocal->InsertTracePack(CicloPack,orig,dest,milongit,IdPack);
              }
            }
    }
    rewind(fich);
  }
  TasaEmit=TasaEmit*LaRedLocal->NumNodes/paqlong; // solo consideramos datos
  packpunt=Tope/STATPOINTS;
  CicloFich=-1;
  // FER no estaban estos 3 y los añado
  CicloPack=-1;
  IdPack=-1;
  myCycles=0;
  FinFich=0;
  contsimu=true;
  do
  {
    // Packet creation for this cycle
    if (fich==NULL)   // Random generation following a Poisson Process. No trace file
    {
        Tasaux=TasaEmit;
        if ((LaRedLocal->NumPackCreated[0]-LaRedLocal->NumPackEmit[0])<500*LaRedLocal->NumNodes*LaRedLocal->NumVirtProcInj)
          while (Tasaux>0)
          {
            if (rand()<(RAND_MAX*Tasaux))
            {
              orig=rand()%LaRedLocal->NumNodes;
              do dest=rand()%LaRedLocal->NumNodes; while (dest==orig);
              new Packet(LaRedLocal,paqlong+cablong,orig,dest,-1);
            }
            Tasaux--;
          }
    }
    else  // Packets from a trace file
    {
      if (FinFich==0)
      {
        do
        {
          if (CicloFich==-1)
          {
            IdPack=-1;
            if (fgets(linea,150,fich)!=NULL)
            {
              if (linea[0]!='#')  // comentario
              {
                milongit=paqlong+cablong;
                //FinFich=sscanf(linea,"%d %d %d %d",&CicloFich,&orig,&dest,&milongit);
                SucRead=sscanf(linea,"%s %d %d %s %s",CicloTxt,&orig,&dest,LongId1,LongId2);
                if (noleelong) milongit=paqlong+cablong;
                if (SucRead<3) FinFich=1;
                else FinFich=0;
                if (sscanf(CicloTxt,"%d",&CicloFich)!=1)
                  if (sscanf(&CicloTxt[1],"%d",&CicloPack)!=1)
                    FinFich=1;
                if (SucRead==4)
                {
                  if (sscanf(LongId1,"%d",&milongit)!=1)
                    if (sscanf(&LongId1[1],"%d",&IdPack)!=1)
                      FinFich=1;
                }
                else if (SucRead==5)
                {
                  if (sscanf(&LongId2[1],"%d",&IdPack)!=1)
                    FinFich=1;
                }
              }
            }
            else
            {
              FinFich=1;
            }
          }
          if (FinFich==1) break;
          // creates packet
          //if (CicloFich==LaRedLocal->Cycles[0])
          if (CicloFich==myCycles)
          {
            new Packet(LaRedLocal,milongit,orig,dest,IdPack);
            CicloFich=-1;
          }
          else if (CicloPack>=0)
          {
            CicloPack=-1; // continues
          }
        //} while (CicloFich<=LaRedLocal->Cycles[0]);
        } while (CicloFich<=myCycles);
        if (FinFich==1)
        {
              LaRedLocal->CyclesCreation[0]=LaRedLocal->Cycles[0];
        }
      }
    }
    LaRedLocal->RunCycle(); // moves packets
	// FER no estaba, lo añado
    myCycles++;   // Local Cycles account for the dummy packets case 
    // controlo el flujo segun continuar
    switch (*continuar)
    {
      case 0: // no muestra y sigue
         break; // nada
      case 1: // Muestra pero se para
         //Dibuja(LaRedLocal);
         //while ((*continuar)==1) Application->ProcessMessages(); // espera que alguien lo saque
         //if (*continuar<3) *continuar=1; //vuelta otra vez
         break;
      case 2: // No se utiliza, es para salir del bucle anterior.
         //Dibuja(this);
         //*continuar=1;
         //ShowMessage("Continuar nunca vale 2...");
         break;
      case 3: // se termina directamente
         //Dibuja(LaRedLocal);
         if (LaRedLocal->Cycles[0]>1) LaRedLocal->Cycles[0]=LaRedLocal->Cycles[0]-2; // Ajusta ciclos
         return;
         //break;
      default: return;
    }
    // end simulation condition
    if ((LaRedLocal->NumPackTrans[0]>=Tope) && (SIMSTOPEMIT==0)) contsimu=false;
    if ((LaRedLocal->NumPackEmit[0]>=Tope)  && (SIMSTOPEMIT==1)) contsimu=false;
    // Update counters
    if (LaRedLocal->NumPackEmit[0]<LaRedLocal->NumPackCreated[0])
      LaRedLocal->CyclesEmit[0]=0;
    else if (LaRedLocal->CyclesEmit[0]==0)
      LaRedLocal->CyclesEmit[0]=LaRedLocal->Cycles[0];
    // saves results to a file
    if ((FINALSTATS==0)
        && (LaRedLocal->NumPackDummy==0)
        && (LaRedLocal->NumPackTrans[1]>=packpunt) )
    {
       PrintStatsToFile(LaRedLocal,f,valorval,1);
       LaRedLocal->ResetStatSet(1);
    }
    if ( (LaRedLocal->BloqueoTotal) && (LaRedLocal->Packets!=NULL) )
    {
      Muestra("DEADLOCK FOUND!!");
      *continuar=3;
      break;
    }
  } while (contsimu);
  if (LaRedLocal->Cycles[0]>1) LaRedLocal->Cycles[0]=LaRedLocal->Cycles[0]-2; // Ajusta ciclos
  if (LaRedLocal->CyclesEmit[0]==0) LaRedLocal->CyclesEmit[0]=LaRedLocal->Cycles[0];
  if (LaRedLocal->CyclesCreation[0]==0) LaRedLocal->CyclesCreation[0]=LaRedLocal->Cycles[0];
  //Dibuja(LaRedLocal);
}


void * RunThread(void *tharg)
{
struct thread_arg *mydata;
mydata=(struct thread_arg *) tharg;
 Simula(mydata->LaRedLocal,mydata->fich,mydata->f,mydata->paqlong,mydata->cablong,mydata->Tope,mydata->TasaEmit,mydata->continuar,mydata->noleelong,mydata->valorval);
 pthread_exit(NULL);
 return(NULL); // to avoid warning
}


void Simular()
{
int rc;
//int ndir,adelanto;
int numpaq;
int paqlong;
int puntos,puntos2,plleva,plleva2;
int pllevapost,ThrID,ThrIDpost;
int SimuVar,SimuVar2;
int dimensiones,nodosdim,lonbuffer,nvirtuales;
int noleelong;
int valoraux;
//double tasaemit;
double tasaemitini,tasaemit[MAX_TH_NUM];
double LaVar,LaVarIni,LaVarFin;
double LaVar2,LaVarIni2,LaVarFin2;
class Red *redaux;
string TrazaName,msgaux;
FILE *fich,*f;
//time_t t;
struct timeval ini, fin;
long tiempousado;

// externos
bool Interactiva;

 //srand((unsigned) time(&t));
// poner valores aqui
 Interactiva=JUSTONE;
 numpaq=PACKNUM;
 paqlong=PACKLEN;
 tasaemitini=PACKPROD;
 dimensiones=DIMENSIONS;
 nodosdim=NODOSDIM;
 lonbuffer=LONBUFFER;
 nvirtuales=NUMVIRT;     
//original
 f=stdout;
 if (SimuStop==false) ShowMsg("Esto no debería aparecer. (Simulacion dentro de otra.)");
 SimuStop=false;
 LaVarIni2=LAVARINI2;
 LaVarFin2=LAVARFIN2;
 LaVar2=LaVarIni2;
 if (Interactiva)   // interactiva
 {
   SimuVar2=0;
   puntos2=1;
 }
 else  // multiple
 {
   SimuVar2=SIMUVAR2;
   if (SimuVar2>0) puntos2=PUNTOS2;
   else puntos2=1;
 }
 TrazaName="";
 if (USETRACE==1)
 {
   if (TRACENAME!="")
   {
      if ((fich=fopen(TRACENAME.c_str(),"rt"))==NULL)
      {
        if (LANG==0)
          ShowMsg("ERROR: Fichero de trazas no encontrado, simulando sin trazas.");
        else
          ShowMsg("ERROR: Trace file not found, simulating witout trace file.");
      }
      else
      {
        fclose(fich);
        TrazaName=TRACENAME;
      }
   }
   else
     if (LANG==0)
       ShowMsg("ERROR: No se ha especificado un fichero de trazas.");
     else
       ShowMsg("ERROR: There is no trace file specification.");
 }
 fich=NULL;
 gettimeofday (&ini, NULL);

 if (Interactiva)   // interactiva
 {
   puntos=1;
 }
 else  // multiple
 {
   puntos=PUNTOS;
 }
 
 // The best number of threads is the maximum of puntos%NumThreads=0 which is less or equal than MAX_TH_NUM
 //NumThreads=0;
 //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (((puntos%NumThreads)!=0) || (NumThreads<NumProcs))) NumThreads++;
 //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (((NumThreads%NumProcs)!=0) || (NumThreads<NumProcs))) NumThreads++;
 //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (NumThreads<NumProcs)) NumThreads++;
 //if (puntos<=NumProcs) NumThreads=puntos+NumProcs;
 //else while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos)) NumThreads+=NumProcs;
 //NumThreads-=NumProcs;
 int idx=1;
 do
 {
	NumThreads=puntos/idx;
	idx++;
 } while (NumThreads>MAX_TH_NUM);
 //NumThreads=15;
 
 // outter loop
 plleva2=0;
 while ( (plleva2<puntos2) && (Continuar!=3) )
 {
   plleva2++;
   switch (SimuVar2)
   {
     case 1: dimensiones=(int)LaVar2;
             msgaux="Dimension";
             break;
     case 2: nodosdim=(int)LaVar2;
             msgaux="Nod/Dim";
             break;
     case 3: lonbuffer=(int)LaVar2;
             msgaux="Buff.Length";
             break;
     case 4: nvirtuales=(int)LaVar2;
             msgaux="Virtual";
             if (ALLVIRTEQ)
             {
                    NUMVIRTINJ=nvirtuales;
                    NUMVIRTEJE=nvirtuales;
             }
             break;
     default:           
             msgaux="Item";
             break;
   }
   SimuVar=SIMUVAR;
   LaVarIni=LAVARINI;
   LaVarFin=LAVARFIN;
   LaVar=LaVarIni;
   
   // inner loop
   plleva=0; 
   pllevapost=0;
   while ( (pllevapost<puntos) && (Continuar!=3) )
   //while ( (plleva<puntos) && (Continuar!=3) )
   {
     //plleva++;
     if (TrazaName!="") fich=fopen(TrazaName.c_str(),"rt");
     if (fich!=NULL) puntos=1; // only once
     noleelong=NOREADLEN;
	 if (f!=NULL)
     {
       if ( ((FINALSTATS) && (plleva==0)) || ((!FINALSTATS) && (NumThreads==1)) )
       {
           if (LANG==0)
             fprintf(f,"\"%s\";\"Ciclos\";\"Paquetes enviados\";\"Paquetes recibidos\";\"Tasa envio (flits/nodo/ciclo)\";\"Tasa recepcion (flits/nodo/ciclo)\";\"Latencia Cabecera\";\"Latencia Cabecera (sin bloqueos)\";\"Latencia Paquete\";\"Latencia Paquete (sin bloqueos)\";\"Ciclos de Bloqueo\";\"Camino Medio (nodos)\"\n",msgaux.c_str());
           else if (LANG==1)
             fprintf(f,"\"%s\";\"Cycles\";\"Delivered packets\";\"Received packets\";\"Delivery rate (flits/node/cycle)\";\"Arrival rate (flits/node/cycle)\";\"Head Latency\";\"Head Latency (no locks)\";\"Packet Latency\";\"Packet Latency (no locks)\";\"Lock cycles\";\"Average Path (nodes)\"\n",msgaux.c_str());
       }
     }
     if (plleva>=NumThreads)
     {
       //wait for thread end
       ThrIDpost=pllevapost%NumThreads;
       rc=pthread_join(Hilos[ThrIDpost],NULL);
       pllevapost++;
       valoraux=pllevapost; //-NumTo+i+1;
       if (!Interactiva)
         if (SIMUVAR2>0)
           valoraux=(int)LaVar2;  // round
       Muestra("----------------------");
       Muestra("Item: "+FloatToStr(pllevapost)+", Variable: "+FloatToStr(tasaemit[ThrIDpost])+", Threads: "+IntToStr(NumThreads)+", Processors: "+IntToStr(NumProcs));
       ShowStats(LaRedGlobal[ThrIDpost],0);
       if (FINALSTATS)
         PrintStatsToFile(LaRedGlobal[ThrIDpost],f, valoraux,0);
       Muestra("");
     }
     if (plleva<puntos)
     {
       ThrID=plleva%NumThreads;
       plleva++;
       valoraux=plleva;
       if (!Interactiva)
         if (SIMUVAR2>0)
           valoraux=(int)LaVar2;
       if (SimuVar==0) tasaemit[ThrID]=LaVar;
       else tasaemit[ThrID]=tasaemitini;
       redaux=LaRedGlobal[ThrID];
       LaRedGlobal[ThrID]=NULL;  //previene accesos indeseados mientras se destruye y se vuelve a crear
       if (redaux!=NULL) delete redaux;
       redaux=new Red(      
                dimensiones,
                nodosdim,    
                nvirtuales,
                NUMVIRTINJ,
                NUMVIRTEJE,
                lonbuffer,   
                NUMDIR,      
                FORWARDING,
                PHYSICAL, 
                ROUTING,
                SWITCH,   
                DELFIFO,     
                DELCROSS,    
                DELCHANNEL,   
                DELSWITCH,
                PACKNUMDUMMY   
             );
       LaRedGlobal[ThrID]=redaux; // ahora ya esta creada
       // Thread data preparation
       thread_arg_array[ThrID].LaRedLocal=LaRedGlobal[ThrID];
       thread_arg_array[ThrID].fich=fich;
       thread_arg_array[ThrID].f=f;
       thread_arg_array[ThrID].paqlong=paqlong;
       thread_arg_array[ThrID].cablong=PACKHEADLEN;
       thread_arg_array[ThrID].Tope=numpaq+PACKNUMDUMMY;
       thread_arg_array[ThrID].TasaEmit=tasaemit[ThrID];
       thread_arg_array[ThrID].continuar=&Continuar;
       thread_arg_array[ThrID].noleelong=noleelong;
       thread_arg_array[ThrID].valorval=valoraux;
       rc=pthread_create(&Hilos[ThrID], NULL, &RunThread, (void *)&thread_arg_array[ThrID]); // error handling...

       //Simula(fich,f,paqlong,PACKHEADLEN,numpaq+PACKNUMDUMMY,tasaemit,&Continuar,noleelong,valoraux);

       if (puntos>1)
       {
         if (ESCALA==0) LaVar=LaVar+(LaVarFin-LaVarIni)/(puntos-1);
         else LaVar=LaVar*pow(LaVarFin/LaVarIni,1.0/(puntos-1));
       }
	 }
     if (fich!=NULL) fclose(fich);
   }
   if (puntos2>1)
   {
     if (ESCALA2==0) LaVar2=LaVar2+(LaVarFin2-LaVarIni2)/(puntos2-1);
     else LaVar2=LaVar2*pow(LaVarFin2/LaVarIni2,1.0/(puntos2-1));
   }
 }
 if ( Continuar!=3 )
 {
   gettimeofday (&fin, NULL);
   tiempousado=(fin.tv_sec*1000+fin.tv_usec/1000)-(ini.tv_sec*1000+ini.tv_usec/1000);
   if (LANG==0)
     ShowMsg("SIMULACIÓN COMPLETADA CON ÉXITO ("+IntToStr(tiempousado)+" ms).");
   else if (LANG==1)
     ShowMsg("SIMULATION SUCCESFULLY FINISHED ("+IntToStr(tiempousado)+" ms).");
 }
 else
 {
   if (LANG==0)
     ShowMsg("SIMULACIÓN INTERRUMPIDA. Ver mensajes anteriores y la red.");
   else if (LANG==1)
     ShowMsg("SIMULATION INTERRUPTED. See last messages and the network.");
 }
 if (f!=NULL) fclose(f);
 Continuar=0;
 SimuStop=true;
}

int main()
{
char linea[200];
char palabra[200];
char pal2[200];
int numlin;
float valor; // double does not work with scan
FILE *fich;
 NumProcs=-1;
#ifdef _SC_NPROCESSORS_ONLN
 NumProcs= sysconf(_SC_NPROCESSORS_ONLN); // unix
#else
 NumProcs=pthread_num_processors_np();  // only windows
#endif
 if (NumProcs<1) NumProcs=2; // default number of processors
 if (NumProcs>MAX_TH_NUM) NumProcs=MAX_TH_NUM;
 fich=stdin;
 numlin=0;
 // defaut values
 LANG=dLANG;
 if (getenv("LANG")!=NULL)
   if (strncmp(getenv("LANG"),"es_ES",5)==0)
     LANG=0;
 DIMENSIONS=dDIMENSIONS;
 NODOSDIM=dNODOSDIM;
 NUMVIRT=dNUMVIRT;
 NUMVIRTINJ=dNUMVIRTINJ;
 NUMVIRTEJE=dNUMVIRTEJE;
 ALLVIRTEQ=dALLVIRTEQ;
 LONBUFFER=dLONBUFFER;
 NUMDIR=dNUMDIR;
 FORWARDING=dFORWARDING;
 PHYSICAL=dPHYSICAL;
 SWITCH=dSWITCH;
 ROUTING=dROUTING;
 DELFIFO=dDELFIFO;
 DELCROSS=dDELCROSS;
 DELCHANNEL=dDELCHANNEL;
 DELSWITCH=dDELSWITCH;
 PACKNUM=dPACKNUM;
 PACKLEN=dPACKLEN;
 PACKHEADLEN=dPACKHEADLEN;
 PACKPROD=dPACKPROD;
 PACKNUMDUMMY=dPACKNUMDUMMY;
 USETRACE=dUSETRACE;
 TRACENAME=dTRACENAME;
 NOREADLEN=dNOREADLEN;
 JUSTONE=dJUSTONE;
 SIMSTOPEMIT=dSIMSTOPEMIT;
 SIMUVAR=dSIMUVAR;
 LAVARINI=dLAVARINI;
 LAVARFIN=dLAVARFIN;
 PUNTOS=dPUNTOS;
 ESCALA=dESCALA;
 SIMUVAR2=dSIMUVAR2;
 LAVARINI2=dLAVARINI2;
 LAVARFIN2=dLAVARFIN2;
 PUNTOS2=dPUNTOS2;
 ESCALA2=dESCALA2;
 FINALSTATS=dFINALSTATS;
 STATPOINTS=dSTATPOINTS;
 while (fgets(linea,190,fich)!=NULL)
 {
   numlin++;
   if (linea[0]!='#')  // comentario
   {
     if (sscanf(linea,"%s %s",palabra,pal2)!=2)
     {
       if (strlen(linea)>1) ShowMsg("Ignoring line "+IntToStr(numlin)+": "+string(linea));
       continue;
     }
     if (sscanf(linea,"%s %e",palabra,&valor)!=2)
     {
       if ((strcmp(palabra,"PHYSICAL")!=0) && 
           (strcmp(palabra,"TRACENAME")!=0) && 
           (strcmp(palabra,"ALLVIRTEQ")!=0) && 
           (strcmp(palabra,"JUSTONE")!=0) ) 
       {
         ShowMsg("Ignored line "+IntToStr(numlin)+": "+string(linea));
         continue;
       }
     }
     if (strcmp(palabra,"LANG")==0) LANG=(int)valor;
     if (strcmp(palabra,"DIMENSIONS")==0) DIMENSIONS=(int)valor;
     if (strcmp(palabra,"NODOSDIM")==0) NODOSDIM=(int)valor;
     if (strcmp(palabra,"NUMVIRT")==0) NUMVIRT=(int)valor;
     if (strcmp(palabra,"NUMVIRTINJ")==0) NUMVIRTINJ=(int)valor;
     if (strcmp(palabra,"NUMVIRTEJE")==0) NUMVIRTEJE=(int)valor;
     if (strcmp(palabra,"LONBUFFER")==0) LONBUFFER=(int)valor;
     if (strcmp(palabra,"NUMDIR")==0) NUMDIR=(int)valor;
     if (strcmp(palabra,"FORWARDING")==0) FORWARDING=(int)valor;
     if (strcmp(palabra,"PHYSICAL")==0)
     {
       if (strcmp(pal2,"true")==0) PHYSICAL=true;
       else PHYSICAL=false;
     }
     if (strcmp(palabra,"ALLVIRTEQ")==0)
     {
       if (strcmp(pal2,"true")==0) ALLVIRTEQ=true;
       else ALLVIRTEQ=false;
     }      
     if (strcmp(palabra,"SWITCH")==0) SWITCH=(int)valor;
     if (strcmp(palabra,"ROUTING")==0) ROUTING=(int)valor;
     if (strcmp(palabra,"DELFIFO")==0) DELFIFO=(int)valor;
     if (strcmp(palabra,"DELCROSS")==0) DELCROSS=(int)valor;
     if (strcmp(palabra,"DELCHANNEL")==0) DELCHANNEL=(int)valor;
     if (strcmp(palabra,"DELSWITCH")==0) DELSWITCH=(int)valor;
     if (strcmp(palabra,"PACKNUM")==0) PACKNUM=(int)valor;
     if (strcmp(palabra,"PACKLEN")==0) PACKLEN=(int)valor;
     if (strcmp(palabra,"PACKHEADLEN")==0) PACKHEADLEN=(int)valor;
     if (strcmp(palabra,"PACKPROD")==0) PACKPROD=valor;
     if (strcmp(palabra,"PACKNUMDUMMY")==0) PACKNUMDUMMY=(int)valor;
     if (strcmp(palabra,"USETRACE")==0) USETRACE=(int)valor;
     if (strcmp(palabra,"TRACENAME")==0) TRACENAME=string(pal2);
     if (strcmp(palabra,"NOREADLEN")==0) NOREADLEN=(int)valor;
     if (strcmp(palabra,"JUSTONE")==0)
     {
       if (strcmp(pal2,"true")==0) JUSTONE=true;
       else JUSTONE=false;
     }
     if (strcmp(palabra,"SIMSTOPEMIT")==0) SIMSTOPEMIT=(int)valor;
     if (strcmp(palabra,"SIMUVAR")==0) SIMUVAR=(int)valor;
     if (strcmp(palabra,"LAVARINI")==0) LAVARINI=valor;
     if (strcmp(palabra,"LAVARFIN")==0) LAVARFIN=valor;
     if (strcmp(palabra,"PUNTOS")==0) PUNTOS=(int)valor;
     if (strcmp(palabra,"ESCALA")==0) ESCALA=(int)valor;
     if (strcmp(palabra,"SIMUVAR2")==0) SIMUVAR2=(int)valor;
     if (strcmp(palabra,"LAVARINI2")==0) LAVARINI2=valor;
     if (strcmp(palabra,"LAVARFIN2")==0) LAVARFIN2=valor;
     if (strcmp(palabra,"PUNTOS2")==0) PUNTOS2=(int)valor;
     if (strcmp(palabra,"ESCALA2")==0) ESCALA2=(int)valor;
     if (strcmp(palabra,"FINALSTATS")==0) FINALSTATS=(int)valor;
     if (strcmp(palabra,"STATPOINTS")==0) STATPOINTS=(int)valor;
   } 
 }
/* fprintf(stderr,"LANG %d\n",LANG);
 fprintf(stderr,"DIMENSIONS %d\n",DIMENSIONS);
 fprintf(stderr,"NODOSDIM %d\n",NODOSDIM);
 fprintf(stderr,"NUMVIRT %d\n",NUMVIRT);
 fprintf(stderr,"LONBUFFER %d\n",LONBUFFER);
 fprintf(stderr,"NUMDIR %d\n",NUMDIR);
 fprintf(stderr,"FORWARDING %d\n",FORWARDING);
 fprintf(stderr,"PHYSICAL %d\n",PHYSICAL);
 fprintf(stderr,"SWITCH %d\n",SWITCH);
 fprintf(stderr,"ROUTING %d\n",ROUTING);
 fprintf(stderr,"DELFIFO %d\n",DELFIFO);
 fprintf(stderr,"USETRACE %d\n",USETRACE);
*/
 //LaRedGlobal=NULL;
 // delete all?
 Continuar=0;
 SimuStop=true;
 Simular();
}
