import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Hashes {

    
    public static void main(String[] args) throws Exception {

        //Comptador de les passwords provades en força bruta
        int npass = 0;

        String salt = "qpoweiruañslkdfjz";

        String pw = "aaabF!"; //password que atacarem amb la força bruta

        Hashes h = new Hashes();

        String[] aHashes = {   
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt) 
        };

        String pwTrobat = null;

        String[] algorismes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("==============================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("------------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass   : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps  : %s\n", h.getInterval(t1, t2));
            System.out.printf("------------------------------\n\n");
        }
    }

    //METODES

    // metode per obtindre el hash sha-512 amb salt
    public String getSHA512AmbSalt(String pw, String salt) throws NoSuchAlgorithmException{
        
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        String combined = pw + salt;
        byte[] hashBytes = md.digest(combined.getBytes());
        HexFormat hex = HexFormat.of();
        
        return hex.formatHex(hashBytes);
    
    }

    public String getPBKDF2AmbSalt(String pw, String salt){
        return null;
    }

    public String forcaBruta(String alg, String hash, String salt){
        return null;
    }

    public String getInterval(long t1, long t2){
        return null;
    }
}
