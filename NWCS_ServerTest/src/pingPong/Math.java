import java.math.BigInteger;

public class Math {
    public static void main(String argv[]){
        long a = 3462948672l;
        long b = 277456266;

        //Berechnung des ggT mit dem Euklidischen Algorithmus
        for(int i=1;b>0;i++){
            long c = a%b;
            a = b;
            b = c;
            System.out.println("Schritt "+i+": "+a+", "+b);
            if(b==0){
                System.out.println("Der ggT ist: "+a);
            }
        }
        
    }
}
