//---------------------------------------------------------------------------
#ifdef WINDOWS
 #include <vcl.h>
#endif
#ifdef __Linux__
 #include <clx.h>
#endif

#pragma hdrstop

//---------------------------------------------------------------------------
#pragma package(smart_init)

#ifdef WINDOWS
#pragma resource "*.dfm"
#endif
#ifdef __Linux__
#pragma resource "*.xfm"
#endif

#include <values>
#include <math>
#include <time.h>

#include "red.h"

#include "simuredunit.h"
#include "dibujounit.h"
#include "graficounit.h"

//extern PACKAGE TSysLocale SysLocale;

//---------------------------------------------------------------------------
__fastcall MiThread::MiThread(struct thread_arg *tharg)
	: TThread(false)  //run inmediatly
{
 mydata=tharg;
 SAVETOFILE=FPrincipal->SAVETOFILE;
}

//---------------------------------------------------------------------------
void __fastcall MiThread::Execute()
{
	//---- Place thread code here ----
  FreeOnTerminate = false;
  FPrincipal->Simula(mydata->LaRedLocal,mydata->fich,mydata->f,mydata->paqlong,mydata->cablong,mydata->Tope,mydata->TasaEmit,mydata->continuar,mydata->noleelong,mydata->valorval);
}




TFPrincipal *FPrincipal;
//---------------------------------------------------------------------------
__fastcall TFPrincipal::TFPrincipal(TComponent* Owner)
        : TForm(Owner)
{
void __fastcall (__closure *FuncPunt)(TObject *);
#ifdef WINDOWS
 ForceCurrentDirectory=true; // solo va en VCL
#endif
#ifdef __Linux__
#define LANG_SPANISH 10
#endif

// solo windows
SYSTEM_INFO info;
GetSystemInfo(&info);

 //pthread_win32_process_attach_np(); // necessary if static pthread linking
 //pthread_win32_thread_attach_np(); // necessary if static pthread linking
 //NumProcs=pthread_num_processors_np();  // only windows
 NumProcs=info.dwNumberOfProcessors;  // solo windows
 if (NumProcs<1) NumProcs=2;
 DecimalSeparator='.';
 SimuStop=true;
 for (int i=0; i<MAX_TH_NUM;i++) LaRedGlobal[i]=NULL;
 Continuar=0;
 //ShowMessage(IntToStr(SysLocale.PriLangID));//+" "+LANG_SPANISH);
 FuncPunt=IdiomaRad->OnClick;
 IdiomaRad->OnClick=NULL;
 if (SysLocale.PriLangID==LANG_SPANISH)
   IdiomaRad->ItemIndex=0;
 else
   IdiomaRad->ItemIndex=1;
 IdiomaRad->OnClick=FuncPunt;
 Idioma(IdiomaRad->ItemIndex);
 // Como cada vez que cambio el xfm, cambian estos, los pongo explicitos
 ConmutTxt->ItemIndex=0;
 RoutingTxt->ItemIndex=0;
 EscalaRad->ItemIndex=0;
 VariableRad->ItemIndex=0;
 EscalaRad2->ItemIndex=1; // logaritmica
 Variable2Rad->ItemIndex=0;
 Hilos=new MiThread*[MAX_TH_NUM];
}
//---------------------------------------------------------------------------

 __fastcall TFPrincipal::~TFPrincipal()
{
 //ShowMessage("Adios");
 //pthread_win32_thread_detach_np(); // necessary if static pthread linking
 //pthread_win32_process_detach_np(); // necessary if static pthread linking
}

void TFPrincipal::Muestra(AnsiString a)
{
  TxtSal->Lines->Add(a);
}

void TFPrincipal::Idioma(int idioma)
{
int RoutingIdx,ConmuIdx;
 RoutingIdx=RoutingTxt->ItemIndex;
 ConmuIdx=ConmutTxt->ItemIndex;
 switch (idioma)
 {
  case 0:  // spanish
     RedTab->Caption="Red";
      GroupBoxRed->Caption="Topología";
       Label1->Caption="Dimensiones";
       Label2->Caption="Nodos/Dim";
       Label4->Caption="Lon. Colas";
       Label3->Caption="Virtuales";
       BidirCheck->Caption="Bidireccional";
       AdelantoCheck->Caption="Adelanto en Colas";
       FisicoCheck->Caption="Canales Físicos";
       LabelInj->Caption="Colas de Inyección";
       LabelEje->Caption="Colas de Eyección";
       InjectionCheck->Caption="Inyección=Eyección=Virtuales";
      GroupBoxRet->Caption="Retrasos";
       Label5->Caption="Buffer";
       Label6->Caption="CrossBar";
       Label7->Caption="Conmutación";
       Label8->Caption="Canal";
      GroupBoxConmut->Caption="Conmutación";
       ConmutTxt->Items->Strings[0]="Wormhole";
      GroupBoxEncam->Caption="Encaminamiento";
       RoutingTxt->Items->Strings[0]="Orden de Dimensión (XY) en mallas (determinista)";
       RoutingTxt->Items->Strings[1]="Orden de Dimensión (XY) en toros (tiene bloqueos mortales)";
       RoutingTxt->Items->Strings[2]="Duato basado en XY para mallas (adaptativo)";
       RoutingTxt->Items->Strings[3]="Completamente Adaptativo para mallas (tiene bloqueos)";
     PaqueteTab->Caption="Paquetes";
      GroupBoxPaq->Caption="Paquete";
       Label10->Caption="Flits de Datos";
       Label14->Caption="Flits de Cabecera";
      GroupBoxGenPaq->Caption="Generación de Paquetes";
       TrazasCheck->Caption="Obtener Paquetes de un Fichero de Trazas";
       Button3->Caption="Especificar Fichero de Trazas";
       LongitCheck->Caption="No leer las longitudes";
      GroupBoxGenAut->Caption="Generación Automática";
       NumFlitsRad->Caption="Flits/Nodo";
       NumPaqRad->Caption="Paquetes";
       Label12->Caption="Productividad (Flits/Ciclo/Nodo)";
      GroupBoxDummy->Caption="Paquetes de relleno";
       Label25->Caption="Calentamiento";
     SimuMultTab->Caption="Simulación";
      GroupBoxPrim->Caption="Primera Variable";
       EscalaRad->Caption="Escala";
       EscalaRad->Items->Strings[0]="Lineal";
       EscalaRad->Items->Strings[1]="Logarítmica";
       Label11->Caption="Inicial";
       Label18->Caption="Final";
       Label19->Caption="Puntos";
       VariableRad->Caption="Parámetro";
       VariableRad->Items->Strings[0]="Productividad (flits/ciclo/nodo)";
      GroupBoxSec->Caption="Segunda Variable";
       EscalaRad2->Caption=EscalaRad->Caption;
       EscalaRad2->Items->Strings[0]=EscalaRad->Items->Strings[0];
       EscalaRad2->Items->Strings[1]=EscalaRad->Items->Strings[1];
       Label9->Caption=Label11->Caption;
       Label15->Caption=Label18->Caption;
       Label16->Caption=Label19->Caption;
       Variable2Rad->Caption=VariableRad->Caption;
       Variable2Rad->Items->Strings[0]="Ninguno";
       Variable2Rad->Items->Strings[1]="Dimensiones";
       Variable2Rad->Items->Strings[2]="Nodos por Dimensión";
       Variable2Rad->Items->Strings[3]="Longitud Buffer";
       Variable2Rad->Items->Strings[4]="Canales Virtuales";
      RadStopSim->Caption="Detener Simulación";
       RadStopSim->Items->Strings[0]="cuando lleguen todos los paquetes";
       RadStopSim->Items->Strings[1]="cuando salgan todos los paquetes";
      Interact->Caption="Habilitar simulación simple/interactiva";
      ButtonEnaSimple->Caption="Simulación Simple/Interactiva";
      bGo->Caption="Simular Múltiple";
      bDetener->Caption="Interrumpir";
     GrafTab->Caption="Resultados";
      GroupBoxRes->Caption="Guardar Resultados";
       GuardarCheck->Caption="Guardar Resultados en un Fichero";
       AnyadeCheck->Caption="Añade los resultados al fichero";
       Button4->Caption="Especificar Fichero de Resultados";
       GroupBoxStats->Caption="Estadísticas";
        RadFinal->Caption="Final";
        RadContinuous->Caption="Continuas";
        Label26->Caption="Puntos";
      GroupBoxGraf->Caption="Gráfico";
       Button1->Caption="Cargar Fichero CSV";
       Button2->Caption="Recargar/Mostrar Gráfico";
       Label22->Caption="Eje X";
       Label24->Caption="Eje Y";
     MiscTab->Caption="Misc";
      IdiomaRad->Caption="Idioma";
      IdiomaRad->Items->Strings[0]="Español";
      IdiomaRad->Items->Strings[1]="Inglés";
     break;
  case 1:   // English
     RedTab->Caption="Network";
      GroupBoxRed->Caption="Topology";
       Label1->Caption="Dimensions";
       Label2->Caption="Nodes/Dim";
       Label4->Caption="Lon./Buffer";
       Label3->Caption="Virtuals";
       BidirCheck->Caption="Bidirectional";
       AdelantoCheck->Caption="Buffer Forward";
       FisicoCheck->Caption="Physical Channels";
       LabelInj->Caption="Injection Buffers";
       LabelEje->Caption="Ejection Buffers";
       InjectionCheck->Caption="Inyection=Ejection=Virtuals";
      GroupBoxRet->Caption="Delays";
       Label5->Caption="Buffer";
       Label6->Caption="CrossBar";
       Label7->Caption="Switching";
       Label8->Caption="Channel";
      GroupBoxConmut->Caption="Switching";
       ConmutTxt->Items->Strings[0]="Wormhole";
      GroupBoxEncam->Caption="Routing";
       RoutingTxt->Items->Strings[0]="Dimension Order (XY) for meshes (deterministic)";
       RoutingTxt->Items->Strings[1]="Dimension Order (XY) for tori (with deadlocks)";
       RoutingTxt->Items->Strings[2]="Duato based on XY for meshes (adaptive)";
       RoutingTxt->Items->Strings[3]="Fully Adaptive for meshes (with deadlocks)";
     PaqueteTab->Caption="Packets";
      GroupBoxPaq->Caption="Packet";
       Label10->Caption="Data Flits";
       Label14->Caption="Head Flits";
      GroupBoxGenPaq->Caption="Packet Creation";
       TrazasCheck->Caption="Take Packets from a Trace File";
       Button3->Caption="Specify Trace File";
       LongitCheck->Caption="Do not read packet lengths";
      GroupBoxGenAut->Caption="Automatic Creation";
       NumFlitsRad->Caption="Flits/Node";
       NumPaqRad->Caption="Packets";
       Label12->Caption="Productivity (Flits/Cycle/Node)";
      GroupBoxDummy->Caption="Dummy packets";
       Label25->Caption="Warm-up packets";
     SimuMultTab->Caption="Simulation";
      GroupBoxPrim->Caption="First Variable";
       EscalaRad->Caption="Scale";
       EscalaRad->Items->Strings[0]="Linear";
       EscalaRad->Items->Strings[1]="Logarithmic";
       Label11->Caption="Initial";
       Label18->Caption="Final";
       Label19->Caption="Points";
       VariableRad->Caption="Parameter";
       VariableRad->Items->Strings[0]="Productivity (Flits/Cycle/Node)";
      GroupBoxSec->Caption="Second Variable";
       EscalaRad2->Caption=EscalaRad->Caption;
       EscalaRad2->Items->Strings[0]=EscalaRad->Items->Strings[0];
       EscalaRad2->Items->Strings[1]=EscalaRad->Items->Strings[1];
       Label9->Caption=Label11->Caption;
       Label15->Caption=Label18->Caption;
       Label16->Caption=Label19->Caption;
       Variable2Rad->Caption=VariableRad->Caption;
       Variable2Rad->Items->Strings[0]="None";
       Variable2Rad->Items->Strings[1]="Dimensions";
       Variable2Rad->Items->Strings[2]="Nodes per Dimension";
       Variable2Rad->Items->Strings[3]="Buffer Length";
       Variable2Rad->Items->Strings[4]="Virtual Channels";
      RadStopSim->Caption="Stop Simulation";
       RadStopSim->Items->Strings[0]="when all pakets arrived";
       RadStopSim->Items->Strings[1]="when all packets emitted";
      Interact->Caption="Enable simple/interactive Simulation";
      ButtonEnaSimple->Caption="Simple/Interactive Simulation";
      bGo->Caption="Multiple Simulate";
      bDetener->Caption="Stop";
     GrafTab->Caption="Results";
      GroupBoxRes->Caption="Results storage";
       GuardarCheck->Caption="Save Results to a File";
       AnyadeCheck->Caption="Append results to the file";
       Button4->Caption="Specify a Result File";
       GroupBoxStats->Caption="Statistics";
        RadFinal->Caption="Final";
        RadContinuous->Caption="Continuous";
        Label26->Caption="Points";
      GroupBoxGraf->Caption="Charts";
       Button1->Caption="Load CSV File";
       Button2->Caption="Reload/Show Chart";
       Label22->Caption="X-axis";
       Label24->Caption="Y-axis";
     MiscTab->Caption="Misc";
      IdiomaRad->Caption="Language";
      IdiomaRad->Items->Strings[0]="Spanish";
      IdiomaRad->Items->Strings[1]="English";
     break;
  default:;
 }
 RoutingTxt->ItemIndex=RoutingIdx;
 ConmutTxt->ItemIndex=ConmuIdx;
}

