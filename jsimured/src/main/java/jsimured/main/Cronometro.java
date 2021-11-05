/*
 * Cronometro.java
 * Class to control simulation elapsed time
 * It uses the system clock to calculate the elapsed time
 */
package jsimured.main;

final class Cronometro {
  /** Tiempo al inicip */  
  private long start; 
  /** Tiempo al terminar */
  private long end;

  /** Constructor: Pone a 0 los contadores */
  public Cronometro() {
    reset();
    }

  /** Actualiza el contador de tiempo start */
  public void start() {
    System.gc();
    start = System.currentTimeMillis();
    }

  /** Actualiza el contador de tiempo end */
  public void end() {
    System.gc();
    end = System.currentTimeMillis();
    }

  /** Calcula la diferencia entre el end y start */
  public long duration(){
    return((end-start)/1000);
    }

  public int hour()
  {
    return((int)(duration()/60/60));     
  }
  
  public int min()
  {
    return((int)(duration()/60)%60);
  }
  
  public int sec()
  {
    return((int)(duration()%3600));     
  }

  /** Resetea los contadores */
  public void reset() {
    start = 0;  end   = 0;
    }
}
    

