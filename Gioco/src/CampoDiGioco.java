
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

class CampoDiGioco extends JPanel{

	Rectangle campo; /** Contorno del campo di gioco */
	Line2D.Double linea; /** Linea per dividere il campo di gioco in quadrati */
	/** JLabel lettera;
	JLabel numero; */

	/** Costruttore */
	public CampoDiGioco(Graphics2D grandePennello) {

		/** Disegno del campo dell'utente */
		campo = new Rectangle(100, 100, 300, 300);
		grandePennello.setColor(Color.green);
		grandePennello.draw(campo);

		/** Disegno del campo del PC */
		campo = new Rectangle(400, 100, 301, 301);
		grandePennello.setColor(Color.black);
		grandePennello.fill(campo);

		disegnaLinea(grandePennello);

		posizionaLettere(grandePennello);
		posizionaNumeri(grandePennello);

	}

	/** Metodo per dividere il campo di gioco in quadrati */
	public void disegnaLinea(Graphics2D grandePennello) {

		int i = 0;

		grandePennello.setColor(Color.magenta);

		/** Linee verticali, campo Utente */
		for (i = 130; i < 400; i+= 30) {

			linea = new Line2D.Double(i, 100, i, 400);
			grandePennello.draw(linea);
		}

		/** Linee orizzontali, campo Utente */
		for (i = 130; i < 400; i+= 30) {

			linea = new Line2D.Double(100, i, 400, i);
			grandePennello.draw(linea);
		}

		/** Linee verticali, campo PC */
		for (i = 430; i < 700; i+= 30) {

			linea = new Line2D.Double(i, 100, i, 400);
			grandePennello.draw(linea);
		}

		/** Linee orizzontali, campo PC */
		for (i = 130; i < 400; i+= 30) {

			linea = new Line2D.Double(400, i, 700, i);
			grandePennello.draw(linea);
		}
	}

	public void posizionaLettere(Graphics2D grandePennello) {

		grandePennello.setColor(Color.black);

		Font carattere = new Font("Comic Sans MS", Font.PLAIN, 16);

		grandePennello.setFont(carattere);

		grandePennello.drawString("A", 85, 120);
		grandePennello.drawString("B", 85, 150);
		grandePennello.drawString("C", 85, 180);
		grandePennello.drawString("D", 85, 210);
		grandePennello.drawString("E", 85, 240);
		grandePennello.drawString("F", 85, 270);
		grandePennello.drawString("G", 85, 300);
		grandePennello.drawString("H", 85, 330);
		grandePennello.drawString("I", 85, 360);
		grandePennello.drawString("L", 85, 390);

		grandePennello.drawString("A", 705, 120);
		grandePennello.drawString("B", 705, 150);
		grandePennello.drawString("C", 705, 180);
		grandePennello.drawString("D", 705, 210);
		grandePennello.drawString("E", 705, 240);
		grandePennello.drawString("F", 705, 270);
		grandePennello.drawString("G", 705, 300);
		grandePennello.drawString("H", 705, 330);
		grandePennello.drawString("I", 705, 360);
		grandePennello.drawString("L", 705, 390);
	}

	public void posizionaNumeri(Graphics2D grandePennello) {

		grandePennello.setColor(Color.black);

		Font carattere = new Font("Comic Sans MS", Font.PLAIN, 16);

		grandePennello.setFont(carattere);

		grandePennello.drawString("1", 110, 95);
		grandePennello.drawString("2", 140, 95);
		grandePennello.drawString("3", 170, 95);
		grandePennello.drawString("4", 200, 95);
		grandePennello.drawString("5", 230, 95);
		grandePennello.drawString("6", 260, 95);
		grandePennello.drawString("7", 290, 95);
		grandePennello.drawString("8", 320, 95);
		grandePennello.drawString("9", 350, 95);
		grandePennello.drawString("10", 380, 95);

		grandePennello.drawString("1", 410, 95);
		grandePennello.drawString("2", 440, 95);
		grandePennello.drawString("3", 470, 95);
		grandePennello.drawString("4", 500, 95);
		grandePennello.drawString("5", 530, 95);
		grandePennello.drawString("6", 560, 95);
		grandePennello.drawString("7", 590, 95);
		grandePennello.drawString("8", 620, 95);
		grandePennello.drawString("9", 650, 95);
		grandePennello.drawString("10", 680, 95);
	}
}