// Funcion para mostrar la red, modificar segun plataforma
void TFPrincipal::Dibuja(Red *LaRed)
{
#ifdef WINDOWS
Graphics::TBitmap *ImgBuf;
Graphics::TBitmap *NodoBmp;
Graphics::TBitmap *NodoBaseBmp;
#endif
#ifdef __Linux__
Qgraphics::TBitmap *ImgBuf;
Qgraphics::TBitmap *NodoBmp;
Qgraphics::TBitmap *NodoBaseBmp;
#endif
TPoint puntos[3];
int FifoLonX,FifoLonY,LonX,LonY;
int i,j,k;
int di,dim,nv;
int ANCHOX,ANCHOY,LONCX,LONCY,AnchoNodo,AltoNodo;
int ANCHOYint,LonXint,Xint,Yint;
TColor ElColorIn,ElColorOut;
class Device *dispo;
 if (LaRed==NULL) return;
 LONCX=4;
 LONCY=4;
#ifdef WINDOWS
 ImgBuf=new Graphics::TBitmap();
 NodoBmp=new Graphics::TBitmap();
 NodoBaseBmp=new Graphics::TBitmap();
#endif
#ifdef __Linux__
 ImgBuf=new Qgraphics::TBitmap();
 NodoBmp=new Qgraphics::TBitmap();
 NodoBaseBmp=new Qgraphics::TBitmap();
#endif
 ImgBuf->Width=FDibujo->Dibujo->Width;
 ImgBuf->Height=FDibujo->Dibujo->Height; // Se le quita la barra de arriba
 AnchoNodo=ImgBuf->Width/LaRed->NumNodesDim;
 NodoBmp->Width=AnchoNodo;
 NodoBaseBmp->Width=AnchoNodo;
 AltoNodo=ImgBuf->Height/LaRed->NumNodesDim;
 NodoBmp->Height=AltoNodo;
 NodoBaseBmp->Height=AltoNodo;
 FifoLonX=AnchoNodo/3;    // longitudes de las colas + canal
 FifoLonY=AltoNodo/3;
 ANCHOX=(AnchoNodo/3-4)/(LaRed->Directions*LaRed->NumVirtuals);  // Ancho de los flits
 ANCHOY=(AltoNodo/3-4)/(LaRed->Directions*LaRed->NumVirtuals);  // Ancho de los flits
 LonX=(FifoLonX-LONCX/2)/LaRed->NumFlitBuf;  // Longitudes de los flits
 LonY=(FifoLonY-LONCY/2)/LaRed->NumFlitBuf;
 LONCX=(FifoLonX-LonX*LaRed->NumFlitBuf)*2;  // reajusta LONCX
 LONCY=(FifoLonY-LonY*LaRed->NumFlitBuf)*2;  // reajusta LONCY
 Xint=NodoBmp->Width/2.5;
 Yint=NodoBmp->Height/2.5;
 LonXint=(NodoBmp->Width-2*Xint)/LaRed->NumFlitBuf;
 ANCHOYint=NodoBmp->Height/12;  // a ojo
 ImgBuf->Canvas->Brush->Color=clWhite;
 ImgBuf->Canvas->FillRect(Rect(0,0,ImgBuf->Width,ImgBuf->Height));
 // Nodo basico
 NodoBaseBmp->Canvas->Pen->Color=clBlack;
 NodoBaseBmp->Canvas->Brush->Color=clWhite;
 NodoBaseBmp->Canvas->FillRect(Rect(0,0,NodoBmp->Width,NodoBmp->Height));
 NodoBaseBmp->Canvas->Pen->Color=clRed;
 NodoBaseBmp->Canvas->Rectangle(LONCX/2,LONCY/2,NodoBmp->Width-LONCX/2,NodoBmp->Height-LONCY/2);
 NodoBaseBmp->Canvas->Pen->Color=clBlack;
 NodoBaseBmp->Canvas->Rectangle(FifoLonX,FifoLonY,NodoBaseBmp->Width-FifoLonX,NodoBaseBmp->Height-FifoLonY);
 NodoBaseBmp->Canvas->Brush->Color=clWhite;
 for (j=0;j<LaRed->NumBuf;j++)
 {
     dim=j/(LaRed->Directions*LaRed->NumVirtuals);
     di=(j%(LaRed->Directions*LaRed->NumVirtuals))/LaRed->NumVirtuals;
     nv=j%(LaRed->NumVirtuals);
     // Primero los canales (no los pongo)
     // Ahora las flechas
     NodoBaseBmp->Canvas->Brush->Color=clBlack;
     if (dim==0) // eje x
     {
          if (di==0)
          {
             puntos[0] = Point(NodoBaseBmp->Width-FifoLonX-1,NodoBaseBmp->Height/2-(nv+0.8)*ANCHOY);
             puntos[1] = Point(NodoBaseBmp->Width-FifoLonX-1,NodoBaseBmp->Height/2-(nv+0.2)*ANCHOY);
             puntos[2] = Point(NodoBaseBmp->Width-FifoLonX-1-NodoBaseBmp->Width/24,NodoBaseBmp->Height/2-(nv+0.5)*ANCHOY);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
             puntos[0] = Point(FifoLonX+NodoBaseBmp->Width/24,NodoBaseBmp->Height/2-(nv+0.8)*ANCHOY);
             puntos[1] = Point(FifoLonX+NodoBaseBmp->Width/24,NodoBaseBmp->Height/2-(nv+0.2)*ANCHOY);
             puntos[2] = Point(FifoLonX,NodoBaseBmp->Height/2-(nv+0.5)*ANCHOY);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
          }
          else
          {
             puntos[0] = Point(NodoBaseBmp->Width-FifoLonX-1-NodoBaseBmp->Width/24,NodoBaseBmp->Height/2+(nv+0.8)*ANCHOY);
             puntos[1] = Point(NodoBaseBmp->Width-FifoLonX-1-NodoBaseBmp->Width/24,NodoBaseBmp->Height/2+(nv+0.2)*ANCHOY);
             puntos[2] = Point(NodoBaseBmp->Width-FifoLonX-1,NodoBaseBmp->Height/2+(nv+0.5)*ANCHOY);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
             puntos[0] = Point(FifoLonX,NodoBaseBmp->Height/2+(nv+0.8)*ANCHOY);
             puntos[1] = Point(FifoLonX,NodoBaseBmp->Height/2+(nv+0.2)*ANCHOY);
             puntos[2] = Point(FifoLonX+NodoBaseBmp->Width/24,NodoBaseBmp->Height/2+(nv+0.5)*ANCHOY);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
          }
     }
     else if (dim==1)
     {
          if (di==0)
          {
             puntos[0] = Point(NodoBaseBmp->Width/2-(nv+0.8)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1-NodoBaseBmp->Height/24);
             puntos[1] = Point(NodoBaseBmp->Width/2-(nv+0.2)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1-NodoBaseBmp->Height/24);
             puntos[2] = Point(NodoBaseBmp->Width/2-(nv+0.5)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
             puntos[0] = Point(NodoBaseBmp->Width/2-(nv+0.8)*ANCHOX,FifoLonY);
             puntos[1] = Point(NodoBaseBmp->Width/2-(nv+0.2)*ANCHOX,FifoLonY);
             puntos[2] = Point(NodoBaseBmp->Width/2-(nv+0.5)*ANCHOX,FifoLonY+NodoBaseBmp->Height/24);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
          }
          else
          {
             puntos[0] = Point(NodoBaseBmp->Width/2+(nv+0.8)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1);
             puntos[1] = Point(NodoBaseBmp->Width/2+(nv+0.2)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1);
             puntos[2] = Point(NodoBaseBmp->Width/2+(nv+0.5)*ANCHOX,NodoBaseBmp->Height-FifoLonY-1-NodoBaseBmp->Height/24);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
             puntos[0] = Point(NodoBaseBmp->Width/2+(nv+0.8)*ANCHOX,FifoLonY+NodoBaseBmp->Height/24);
             puntos[1] = Point(NodoBaseBmp->Width/2+(nv+0.2)*ANCHOX,FifoLonY+NodoBaseBmp->Height/24);
             puntos[2] = Point(NodoBaseBmp->Width/2+(nv+0.5)*ANCHOX,FifoLonY);
             NodoBaseBmp->Canvas->Polygon(puntos,2);
          }
     }
     // ahora los flits de cada cola
     NodoBaseBmp->Canvas->Pen->Color=clBlack;
     NodoBaseBmp->Canvas->Brush->Color=clWhite;
     for (k=0;k<LaRed->NumFlitBuf;k++)
     {
       if (dim==0) // eje x
       {
          if (di==0)
          {
             NodoBaseBmp->Canvas->Rectangle(-1+NodoBaseBmp->Width-FifoLonX+(LaRed->NumFlitBuf-k-1)*LonX,NodoBaseBmp->Height/2-(nv+1)*ANCHOY,NodoBaseBmp->Width-FifoLonX+(LaRed->NumFlitBuf-k)*LonX,NodoBaseBmp->Height/2-nv*ANCHOY);
             NodoBaseBmp->Canvas->Rectangle(FifoLonX-(k+1)*LonX,NodoBaseBmp->Height/2-(nv+1)*ANCHOY,1+FifoLonX-k*LonX,NodoBaseBmp->Height/2-(nv)*ANCHOY);
          }
          else
          {
             NodoBaseBmp->Canvas->Rectangle(-1+NodoBaseBmp->Width-FifoLonX+k*LonX,NodoBaseBmp->Height/2+(nv)*ANCHOY,NodoBaseBmp->Width-FifoLonX+(k+1)*LonX,NodoBaseBmp->Height/2+(nv+1)*ANCHOY);
             NodoBaseBmp->Canvas->Rectangle(FifoLonX-(LaRed->NumFlitBuf-k)*LonX,NodoBaseBmp->Height/2+(nv)*ANCHOY,1+FifoLonX-(LaRed->NumFlitBuf-k-1)*LonX,NodoBaseBmp->Height/2+(nv+1)*ANCHOY);
          }
       }
       else if (dim==1) // eje y
       {
          if (di==1)
          {
             NodoBaseBmp->Canvas->Rectangle(NodoBaseBmp->Width/2+(nv+1)*ANCHOX,-1+NodoBaseBmp->Height-FifoLonY+(LaRed->NumFlitBuf-k-1)*LonY,NodoBaseBmp->Width/2+(nv)*ANCHOX,NodoBaseBmp->Height-FifoLonY+(LaRed->NumFlitBuf-k)*LonY);
             NodoBaseBmp->Canvas->Rectangle(NodoBaseBmp->Width/2+(nv+1)*ANCHOX,FifoLonY-(k+1)*LonY,NodoBaseBmp->Width/2+(nv)*ANCHOX,1+FifoLonY-k*LonY);
          }
          else
          {
             NodoBaseBmp->Canvas->Rectangle(NodoBaseBmp->Width/2-(nv)*ANCHOX,FifoLonY-(LaRed->NumFlitBuf-k)*LonY,NodoBaseBmp->Width/2-(nv+1)*ANCHOX,1+FifoLonY-(LaRed->NumFlitBuf-k-1)*LonY);
             NodoBaseBmp->Canvas->Rectangle(NodoBaseBmp->Width/2-(nv)*ANCHOX,-1+NodoBaseBmp->Height-FifoLonY+k*LonY,NodoBaseBmp->Width/2-(nv+1)*ANCHOX,NodoBaseBmp->Height-FifoLonY+(k+1)*LonY);
          }
       }
     }
 }
 //Los internos
 for (k=0;k<LaRed->NumFlitBuf;k++)
 {
   for (i=0;i<LaRed->NumVirtProcInj;i++)
   {
       NodoBaseBmp->Canvas->Rectangle(Xint+k*LonXint,NodoBaseBmp->Height-Yint-i*ANCHOYint/LaRed->NumVirtProcInj,Xint+(k+1)*LonXint,NodoBaseBmp->Height-Yint-(i+1)*ANCHOYint/LaRed->NumVirtProcInj);
   }
   for (i=0;i<LaRed->NumVirtProcEje;i++)
   {
       NodoBaseBmp->Canvas->Rectangle(Xint+k*LonXint,Yint+i*ANCHOYint/LaRed->NumVirtProcEje,Xint+(k+1)*LonXint,Yint+(i+1)*ANCHOYint/LaRed->NumVirtProcEje);
   }
 }
 // ahora superponemos las diferencias...
 for (i=0;i<LaRed->NumNodes;i++)
 {
   NodoBmp->Canvas->Draw(0,0,NodoBaseBmp);
   for (j=0;j<LaRed->NumBuf;j++)
   {
     dim=j/(LaRed->Directions*LaRed->NumVirtuals);
     di=(j%(LaRed->Directions*LaRed->NumVirtuals))/LaRed->NumVirtuals;
     nv=j%(LaRed->NumVirtuals);
     // Primero los canales
     dispo=&(LaRed->Channels[LaRed->ChannelId(i,dim,di,nv)]);
     if (dispo->Paq!=NULL)
     {
         if ( (dispo->Busy) && (((Channel*)dispo)->Turn==dispo->MyBuf%LaRed->NumVirtuals) ) ElColorOut=(TColor)dispo->Paq->Color;
         else ElColorOut=clLtGray;
         NodoBmp->Canvas->Pen->Color=ElColorOut;
         NodoBmp->Canvas->Brush->Color=ElColorOut;
         if (dim==0)
         {
           if (di==0)
           {
             NodoBmp->Canvas->Rectangle(0, NodoBmp->Height/2-(nv+1)*ANCHOY,LONCX/2,NodoBmp->Height/2-(nv)*ANCHOY);
           }
           else
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width-LONCX/2,NodoBmp->Height/2+(nv)*ANCHOY,NodoBmp->Width,NodoBmp->Height/2+(nv+1)*ANCHOY);
           }
         }
         else if (dim==1)
         {
           if (di==1)
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width/2+(nv+1)*ANCHOX,0,NodoBmp->Width/2+(nv)*ANCHOX,LONCY/2);
           }
           else
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width/2-(nv)*ANCHOX,NodoBmp->Height,NodoBmp->Width/2-(nv+1)*ANCHOX,NodoBmp->Height-LONCY/2);
           }
         }
     }
     dispo=&(LaRed->Channels[LaRed->ChannelId(LaRed->Trans(i,dim,(di==0)?1:0),dim,di,nv)]);
     if (dispo->Paq!=NULL)
     {
         if ( (dispo->Busy) && (((Channel*)dispo)->Turn==dispo->MyBuf%LaRed->NumVirtuals) ) ElColorIn=(TColor)dispo->Paq->Color;
         else ElColorIn=clLtGray;
         NodoBmp->Canvas->Pen->Color=ElColorIn;
         NodoBmp->Canvas->Brush->Color=ElColorIn;
         if (dim==0)
         {
           if (di==0)
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width-LONCX/2,NodoBmp->Height/2-(nv+1)*ANCHOY,NodoBmp->Width,NodoBmp->Height/2-(nv)*ANCHOY);
           }
           else
           {
             NodoBmp->Canvas->Rectangle(0, NodoBmp->Height/2+(nv)*ANCHOY,LONCX/2,NodoBmp->Height/2+(nv+1)*ANCHOY);
           }
         }
         else if (dim==1)
         {
           if (di==1)
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width/2+(nv+1)*ANCHOX,NodoBmp->Height,NodoBmp->Width/2+(nv)*ANCHOX,NodoBmp->Height-LONCY/2);
           }
           else
           {
             NodoBmp->Canvas->Rectangle(NodoBmp->Width/2-(nv)*ANCHOX,0,NodoBmp->Width/2-(nv+1)*ANCHOX,LONCY/2);
           }
         }
     }
     // ahora los flits de cada buffer
     NodoBmp->Canvas->Font->Height=(ANCHOY+LonX)*0.3;
     for (k=0;k<LaRed->NumFlitBuf;k++)
     {
       NodoBmp->Canvas->Pen->Color=clBlack;
       dispo=&(LaRed->Nodes[i].Buf_in[j].Fifo[k]);
       if (dispo->Paq!=NULL)
       {
         if (dispo->Busy) ElColorIn=(TColor)dispo->Paq->Color;
         else ElColorIn=clLtGray;
         NodoBmp->Canvas->Brush->Color=ElColorIn;
         if (dim==0) // eje x
         {
            if (di==0)
            {
               NodoBmp->Canvas->Rectangle(-1+NodoBmp->Width-FifoLonX+(LaRed->NumFlitBuf-k-1)*LonX,NodoBmp->Height/2-(nv+1)*ANCHOY,NodoBmp->Width-FifoLonX+(LaRed->NumFlitBuf-k)*LonX,NodoBmp->Height/2-nv*ANCHOY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(NodoBmp->Width-FifoLonX+(LaRed->NumFlitBuf-k-1)*LonX,1+NodoBmp->Height/2-(nv+1)*ANCHOY,IntToStr(dispo->Value));
            }
            else
            {
               NodoBmp->Canvas->Rectangle(FifoLonX-(LaRed->NumFlitBuf-k)*LonX,NodoBmp->Height/2+(nv)*ANCHOY,1+FifoLonX-(LaRed->NumFlitBuf-k-1)*LonX,NodoBmp->Height/2+(nv+1)*ANCHOY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+FifoLonX-(LaRed->NumFlitBuf-k)*LonX,1+NodoBmp->Height/2+(nv)*ANCHOY,IntToStr(dispo->Value));
            }
         }
         else if (dim==1) // eje y
         {
            if (di==1)
            {
               NodoBmp->Canvas->Rectangle(NodoBmp->Width/2+(nv+1)*ANCHOX,-1+NodoBmp->Height-FifoLonY+(LaRed->NumFlitBuf-k-1)*LonY,NodoBmp->Width/2+(nv)*ANCHOX,NodoBmp->Height-FifoLonY+(LaRed->NumFlitBuf-k)*LonY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+NodoBmp->Width/2+(nv)*ANCHOX,NodoBmp->Height-FifoLonY+(LaRed->NumFlitBuf-k-1)*LonY,IntToStr(dispo->Value));
            }
            else
            {
               NodoBmp->Canvas->Rectangle(NodoBmp->Width/2-(nv)*ANCHOX,FifoLonY-(LaRed->NumFlitBuf-k)*LonY,NodoBmp->Width/2-(nv+1)*ANCHOX,1+FifoLonY-(LaRed->NumFlitBuf-k-1)*LonY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+NodoBmp->Width/2-(nv+1)*ANCHOX,1+FifoLonY-(LaRed->NumFlitBuf-k)*LonY,IntToStr(dispo->Value));
            }
         }
       }
       dispo=&(LaRed->Nodes[i].Buf_out[j].Fifo[k]);
       if (dispo->Paq!=NULL)
       {
         if (dispo->Busy) ElColorOut=(TColor)dispo->Paq->Color;
         else ElColorOut=clLtGray;
         NodoBmp->Canvas->Brush->Color=ElColorOut;
         if (dim==0) // eje x
         {
            if (di==0)
            {
               NodoBmp->Canvas->Rectangle(FifoLonX-(k+1)*LonX,NodoBmp->Height/2-(nv+1)*ANCHOY,1+FifoLonX-k*LonX,NodoBmp->Height/2-(nv)*ANCHOY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+FifoLonX-(k+1)*LonX,1+NodoBmp->Height/2-(nv+1)*ANCHOY,IntToStr(dispo->Value));
            }
            else
            {
               NodoBmp->Canvas->Rectangle(-1+NodoBmp->Width-FifoLonX+k*LonX,NodoBmp->Height/2+(nv)*ANCHOY,NodoBmp->Width-FifoLonX+(k+1)*LonX,NodoBmp->Height/2+(nv+1)*ANCHOY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(NodoBmp->Width-FifoLonX+k*LonX,1+NodoBmp->Height/2+(nv)*ANCHOY,IntToStr(dispo->Value));
            }
         }
         else if (dim==1) // eje y
         {
            if (di==1)
            {
               NodoBmp->Canvas->Rectangle(NodoBmp->Width/2+(nv+1)*ANCHOX,FifoLonY-(k+1)*LonY,NodoBmp->Width/2+(nv)*ANCHOX,1+FifoLonY-k*LonY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+NodoBmp->Width/2+(nv)*ANCHOX,1+FifoLonY-(k+1)*LonY,IntToStr(dispo->Value));
            }
            else
            {
               NodoBmp->Canvas->Rectangle(NodoBmp->Width/2-(nv)*ANCHOX,-1+NodoBmp->Height-FifoLonY+k*LonY,NodoBmp->Width/2-(nv+1)*ANCHOX,NodoBmp->Height-FifoLonY+(k+1)*LonY);
               if ((FDibujo->VerNum->Checked) && (dispo->Value>0)) NodoBmp->Canvas->TextOut(1+NodoBmp->Width/2-(nv+1)*ANCHOX,NodoBmp->Height-FifoLonY+k*LonY,IntToStr(dispo->Value));
            }
         }
       }
     }
   }
   //Los buffers internos
   for (k=0;k<LaRed->NumFlitBuf;k++)
   {
     for (j=0;j<LaRed->NumVirtProcInj;j++)
     {
       dispo=&(LaRed->Nodes[i].Buf_in[LaRed->NumBuf+j].Fifo[k]);
       if (dispo->Paq!=NULL)
       {
         if (dispo->Busy) ElColorIn=(TColor)dispo->Paq->Color;
         else ElColorIn=clLtGray;
         NodoBmp->Canvas->Brush->Color=ElColorIn;
         NodoBmp->Canvas->Rectangle(Xint+k*LonXint,NodoBmp->Height-Yint-j*ANCHOYint/LaRed->NumVirtProcInj,Xint+(k+1)*LonXint,NodoBmp->Height-Yint-(j+1)*ANCHOYint/LaRed->NumVirtProcInj);
       }
     }
     for (j=0;j<LaRed->NumVirtProcEje;j++)
     {
       dispo=&(LaRed->Nodes[i].Buf_out[LaRed->NumBuf+j].Fifo[k]);
       if (dispo->Paq!=NULL)
       {
         if (dispo->Busy) ElColorIn=(TColor)dispo->Paq->Color;
         else ElColorIn=clLtGray;
         NodoBmp->Canvas->Brush->Color=ElColorIn;
         NodoBmp->Canvas->Rectangle(Xint+k*LonXint,Yint+j*ANCHOYint/LaRed->NumVirtProcEje,Xint+(k+1)*LonXint,Yint+(j+1)*ANCHOYint/LaRed->NumVirtProcEje);
       }
     }
   }
   ImgBuf->Canvas->Draw((i%LaRed->NumNodesDim)*AnchoNodo,ImgBuf->Height-(i/LaRed->NumNodesDim+1)*AltoNodo,NodoBmp);
 }
 FDibujo->Dibujo->Canvas->Draw(0,0,ImgBuf);
 delete ImgBuf;
 delete NodoBaseBmp;
 delete NodoBmp;
}

