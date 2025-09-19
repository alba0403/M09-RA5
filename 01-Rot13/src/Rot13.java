public class Rot13 {
    
    public static void main(String[] args) {
        String exemple = "Hola";
       
        xifraRot13(exemple);

    }

    public static String xifraRot13(String cadena){
        // creem un array de l'abecedari en miniscules
        String lletresMinusucules = "aàábcçdeèéfghíìïjklmnñopqrstuvwxyz";
        char[] arrayLletresMin = lletresMinusucules.toCharArray();

        // creem un array de l'abecedari en majuscules
        String lletresMajuscules = "AÀÁBCÇDEÉÈFGHIÍÌÏJKLMNÑOPQRSTUVWXYZ";
        char[] arrayLletresMaj = lletresMajuscules.toCharArray();

        //string que va obtenint els caracters
        //un cop encriptada la lletra
        String paraulaEncriptada = "";

        //recorregut de la paraula donada
        for(int i = 0; i < cadena.length(); i++){
            // tornem la lletra en caracter
            char lletra = cadena.charAt(i);
            //si la lletra es majuscula
            if(Character.isUpperCase(lletra) == true){
                
                for

            } else if(){    // si la lletra es minuscula
                for
            } else {        // si la lletra no es majuscula ni minuscula

            }
        }
        return paraulaEncriptada;
    }

    public static String desxifraRot13(String cadena){
        return null;
    }
}
