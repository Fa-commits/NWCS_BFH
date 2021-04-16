package pingPong;

import java.net.ServerSocket;
import java.util.Scanner;

public class LeseThread implements Runnable {
	static int length = 256;
	ServerSocket socket;

	//Konstuktor des LeseThread fuer den Input per Tastatur zur Serverbedienung
	public LeseThread(ServerSocket welcomeSocket) {
		this.socket = welcomeSocket;
		Thread t = new Thread(this, "Lesen");
		t.start();
	}

	@Override
	public void run() {
		//Scanner Start fuer Tastatureingabe Server
		Scanner tastaturEingabe = new Scanner(System.in);
		while (true) {
			String Eingabe = tastaturEingabe.nextLine();
			//Bei Eingabe "Liste" wird die Liste der aktiven Clients ausgegeben
			if (Eingabe.equals("Liste")) {
				Pong.showListe();
				Thread.currentThread().interrupt();
			}

			//Bei Eingabe "Ende" wird Programm beendet
			if (Eingabe.equals("Ende")) {
				System.out.println("Der Server wird abgeschalten");
				tastaturEingabe.close();
				System.exit(0);
			}
			else{
				System.out.println("Eingabe war keine korrekte Anweisung");
			}
		}
	}
}
