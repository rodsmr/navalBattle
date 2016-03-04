
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class PannelloGioco extends JPanel{

	protected Graphics2D pennello2D; /** Pennello pubblico */
	protected CampoDiGioco campo; /** Varibiale di tipo CampoDiGioco per creare il campo */
	protected int[] mosseX; /** Vettore che mi contiene le posizioni delle x */
	protected int[] mosseY; /** Vettore che mi contiene le posizioni delle y */
	protected int[] mosseAttaccoX; /** Vettore che mi contiene le posizioni delle x attaccate */
	protected int[] mosseAttaccoY; /** Vettore che mi contiene le posizioni delle y attaccate */
	protected int contatoreX; /** Contatore che mi conteggia mosseX */
	protected int contatoreY; /** Contatore che mi conteggia mosseY */
	protected Rectangle nave; /** Nave */
	protected int[][] mosseUtente; /** Matrice per le mosse dell'utente */
	protected int[][] naviPC; /** Matrice per le navi del PC, inserite in maniera random */
	protected int xAttacco; /** Posizione d'attacco dell'utente delle x */
	protected int yAttacco; /** Posizione d'attacco dell'utente delle y */
	protected int[][] matriceAttaccoUtente; /** Matrice per contenere gli attacchi effettuati dall'utente */
	protected int[] attaccoXPositivi; /** Vettore per contenere la posizione x degli attacchi andati a buon fine da parte dell'utente */
	protected int[] attaccoYPositivi; /** Vettore per contenere la posizione y degli attacchi andati a buon fine da parte dell'utente */
	protected int[] attaccoXNegativi; /** Vettore per contenere la posizione x degli attacchi non andati a buon fine da parte dell'utente */
	protected int[] attaccoYNegativi; /** Vettore per contenere la posizione y degli attacchi non andati a buon fine da parte dell'utente */
	protected int[] attaccoXPositiviPC; /** Vettore per contenere la posizione x degli attacchi andati a buon fine da parte del PC */
	protected int[] attaccoYPositiviPC; /** Vettore per contenere la posizione y degli attacchi andati a buon fine da parte del PC */
	protected int[] attaccoXNegativiPC; /** Vettore per contenere la posizione x degli attacchi non andati a buon fine da parte del PC */
	protected int[] attaccoYNegativiPC; /** Vettore per contenere la posizione y degli attacchi andati a buon fine da parte del PC */
	protected int contaAttacchiPositivi; /** Tiene il conto degli attacchi andati a buon fine dell'utente */
	protected int contaAttacchiNegativi; /** Tiene il conto degli attacchi non andati a buon fine dell'utente */
	protected int contaAttacchiPositiviPC; /** Tiene il conto degli attacchi andati a buon fine del PC */
	protected int contaAttacchiNegativiPC; /** Tiene il conto degli attacchi non andati a buon fine del PC */
	protected JTextArea specificheAzioni; /** Area di testo in cui vengono suggerite le azioni da compiere */
	protected Random generatore; /** Generatore random */

	/** Costruttore */
	public PannelloGioco() {

		mosseX = new int[15];
		mosseY = new int[15];

		contatoreX = 0;
		contatoreY = 0;

		mosseUtente = new int[10][10];
		naviPC = new int[10][10];

		xAttacco = 0;
		yAttacco = 0;

		generatore = new Random();

		matriceAttaccoUtente = new int[10][10];

		attaccoXPositivi = new int[100];
		attaccoYPositivi = new int[100];

		attaccoXNegativi = new int[100];
		attaccoYNegativi = new int[100];

		attaccoXPositiviPC = new int[100];
		attaccoYPositiviPC = new int[100];

		attaccoXNegativiPC = new int[100];
		attaccoYNegativiPC = new int[100];

		contaAttacchiPositivi = 0;
		contaAttacchiNegativi = 0;

		contaAttacchiPositiviPC = 0;
		contaAttacchiNegativiPC = 0;

		specificheAzioni = new JTextArea("Utente, posiziona le tue navi!");
		specificheAzioni.setBounds(300, 450, 157, 18);
		specificheAzioni.setEditable(false);
		this.add(specificheAzioni);

		posizionaNavePC();

		addMouseListener(new AscoltatoreMouse());

		this.setLayout(null);
	}

	/** Paint */
	public void paint(Graphics pennello) {

		super.paint(pennello);

		pennello2D = (Graphics2D) pennello;

		campo = new CampoDiGioco(pennello2D);

		disegnaNave(pennello2D);

		if (posizionePerMatricePC(xAttacco) >= 0 && posizionePerMatriceUtente(yAttacco) >= 0){
			disegnaNaveAttacco(pennello2D);
		}
	}

	/** Metodo che mi posiziona le navi del PC in maniera random nella matrice apposita */
	public void posizionaNavePC() {

		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;


		/** Posizionamento nave da un quadretto */
		int numeroGenerato = generatore.nextInt(3);
		naviPC[numeroGenerato][numeroGenerato] = 1;

		/** Posizionamento nave da due quadretti */
		numeroGenerato = generatore.nextInt(4)+6;
		x = numeroGenerato;
		numeroGenerato = generatore.nextInt(3);
		y = numeroGenerato;
		naviPC[y][x] = 1;
		if (y == 0) {
			naviPC[y+1][x] = 1;
		}
		if (y == 1) {
			naviPC[y][x-1] = 1;
		}
		if (y == 2) {
			naviPC[y-1][x] = 1;
		}

		/** Posizionamento nave da tre quadretti */
		numeroGenerato = generatore.nextInt(4)+6;
		x = numeroGenerato;
		numeroGenerato = generatore.nextInt(2)+6;
		y = numeroGenerato;
		naviPC[x][y] = 1;
		if (y == 6) {
			naviPC[x][y+1] = 1;
			naviPC[x][y+2] = 1;
		}
		if (y == 9) {
			naviPC[x][y-1] = 1;
			naviPC[x][y-2] = 1;
		}
		if (y == 8 || y == 7) {
			naviPC[x][y-1] = 1;
			naviPC[x][y+1] = 1;
		}

		/** Posizionamento nave da quattro quadretti */
		numeroGenerato = generatore.nextInt(5)+4;
		x = numeroGenerato;
		numeroGenerato = generatore.nextInt(2)+1;
		y = numeroGenerato;
		naviPC[x][y] = 1;
		if (x == 4) {
			naviPC[x+1][y] = 1;
			naviPC[x+2][y] = 1;
			naviPC[x+3][y] = 1;
		}
		if (x == 8) {
			naviPC[x-1][y] = 1;
			naviPC[x-2][y] = 1;
			naviPC[x-3][y] = 1;
		}
		if (x == 6) {
			naviPC[x-1][y] = 1;
			naviPC[x+1][y] = 1;
			naviPC[x+2][y] = 1;
		}
		if (x == 5) {
			naviPC[x-1][y] = 1;
			naviPC[x+1][y] = 1;
			naviPC[x+2][y] = 1;
		}
		if (x == 7) {
			naviPC[x+1][y] = 1;
			naviPC[x-1][y] = 1;
			naviPC[x-2][y] = 1;
		}

		/** Posizionamento nave da cinque quadretti */
		x = 4;
		numeroGenerato = generatore.nextInt(9);
		y = numeroGenerato;
		naviPC[y][x] = 1;
		if (y == 8) {
			naviPC[y-1][x] = 1;
			naviPC[y-2][x] = 1;
			naviPC[y-3][x] = 1;
			naviPC[y-4][x] = 1;
		}
		if (y == 0) {
			naviPC[y+1][x] = 1;
			naviPC[y+2][x] = 1;
			naviPC[y+3][x] = 1;
			naviPC[y+4][x] = 1;
		}
		if (y == 4) {
			naviPC[y+1][x] = 1;
			naviPC[y+2][x] = 1;
			naviPC[y-1][x] = 1;
			naviPC[y-2][x] = 1;
		}
		if (y == 5 || y == 6) {
			naviPC[y+1][x] = 1;
			naviPC[y+2][x] = 1;
			naviPC[y-1][x] = 1;
			naviPC[y-2][x] = 1;
		}
		if (y == 3 || y == 2) {
			naviPC[y+1][x] = 1;
			naviPC[y+2][x] = 1;
			naviPC[y-1][x] = 1;
			naviPC[y-2][x] = 1;
		}
		if (y == 1) {
			naviPC[y-1][x] = 1;
			naviPC[y+1][x] = 1;
			naviPC[y+2][x] = 1;
			naviPC[y+3][x] = 1;
		}
		if (y == 7) {
			naviPC[y+1][x] = 1;
			naviPC[y-1][x] = 1;
			naviPC[y-2][x] = 1;
			naviPC[y-3][x] = 1;
		}

		/** Stampa di prova
		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				System.out.print(naviPC[i][j]);
			}
			System.out.println();
		} */

	}

	/** Metodo per disegnare le navi di difesa posizionate dall'utente */
	public void disegnaNave(Graphics2D grandePennello) {

		grandePennello.setColor(Color.yellow);

		int i = 0;
		for (i = 0; i < 15; i++) {

			if (mosseX[i] != 0 || mosseY[i] != 0 && contatoreX < 15 && contatoreY < 15) {

				nave = new Rectangle(mosseX[i]+1, mosseY[i]+1, 29, 29);
				grandePennello.fill(nave);

				mosseUtente[posizionePerMatriceUtente(mosseY[i])][posizionePerMatriceUtente(mosseX[i])] = 1;
			}
		}

		/** Controllo temporaneo per vedere se si ferma dopo 15 caselle occupate */
		if (contatoreX == 15 && contatoreY == 15) {

			specificheAzioni.setText("Utente, attaca il campo del PC!");

			//System.out.println("STOP!");

			//System.out.println();

			//stampaMosseX();
			//System.out.println();
			//System.out.println();
			//stampaMosseY();
			//stampaMatrice();

		}
	}

	/** Metodo per disegnare le navi in attacco nel campo del PC */
	public void disegnaNaveAttacco(Graphics2D grandePennello) {

		int i = 0;
		int x = 0;
		int y = 0;

		x = xAttacco;
		y = yAttacco;

		if (naviPC[posizionePerMatriceUtente(y)][posizionePerMatricePC(x)] == 1) {
			attaccoXPositivi[contaAttacchiPositivi] = x;
			attaccoYPositivi[contaAttacchiPositivi] = y;
			naviPC[posizionePerMatriceUtente(y)][posizionePerMatricePC(x)] = 2;
			contaAttacchiPositivi++;
			specificheAzioni.setText("Utente, attacca ancora!");
		}
		else {
			if (naviPC[posizionePerMatriceUtente(y)][posizionePerMatricePC(x)] == 2){
				naviPC[posizionePerMatriceUtente(y)][posizionePerMatricePC(x)] = 2;
			}
			else {
				attaccoXNegativi[contaAttacchiNegativi] = x;
				attaccoYNegativi[contaAttacchiNegativi] = y;
				naviPC[posizionePerMatriceUtente(y)][posizionePerMatricePC(x)] = 3;
				contaAttacchiNegativi++;
			}
			attaccoDelPC(grandePennello);
		}

		for (i = 0; i < contaAttacchiPositivi; i++) {
			nave = new Rectangle(attaccoXPositivi[i] + 1, attaccoYPositivi[i] + 1, 29, 29);
			grandePennello.setColor(Color.orange);
			grandePennello.fill(nave);
		}

		for (i = 0; i < contaAttacchiNegativi; i++) {
			nave = new Rectangle(attaccoXNegativi[i] + 1, attaccoYNegativi[i] + 1, 29, 29);
			grandePennello.setColor(Color.cyan);
			grandePennello.fill(nave);
		}

		 for (i = 0; i < contaAttacchiPositiviPC; i++) {
			nave = new Rectangle(attaccoXPositiviPC[i] + 1, attaccoYPositiviPC[i] + 1, 29, 29);
			grandePennello.setColor(Color.orange);
			grandePennello.fill(nave);
		}

		for (i = 0; i < contaAttacchiNegativiPC; i++) {
			nave = new Rectangle(attaccoXNegativiPC[i] + 1, attaccoYNegativiPC[i] + 1, 29, 29);
			grandePennello.setColor(Color.cyan);
			grandePennello.fill(nave);
		}
	}

	/** Metodo per disegna le navi quando il PC attacca */
	public void attaccoDelPC(Graphics2D grandePennello) {

		int x;
		int y;
		int i;
		x = generatore.nextInt(9);
		y = generatore.nextInt(9);

		if (mosseUtente[y][x] == 1) {
			mosseUtente[y][x] = 2;
			attaccoXPositiviPC[contaAttacchiPositiviPC] = ritornaPosizioneY(x);
			attaccoYPositiviPC[contaAttacchiPositiviPC] = ritornaPosizioneY(y);
			contaAttacchiPositiviPC++;
			if (mosseUtente[y+1][x] == 1) {
				mosseUtente[y+1][x] = 2;
				attaccoXPositiviPC[contaAttacchiPositiviPC] = ritornaPosizioneY(x);
				attaccoYPositiviPC[contaAttacchiPositiviPC] = ritornaPosizioneY(y+1);
				contaAttacchiPositiviPC++;
			}
			else {
				attaccoDelPC(grandePennello);
			}
		}
		else {
			if (mosseUtente[y][x] == 2) {
				mosseUtente[y][x] = 2;
			}
			else {
				attaccoXNegativiPC[contaAttacchiNegativiPC] = ritornaPosizioneY(x);
				attaccoYNegativiPC[contaAttacchiNegativiPC] = ritornaPosizioneY(y);
			}
			contaAttacchiNegativiPC++;
			specificheAzioni.setText("Utente, attacca il campo del PC!");
		}

		for (i = 0; i < contaAttacchiPositiviPC; i++) {
			grandePennello.setColor(Color.orange);
			nave = new Rectangle(attaccoXPositiviPC[i]+1, attaccoYPositiviPC[i]+1, 29, 29);
			grandePennello.fill(nave);
		}

		for (i = 0; i < contaAttacchiNegativiPC; i++) {
			grandePennello.setColor(Color.cyan);
			nave = new Rectangle(attaccoXNegativiPC[i]+1, attaccoYNegativiPC[i]+1, 29, 29);
			grandePennello.fill(nave);
		}
	}

	/** Metodo per controllare se l'utente o il PC hanno vinto*/
	public String controllaVittoria() {

		String nomeVincitore = "Nessuno";

		int i;
		int j;
		int beccatePC = 0;
		int beccateUtente = 0;

		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				if (mosseUtente[j][i] == 2) {
					beccatePC++;
				}
				if (naviPC[j][i] == 2) {
					beccateUtente++;
				}
			}
		}

		if (beccatePC == 15) {
			nomeVincitore = "PC";
		}
		if (beccateUtente == 15) {
			nomeVincitore = ("Utente");
		}

		return nomeVincitore;

	}

	public int ritornaPosizioneX(int x) {

		if (x == 0)
			x = 400;
		if (x == 1)
			x = 430;
		if (x == 2)
			x = 460;
		if (x == 3)
			x = 490;
		if (x == 4)
			x = 520;
		if (x == 5)
			x = 550;
		if (x == 6)
			x = 580;
		if (x == 7)
			x = 610;
		if (x == 8)
			x = 640;
		if (x == 9)
			x = 670;

		return x;
	}

	public int ritornaPosizioneY(int y) {

		if (y == 0)
			y = 100;
		if (y == 1)
			y = 130;
		if (y == 2)
			y = 160;
		if (y == 3)
			y = 190;
		if (y == 4)
			y = 220;
		if (y == 5)
			y = 250;
		if (y == 6)
			y = 280;
		if (y == 7)
			y = 310;
		if (y == 8)
			y = 340;
		if (y == 9)
			y = 370;

		return y;
	}

	/** Metodo temporaneo di controllo per vedere se la matrice è occupata */
	public void stampaMatrice() {

		int i = 0;
		int j = 0;

		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				System.out.print(mosseUtente[i][j]);
				System.out.print(matriceAttaccoUtente[i][j]);
				System.out.print(naviPC[i][j]);
			}
			System.out.println();
		}
	}

	/** Metodo temporaneo per stampare le mosseX */
	public void stampaMosseX() {
		int i = 0;
		for (i = 0; i < 15; i++) {
			System.out.print(mosseX[i]+" ");
		}
	}

	/** Metodo temporaneo per stampare le mosseY */
	public void stampaMosseY() {
		int i = 0;
		for (i = 0; i < 15; i++) {
			System.out.print(mosseY[i]+" ");
		}
	}

	/** Metodo per vedere la posizione occupata per inserila nella matrice */
	public int posizionePerMatriceUtente(int clic) {

		clic = (clic - 100) / 30;

		return clic;
	}

	/** Metodo per vedere la posizione occupata per inserila nella matrice */
	public int posizionePerMatricePC(int clic) {

		clic = (clic - 400) / 30;

		return clic;
	}

	/** Metodo per prendere la posizione cliccata per l'inserimento */
	public int posizioneVeraDifesa(int clic) {

		if (clic < 100 || clic > 400) {

			clic = 0;
		}

		if (clic >= 100 && clic < 130) {

			clic = 100;
		}

		if (clic >= 130 && clic < 160) {

			clic = 130;
		}

		if (clic >= 160 && clic < 190) {

			clic = 160;
		}

		if (clic >= 190 && clic < 220) {

			clic = 190;
		}

		if (clic >= 220 && clic < 250) {

			clic = 220;
		}

		if (clic >= 250 && clic < 280) {

			clic = 250;
		}

		if (clic >= 280 && clic < 310) {

			clic = 280;
		}

		if (clic >= 310 && clic < 340) {

			clic = 310;
		}

		if (clic >= 340 && clic < 370) {

			clic = 340;
		}

		if (clic >= 370 && clic < 400) {

			clic = 370;
		}

		return clic;
	}

	/** Metodo per prendere la posizione cliccata per l'attacco */
	public int posizioneVeraAttacco(int clic) {

		if (clic < 400 && clic > 700){

			clic = 0;
		}

		if (clic >= 400 && clic < 430){

			clic = 400;
		}

		if (clic >= 430 && clic < 460){

			clic = 430;
		}

		if (clic >= 460 && clic < 490){

			clic = 460;
		}

		if (clic >= 490 && clic < 520){

			clic = 490;
		}

		if (clic >= 520 && clic < 550){

			clic = 520;
		}

		if (clic >= 550 && clic < 580){

			clic = 550;
		}

		if (clic >= 580 && clic < 610){

			clic = 580;
		}

		if (clic >= 610 && clic < 640){

			clic = 610;
		}

		if (clic >= 640 && clic < 670){

			clic = 640;
		}

		if (clic >= 670 && clic < 700){

			clic = 670;
		}

		return clic;
	}
	/** Ascoltatore del mouse */
	public class AscoltatoreMouse implements MouseListener {

		public void mouseClicked(MouseEvent e) {

			if (contatoreX < 15 && contatoreY < 15) {

				if (posizioneVeraDifesa(e.getX()) != 0 && posizioneVeraDifesa(e.getY()) != 0){
					mosseX[contatoreX] = posizioneVeraDifesa(e.getX());
					mosseY[contatoreY] = posizioneVeraDifesa(e.getY());
					contatoreX++;
					contatoreY++;
				}
			}

			if (e.getX() >= 400 && e.getX() <= 700) {

				String vincitore = controllaVittoria();

				if (vincitore == "Utente") {
					JLabel utente = new JLabel("Utente hai vinto");
					utente.setBounds(400, 500, 100, 100);
					add(utente);
				}
				if (vincitore == "PC") {
						JLabel pc = new JLabel("PC hai vinto");
						pc.setBounds(400, 500, 100, 100);
						add(pc);
				}
				else {
					xAttacco = posizioneVeraAttacco(e.getX());
					yAttacco = posizioneVeraDifesa(e.getY());
				}
			}

			repaint();
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

	}
}
