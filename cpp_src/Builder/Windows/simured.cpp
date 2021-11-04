//---------------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop

USEFORM("..\Common\simuredunit.cpp", FPrincipal);
USEFORM("..\Common\dibujounit.cpp", FDibujo);
USEFORM("..\Common\graficounit.cpp", FGrafico);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
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
        return 0;
}
//---------------------------------------------------------------------------
