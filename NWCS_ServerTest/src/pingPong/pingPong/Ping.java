package pingPong;

import java.net.*;
import java.util.Scanner;
import java.io.*;

//////////////////////////////
//							//
//	Ping-Pong-Client		//
//							//
//////////////////////////////

class Ping {
	public static void main(String argv[]) throws Exception {
		String sentence;
		final String IP = "192.168.1.114";
		final int PORT = 5678;
		int durchlauf = 3;

		// Verbindung aufbauen
		Socket clientSocket = new Socket(IP, PORT);
		System.out.println("Verbindung aufgebaut.");

		// Socket Streams erstellen
		@SuppressWarnings("resource")
		Scanner tastaturEingabe = new Scanner(System.in);
		PrintStream outToServer = new PrintStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


		while (clientSocket.isConnected()) {
			// String liste = inFromServer.readLine();
			String text = tastaturEingabe.next();
			
			if(inFromServer.ready()==true){	
				if(inFromServer.readLine().contentEquals("Liste")){
				String listeInhalt = inFromServer.readLine();
				System.out.println(listeInhalt);
				System.out.println("Anzeige der Liste");
				}
			}

			if (text.equals("Start")) {
				outToServer.println(text);
				outToServer.println(durchlauf);
				outToServer.flush();

				// 10 mal PING senden und Antwort empfangen
				for (int i = 0; i < durchlauf; i++) {
					System.out.print("sende PING, ");

					// Daten senden
					outToServer.print(i + 1 + ". PING\n");
					outToServer.flush();
					System.out.print("gesendet warte auf Antwort, ");

					// Daten empfangen
					sentence = inFromServer.readLine();
					System.out.println("Antwort erhalten >" + sentence + "<");
				}
				System.out.println("Uebermittlung abgeschlossen\nWarten auf neue Anweisung");
			}
			if (text.equals("Ende")) {

				// letzte Sendeanweisung an den Server damit die Abmeldung erfolgt
				outToServer.println(text);

				// Verbindug abbauen
				clientSocket.close();
				System.out.println("Verbindung abgebaut.");
				System.exit(0);
			}
			if (text.equals("Liste")) {
				outToServer.println(text);

				//Funktioniert nicht, ist die Annahme der Liste welche vom Server gesendet wurde.
				// if(inFromServer.ready()==true){
				// String listeInhalt = inFromServer.readLine();
				// System.out.println(listeInhalt);
				// System.out.println("Anzeige der Liste");
				// }
			}
			//Gleich wie oben, andere Stelle, funktioniert auch nicht
			// if(inFromServer.ready()==true){	
			// 	if(inFromServer.readLine().contentEquals("Liste")){
			// 	String listeInhalt = inFromServer.readLine();
			// 	System.out.println(listeInhalt);
			// 	System.out.println("Anzeige der Liste");
			// 	}
			// }
		}
	}
}
