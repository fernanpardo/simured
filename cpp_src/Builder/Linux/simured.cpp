#define __Linux__
#include <clx.h>
#pragma hdrstop
USEFORM("simuredunit.cpp", FPrincipal);
USEFORM("dibujounit.cpp", FDibujo);
USEFORM("graficounit.cpp", FGrafico);
//---------------------------------------------------------------------------
int main(void)
{
        try
        {
                 Application->Initialize();
                 Application->Title = "SimuRed";
                 Application->CreateForm(__classid(TFPrincipal), &FPrincipal);
                 Application->CreateForm(__classid(TFDibujo), &FDibujo);
                 Application->CreateForm(__classid(TFGrafico), &FGrafico);
                 Application->Run();
        }
        catch (Exception &exception)
        {
                 Application->ShowException(&exception);
        }
        catch(...)
        {
                 try
                 {
                         throw Exception("");
                 }
                 catch(Exception &exception)
                 {
                         Application->ShowException(&exception);
                 }
        }
        return 0;
}
//---------------------------------------------------------------------------


