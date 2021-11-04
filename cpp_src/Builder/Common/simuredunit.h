//---------------------------------------------------------------------------
#ifndef simuredunitH
#define simuredunitH
//---------------------------------------------------------------------------

#ifdef WINDOWS
 #include <Classes.hpp>
 #include <ComCtrls.hpp>
 #include <Controls.hpp>
 #include <Dialogs.hpp>
 #include <ExtCtrls.hpp>
 #include <StdCtrls.hpp>
 #include <Types.hpp>
 #include <Graphics.hpp>
#endif
#ifdef __Linux__
 #include <Classes.hpp>
 #include <QComCtrls.hpp>
 #include <QControls.hpp>
 #include <QDialogs.hpp>
 #include <QExtCtrls.hpp>
 #include <QStdCtrls.hpp>
 #include <QTypes.hpp>
 #include <QGraphics.hpp>
#endif

#include <stdio.h>
#include <stdlib.h>

// uncomment to statically link pthreads (does not work anyway)
//#define PTW32_STATIC_LIB    1
//#include <pthread.h>

#define MAX_TH_NUM 50

//---------------------------------------------------------------------------
class MiThread : public TThread
{
private:
protected:
  void __fastcall Execute();
public:
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
	__fastcall MiThread(struct thread_arg *tharg);
        struct thread_arg * mydata;
        int SAVETOFILE;
};
//---------------------------------------------------------------------------

