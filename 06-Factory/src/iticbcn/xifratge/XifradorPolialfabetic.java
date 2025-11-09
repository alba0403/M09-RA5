package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador{
    static final char[] arrayLletresMaj = "AÁÀBCÇDEÉÈFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    private  long clauSecreta = 1234;
    static char[] alfpermutat = new char[arrayLletresMaj.length];
    private  Random rndm; 
    public  void main(String[] args){
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre", 
        "Test 02 Taüll, DÍA año",
        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n----------");

        for(int i = 0; i < msgs.length; i++){
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n-----------");
        for(int i = 0; i < msgs.length; i++){
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
    /*Si se crean dos instancias de Random con la misma semilla 
    y se realiza la misma secuencia de llamadas a métodos para 
    cada una, generarán y devolverán secuencias de números idénticas.*/
    public  void initRandom(Long clau){
        rndm = new Random(clau);
    }

    //mètode que genera una permutació del alfabet i ho emmagatzema en una var global
    public  void permutaAlfabet(){
        ArrayList<Character> llistaAlfPermutat = new ArrayList<Character>();
        for(char c : arrayLletresMaj){
            llistaAlfPermutat.add(c);
        }
        Collections.shuffle(llistaAlfPermutat, rndm);

        
        for(int i = 0; i < alfpermutat.length; i++){
            alfpermutat[i] = llistaAlfPermutat.get(i);
        }

    }

    //xifra la cadena que passem amb xifrat polialfabetic i retorna aquest string
    public  String xifraPoliAlfa(String msg){
        String paraulaEncriptada = "";

        for(int i = 0; i < msg.length(); i++){
            char lletra = msg.charAt(i);
            if(Character.isUpperCase(lletra)){
                permutaAlfabet();
                paraulaEncriptada += xifrarLletra(lletra);
            } else if (Character.isLowerCase(lletra)){
                permutaAlfabet();
                char lletraXifrada = xifrarLletra(Character.toUpperCase(lletra));
                paraulaEncriptada += Character.toLowerCase(lletraXifrada);
            } else {
                paraulaEncriptada += lletra;
            }
        }
        return paraulaEncriptada;
    }  
    
    //desxifra la cadena que passem i torna la cadena desxifrada
    public  String desxifraPoliAlfa(String msgXifrat){
        String paraulaDesencriptada = "";

        for(int i = 0; i < msgXifrat.length(); i++){
            char lletra = msgXifrat.charAt(i);
            if(Character.isUpperCase(lletra)){
                permutaAlfabet();
                paraulaDesencriptada += desxifrarLletra(lletra);
            } else if (Character.isLowerCase(lletra)){
                permutaAlfabet();
                char lletraXifrada = desxifrarLletra(Character.toUpperCase(lletra));
                paraulaDesencriptada += Character.toLowerCase(lletraXifrada);
            } else {
                paraulaDesencriptada += lletra;
            }
        }
        return paraulaDesencriptada;
    }

    public  char desxifrarLletra(char lletra){
        int posicio = 0;
        for (char c : alfpermutat){
            if (lletra == c){
                return arrayLletresMaj[posicio];
            }
            posicio++;
        }
        return lletra;
    }

    //función que cifra cada letra en especifico
    public  char xifrarLletra(char lletra){
        int posicio = 0;
        for (char c : arrayLletresMaj){
            if (lletra == c){
                return alfpermutat[posicio];
            }
            posicio++;
        }
        return lletra;
    }


    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            long clauNum = Long.parseLong(clau);
            initRandom(clauNum);
            String resultat = xifraPoliAlfa(msg);
            return new TextXifrat(resultat.getBytes());
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            long clauNum = Long.parseLong(clau);
            initRandom(clauNum);
            String text = new String(xifrat.getBytes());
            return desxifraPoliAlfa(text);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }
}