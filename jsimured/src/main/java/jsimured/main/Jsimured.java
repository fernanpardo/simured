/*
 * JsimuredJava.java
 * Main Class of the application
 * For Applet version search APPLET in the code (only one occurrence).
 *
 */
package jsimured.main;

//import jsimured.main.GraficoPkg.TRCFilter;
import jsimured.red.Channel;
import jsimured.red.Device;
import jsimured.red.Red;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


//#ifdef{APPLET}
/*
 public class simuredJava extends javax.swing.JApplet{
 // Initializes the applet simuredJava
 public void init(){
      java.awt.event.ActionEvent evt;
        evt=null;
        SimuStop =  true;
        LaRedGlobal = null;
        Continuar = 0;
        AreaDibujo = null;
        initComponents();
        GraficoTab.setEnabled(false);
        GuardarCheck.setEnabled(false);
        RadFinal.setEnabled(false);
        RadContinuous.setEnabled(false);
        StatPointsTxt.setEnabled(false);
        TrazasCheck.setEnabled(false);
        jRadioButton14ActionPerformed(evt);
    }
 */
//#else // application

public class Jsimured extends javax.swing.JFrame{
//public class Jsimured extends javax.swing.JApplet{
    
    // User Declarations
    //public Red LaRedGlobal;
    public static ArrayList Simulaciones; // esto no se bien para que un array...
    public static boolean SimuStop;
    //public static int Continuar;
    public static SimuControlclass SimuControl;
    public static int SimuError;
    public static JPanel AreaDibujo;
    public Dibujo elDibujo = null;
    //public Grafico elGrafico = null;
    public static int vernum = 1;
    public boolean NoDibujes = false;
    //private Simular hilo = null;
    public static int ejeX;
    public static int ejeY;
    //public static int puntos;
    public static int STATPOINTS,FINALSTATS,SAVETOFILE,SIMSTOPEMIT;
    static int LANG,
        DIMENSIONS,   
        NODOSDIM,     
        NUMVIRT,
        NUMVIRTINJ,
        NUMVIRTEJE,
        LONBUFFER,
        NUMDIR,       
        FORWARDING,        
        SWITCH,       
        ROUTING,      
        DELFIFO,      
        DELCROSS,     
        DELCHANNEL,    
        DELSWITCH;   
    static boolean PHYSICAL;
    static boolean ALLVIRTEQ;
    static int PACKNUM,
        PACKLEN,    
        PACKHEADLEN,
        //USETRACE,   
        //NOREADLEN,
        PACKNUMDUMMY; 
    static boolean USETRACE,NOREADLEN;
    static double PACKPROD; 
    static String TRACENAME;
    public static boolean JUSTONE;
    static int SIMUVAR,   
        PUNTOS,     
        ESCALA,     
        SIMUVAR2,
        PUNTOS2,    
        ESCALA2;
    static double LAVARINI,   
        LAVARFIN,   
        LAVARINI2,  
        LAVARFIN2;
    static String VAR2TXT;   
    
    static boolean esApplet, esComando;

  // Creates new form simuredJava
  public Jsimured(boolean esAppl, boolean esComan) {
    //java.awt.event.ActionEvent evt;
    //evt=null;
    SimuStop =  true;
    SimuError=0;
    //LaRedGlobal = null;
    //Continuar = 0;
    SimuControl=new SimuControlclass();
    SimuControl.Write(0);
    AreaDibujo = null;
    esApplet=esAppl;
    esComando=esComan;
    initComponents();
    
    if (esApplet) // en el applet no puedo poner lo de exit on close
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    else // es una aplicacion
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);        
    }
    setLocationByPlatform(true);
    //if (!esComando) setVisible(true);
    // lo pongo aqui pues en el GUI no me hace caso, o no se
    //setSize(565,620);
    setTitle("JSimured: Multicomputer Network Simulator");
    //jRadioButton14ActionPerformed(evt); // pone idioma

    if (Locale.getDefault().getLanguage().equals(new Locale("es").getLanguage()))
    {
        jRadioButton12.setSelected(true);
    }
    else // en ingles por defecto
    {
        jRadioButton14.setSelected(true);      
    }
    
    // si es comando tengo que lanzar la simulacion
    if (esComando)
    {
        Jsimured.SimuControl.Write(0);
        new Thread(Jsimured.Simular).start();
        // FER creo que aqu� deber�a esperar a que acabe el thread y luego salir del sistema
        // pues resulta que no lo puedo hacer aqui; debe ser el propio htread de simular el que salga del sistema si es comando...

    }
    // estos no hacen falta si comando... FER
    elDibujo=new Dibujo();
    //elGrafico=new Grafico();
    setIdioma(Idioma());
    SIMUVAR2=0; // Hab�a que inicializarlo
    String fichero="result.csv";
    ResultFichTxt.addItem(fichero);
    ResultFichTxt.setSelectedItem(fichero);
    GrafTxt.addItem(fichero);
    GrafTxt.setSelectedItem(fichero);
  }
  
