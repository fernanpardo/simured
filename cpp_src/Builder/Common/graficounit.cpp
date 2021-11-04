//---------------------------------------------------------------------------

#ifdef WINDOWS
 #include <vcl.h>
#endif
#ifdef __Linux__
 #include <clx.h>
#endif

#pragma hdrstop

#include "graficounit.h"
#include "simuredunit.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#ifdef WINDOWS
#pragma resource "*.dfm"
#endif
#ifdef __Linux__
#pragma resource "*.xfm"
#endif
TFGrafico *FGrafico;
//---------------------------------------------------------------------------
__fastcall TFGrafico::TFGrafico(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TFGrafico::GraficoPaint(TObject *Sender)
{
 FPrincipal->Redibuja(Sender);
}
//---------------------------------------------------------------------------

