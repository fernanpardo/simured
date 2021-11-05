/*
 * It seems that applets are not supported anymore by any navigator.
 * Last time I tried I could not run or test anything. I leave this file here just in case...
*/

package jsimured.main;

import java.awt.Graphics;

public class JsimuredApplet extends javax.swing.JApplet{
    // Initializes the applet simuredJava
    @Override
    public void init(){
        new Jsimured(true,false).setVisible(true); //true=es applet, false=no es comando
    }
    @Override
    public void paint(Graphics g) {
        g.drawString("Simured has been started in a new window. Allow emergent windows in the navigator.", 10, 30);
    }
}