//#endif
  
  /** This method is called from within the init() method to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EscalaRad = new javax.swing.ButtonGroup();
        EscalaRad2 = new javax.swing.ButtonGroup();
        Variable2Rad = new javax.swing.ButtonGroup();
        IdiomaRad = new javax.swing.ButtonGroup();
        btGroupGenAutom = new javax.swing.ButtonGroup();
        StopSimuRadGr = new javax.swing.ButtonGroup();
        StatsRadGr = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        RedTab = new javax.swing.JPanel();
        GroupBoxRed = new javax.swing.JPanel();
        DimTxt = new javax.swing.JComboBox();
        NodosTxt = new javax.swing.JComboBox();
        FifoTxt = new javax.swing.JComboBox();
        VirtEjeTxt = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BidirCheck = new javax.swing.JCheckBox();
        AdelantoCheck = new javax.swing.JCheckBox();
        FisicoCheck = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        VirtualesTxt1 = new javax.swing.JComboBox();
        VirtInjTxt = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        GroupBoxRet = new javax.swing.JPanel();
        LatFifoTxt = new javax.swing.JComboBox();
        LatCrossTxt = new javax.swing.JComboBox();
        LatConmutTxt = new javax.swing.JComboBox();
        LatCanalTxt = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        GroupBoxConmut = new javax.swing.JPanel();
        ConmutTxt = new javax.swing.JComboBox();
        GroupBoxEncam = new javax.swing.JPanel();
        RoutingTxt = new javax.swing.JComboBox();
        PaquetesTab = new javax.swing.JPanel();
        GroupBoxPaq = new javax.swing.JPanel();
        LonCabTxt = new javax.swing.JComboBox();
        LonPaqTxt = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        GroupBoxGenAut = new javax.swing.JPanel();
        NumFlitTxt = new javax.swing.JComboBox();
        NumPaqTxt = new javax.swing.JComboBox();
        NumFlitsRad = new javax.swing.JRadioButton();
        NumPaqRad = new javax.swing.JRadioButton();
        EmisionTxt = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        DummyUpPackTxt = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        GroupBoxGenPaq = new javax.swing.JPanel();
        TrazasCheck = new javax.swing.JCheckBox();
        LongitCheck = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        TrazaFichTxt = new javax.swing.JComboBox();
        SimulacionTab = new javax.swing.JPanel();
        GroupBoxPrim = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        VariableRad = new javax.swing.JRadioButton();
        IniTxt = new javax.swing.JComboBox();
        FinTxt = new javax.swing.JComboBox();
        PuntosTxt = new javax.swing.JComboBox();
        jPanel16 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        GroupBoxSec = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        Puntos2Txt = new javax.swing.JComboBox();
        Ini2Txt = new javax.swing.JComboBox();
        Fin2Txt = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        RadStopSim0 = new javax.swing.JRadioButton();
        RadStopSim1 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        GraficoTab = new javax.swing.JPanel();
        GrafTxt = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        Xtxt = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        Ytxt = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        GuardarCheck = new javax.swing.JCheckBox();
        AnyadeCheck = new javax.swing.JCheckBox();
        GroupBoxStats = new javax.swing.JPanel();
        RadFinal = new javax.swing.JRadioButton();
        RadContinuous = new javax.swing.JRadioButton();
        StatPointsTxt = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        ResultFichTxt = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        MiscTab = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        TxtSal = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SimuRed");
        setBounds(new java.awt.Rectangle(0, 0, 553, 583));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("Form"); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        RedTab.setName("RedTab"); // NOI18N
        RedTab.setLayout(null);

        GroupBoxRed.setBorder(javax.swing.BorderFactory.createTitledBorder("Topología"));
        GroupBoxRed.setName("GroupBoxRed"); // NOI18N
        GroupBoxRed.setLayout(null);

        DimTxt.setEditable(true);
        DimTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        DimTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        DimTxt.setSelectedIndex(1);
        DimTxt.setName("DimTxt"); // NOI18N
        DimTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DimTxtActionPerformed(evt);
            }
        });
        GroupBoxRed.add(DimTxt);
        DimTxt.setBounds(70, 30, 60, 20);

        NodosTxt.setEditable(true);
        NodosTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        NodosTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4", "6", "8", "12", "16", "24", "32", "48", "64", "96", "128", "192", "256" }));
        NodosTxt.setSelectedIndex(2);
        NodosTxt.setName("NodosTxt"); // NOI18N
        NodosTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NodosTxtActionPerformed(evt);
            }
        });
        GroupBoxRed.add(NodosTxt);
        NodosTxt.setBounds(190, 30, 60, 20);

        FifoTxt.setEditable(true);
        FifoTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FifoTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "6", "8", "12", "16", "24", "32", "48", "64", "96", "128", "192", "256" }));
        FifoTxt.setSelectedIndex(1);
        FifoTxt.setName("FifoTxt"); // NOI18N
        FifoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FifoTxtActionPerformed(evt);
            }
        });
        GroupBoxRed.add(FifoTxt);
        FifoTxt.setBounds(310, 30, 60, 20);

        VirtEjeTxt.setEditable(true);
        VirtEjeTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        VirtEjeTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        VirtEjeTxt.setEnabled(false);
        VirtEjeTxt.setName("VirtEjeTxt"); // NOI18N
        VirtEjeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VirtEjeTxtActionPerformed(evt);
            }
        });
        GroupBoxRed.add(VirtEjeTxt);
        VirtEjeTxt.setBounds(420, 90, 60, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Dimensiones");
        jLabel1.setName("jLabel1"); // NOI18N
        GroupBoxRed.add(jLabel1);
        jLabel1.setBounds(70, 10, 100, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setText("Nodos/Dim.");
        jLabel2.setName("jLabel2"); // NOI18N
        GroupBoxRed.add(jLabel2);
        jLabel2.setBounds(190, 10, 100, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("Lon./Buffer");
        jLabel3.setName("jLabel3"); // NOI18N
        GroupBoxRed.add(jLabel3);
        jLabel3.setBounds(310, 10, 100, 20);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setText("Virtuales");
        jLabel4.setName("jLabel4"); // NOI18N
        GroupBoxRed.add(jLabel4);
        jLabel4.setBounds(420, 10, 70, 20);

        BidirCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BidirCheck.setSelected(true);
        BidirCheck.setText("Bidireccional");
        BidirCheck.setName("BidirCheck"); // NOI18N
        BidirCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BidirCheckActionPerformed(evt);
            }
        });
        GroupBoxRed.add(BidirCheck);
        BidirCheck.setBounds(60, 60, 180, 21);

        AdelantoCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        AdelantoCheck.setSelected(true);
        AdelantoCheck.setText("Adelanto en Buffer");
        AdelantoCheck.setName("AdelantoCheck"); // NOI18N
        AdelantoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdelantoCheckActionPerformed(evt);
            }
        });
        GroupBoxRed.add(AdelantoCheck);
        AdelantoCheck.setBounds(60, 90, 200, 21);

        FisicoCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FisicoCheck.setText("Canales Físicos");
        FisicoCheck.setName("FisicoCheck"); // NOI18N
        FisicoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FisicoCheckActionPerformed(evt);
            }
        });
        GroupBoxRed.add(FisicoCheck);
        FisicoCheck.setBounds(60, 120, 250, 21);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel24.setText("Canales de eyección");
        jLabel24.setName("jLabel24"); // NOI18N
        GroupBoxRed.add(jLabel24);
        jLabel24.setBounds(310, 90, 110, 13);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel26.setText("Canales de inyección");
        jLabel26.setName("jLabel26"); // NOI18N
        GroupBoxRed.add(jLabel26);
        jLabel26.setBounds(310, 60, 110, 13);

        VirtualesTxt1.setEditable(true);
        VirtualesTxt1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        VirtualesTxt1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        VirtualesTxt1.setName("VirtualesTxt1"); // NOI18N
        VirtualesTxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VirtualesTxt1ActionPerformed(evt);
            }
        });
        GroupBoxRed.add(VirtualesTxt1);
        VirtualesTxt1.setBounds(420, 30, 60, 20);

        VirtInjTxt.setEditable(true);
        VirtInjTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        VirtInjTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        VirtInjTxt.setEnabled(false);
        VirtInjTxt.setName("VirtInjTxt"); // NOI18N
        VirtInjTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VirtInjTxtActionPerformed(evt);
            }
        });
        GroupBoxRed.add(VirtInjTxt);
        VirtInjTxt.setBounds(420, 60, 60, 20);

        jCheckBox1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Inyección=Eyección=Virtuales");
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        GroupBoxRed.add(jCheckBox1);
        jCheckBox1.setBounds(260, 120, 220, 21);

        RedTab.add(GroupBoxRed);
        GroupBoxRed.setBounds(0, 0, 520, 150);

        GroupBoxRet.setBorder(javax.swing.BorderFactory.createTitledBorder("Retrasos"));
        GroupBoxRet.setName("GroupBoxRet"); // NOI18N
        GroupBoxRet.setLayout(null);

        LatFifoTxt.setEditable(true);
        LatFifoTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LatFifoTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        LatFifoTxt.setName("LatFifoTxt"); // NOI18N
        LatFifoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatFifoTxtActionPerformed(evt);
            }
        });
        GroupBoxRet.add(LatFifoTxt);
        LatFifoTxt.setBounds(70, 30, 60, 20);

        LatCrossTxt.setEditable(true);
        LatCrossTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LatCrossTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        LatCrossTxt.setName("LatCrossTxt"); // NOI18N
        LatCrossTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatCrossTxtActionPerformed(evt);
            }
        });
        GroupBoxRet.add(LatCrossTxt);
        LatCrossTxt.setBounds(170, 30, 60, 20);

        LatConmutTxt.setEditable(true);
        LatConmutTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LatConmutTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        LatConmutTxt.setName("LatConmutTxt"); // NOI18N
        LatConmutTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatConmutTxtActionPerformed(evt);
            }
        });
        GroupBoxRet.add(LatConmutTxt);
        LatConmutTxt.setBounds(270, 30, 60, 20);

        LatCanalTxt.setEditable(true);
        LatCanalTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LatCanalTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        LatCanalTxt.setName("LatCanalTxt"); // NOI18N
        LatCanalTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatCanalTxtActionPerformed(evt);
            }
        });
        GroupBoxRet.add(LatCanalTxt);
        LatCanalTxt.setBounds(370, 30, 60, 20);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setText("Buffer");
        jLabel5.setName("jLabel5"); // NOI18N
        GroupBoxRet.add(jLabel5);
        jLabel5.setBounds(80, 10, 50, 20);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setText("CrossBar");
        jLabel6.setName("jLabel6"); // NOI18N
        GroupBoxRet.add(jLabel6);
        jLabel6.setBounds(180, 10, 90, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setText("Conmutación");
        jLabel7.setName("jLabel7"); // NOI18N
        GroupBoxRet.add(jLabel7);
        jLabel7.setBounds(270, 10, 110, 20);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setText("Canal");
        jLabel8.setName("jLabel8"); // NOI18N
        GroupBoxRet.add(jLabel8);
        jLabel8.setBounds(380, 10, 80, 20);

        RedTab.add(GroupBoxRet);
        GroupBoxRet.setBounds(0, 160, 520, 60);

        GroupBoxConmut.setBorder(javax.swing.BorderFactory.createTitledBorder("Conmutación"));
        GroupBoxConmut.setName("GroupBoxConmut"); // NOI18N
        GroupBoxConmut.setLayout(null);

        ConmutTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ConmutTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wormhole " }));
        ConmutTxt.setName("ConmutTxt"); // NOI18N
        ConmutTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConmutTxtActionPerformed(evt);
            }
        });
        GroupBoxConmut.add(ConmutTxt);
        ConmutTxt.setBounds(70, 20, 410, 19);

        RedTab.add(GroupBoxConmut);
        GroupBoxConmut.setBounds(0, 220, 520, 50);

        GroupBoxEncam.setBorder(javax.swing.BorderFactory.createTitledBorder("Encaminamiento"));
        GroupBoxEncam.setName("GroupBoxEncam"); // NOI18N
        GroupBoxEncam.setLayout(null);

        RoutingTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        RoutingTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orden de Dimensión (XY) en mallas (determinista)", "Orden de Dimensión (XY) en toros (tiene bloqueos mortales)", "Duato basado en XY para mallas (adaptativo)", "Completamente Adaptativo para mallas (tiene bloqueos) " }));
        RoutingTxt.setName("RoutingTxt"); // NOI18N
        RoutingTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoutingTxtActionPerformed(evt);
            }
        });
        GroupBoxEncam.add(RoutingTxt);
        RoutingTxt.setBounds(70, 20, 410, 19);

        RedTab.add(GroupBoxEncam);
        GroupBoxEncam.setBounds(0, 280, 520, 50);

        jTabbedPane1.addTab("Red", RedTab);

        PaquetesTab.setName("PaquetesTab"); // NOI18N
        PaquetesTab.setLayout(null);

        GroupBoxPaq.setBorder(javax.swing.BorderFactory.createTitledBorder("Paquete"));
        GroupBoxPaq.setName("GroupBoxPaq"); // NOI18N
        GroupBoxPaq.setLayout(null);

        LonCabTxt.setEditable(true);
        LonCabTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LonCabTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" }));
        LonCabTxt.setName("LonCabTxt"); // NOI18N
        GroupBoxPaq.add(LonCabTxt);
        LonCabTxt.setBounds(240, 20, 70, 20);

        LonPaqTxt.setEditable(true);
        LonPaqTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LonPaqTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024" }));
        LonPaqTxt.setSelectedIndex(5);
        LonPaqTxt.setName("LonPaqTxt"); // NOI18N
        LonPaqTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LonPaqTxtActionPerformed(evt);
            }
        });
        GroupBoxPaq.add(LonPaqTxt);
        LonPaqTxt.setBounds(20, 20, 70, 20);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel9.setText("Flits de Datos");
        jLabel9.setName("jLabel9"); // NOI18N
        GroupBoxPaq.add(jLabel9);
        jLabel9.setBounds(100, 20, 160, 20);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel10.setText("Flits de Cabecera");
        jLabel10.setName("jLabel10"); // NOI18N
        GroupBoxPaq.add(jLabel10);
        jLabel10.setBounds(320, 20, 160, 20);

        PaquetesTab.add(GroupBoxPaq);
        GroupBoxPaq.setBounds(0, 5, 520, 50);

        GroupBoxGenAut.setBorder(javax.swing.BorderFactory.createTitledBorder("Generación Automática"));
        GroupBoxGenAut.setName("GroupBoxGenAut"); // NOI18N
        GroupBoxGenAut.setLayout(null);

        NumFlitTxt.setEditable(true);
        NumFlitTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        NumFlitTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "10", "20", "50", "100", "200", "500", "1000", "2000", "5000", "10000" }));
        NumFlitTxt.setName("NumFlitTxt"); // NOI18N
        NumFlitTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumFlitTxtActionPerformed(evt);
            }
        });
        GroupBoxGenAut.add(NumFlitTxt);
        NumFlitTxt.setBounds(20, 20, 80, 19);

        NumPaqTxt.setEditable(true);
        NumPaqTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        NumPaqTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "100", "500", "1000", "2000", "5000", "10000", "50000", "100000" }));
        NumPaqTxt.setSelectedIndex(5);
        NumPaqTxt.setName("NumPaqTxt"); // NOI18N
        NumPaqTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumPaqTxtActionPerformed(evt);
            }
        });
        GroupBoxGenAut.add(NumPaqTxt);
        NumPaqTxt.setBounds(20, 50, 80, 19);

        btGroupGenAutom.add(NumFlitsRad);
        NumFlitsRad.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        NumFlitsRad.setText("Flits/Nodo");
        NumFlitsRad.setName("NumFlitsRad"); // NOI18N
        NumFlitsRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumFlitsRadActionPerformed(evt);
            }
        });
        GroupBoxGenAut.add(NumFlitsRad);
        NumFlitsRad.setBounds(100, 20, 160, 20);

        btGroupGenAutom.add(NumPaqRad);
        NumPaqRad.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        NumPaqRad.setSelected(true);
        NumPaqRad.setText("Paquetes");
        NumPaqRad.setName("NumPaqRad"); // NOI18N
        NumPaqRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumPaqRadActionPerformed(evt);
            }
        });
        GroupBoxGenAut.add(NumPaqRad);
        NumPaqRad.setBounds(100, 50, 170, 20);

        EmisionTxt.setEditable(true);
        EmisionTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        EmisionTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.001", "0.005", "0.01", "0.05", "0.1", "0.3", "0.5", "0.8", "1.0", "1.5", "2.0" }));
        EmisionTxt.setSelectedIndex(5);
        EmisionTxt.setName("EmisionTxt"); // NOI18N
        EmisionTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmisionTxtActionPerformed(evt);
            }
        });
        GroupBoxGenAut.add(EmisionTxt);
        EmisionTxt.setBounds(300, 40, 80, 19);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel11.setText("Productividad (Flits/Ciclo/Nodo)");
        jLabel11.setName("jLabel11"); // NOI18N
        GroupBoxGenAut.add(jLabel11);
        jLabel11.setBounds(270, 20, 220, 20);

        PaquetesTab.add(GroupBoxGenAut);
        GroupBoxGenAut.setBounds(0, 60, 520, 80);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dummy Packets"));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(null);

        DummyUpPackTxt.setEditable(true);
        DummyUpPackTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        DummyUpPackTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "100", "500", "1000", "5000", "10000", "50000" }));
        DummyUpPackTxt.setSelectedIndex(1);
        DummyUpPackTxt.setName("DummyUpPackTxt"); // NOI18N
        DummyUpPackTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DummyUpPackTxtActionPerformed(evt);
            }
        });
        jPanel2.add(DummyUpPackTxt);
        DummyUpPackTxt.setBounds(80, 30, 80, 20);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel33.setText("Hola");
        jLabel33.setToolTipText("");
        jLabel33.setName("jLabel33"); // NOI18N
        jPanel2.add(jLabel33);
        jLabel33.setBounds(170, 30, 330, 13);

        PaquetesTab.add(jPanel2);
        jPanel2.setBounds(0, 150, 520, 70);

        GroupBoxGenPaq.setBorder(javax.swing.BorderFactory.createTitledBorder("Generación de Paquetes"));
        GroupBoxGenPaq.setName("GroupBoxGenPaq"); // NOI18N
        GroupBoxGenPaq.setLayout(null);

        TrazasCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TrazasCheck.setText("Obtener Paquetes de un Fichero de Trazas");
        TrazasCheck.setName("TrazasCheck"); // NOI18N
        TrazasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrazasCheckActionPerformed(evt);
            }
        });
        GroupBoxGenPaq.add(TrazasCheck);
        TrazasCheck.setBounds(10, 20, 240, 21);

        LongitCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        LongitCheck.setText("No leer las longitudes");
        LongitCheck.setEnabled(false);
        LongitCheck.setName("LongitCheck"); // NOI18N
        LongitCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LongitCheckActionPerformed(evt);
            }
        });
        GroupBoxGenPaq.add(LongitCheck);
        LongitCheck.setBounds(10, 40, 220, 21);

        jButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton1.setText("Especificar Fichero de Trazas");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        GroupBoxGenPaq.add(jButton1);
        jButton1.setBounds(290, 20, 220, 21);

        TrazaFichTxt.setName("TrazaFichTxt"); // NOI18N
        TrazaFichTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrazaFichTxtActionPerformed(evt);
            }
        });
        GroupBoxGenPaq.add(TrazaFichTxt);
        TrazaFichTxt.setBounds(10, 70, 500, 22);

        PaquetesTab.add(GroupBoxGenPaq);
        GroupBoxGenPaq.setBounds(0, 230, 520, 100);

        jTabbedPane1.addTab("Paquetes", PaquetesTab);

        SimulacionTab.setName("SimulacionTab"); // NOI18N
        SimulacionTab.setLayout(null);

        GroupBoxPrim.setBorder(javax.swing.BorderFactory.createTitledBorder("Primera Variable"));
        GroupBoxPrim.setName("GroupBoxPrim"); // NOI18N
        GroupBoxPrim.setLayout(null);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Primera Variable"));
        jPanel15.setName("jPanel15"); // NOI18N
        jPanel15.setLayout(null);

        VariableRad.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        VariableRad.setSelected(true);
        VariableRad.setText("Productividad (flits/ciclo/nodo)");
        VariableRad.setName("VariableRad"); // NOI18N
        VariableRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VariableRadActionPerformed(evt);
            }
        });
        jPanel15.add(VariableRad);
        VariableRad.setBounds(10, 20, 210, 30);

        GroupBoxPrim.add(jPanel15);
        jPanel15.setBounds(10, 110, 230, 60);

        IniTxt.setEditable(true);
        IniTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        IniTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.001", "0.002", "0.005", "0.01", "0.02", "0.05", "0.1", "0.2", "0.5", "1.0", "2.0" }));
        IniTxt.setName("IniTxt"); // NOI18N
        IniTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniTxtActionPerformed(evt);
            }
        });
        GroupBoxPrim.add(IniTxt);
        IniTxt.setBounds(10, 80, 70, 19);

        FinTxt.setEditable(true);
        FinTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FinTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.01", "0.02", "0.05", "0.1", "0.2", "0.5", "1.0", "1.5", "2.0", "5.0" }));
        FinTxt.setSelectedIndex(5);
        FinTxt.setName("FinTxt"); // NOI18N
        FinTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinTxtActionPerformed(evt);
            }
        });
        GroupBoxPrim.add(FinTxt);
        FinTxt.setBounds(90, 80, 70, 19);

        PuntosTxt.setEditable(true);
        PuntosTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        PuntosTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "5", "10", "20", "50", "100", "200" }));
        PuntosTxt.setSelectedIndex(3);
        PuntosTxt.setName("PuntosTxt"); // NOI18N
        PuntosTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuntosTxtActionPerformed(evt);
            }
        });
        GroupBoxPrim.add(PuntosTxt);
        PuntosTxt.setBounds(170, 80, 70, 19);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Escala"));
        jPanel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jPanel16.setName("jPanel16"); // NOI18N
        jPanel16.setLayout(null);

        EscalaRad.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton4.setSelected(true);
        jRadioButton4.setText("Lineal");
        jRadioButton4.setName("jRadioButton4"); // NOI18N
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jPanel16.add(jRadioButton4);
        jRadioButton4.setBounds(60, 10, 70, 20);

        EscalaRad.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton5.setText("Logarítmica");
        jRadioButton5.setName("jRadioButton5"); // NOI18N
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        jPanel16.add(jRadioButton5);
        jRadioButton5.setBounds(130, 10, 90, 21);

        GroupBoxPrim.add(jPanel16);
        jPanel16.setBounds(10, 20, 230, 40);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel15.setText("Inicial");
        jLabel15.setName("jLabel15"); // NOI18N
        GroupBoxPrim.add(jLabel15);
        jLabel15.setBounds(30, 60, 50, 20);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel16.setText("Final");
        jLabel16.setName("jLabel16"); // NOI18N
        GroupBoxPrim.add(jLabel16);
        jLabel16.setBounds(100, 60, 60, 20);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel17.setText("Puntos");
        jLabel17.setName("jLabel17"); // NOI18N
        GroupBoxPrim.add(jLabel17);
        jLabel17.setBounds(180, 60, 60, 20);

        SimulacionTab.add(GroupBoxPrim);
        GroupBoxPrim.setBounds(10, 0, 250, 180);

        GroupBoxSec.setBorder(javax.swing.BorderFactory.createTitledBorder("Segunda Variable"));
        GroupBoxSec.setName("GroupBoxSec"); // NOI18N
        GroupBoxSec.setLayout(null);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Escala"));
        jPanel13.setName("jPanel13"); // NOI18N
        jPanel13.setLayout(null);

        EscalaRad2.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton6.setText("Lineal");
        jRadioButton6.setName("jRadioButton6"); // NOI18N
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        jPanel13.add(jRadioButton6);
        jRadioButton6.setBounds(60, 10, 80, 21);

        EscalaRad2.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton7.setSelected(true);
        jRadioButton7.setText("Logarítmica");
        jRadioButton7.setName("jRadioButton7"); // NOI18N
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });
        jPanel13.add(jRadioButton7);
        jRadioButton7.setBounds(140, 10, 90, 21);

        GroupBoxSec.add(jPanel13);
        jPanel13.setBounds(10, 20, 240, 40);

        Puntos2Txt.setEditable(true);
        Puntos2Txt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        Puntos2Txt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "8", "10", "12", "15", "20", "25", "30", "50", "100" }));
        Puntos2Txt.setSelectedIndex(4);
        Puntos2Txt.setName("Puntos2Txt"); // NOI18N
        Puntos2Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Puntos2TxtActionPerformed(evt);
            }
        });
        GroupBoxSec.add(Puntos2Txt);
        Puntos2Txt.setBounds(180, 80, 70, 19);

        Ini2Txt.setEditable(true);
        Ini2Txt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        Ini2Txt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "6", "8", "12", "16", "24", "32", "48", "64", "96", "128", "256" }));
        Ini2Txt.setName("Ini2Txt"); // NOI18N
        Ini2Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ini2TxtActionPerformed(evt);
            }
        });
        GroupBoxSec.add(Ini2Txt);
        Ini2Txt.setBounds(20, 80, 70, 19);

        Fin2Txt.setEditable(true);
        Fin2Txt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        Fin2Txt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "6", "8", "12", "16", "24", "32", "48", "64", "96", "128", "256" }));
        Fin2Txt.setSelectedIndex(7);
        Fin2Txt.setName("Fin2Txt"); // NOI18N
        Fin2Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Fin2TxtActionPerformed(evt);
            }
        });
        GroupBoxSec.add(Fin2Txt);
        Fin2Txt.setBounds(100, 80, 70, 19);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel12.setText("Inicial");
        jLabel12.setName("jLabel12"); // NOI18N
        GroupBoxSec.add(jLabel12);
        jLabel12.setBounds(30, 60, 60, 20);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel13.setText("Final");
        jLabel13.setName("jLabel13"); // NOI18N
        GroupBoxSec.add(jLabel13);
        jLabel13.setBounds(110, 60, 60, 20);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel14.setText("Puntos");
        jLabel14.setName("jLabel14"); // NOI18N
        GroupBoxSec.add(jLabel14);
        jLabel14.setBounds(190, 60, 60, 20);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Segunda Variable"));
        jPanel14.setName("jPanel14"); // NOI18N
        jPanel14.setLayout(null);

        Variable2Rad.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton8.setSelected(true);
        jRadioButton8.setText("Ninguna");
        jRadioButton8.setName("jRadioButton8"); // NOI18N
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });
        jPanel14.add(jRadioButton8);
        jRadioButton8.setBounds(10, 20, 220, 20);

        Variable2Rad.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton9.setText("Dimensiones");
        jRadioButton9.setName("jRadioButton9"); // NOI18N
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });
        jPanel14.add(jRadioButton9);
        jRadioButton9.setBounds(10, 40, 220, 20);

        Variable2Rad.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton10.setText("Nodos por Dimensión");
        jRadioButton10.setName("jRadioButton10"); // NOI18N
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });
        jPanel14.add(jRadioButton10);
        jRadioButton10.setBounds(10, 60, 220, 20);

        Variable2Rad.add(jRadioButton11);
        jRadioButton11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton11.setText("Longitud Buffer");
        jRadioButton11.setName("jRadioButton11"); // NOI18N
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });
        jPanel14.add(jRadioButton11);
        jRadioButton11.setBounds(10, 80, 220, 20);

        Variable2Rad.add(jRadioButton13);
        jRadioButton13.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton13.setText("Canales Virtuales");
        jRadioButton13.setName("jRadioButton13"); // NOI18N
        jRadioButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton13ActionPerformed(evt);
            }
        });
        jPanel14.add(jRadioButton13);
        jRadioButton13.setBounds(10, 100, 210, 20);

        GroupBoxSec.add(jPanel14);
        jPanel14.setBounds(10, 110, 240, 130);

        SimulacionTab.add(GroupBoxSec);
        GroupBoxSec.setBounds(260, 0, 260, 250);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Stop Simulation"));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(null);

        StopSimuRadGr.add(RadStopSim0);
        RadStopSim0.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        RadStopSim0.setSelected(true);
        RadStopSim0.setText("when all packets arrived");
        RadStopSim0.setName("RadStopSim0"); // NOI18N
        jPanel1.add(RadStopSim0);
        RadStopSim0.setBounds(10, 20, 230, 21);

        StopSimuRadGr.add(RadStopSim1);
        RadStopSim1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        RadStopSim1.setText("when all packets emitted");
        RadStopSim1.setName("RadStopSim1"); // NOI18N
        jPanel1.add(RadStopSim1);
        RadStopSim1.setBounds(10, 40, 230, 21);

        SimulacionTab.add(jPanel1);
        jPanel1.setBounds(10, 180, 250, 70);

        jButton3.setText("Mostrar/Ocultar Ventana Simulación");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        SimulacionTab.add(jButton3);
        jButton3.setBounds(100, 270, 300, 23);

        jTabbedPane1.addTab("Simulación", SimulacionTab);

        GraficoTab.setName("GraficoTab"); // NOI18N
        GraficoTab.setLayout(null);

        GrafTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        GrafTxt.setName("GrafTxt"); // NOI18N
        GrafTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GrafTxtMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GrafTxtMousePressed(evt);
            }
        });
        GrafTxt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GrafTxtItemStateChanged(evt);
            }
        });
        GrafTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrafTxtActionPerformed(evt);
            }
        });
        GraficoTab.add(GrafTxt);
        GrafTxt.setBounds(10, 220, 490, 20);

        jButton5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton5.setText("Cargar Fichero CSV");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        GraficoTab.add(jButton5);
        jButton5.setBounds(10, 160, 230, 21);

        jButton6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton6.setText("Recargar/Mostrar Gráfico");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        GraficoTab.add(jButton6);
        jButton6.setBounds(270, 160, 230, 21);

        Xtxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        Xtxt.setName("Xtxt"); // NOI18N
        Xtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XtxtActionPerformed(evt);
            }
        });
        GraficoTab.add(Xtxt);
        Xtxt.setBounds(50, 260, 450, 20);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel18.setText("Eje X");
        jLabel18.setName("jLabel18"); // NOI18N
        GraficoTab.add(jLabel18);
        jLabel18.setBounds(10, 260, 40, 20);

        Ytxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        Ytxt.setName("Ytxt"); // NOI18N
        Ytxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YtxtActionPerformed(evt);
            }
        });
        GraficoTab.add(Ytxt);
        Ytxt.setBounds(50, 300, 450, 20);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel19.setText("Eje Y");
        jLabel19.setName("jLabel19"); // NOI18N
        GraficoTab.add(jLabel19);
        jLabel19.setBounds(10, 300, 40, 13);

        jButton2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton2.setText("Especificar Fichero de Resultados");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        GraficoTab.add(jButton2);
        jButton2.setBounds(10, 70, 230, 21);

        GuardarCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        GuardarCheck.setText("Guardar Resultados en un Fichero");
        GuardarCheck.setName("GuardarCheck"); // NOI18N
        GuardarCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarCheckActionPerformed(evt);
            }
        });
        GraficoTab.add(GuardarCheck);
        GuardarCheck.setBounds(10, 20, 240, 21);

        AnyadeCheck.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        AnyadeCheck.setText("Añade los resultados al fichero");
        AnyadeCheck.setEnabled(false);
        AnyadeCheck.setName("AnyadeCheck"); // NOI18N
        AnyadeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnyadeCheckActionPerformed(evt);
            }
        });
        GraficoTab.add(AnyadeCheck);
        AnyadeCheck.setBounds(10, 40, 230, 20);

        GroupBoxStats.setBorder(javax.swing.BorderFactory.createTitledBorder("Statistics"));
        GroupBoxStats.setName("GroupBoxStats"); // NOI18N
        GroupBoxStats.setLayout(null);

        StatsRadGr.add(RadFinal);
        RadFinal.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        RadFinal.setSelected(true);
        RadFinal.setText("Final");
        RadFinal.setName("RadFinal"); // NOI18N
        RadFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadFinalActionPerformed(evt);
            }
        });
        GroupBoxStats.add(RadFinal);
        RadFinal.setBounds(20, 20, 170, 21);

        StatsRadGr.add(RadContinuous);
        RadContinuous.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        RadContinuous.setText("Continuous");
        RadContinuous.setName("RadContinuous"); // NOI18N
        GroupBoxStats.add(RadContinuous);
        RadContinuous.setBounds(20, 40, 180, 21);

        StatPointsTxt.setEditable(true);
        StatPointsTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        StatPointsTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100", "500", "1000", "5000", "10000" }));
        StatPointsTxt.setName("StatPointsTxt"); // NOI18N
        GroupBoxStats.add(StatPointsTxt);
        StatPointsTxt.setBounds(40, 70, 80, 19);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel32.setText("Points");
        jLabel32.setName("jLabel32"); // NOI18N
        GroupBoxStats.add(jLabel32);
        jLabel32.setBounds(130, 70, 70, 20);

        GraficoTab.add(GroupBoxStats);
        GroupBoxStats.setBounds(290, 0, 210, 100);

        ResultFichTxt.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ResultFichTxt.setToolTipText("");
        ResultFichTxt.setName("ResultFichTxt"); // NOI18N
        ResultFichTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultFichTxtActionPerformed(evt);
            }
        });
        GraficoTab.add(ResultFichTxt);
        ResultFichTxt.setBounds(10, 120, 490, 20);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel25.setText("Fichero donde se guardarán los resultados:");
        GraficoTab.add(jLabel25);
        jLabel25.setBounds(10, 100, 380, 16);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel27.setText("Fichero CSV para confeccionar el gráfico:");
        GraficoTab.add(jLabel27);
        jLabel27.setBounds(10, 200, 380, 16);

        jTabbedPane1.addTab("Gráfico", GraficoTab);

        MiscTab.setName("MiscTab"); // NOI18N
        MiscTab.setLayout(null);

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Idioma"));
        jPanel19.setName("jPanel19"); // NOI18N
        jPanel19.setLayout(null);

        IdiomaRad.add(jRadioButton12);
        jRadioButton12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton12.setText("Español");
        jRadioButton12.setName("jRadioButton12"); // NOI18N
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });
        jPanel19.add(jRadioButton12);
        jRadioButton12.setBounds(30, 30, 230, 21);

        IdiomaRad.add(jRadioButton14);
        jRadioButton14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jRadioButton14.setSelected(true);
        jRadioButton14.setText("Inglés");
        jRadioButton14.setToolTipText("Idioma Inglés");
        jRadioButton14.setName("jRadioButton14"); // NOI18N
        jRadioButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton14ActionPerformed(evt);
            }
        });
        jPanel19.add(jRadioButton14);
        jRadioButton14.setBounds(30, 60, 180, 21);

        MiscTab.add(jPanel19);
        jPanel19.setBounds(10, 10, 500, 100);

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));
        jPanel20.setFont(new java.awt.Font("MS Sans Serif", 0, 10)); // NOI18N
        jPanel20.setName("jPanel20"); // NOI18N
        jPanel20.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setText("JSimured v 3.4 (Nov. 2021)");
        jLabel20.setToolTipText("");
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel20.add(jLabel20);
        jLabel20.setBounds(30, 20, 440, 30);
        jLabel20.getAccessibleContext().setAccessibleName("JSimured v3.0 (2012)");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel21.setText("Universidad de Valencia, Fernando Pardo");
        jLabel21.setDebugGraphicsOptions(javax.swing.DebugGraphics.FLASH_OPTION);
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel20.add(jLabel21);
        jLabel21.setBounds(30, 50, 290, 13);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel23.setText("<html><a href=\"http://simured.uv.es\">http://simured.uv.es</a></html>");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        jPanel20.add(jLabel23);
        jLabel23.setBounds(30, 70, 300, 13);

        MiscTab.add(jPanel20);
        jPanel20.setBounds(10, 120, 500, 120);

        jTabbedPane1.addTab("Misc", MiscTab);

        TxtSal.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        TxtSal.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        TxtSal.setName("TxtSal"); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setName("jTextArea1"); // NOI18N
        TxtSal.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtSal))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(TxtSal, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(570, 644));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void DummyUpPackTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DummyUpPackTxtActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_DummyUpPackTxtActionPerformed
            
    private void RadFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadFinalActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_RadFinalActionPerformed
    
    private void GrafTxtItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GrafTxtItemStateChanged
      // TODO add your handling code here:
      //limpiaCampos();
        /*if (evt.MOUSE_EVENT_MASK != 0){
             Xtxt.removeAllItems();
             Ytxt.removeAllItems();
             Grafico();
        }*/
    }//GEN-LAST:event_GrafTxtItemStateChanged
    
    private void GrafTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GrafTxtMousePressed
      // TODO add your handling code here:
      
    }//GEN-LAST:event_GrafTxtMousePressed
    
    private void GrafTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GrafTxtMouseClicked
      // TODO add your handling code here:
      
    }//GEN-LAST:event_GrafTxtMouseClicked
    
    private void jRadioButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton13ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton13.isSelected())
        SIMUVAR2 = 4;
    }//GEN-LAST:event_jRadioButton13ActionPerformed
    
    private void jRadioButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton11ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton11.isSelected())
        SIMUVAR2 = 3;
    }//GEN-LAST:event_jRadioButton11ActionPerformed
    
    private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton10ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton10.isSelected())
        SIMUVAR2 = 2;
    }//GEN-LAST:event_jRadioButton10ActionPerformed
    
    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton9ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton9.isSelected())
        SIMUVAR2 = 1;
    }//GEN-LAST:event_jRadioButton9ActionPerformed
    
    private void YtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YtxtActionPerformed
      // TODO add your handling code here:
      int indice;
      indice = Ytxt.getSelectedIndex();
      SeleccionaEjeY(indice);
    }//GEN-LAST:event_YtxtActionPerformed
    
    private void XtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XtxtActionPerformed
      // TODO add your handling code here:
      int indice;
      indice = Xtxt.getSelectedIndex();
      SeleccionaEjeX(indice);
    }//GEN-LAST:event_XtxtActionPerformed
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Este es Recargar/Mostrar Grafico
        // TODO add your handling code here:
        //Grafico g;
        if (leeDatosCSV()==0)
        {
            //if (Simulaciones!= null)
              //g = new Grafico(this,Simulaciones);

              //elGrafico.Pinta(); // esto habia
            pintaGraf();
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      // TODO add your handling code here:
      // Cargar Fichero Graficos
      Xtxt.removeAllItems();
      Ytxt.removeAllItems();
      JFileChooser chooser = new JFileChooser();
      CSVFilter filtroCSV=new CSVFilter();
      chooser.addChoosableFileFilter(filtroCSV);
      chooser.setFileFilter(filtroCSV);
      chooser.paintAll(chooser.getGraphics());
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = chooser.showOpenDialog(null);
      
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        GrafTxt.addItem(chooser.getSelectedFile());
        GrafTxt.setSelectedItem(chooser.getSelectedFile());
        /**
         * Aqui llamaremos a Generar los datos a partir del fichero
         */
        //Grafico();
        System.runFinalization();//Terminamos y Llamamos al garbage collector
        System.gc();
      }
      
    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void GrafTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrafTxtActionPerformed
      // TODO add your handling code here:
      // Llamamos a Grafico q sera el q cargue el grafico actual en memoria
      // para despues mostrarlo con el boton Recargar/mostrar Grafico
      //
    }//GEN-LAST:event_GrafTxtActionPerformed
    
    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton7ActionPerformed
    
    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton6ActionPerformed
    
    private void Fin2TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Fin2TxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_Fin2TxtActionPerformed
    
    private void Ini2TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ini2TxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_Ini2TxtActionPerformed
    
    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton8.isSelected())
        SIMUVAR2 = 0;
    }//GEN-LAST:event_jRadioButton8ActionPerformed
    
   
    private void ResultFichTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultFichTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_ResultFichTxtActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String fichero;// = new String();
        JFileChooser chooser = new JFileChooser();
        CSVFilter filtroCSV=new CSVFilter();
        chooser.addChoosableFileFilter(filtroCSV);
        chooser.setFileFilter(filtroCSV);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            fichero = chooser.getSelectedFile().toString();
            // Si no tiene extension se la a???adimos
            if (!fichero.endsWith(".csv"))
              fichero = fichero.concat(".csv");

            ResultFichTxt.addItem(fichero);
            ResultFichTxt.setSelectedItem(fichero);
            GrafTxt.addItem(fichero);
            GrafTxt.setSelectedItem(fichero);

            GuardarCheck.setSelected(true);
            GuardarCheckActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void VariableRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariableRadActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_VariableRadActionPerformed
    
    private void PuntosTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuntosTxtActionPerformed
      // TODO add your handling code here:
      
    }//GEN-LAST:event_PuntosTxtActionPerformed
    
    private void FinTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_FinTxtActionPerformed
    
    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton5ActionPerformed
    
    private void EmisionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmisionTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_EmisionTxtActionPerformed
    
    private void RoutingTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoutingTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_RoutingTxtActionPerformed
    
    private void NumPaqTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumPaqTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_NumPaqTxtActionPerformed
    
    private void NumPaqRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumPaqRadActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_NumPaqRadActionPerformed
    
    private void NumFlitsRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumFlitsRadActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_NumFlitsRadActionPerformed
    
    private void NumFlitTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumFlitTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_NumFlitTxtActionPerformed
    
    private void TrazaFichTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrazaFichTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_TrazaFichTxtActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      // TODO add your handling code here:
      JFileChooser chooser = new JFileChooser();
      TRCFilter filtroTRC=new TRCFilter();
      chooser.addChoosableFileFilter(filtroTRC);
      chooser.setFileFilter(filtroTRC);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = chooser.showOpenDialog(null);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        TrazaFichTxt.addItem(chooser.getSelectedFile());
        TrazaFichTxt.setSelectedItem(chooser.getSelectedFile());
        TrazasCheck.setSelected(true);
        TrazasCheckActionPerformed(evt);
      }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void LongitCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LongitCheckActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_LongitCheckActionPerformed
    
    private void TrazasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrazasCheckActionPerformed
      // TODO add your handling code here:
      NumFlitTxt.setEnabled(!TrazasCheck.isSelected());
      NumPaqTxt.setEnabled(!TrazasCheck.isSelected());
      EmisionTxt.setEnabled(!TrazasCheck.isSelected());
      NumFlitsRad.setEnabled(!TrazasCheck.isSelected());
      NumPaqRad.setEnabled(!TrazasCheck.isSelected());
      LongitCheck.setEnabled(TrazasCheck.isSelected());
    }//GEN-LAST:event_TrazasCheckActionPerformed
    
    private void VirtEjeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VirtEjeTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_VirtEjeTxtActionPerformed
    
    private void FifoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FifoTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_FifoTxtActionPerformed
    
    private void NodosTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NodosTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_NodosTxtActionPerformed
    
    private void LatCanalTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatCanalTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_LatCanalTxtActionPerformed
    
    private void LatConmutTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatConmutTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_LatConmutTxtActionPerformed
    
    private void LatCrossTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatCrossTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_LatCrossTxtActionPerformed
    
    private void LatFifoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatFifoTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_LatFifoTxtActionPerformed
    
    private void FisicoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FisicoCheckActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_FisicoCheckActionPerformed
    
    private void AdelantoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdelantoCheckActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_AdelantoCheckActionPerformed
    
    private void BidirCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BidirCheckActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_BidirCheckActionPerformed
    
    private void jRadioButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton14ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton14.isSelected()){
        setIdioma(1);
      }
    }//GEN-LAST:event_jRadioButton14ActionPerformed
    
    private void jRadioButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton12ActionPerformed
      // TODO add your handling code here:
      if (jRadioButton12.isSelected()){
          setIdioma(0);
      }
    }//GEN-LAST:event_jRadioButton12ActionPerformed
    
    public static void setIdioma(int elidioma)
    {
      Dibujo.setIdioma(elidioma);
      if (elidioma==1){
        jRadioButton12.setText("Spanish");
        jRadioButton14.setText("English");
        jLabel1.setText("Dimensions");
        jLabel2.setText("Nodes/Dim.");
        jLabel3.setText("Leng.Buffer");
        jLabel4.setText("Virtuals");
        jLabel26.setText("Injection Channels");
        jLabel24.setText("Ejection Channels");
        jCheckBox1.setText("Injection=Eyection=Virtuals");
        BidirCheck.setText("Bidirectional");
        AdelantoCheck.setText("Buffer Forwarding");
        FisicoCheck.setText("Virtual Channels are in fact Physical");
        jLabel5.setText("Buffer");
        jLabel6.setText("CrossBar");
        jLabel7.setText("Switching");
        jLabel8.setText("Channel");
        jLabel9.setText("Data Flits");
        jLabel10.setText("Head Flits");
        TrazasCheck.setText("Read Packets from a Trace File");
        LongitCheck.setText("Do not read packet lengths");
        jButton1.setText("Specify Trace File");
        NumFlitsRad.setText("Flits/Node");
        NumPaqRad.setText("Packets");
        jLabel11.setText("Default Productivity (Flits/Node/Cycle)");
        VariableRad.setText("Productivity (Flits/Node/Cycle)");
        jLabel33.setText("Number of extra wam-up packets");
        jRadioButton4.setText("Linear");
        jRadioButton5.setText("Logarithmic");
        jLabel15.setText("Start");
        jLabel16.setText("End");
        jLabel17.setText("Points");
        jRadioButton6.setText("Linear");
        jRadioButton7.setText("Logarithtmic");
        jLabel12.setText("Start");
        jLabel13.setText("End");
        jLabel14.setText("Points");
        jRadioButton8.setText("None");
        jRadioButton9.setText("Dimensions");
        jRadioButton10.setText("Nodes per Dimension");
        jRadioButton11.setText("Buffer Length");
        jRadioButton13.setText("Virtual Channels");
        RadStopSim0.setText("when all packets have arrived");
        RadStopSim1.setText("when all packets have been emitted");
        jButton3.setText("Show/Hide Simulation Window");
        //InteractCheck.setText("Enable simple/interactive Simulation");
        GuardarCheck.setText("Save statistics to a CSV file");
        AnyadeCheck.setText("Append statistics to the file");
        jButton2.setText("Specify a statistics CSV File");
        RadFinal.setText("Final");
        RadContinuous.setText("Continuously using...");
        jLabel32.setText("records");
        //bGo.setText("Multiple Simulate");
        //bDetener.setText("Stop");
        jButton5.setText("Specify a CSV File for Chart drawing");
        jButton6.setText("Show/Repaint Chart");
        jLabel18.setText("X-axis");
        jLabel19.setText("Y-axis");
        jLabel25.setText("CSV file for storing the results:");
        jLabel27.setText("CSV file with the data for the chart:");
        
        jTabbedPane1.setTitleAt(0, "Network");
        jTabbedPane1.setTitleAt(1, "Packets");
        jTabbedPane1.setTitleAt(2, "Simulation");
        jTabbedPane1.setTitleAt(3, "Results");
        jTabbedPane1.setTitleAt(4, "Language");
        GroupBoxRed.setBorder(new javax.swing.border.TitledBorder("Topology"));
        GroupBoxRet.setBorder(new javax.swing.border.TitledBorder("Delays"));
        GroupBoxConmut.setBorder(new javax.swing.border.TitledBorder("Switching"));
        GroupBoxEncam.setBorder(new javax.swing.border.TitledBorder("Routing"));
        RoutingTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dimension Order (XY) for meshes (deterministic)", "Dimension Order (XY) for tori (with deadlocks)", "Duato based on XY for meshes (adaptative)", "Fully Adaptative for meshes (with deadlocks)" }));
        GroupBoxPaq.setBorder(new javax.swing.border.TitledBorder("Packet"));
        GroupBoxGenPaq.setBorder(new javax.swing.border.TitledBorder("Use a trace file"));
        GroupBoxGenAut.setBorder(new javax.swing.border.TitledBorder("Automatic Creation"));
        jPanel2.setBorder(new javax.swing.border.TitledBorder("Initial warm-up packets"));
        jPanel14.setBorder(new javax.swing.border.TitledBorder("Second Variable"));
        GroupBoxSec.setBorder(new javax.swing.border.TitledBorder("Second Variable"));
        jPanel13.setBorder(new javax.swing.border.TitledBorder("Range"));
        jPanel16.setBorder(new javax.swing.border.TitledBorder("Range"));
        jPanel15.setBorder(new javax.swing.border.TitledBorder("First Variable"));
        GroupBoxPrim.setBorder(new javax.swing.border.TitledBorder("First Variable"));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Finish Simulation"));
        GroupBoxStats.setBorder(new javax.swing.border.TitledBorder("Statistics"));
        jPanel19.setBorder(new javax.swing.border.TitledBorder("Language"));
      }
      else if (elidioma==0){
        jRadioButton12.setText("Español");
        jRadioButton14.setText("Inglés");
        jLabel1.setText("Dimensiones");
        jLabel2.setText("Nodos/Dim.");
        jLabel3.setText("Long.Buffer");
        jLabel4.setText("Virtuales");
        jLabel26.setText("Canales de Inyección");
        jLabel24.setText("Canales de Eyección");
        jCheckBox1.setText("Inyección=Eyección=Virtuales");
        BidirCheck.setText("Bidireccional");
        AdelantoCheck.setText("Adelanto en Buffer");
        FisicoCheck.setText("Los Canales Virtuales son Físicos");
        jLabel5.setText("Buffer");
        jLabel6.setText("CrossBar");
        jLabel7.setText("Conmutación");
        jLabel8.setText("Canal");
        jLabel9.setText("Flits de Datos");
        jLabel10.setText("Flits de Cabecera");
        TrazasCheck.setText("Leer Paquetes de un Fichero de Trazas");
        LongitCheck.setText("No leer las longitudes");
        jButton1.setText("Especificar Fichero de Trazas");
        NumFlitsRad.setText("Flits/Nodo");
        NumPaqRad.setText("Paquetes");
        jLabel11.setText("Productividad por defecto (Flits/Nodo/Ciclo)");
        VariableRad.setText("Productividad (flits/Nodo/Ciclo)");
        jLabel33.setText("Número de paquetes extra para precalentamiento");
        jRadioButton4.setText("Lineal");
        jRadioButton5.setText("Logarítmica");
        jLabel15.setText("Inicial");
        jLabel16.setText("Final");
        jLabel17.setText("Puntos");
        jRadioButton6.setText("Lineal");
        jRadioButton7.setText("Logarítmica");
        jLabel12.setText("Inicial");
        jLabel13.setText("Final");
        jLabel14.setText("Puntos");
        jRadioButton8.setText("Ninguna");
        jRadioButton9.setText("Dimensiones");
        jRadioButton10.setText("Nodos por Dimensión");
        jRadioButton11.setText("Longitud Colas");
        jRadioButton13.setText("Canales Virtuales");
        RadStopSim0.setText("cuando todos los paquetes hayan llegado");
        RadStopSim1.setText("cuando todos los paquetes hayan salido");
        jButton3.setText("Mostrar/Ocultar Ventana de Simulación");
        //InteractCheck.setText("Habilitar simulaci\u00f3n simple/interactiva");
        GuardarCheck.setText("Guardar Estadísticas en un Fichero CSV");
        AnyadeCheck.setText("Añade los resultados al fichero");
        jButton2.setText("Especificar Fichero de Resultados CSV");
        RadFinal.setText("Al final");
        RadContinuous.setText("Continuamente usando...");
        jLabel32.setText("registros");
        //bGo.setText("Simular Múltiple");
        //bDetener.setText("Interrumpir");
        jButton5.setText("Especificar Fichero CSV para el Gráfico");
        jButton6.setText("Recargar/Mostrar Gráfico");
        jLabel18.setText("Eje X");
        jLabel19.setText("Eje Y");
        jLabel25.setText("Fichero donde se guardarán los resultados:");
        jLabel27.setText("Fichero CSV para confeccionar el gráfico:");

        jTabbedPane1.setTitleAt(0, "Red");
        jTabbedPane1.setTitleAt(1, "Paquetes");
        jTabbedPane1.setTitleAt(2, "Simulación");
        jTabbedPane1.setTitleAt(3, "Resultados");
        jTabbedPane1.setTitleAt(4, "Idioma");
        GroupBoxRed.setBorder(new javax.swing.border.TitledBorder("Topología"));
        GroupBoxRet.setBorder(new javax.swing.border.TitledBorder("Retrasos"));
        GroupBoxConmut.setBorder(new javax.swing.border.TitledBorder("Conmutación"));
        GroupBoxEncam.setBorder(new javax.swing.border.TitledBorder("Encaminamiento"));
        RoutingTxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orden de Dimensión (XY) en mallas (determinista)", "Orden de Dimensión (XY) en toros (tiene bloqueos mortales)", "Duato basado en XY para mallas (adaptativo)", "Completamente Adaptativo para mallas (tiene bloqueos)"}));
        GroupBoxPaq.setBorder(new javax.swing.border.TitledBorder("Paquete"));
        GroupBoxGenPaq.setBorder(new javax.swing.border.TitledBorder("Usar un fichero de trazas"));
        GroupBoxGenAut.setBorder(new javax.swing.border.TitledBorder("Generación Automática"));
        jPanel2.setBorder(new javax.swing.border.TitledBorder("Paquetes iniciales de precalentamiento"));
        jPanel14.setBorder(new javax.swing.border.TitledBorder("Segunda Variable"));
        GroupBoxSec.setBorder(new javax.swing.border.TitledBorder("Segunda Variable"));
        jPanel13.setBorder(new javax.swing.border.TitledBorder("Escala"));
        jPanel16.setBorder(new javax.swing.border.TitledBorder("Escala"));
        jPanel15.setBorder(new javax.swing.border.TitledBorder("Primera Variable"));
        GroupBoxPrim.setBorder(new javax.swing.border.TitledBorder("Primera Variable"));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Termina la simulación"));
        GroupBoxStats.setBorder(new javax.swing.border.TitledBorder("Estadísticas"));
        jPanel19.setBorder(new javax.swing.border.TitledBorder("Idioma"));
      }      
    }
    
    private void GuardarCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarCheckActionPerformed
      // TODO add your handling code here:
      if(GuardarCheck.isSelected())
        AnyadeCheck.setEnabled(true);
      else{
        AnyadeCheck.setEnabled(false);
        AnyadeCheck.setSelected(false);
      }
    }//GEN-LAST:event_GuardarCheckActionPerformed
    
    private void ConmutTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConmutTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_ConmutTxtActionPerformed
    
    private void DimTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DimTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_DimTxtActionPerformed
    
    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed
    
    private void IniTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniTxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_IniTxtActionPerformed
    
    private void AnyadeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnyadeCheckActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_AnyadeCheckActionPerformed
    
    private void Puntos2TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Puntos2TxtActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_Puntos2TxtActionPerformed

    private void VirtualesTxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VirtualesTxt1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox1.isSelected())
        {
            VirtInjTxt.setSelectedItem(VirtualesTxt1.getSelectedItem());
            VirtEjeTxt.setSelectedItem(VirtualesTxt1.getSelectedItem());
        }
    }//GEN-LAST:event_VirtualesTxt1ActionPerformed

    private void VirtInjTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VirtInjTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VirtInjTxtActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        //VirtInjTxt
        if (jCheckBox1.isSelected())
        {
            VirtInjTxt.setSelectedItem(VirtualesTxt1.getSelectedItem());
            VirtInjTxt.setEnabled(false);
            VirtEjeTxt.setSelectedItem(VirtualesTxt1.getSelectedItem());
            VirtEjeTxt.setEnabled(false);
        }
        else
        {
            VirtInjTxt.setEnabled(true);
            VirtEjeTxt.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        elDibujo.setVisible(!elDibujo.isVisible());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void LonPaqTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LonPaqTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LonPaqTxtActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("http://simured.uv.es"));
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel23MouseClicked
    /**
     * Comprueba el idioma seleccionado
     * 0 -> Español
     * 1 -> Ingles
     */
    public static int Idioma(){
      if (jRadioButton12.isSelected()){
        return (0);
      } else
        return(1);
    }

    private static int strcmp(String str1, String str2)
    {
        if (str1.equalsIgnoreCase(str2)) return (0);
        return(1);
    }
    
    
    public static void preSimularCMD()
    {
        BufferedReader fich = new BufferedReader(new InputStreamReader(System.in));
 
        //InputStream fich=System.in;
        int numlin=0;
 // defaut values
 LANG=Idioma();
 
 // Network
 DIMENSIONS=2;
 NODOSDIM=4;
 NUMVIRT=1;
 NUMVIRTINJ=1;
 NUMVIRTEJE=1;
 ALLVIRTEQ=true;
 LONBUFFER=2;
 NUMDIR=2;  // 2=bidir, 1=unidir
 FORWARDING=1; // 1=buffer forwarding, 0=no forward
 PHYSICAL=false;
 SWITCH=0; // 0=wormhole
 ROUTING=0; // 0=xy mesh, 1=xy toro, 2=Duato mesh, 3=fully adapt. mesh.
 DELFIFO=1;
 DELCROSS=1;
 DELCHANNEL=1;
 DELSWITCH=1;
 
 // Packet
 PACKNUM=10000;
 PACKLEN=32;
 PACKHEADLEN=0;
 PACKPROD=0.3; // (flits/node/cycle)
 PACKNUMDUMMY=100;
 
 // Trace File
 int USETRACEnum=0; // 0=no, 1=yes
 TRACENAME="trace.trc";
 int NOREADLENnum=0; // OverRide packet lengh specified in trace file
 
 // Simulation
 JUSTONE=false;
 // esta no la tenia en el original...
 SIMSTOPEMIT=0; // 0=stop cuando han llegado, 1=cuando salen

 //Inner loop variable
 SIMUVAR=0; // 0=Productivity (flits/node/cycle)
 LAVARINI=0.01;
 LAVARFIN=0.6;
 PUNTOS=10;
 ESCALA=0; // 0=linear, 1=log
 // Outer loop variable
 SIMUVAR2=0; // 0=none, 1=dimensions, 2=nodes/dimension, 3=lon.buffer, 4=virtuals
 LAVARINI2=1;
 LAVARFIN2=16;
 PUNTOS2=5;
 ESCALA2=1; // 0=linear, 1=log
 FINALSTATS=1; // 1=only final statistics, 0=continuous tracking
 STATPOINTS=100; // points for continuos stat tracking

 String linea;
 String palabra, pal2;
 double valor=0;
 while (true)
 {
     linea=null;
            try {
                linea=fich.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Jsimured.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
   if (linea==null) break;
   numlin++;
   //System.err.println(numlin);
   if (linea.length()==0) continue;
   if (linea.charAt(0)!='#')  // comentario
   {
       String cosa[]=linea.split("[ \t]+");
     if (cosa.length<2)
     {
        System.err.println("Less than two parameters in "+numlin+": "+linea);
        continue;
     }
     palabra=cosa[0];
     pal2=cosa[1];
     try {
        valor=Double.valueOf(pal2);
     }
     catch (NumberFormatException ex)
     {
         // no es num
       if ((strcmp(palabra,"PHYSICAL")!=0) && 
           (strcmp(palabra,"TRACENAME")!=0) && 
           (strcmp(palabra,"ALLVIRTEQ")!=0) && 
           (strcmp(palabra,"JUSTONE")!=0) ) 
       {
         System.err.println("Malformed line "+(numlin)+": "+(linea));
         continue;
       }
     }
     if (strcmp(palabra,"LANG")==0) LANG=(int)valor;
     if (strcmp(palabra,"DIMENSIONS")==0) DIMENSIONS=(int)valor;
     if (strcmp(palabra,"NODOSDIM")==0) NODOSDIM=(int)valor;
     if (strcmp(palabra,"NUMVIRT")==0) NUMVIRT=(int)valor;
     if (strcmp(palabra,"NUMVIRTINJ")==0) NUMVIRTINJ=(int)valor;
     if (strcmp(palabra,"NUMVIRTEJE")==0) NUMVIRTEJE=(int)valor;
     if (strcmp(palabra,"LONBUFFER")==0) LONBUFFER=(int)valor;
     if (strcmp(palabra,"NUMDIR")==0) NUMDIR=(int)valor;
     if (strcmp(palabra,"FORWARDING")==0) FORWARDING=(int)valor;
     if (strcmp(palabra,"PHYSICAL")==0)
     {
       if (strcmp(pal2,"true")==0) PHYSICAL=true;
       else PHYSICAL=false;
     }
     if (strcmp(palabra,"ALLVIRTEQ")==0)
     {
       if (strcmp(pal2,"true")==0) ALLVIRTEQ=true;
       else ALLVIRTEQ=false;
     }      
     if (strcmp(palabra,"SWITCH")==0) SWITCH=(int)valor;
     if (strcmp(palabra,"ROUTING")==0) ROUTING=(int)valor;
     if (strcmp(palabra,"DELFIFO")==0) DELFIFO=(int)valor;
     if (strcmp(palabra,"DELCROSS")==0) DELCROSS=(int)valor;
     if (strcmp(palabra,"DELCHANNEL")==0) DELCHANNEL=(int)valor;
     if (strcmp(palabra,"DELSWITCH")==0) DELSWITCH=(int)valor;
     if (strcmp(palabra,"PACKNUM")==0) PACKNUM=(int)valor;
     if (strcmp(palabra,"PACKLEN")==0) PACKLEN=(int)valor;
     if (strcmp(palabra,"PACKHEADLEN")==0) PACKHEADLEN=(int)valor;
     if (strcmp(palabra,"PACKPROD")==0) PACKPROD=valor;
     if (strcmp(palabra,"PACKNUMDUMMY")==0) PACKNUMDUMMY=(int)valor;
     if (strcmp(palabra,"USETRACE")==0) USETRACEnum=(int)valor;
     if (strcmp(palabra,"TRACENAME")==0) TRACENAME=(pal2);
     System.err.println("TRACENAME="+TRACENAME);
     if (strcmp(palabra,"NOREADLEN")==0) NOREADLENnum=(int)valor;
     if (strcmp(palabra,"JUSTONE")==0)
     {
       if (strcmp(pal2,"true")==0) JUSTONE=true;
       else JUSTONE=false;
     }
     if (strcmp(palabra,"SIMSTOPEMIT")==0) SIMSTOPEMIT=(int)valor;
     if (strcmp(palabra,"SIMUVAR")==0) SIMUVAR=(int)valor;
     if (strcmp(palabra,"LAVARINI")==0) LAVARINI=valor;
     if (strcmp(palabra,"LAVARFIN")==0) LAVARFIN=valor;
     if (strcmp(palabra,"PUNTOS")==0) PUNTOS=(int)valor;
     if (strcmp(palabra,"ESCALA")==0) ESCALA=(int)valor;
     if (strcmp(palabra,"SIMUVAR2")==0) SIMUVAR2=(int)valor;
     if (strcmp(palabra,"LAVARINI2")==0) LAVARINI2=valor;
     if (strcmp(palabra,"LAVARFIN2")==0) LAVARFIN2=valor;
     if (strcmp(palabra,"PUNTOS2")==0) PUNTOS2=(int)valor;
     if (strcmp(palabra,"ESCALA2")==0) ESCALA2=(int)valor;
     if (strcmp(palabra,"FINALSTATS")==0) FINALSTATS=(int)valor;
     if (strcmp(palabra,"STATPOINTS")==0) STATPOINTS=(int)valor;
   } 
 }
 if (USETRACEnum==0) USETRACE=false;
 else USETRACE=true;
 if (NOREADLENnum==0) NOREADLEN=false;
 else NOREADLEN=true;
} //preSimularCMD
    
    public static void preSimular()
    {
        if (esComando)
        {
            preSimularCMD();
            return;
        }
        // arreglamos botones
        Dibujo.PanelSimple.setEnabled(JUSTONE);
        Dibujo.bGoSimple.setEnabled(JUSTONE);
        //Dibujo.bPausa.setEnabled(JUSTONE);
        Dibujo.bPaso.setEnabled(JUSTONE);
        Dibujo.bPararSimple.setEnabled(JUSTONE);
        Dibujo.MostrarCheck.setEnabled(JUSTONE);
        Dibujo.VerNum.setEnabled(JUSTONE);
        Dibujo.RetrasoTxt.setEnabled(JUSTONE);

        Dibujo.PanelMultiple.setEnabled(!JUSTONE);
        Dibujo.bGoMultiple.setEnabled(!JUSTONE);
        Dibujo.bPararMultiple.setEnabled(!JUSTONE);

        
        // variables de simulacion
        // de los forms:
        if (Jsimured.NumPaqRad.isSelected())
            PACKNUM = Integer.parseInt(Jsimured.NumPaqTxt.getSelectedItem().toString());
        //numpaq = new Integer(NumPaqTxt.getSelectedItem().toString()).intValue();
        else
        //numpaq = new Integer(NumFlitTxt.getSelectedItem().toString()).intValue()*new Integer(DimTxt.getSelectedItem().toString()).intValue()*new Integer(NodosTxt.getSelectedItem().toString()).intValue()/new Integer(LonPaqTxt.getSelectedItem().toString()).intValue();
            PACKNUM = Integer.parseInt(Jsimured.NumFlitTxt.getSelectedItem().toString())*Integer.parseInt(Jsimured.DimTxt.getSelectedItem().toString())*Integer.parseInt(Jsimured.NodosTxt.getSelectedItem().toString())/Integer.parseInt(Jsimured.LonPaqTxt.getSelectedItem().toString());
        PACKLEN = Integer.parseInt(Jsimured.LonPaqTxt.getSelectedItem().toString());
        PACKHEADLEN = Integer.parseInt(Jsimured.LonCabTxt.getSelectedItem().toString());
        PACKPROD = Double.parseDouble(Jsimured.EmisionTxt.getSelectedItem().toString());
        DIMENSIONS = Integer.parseInt(Jsimured.DimTxt.getSelectedItem().toString());
        NODOSDIM = Integer.parseInt(Jsimured.NodosTxt.getSelectedItem().toString());
        LONBUFFER = Integer.parseInt(Jsimured.FifoTxt.getSelectedItem().toString());
        NUMVIRT = Integer.parseInt(Jsimured.VirtEjeTxt.getSelectedItem().toString());
        STATPOINTS=Integer.parseInt(Jsimured.StatPointsTxt.getSelectedItem().toString());
        if (Jsimured.RadContinuous.isSelected()) FINALSTATS=0;
        else FINALSTATS=1;
        if (Jsimured.GuardarCheck.isSelected()) SAVETOFILE=1;
        else SAVETOFILE=0;
        if (Jsimured.RadStopSim0.isSelected()) SIMSTOPEMIT=0;
        else SIMSTOPEMIT=1;
        LAVARINI2 = Double.parseDouble(Jsimured.Ini2Txt.getSelectedItem().toString());
        LAVARFIN2 = Double.parseDouble(Jsimured.Fin2Txt.getSelectedItem().toString());
        PUNTOS2 = Integer.parseInt(Jsimured.Puntos2Txt.getSelectedItem().toString());
        //SIMUVAR2= Esto ya se ha ido poniendo bien segun radiobutton
        SIMUVAR = 0; // es 0 pues no hay otra de momento
        if (JUSTONE) LAVARINI=Double.parseDouble(Jsimured.EmisionTxt.getSelectedItem().toString());
        else LAVARINI = Double.parseDouble(Jsimured.IniTxt.getSelectedItem().toString());
        LAVARFIN = Double.parseDouble(Jsimured.FinTxt.getSelectedItem().toString());
        PUNTOS = Integer.parseInt(Jsimured.PuntosTxt.getSelectedItem().toString());
        USETRACE=Jsimured.TrazasCheck.isSelected();
        if (USETRACE) TRACENAME = Jsimured.TrazaFichTxt.getSelectedItem().toString();
        else TRACENAME="";
        LANG=Jsimured.Idioma();
        VAR2TXT=Jsimured.Variable2Rad.getSelection().toString();
        if (Jsimured.BidirCheck.isSelected()) NUMDIR = 2;
        else NUMDIR = 1;
        if(Jsimured.AdelantoCheck.isSelected()) FORWARDING = 1;
        else FORWARDING = 0;       
        NOREADLEN=Jsimured.LongitCheck.isSelected();
        ALLVIRTEQ=Jsimured.jCheckBox1.isSelected();
        if (ALLVIRTEQ)
        {
            NUMVIRTINJ=NUMVIRTEJE=NUMVIRT;
        }
        else
        {
            NUMVIRTINJ=Integer.parseInt(Jsimured.VirtInjTxt.getSelectedItem().toString());
            NUMVIRTEJE=Integer.parseInt(Jsimured.VirtEjeTxt.getSelectedItem().toString());
        }
        PHYSICAL=Jsimured.FisicoCheck.isSelected();
        ROUTING=Jsimured.RoutingTxt.getSelectedIndex();
        SWITCH=Jsimured.ConmutTxt.getSelectedIndex();
        DELFIFO=Integer.parseInt(Jsimured.LatFifoTxt.getSelectedItem().toString());
        DELCROSS=Integer.parseInt(Jsimured.LatCrossTxt.getSelectedItem().toString());
        DELCHANNEL=Integer.parseInt(Jsimured.LatCanalTxt.getSelectedItem().toString());
        DELSWITCH=Integer.parseInt(Jsimured.LatConmutTxt.getSelectedItem().toString());
        PACKNUMDUMMY=Integer.parseInt(Jsimured.DummyUpPackTxt.getSelectedItem().toString());
        if (Jsimured.jRadioButton4.isSelected()) ESCALA=0;
        else ESCALA=1;
        if (Jsimured.jRadioButton6.isSelected()) ESCALA2=0;
        else ESCALA2=1;        
    }
    
    public static void postSimular()
    {
      // arreglamos botones
      Dibujo.PanelSimple.setEnabled(true);
      Dibujo.bGoSimple.setEnabled(true);
      //Dibujo.bPausa.setEnabled(false);
      Dibujo.bPaso.setEnabled(true);
      Dibujo.bPararSimple.setEnabled(false);      
      Dibujo.MostrarCheck.setEnabled(true);
      Dibujo.VerNum.setEnabled(true);
      Dibujo.RetrasoTxt.setEnabled(true);
      
      Dibujo.PanelMultiple.setEnabled(true);
      Dibujo.bGoMultiple.setEnabled(true);
      Dibujo.bPararMultiple.setEnabled(false);          
      //Dibujo.bGoMultiple.setText("Simular");
      //Dibujo.bGoMultiple.setText("Simular");
      setIdioma(Idioma());
    }
    
public static Runnable Simular = new Runnable() {   
        @Override
  public void run() {
  //public static int Continuar;
  //private static final int MAX_TH_NUM=10;
    final int MAX_TH_NUM=32;
    int NumThreads;
    Simula[] Hilos= new Simula[MAX_TH_NUM];
    Red[] LaRedGlobal = new Red[MAX_TH_NUM];
    int ndir, adelanto, numpaq, lonCabeza;
    int paqlong;
    int puntos, puntos2, plleva, plleva2;
    int pllevapost,ThrID,ThrIDpost;
    // int SimuVar, SimuVar2; /* Estas son las de F. Pardo
    //boolean SimuVar; // Pues solo hay una eleccion en VariableRad
    //String SimuVar2mia;
    int SimuVar2, SimuVar;
    int dimensiones, nodosdim, lonbuffer, nvirtuales;
    int noleelong;
    int valoraux;
    double tasaemitini;
    double [] tasaemit=new double[MAX_TH_NUM];
    double LaVar, LaVarIni, LaVarFin;
    double LaVar2, LaVarIni2, LaVarFin2;
    //Red redaux;
    String TrazaName, msgaux;
    File fichTraza;
    //, fichCSV = null;
    //FileWriter outCSV = null;
    PrintStream printCSV = null;
    OutputStream outCSV = null;
    //InputStreamReader in = null;
    String auxi;    
    boolean Interactive;
    int NumProcs;
    Runtime runtime;
    
    if(SimuStop == false){
      System.err.println("Esto no deberia aparecer. (Simulacion dentro de otra)");
    }
    SimuStop = false;    
    
    preSimular();
    
    Interactive=JUSTONE;
    numpaq=PACKNUM;
    paqlong=PACKLEN;
    tasaemitini=PACKPROD;
    dimensiones=DIMENSIONS;
    nodosdim=NODOSDIM;
    lonbuffer=LONBUFFER;
    nvirtuales=NUMVIRT;     
    
    runtime = Runtime.getRuntime();
    NumProcs = runtime.availableProcessors();
    if (NumProcs<1) NumProcs=2;
   
    System.err.println("");
    System.err.println("NUEVA SIMULACION ************************************************");
    Jsimured.SimuError=0;
    
    //Jsimured.SimuControl.Write(0); // podr�a ser 1, mostrar o paso a paso
    
    // ponemos en marcha el cronometro
    Cronometro crono = new Cronometro();
    crono.start();
    //Hecho = false; // Ponemos a false la vble para Interrumpir la Simulacion
    //lonCabeza = Integer.parseInt(Jsimured.LonCabTxt.getSelectedItem().toString());
    lonCabeza = PACKHEADLEN;
    // esto es para la version en forms
    // en linea de comandos esto se deber�a mandar a la salida estandar...
    if (esComando)
    {
        printCSV=System.out;
    }
    else
    {
    if(Jsimured.GuardarCheck.isSelected()){
      auxi="";
      if (Jsimured.ResultFichTxt.getSelectedItem()!=null)
        auxi = Jsimured.ResultFichTxt.getSelectedItem().toString();
      if(!auxi.equals("")){ // comparaba con " " error? igual poner comparar con null mejor, o length
        //fichCSV = new File(auxi);
        try{
            if(Jsimured.AnyadeCheck.isSelected())
                outCSV = new FileOutputStream(auxi, true);
            else
                outCSV = new FileOutputStream(auxi);
            printCSV=new PrintStream(outCSV);
          }
        catch(IOException e){
            postSimular();
            System.err.println("Exception en FileWriter");
            System.exit(1);
          }
      }
      else
        if(LANG== 0)
          System.err.println("No se ha especificado un fichero de Resultados.\n");
        else
          System.err.println("There is no Result File specification.\n");
    }
    }
    
    if (Interactive){
      // Interactiva
      SimuVar2 = 0;
      puntos2 = 1;
      LaVarIni2 = 0.0;
      LaVarFin2 = 0.0;
    } 
    else{
      // Multiple
      SimuVar2 = SIMUVAR2;
      //LaVarFin2 = new Double(Fin2Txt.getSelectedItem().toString()).doubleValue();
      if(SimuVar2>0) puntos2 = PUNTOS2;
      else puntos2 = 1;
      LaVarIni2 = LAVARINI2;
      LaVarFin2 = LAVARFIN2;
    }
    LaVar2 = LaVarIni2;
    if(SimuVar2>0){
        //msgaux=((JRadioButton)(Jsimured.Variable2Rad.getSelection())).toString();
        msgaux=VAR2TXT;
        //switch (SimuVar2){
        //  case 1: msgaux = "dimensiones";break;
        //  case 2: msgaux = "Nodos por Dimension";break;
        //  case 3: msgaux = "Numero de Canales Virtuales";break;
        //  case 4: msgaux = "Longitud del Buffer";break;
        //}
    }
    else msgaux = "Item"; 
    
    LaVarIni = LAVARINI;
    LaVarFin = LAVARFIN;
    SimuVar = SIMUVAR;
    if(Interactive){
        // Interactiva
        puntos = 1;
    }
    else{
        // Multiple
        puntos = PUNTOS;
    }

    //TrazaName ="";
    fichTraza = null;
    if(USETRACE){
      if(!TRACENAME.equals("")){
        TrazaName = TRACENAME;
        fichTraza = new File(TrazaName);
        puntos=1;
      } else
        if(LANG == 0)
          System.err.println("NO se ha especificado un fichero de Trazas");
        else
          System.err.println("There is NO trace file specification");
    }
    ndir=NUMDIR;
    adelanto=FORWARDING;

    // calcula el n�mero de threads optimo
    // The best number of threads is the maximum of puntos%NumThreads=0 which is less or equal than MAX_TH_NUM
    //NumThreads=0;
    //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (((puntos%NumThreads)!=0) || (NumThreads<NumProcs))) NumThreads++;
    //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (((NumThreads%NumProcs)!=0) || (NumThreads<NumProcs))) NumThreads++;
    //while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos) && (NumThreads<NumProcs)) NumThreads++;
    //if (puntos<=NumProcs) NumThreads=puntos+NumProcs;
    //else while ((NumThreads<MAX_TH_NUM) && (NumThreads<puntos)) NumThreads+=NumProcs;
    //NumThreads-=NumProcs;
    int idx=1;
    do
    {
	NumThreads=puntos/idx;
	idx++;
    } while (NumThreads>MAX_TH_NUM);    
    
    // outter loop
    plleva2 = 0;
    while ((plleva2 < puntos2) && (Jsimured.SimuControl.Read()!= 3)){ // FERTODO simucontrol deberia ser de simular
      plleva2++;
      msgaux = "Item";

      switch (SimuVar2){
        case 1: dimensiones = (int)LaVar2; msgaux="dim"; break;
        case 2: nodosdim = (int)LaVar2; msgaux="nodos"; break;
        case 3: lonbuffer = (int)LaVar2; msgaux="lonbuf"; break;
        case 4: nvirtuales = (int)LaVar2; msgaux="virt";
                if (ALLVIRTEQ)
                {
                    NUMVIRTINJ=nvirtuales;
                    NUMVIRTEJE=nvirtuales;
                }
                break;
      }
      //System.err.println("Simuvar2="+SimuVar2+" LaVar2="+LaVar2+" nvirtuales="+nvirtuales);
      // inner loop
      LaVar = LaVarIni;
      plleva = 0;
      pllevapost=0;
      //while((pllevapost < puntos) && (Jsimured.Continuar!=3)){
      while((pllevapost < puntos) && (Jsimured.SimuControl.Read()!=3)){        
        if (fichTraza!= null) puntos = 1; // solo uno
        if (NOREADLEN) noleelong=1;
        else noleelong=0;

        if(printCSV!= null){
          if ( ((FINALSTATS==1) && (plleva==0)) || ((FINALSTATS==0) && (NumThreads==1)) ){
            if(LANG == 0){
              //try{
                printCSV.print("\""+msgaux+"\";");
                printCSV.println("\"Ciclos\";\"Paquetes enviados\";\"Paquetes recibidos\";\"Tasa envio (flits/nodo/ciclo)\";\"Tasa recepcion (flits/nodo/ciclo)\";\"Latencia Cabecera\";\"Latencia Cabecera (sin bloqueos)\";\"Latencia Paquete\";\"Latencia Paquete (sin bloqueos)\";\"Ciclos de Bloqueo\";\"Camino Medio (nodos)\"");
              //} 
              //catch(IOException e){
              //    postSimular();
              //    System.err.println("Exception al escribir en CSV");
              //    System.exit(1);
              //}
            } else if(Jsimured.LANG == 1){
              //try{
                  //System.out.
                printCSV.print("\""+msgaux+"\";");
                printCSV.println("\"Cycles\";\"Delivered packets\";\"Received packets\";\"Delivery rate (flits/node/cycle)\";\"Arrival rate (flits/node/cycle)\";\"Head Latency\";\"Head Latency (no locks)\";\"Packet Latency\";\"Packet Latency (no locks)\";\"Lock cycles\";\"Average Path (nodes)\"");
              //} 
              //catch(IOException e){
              //  postSimular();
              //  System.err.println("Exception al escribir en CSV "+e.toString());
              //  System.exit(1);
              //}
            }
          }
        }
        
        // aqu� van terminando
        if (plleva>=NumThreads)
        {
            ThrIDpost=pllevapost%NumThreads;
            //wait for thread end
            try {
                Hilos[ThrIDpost].join();
            }
            catch (InterruptedException ignore)
            {
                System.err.println("Exception en llamada a Join simula "+ignore.toString());
            }
            
            if (!esComando) Jsimured.Dibuja(LaRedGlobal[ThrIDpost]); // da error si multiple
            
            pllevapost++;
            valoraux=pllevapost; //-NumTo+i+1;
            if (!Interactive)
                if (SimuVar2>0)
                    valoraux=(int)LaVar2;
            Jsimured.Muestra("----------------------------------");
            // if(isInteractive)
            Jsimured.Muestra("Item: "+pllevapost+", Variable: "+tasaemit[ThrIDpost]+", Threads: "+NumThreads+", Processors: "+NumProcs);

            Jsimured.ShowStats(LaRedGlobal[ThrIDpost],0);
            //Jsimured.ShowStats(0);
            //if (Jsimured.RadFinal.isSelected())
            if (FINALSTATS==1)
                Jsimured.PrintStatsToFile(LaRedGlobal[ThrIDpost],printCSV, valoraux,0);
            Jsimured.Muestra("");
        }
        
        // Aqu� se van lanzando
        if (plleva<puntos)
        {
            ThrID=plleva%NumThreads;
            plleva++;
            valoraux=plleva;
            if (!Interactive)
                if (SimuVar2>0)
                    valoraux=(int)LaVar2;
            if (SimuVar==0) tasaemit[ThrID]=LaVar;
            else tasaemit[ThrID]=tasaemitini;
                       /* try {
                            // creo que en java no es necesario esto pues los destruye cuando le parece
                            //redaux=LaRedGlobal[ThrID];
                            //LaRedGlobal[ThrID]=NULL;  //previene accesos indeseados mientras se destruye y se vuelve a crear
                            //if (redaux!=NULL) delete redaux;
                wait(1);} catch (InterruptedException ex) {
                            Logger.getLogger(Jsimured.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
            LaRedGlobal[ThrID] = new Red(
                dimensiones,
                nodosdim,
                nvirtuales,
                NUMVIRTINJ,
                NUMVIRTEJE,
                lonbuffer,
                ndir,
                adelanto,
                PHYSICAL, 
                ROUTING,
                SWITCH,   
                DELFIFO,     
                DELCROSS,    
                DELCHANNEL,   
                DELSWITCH,
                PACKNUMDUMMY   
             );
             //Jsimured.LaRedGlobal = redaux; // Ahora ya esta creada

            ////////////////////////////////////
            /////////// SIMULA /////////////////
            ////////////////////////////////////
            Hilos[ThrID] = new Simula(LaRedGlobal[ThrID],fichTraza, printCSV, paqlong, lonCabeza, numpaq+LaRedGlobal[ThrID].NumPackDummy, (float)tasaemit[ThrID], noleelong, valoraux);
            Hilos[ThrID].start();
            // Simula(LaRedGlobal[ThrID],fich,f,paqlong,StrToInt((AnsiString)LonCabTxt->Text),numpaq+LaRedGlobal[ThrID]->NumPackDummy,tasaemit[ThrID],&Continuar,noleelong,valoraux);
            if (puntos>1){
                if (ESCALA==0) // linear
                    LaVar = LaVar+(LaVarFin-LaVarIni)/(puntos-1);
                else
                    LaVar = LaVar*Math.pow(LaVarFin/LaVarIni, 1.0/(puntos-1));
            }
        }
      }
      if (puntos2> 1){
          if (ESCALA2==0)
            LaVar2 = LaVar2+(LaVarFin2-LaVarIni2)/(puntos2-1);
          else
            LaVar2 = LaVar2*Math.pow((LaVarFin2/LaVarIni2), 1.0/(puntos2-1));
      }
    }
    
    // que Simula devuelva un valor en otro sitio para detectar esto...
    if ((SimuError== 0) && (SimuControl.Read()!=3)) {            
      if(LANG == 0)
        Jsimured.Muestra("SIMULACION COMPLETADA CON EXITO.");
      else if(LANG == 1)
        Jsimured.Muestra("SIMULATION SUCCESSFULLY FINISHED.");
    }
    else{
      if(LANG == 0)
        Jsimured.Muestra("SIMULACION INTERRUMPIDA. Ver mensajes anteriores y la Red");
      else if(LANG == 1)
        Jsimured.Muestra("SIMULATION INTERRUPTED. See last messages and the network.");
    }
    
    // ojo que si es la salida estandar igual da error
    if(outCSV!= null){
      try{
          printCSV.flush();
          outCSV.flush();
          if (printCSV!=System.out)
          {
              printCSV.close();
              outCSV.close(); // igual no va pues el otro igual lo cierra ya
          }
      }
      catch(IOException e){
        postSimular();
        System.err.println("Exception al cerrar filewriter out "+e.toString());
        System.exit(1);
      }
    }
    SimuControl.Write(0);
    crono.end();
    
    //System.out.println(Jsimured.LaRedGlobal.NumPackTrans+" de "+Jsimured.LaRedGlobal.NumPackEmit+"FIN SIMULACION ************************************************");
    System.err.println("FIN SIMULACION ************************************************");
    System.err.println("");
    if (LANG==0)
        Muestra("Tiempo de simulación ("+crono.duration()+" s): "+crono.hour()+" h, "+crono.min()+" m, "+crono.sec()+" s.");
    else if (LANG==1)
        Muestra("Simulation time: ("+crono.duration()+" s): "+crono.hour()+" h, "+crono.min()+" m, "+crono.sec()+" s.");
    Muestra(" ");
    //System.err.println("Tiempo de simulacion: "+(crono.duration())+" segundos");
    System.runFinalization(); // si hubiera finalizes esto los acaba
    System.gc();  // garbage collector
    postSimular();
    SimuStop = true;
    if (esComando) System.exit(0);
}

};
// END Simular();


    /**
     * Esta funcion anyade al TextArea de salida de informacion
     * la cadena de texto que se le pasa
     */
    public static synchronized void Muestra(String a){ // las llamadas desde Simula deben estar sincronizadas para evitar conflictos
      if (esComando)
      {
          //System.err.println(a);
          PrintWriter bw;
            //try {
                bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.err)));
                bw.println(a);
                bw.flush();
            //} catch (UnsupportedEncodingException ex) {
            //    Logger.getLogger(Jsimured.class.getName()).log(Level.SEVERE, null, ex);
            //}

      }
      else
      {
          jTextArea1.append(a);
          jTextArea1.append("\n");
          jTextArea1.setCaretPosition(jTextArea1.getText().length());
      }
    }
    
    public static synchronized void PrintStatsToFile(Red LaRedGlobal, PrintStream out, int valor, int set) {
      int realTrans;
      if (set==0) realTrans=LaRedGlobal.NumPackTrans[set]-LaRedGlobal.NumPackDummyIni;
      else realTrans=LaRedGlobal.NumPackTrans[set];
      if (set==1) LaRedGlobal.CyclesEmit[set]=LaRedGlobal.Cycles[set];
      if ( (realTrans>0) &&
          (LaRedGlobal.CyclesEmit[set]>0) &&
          (LaRedGlobal.Cycles[set]>0) && (out!=null) )
      {
          String realFormat="%.10g"; // 10 digitos significativos, si no se pone nada considera 6
        //try{
          out.print(valor+";"+LaRedGlobal.Cycles[0]+";"+LaRedGlobal.NumPackEmit[0]+";"+LaRedGlobal.NumPackTrans[0]);
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.EmiFlits[set])/LaRedGlobal.CyclesEmit[set]/LaRedGlobal.NumNodes));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.TransFlits[set])/LaRedGlobal.Cycles[set]/LaRedGlobal.NumNodes));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.LatHead[set])/realTrans));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.LatHead[set]-LaRedGlobal.LatBlock[set])/realTrans));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.LatTrans[set])/realTrans));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.LatTrans[set]-LaRedGlobal.LatBlock[set])/realTrans));
          out.print(";"+String.format(realFormat,1.0*(LaRedGlobal.LatBlock[set])/realTrans));
          out.println(";"+String.format(realFormat,1.0*(LaRedGlobal.PathNodes[set])/realTrans));
          //out.write("\n");
        //} catch(IOException e){
        //  System.err.println("Exception al escribir en fichero out "+e.toString());
        //  System.exit(1);
        //}
      }
    }
    /**
     * Funcion para mostrar las estadisticas
     * llama a Muestra para mostrar en el TextArea
     */
    public static void ShowStats(Red LaRedGlobal, int set) {
      double latmed;
      int realTrans;
      double Cyc,PackGen,PackSent,PackRec,ProdGen,ProdSent,ProdRec;
      double LatHead,LatHeadNo,LatPack,LatPackNo,StdDev,CycBlock,MeanPathNod,MeanPathCha;
      if (set==0)
        realTrans=LaRedGlobal.NumPackTrans[set]-LaRedGlobal.NumPackDummyIni;
      else
        realTrans=LaRedGlobal.NumPackTrans[set];
      latmed=1.0*(LaRedGlobal.LatTrans[set])/realTrans;
      Cyc=(LaRedGlobal.Cycles[set]);
      PackGen=(LaRedGlobal.NumPackCreated[set]);
      PackSent=(LaRedGlobal.NumPackEmit[set]);
      PackRec=(LaRedGlobal.NumPackTrans[set]);
      ProdGen=ProdSent=ProdRec=LatHead=LatHeadNo=LatPack=LatPackNo=StdDev=CycBlock=MeanPathNod=MeanPathCha=0;
      if (LaRedGlobal.Cycles[set]>0) {
        if (LaRedGlobal.CyclesCreation[set]>0)
          ProdGen=(1.0*LaRedGlobal.CreatedFlits[set]/LaRedGlobal.CyclesCreation[set]/LaRedGlobal.NumNodes);
        if (LaRedGlobal.CyclesEmit[set]>0)
          ProdSent=(1.0*(LaRedGlobal.EmiFlits[set])/LaRedGlobal.CyclesEmit[set]/LaRedGlobal.NumNodes);
        ProdRec=(1.0*(LaRedGlobal.TransFlits[set])/LaRedGlobal.Cycles[set]/LaRedGlobal.NumNodes);
        if (realTrans>0) {
          LatHead=(1.0*(LaRedGlobal.LatHead[set])/realTrans);
          LatHeadNo=(1.0*(LaRedGlobal.LatHead[set]-LaRedGlobal.LatBlock[set])/realTrans);
          LatPack=(latmed);
          LatPackNo=(1.0*(LaRedGlobal.LatTrans[set]-LaRedGlobal.LatBlock[set])/realTrans);
          StdDev=(Math.sqrt(1.0*(LaRedGlobal.LatTrans2[set])/realTrans-latmed*latmed));
          CycBlock=(1.0*(LaRedGlobal.LatBlock[set])/realTrans);
          MeanPathNod=(1.0*LaRedGlobal.PathNodes[set]/realTrans);
          MeanPathCha=(1.0*LaRedGlobal.PathNodes[set]/realTrans-1);
        }
      }
      Muestra("--------------------------------------------");
      if (Idioma()==0) {
        Muestra("Ciclos: "+Cyc);
        Muestra("Paquetes Generados: "+PackGen);
        Muestra("Paquetes Enviados:  "+PackSent);
        Muestra("Paquetes Recibidos: "+PackRec);
        if (LaRedGlobal.Cycles[set]>0) {
          Muestra("---------------------------------------");
//     if (LaRedGlobal->CyclesEmit[set]) Muestra("Productividad Objetivo (flits/ciclo):      "+FloatToStr((double)LaRedGlobal->EmiFlits[set]/LaRedGlobal->CyclesEmit[set]));
          if (LaRedGlobal.CyclesCreation[set]>0)
            Muestra("Tasa  Creación (flits/ciclo/nodo): "+ProdGen);
          //Muestra("Productividad (flits/ciclo):       "+FloatToStr((double)LaRedGlobal->EmiFlits[set]/LaRedGlobal->Cycles[set]));
          if (LaRedGlobal.CyclesEmit[set]>0)
            Muestra("Tasa de  Envio (flits/ciclo/nodo): "+ProdSent);
          Muestra("Tasa Recepción (flits/ciclo/nodo): "+ProdRec);
          if (realTrans>0) {
            Muestra("---------------------------------------");
            Muestra("Latencia Cabeza:                 "+LatHead);
            Muestra("Latencia Cabeza (sin bloqueos):  "+LatHeadNo);
            Muestra("Latencia Paquete:                "+LatPack);
            Muestra("Desviación Estándar:             "+StdDev);
            Muestra("Latencia Paquete (sin bloqueos): "+LatPackNo);
            Muestra("Ciclos de Bloqueo en Red:        "+CycBlock);
            //Muestra("Ciclos de Bloqueo en el Emisor:  "+FloatToStr((double)(LaRedGlobal->LatBlockEmit[set])/realTrans));
//       Muestra("----- en el Proc. Receptor:      "+IntToStr(LaRedGlobal->LatBlockRecep/LaRedGlobal->NumPackTrans));
            //Muestra("Latencia Total del Paquete:      "+FloatToStr((double)(LaRedGlobal->LatTotal[set])/realTrans));
            Muestra("--------------------------------");
            Muestra("Camino medio (nodos):        "+MeanPathNod);
            Muestra("Camino medio (canales):      "+MeanPathCha);
          }
        }
      } else if (Idioma()==1) // ingles
      {
        Muestra("Cycles: "+Cyc);
        Muestra("Created  Packets: "+PackGen);
        Muestra("Sent     Packets: "+PackSent);
        Muestra("Received Packets: "+PackRec);
        if (LaRedGlobal.Cycles[set]>0) {
          Muestra("---------------------------------------");
//     if (LaRedGlobal->CyclesEmit[set]) Muestra("Target Productivity (flits/cycle):      "+FloatToStr((double)LaRedGlobal->EmiFlits[set]/LaRedGlobal->CyclesEmit[set]));
          if (LaRedGlobal.CyclesCreation[set]>0)
            Muestra("Creation rate (flits/cycle/node): "+ProdGen);
          //Muestra("Productivity (flits/cycle):       "+FloatToStr((double)LaRedGlobal->EmiFlits[set]/LaRedGlobal->Cycles[set]));
          if (LaRedGlobal.CyclesEmit[set]>0)
            Muestra("Delivery rate (flits/cycle/node): "+ProdSent);
          Muestra("Arrival  rate (flits/cycle/node): "+ProdRec);
          if (realTrans>0) {
            Muestra("---------------------------------------");
            Muestra("Head Latency on the Net:            "+LatHead);
            Muestra("Head Latency on the Net (no locks): "+LatHeadNo);
            Muestra("Packet Latency:                     "+LatPack);
            Muestra("Standard Deviation:                 "+StdDev);
            Muestra("Packet Latency (no locks):          "+LatPackNo);
            Muestra("Lock Cycles on the Net:             "+CycBlock);
//       Muestra("Lock Cycles at the Emitter:         "+FloatToStr((double)(LaRedGlobal->LatBlockEmit[set])/realTrans));
//       Muestra("----- en el Proc. Receptor:         "+IntToStr(LaRedGlobal->LatBlockRecep/LaRedGlobal->NumPackTrans));
//       Muestra("Total Packet Latency:               "+FloatToStr((double)(LaRedGlobal->LatTotal[set])/realTrans));
            Muestra("--------------------------------");
            Muestra("Average Path (nodes):      "+MeanPathNod);
            Muestra("Average Path (channels):   "+MeanPathCha);
          }
        }
      }
      Muestra(" ");
    }
    
    
    /**
     * Funci???n para Dibujar los paquetes en movimiento junto con la red
     * @param LaRed red a dibujar
     */
    public static synchronized void Dibuja(Red LaRed){
      int[] puntosx = new int[3];
      int[] puntosy = new int[3];
      int i,j,k;
      int FifoLonX, FifoLonY, LonX, LonY;
      int di, dim, nv;
      //int x, y;
      //int kkk;
      
      int ANCHOX, ANCHOY, LONCX, LONCY, AnchoNodo, AltoNodo;
      int ANCHOYint, LonXint, Xint, Yint;
      Color ElColorIn, ElColorOut;
      Device dispo;
      Graphics  NodoBaseBmp, NodoBmp;
      int DibujoWidth, DibujoHeight;
      // igual es m�s apropiado el BufferedImage en vez de Image
      Image ImgBuf, ImgNodoBaseBmp, ImgNodoBmp;
      Graphics ImgDibujo;
      if(LaRed == null) return; //Si no hay red no se dibuja nada
      LONCX = 4;
      LONCY = 4;
      DibujoWidth = AreaDibujo.getWidth(); //Cogemos los valores del area
      DibujoHeight = AreaDibujo.getHeight();
      AnchoNodo = (int)(DibujoWidth/LaRed.NumNodesDim);
      AltoNodo = (int)(DibujoHeight/LaRed.NumNodesDim);
      ImgDibujo = AreaDibujo.getGraphics();
      /** Nos creamos 3 im�genes para dibujar el nodo, el nodo con cambios,
       * y la imagen diferencia
       */
      
      // He puesto ese componente pero igual se puede poner cualquier otro, lo importante es poner uno est�tico
      ImgBuf = jTextArea1.createImage(DibujoWidth, DibujoHeight);
      ImgNodoBaseBmp = jTextArea1.createImage(AnchoNodo, AltoNodo);
      ImgNodoBmp = jTextArea1.createImage(AnchoNodo, AltoNodo);
            
      NodoBaseBmp = ImgNodoBaseBmp.getGraphics();
      NodoBmp = ImgNodoBmp.getGraphics();
      
      FifoLonX = (int)(AnchoNodo/3); // Longitudes de las colas + canal
      FifoLonY = (int)(AltoNodo/3);
      ANCHOX =(int)((AnchoNodo/3-4)/(LaRed.Directions*LaRed.NumVirtuals)); // Ancho de los flits
      ANCHOY = (int)((AltoNodo/3-4)/(LaRed.Directions*LaRed.NumVirtuals));
      LonX = (int)((FifoLonX-LONCX/2)/LaRed.NumFlitBuf);
      LonY = (int)((FifoLonY-LONCY/2)/LaRed.NumFlitBuf);
      LONCX = (FifoLonX-LonX*LaRed.NumFlitBuf)*2;
      LONCY = (FifoLonY-LonY*LaRed.NumFlitBuf)*2;
      Xint = (int)(AnchoNodo/2.5);
      Yint= (int)(AltoNodo/2.5);
      LonXint = (int)((AnchoNodo-2*Xint)/LaRed.NumFlitBuf);
      ANCHOYint = (int)(AltoNodo/12); // a ojo
      NodoBaseBmp.setColor(Color.WHITE);
      NodoBaseBmp.fillRect(0,0,DibujoWidth, DibujoHeight);
      NodoBaseBmp.setColor(Color.BLACK);
      NodoBaseBmp.drawRect(0, 0,DibujoWidth, DibujoHeight);
      NodoBaseBmp.setColor(Color.WHITE);
      NodoBaseBmp.fillRect(0,0,AnchoNodo, AltoNodo);
      //NodoBaseBmp.setColor(Color.BLACK);
      //NodoBaseBmp.drawRect(0, 0,AnchoNodo, AltoNodo);
      NodoBaseBmp.setColor(Color.RED);
      NodoBaseBmp.drawRect(LONCX/2, LONCY/2,AnchoNodo-LONCX/2 -LONCX/2,AltoNodo-LONCY/2-LONCY/2);
      NodoBaseBmp.setColor(Color.BLACK);
      NodoBaseBmp.drawRect(FifoLonX, FifoLonY,(AnchoNodo-FifoLonX)-FifoLonX, (AltoNodo-FifoLonY)-FifoLonY);
      NodoBaseBmp.setColor(Color.WHITE);
      for (j = 0; j <LaRed.NumBuf; j++){
        dim = (int)(j/(LaRed.Directions*LaRed.NumVirtuals));
        di = (int)((j%(LaRed.Directions*LaRed.NumVirtuals))/LaRed.NumVirtuals);
        nv = (int)(j%(LaRed.NumVirtuals));
        // Primero los canales, q no los pongo
        // Ahora las flechas
        NodoBaseBmp.setColor(Color.BLACK);
        if (dim == 0){
          if(di == 0){
            // eje X
            puntosx[0] = AnchoNodo-FifoLonX-1;
            puntosx[1] = AnchoNodo-FifoLonX-1;
            puntosx[2] = AnchoNodo-FifoLonX-1-(int)(AnchoNodo/24);
            puntosy[0] = (int)(AltoNodo/2-(nv+0.8)*ANCHOY);
            puntosy[1] = (int)(AltoNodo/2-(nv+0.2)*ANCHOY);
            puntosy[2] = (int)(AltoNodo/2-(nv+0.5)*ANCHOY);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            puntosx[0] = FifoLonX+(int)(AnchoNodo/24);
            puntosx[1] = FifoLonX+(int)(AnchoNodo/24);
            puntosx[2] = FifoLonX;
            puntosy[0] = (int)(AltoNodo/2-(nv+0.8)*ANCHOY);
            puntosy[1] = (int)(AltoNodo/2-(nv+0.2)*ANCHOY);
            puntosy[2] = (int)(AltoNodo/2-(nv+0.5)*ANCHOY);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            
          } else{
            puntosx[0] = AnchoNodo-FifoLonX-1-(int)(AnchoNodo/24);
            puntosx[1] = AnchoNodo-FifoLonX-1-(int)(AnchoNodo/24);
            puntosx[2] = AnchoNodo-FifoLonX-1;
            puntosy[0] = (int)(AltoNodo/2+(nv+0.8)*ANCHOY);
            puntosy[1] = (int)(AltoNodo/2+(nv+0.2)*ANCHOY);
            puntosy[2] = (int)(AltoNodo/2+(nv+0.5)*ANCHOY);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            puntosx[0] = FifoLonX;
            puntosx[1] = FifoLonX;
            puntosx[2] = FifoLonX+(int)(AnchoNodo/24);
            puntosy[0] = (int)(AltoNodo/2+(nv+0.8)*ANCHOY);
            puntosy[1] = (int)(AltoNodo/2+(nv+0.2)*ANCHOY);
            puntosy[2] = (int)(AltoNodo/2+(nv+0.5)*ANCHOY);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
          }
        } else if(dim == 1){
          if (di == 0){
            puntosx[0] =(int) (AnchoNodo/2-(nv+0.8)*ANCHOX);
            puntosx[1] = (int)(AnchoNodo/2-(nv+0.2)*ANCHOX);
            puntosx[2] = (int)(AnchoNodo/2-(nv+0.5)*ANCHOX);
            puntosy[0] = AltoNodo-FifoLonY-1-(int)(AltoNodo/24);
            puntosy[1] = AltoNodo-FifoLonY-1-(int)(AltoNodo/24);
            puntosy[2] = AltoNodo-FifoLonY-1;
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            puntosx[0] = (int)(AnchoNodo/2-(nv+0.8)*ANCHOX);
            puntosx[1] = (int)(AnchoNodo/2-(nv+0.2)*ANCHOX);
            puntosx[2] = (int)(AnchoNodo/2-(nv+0.5)*ANCHOX);
            puntosy[0] = FifoLonY;
            puntosy[1] = FifoLonY;
            puntosy[2] = FifoLonY+(int)(AltoNodo/24);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
          } else{
            puntosx[0] =(int) (AnchoNodo/2+(nv+0.8)*ANCHOX);
            puntosx[1] = (int)(AnchoNodo/2+(nv+0.2)*ANCHOX);
            puntosx[2] = (int)(AnchoNodo/2+(nv+0.5)*ANCHOX);
            puntosy[0] = AltoNodo-FifoLonY-1;
            puntosy[1] = AltoNodo-FifoLonY-1;
            puntosy[2] = AltoNodo-FifoLonY-1-(int)(AltoNodo/24);
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            puntosx[0] = (int)(AnchoNodo/2+(nv+0.8)*ANCHOX);
            puntosx[1] = (int)(AnchoNodo/2+(nv+0.2)*ANCHOX);
            puntosx[2] = (int)(AnchoNodo/2+(nv+0.5)*ANCHOX);
            puntosy[0] = FifoLonY+(int)(AltoNodo/24);
            puntosy[1] = FifoLonY+(int)(AltoNodo/24);
            puntosy[2] = FifoLonY;
            NodoBaseBmp.fillPolygon(puntosx, puntosy, 3);
            
          }
        }
        // Ahora Los Flits de Cada Cola
        NodoBaseBmp.setColor(Color.BLACK);
        for(k=0; k <LaRed.NumFlitBuf; k++){
          if(dim==0){
            //eje x
            if(di==0){
              NodoBaseBmp.drawRect((AnchoNodo-FifoLonX+(LaRed.NumFlitBuf-k-1)*LonX), (AltoNodo/2-(nv+1)*ANCHOY), LonX, ANCHOY);
              NodoBaseBmp.drawRect((FifoLonX-(k+1)*LonX), (AltoNodo/2-(nv+1)*ANCHOY), LonX, ANCHOY);
            } else{
              NodoBaseBmp.drawRect((AnchoNodo-FifoLonX+k*LonX), (AltoNodo/2+(nv)*ANCHOY), LonX, ANCHOY);
              NodoBaseBmp.drawRect((FifoLonX-(LaRed.NumFlitBuf-k)*LonX),(AltoNodo/2+(nv)*ANCHOY), LonX,ANCHOY);
            }
          } else if(dim == 1){
            if (di == 1){
              NodoBaseBmp.drawRect((AnchoNodo/2+(nv)*ANCHOX), (AltoNodo-FifoLonY+(LaRed.NumFlitBuf-k-1)*LonY), ANCHOX, LonY);
              NodoBaseBmp.drawRect((AnchoNodo/2+(nv)*ANCHOX), (FifoLonY-(k+1)*LonY), ANCHOX, LonY);
            } else{
              NodoBaseBmp.drawRect((AnchoNodo/2-(nv+1)*ANCHOX), (FifoLonY-(LaRed.NumFlitBuf-k)*LonY),ANCHOX, LonY);
              NodoBaseBmp.drawRect((AnchoNodo/2-(nv+1)*ANCHOX), (AltoNodo-FifoLonY+k*LonY), ANCHOX, LonY);
            }
          }
        }
      }
      // Ahora los internos
      for(k=0; k<LaRed.NumFlitBuf; k++){
        for(i=0; i<LaRed.NumVirtuals; i++){
          NodoBaseBmp.drawRect((Xint+k*LonXint),(AltoNodo-Yint-(i+1)*ANCHOYint/LaRed.NumVirtuals), (Xint+(k+1)*LonXint)-(Xint+k*LonXint),(AltoNodo-Yint-i*ANCHOYint/LaRed.NumVirtuals)-(AltoNodo-Yint-(i+1)*ANCHOYint/LaRed.NumVirtuals) );
          NodoBaseBmp.drawRect((Xint+k*LonXint),(Yint+i*ANCHOYint/LaRed.NumVirtuals),(Xint+(k+1)*LonXint)-(Xint+k*LonXint),(Yint+(i+1)*ANCHOYint/LaRed.NumVirtuals)-(Yint+i*ANCHOYint/LaRed.NumVirtuals));
        }
      }
      
      // Ahora superponemos las diferencias
      for(i=0; i<LaRed.NumNodes; i++) {
        NodoBmp.drawImage(ImgNodoBaseBmp, 0, 0,null);
        for(j=0; j<LaRed.NumBuf; j++){
          dim =(int)(j/(LaRed.Directions*LaRed.NumVirtuals));
          di = (int)(j%(LaRed.Directions*LaRed.NumVirtuals)/LaRed.NumVirtuals);
          nv = (int)(j%LaRed.NumVirtuals);
          
          // Primero los canales
          dispo =(LaRed.Channels[LaRed.ChannelId(i,dim,di, nv)]);
          if(dispo.Paq!= null){
            if((dispo.Busy)&&(((Channel)dispo).Turn== dispo.MyBuf%LaRed.NumVirtuals))
              ElColorOut = new Color(dispo.Paq.Color);
            else
              ElColorOut = Color.GRAY;
            NodoBmp.setColor(ElColorOut);
            if(dim==0){
              if(di == 0){
                NodoBmp.fillRect(0, 1+AltoNodo/2-(nv+1)*ANCHOY, LONCX/2, ANCHOY-1);
              } else{
                NodoBmp.fillRect(1+(AnchoNodo-LONCX/2), 1+(AltoNodo/2+(nv)*ANCHOY), LONCX/2, ANCHOY-1);
              }
            } else if(dim == 1){
              if(di == 1){
                NodoBmp.fillRect(1+(AnchoNodo/2+(nv)*ANCHOX), 0, ANCHOX-1, (LONCY/2));
              } else{
                NodoBmp.fillRect(1+(AnchoNodo/2 -(nv+1)*ANCHOX), 1+(AltoNodo-LONCY/2), ANCHOX-1, LONCY/2);
              }
            }
          }
          dispo=LaRed.Channels[LaRed.ChannelId(LaRed.Trans(i,dim,(di==0)?1:0),dim,di,nv)]; // TOMA GEROMA!
          if(dispo.Paq!= null){
            if((dispo.Busy)&&(((Channel)dispo).Turn == dispo.MyBuf%LaRed.NumVirtuals))
              ElColorIn = new Color(dispo.Paq.Color);
            else
              ElColorIn = Color.GRAY;
            NodoBmp.setColor(ElColorIn);
            if(dim == 0){
              if(di == 0){
                NodoBmp.fillRect(1+AnchoNodo-LONCX/2, 1+AltoNodo/2-(nv+1)*ANCHOY, LONCX/2, ANCHOY-1);
              } else{
                NodoBmp.fillRect(0, 1+AltoNodo/2+(nv)*ANCHOY, LONCX/2, ANCHOY-1);
              }
            } else if(dim == 1){
              if(di == 1){
                NodoBmp.fillRect(1+(AnchoNodo/2+(nv)*ANCHOX),1+AltoNodo-LONCY/2, ANCHOX-1, LONCY/2);
              } else{
                NodoBmp.fillRect(1+(AnchoNodo/2-(nv+1)*ANCHOX), 0, ANCHOX-1, LONCY/2);
              }
            }
          }
          
          // Ahora los flits de cada Buffer
          int size =(int)Math.floor(Math.min(ANCHOY,LonX)*0.8); 
          Font f = new Font("fuente", Font.PLAIN, size);
          NodoBmp.setFont(f);

          for(k=0; k<LaRed.NumFlitBuf;k++){
            NodoBmp.setColor(Color.BLACK);
            //for(kkk=0; kkk<2; kkk++)
            //{
                //if (kkk==0) dispo = LaRed.Nodes[i].Buf_in[j].Fifo[k];
                //else dispo = LaRed.Nodes[i].Buf_out[j].Fifo[k];
             dispo = LaRed.Nodes[i].Buf_in[j].Fifo[k];
             if(dispo.Paq!=null){
              if(dispo.Busy)
                ElColorIn=new Color(dispo.Paq.Color);
              else
                ElColorIn = Color.GRAY;
              NodoBmp.setColor(ElColorIn);
              if(dim == 0){ // eje X
                if (di== 0){
                  NodoBmp.fillRect(1+(AnchoNodo-FifoLonX+(LaRed.NumFlitBuf-k-1)*LonX), 1+AltoNodo/2-(nv+1)*ANCHOY, -1+LonX, -1+ANCHOY);
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), 1+(AnchoNodo-FifoLonX+(LaRed.NumFlitBuf-k-1)*LonX)+LonX/4,(AltoNodo/2-(nv+1)*ANCHOY)+3*ANCHOY/4);
                    NodoBmp.setColor(ElColorIn);
                  }
                  //Aqui va lo del numero
                } else{
                  NodoBmp.fillRect(1+(FifoLonX-(LaRed.NumFlitBuf-k)*LonX), 1+(AltoNodo/2+(nv)*ANCHOY),-1+LonX,-1+ANCHOY);
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value),1+(FifoLonX-(LaRed.NumFlitBuf-k)*LonX)+LonX/4, (AltoNodo/2+(nv)*ANCHOY)+3*ANCHOY/4);
                    NodoBmp.setColor(ElColorIn);
                  }
                  // y lo del numero
                }
              } else if(dim == 1){ // eje Y
                if(di == 1){
                  NodoBmp.fillRect(1+(AnchoNodo/2+(nv)*ANCHOX), 1+(AltoNodo-FifoLonY+(LaRed.NumFlitBuf-k-1)*LonY),-1+ANCHOX, -1+LonY);
                  // y el numerito
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (1+AnchoNodo/2+nv*ANCHOX)+ANCHOX/4, (AltoNodo-FifoLonY+(LaRed.NumFlitBuf-k-1)*LonY)+3*LonY/4);
                    NodoBmp.setColor(ElColorIn);
                  }
                  
                } else{
                  NodoBmp.fillRect(1+(AnchoNodo/2-(nv+1)*ANCHOX), 1+FifoLonY-(LaRed.NumFlitBuf-k)*LonY,-1+ANCHOX, -1+LonY);
                  // y el numerito
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (1+AnchoNodo/2-(nv+1)*ANCHOX)+ANCHOX/4,(FifoLonY-(LaRed.NumFlitBuf-k)*LonY)+3*LonY/4);
                    NodoBmp.setColor(ElColorIn);
                  }
                }
              }
            }
            dispo = LaRed.Nodes[i].Buf_out[j].Fifo[k];
            if (dispo.Value == 0)
              dispo.Busy = false;
            if(dispo.Paq!= null){
              if(dispo.Busy)
                ElColorOut=new Color(dispo.Paq.Color);
              else
                ElColorOut = Color.GRAY; // Puede q sea Color.LTGRAY;
              NodoBmp.setColor(ElColorOut);
              if(dim==0){ // eje X
                if(di==0){ // 0 sentido negativo
                  //NodoBmp.fillRoundRect((FifoLonX-(k+1)*LonX), (AltoNodo/2-(nv+1)*ANCHOY), (1+FifoLonX-k*LonX)-(FifoLonX-(k+1)*LonX),(AltoNodo/2-(nv)*ANCHOY)-(AltoNodo/2-(nv+1)*ANCHOY),80,80 );
                  NodoBmp.fillRect(1+(FifoLonX-(k+1)*LonX), 1+(AltoNodo/2-(nv+1)*ANCHOY), -1+LonX,-1+ANCHOY);
                  // y el numerito
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (FifoLonX-(k+1)*LonX)+LonX/4,(AltoNodo/2-(nv+1)*ANCHOY)+3*ANCHOY/4);
                    NodoBmp.setColor(ElColorOut);
                  }
                } else{ // sentido positivo
                  //NodoBmp.fillRoundRect((-1+AnchoNodo-FifoLonX+k*LonX), (AltoNodo/2+(nv)*ANCHOY), (AnchoNodo-FifoLonX+(k+1)*LonX)-(-1+AnchoNodo-FifoLonX+k*LonX),(AltoNodo/2+(nv+1)*ANCHOY)-(AltoNodo/2+(nv)*ANCHOY),80,80);
                  NodoBmp.fillRect((1+AnchoNodo-FifoLonX+k*LonX), 1+(AltoNodo/2+(nv)*ANCHOY), -1+LonX,-1+ANCHOY);
                  // y el numerito
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (AnchoNodo-FifoLonX+k*LonX)+LonX/4,(AltoNodo/2+(nv)*ANCHOY)+3*ANCHOY/4);
                    NodoBmp.setColor(ElColorOut);
                  }
                }
              } else if(dim == 1){ // eje Y
                if(di == 1){ //1
                  //NodoBmp.fillRoundRect((AnchoNodo/2+(nv)*ANCHOX), (FifoLonY-(k+1)*LonY),(AnchoNodo/2+(nv+1)*ANCHOX)-(AnchoNodo/2+(nv)*ANCHOX), (1+FifoLonY-k*LonY)-(FifoLonY-(k+1)*LonY),80,80);
                  NodoBmp.fillRect(1+(AnchoNodo/2+(nv)*ANCHOX), 1+(FifoLonY-(k+1)*LonY),-1+ANCHOX, -1+LonY);
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (AnchoNodo/2+nv*ANCHOX)+ANCHOX/4,(FifoLonY-(k+1)*LonY)+3*LonY/4);
                    NodoBmp.setColor(ElColorOut);
                  }
                  // y el numerico
                } else{
                  //NodoBmp.fillRoundRect((AnchoNodo/2-(nv+1)*ANCHOX),(-1+AltoNodo-FifoLonY+k*LonY),(AnchoNodo/2-(nv)*ANCHOX)-(AnchoNodo/2-(nv+1)*ANCHOX), (AltoNodo-FifoLonY+(k+1)*LonY)-(-1+AltoNodo-FifoLonY+k*LonY),80,80);
                  NodoBmp.fillRect(1+(AnchoNodo/2-(nv+1)*ANCHOX),1+(AltoNodo-FifoLonY+k*LonY),-1+ANCHOX, -1+LonY);
                  // y el numerico
                  if((vernum == 1)&&(dispo.Value>0)){
                    NodoBmp.setColor(Color.BLACK);
                    NodoBmp.drawString(Integer.toString(dispo.Value), (AnchoNodo/2-(nv+1)*ANCHOX)+ANCHOX/4,(AltoNodo-FifoLonY+k*LonY)+3*LonY/4);
                    NodoBmp.setColor(ElColorOut);
                  }
                  
                }
              }             
            }
          }
        }
        
        // Los Buffers internos
        int size =(int)Math.floor(Math.min(ANCHOYint/LaRed.NumVirtProcInj,LonXint)*0.8); 
        Font f = new Font("fuente", Font.PLAIN, size);
        NodoBmp.setFont(f);

        for(k=0; k<LaRed.NumFlitBuf; k++){
          for(j=0; j<LaRed.NumVirtProcInj; j++){
            dispo = LaRed.Nodes[i].Buf_in[LaRed.NumBuf+j].Fifo[k];
            if(dispo.Paq!= null){
              if(dispo.Busy)
                ElColorIn = new Color(dispo.Paq.Color);
              else
                ElColorIn = Color.GRAY; // podria ser Color.LIGHT_GRAY
              NodoBmp.setColor(ElColorIn);
              NodoBmp.fillRect(1+(Xint+k*LonXint),1+(AltoNodo-Yint-(j+1)*ANCHOYint/LaRed.NumVirtProcInj), LonXint-1,ANCHOYint/LaRed.NumVirtProcInj-1);
              if((vernum == 1)&&(dispo.Value>0)){
                NodoBmp.setColor(Color.BLACK);
                NodoBmp.drawString(Integer.toString(dispo.Value), 1+(Xint+k*LonXint)+LonXint/4, 1+(AltoNodo-Yint-(j+1)*ANCHOYint/LaRed.NumVirtProcInj)+3*ANCHOYint/LaRed.NumVirtProcEje/4);
              }
            }
          }
          for(j=0; j<LaRed.NumVirtProcEje; j++){
            dispo = LaRed.Nodes[i].Buf_out[LaRed.NumBuf+j].Fifo[k];
            if(dispo.Paq!= null){
              if(dispo.Busy)
                ElColorIn = new Color(dispo.Paq.Color);
              else
                ElColorIn = Color.GRAY;
              NodoBmp.setColor(ElColorIn);
              NodoBmp.fillRect(1+(Xint+k*LonXint), 1+(Yint+j*ANCHOYint/LaRed.NumVirtProcEje), LonXint-1, ANCHOYint/LaRed.NumVirtProcEje-1);
              if((vernum == 1)&&(dispo.Value>0)){
                NodoBmp.setColor(Color.BLACK);
                NodoBmp.drawString(Integer.toString(dispo.Value), 1+(Xint+k*LonXint)+LonXint/4,+1+(Yint+j*ANCHOYint/LaRed.NumVirtProcEje)+3*ANCHOYint/LaRed.NumVirtProcEje/4);
              }
            }
          }
        }
        if(ImgDibujo!=null)
          ImgDibujo.drawImage(ImgNodoBmp,(i%LaRed.NumNodesDim)*AnchoNodo, DibujoHeight-(i/LaRed.NumNodesDim+1)*AltoNodo, null);
      }
  ///    if(ImgDibujo!= null)
