package Main;

/*
 * CSVFilter.java
 * Filtro para ver solo archivos de tipo CSV
 * Created on 28 de abril de 2005, 11:47
 */
import java.io.File;
import javax.swing.filechooser.*;
/**
 *
 * @author oscar
 */
public class CSVFilter extends FileFilter {
    final static String csv ="csv";
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            String extension = s.substring(i+1).toLowerCase();
            if (csv.equals(extension))
                    return true;
             else 
                return false;            
        }

        return false;
    }
      // La descripción del filtro
    public String getDescription() {
        return "Comma separator values (CSV)";
    }
    /** Creates a new instance of CSVFilter */
    public CSVFilter() {
    }
    
}
