package udp_client;

import java.net.*;
import java.io.*;

public class DatagrammClient {
   static final String  ANMELDUNG = "ANMELDUNG";
   static final String  ENDE      = "ENDE";
   static int port    = 5678;
   static int length  = 256;    // Länge eines Pakets

   public static void main( String args[]) {
      String servername = "localhost";
      String text = null;
      DatagramPacket packet;
      byte[] ba = ANMELDUNG.getBytes();

      // Namen des Servers von Kommandozeile übernehmen
      if( args.length > 0 ) servername = args[0];

      try {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ia =  InetAddress.getByName( servername );
        packet = new DatagramPacket( ba, ba.length, ia, port);
        // sende Anmeldung
        socket.send( packet );

        // Lesen der empfangenen Pakete erfolgt in eigenem Thread
        LeseThread lt = new LeseThread( socket );

        // Eingaben von Tastatur an Server schicken
        BufferedReader br = new BufferedReader(
             new InputStreamReader( System.in )  );
        do {
           text = br.readLine();
           ba = text.getBytes();
           packet.setData( ba, 0, ba.length );
           socket.send( packet );
        } while( ! text.equals("ENDE") );

        // alles beenden
        System.exit(0);

      }
      catch( IOException e ) {
        System.err.println( e );
      }
   }
}


class LeseThread implements Runnable {
   static int length  = 256;
   DatagramSocket socket;

   LeseThread(DatagramSocket socket ) {
      this.socket = socket;
      Thread t = new Thread(this,"Lesen");
      t.start();
   }

   public void run()  {
      DatagramPacket packet =
         new DatagramPacket( new byte[length], length);
      while( true ) {
        try {
           socket.receive( packet );
           InetSocketAddress add =
              (InetSocketAddress)packet.getSocketAddress();
           String text =
             new String(packet.getData(), 0, packet.getLength());
           System.out.println( add +">" + text);
           //System.out.println( ">" + text);
        }
        catch( IOException e ) { System.err.println( e ); }
      }
   }
}

