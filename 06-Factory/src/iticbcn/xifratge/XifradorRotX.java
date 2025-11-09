package iticbcn.xifratge;
public class XifradorRotX implements Xifrador{

    //Arrays a nivell de classe
    static final char[] arrayLletresMin = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    static final char[] arrayLletresMaj = "AÁÀBCÇDEÉÈFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    
    public void main(String[] args) {
        String[] exemples = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

        System.out.println("Xifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            System.out.println("("+ i*2 +")-" + exemples[i] + " => "+ xifraRotX(exemples[i], i*2));
        }

        System.out.println("\nDesxifrat \n-------------");
        for(int i = 0; i < exemples.length; i++){
            System.out.println("("+ i*2 +")-" + (xifraRotX(exemples[i], i*2)) + " => "+ exemples[i]);
        }

        System.out.println("\nMissatge xifrat: " + xifraRotX(exemples[exemples.length-1], 6) + "\n-------------");
        forçaBrutaRotX(xifraRotX(exemples[exemples.length-1], 6));
    }

    // funció que xifra cada lletra segons el nombre que se li passa
    public  String xifraRotX(String paraula, int desplaçament){
        String paraulaEncriptada = "";
        
        // recorregut del String lletra per lletra
        for(int i = 0; i < paraula.length(); i++){
            // tornem la lletra en caracter
            char lletra = paraula.charAt(i);
            
            if(Character.isUpperCase(lletra)){
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMaj, desplaçament);
            } else if(Character.isLowerCase(lletra)){    // si la lletra es minuscula
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMin, desplaçament);
            } else {   // si la lletra no es majuscula ni minuscula
                paraulaEncriptada += lletra;
            }
        }
        return paraulaEncriptada;
    }

    // funció que desxifra cada lletra segons el nombre que se li passa
    public  String desxifraRotX(String paraula, int desplaçament){
        String paraulaDesencriptada = "";
        for(int i = 0; i < paraula.length(); i++){
            // tornem la lletra en caracter
            char lletra = paraula.charAt(i);
            //si la lletra es majuscula
            if(Character.isUpperCase(lletra)){
                paraulaDesencriptada += desxifrarLletra(lletra, arrayLletresMaj, desplaçament);
            } else if(Character.isLowerCase(lletra)){    // si la lletra es minuscula
                paraulaDesencriptada += desxifrarLletra(lletra, arrayLletresMin, desplaçament);
            } else {        
                paraulaDesencriptada += lletra;
            }
        }
        return paraulaDesencriptada;
    }
    /* funcio que recorre l'array, i quan coincideixi la lletra calcula la posició de la 
    lletra xifrada i retorna aquesta lletra */
    public  char xifrarLletra(char lletra, char[] abecedari, int desplaçament){
        int posicio = 0;
        for (char c : abecedari){
            if (lletra == c){
                posicio = (posicio + desplaçament) % abecedari.length;
                if (posicio < 0) posicio += abecedari.length;
                // retorna la lletra xifrada
                return abecedari[posicio];
            }
            posicio++;
        }
        return lletra;
    }
    /* funció que recorre l'array, i quan coincideixi la lletra calcula la posició de la 
    lletra desxifrada i retorna aquesta lletra */
    public  char desxifrarLletra(char lletra, char[] abecedari, int desplaçament){
        int posicio = 0;
        for (char c : abecedari){
            if (lletra == c){
                posicio = (posicio - desplaçament) % abecedari.length;
                if (posicio < 0) posicio += abecedari.length;
                // retorna la lletra desxifrada
                return abecedari[posicio];
            }
            posicio++;
        }
        return lletra;
    }

    // funció que mostra per pantalla totes les possibilitat de desxifrat
    public  void forçaBrutaRotX(String cadenaXifrada){
        for(int i = 0; i < arrayLletresMaj.length; i++){
            System.out.println("("+ i +")->" + desxifraRotX(cadenaXifrada, i));
        }
    }

    
    @Override 
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            int desplaçament = Integer.parseInt(clau);
            if (desplaçament < 0 || desplaçament > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
            String textXifrat = xifraRotX(msg, desplaçament);
            return new TextXifrat(textXifrat.getBytes());
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            int desplaçament = Integer.parseInt(clau);
            if (desplaçament < 0 || desplaçament > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
            String text = new String(xifrat.getBytes());
            return desxifraRotX(text, desplaçament);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }
}