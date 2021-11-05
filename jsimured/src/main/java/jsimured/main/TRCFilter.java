/*
 * TRCFilter.java
 * Filter to reaf trace file
 */
package jsimured.main;

import java.io.File;
import javax.swing.filechooser.*;

public class TRCFilter extends FileFilter {
     final static String trc ="trc";
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            String extension = s.substring(i+1).toLowerCase();
            if (trc.equals(extension))
                    return true;
             else 
                return false;            
        }

        return false;
    }
      // La descripción del filtro
    public String getDescription() {
        return "Ficheros de Traza (TRC)";
    }
    /** Creates a new instance of TRCFilter */
    public TRCFilter() {
    }
    
}
