package pingPong;

import java.net.ServerSocket;
import java.util.Scanner;

public class LeseThread implements Runnable {
	static int length = 256;
	ServerSocket socket;

	LeseThread(ServerSocket welcomeSocket) {
		this.socket = welcomeSocket;
		Thread t = new Thread(this, "Lesen");
		t.start();
	}

	@Override
	public void run() {

		Scanner tastaturEingabe = new Scanner(System.in);
		while (true) {
			String Eingabe = tastaturEingabe.nextLine();
			if (Eingabe.equals("Liste")) {
				Pong.showListe();
				;
				Thread.currentThread().interrupt();
			}
			if (Eingabe.equals("Ende")) {
				System.out.println("Ende");
				tastaturEingabe.close();
				Pong.setDead();
				Thread.currentThread().interrupt();
			}
		}
	}
}
