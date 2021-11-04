//---------------------------------------------------------------------------

#ifndef dibujounitH
#define dibujounitH
//---------------------------------------------------------------------------
#ifdef WINDOWS
 #include <Classes.hpp>
 #include <Controls.hpp>
 #include <StdCtrls.hpp>
 #include <Forms.hpp>
 #include <ExtCtrls.hpp>
 #include <Graphics.hpp>
#endif
#ifdef __Linux__
 #include <Classes.hpp>
 #include <QControls.hpp>
 #include <QStdCtrls.hpp>
 #include <QForms.hpp>
 #include <QExtCtrls.hpp>
 #include <QGraphics.hpp>
#endif
//---------------------------------------------------------------------------
class TFDibujo : public TForm
{
__published:	// IDE-managed Components
        TPaintBox *Dibujo;
        TButton *bPaso;
        TButton *bPausa;
        TButton *bGoOtro;
        TButton *bDetenerOtro;
        TCheckBox *VerNum;
        TLabel *Label13;
        TComboBox *RetrasoTxt;
        TCheckBox *MostrarCheck;
        TTimer *SimuTimer;
        void __fastcall DibujoPaint(TObject *Sender);
        void __fastcall bPausaClick(TObject *Sender);
        void __fastcall bPasoClick(TObject *Sender);
        void __fastcall bGoOtroClick(TObject *Sender);
        void __fastcall bDetenerOtroClick(TObject *Sender);
        void __fastcall MostrarCheckClick(TObject *Sender);
        void __fastcall SimuTimerTimer(TObject *Sender);
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall RetrasoTxtChange(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TFDibujo(TComponent* Owner);
        void Idioma(int idioma);
};
//---------------------------------------------------------------------------
extern PACKAGE TFDibujo *FDibujo;
//---------------------------------------------------------------------------
#endif