//---------------------------------------------------------------------------
class TFPrincipal : public TForm
{
__published:	// IDE-managed Components
        TMemo *TxtSal;
        TOpenDialog *OpenFichDialog;
        TOpenDialog *OpenTrazaDiag;
        TPageControl *MultiPages;
        TTabSheet *RedTab;
        TTabSheet *PaqueteTab;
        TGroupBox *GroupBoxRed;
        TLabel *Label1;
        TLabel *Label3;
        TLabel *Label4;
        TComboBox *DimTxt;
        TComboBox *NodosTxt;
        TComboBox *VirtualesTxt;
        TComboBox *FifoTxt;
        TCheckBox *BidirCheck;
        TCheckBox *AdelantoCheck;
        TGroupBox *GroupBoxPaq;
        TLabel *Label10;
        TLabel *Label14;
        TComboBox *LonPaqTxt;
        TComboBox *LonCabTxt;
        TGroupBox *GroupBoxGenPaq;
        TCheckBox *TrazasCheck;
        TTabSheet *SimuMultTab;
        TButton *bGo;
        TButton *bDetener;
        TGroupBox *GroupBoxRet;
        TComboBox *LatCrossTxt;
        TComboBox *LatConmutTxt;
        TComboBox *LatCanalTxt;
        TLabel *Label5;
        TLabel *Label2;
        TLabel *Label6;
        TLabel *Label7;
        TLabel *Label8;
        TComboBox *LatFifoTxt;
        TGroupBox *GroupBoxConmut;
        TComboBox *ConmutTxt;
        TGroupBox *GroupBoxEncam;
        TComboBox *RoutingTxt;
        TGroupBox *GroupBoxGenAut;
        TComboBox *NumFlitTxt;
        TComboBox *NumPaqTxt;
        TRadioButton *NumFlitsRad;
        TRadioButton *NumPaqRad;
        TLabel *Label12;
        TComboBox *EmisionTxt;
        TButton *Button3;
        TComboBox *TrazaFichTxt;
        TGroupBox *GroupBoxPrim;
        TRadioGroup *EscalaRad;
        TLabel *Label11;
        TLabel *Label18;
        TLabel *Label19;
        TComboBox *IniTxt;
        TComboBox *FinTxt;
        TComboBox *PuntosTxt;
        TGroupBox *GroupBoxSec;
        TLabel *Label9;
        TRadioGroup *EscalaRad2;
        TLabel *Label15;
        TLabel *Label16;
        TComboBox *Ini2Txt;
        TComboBox *Fin2Txt;
        TComboBox *Puntos2Txt;
        TRadioGroup *VariableRad;
        TRadioGroup *Variable2Rad;
        TTabSheet *MiscTab;
        TCheckBox *Interact;
        TGroupBox *GroupBox9;
        TLabel *Label17;
        TLabel *Label13;
        TLabel *Label20;
        TLabel *Label21;
        TLabel *Label23;
        TRadioGroup *IdiomaRad;
        TCheckBox *LongitCheck;
        TCheckBox *FisicoCheck;
        TTabSheet *GrafTab;
        TOpenDialog *OpenGrafDialog;
        TGroupBox *GroupBoxDummy;
        TComboBox *DummyUpPackTxt;
        TLabel *Label25;
        TGroupBox *GroupBoxGraf;
        TComboBox *Ytxt;
        TLabel *Label24;
        TComboBox *Xtxt;
        TLabel *Label22;
        TButton *Button1;
        TButton *Button2;
        TComboBox *GrafTxt;
        TGroupBox *GroupBoxRes;
        TCheckBox *GuardarCheck;
        TCheckBox *AnyadeCheck;
        TButton *Button4;
        TComboBox *ResultFichTxt;
        TButton *ButtonEnaSimple;
        TGroupBox *GroupBoxStats;
        TRadioButton *RadFinal;
        TRadioButton *RadContinuous;
        TComboBox *StatPointsTxt;
        TLabel *Label26;
        TRadioGroup *RadStopSim;
        TComboBox *VirtInjTxt;
        TComboBox *VirtEjeTxt;
        TLabel *LabelInj;
        TLabel *LabelEje;
        TCheckBox *InjectionCheck;
        void __fastcall bGoClick(TObject *Sender);
        void __fastcall DimTxtChange(TObject *Sender);
        void __fastcall EmisionTxtChange(TObject *Sender);
        void __fastcall NumFlitTxtChange(TObject *Sender);
        void __fastcall NumPaqTxtChange(TObject *Sender);
        void __fastcall bDetenerClick(TObject *Sender);
        void __fastcall TrazasCheckClick(TObject *Sender);
        void __fastcall Button3Click(TObject *Sender);
        void __fastcall Button4Click(TObject *Sender);
        void __fastcall InteractClick(TObject *Sender);
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall IdiomaRadClick(TObject *Sender);
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall Button2Click(TObject *Sender);
        void __fastcall GrafTxtChange(TObject *Sender);
        void __fastcall XtxtChange(TObject *Sender);
        void __fastcall YtxtChange(TObject *Sender);
        void __fastcall GuardarCheckClick(TObject *Sender);
        void __fastcall DummyUpPackTxtChange(TObject *Sender);
        void __fastcall ButtonEnaSimpleClick(TObject *Sender);
        void __fastcall StatPointsTxtChange(TObject *Sender);
        void __fastcall RadContinuousClick(TObject *Sender);
        void __fastcall InjectionCheckClick(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TFPrincipal(TComponent* Owner);
        __fastcall ~TFPrincipal(void);
        class Red *LaRedGlobal[MAX_TH_NUM]; // variable global para guardar la red.
        int NumThreads;
        int NumProcs;
        bool SimuStop;
        int Continuar;
        bool Interactiva;
        int STATPOINTS;
        int FINALSTATS;
        int SAVETOFILE;
        int SIMSTOPEMIT;
        void ShowStats(class Red *LaRedLocal, int set);
        void Muestra(AnsiString a);
        void __fastcall  Simula(class Red * LaRedLocal,FILE *fich, FILE *f,int paqlong,int cablong,int Tope,double TasaEmit,int *continuar,int noleelong,int valorval);
        void Simular();
        void PrintStatsToFile(class Red *LaRedLocal, FILE *fich, int valor, int set);
        void Idioma(int idioma);
        void Dibuja(class Red *LaRed);
        bool __fastcall Redibuja(TObject *Sender);

        // Threads
        //pthread_t Hilos[MAX_TH_NUM];
        //MiThread * Hilos[MAX_TH_NUM];
        MiThread ** Hilos;
        /*struct thread_arg
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
        */
        struct MiThread::thread_arg thread_arg_array[MAX_TH_NUM];

        //static void * RunThread(void *tharg);
};
//---------------------------------------------------------------------------
extern PACKAGE TFPrincipal *FPrincipal;
//---------------------------------------------------------------------------
#endif
