import java.util.ArrayList;
import java.util.Collections; 
import java.util.List;

public class Monoalfabetic {
    static final char[] arrayLletresMaj = "AÁÀBCÇDEÉÈFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    static char[] permutacio = permutaAlfabet(arrayLletresMaj);

    public static void main(String[] args) {
        String[] exemples = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

        permutaAlfabet(arrayLletresMaj);

        System.out.println("Xifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            System.out.println(exemples[i] + " => "+ xifraMonoAlfa(exemples[i]));
        }

        System.out.println("\nDesxifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            //System.out.println( + " => ");
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
        for(char c : arrayAlfPermutat){
            arrayAlfPermutat[c] = llistaAlfPermutat.get(c);
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
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMaj);
            }
        }
        return null;
    }

    public static char xifrarLletra(char lletra, char[] abecedari){
        int posicio = 0;
        abecedari = abecedari.toLowerCase(); //tractant de tornar l'abecedari a minuscules
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
        return null;
    }
}
