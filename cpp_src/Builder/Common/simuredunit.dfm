object FPrincipal: TFPrincipal
  Left = 774
  Top = 171
  Width = 480
  Height = 641
  HorzScrollBar.Range = 457
  VertScrollBar.Range = 313
  ActiveControl = TxtSal
  AutoScroll = False
  Caption = 'SimuRed'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = 10
  Font.Name = 'MS Sans Serif'
  Font.Pitch = fpVariable
  Font.Style = []
  OldCreateOrder = True
  Position = poDefaultPosOnly
  OnClose = FormClose
  DesignSize = (
    464
    603)
  PixelsPerInch = 96
  TextHeight = 13
  object MultiPages: TPageControl
    Left = 8
    Top = 0
    Width = 449
    Height = 329
    ActivePage = RedTab
    MultiLine = True
    TabIndex = 0
    TabOrder = 1
    object RedTab: TTabSheet
      Caption = 'Red'
      object GroupBoxRed: TGroupBox
        Left = 8
        Top = 0
        Width = 425
        Height = 129
        Caption = 'Topolog'#237'a'
        TabOrder = 0
        object Label1: TLabel
          Left = 64
          Top = 16
          Width = 60
          Height = 13
          Caption = 'Dimensiones'
        end
        object Label3: TLabel
          Left = 360
          Top = 16
          Width = 40
          Height = 13
          Caption = 'Virtuales'
        end
        object Label4: TLabel
          Left = 256
          Top = 16
          Width = 52
          Height = 13
          Caption = 'Lon. Buffer'
        end
        object Label2: TLabel
          Left = 160
          Top = 16
          Width = 54
          Height = 13
          Caption = 'Nodos/Dim'
        end
        object LabelInj: TLabel
          Left = 256
          Top = 64
          Width = 87
          Height = 13
          Caption = 'Injection Channels'
        end
        object LabelEje: TLabel
          Left = 256
          Top = 88
          Width = 85
          Height = 13
          Caption = 'Ejection Channels'
        end
        object DimTxt: TComboBox
          Left = 64
          Top = 32
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 1
          TabOrder = 3
          Text = '2'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9')
        end
        object NodosTxt: TComboBox
          Left = 160
          Top = 32
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 1
          TabOrder = 5
          Text = '4'
          OnChange = DimTxtChange
          Items.Strings = (
            '2'
            '4'
            '6'
            '8'
            '12'
            '16'
            '24'
            '32'
            '48'
            '64'
            '96'
            '128'
            '192'
            '256')
        end
        object VirtualesTxt: TComboBox
          Left = 352
          Top = 32
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 2
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object FifoTxt: TComboBox
          Left = 256
          Top = 32
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 1
          TabOrder = 4
          Text = '2'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '6'
            '8'
            '12'
            '16'
            '24'
            '32'
            '48'
            '64'
            '96'
            '128'
            '192'
            '256')
        end
        object BidirCheck: TCheckBox
          Left = 64
          Top = 56
          Width = 137
          Height = 17
          Caption = 'Bidireccional'
          Checked = True
          State = cbChecked
          TabOrder = 0
        end
        object AdelantoCheck: TCheckBox
          Left = 64
          Top = 80
          Width = 137
          Height = 17
          Caption = 'Adelanto en Buffer'
          Checked = True
          State = cbChecked
          TabOrder = 1
        end
        object FisicoCheck: TCheckBox
          Left = 64
          Top = 104
          Width = 121
          Height = 17
          Caption = 'Canales F'#237'sicos'
          TabOrder = 6
        end
        object VirtInjTxt: TComboBox
          Left = 352
          Top = 56
          Width = 65
          Height = 21
          DropDownCount = 20
          Enabled = False
          ItemHeight = 13
          TabOrder = 7
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object VirtEjeTxt: TComboBox
          Left = 352
          Top = 80
          Width = 65
          Height = 21
          DropDownCount = 20
          Enabled = False
          ItemHeight = 13
          TabOrder = 8
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object InjectionCheck: TCheckBox
          Left = 254
          Top = 104
          Width = 163
          Height = 17
          Alignment = taLeftJustify
          Caption = 'Injection=Ejection=Vitruals'
          Checked = True
          State = cbChecked
          TabOrder = 9
          OnClick = InjectionCheckClick
        end
      end
      object GroupBoxRet: TGroupBox
        Left = 8
        Top = 136
        Width = 425
        Height = 57
        Caption = 'Retrasos'
        TabOrder = 1
        object Label5: TLabel
          Left = 80
          Top = 8
          Width = 28
          Height = 13
          Caption = 'Buffer'
        end
        object Label6: TLabel
          Left = 168
          Top = 8
          Width = 42
          Height = 13
          Caption = 'CrossBar'
        end
        object Label7: TLabel
          Left = 256
          Top = 8
          Width = 62
          Height = 13
          Caption = 'Conmutaci'#243'n'
        end
        object Label8: TLabel
          Left = 368
          Top = 8
          Width = 27
          Height = 13
          Caption = 'Canal'
        end
        object LatCrossTxt: TComboBox
          Left = 160
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 3
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object LatConmutTxt: TComboBox
          Left = 256
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 0
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object LatCanalTxt: TComboBox
          Left = 352
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 1
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
        object LatFifoTxt: TComboBox
          Left = 64
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 2
          Text = '1'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8'
            '9'
            '10'
            '11'
            '12'
            '13'
            '14'
            '15'
            '16')
        end
      end
      object GroupBoxConmut: TGroupBox
        Left = 8
        Top = 200
        Width = 425
        Height = 41
        Caption = 'Conmutaci'#243'n'
        TabOrder = 2
        object ConmutTxt: TComboBox
          Left = 224
          Top = 12
          Width = 193
          Height = 21
          Style = csDropDownList
          BiDiMode = bdLeftToRight
          ItemHeight = 13
          ItemIndex = 0
          ParentBiDiMode = False
          TabOrder = 0
          Text = 'Wormhole'
          Items.Strings = (
            'Wormhole')
        end
      end
      object GroupBoxEncam: TGroupBox
        Left = 8
        Top = 248
        Width = 425
        Height = 49
        Caption = 'Encaminamiento'
        TabOrder = 3
        object RoutingTxt: TComboBox
          Left = 64
          Top = 20
          Width = 353
          Height = 21
          Style = csDropDownList
          ItemHeight = 13
          ItemIndex = 0
          TabOrder = 0
          Text = 'Orden de Dimensi'#243'n (XY) en mallas (determinista)'
          Items.Strings = (
            'Orden de Dimensi'#243'n (XY) en mallas (determinista)'
            'Orden de Dimensi'#243'n (XY) en toros (tiene bloqueos mortales)'
            'Duato basado en XY para mallas (adaptativo)'
            'Completamente Adaptativo para mallas (tiene bloqueos)')
        end
      end
    end
    object PaqueteTab: TTabSheet
      Caption = 'Paquetes'
      ImageIndex = 1
      object GroupBoxPaq: TGroupBox
        Left = 8
        Top = 8
        Width = 425
        Height = 57
        Caption = 'Paquete'
        TabOrder = 0
        object Label10: TLabel
          Left = 80
          Top = 8
          Width = 64
          Height = 13
          Caption = 'Flits de Datos'
        end
        object Label14: TLabel
          Left = 224
          Top = 8
          Width = 82
          Height = 13
          Caption = 'Flits de Cabecera'
        end
        object LonPaqTxt: TComboBox
          Left = 80
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 12
          ItemHeight = 13
          ItemIndex = 5
          TabOrder = 0
          Text = '32'
          OnChange = DimTxtChange
          Items.Strings = (
            '1'
            '2'
            '4'
            '8'
            '16'
            '32'
            '64'
            '128'
            '256'
            '512'
            '1024')
        end
        object LonCabTxt: TComboBox
          Left = 232
          Top = 24
          Width = 65
          Height = 21
          DropDownCount = 9
          ItemHeight = 13
          TabOrder = 1
          Text = '0'
          OnChange = DimTxtChange
          Items.Strings = (
            '0'
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '7'
            '8')
        end
      end
      object GroupBoxGenPaq: TGroupBox
        Left = 8
        Top = 72
        Width = 425
        Height = 89
        Caption = 'Generaci'#243'n de Paquetes'
        TabOrder = 1
        object TrazasCheck: TCheckBox
          Left = 184
          Top = 16
          Width = 233
          Height = 17
          Caption = 'Obtener Paquetes de un fichero de trazas'
          TabOrder = 0
          OnClick = TrazasCheckClick
        end
        object Button3: TButton
          Left = 8
          Top = 24
          Width = 169
          Height = 25
          Caption = 'Especificar Fichero de trazas'
          TabOrder = 1
          OnClick = Button3Click
        end
        object TrazaFichTxt: TComboBox
          Left = 8
          Top = 56
          Width = 409
          Height = 21
          Hint = '(vac'#237'o)'
          Style = csDropDownList
          ItemHeight = 13
          ParentShowHint = False
          ShowHint = True
          TabOrder = 2
        end
        object LongitCheck: TCheckBox
          Left = 184
          Top = 32
          Width = 161
          Height = 17
          Caption = 'No leer las longitudes'
          Enabled = False
          TabOrder = 3
        end
      end
      object GroupBoxGenAut: TGroupBox
        Left = 8
        Top = 168
        Width = 425
        Height = 73
        Caption = 'Generaci'#243'n Autom'#225'tica'
        TabOrder = 2
        object Label12: TLabel
          Left = 184
          Top = 24
          Width = 151
          Height = 13
          Caption = 'Productividad (Flits/Ciclo/Nodo)'
        end
        object NumFlitTxt: TComboBox
          Left = 8
          Top = 16
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 1
          Text = '1'
          OnChange = NumFlitTxtChange
          Items.Strings = (
            '1'
            '10'
            '20'
            '50'
            '100'
            '200'
            '500'
            '1000'
            '2000'
            '5000'
            '10000')
        end
        object NumPaqTxt: TComboBox
          Left = 8
          Top = 40
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 4
          TabOrder = 0
          Text = '20000'
          OnChange = NumPaqTxtChange
          Items.Strings = (
            '1'
            '1000'
            '5000'
            '10000'
            '20000'
            '50000'
            '100000')
        end
        object NumFlitsRad: TRadioButton
          Left = 72
          Top = 24
          Width = 81
          Height = 17
          Caption = 'Flits/Nodo'
          TabOrder = 2
        end
        object NumPaqRad: TRadioButton
          Left = 72
          Top = 48
          Width = 81
          Height = 17
          Caption = 'Paquetes'
          Checked = True
          TabOrder = 3
          TabStop = True
        end
        object EmisionTxt: TComboBox
          Left = 224
          Top = 40
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 5
          TabOrder = 4
          Text = '0.3'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '0.001'
            '0.005'
            '0.01'
            '0.05'
            '0.1'
            '0.3'
            '0.5'
            '0.8'
            '1.0'
            '1.5'
            '2.0')
        end
      end
      object GroupBoxDummy: TGroupBox
        Left = 8
        Top = 248
        Width = 425
        Height = 49
        Caption = 'Calentamiento'
        TabOrder = 3
        object Label25: TLabel
          Left = 96
          Top = 24
          Width = 45
          Height = 13
          Caption = 'Paquetes'
        end
        object DummyUpPackTxt: TComboBox
          Left = 24
          Top = 20
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 1
          TabOrder = 0
          Text = '100'
          OnChange = DummyUpPackTxtChange
          Items.Strings = (
            '0'
            '100'
            '500'
            '1000'
            '2000'
            '4000'
            '8000')
        end
      end
    end
    object SimuMultTab: TTabSheet
      Caption = 'Simulaci'#243'n'
      ImageIndex = 3
      object bGo: TButton
        Left = 224
        Top = 248
        Width = 113
        Height = 25
        Caption = 'Simulaci'#243'n M'#250'ltiple'
        TabOrder = 0
        OnClick = bGoClick
      end
      object bDetener: TButton
        Left = 344
        Top = 248
        Width = 89
        Height = 25
        Caption = 'Interrumpir'
        Enabled = False
        TabOrder = 1
        OnClick = bDetenerClick
      end
      object GroupBoxPrim: TGroupBox
        Left = 8
        Top = 0
        Width = 209
        Height = 169
        Caption = 'Primera Variable'
        TabOrder = 2
        DesignSize = (
          209
          169)
        object Label11: TLabel
          Left = 16
          Top = 72
          Width = 27
          Height = 13
          Caption = 'Inicial'
        end
        object Label18: TLabel
          Left = 88
          Top = 72
          Width = 22
          Height = 13
          Caption = 'Final'
        end
        object Label19: TLabel
          Left = 152
          Top = 72
          Width = 33
          Height = 13
          Caption = 'Puntos'
        end
        object EscalaRad: TRadioGroup
          Left = 8
          Top = 16
          Width = 193
          Height = 57
          Caption = 'Escala'
          ItemIndex = 0
          Items.Strings = (
            'Lineal'
            'Logar'#237'tmica')
          TabOrder = 0
        end
        object IniTxt: TComboBox
          Left = 8
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 0
          TabOrder = 4
          Text = '0.001'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '0.001'
            '0.002'
            '0.005'
            '0.01'
            '0.02'
            '0.05'
            '0.1'
            '0.2'
            '0.5'
            '1.0'
            '1.5'
            '2.0')
        end
        object FinTxt: TComboBox
          Left = 72
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 6
          TabOrder = 2
          Text = '0.8'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '0.01'
            '0.02'
            '0.05'
            '0.1'
            '0.2'
            '0.5'
            '0.8'
            '1.0'
            '1.5'
            '2.0')
        end
        object PuntosTxt: TComboBox
          Left = 136
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 10
          TabOrder = 3
          Text = '20'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '8'
            '10'
            '12'
            '15'
            '20'
            '25'
            '30'
            '50'
            '60'
            '100')
        end
        object VariableRad: TRadioGroup
          Left = 8
          Top = 112
          Width = 193
          Height = 49
          Anchors = [akLeft, akTop, akRight, akBottom]
          Caption = 'Primera Variable'
          ItemIndex = 0
          Items.Strings = (
            'Productividad (flits/ciclo/nodo)')
          TabOrder = 1
        end
      end
      object GroupBoxSec: TGroupBox
        Left = 224
        Top = 0
        Width = 209
        Height = 241
        Caption = 'Segunda Variable'
        TabOrder = 3
        DesignSize = (
          209
          241)
        object Label9: TLabel
          Left = 16
          Top = 72
          Width = 27
          Height = 13
          Caption = 'Inicial'
        end
        object Label15: TLabel
          Left = 88
          Top = 72
          Width = 22
          Height = 13
          Caption = 'Final'
        end
        object Label16: TLabel
          Left = 152
          Top = 72
          Width = 33
          Height = 13
          Caption = 'Puntos'
        end
        object EscalaRad2: TRadioGroup
          Left = 8
          Top = 16
          Width = 193
          Height = 57
          Caption = 'Escala'
          ItemIndex = 1
          Items.Strings = (
            'Lineal'
            'Logar'#237'tmica')
          TabOrder = 0
        end
        object Ini2Txt: TComboBox
          Left = 8
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          TabOrder = 4
          Text = '1'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '6'
            '8'
            '12'
            '16'
            '24'
            '32'
            '48'
            '64'
            '96'
            '128'
            '256')
        end
        object Fin2Txt: TComboBox
          Left = 72
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 7
          TabOrder = 2
          Text = '16'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '6'
            '8'
            '12'
            '16'
            '24'
            '32'
            '48'
            '64'
            '96'
            '128'
            '256')
        end
        object Puntos2Txt: TComboBox
          Left = 136
          Top = 88
          Width = 65
          Height = 21
          DropDownCount = 20
          ItemHeight = 13
          ItemIndex = 4
          TabOrder = 3
          Text = '5'
          OnChange = EmisionTxtChange
          Items.Strings = (
            '1'
            '2'
            '3'
            '4'
            '5'
            '6'
            '8'
            '10'
            '12'
            '15'
            '20'
            '25'
            '30'
            '50'
            '100')
        end
        object Variable2Rad: TRadioGroup
          Left = 8
          Top = 112
          Width = 193
          Height = 121
          Anchors = [akLeft, akTop, akRight, akBottom]
          Caption = 'Segunda Variable'
          ItemIndex = 0
          Items.Strings = (
            'Ninguna'
            'Dimensiones'
            'Nodos por dimensi'#243'n'
            'Longitud Buffer'
            'Canales Virtuales')
          TabOrder = 1
        end
      end
      object Interact: TCheckBox
        Left = 8
        Top = 280
        Width = 209
        Height = 17
        Caption = 'Habilitar simulaci'#243'n simple/interactiva'
        TabOrder = 4
        Visible = False
        OnClick = InteractClick
      end
      object ButtonEnaSimple: TButton
        Left = 8
        Top = 248
        Width = 209
        Height = 25
        Caption = 'Open Simple/Interactive Simulation'
        TabOrder = 5
        OnClick = ButtonEnaSimpleClick
      end
      object RadStopSim: TRadioGroup
        Left = 8
        Top = 168
        Width = 209
        Height = 73
        Caption = 'Stop Simulation'
        ItemIndex = 0
        Items.Strings = (
          'when all packets arrived'
          'when all packets emmited')
        TabOrder = 6
      end
    end
    object GrafTab: TTabSheet
      Caption = 'Results'
      ImageIndex = 4
      object GroupBoxGraf: TGroupBox
        Left = 8
        Top = 144
        Width = 425
        Height = 153
        Caption = 'Gr'#225'fico'
        TabOrder = 0
        object Label24: TLabel
          Left = 8
          Top = 104
          Width = 25
          Height = 13
          Caption = 'Eje Y'
        end
        object Label22: TLabel
          Left = 8
          Top = 64
          Width = 25
          Height = 13
          Caption = 'Eje X'
        end
        object Ytxt: TComboBox
          Left = 8
          Top = 120
          Width = 409
          Height = 21
          ItemHeight = 13
          TabOrder = 0
          OnChange = YtxtChange
        end
        object Xtxt: TComboBox
          Left = 8
          Top = 80
          Width = 409
          Height = 21
          ItemHeight = 13
          TabOrder = 1
          OnChange = XtxtChange
        end
        object Button1: TButton
          Left = 56
          Top = 40
          Width = 129
          Height = 25
          Caption = 'Cargar Fichero CSV'
          TabOrder = 2
          OnClick = Button1Click
        end
        object Button2: TButton
          Left = 208
          Top = 40
          Width = 145
          Height = 25
          Caption = 'Recargar/Mostrar Gr'#225'fico'
          TabOrder = 3
          OnClick = Button2Click
        end
        object GrafTxt: TComboBox
          Left = 8
          Top = 16
          Width = 409
          Height = 21
          Hint = '(vac'#237'o)'
          Style = csDropDownList
          ItemHeight = 13
          ParentShowHint = False
          ShowHint = True
          TabOrder = 4
          OnChange = GrafTxtChange
        end
      end
      object GroupBoxRes: TGroupBox
        Left = 8
        Top = 8
        Width = 425
        Height = 129
        Caption = 'Guardar resultados'
        TabOrder = 1
        DesignSize = (
          425
          129)
        object GuardarCheck: TCheckBox
          Left = 8
          Top = 16
          Width = 209
          Height = 17
          Anchors = [akLeft, akBottom]
          Caption = 'Guardar Resultados en un Fichero'
          TabOrder = 0
          OnClick = GuardarCheckClick
        end
        object AnyadeCheck: TCheckBox
          Left = 8
          Top = 40
          Width = 185
          Height = 17
          Caption = 'A'#241'ade los resultados al fichero'
          Enabled = False
          TabOrder = 1
        end
        object Button4: TButton
          Left = 8
          Top = 72
          Width = 185
          Height = 25
          Caption = 'Especificar Fichero de Resultados'
          TabOrder = 2
          OnClick = Button4Click
        end
        object ResultFichTxt: TComboBox
          Left = 8
          Top = 100
          Width = 409
          Height = 21
          Hint = '(vac'#237'o)'
          Style = csDropDownList
          ItemHeight = 13
          ParentShowHint = False
          ShowHint = True
          TabOrder = 3
        end
        object GroupBoxStats: TGroupBox
          Left = 208
          Top = 8
          Width = 209
          Height = 89
          Caption = 'Stats'
          TabOrder = 4
          object Label26: TLabel
            Left = 112
            Top = 64
            Width = 33
            Height = 13
            Hint = 'Puntos'
            Caption = 'Puntos'
          end
          object RadFinal: TRadioButton
            Left = 8
            Top = 16
            Width = 113
            Height = 17
            Caption = 'Final'
            Checked = True
            Enabled = False
            TabOrder = 0
            TabStop = True
            OnClick = RadContinuousClick
          end
          object RadContinuous: TRadioButton
            Left = 8
            Top = 32
            Width = 113
            Height = 17
            Caption = 'Continuous'
            Enabled = False
            TabOrder = 1
            OnClick = RadContinuousClick
          end
          object StatPointsTxt: TComboBox
            Left = 8
            Top = 56
            Width = 97
            Height = 21
            Enabled = False
            ItemHeight = 13
            ItemIndex = 2
            TabOrder = 2
            Text = '100'
            OnChange = StatPointsTxtChange
            Items.Strings = (
              '1'
              '50'
              '100'
              '500'
              '1000'
              '5000'
              '10000'
              '50000')
          end
        end
      end
    end
    object MiscTab: TTabSheet
      Caption = 'Misc.'
      ImageIndex = 4
      object GroupBox9: TGroupBox
        Left = 8
        Top = 120
        Width = 425
        Height = 169
        Caption = 'Info'
        TabOrder = 0
        object Label17: TLabel
          Left = 32
          Top = 16
          Width = 112
          Height = 32
          Caption = 'SimuRed'
          Color = clBtnFace
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlue
          Font.Height = 35
          Font.Name = 'MS Sans Serif'
          Font.Pitch = fpVariable
          Font.Style = []
          ParentColor = False
          ParentFont = False
        end
        object Label13: TLabel
          Left = 32
          Top = 56
          Width = 148
          Height = 16
          Caption = 'Universidad de Valencia'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = 16
          Font.Name = 'MS Sans Serif'
          Font.Pitch = fpVariable
          Font.Style = []
          ParentFont = False
        end
        object Label20: TLabel
          Left = 32
          Top = 80
          Width = 115
          Height = 16
          Caption = 'http://simured.uv.es'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = 16
          Font.Name = 'MS Sans Serif'
          Font.Pitch = fpVariable
          Font.Style = []
          ParentFont = False
        end
        object Label21: TLabel
          Left = 32
          Top = 105
          Width = 144
          Height = 16
          Caption = 'Fernando.Pardo@uv.es'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = 16
          Font.Name = 'MS Sans Serif'
          Font.Pitch = fpVariable
          Font.Style = []
          ParentFont = False
        end
        object Label23: TLabel
          Left = 32
          Top = 132
          Width = 78
          Height = 13
          Caption = 'v 3.1 (dec 2012)'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = 13
          Font.Name = 'MS Sans Serif'
          Font.Pitch = fpVariable
          Font.Style = []
          ParentFont = False
        end
      end
      object IdiomaRad: TRadioGroup
        Left = 8
        Top = 8
        Width = 425
        Height = 97
        Caption = 'Idioma'
        ItemIndex = 1
        Items.Strings = (
          'Espa'#241'ol'
          'Ingl'#233's')
        TabOrder = 1
        OnClick = IdiomaRadClick
      end
    end
  end
  object TxtSal: TMemo
    Left = 8
    Top = 336
    Width = 449
    Height = 260
    Anchors = [akLeft, akTop, akRight, akBottom]
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = 11
    Font.Name = 'Courier'
    Font.Pitch = fpVariable
    Font.Style = []
    ParentFont = False
    ReadOnly = True
    ScrollBars = ssVertical
    TabOrder = 0
  end
  object OpenFichDialog: TOpenDialog
    DefaultExt = 'csv'
    Filter = 'CSV (*.csv)|*csv|All (*.*)|*.*'
    FilterIndex = 0
    Title = 'Open'
    Left = 392
  end
  object OpenTrazaDiag: TOpenDialog
    DefaultExt = 'trc'
    Filter = 'Traces (*.trc)|*.trc|All (*.*)|*.*'
    FilterIndex = 0
    Title = 'Open'
    Left = 360
  end
  object OpenGrafDialog: TOpenDialog
    DefaultExt = 'csv'
    Filter = 'CSV (*.csv)|*csv|All (*.*)|*.*'
    FilterIndex = 0
    Title = 'Open'
    Left = 424
  end
end