// Funcion que simula un conjunto de paquetes con las condiciones que se le pasan
void __fastcall  TFPrincipal::Simula(class Red * LaRedLocal,FILE *fich,FILE *f,int paqlong,int cablong,int Tope,double TasaEmit,int *continuar,int noleelong,int valorval)
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
  //Dibuja(LaRedGlobal);
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
                if (noleelong) milongit=paqlong+cablong;
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
  packpunt=Tope/FPrincipal->STATPOINTS; // StrToInt((AnsiString)StatPointsTxt->Text);
  CicloFich=-1;
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
                SucRead=sscanf(linea,"%s %d %d %s %s",CicloTxt,&orig,&dest,LongId1,LongId2);
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
                if (noleelong) milongit=paqlong+cablong;
              }
            }
            else
            {
              FinFich=1;
            }
          }
          if (FinFich==1) break;
          // creates packet
          if (CicloFich==myCycles)
          {
            new Packet(LaRedLocal,milongit,orig,dest,IdPack);
            CicloFich=-1;
          }
          else if (CicloPack>=0)
          {
            CicloPack=-1; // continues
          }
        } while (CicloFich<=myCycles);
        if (FinFich==1)
        {
              LaRedLocal->CyclesCreation[0]=LaRedLocal->Cycles[0];
        }
      }
    }
    LaRedLocal->RunCycle(); // moves packets
    myCycles++;   // Local Cycles account for the dummy packets case 
    // controlo el flujo segun continuar
    switch (*continuar)
    {
      case 0: // no muestra y sigue
         Application->ProcessMessages();
         //
         break; // nada
      case 1: // Muestra pero se para
         Dibuja(LaRedLocal);
         while ((*continuar)==1) Application->ProcessMessages(); // espera que alguien lo saque
         if (*continuar<3) *continuar=1; //vuelta otra vez
         break;
      case 2: // No se utiliza, es para salir del bucle anterior.
         //Dibuja(this);
         //*continuar=1;
         //ShowMessage("Continuar nunca vale 2...");
         break;
      case 3: // se termina directamente
         //Dibuja(LaRedGlobal);       // it is better to do it outside
         if (LaRedLocal->Cycles[0]>1) LaRedLocal->Cycles[0]=LaRedLocal->Cycles[0]-2; // Ajusta ciclos
         return;
         //break;
      default: return;
    }
    // end simulation condition
    if ((LaRedLocal->NumPackTrans[0]>=Tope) && (FPrincipal->SIMSTOPEMIT==0)) contsimu=false;
    if ((LaRedLocal->NumPackEmit[0]>=Tope)  && (FPrincipal->SIMSTOPEMIT==1)) contsimu=false;
    // Update counters
    //if (LaRedGlobal->NumPackEmit[0]<LaRedGlobal->NumPackCreated[0])
    //  LaRedGlobal->CyclesEmit[0]=0;
    //else if (LaRedGlobal->CyclesEmit[0]==0)
    //  LaRedGlobal->CyclesEmit[0]=LaRedGlobal->Cycles[0];
    // saves results to a file
    if ( (FPrincipal->FINALSTATS==0) && (FPrincipal->SAVETOFILE==1)
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
  //Dibuja(LaRedGlobal);  // better outside
}


