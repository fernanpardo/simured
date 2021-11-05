/*
 * CrossBar.java
 * Crossbar model
 */
package jsimured.red;

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

