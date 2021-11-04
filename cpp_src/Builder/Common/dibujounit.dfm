object FDibujo: TFDibujo
  Left = 700
  Top = 231
  Width = 524
  Height = 472
  Caption = 'La Red'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = 11
  Font.Name = 'MS Sans Serif'
  Font.Pitch = fpVariable
  Font.Style = []
  OldCreateOrder = True
  Position = poDefaultPosOnly
  OnClose = FormClose
  DesignSize = (
    508
    434)
  PixelsPerInch = 96
  TextHeight = 13
  object Dibujo: TPaintBox
    Left = 0
    Top = 64
    Width = 505
    Height = 369
    Anchors = [akLeft, akTop, akRight, akBottom]
    OnPaint = DibujoPaint
  end
  object Label13: TLabel
    Left = 264
    Top = 40
    Width = 117
    Height = 13
    Caption = 'Retraso entre Ciclos (ms)'
  end
  object bPaso: TButton
    Left = 8
    Top = 32
    Width = 81
    Height = 25
    Caption = 'Paso a Paso'
    Enabled = False
    TabOrder = 0
    OnClick = bPasoClick
  end
  object bPausa: TButton
    Left = 96
    Top = 0
    Width = 81
    Height = 25
    Caption = 'Pausa'
    Enabled = False
    TabOrder = 1
    OnClick = bPausaClick
  end
  object bGoOtro: TButton
    Left = 8
    Top = 0
    Width = 81
    Height = 25
    Caption = 'Simular'
    TabOrder = 2
    OnClick = bGoOtroClick
  end
  object bDetenerOtro: TButton
    Left = 96
    Top = 32
    Width = 81
    Height = 25
    Caption = 'Interrumpir'
    Enabled = False
    TabOrder = 3
    OnClick = bDetenerOtroClick
  end
  object VerNum: TCheckBox
    Left = 192
    Top = 16
    Width = 137
    Height = 17
    Caption = 'Mostrar N'#250'mero de flit'
    Checked = True
    Enabled = False
    State = cbChecked
    TabOrder = 4
  end
  object RetrasoTxt: TComboBox
    Left = 192
    Top = 32
    Width = 65
    Height = 21
    DropDownCount = 20
    Enabled = False
    ItemHeight = 13
    ItemIndex = 3
    TabOrder = 5
    Text = '100'
    OnChange = RetrasoTxtChange
    Items.Strings = (
      '0'
      '1'
      '10'
      '100'
      '200'
      '500'
      '1000'
      '2000')
  end
  object MostrarCheck: TCheckBox
    Left = 192
    Top = 0
    Width = 105
    Height = 17
    Caption = 'Mostrar Evoluci'#243'n'
    TabOrder = 6
    OnClick = MostrarCheckClick
  end
  object SimuTimer: TTimer
    Enabled = False
    OnTimer = SimuTimerTimer
    Left = 424
    Top = 8
  end
end
