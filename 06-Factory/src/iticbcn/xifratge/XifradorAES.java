package iticbcn.xifratge;
/*AES és un algorisme de xifrat simetric, es a dir, la misma clau s'utilitza 
 * per xifrar i desxifrar. Treballa amb blocs de 16 bytes.
 */
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.util.Arrays;

public class XifradorAES implements Xifrador{
    public static final String ALGORISME_XIFRAT = "AES";            
    public static final String ALGORISME_HASH = "SHA-256";          
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding"; //format complert per al Cipher

    private static final int MIDA_IV = 16;      //com AES usa blocs de 16bytes, iv per CBC és també de 16 bytes.
    private  byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "Rastita";   //llavor per a generar la clau AES
    public  void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                        "Hola Andrés cómo está tu cuñado",
                        "Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            //variables per emmagatzemar el resultat de xifrat i desxifrat
            byte[] bXifrats = null;
            String desxifrat = "";

            try{
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e){
                System.out.println("Error de xifrat: "
                + e.getLocalizedMessage());
            }
            System.out.println("--------------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc:" + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }

    }



    // metode de xifrat
    public  byte[] xifraAES(String msg, String clau) throws Exception{
        
        //Obtenir els bytes de l'String
        byte[] msgBytes = msg.getBytes("UTF-8");    //utf-8 per preservar els accents i els caracters especials
        
        // Genera IvParameterSpec
        SecureRandom rndm = new SecureRandom();
        rndm.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);   //emmagatzema els bytes de iv en un objecte Ivparam. que el Cipher entén.

        // Genera hash
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hashBytes = sha.digest(clau.getBytes("UTF-8"));
        SecretKeySpec scrtKeySpec = new SecretKeySpec(hashBytes, ALGORISME_XIFRAT);

        // Encrypt
        Cipher cipher = Cipher.getInstance(FORMAT_AES); //crea el cipher
        cipher.init(Cipher.ENCRYPT_MODE, scrtKeySpec, ivSpec);
        byte[] msgXifrat = cipher.doFinal(msgBytes);

        // Combinar IV i part xifrada 
        byte[] ivMsgXifrat = new byte[MIDA_IV + msgXifrat.length];  //array per emmagatzemar el iv i el text xifrat
        System.arraycopy(iv, 0, ivMsgXifrat, 0, MIDA_IV);   //copia els 16 bytes del iv a l'inici del array final
        System.arraycopy(msgXifrat, 0, ivMsgXifrat, MIDA_IV, msgXifrat.length); //copia el text xifrat

        // return iv+msgxifrat
        return ivMsgXifrat;
    }


    // metode de desxifrat
    public  String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception{
        // Extreure l'iv
        byte[] iv = Arrays.copyOfRange(bIvIMsgXifrat, 0, MIDA_IV);  //extreu els 16 primers bytes
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        // extreure la part xifrada
        byte[] textXifrat = Arrays.copyOfRange(bIvIMsgXifrat, MIDA_IV, bIvIMsgXifrat.length);
        
        // fer hash de la clau
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);      //calcula sha-256 de la clau per obtenir
        byte[] hashBytes = sha.digest(clau.getBytes("UTF-8"));  //els mateixos 32 bytes utilitzats
        hashBytes = Arrays.copyOf(hashBytes, 32);                 //a la clau.
        
        //Convertim la contrasenya en clau AES igual que en el xifrat
        SecretKeySpec keySpec = new SecretKeySpec(hashBytes, ALGORISME_XIFRAT);
        
        // desxifrar
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);  //inicialitza el cipher en mode decrypt/desencriptar
        byte[] txtBytes = cipher.doFinal(textXifrat);
        
        // return string desxifrat
        return new String(txtBytes, "UTF-8");   //passa els bytes desxifrats a string
    }


    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            byte[] dadesXifrades = xifraAES(msg, clau);
            return new TextXifrat(dadesXifrades);
        } catch (Exception e) {
            // Mostra l'error i surt
            System.err.println("Error al xifrar amb AES: " + e.getMessage());
            System.exit(1); // termina el programa
            return null; // aixo no s'exectuta, pero si no ho possem dona error
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            System.err.println("Error al desxifrar amb AES: " + e.getMessage());
            System.exit(1); 
            return null;
        }
    }
}
