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

