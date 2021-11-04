object FGrafico: TFGrafico
  Left = 1097
  Top = 164
  Width = 605
  Height = 475
  Caption = 'Chart'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = 11
  Font.Name = 'MS Sans Serif'
  Font.Pitch = fpVariable
  Font.Style = []
  OldCreateOrder = True
  Position = poDefaultPosOnly
  DesignSize = (
    589
    437)
  PixelsPerInch = 96
  TextHeight = 13
  object Grafico: TPaintBox
    Left = 0
    Top = 0
    Width = 585
    Height = 433
    Anchors = [akLeft, akTop, akRight, akBottom]
    OnPaint = GraficoPaint
  end
end
