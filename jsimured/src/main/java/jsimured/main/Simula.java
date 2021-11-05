/*
 * Simula.java
 * Runs a simulations with given parameters
 */
package jsimured.main;

import jsimured.red.Packet;
import jsimured.red.Red;
import java.io.*;
import java.util.Random;

public class Simula extends Thread{
  //private static simuredJava princ;
//     private InputStreamReader fich;
  private Red LaRedLocal;
  private File fich;
  private PrintStream f;
  //private Simular ThreadPadre;
  private int paqlong;
  private int cablong;
  private int Tope;
  private float TasaEmit;
  //private int continuar;
  private int noleelong;
  private int valorval;
  
  private Packet auxpaq;
  
  /** Creates a new instance of Simula */
  //public Simula(simuredJava Princ, File Fich, FileWriter F,int Paqlong, int Cablong, int tope, float tasaEmit, int Continuar, int Noleelong, int ValorVal, Simular hilo ){
  public Simula(Red laRedLocal, File Fich, PrintStream F,int Paqlong, int Cablong, int tope, float tasaEmit, int Noleelong, int ValorVal){
    setName("Simula"); // es para ponerle un nombre al hilo
    // setPriority(MAX_PRIORITY);
    //princ = Princ;
    LaRedLocal=laRedLocal;
    fich = Fich;
    f = F;
    paqlong = Paqlong;
    cablong = Cablong;
    Tope = tope;
    TasaEmit = tasaEmit;
    //continuar = Continuar;
    noleelong = Noleelong;
    valorval = ValorVal;
    //ThreadPadre = hilo;
  }
  /** Funcion que simula un conjunto de paquetes
   * con las condiciones que se le pasan
   */
    @Override
  public void run(){
    int orig=0, dest=0;
    int CicloFich,FinFich,milongit=0,CicloPack,IdPack;
    int SucRead;
    //String CicloTxt,LongId1,LongId2;
    int packpunt;
    float Tasaux;    // paquete/ciclo
    String linea;
    boolean contsimu;
    int myCycles;
    //int cont = 0;
    
    // acoples para leer fich
    InputStreamReader talfich;
    BufferedReader in;
  
    Random random = new Random(); // aqu� se puede poner una semilla si quiero siempre lo mismo, sino, sale random
    
    //princ.Dibuja(princ.LaRedLocal);
    
    
    if (fich!=null){
      try{
        talfich = new FileReader(fich);
        in = new BufferedReader(talfich);
        
        if (in!=null) {
          // first trace pass to see dependent packets
          Tope=0;
          FinFich=0;
          while (FinFich==0) {
            CicloPack=-1;
            CicloFich=-1;
            IdPack=-1;
            if ((linea=in.readLine())!=null) {
              if (!linea.startsWith("#"))  // comentario
              {
                milongit=paqlong+cablong;
                String[] result = linea.split("\\s");
                SucRead=result.length;
                //SucRead=sscanf(linea,"%s %d %d %s %s",CicloTxt,orig,dest,LongId1,LongId2);
                if (SucRead<3) FinFich=1;
                else
                {
                  FinFich=0;
                  if (result[0].startsWith("p")) CicloPack=Integer.parseInt(result[0].substring(1));
                  else CicloFich=Integer.parseInt(result[0]);
                  orig=Integer.parseInt(result[1]);
                  dest=Integer.parseInt(result[2]);
                  if (SucRead==4){
                    if (result[3].startsWith("p")) IdPack=Integer.parseInt(result[3].substring(1));
                    else milongit=Integer.parseInt(result[3]);
                  }
                  if (SucRead==5){
                    if (result[4].startsWith("p")) IdPack=Integer.parseInt(result[4].substring(1));
                    else milongit=Integer.parseInt(result[4]);
                  }
                  if (noleelong!=0) milongit=paqlong+cablong;
                }
              }
            } else {
              FinFich=1;
            }
            if (FinFich==0) {
              if (CicloFich>=0) Tope++;
              if (CicloPack>=0) {
                Tope++;
                // Creates dependent packet
                LaRedLocal.InsertTracePack(CicloPack,orig,dest,milongit,IdPack);
              }
            }
          }
          in.close();
          talfich.close();
        }
      }catch(IOException e){
        System.err.println("Exception closing filewriter out: \n"+e.getMessage());
        System.exit(1);
      }
    }
// reopen fich to read the remaining traces
    in = null;
    if (fich!=null)
      try{
        talfich = new FileReader(fich);
        in = new BufferedReader(talfich);
      } catch(IOException e){
        System.err.println("Exception creating filereader out2");
        System.exit(1);
      }

    TasaEmit =(float) 1.0*TasaEmit*LaRedLocal.NumNodes/paqlong; // Solo consideramos datos
    //packpunt=Tope/Integer.parseInt(princ.StatPointsTxt.getSelectedItem().toString());
    packpunt=Tope/Jsimured.STATPOINTS;
    CicloFich=-1;
    CicloPack=-1;
    IdPack=-1;
    FinFich=0;
    myCycles=0;
    contsimu=true;
    do
    {
      //while(((princ.LaRedLocal.NumPackTrans<princ.LaRedLocal.NumPackEmit) || (FinFich == 0))&&(!ThreadPadre.getHecho())){
      //n paquetes y se para
      /*if(ThreadPadre.getSleep()){
        try{
          this.currentThread().wait();
        }catch(java.lang.InterruptedException e){
          System.err.println("Exception en Wait de Simula: "+e);
        }
      }
      */
        
      // Crea los paquetes de este ciclo
      // Packet creation for this cycle
      if(fich==null){
        Tasaux = TasaEmit;
        if ((LaRedLocal.NumPackCreated[0]-LaRedLocal.NumPackEmit[0])<500*LaRedLocal.NumNodes*LaRedLocal.NumVirtProcInj)
          while(Tasaux>0){
            if (random.nextFloat()<Tasaux){ // nextFloat esta entre 0.0 y 1.0
              orig = random.nextInt(LaRedLocal.NumNodes);
              do {
                dest = random.nextInt(LaRedLocal.NumNodes);
              }  while (dest == orig);
              auxpaq=new Packet(LaRedLocal, paqlong+cablong, orig, dest,-1);
            }
            Tasaux--;
          }
      }
      else{
        //Paquetes de un fichero
        if(FinFich == 0){
          do{
            try{
              if (CicloFich==-1){
                IdPack=-1;
                if ((linea=in.readLine())!=null) {
                  if (!linea.startsWith("#"))  // comentario
                  {
                    milongit=paqlong+cablong;
                    String[] result = linea.split("\\s");
                    SucRead=result.length;
                    //SucRead=sscanf(linea,"%s %d %d %s %s",CicloTxt,orig,dest,LongId1,LongId2);
                    if (SucRead<3) FinFich=1;
                    else{
                      FinFich=0;
                      if (result[0].startsWith("p")) CicloPack=Integer.parseInt(result[0].substring(1));
                      else CicloFich=Integer.parseInt(result[0]);
                      orig=Integer.parseInt(result[1]);
                      dest=Integer.parseInt(result[2]);
                      if (SucRead==4){
                        if (result[3].startsWith("p")) IdPack=Integer.parseInt(result[3].substring(1));
                        else milongit=Integer.parseInt(result[3]);
                      }
                      if (SucRead==5){
                        if (result[4].startsWith("p")) IdPack=Integer.parseInt(result[4].substring(1));
                        else milongit=Integer.parseInt(result[4]);
                      }
                      if (noleelong!=0) milongit=paqlong+cablong;
                    }
                  }
                } else {
                  FinFich=1;
                }
              }
              if (FinFich==1) break;
              // creates packet
              if (CicloFich==myCycles) {
                auxpaq=new Packet(LaRedLocal,milongit,orig,dest,IdPack);
                CicloFich=-1;
              } else if (CicloPack>=0) {
                CicloPack=-1; // continues
              }
            } catch(IOException e){
              System.err.println("Exception closing filewriter out in do loop");
              System.exit(1);
            }
          } while(CicloFich<=myCycles);
          if(FinFich == 1){
            LaRedLocal.CyclesCreation[0]=LaRedLocal.Cycles[0];
          }
        }
      }
      ///////////////////////////////
      ///////////////////////////////
      try{
        LaRedLocal.RunCycle(); // Mueve paquetes por la red
      }catch(InterruptedException ie){System.out.println("Error en RunCycle Red "+ie);}
      myCycles++;
      ////////////////////////////////
      ////////////////////////////////
      //if((princ.LaRedLocal.BloqueoTotal) && (princ.LaRedLocal.Packets!=null)){
      //  princ.Muestra("BLOQUEO MORTAL");
      //  princ.Continuar = 3;
      //  break;
      //}
      
      //Controlo el flujo segun continuar
      /*
      switch(Jsimured.Continuar){
        case 0: //No muestra nada y sigue
          break; //nada;
        case 1: //Muestra pero se para
          //if(!princ.NoDibujes)
            Jsimured.Dibuja(LaRedLocal);
          while(Jsimured.Continuar == 1) ; // se supone que esa variable cambia externamente...
          if(Jsimured.Continuar<3) Jsimured.Continuar = 1;
          break;
        case 2: // No se utiliza, es para salir del bucle anterior
          //   princ.Dibuja(princ.LaRedLocal);
          //continuar = 1;
          //System.out.println("Continuar nunca vale 2");
          break;
        case 3: // Se termina directamente
          //if(!princ.NoDibujes)
           // princ.Dibuja(princ.LaRedLocal); // it is better to do it outside
          if(LaRedLocal.Cycles[0]>1)
            LaRedLocal.Cycles[0] = LaRedLocal.Cycles[0]-2;
          //Ajusta Ciclos
          return;
        default: return;        
      }
      */
      if (Jsimured.SimuControl.TestWait(LaRedLocal)==3) // es el unico caso que hago algo, si no, sigue
      {
          if(LaRedLocal.Cycles[0]>1)
            LaRedLocal.Cycles[0] = LaRedLocal.Cycles[0]-2;
          //Ajusta Ciclos
          //return;
          contsimu=false;
      }
      
      // finish condition
      if ((LaRedLocal.NumPackTrans[0]>=Tope) && (Jsimured.SIMSTOPEMIT==0)) contsimu=false;
      if ((LaRedLocal.NumPackEmit[0]>=Tope)  && (Jsimured.SIMSTOPEMIT==1)) contsimu=false;
      //if (ThreadPadre.getHecho()) contsimu=false;
      // Update counters
      if (LaRedLocal.NumPackEmit[0]<LaRedLocal.NumPackCreated[0])
        LaRedLocal.CyclesEmit[0]=0;
      else if (LaRedLocal.CyclesEmit[0]==0)
        LaRedLocal.CyclesEmit[0]=LaRedLocal.Cycles[0];
      // saves results to a file
      if ((Jsimured.FINALSTATS==0) && (Jsimured.SAVETOFILE==1)
      && (LaRedLocal.NumPackDummy==0)
      && (LaRedLocal.NumPackTrans[1]>=packpunt) ) {
        Jsimured.PrintStatsToFile(LaRedLocal,f,valorval,1);
        LaRedLocal.ResetStatSet(1);
      }
      if ( (LaRedLocal.BloqueoTotal) && (LaRedLocal.Packets!=null) ) {
        Jsimured.Muestra("DEADLOCK FOUND!!");
        Jsimured.SimuError=3;
        break;
      }
    } while (contsimu);
    if (LaRedLocal.Cycles[0]>1) LaRedLocal.Cycles[0]=LaRedLocal.Cycles[0]-2; // Ajusta ciclos
    if (LaRedLocal.CyclesEmit[0]==0) LaRedLocal.CyclesEmit[0]=LaRedLocal.Cycles[0];
    if (LaRedLocal.CyclesCreation[0]==0) LaRedLocal.CyclesCreation[0]=LaRedLocal.Cycles[0];
    if (in!=null)
    {
        try {
            in.close();
        }
        catch (IOException ex) {
            System.err.println("Error traying to close in: "+ ex.toString());
        }
    }
    // may be I should close talfich and in
    //princ.Dibuja(princ.LaRedLocal); // better outside 
  }
  
  /*
  public synchronized void Despierte(){
    ThreadPadre.setNotSleep();
    notify();
 }
  */

}