//        AreaDibujo.paintComponents(ImgDibujo);
      
      //ImgNodoBaseBmp.flush();
      //ImgNodoBmp.flush();
      //ImgBuf.flush();
      
      //if(NodoBaseBmp!=null)
      //  NodoBaseBmp.dispose();
      //if(NodoBmp!= null)
      //  NodoBmp.dispose();
      //if(ImgDibujo!=null)
      //  ImgDibujo.dispose();
    }
    
    
    /**
     * Funcion para mostrar Errores y/o Excepciones por pantalla
     */
    //public void ShowError(String Texto){
    //  Error VentanaError = new Error(this, Texto);
    //}
    
    /**
     * Funcion para Crear los Graficos
     */
    public int leeDatosCSV(){
      String linea;
      String aux;
      //ArrayList DatosTotales = new ArrayList();
      String [] Variables = null;
      Simulaciones = new ArrayList();
      ArrayList Bloque = new ArrayList();
      
      FileReader fich = null;
      BufferedReader in = null;
      
      
      int simuNum = 0;
      
      //final Font titleFont=new Font("Default",Font.BOLD,14);
      //Label title;
      if (GrafTxt.getSelectedItem()==null) return 1;
      aux = GrafTxt.getSelectedItem().toString();
      try{
        fich = new FileReader(aux);
      } catch(FileNotFoundException e){
        System.err.println("File not found: "+aux); //+e);
        return 1;
      }
      
      if(fich!= null){
        in = new BufferedReader(fich);
      }
      
      try{
        while((linea = in.readLine())!= null){
          if (linea.startsWith("\"")){
            Variables = linea.split(";");
            simuNum++;
            Bloque = new ArrayList();
            Simulaciones.add(Bloque);
          } else {
            linea=linea.replace(",",".");  
            String [] datos = linea.split(";");
            //Vector simulacion = new Vector();
            ArrayList simulacion = new ArrayList();
            for (int i = 0; i < datos.length; i++)
              simulacion.add(Float.parseFloat(datos[i]));
            Bloque.add(simulacion);
            //DatosTotales.add(simulacion);
          }
        }
      }catch(IOException e){
        System.err.println("Parse error in CSV file throws exception: "+e);
        return 1;
      }
      
      //System.out.println("Numero de Variables:"+Variables.length);
      //System.out.println("Numero de Variables en X: "+Xtxt.getSelectedIndex());
      
      if (Xtxt.getSelectedIndex()<0){
        Xtxt.removeAllItems();
        Ytxt.removeAllItems();
        for(int i = 0; i < Variables.length; i++){
          Xtxt.addItem(Variables[i]);
          Ytxt.addItem(Variables[i]);
        }
        /*   Seleccionamos por defecto el eje X e Y */
        if (RadFinal.isSelected()){
          Xtxt.setSelectedIndex(5);
          Ytxt.setSelectedIndex(8);
        } else{
          Xtxt.setSelectedIndex(3);
          Ytxt.setSelectedIndex(4);
        }
      }
      return 0; // todo bien.
    }
    
    /**
     * Funcion para Seleccionar un eje X
     * @param eje indice del eje X a seleccionar
     */
    public void SeleccionaEjeX(int eje){
      ejeX = eje;
    }
    /**
     * Esta funci???n nos devuelve el eje X
     * seleccionado
     */
    public static int DevuelveEjeX(){
      return (ejeX);
    }
    /**
     * Funcion para Seleccionar un eje Y
     * @param eje indice del eje Y a seleccionar
     */
    public void SeleccionaEjeY(int eje){
      ejeY = eje;
    }
    
    /**
     * Esta funci???n nos devuelve el eje X
     * seleccionado
     */
    public static int DevuelveEjeY(){
      return (ejeY);
    }
    
    public void pintaGraf()
    {   
        XYSeriesCollection dataset = new XYSeriesCollection();
        ArrayList sims=Jsimured.Simulaciones;
        int indiX = Jsimured.DevuelveEjeX();
        int indiY = Jsimured.DevuelveEjeY();
        XYSeries serie;
        for (int i=0; i < sims.size(); i++){
            // el false es para que no ordene (por defecto es true y ordena de menor a mayor la x)
            serie = new XYSeries("Object "+i,false);
            ArrayList bloques = (ArrayList)(sims.get(i));
            if (bloques.size()<2)
            {
                if (i==0) return;
                else continue; // gr�fica con menos de dos puntos
            }
            // recorremos las simulaciones del bloque
            //double [] valoresY = new double[bloques.size()];
            //double [] valoresX = new double[bloques.size()];
            float param=0;
            for (int j=0; j < bloques.size(); j++)
            {       
                //Vector sim = (Vector)(bloques.get(j));                
                ArrayList sim = (ArrayList)(bloques.get(j));                
                //valoresY[j] = (Float)(sim.get(indiY));
                //valoresX[j] = (Float)(sim.get(indiX));

                serie.add((Float)(sim.get(indiX)),(Float)(sim.get(indiY)));
                
                //Param[simuNum] = (Float)sim.get(0);               
                param=(float)(sim.get(0));
            }
            //simuNum++;
            if (sims.size()>1) serie.setKey(param);
            else serie.setKey("");
            dataset.addSeries(serie);
        }
 
        String msgaux = (String)Xtxt.getItemAt(0);
        msgaux=msgaux.replace("\"","");

        String titulo =  "Simulación Simple";
        if (sims.size()>1)
        {
            if (msgaux.equals("dim")) titulo="Dimensions";
            else if (msgaux.equals("nodos")) titulo="Nodes";
            else if (msgaux.equals("lonbuf")) titulo="Length Buffers";
            else if (msgaux.equals("virt")) titulo="Virtual Channels";
        }
        String ejex=Jsimured.Xtxt.getSelectedItem().toString().replace("\"","");
        String ejey=Jsimured.Ytxt.getSelectedItem().toString().replace("\"","");
        // create a chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
            titulo,
            ejex,
            ejey,
            dataset // URLs?
        );
        
        // cambiamos cosas:
        XYPlot plot = chart.getXYPlot();// referencia
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); //linea y simbolo
        // No funciona nada de esto, tengo que calcular el minimo y poner lo de lowerbound, no hay otra
        //plot.getRangeAxis().setLowerBound(30.0);
        //((NumberAxis)(plot.getRangeAxis())).setAutoRangeIncludesZero(Boolean.FALSE);
        //plot.getRangeAxis().setAutoRange(Boolean.TRUE);
        //plot.getRangeAxis().setRangeWithMargins(dataset.getDomainBounds(Boolean.FALSE)); // domain es el ejex
        plot.getRangeAxis().setRangeWithMargins(dataset.getRangeBounds(Boolean.FALSE)); // sin domain son los y
        // esto tampoco va
        //if (plot.getSeriesCount()==1)
        //for (int j=0;j<plot.getSeriesCount();j++)
        //    renderer.setSeriesVisibleInLegend(j, Boolean.FALSE); // no va
        /*
        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        */
        // grid
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        // cambiando el plot:
        plot.setOutlinePaint(Color.BLACK); // contorno
        //plot.setOutlineStroke(new BasicStroke(2.0f)); // grosor contorno
        plot.setBackgroundPaint(Color.WHITE); // fondo!
        
        plot.setRenderer(renderer);

        // create and display a frame...
        ChartFrame frame = new ChartFrame("SimuRed", chart);
        frame.pack();
        frame.setVisible(true);
    }
        


    
    /**
     * @param args the command line arguments
     * ESTA ES LA FUNCION MAIN
     */
    // I used to comment this function for the applet, but in fact it seems not necessary
    public static void main(String args[]) {
        //System.out.println(args.length);
        //for (int i=0;i<args.length;i++) System.out.println(args[i]);
        //System.out.flush();
        //final String noargs[];
        //noargs=args;
        boolean preCom=false;
        final boolean esComando;
        if (args.length>0)
            if (args[0].equals("-c")) preCom=true;
        //preCom=true; // for debuggin purposes
        esComando=preCom;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
        //System.out.println(noargs.length);
        //for (int i=0;i<noargs.length;i++) System.out.println(noargs[i]);
                //System.err.println(esComando);
                System.err.println("command line usage: java -jar jsimured.jar [-c]");
                System.err.println("-c: execute as batch simulation instead of interactive windows");
                System.err.println("    in batch mode, specify simulations options from a configuration file by redirecting the std input,");
                System.err.println("    in batch mode, specify a result file by redirecting the std output.");
                System.err.println("Example:");
                System.err.println("java -jar jsimured.jar -c < simured.conf > stats.csv");
                System.err.println();
                if (esComando)
                    System.err.println("Launched as a command, no windows or interaction is possible.");
                else
                    System.err.println("Launched in interactive window mode, see main window for options and start simulation.");
                System.err.println();
                
                new Jsimured(false,esComando).setVisible(!esComando); // false=es una aplicacion
            }
        });
    }
    
    public class SimuControlclass
    {
        private int SimuControlval=0;
        
        public synchronized void Write(int valor)
        {
            SimuControlval=valor;
            notifyAll();
        }
        
        public synchronized int Read()
        {
            return(SimuControlval);
        }
        
        public synchronized int TestWait(Red LaRed)
        {
            if (SimuControlval==1)
            {
                Jsimured.Dibuja(LaRed);
                try {
                    wait();
                }
                catch(InterruptedException e) {}
            }
            if (SimuControlval==2) SimuControlval=1; // aunque escribo no notifico pues es 1
            return(SimuControlval);
        }
    }
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    static javax.swing.JCheckBox AdelantoCheck;
    static javax.swing.JCheckBox AnyadeCheck;
    static javax.swing.JCheckBox BidirCheck;
    static javax.swing.JComboBox ConmutTxt;
    static javax.swing.JComboBox DimTxt;
    static javax.swing.JComboBox DummyUpPackTxt;
    static javax.swing.JComboBox EmisionTxt;
    public javax.swing.ButtonGroup EscalaRad;
    public javax.swing.ButtonGroup EscalaRad2;
    static javax.swing.JComboBox FifoTxt;
    static javax.swing.JComboBox Fin2Txt;
    static javax.swing.JComboBox FinTxt;
    static javax.swing.JCheckBox FisicoCheck;
    static javax.swing.JComboBox GrafTxt;
    static javax.swing.JPanel GraficoTab;
    static javax.swing.JPanel GroupBoxConmut;
    static javax.swing.JPanel GroupBoxEncam;
    static javax.swing.JPanel GroupBoxGenAut;
    static javax.swing.JPanel GroupBoxGenPaq;
    static javax.swing.JPanel GroupBoxPaq;
    static javax.swing.JPanel GroupBoxPrim;
    static javax.swing.JPanel GroupBoxRed;
    static javax.swing.JPanel GroupBoxRet;
    static javax.swing.JPanel GroupBoxSec;
    static javax.swing.JPanel GroupBoxStats;
    static javax.swing.JCheckBox GuardarCheck;
    public javax.swing.ButtonGroup IdiomaRad;
    static javax.swing.JComboBox Ini2Txt;
    static javax.swing.JComboBox IniTxt;
    static javax.swing.JComboBox LatCanalTxt;
    static javax.swing.JComboBox LatConmutTxt;
    static javax.swing.JComboBox LatCrossTxt;
    static javax.swing.JComboBox LatFifoTxt;
    static javax.swing.JComboBox LonCabTxt;
    static javax.swing.JComboBox LonPaqTxt;
    static javax.swing.JCheckBox LongitCheck;
    public javax.swing.JPanel MiscTab;
    static javax.swing.JComboBox NodosTxt;
    static javax.swing.JComboBox NumFlitTxt;
    static javax.swing.JRadioButton NumFlitsRad;
    static javax.swing.JRadioButton NumPaqRad;
    static javax.swing.JComboBox NumPaqTxt;
    public javax.swing.JPanel PaquetesTab;
    static javax.swing.JComboBox Puntos2Txt;
    static javax.swing.JComboBox PuntosTxt;
    static javax.swing.JRadioButton RadContinuous;
    static javax.swing.JRadioButton RadFinal;
    static javax.swing.JRadioButton RadStopSim0;
    static javax.swing.JRadioButton RadStopSim1;
    public javax.swing.JPanel RedTab;
    static javax.swing.JComboBox ResultFichTxt;
    static javax.swing.JComboBox RoutingTxt;
    static javax.swing.JPanel SimulacionTab;
    static javax.swing.JComboBox StatPointsTxt;
    public javax.swing.ButtonGroup StatsRadGr;
    public javax.swing.ButtonGroup StopSimuRadGr;
    static javax.swing.JComboBox TrazaFichTxt;
    static javax.swing.JCheckBox TrazasCheck;
    private javax.swing.JScrollPane TxtSal;
    public static javax.swing.ButtonGroup Variable2Rad;
    static javax.swing.JRadioButton VariableRad;
    static javax.swing.JComboBox VirtEjeTxt;
    static javax.swing.JComboBox VirtInjTxt;
    static javax.swing.JComboBox VirtualesTxt1;
    static javax.swing.JComboBox Xtxt;
    static javax.swing.JComboBox Ytxt;
    public javax.swing.ButtonGroup btGroupGenAutom;
    static javax.swing.JButton jButton1;
    static javax.swing.JButton jButton2;
    static javax.swing.JButton jButton3;
    static javax.swing.JButton jButton5;
    static javax.swing.JButton jButton6;
    static javax.swing.JCheckBox jCheckBox1;
    static javax.swing.JLabel jLabel1;
    static javax.swing.JLabel jLabel10;
    static javax.swing.JLabel jLabel11;
    static javax.swing.JLabel jLabel12;
    static javax.swing.JLabel jLabel13;
    static javax.swing.JLabel jLabel14;
    static javax.swing.JLabel jLabel15;
    static javax.swing.JLabel jLabel16;
    static javax.swing.JLabel jLabel17;
    private static javax.swing.JLabel jLabel18;
    private static javax.swing.JLabel jLabel19;
    static javax.swing.JLabel jLabel2;
    static javax.swing.JLabel jLabel20;
    static javax.swing.JLabel jLabel21;
    static javax.swing.JLabel jLabel23;
    static javax.swing.JLabel jLabel24;
    static javax.swing.JLabel jLabel25;
    static javax.swing.JLabel jLabel26;
    static javax.swing.JLabel jLabel27;
    static javax.swing.JLabel jLabel3;
    static javax.swing.JLabel jLabel32;
    static javax.swing.JLabel jLabel33;
    static javax.swing.JLabel jLabel4;
    static javax.swing.JLabel jLabel5;
    static javax.swing.JLabel jLabel6;
    static javax.swing.JLabel jLabel7;
    static javax.swing.JLabel jLabel8;
    static javax.swing.JLabel jLabel9;
    static javax.swing.JPanel jPanel1;
    static javax.swing.JPanel jPanel13;
    static javax.swing.JPanel jPanel14;
    static javax.swing.JPanel jPanel15;
    static javax.swing.JPanel jPanel16;
    static javax.swing.JPanel jPanel19;
    static javax.swing.JPanel jPanel2;
    static javax.swing.JPanel jPanel20;
    static javax.swing.JRadioButton jRadioButton10;
    static javax.swing.JRadioButton jRadioButton11;
    static javax.swing.JRadioButton jRadioButton12;
    static javax.swing.JRadioButton jRadioButton13;
    static javax.swing.JRadioButton jRadioButton14;
    public static javax.swing.JRadioButton jRadioButton4;
    public static javax.swing.JRadioButton jRadioButton5;
    static javax.swing.JRadioButton jRadioButton6;
    static javax.swing.JRadioButton jRadioButton7;
    static javax.swing.JRadioButton jRadioButton8;
    static javax.swing.JRadioButton jRadioButton9;
    static javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    
}
