import java.util.ArrayList;
import java.util.Collections; 

public class Monoalfabetic {
    static final char[] arrayLletresMaj = "AÁÀBCÇDEÉÈFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    static char[] permutacio = permutaAlfabet(arrayLletresMaj);

    public static void main(String[] args) {
        String[] exemples = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

        System.out.println("Xifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            System.out.println(exemples[i] + " => "+ xifraMonoAlfa(exemples[i]));
        }

        System.out.println("\nDesxifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            String paraulaXifrada = xifraMonoAlfa(exemples[i]);
            System.out.println( paraulaXifrada + " => " + desxifraMonoAlfa(paraulaXifrada));
        }
    }

    //obte el array de char, ho afegeix a una llista, fa el shuffle
    // ho fica en una array de char, i ho retorna.
    public static char[] permutaAlfabet(char[] alfabet){
        ArrayList<Character> llistaAlfPermutat = new ArrayList<Character>();
        for(char c : alfabet){
            llistaAlfPermutat.add(c);
        }
        Collections.shuffle(llistaAlfPermutat);

        char[] arrayAlfPermutat = new char[llistaAlfPermutat.size()];
        for(int i = 0; i < arrayAlfPermutat.length; i++){
            arrayAlfPermutat[i] = llistaAlfPermutat.get(i);
        }
        return arrayAlfPermutat;
    }

    //li passas el string i ho va xifrant
    public static String xifraMonoAlfa(String frase){
        String paraulaEncriptada = "";

        for(int i = 0; i < frase.length(); i++){
            char lletra = frase.charAt(i);
            if(Character.isUpperCase(lletra)){
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMaj);
            } else if (Character.isLowerCase(lletra)){
                char lletraXifrada = xifrarLletra(Character.toUpperCase(lletra), arrayLletresMaj);
                paraulaEncriptada += Character.toLowerCase(lletraXifrada);
            } else {
                paraulaEncriptada += lletra;
            }
        }
        return paraulaEncriptada;
    }

    public static char xifrarLletra(char lletra, char[] abecedari){
        int posicio = 0;
        for (char c : abecedari){
            if (lletra == c){
                return permutacio[posicio];
            }
            posicio++;
        }
        return lletra;
    }

    //li passas el string i ho va desxifrant
    public static String desxifraMonoAlfa(String frase){
        String paraulaDesencriptada = "";

        for(int i = 0; i < frase.length(); i++){
            char lletra = frase.charAt(i);
            if(Character.isUpperCase(lletra)){
                paraulaDesencriptada += desxifrarLletra(lletra, permutacio);
            } else if (Character.isLowerCase(lletra)){
                char lletraXifrada = desxifrarLletra(Character.toUpperCase(lletra), permutacio);
                paraulaDesencriptada += Character.toLowerCase(lletraXifrada);
            } else {
                paraulaDesencriptada += lletra;
            }
        }
        return paraulaDesencriptada;
    }

    /* funció que recorre l'array, i quan coincideixi la lletra 
    retorna aquesta lletra del alfabet normal q es troba a la mateixa posicio*/
    public static char desxifrarLletra(char lletra, char[] permutacio){
        int posicio = 0;
        for (char c : permutacio){
            if (lletra == c){
                return arrayLletresMaj[posicio];
            }
            posicio++;
        }
        return lletra;
    }
}