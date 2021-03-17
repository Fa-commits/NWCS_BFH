package pingPong;

import java.io.*;
import java.net.*;
import java.util.*;

//////////////////////////////
//							//
//	06-MAR-2009/lar1@SWS	//
//	Ping-Pong-Server		//
//							//
//////////////////////////////

class Pong {

	static boolean dead = false;
	static Vector<Serializable> clients = new Vector<Serializable>();

	//Hinzufügen von Clients im Speicher
	public static void addToVector(String string) {
		clients.add(string);
	}

	//Entfernen von Clients im Speicher
	public static void removeFromVector(String string) {
		clients.remove(string);
	}

	//Beenden des gesamten Programms
	public static void setDead() {
		System.exit(0);
	}

	//Anzeige der Liste aller verbundenen Clients
	public static void showListe() {
		System.out.println("Liste der verbundenen Clients:\n" + Pong.clients.toString());
	}

	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(5678);

		InetAddress ip = Inet4Address.getLocalHost();
		System.out.println("Warte auf Verbindung mit folgenden Angaben:");
		System.out.println("Portnummer: " + welcomeSocket.getLocalPort());
		System.out.println("Hostaddresse: " + ip.getHostAddress());
		System.out.println("Hostname: " + ip.getHostName());

		@SuppressWarnings("unused")
		LeseThread leseThread = new LeseThread(welcomeSocket);

		while (true) {

			// Verbindung aufbauen
			System.out.println("Warten auf neue Verbindung");
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Verbindung aufgebaut und an PongThread übergeben.");
			PongThread thread = new PongThread(connectionSocket);
			thread.start();

		}

	}
}
