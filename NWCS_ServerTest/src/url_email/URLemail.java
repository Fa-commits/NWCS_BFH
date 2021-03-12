package url_email;

import java.io.*;
import java.net.*;

public class URLemail
{
   public static void main(String args[])throws Exception
   {
   String from    =("stephan.euler@t-online.de");
   String to      ="stephan.euler@t-online.de";
   String subject = "Klausurergebnissen";
   String nachricht = "Herzlichen Glückwunsch, Sie haben die Prüfung bestanden.";

   System.setProperty("mail.host", "hier mailhost eintragen");
   URL u = new URL("mailto:" + to);
   URLConnection c = u.openConnection();
   c.setDoOutput(true);
   System.out.println("Connecting...");
   c.connect();

   PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream()));
   out.println("Subject: " + subject);
   out.println(nachricht);
   out.close();
   System.out.println("Nachricht versendet.");
 }
}
