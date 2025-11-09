package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections; 

public class XifradorMonoalfabetic implements Xifrador{
    static final char[] arrayLletresMaj = "AÁÀBCÇDEÉÈFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
     char[] permutacio;

    //permutació inicialitza al constructor
    public XifradorMonoalfabetic(){
        this.permutacio = permutaAlfabet(arrayLletresMaj);
    }

    public void main(String[] args) {
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
    public  char[] permutaAlfabet(char[] alfabet){
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
    public String xifraMonoAlfa(String frase){
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

    public  char xifrarLletra(char lletra, char[] abecedari){
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
    public  String desxifraMonoAlfa(String frase){
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
    public  char desxifrarLletra(char lletra, char[] permutacio){
        int posicio = 0;
        for (char c : permutacio){
            if (lletra == c){
                return arrayLletresMaj[posicio];
            }
            posicio++;
        }
        return lletra;
    }


    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null ) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null ) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String text = new String(xifrat.getBytes());
        return desxifraMonoAlfa(text);
    }

}