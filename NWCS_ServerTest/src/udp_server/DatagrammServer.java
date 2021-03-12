package udp_server;

import java.net.*;
import java.io.*;
import java.util.*;

public class DatagrammServer {
	static final String ANMELDUNG = "ANMELDUNG";
	static final String ENDE = "ENDE";
	static int port = 5678;
	static int length = 256; // Länge eines Pakets

	public static void main(String args[]) {

		DatagramPacket paket = new DatagramPacket(new byte[length], length);
		Vector clients = new Vector(); // Liste der Clients

		try {
			DatagramSocket socket = new DatagramSocket(port);
			for (;;) {

				// Warten auf nächstes Paket
				socket.receive(paket);
				InetSocketAddress add = (InetSocketAddress) paket.getSocketAddress();

				// Text aus Paket extrahieren
				String text = new String(paket.getData(), 0, paket.getLength());
				System.out.println(add + ">" + text);

				// Paket auswerten
				if (text.equals(ANMELDUNG)) {
					clients.add(add);
					System.out.println("Anzahl Clients: " + clients.size());
				} else if (text.equals(ENDE)) {
					clients.remove(add);
					System.out.println("Anzahl Clients: " + clients.size());
				} else {
					// Versenden von Kopien an alle anderen Clients
					for (int i = 0; i < clients.size(); i++) {
						InetSocketAddress dest = (InetSocketAddress) clients.get(i);
						if (!dest.equals(add)) {
							paket.setSocketAddress(dest);
							socket.send(paket);
							System.out.println("Kopie an " + dest);
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Ausnahmefehler: " + e);
		}
	}
}