void TFPrincipal::ShowStats(class Red *LaRedLocal, int set)
{
double latmed;
int realTrans;
AnsiString Cyc,PackGen,PackSent,PackRec,ProdGen,ProdSent,ProdRec;
AnsiString LatHead,LatHeadNo,LatPack,LatPackNo,StdDev,CycBlock,MeanPathNod,MeanPathCha;
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
 if (IdiomaRad->ItemIndex==0)
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
 else if (IdiomaRad->ItemIndex==1) // ingles
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

void TFPrincipal::PrintStatsToFile(class Red *LaRedLocal, FILE *fich, int valor, int set)
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

/*
void * TFPrincipal::RunThread(void *tharg)
{
struct thread_arg *mydata;
mydata=(struct thread_arg *) tharg;

 FPrincipal->Simula(mydata->LaRedLocal,mydata->fich,mydata->f,mydata->paqlong,mydata->cablong,mydata->Tope,mydata->TasaEmit,mydata->continuar,mydata->noleelong,mydata->valorval);
 pthread_exit(NULL);
 return(NULL); // to avoid warning
}
*/

void TFPrincipal::Simular()
{
//int rc;
int ndir,adelanto,numpaq;
int paqlong;
int puntos,puntos2,plleva,plleva2;
int pllevapost,ThrID,ThrIDpost;
int SimuVar,SimuVar2;
int dimensiones,nodosdim,lonbuffer,nvirtuales,nvirteje,nvirtinj;
int noleelong;
int valoraux;
double tasaemitini,tasaemit[MAX_TH_NUM];
double LaVar,LaVarIni,LaVarFin;
double LaVar2,LaVarIni2,LaVarFin2;
class Red *redaux;
TDateTime tStart,tEnd,tElapsed;
unsigned int hour,min;
double sec;
double tDouble;
AnsiString TrazaName,msgaux;
FILE *fich,*f;

 f=NULL;
 if (SimuStop==false) ShowMessage("Esto no debería aparecer. (Simulacion dentro de otra.)");
 SimuStop=false;
 STATPOINTS=StrToInt((AnsiString)StatPointsTxt->Text);
 if (RadContinuous->Checked) FINALSTATS=0;
 else FINALSTATS=1;
 if (GuardarCheck->Checked) SAVETOFILE=1;
 else SAVETOFILE=0;
 SIMSTOPEMIT=RadStopSim->ItemIndex;
 tStart=tStart.CurrentDateTime();
 if (GuardarCheck->Checked)
 {
     if ((AnsiString)ResultFichTxt->Text!="")
     {
       if (AnyadeCheck->Checked)
         f=fopen(AnsiString(ResultFichTxt->Text).c_str(),"at");
       else
         f=fopen(AnsiString(ResultFichTxt->Text).c_str(),"wt");
     }
     else
      if (IdiomaRad->ItemIndex==0)
        ShowMessage("No se ha especificado un fichero de resultados.");
      else if (IdiomaRad->ItemIndex==1)
        ShowMessage("There is no result file specification.");
 }
 if (Interactiva)   // interactiva
 {
   SimuVar2=0;
   LaVarIni2=0;
   LaVarFin2=0;
   LaVar2=0;
   puntos2=1;
 }
 else  // multiple
 {
   SimuVar2=Variable2Rad->ItemIndex;
   LaVarIni2=StrToFloat(AnsiString(Ini2Txt->Text));
   LaVarFin2=StrToFloat(AnsiString(Fin2Txt->Text));
   LaVar2=LaVarIni2;
   if (Variable2Rad->ItemIndex!=0) puntos2=StrToFloat((AnsiString)Puntos2Txt->Text);
   else puntos2=1;
 }
 TrazaName="";
 fich=NULL;
 if (TrazasCheck->Checked)
 {
   if (AnsiString(TrazaFichTxt->Text)!="")
     TrazaName=AnsiString(TrazaFichTxt->Text);
   else
     if (IdiomaRad->ItemIndex==0)
       ShowMessage("No se ha especificado un fichero de trazas.");
     else
       ShowMessage("There is no trace file specification.");
 }
 if (BidirCheck->Checked) ndir=2;
 else ndir=1;
 if (AdelantoCheck->Checked) adelanto=1;
 else adelanto=0;
 if (NumPaqRad->Checked) numpaq=StrToInt((AnsiString)NumPaqTxt->Text);
 else numpaq=StrToInt((AnsiString)NumFlitTxt->Text)*StrToInt((AnsiString)DimTxt->Text)*StrToInt((AnsiString)NodosTxt->Text)/StrToInt((AnsiString)LonPaqTxt->Text);
 paqlong=StrToInt((AnsiString)LonPaqTxt->Text);
 tasaemitini=StrToFloat((AnsiString)EmisionTxt->Text);
 dimensiones=StrToInt((AnsiString)DimTxt->Text);
 nodosdim=StrToInt((AnsiString)NodosTxt->Text);
 lonbuffer=StrToInt((AnsiString)FifoTxt->Text);
 nvirtuales=StrToInt((AnsiString)VirtualesTxt->Text);
 if (InjectionCheck->Checked)
 {
        nvirtinj=nvirtuales;
        nvirteje=nvirtuales;
 }
 else
 {
        nvirtinj=StrToInt((AnsiString)VirtInjTxt->Text);
        nvirteje=StrToInt((AnsiString)VirtEjeTxt->Text);
 }
 plleva2=0;
 while ( (plleva2<puntos2) && (Continuar!=3) )
 {
   plleva2++;
   msgaux="Item";
   if (Interactiva)   // interactiva
   {
     SimuVar=0;
     LaVarIni=0;
     LaVarFin=0;
     LaVar=StrToFloat((AnsiString)EmisionTxt->Text);
     puntos=1;
     msgaux="Item";
   }
   else  // multiple
   {
     SimuVar=VariableRad->ItemIndex;
     LaVarIni=StrToFloat((AnsiString)IniTxt->Text);
     LaVarFin=StrToFloat((AnsiString)FinTxt->Text);
     LaVar=LaVarIni;
     puntos=StrToFloat((AnsiString)PuntosTxt->Text);
     if (Variable2Rad->ItemIndex>0)
       msgaux=Variable2Rad->Items->Strings[Variable2Rad->ItemIndex];
   }

   switch (SimuVar2)
   {
     case 1: dimensiones=LaVar2; break;
     case 2: nodosdim=LaVar2; break;
     case 3: lonbuffer=LaVar2; break;
     case 4: nvirtuales=LaVar2;
             if (InjectionCheck->Checked)
             {
                    nvirtinj=nvirtuales;
                    nvirteje=nvirtuales;
             }
             break;
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
   // inner loop
   plleva=0;
   pllevapost=0;
   while ( (pllevapost<puntos) && (Continuar!=3) )
   {
     //plleva++;
     if (TrazaName!="") fich=fopen(TrazaName.c_str(),"rt");
     if (fich!=NULL) puntos=1; // only once
     if (LongitCheck->Checked) noleelong=1;
     else noleelong=0;
     if (f!=NULL)
     {
       if ( ((RadFinal->Checked) && (plleva==0)) || ((RadContinuous->Checked) && (NumThreads==1)) )
       {
           if (IdiomaRad->ItemIndex==0)
             fprintf(f,"\"%s\";\"Ciclos\";\"Paquetes enviados\";\"Paquetes recibidos\";\"Tasa envio (flits/nodo/ciclo)\";\"Tasa recepcion (flits/nodo/ciclo)\";\"Latencia Cabecera\";\"Latencia Cabecera (sin bloqueos)\";\"Latencia Paquete\";\"Latencia Paquete (sin bloqueos)\";\"Ciclos de Bloqueo\";\"Camino Medio (nodos)\"\n",msgaux.c_str());
           else if (IdiomaRad->ItemIndex==1)
             fprintf(f,"\"%s\";\"Cycles\";\"Delivered packets\";\"Received packets\";\"Delivery rate (flits/node/cycle)\";\"Arrival rate (flits/node/cycle)\";\"Head Latency\";\"Head Latency (no locks)\";\"Packet Latency\";\"Packet Latency (no locks)\";\"Lock cycles\";\"Average Path (nodes)\"\n",msgaux.c_str());
       }
     }
     //NumTo=puntos-plleva;
     //if (NumTo>NumThreads) NumTo=NumThreads;
     //for (int i=0;i<NumTo;i++)
     //{
     if (plleva>=NumThreads)
     {
       //wait for thread end
       ThrIDpost=pllevapost%NumThreads;
       //rc=pthread_join(Hilos[ThrIDpost],NULL);
       if (puntos>1)
       {
         Hilos[ThrIDpost]->WaitFor();
         delete Hilos[ThrIDpost];
       }
       Dibuja(LaRedGlobal[ThrIDpost]);
       pllevapost++;
       valoraux=pllevapost; //-NumTo+i+1;
       if (!Interactiva)
         if (Variable2Rad->ItemIndex>0)
           valoraux=LaVar2;
       Muestra("----------------------");
       //if (Interactiva)   // interactiva
         //Muestra("Item: "+FloatToStr(plleva)+", Variable: "+FloatToStr(LaVar));
         //Muestra("Item: "+FloatToStr(plleva-NumTo+i+1)+", Variable: "+FloatToStr(tasaemit[i]));
       Muestra("Item: "+FloatToStr(pllevapost)+", Variable: "+FloatToStr(tasaemit[ThrIDpost])+", Threads: "+IntToStr(NumThreads)+", Processors: "+IntToStr(NumProcs));
       ShowStats(LaRedGlobal[ThrIDpost],0);
       if (RadFinal->Checked)
         PrintStatsToFile(LaRedGlobal[ThrIDpost],f, valoraux,0);
       Muestra("");
     }
     //}
     if (plleva<puntos)
     {
       ThrID=plleva%NumThreads;
       plleva++;
       valoraux=plleva;
       if (!Interactiva)
         if (Variable2Rad->ItemIndex>0)
           valoraux=LaVar2;
       if (SimuVar==0) tasaemit[ThrID]=LaVar;
       else tasaemit[ThrID]=tasaemitini;
       redaux=LaRedGlobal[ThrID];
       LaRedGlobal[ThrID]=NULL;  //previene accesos indeseados mientras se destruye y se vuelve a crear
       if (redaux!=NULL) delete redaux;
       redaux=new Red(
             dimensiones,
             nodosdim,
             nvirtuales,
             nvirtinj, //StrToInt((AnsiString)VirtInjTxt->Text),
             nvirteje, //StrToInt((AnsiString)VirtEjeTxt->Text),
             lonbuffer,
             ndir,
             adelanto,
             FisicoCheck->Checked,
             RoutingTxt->ItemIndex,
             ConmutTxt->ItemIndex,
             StrToInt((AnsiString)LatFifoTxt->Text),
             StrToInt((AnsiString)LatCrossTxt->Text),
             StrToInt((AnsiString)LatCanalTxt->Text),
             StrToInt((AnsiString)LatConmutTxt->Text),
             StrToInt((AnsiString)DummyUpPackTxt->Text));
       LaRedGlobal[ThrID]=redaux; // ahora ya esta creada
       if (puntos>1)
       {
         // Thread data preparation
         thread_arg_array[ThrID].LaRedLocal=LaRedGlobal[ThrID];
         thread_arg_array[ThrID].fich=fich;
         thread_arg_array[ThrID].f=f;
         thread_arg_array[ThrID].paqlong=paqlong;
         thread_arg_array[ThrID].cablong=StrToInt((AnsiString)LonCabTxt->Text);
         thread_arg_array[ThrID].Tope=numpaq+LaRedGlobal[ThrID]->NumPackDummy;
         thread_arg_array[ThrID].TasaEmit=tasaemit[ThrID];
         thread_arg_array[ThrID].continuar=&Continuar;
         thread_arg_array[ThrID].noleelong=noleelong;
         thread_arg_array[ThrID].valorval=valoraux;
         //rc=pthread_create(&Hilos[ThrID], NULL, &TFPrincipal::RunThread, (void *)&thread_arg_array[ThrID]); // error handling...
         Hilos[ThrID]=new MiThread(&thread_arg_array[ThrID]);
        }
        else Simula(LaRedGlobal[ThrID],fich,f,paqlong,StrToInt((AnsiString)LonCabTxt->Text),numpaq+LaRedGlobal[ThrID]->NumPackDummy,tasaemit[ThrID],&Continuar,noleelong,valoraux);
       if (puntos>1)
       {
         if (EscalaRad->ItemIndex==0) LaVar=LaVar+(LaVarFin-LaVarIni)/(puntos-1);
         else LaVar=LaVar*pow(LaVarFin/LaVarIni,1.0/(puntos-1));
       }
     }

     if (fich!=NULL) fclose(fich);
     Application->ProcessMessages();
   }
   // end inner loop

   if (puntos2>1)
   {
     if (EscalaRad2->ItemIndex==0) LaVar2=LaVar2+(LaVarFin2-LaVarIni2)/(puntos2-1);
     else LaVar2=LaVar2*pow(LaVarFin2/LaVarIni2,1.0/(puntos2-1));
   }
   Application->ProcessMessages();
 }
 if ( Continuar!=3 )
 {
   if (IdiomaRad->ItemIndex==0)
     Muestra("SIMULACIÓN COMPLETADA CON ÉXITO.");
   else if (IdiomaRad->ItemIndex==1)
     Muestra("SIMULATION SUCCESFULLY FINISHED.");
 }
 else
 {
   if (IdiomaRad->ItemIndex==0)
     Muestra("SIMULACIÓN INTERRUMPIDA. Ver mensajes anteriores y la red.");
   else if (IdiomaRad->ItemIndex==1)
     Muestra("SIMULATION INTERRUPTED. See last messages and the network.");
 }
 tEnd=tEnd.CurrentDateTime();
 tElapsed=tEnd-tStart;
 tDouble=tElapsed;
 hour=(int)(tDouble*24);
 min=(int)(tDouble*24*60)%60;
 //sec=(int)(tDouble*24*60*60)%60;
 sec=(int)(0.5+100*(tDouble*24*60*60-(hour*60*60+min*60)))/100.0;
 if (IdiomaRad->ItemIndex==0)
   Muestra("Tiempo de simulación: "+IntToStr(hour)+" h, "+IntToStr(min)+" m, "+FloatToStr(sec)+" s.");
 else if (IdiomaRad->ItemIndex==1)
   Muestra("Simulation time: "+IntToStr(hour)+" h, "+IntToStr(min)+" m, "+FloatToStr(sec)+" s.");
 Muestra(" ");
 if (f!=NULL) fclose(f);
 Continuar=0;
 SimuStop=true;
}

void __fastcall TFPrincipal::bGoClick(TObject *Sender)
{
 Interact->Enabled=false;
 InteractClick(Sender);
 FDibujo->Show();
 FGrafico->Hide();
 bGo->Enabled=false;
 bDetener->Enabled=true;
 Interactiva=false;
 Continuar=0;
 FDibujo->SimuTimer->Enabled=false;
 Simular();
 Interact->Enabled=true;
 InteractClick(Sender);
 FDibujo->Show();
 bGo->Enabled=true;
 bDetener->Enabled=false;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::DimTxtChange(TObject *Sender)
{
AnsiString a;
 a=((TComboBox*)Sender)->Text;
 if (a!="")
 {
   if (StrToIntDef(a,-1)<0)
     if (IdiomaRad->ItemIndex==0)
       ShowMessage("Debe introducir un entero positivo.");
     else
       ShowMessage("Positive integer value required.");
   else
     if (InjectionCheck->Checked)
     {
       VirtInjTxt->Text=VirtualesTxt->Text;
       VirtEjeTxt->Text=VirtualesTxt->Text;
     }
 }
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::EmisionTxtChange(TObject *Sender)
{
AnsiString a;
 a=((TComboBox*)Sender)->Text;
 if (a!="") if (StrToFloatDef(a,0)<=0)
   if (IdiomaRad->ItemIndex==0)
     ShowMessage("Debe introducir un número real positivo.");
   else
     ShowMessage("Positive real number required.");
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::NumFlitTxtChange(TObject *Sender)
{
 DimTxtChange(Sender);
 NumFlitsRad->Checked=true;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::NumPaqTxtChange(TObject *Sender)
{
 DimTxtChange(Sender);
 NumPaqRad->Checked=true;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::bDetenerClick(TObject *Sender)
{
 Continuar=3;
 FDibujo->SimuTimer->Enabled=false;
}
//---------------------------------------------------------------------------


void __fastcall TFPrincipal::TrazasCheckClick(TObject *Sender)
{
 NumFlitTxt->Enabled=!TrazasCheck->Checked;
 NumPaqTxt->Enabled=!TrazasCheck->Checked;
 EmisionTxt->Enabled=!TrazasCheck->Checked;
 NumFlitsRad->Enabled=!TrazasCheck->Checked;
 NumPaqRad->Enabled=!TrazasCheck->Checked;
 LongitCheck->Enabled=TrazasCheck->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::Button3Click(TObject *Sender)
{
int i;
  if (OpenTrazaDiag->Execute())
  {
    if ((i=TrazaFichTxt->Items->IndexOf((AnsiString)OpenTrazaDiag->FileName))>0)
      TrazaFichTxt->Items->Delete(i);
    TrazaFichTxt->Items->Insert(0,(AnsiString)OpenTrazaDiag->FileName);
    TrazaFichTxt->ItemIndex=0;
    TrazaFichTxt->Hint=(AnsiString)OpenFichDialog->FileName;
    TrazasCheck->Checked=true;
  }
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::Button4Click(TObject *Sender)
{
int i;
  if (OpenFichDialog->Execute())
  {
    if ((i=ResultFichTxt->Items->IndexOf((AnsiString)OpenFichDialog->FileName))>0)
      ResultFichTxt->Items->Delete(i);
    ResultFichTxt->Items->Insert(0,(AnsiString)OpenFichDialog->FileName);
    ResultFichTxt->ItemIndex=0;
    ResultFichTxt->Hint=(AnsiString)OpenFichDialog->FileName;
    GuardarCheck->Checked=true;
    // pone tambien el fichero a cargar
    FGrafico->Hide();
    if ((i=GrafTxt->Items->IndexOf((AnsiString)OpenFichDialog->FileName))>0)
      GrafTxt->Items->Delete(i);
    GrafTxt->Items->Insert(0,(AnsiString)OpenFichDialog->FileName);
    GrafTxt->ItemIndex=0;
    GrafTxt->Hint=(AnsiString)OpenFichDialog->FileName;
  }
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::InteractClick(TObject *Sender)
{
  FDibujo->bPaso->Enabled=Interact->Checked && FDibujo->MostrarCheck->Checked;
  FDibujo->bGoOtro->Enabled=Interact->Checked;
  //FDibujo->bPausa->Enabled=Interact->Checked && FDibujo->MostrarCheck->Checked;
  FDibujo->MostrarCheck->Enabled=Interact->Checked;
  FDibujo->VerNum->Enabled=Interact->Checked && FDibujo->MostrarCheck->Checked;
  FDibujo->RetrasoTxt->Enabled=Interact->Checked && FDibujo->MostrarCheck->Checked;
  FDibujo->WindowState=wsNormal;
  if (Interact->Checked) FDibujo->Show();
  else FDibujo->Hide();
  bGo->Enabled=!Interact->Checked;
  bDetener->Enabled=false;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::FormClose(TObject *Sender,
      TCloseAction &Action)
{
 FDibujo->SimuTimer->Enabled=false;
 if (SimuStop==false) Continuar=3;
}
//---------------------------------------------------------------------------


void __fastcall TFPrincipal::IdiomaRadClick(TObject *Sender)
{
  Idioma(IdiomaRad->ItemIndex);
  FDibujo->Idioma(IdiomaRad->ItemIndex);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::Button1Click(TObject *Sender)
{
int i;
  if (OpenGrafDialog->Execute())
  {
    if ((i=GrafTxt->Items->IndexOf((AnsiString)OpenGrafDialog->FileName))>0)
      GrafTxt->Items->Delete(i);
    GrafTxt->Items->Insert(0,(AnsiString)OpenGrafDialog->FileName);
    GrafTxt->ItemIndex=0;
    GrafTxt->Hint=(AnsiString)OpenGrafDialog->FileName;
    if (Redibuja(Sender)) FGrafico->Show();
  }
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::Button2Click(TObject *Sender)
{
 if (Redibuja(Sender)) FGrafico->Show();
}

bool __fastcall TFPrincipal::Redibuja(TObject *Sender)
{
TStringList *ElFichList;
TStringList **Lineas;
int i,NumLin;
int Xpos,Ypos,puntos,graficas,Graficas;
int AnchoG,AltoG;
int Ancho,Alto;
int expo;
int xmsg;
double NumDivX,NumDivY;
double r,g,b,f1;
double xminG,yminG;
double manti,aux;
double EscX,EscY,divX,divY;
double xmin,ymin,xmax,ymax,x,y;
int SEP;
TColor ElColor;
 SEP=30;
 if (FileExists((AnsiString)GrafTxt->Text))
 {
   ElFichList=new TStringList;
   ElFichList->LoadFromFile((AnsiString)GrafTxt->Text);
   for (i=0;i<ElFichList->Count;i++)
     if (ElFichList->Strings[i]=="")
     {
        ElFichList->Delete(i);  // borra blancos
        i--;  // repite el mismo
     }
   NumLin=ElFichList->Count;
   Lineas=new TStringList*[NumLin];
   for (i=0;i<NumLin;i++)
   {
     Lineas[i]=new TStringList;
     Lineas[i]->Delimiter=';';
     //Lineas[i]->QuoteChar=';';
     AnsiString cad=ElFichList->Strings[i];
     int p;
     while ((p=cad.Pos(","))>0) cad[p]='.';
     //Lineas[i]->DelimitedText=ElFichList->Strings[i];
     Lineas[i]->DelimitedText=cad;
     if (Lineas[i]->Count<8)  // Probablemente no es valida esa linea
     {
       delete Lineas[i];
       ElFichList->Delete(i);
       i--;
       NumLin--;
     }
   }
   // Selección de ejes
   Xpos=Xtxt->ItemIndex;
   Ypos=Ytxt->ItemIndex;
   Xtxt->Items->Assign(Lineas[0]);
   Ytxt->Items->Assign(Lineas[0]);
   Xtxt->ItemIndex=Xpos;
   Ytxt->ItemIndex=Ypos;
   if (RadFinal->Checked)
   {
     if (Xtxt->ItemIndex<0) Xtxt->ItemIndex=5;
     if (Ytxt->ItemIndex<0) Ytxt->ItemIndex=8;
   }
   else
   {
     if (Xtxt->ItemIndex<0) Xtxt->ItemIndex=1;
     if (Ytxt->ItemIndex<0) Ytxt->ItemIndex=8;
   }
   Xpos=Xtxt->ItemIndex;
   Ypos=Ytxt->ItemIndex;
   // el maximo y minimo x e y, puntos, graficas
   graficas=puntos=0;
   xmin=ymin=MAXDOUBLE;
   xmax=ymax=-1;
   for (i=0;i<NumLin;i++)
   {
     x=StrToFloatDef(Lineas[i]->Strings[Xpos],-1);
     y=StrToFloatDef(Lineas[i]->Strings[Ypos],-1);
     if ( (x==-1) || (y==-1) )
     {
       graficas++;
     }
     else
     {
       if (x>xmax) xmax=x;
       if (x<xmin) xmin=x;
       if (y>ymax) ymax=y;
       if (y<ymin) ymin=y;
       puntos++;
     }
   }
   Graficas=graficas;
   // Aqui empieza el dibujado
   FGrafico->Grafico->Canvas->Pen->Color=clBlack;
   FGrafico->Grafico->Canvas->Font->Color=clBlack;
   FGrafico->Grafico->Canvas->Brush->Color=clWhite;
   FGrafico->Grafico->Canvas->FillRect(FGrafico->Grafico->Canvas->ClipRect);
   if (graficas>0)
   {
     // It handle badly lines with different number of points...
     // but it draws something alnmost good anyway... 
     //if ((puntos%graficas)!=0) ShowMessage("Wrong CSV file format.");
   }
   else ShowMessage("Wrong CSV file format.");
   puntos=puntos/graficas;
   Ancho=FGrafico->Grafico->Width;
   Alto=FGrafico->Grafico->Height;
   // Dibuja eje X
   AnchoG=Ancho-1-2*SEP; // Ancho del grafico
   FGrafico->Grafico->Canvas->TextOut(2*SEP+(AnchoG-FGrafico->Grafico->Canvas->TextWidth(Lineas[0]->Strings[Xpos]))/2,Alto-SEP/2,Lineas[0]->Strings[Xpos]);
   FGrafico->Grafico->Canvas->MoveTo(2*SEP,Alto-SEP);
   FGrafico->Grafico->Canvas->LineTo(Ancho-1,Alto-SEP);
   aux=(xmax-xmin)/10;
   expo=floor(log10(aux)+1);
   manti=aux/pow(10,expo);
   if (manti<0.15) divX=0.1;
   else if (manti<0.3) divX=0.2;
   else if (manti<0.7) divX=0.5;
   else divX=1.0;
   divX=divX*pow(10,expo);
   NumDivX=(xmax-xmin)/divX+1.5;
   EscX=AnchoG/(divX*NumDivX);
   xminG=floor(xmin/divX)*divX;
   for (i=0;i<NumDivX;i++)
   {
     FGrafico->Grafico->Canvas->MoveTo(2*SEP+i*EscX*divX,Alto-SEP);
     FGrafico->Grafico->Canvas->LineTo(2*SEP+i*EscX*divX,Alto-SEP+3);
     FGrafico->Grafico->Canvas->TextOut(2*SEP+i*EscX*divX,Alto-SEP+3,FloatToStr(i*divX+xminG));
   }
   // Dibuja eje Y
   AltoG=Alto-SEP;   // Alto del grafico
   FGrafico->Grafico->Canvas->TextOut(2*SEP+2,0,Lineas[0]->Strings[Ypos]);
   FGrafico->Grafico->Canvas->MoveTo(2*SEP,Alto-SEP);
   FGrafico->Grafico->Canvas->LineTo(2*SEP,0);
   aux=(ymax-ymin)/10;
   expo=floor(log10(aux)+1);
   manti=aux/pow(10,expo);
   if (manti<0.15) divY=0.1;
   else if (manti<0.3) divY=0.2;
   else if (manti<0.7) divY=0.5;
   else divY=1.0;
   divY=divY*pow(10,expo);
   NumDivY=(ymax-ymin)/divY+1.5;
   EscY=AltoG/(divY*NumDivY);
   yminG=floor(ymin/divY)*divY;
   for (i=0;i<NumDivY;i++)
   {
     FGrafico->Grafico->Canvas->MoveTo(2*SEP,Alto-SEP-i*EscY*divY);
     FGrafico->Grafico->Canvas->LineTo(2*SEP-3,Alto-SEP-i*EscY*divY);
     FGrafico->Grafico->Canvas->TextOut(2*SEP-FGrafico->Grafico->Canvas->TextWidth(FloatToStr(i*divY+yminG))-3,Alto-SEP-i*EscY*divY,FloatToStr(i*divY+yminG));
   }
   // las graficas una por una
   graficas=0;
   xmsg=1+graficas*15;
   for (i=0;i<NumLin;i++)
   {
     x=StrToFloatDef(Lineas[i]->Strings[Xpos],-1);
     y=StrToFloatDef(Lineas[i]->Strings[Ypos],-1);
     if ( (x==-1) || (y==-1) )  // primera linea
     {
        r=graficas%2+(graficas/8)%2;
        g=(graficas/2)%2+(graficas/16)%2;
        b=(graficas/4)%2+(graficas/32)%2;
        f1=1+(int)(r+g+b)/2+(int)(r+g+b)/3;
        ElColor=TColor((b*0xFF0000+g*0xFF00+r*0xFF)/f1);
        if (Graficas>1)
        {
          if (graficas==0)
          {
            FGrafico->Grafico->Canvas->TextOut(xmsg,Alto-SEP/2,Lineas[i]->Strings[0]+":");
            xmsg=xmsg+3+FGrafico->Grafico->Canvas->TextWidth(Lineas[i]->Strings[0]);
          }
          FGrafico->Grafico->Canvas->Font->Color=ElColor;
          FGrafico->Grafico->Canvas->TextOut(xmsg,Alto-SEP/2,Lineas[i+1]->Strings[0]);
          xmsg=xmsg+3+FGrafico->Grafico->Canvas->TextWidth(Lineas[i+1]->Strings[0]);
        }
        FGrafico->Grafico->Canvas->Pen->Color=ElColor;
        graficas++;
     }
     else
     {
       // dibuja lineas
       if ( (i%(puntos+1))!=1 ) // no es el primer punto
         FGrafico->Grafico->Canvas->LineTo(2*SEP+(x-xminG)*EscX,Alto-SEP-(y-yminG)*EscY);
       // dibuja puntos
       FGrafico->Grafico->Canvas->MoveTo(2*SEP+(x-xminG)*EscX-3,Alto-SEP-(y-yminG)*EscY);
       FGrafico->Grafico->Canvas->LineTo(2*SEP+(x-xminG)*EscX+3,Alto-SEP-(y-yminG)*EscY);
       FGrafico->Grafico->Canvas->MoveTo(2*SEP+(x-xminG)*EscX,Alto-SEP-(y-yminG)*EscY+3);
       FGrafico->Grafico->Canvas->LineTo(2*SEP+(x-xminG)*EscX,Alto-SEP-(y-yminG)*EscY-3);
       FGrafico->Grafico->Canvas->MoveTo(2*SEP+(x-xminG)*EscX,Alto-SEP-(y-yminG)*EscY);
     }
   }
   delete ElFichList;
   delete [] Lineas;
 }
 else
 {
      if (IdiomaRad->ItemIndex==0)
        ShowMessage("No se ha especificado el fichero CSV.");
      else
        ShowMessage("CSV File missing.");
      return false;
 }
 return(true);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::GrafTxtChange(TObject *Sender)
{
 Button2Click(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::XtxtChange(TObject *Sender)
{
 Button2Click(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::YtxtChange(TObject *Sender)
{
 Button2Click(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::GuardarCheckClick(TObject *Sender)
{
  AnyadeCheck->Enabled=GuardarCheck->Checked;
  RadFinal->Enabled=GuardarCheck->Checked;
  RadContinuous->Enabled=GuardarCheck->Checked;
  StatPointsTxt->Enabled=GuardarCheck->Checked && RadContinuous->Checked;
}
//---------------------------------------------------------------------------


void __fastcall TFPrincipal::DummyUpPackTxtChange(TObject *Sender)
{
 DimTxtChange(Sender);
}
//---------------------------------------------------------------------------


void __fastcall TFPrincipal::ButtonEnaSimpleClick(TObject *Sender)
{
  Interact->Checked=!Interact->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::StatPointsTxtChange(TObject *Sender)
{
 DimTxtChange(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::RadContinuousClick(TObject *Sender)
{
 StatPointsTxt->Enabled=RadContinuous->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFPrincipal::InjectionCheckClick(TObject *Sender)
{
    VirtInjTxt->Enabled=!InjectionCheck->Checked;
    VirtEjeTxt->Enabled=!InjectionCheck->Checked;
}
//---------------------------------------------------------------------------


