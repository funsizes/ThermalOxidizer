
package mainPackage;

import java.awt.Container;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater( new Runnable(){

            @Override
            public void run() {
                
                final ApplicationGUI frame = new ApplicationGUI();
                //frame.setSize(1400, 650);
                //frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                Container p = frame.getContentPane();
               
                
            }
         
        });        
        
    }
    
}
