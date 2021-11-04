package Red;

/*
 * CrossBar.java
 *
 * Created on 27 de septiembre de 2004, 17:10
 * Modified may 2012
 */
/**
 *
 * @author  Oscar, Fernando
 * Clase CrossBar
 * Define el crossbar
 */
public class CrossBar {
    private Red LaRed;
    /** Vector de CrossSwitch, uno por cada buffer de salida */
    public CrossSwitch[] Output;
    /** Creates a new instance of CrossBar
     * @param Red La red asociada
     */
    public CrossBar(Red unared) {
        int i;
        LaRed = unared;
        Output = new CrossSwitch[LaRed.NumBuf+LaRed.NumVirtProcEje];
        for(i = 0;i < (LaRed.NumBuf+LaRed.NumVirtProcEje); i++){
            Output[i] = new CrossSwitch(LaRed);
            Output[i].MyBuf=i; // nuevo
        }
    }
}

