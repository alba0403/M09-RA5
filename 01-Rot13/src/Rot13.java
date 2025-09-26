/* Programa que conté dues funcions principals, una que xifra cada caràcter de la frase substituint-lo per el que està a 13 posicions
 * per endavant, i l'altra funció desxifra, substituint el caràcter pel que està a 13 posicions endarrere.
 */
public class Rot13 {
    
    //Arrays a nivell de classe
    static final char[] arrayLletresMin = "aàábcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    static final char[] arrayLletresMaj = "AÀÁBCÇDEÈÉFGHIÌÍÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    
    public static void main(String[] args) {
        String exemple1 = "Hola";
        String exemple2 = "Òwúi";

        String resultatEncrip = xifraRot13(exemple1);
        System.out.println("Xifrat :" + resultatEncrip);

        String resultatDesencrip = desxifraRot13(exemple2);
        System.out.println("Desxifrat :" + resultatDesencrip);
    }

    public static String xifraRot13(String paraula){
        //String que va obtenint els caràcters un cop encriptada la lletra
        String paraulaEncriptada = "";
        
        // recorregut de la paraula hola
        for(int i = 0; i < paraula.length(); i++){
            // tornem la lletra en caracter
            char lletra = paraula.charAt(i);
            //si la lletra es majuscula
            if(Character.isUpperCase(lletra)){
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMaj);
            } else if(Character.isLowerCase(lletra)){    // si la lletra es minuscula
                paraulaEncriptada += xifrarLletra(lletra, arrayLletresMin);
            } else {   // si la lletra no es majuscula ni minuscula
                paraulaEncriptada += lletra;
            }
        }
        return paraulaEncriptada;
    }

    public static String desxifraRot13(String paraula){
        String paraulaDesencriptada = "";

        // recorregut de la paraula Òwúi
        for(int i = 0; i < paraula.length(); i++){
            // tornem la lletra en caracter
            char lletra = paraula.charAt(i);
            //si la lletra es majuscula
            if(Character.isUpperCase(lletra)){
                paraulaDesencriptada += desxifrarLletra(lletra, arrayLletresMaj);
            } else if(Character.isLowerCase(lletra)){    // si la lletra es minuscula
                paraulaDesencriptada += desxifrarLletra(lletra, arrayLletresMin);
            } else {        
                paraulaDesencriptada += lletra;
            }
        }
        return paraulaDesencriptada;
    }
    /* funcio que recorre l'array, i quan coincideixi la lletra calcula la posició de la 
    lletra xifrada i retorna aquesta lletra */
    public static char xifrarLletra(char lletra, char[] abecedari){
        int posicio = 0;
        for (char c : abecedari){
            if (lletra == c){
                posicio = posicio + 13;
                // si la posicio és més llarga que l'array, es resta la llargada de l'array a la posició
                if (posicio >= abecedari.length){
                    posicio -= abecedari.length;                        
                }
                // retorna la lletra xifrada
                return abecedari[posicio];
            }
            posicio++;
        }
        return lletra;
    }

    /* funció que recorre l'array, i quan coincideixi la lletra calcula la posició de la 
    lletra desxifrada i retorna aquesta lletra */
    public static char desxifrarLletra(char lletra, char[] abecedari){
        int posicio = 0;
        //recorregut de l'abecedari (array)
        for (char c : abecedari){
            if (lletra == c){
                posicio = posicio - 13;
                // si la posicio és més curta que l'array, es suma la llargada de l'array a la posició
                if (posicio < 0){
                    posicio += abecedari.length;
                }
                // retorna la lletra desxifrada
                return abecedari[posicio];
            }
            posicio++;
        }
        return lletra;
    }
}