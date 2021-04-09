package pingPong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PongThread extends Thread {
	private Socket connectionSocket;
	long durchlauf;

	public PongThread(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	public PongThread(Socket connectionSocket, long durchlauf) {
		this.connectionSocket = connectionSocket;
		this.durchlauf = durchlauf;
	}

	public void run() {

		try {
			System.out.println("Neue Verbindung in Thread Nummer: " + Thread.currentThread().getId());

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			PrintStream outToClient = new PrintStream(connectionSocket.getOutputStream());
			Pong.addToVector(connectionSocket.toString());

			while (connectionSocket.isConnected()) {

				String rein = inFromClient.readLine();

				if (rein.contentEquals("Start")) {
					durchlauf = Integer.parseInt(inFromClient.readLine()); // holt sich den ersten Input des Clients und
																			// definiert damit die Anzahl Durchläufe

					for (int i = 0; i < durchlauf; i++) {
						System.out.print("warte auf Daten, ");
						Thread.sleep(1000);

						// Daten empfangen
						String clientSentence = inFromClient.readLine();
						System.out.print("erhalten >" + clientSentence + "< ");

						// Daten senden
						String pattern = "mm:ss"; // Darstellung des Datumsformats
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
						String dt = LocalTime.now().format(formatter);

						outToClient.println((i + 1 + ". PONG " + dt));
						System.out.println(i + 1 + ". PONG " + dt + " gesendet.");
					}
					System.out.println("Uebermittlung abgeschlossen\nWarten auf neues Datenpacket");
				}
				if (rein.contentEquals("Ende")) {

					try {
						Pong.removeFromVector(connectionSocket.toString());
						System.out.println("Verbindung abgebaut");
						Pong.showListe();
						connectionSocket.close();

						Thread.currentThread().interrupt();
					} catch (IOException e) {

					}
				}
				if (rein.contentEquals("Liste")) {
					Pong.showListe();
				}
			}
		} catch (IOException | InterruptedException e) {

		}

	}

}
