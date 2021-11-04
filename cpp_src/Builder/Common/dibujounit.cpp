//---------------------------------------------------------------------------

#ifdef WINDOWS
 #include <vcl.h>
#endif
#ifdef __Linux__
 #include <clx.h>
#endif
#pragma hdrstop

#include "dibujounit.h"
#include "simuredunit.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#ifdef WINDOWS
#pragma resource "*.dfm"
#endif
#ifdef __Linux__
#pragma resource "*.xfm"
#endif

void Dibuja(class Red *red);

extern class Red *LaRedGlobal;


TFDibujo *FDibujo;
//---------------------------------------------------------------------------
__fastcall TFDibujo::TFDibujo(TComponent* Owner)
        : TForm(Owner)
{
 Idioma(FPrincipal->IdiomaRad->ItemIndex);
}

void TFDibujo::Idioma(int idioma)
{
 switch (idioma)
 {
  case 0:  // spanish
     bGoOtro->Caption="Simular";
     bPausa->Caption="Pausa";
     bPaso->Caption="Paso a Paso";
     bDetenerOtro->Caption="Interrumpir";
     MostrarCheck->Caption="Mostrar Evolución";
     VerNum->Caption="Mostrar Número de Flit";
     Label13->Caption="Retraso entre ciclos (ms)";
     break;
  case 1:   // English
     bGoOtro->Caption="Simulate";
     bPausa->Caption="Pause";
     bPaso->Caption="Step Simulate";
     bDetenerOtro->Caption="Stop";
     MostrarCheck->Caption="Show Evolution";
     VerNum->Caption="Show Flit Number";
     Label13->Caption="Cycle Delay (ms)";
     break;
  default:;
 }
}

//---------------------------------------------------------------------------
void __fastcall TFDibujo::DibujoPaint(TObject *Sender)
{
 if (FPrincipal->LaRedGlobal[0]!=NULL) FPrincipal->Dibuja(FPrincipal->LaRedGlobal[0]);
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::bPausaClick(TObject *Sender)
{
 SimuTimer->Interval=StrToInt((AnsiString)RetrasoTxt->Text);
 if (SimuTimer->Enabled)
 {
   if (FPrincipal->IdiomaRad->ItemIndex==0)
     ((TButton*)Sender)->Caption="Continuar";
   else if (FPrincipal->IdiomaRad->ItemIndex==1)
     ((TButton*)Sender)->Caption="Continue";
   SimuTimer->Enabled=false;
 }
 else
 {
   if (FPrincipal->IdiomaRad->ItemIndex==0)
     ((TButton*)Sender)->Caption="Pausa";
   else if (FPrincipal->IdiomaRad->ItemIndex==1)
     ((TButton*)Sender)->Caption="Pause";
   SimuTimer->Enabled=true;
 }
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::bPasoClick(TObject *Sender)
{
 SimuTimer->Interval=StrToInt((AnsiString)RetrasoTxt->Text);
 MostrarCheck->Enabled=false;
 FPrincipal->Interact->Enabled=false;
 bDetenerOtro->Enabled=true;
 bPausa->Enabled=true;
 if (FPrincipal->IdiomaRad->ItemIndex==0)
   bPausa->Caption="Continuar";
 else if (FPrincipal->IdiomaRad->ItemIndex==1)
   bPausa->Caption="Continue";
 bGoOtro->Enabled=false;
 SimuTimer->Enabled=false;
 if (FPrincipal->SimuStop==true)   // aqui solo debe entrar si no estaba simulando
 {
   FPrincipal->Continuar=1;
   FPrincipal->Interactiva=true;
   FPrincipal->Simular();
   MostrarCheck->Enabled=true;
   bDetenerOtro->Enabled=false;
   bPausa->Enabled=false;
   if (FPrincipal->IdiomaRad->ItemIndex==0)
     bPausa->Caption="Pausa";
   else if (FPrincipal->IdiomaRad->ItemIndex==1)
   bPausa->Caption="Pause";
   bGoOtro->Enabled=true;
   bPaso->Enabled=true;
   FPrincipal->Interact->Enabled=true;
 }
 else if (FPrincipal->Continuar==1)
 {
   FPrincipal->Continuar=2;
 }
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::bGoOtroClick(TObject *Sender)
{
 FPrincipal->Interactiva=true;
 FPrincipal->Interact->Enabled=false;
 MostrarCheck->Enabled=false;
 SimuTimer->Interval=StrToInt((AnsiString)RetrasoTxt->Text);
 bGoOtro->Enabled=false;
 bDetenerOtro->Enabled=true;
 if (MostrarCheck->Checked)
 {
     FPrincipal->Continuar=1;
     SimuTimer->Enabled=true;
     bPausa->Enabled=true;
     bPaso->Enabled=true;
 }
 else
 {
     FPrincipal->Continuar=0;
     SimuTimer->Enabled=false;
     bPausa->Enabled=false;
     bPaso->Enabled=false;
 }
 FPrincipal->Simular();
 bGoOtro->Enabled=true;
 bDetenerOtro->Enabled=false;
 bPausa->Enabled=false;
 bPaso->Enabled=MostrarCheck->Checked;
 MostrarCheck->Enabled=true;
 FPrincipal->Interact->Enabled=true;
 SimuTimer->Enabled=false;
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::bDetenerOtroClick(TObject *Sender)
{
 FPrincipal->Continuar=3;
 SimuTimer->Enabled=false;
 if (FPrincipal->IdiomaRad->ItemIndex==0)
   bPausa->Caption="Pausa";
 else if (FPrincipal->IdiomaRad->ItemIndex==1)
   bPausa->Caption="Pause";
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::MostrarCheckClick(TObject *Sender)
{
   RetrasoTxt->Enabled=MostrarCheck->Checked;
   bPaso->Enabled=MostrarCheck->Checked;
   VerNum->Enabled=MostrarCheck->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::SimuTimerTimer(TObject *Sender)
{
 if ((FPrincipal->Continuar>0) && (FPrincipal->Continuar<3)) FPrincipal->Continuar=2;
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::FormClose(TObject *Sender, TCloseAction &Action)
{
 if (FPrincipal->SimuStop==false) FPrincipal->Continuar=3;
 SimuTimer->Enabled=false;
 FPrincipal->Interact->Enabled=true;
 FPrincipal->Interact->Checked=false;
 FPrincipal->InteractClick(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFDibujo::RetrasoTxtChange(TObject *Sender)
{
  SimuTimer->Interval=StrToIntDef((AnsiString)RetrasoTxt->Text,10);
}
//---------------------------------------------------------------------------

