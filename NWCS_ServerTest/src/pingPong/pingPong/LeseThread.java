package pingPong;

import java.net.ServerSocket;
import java.util.Scanner;

public class LeseThread implements Runnable {
	static int length = 256;
	ServerSocket socket;

	//Konstuktor des LeseThread f�r den Input per Tastatur zur Serverbedienung
	LeseThread(ServerSocket welcomeSocket) {
		this.socket = welcomeSocket;
		Thread t = new Thread(this, "Lesen");
		t.start();
	}

	@Override
	public void run() {
		//Scanner Start f�r Tastatureingabe Server
		Scanner tastaturEingabe = new Scanner(System.in);
		while (true) {
			String Eingabe = tastaturEingabe.nextLine();
			//Bei Eingabe "Liste" wird die Liste der aktiven Clients ausgegeben
			if (Eingabe.equals("Liste")) {
				Pong.showListe();
				Thread.currentThread().interrupt();
			}

			if(Eingabe.equals("Start")){
				
			}
			//Bei Eingabe "Ende" wird Programm beendet
			if (Eingabe.equals("Ende")) {
				System.out.println("Der Server wird abgeschalten");
				tastaturEingabe.close();
				System.exit(0);
			}
		}
	}
}
