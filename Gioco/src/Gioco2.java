/**
 * @(#)Gioco2.java
 *
 * Gioco2 application
 *
 * @author
 * @version 1.00 2011/5/16
 */

import javax.swing.*;
import java.awt.*;

public class Gioco2 {

    public static void main(String[] args) {

    	/** Creazione e settaggio della finestra */
    	JFrame finestra = new JFrame("Gioco: Battaglia Navale");
    	finestra.setBounds(100, 100, 800, 600);
    	finestra.setResizable(false);
    	finestra.setDefaultCloseOperation(finestra.EXIT_ON_CLOSE);

		/** Creazione e settaggio del pannello alla finestra */
    	PannelloGioco panel = new PannelloGioco();
    	finestra.add(panel);

    	finestra.setVisible(true);

    }
}
