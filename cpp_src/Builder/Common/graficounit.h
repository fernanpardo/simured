//---------------------------------------------------------------------------

#ifndef GraficounitH
#define GraficounitH
//---------------------------------------------------------------------------
#ifdef WINDOWS
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
#endif
#ifdef __Linux__
#include <Classes.hpp>
#include <QControls.hpp>
#include <QExtCtrls.hpp>
#include <QForms.hpp>
#endif
//---------------------------------------------------------------------------
class TFGrafico : public TForm
{
__published:	// IDE-managed Components
        TPaintBox *Grafico;
        void __fastcall GraficoPaint(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TFGrafico(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFGrafico *FGrafico;
//---------------------------------------------------------------------------
#endif
