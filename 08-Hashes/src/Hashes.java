import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    static int npass;
    
    public static void main(String[] args) throws Exception {

        //Comptador de les passwords provades en força bruta
        npass = 0;

        String salt = "qpoweiruañslkdfjz"; //es crea per a que dues pw amb el mateix text tinguin un hash diferent

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

    //MÈTODES

    // mètode per obtindre el hash sha-512 amb salt
    public String getSHA512AmbSalt(String pw, String salt) throws Exception{
        
        MessageDigest messDig = MessageDigest.getInstance("SHA-512");
        String passSalt = pw + salt;
        byte[] hashBytes = messDig.digest(passSalt.getBytes());
        
        //ho tornem en un string i ho retornem
        HexFormat hex = HexFormat.of();
        String hash = hex.formatHex(hashBytes);
        return hash;
    }

    // mètode per obtenir el hash pbkdf2 amb el salt
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception{
        
        int iteracions = 1000;
        int longuitudBits = 128; //longuitud del hash
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), iteracions, longuitudBits);
        
        //fem el hash
        SecretKeyFactory secrKeyFact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashBytes = secrKeyFact.generateSecret(spec).getEncoded();
        
        //ho tornem a un string i fem el return
        HexFormat hex = HexFormat.of();
        String hash = hex.formatHex(hashBytes);
        return hash;
    }

    // mètode per probar una contrasenya generada
    private boolean provaPassword(char[] pw, String alg, String hash, String salt) throws Exception {
        npass++;

        String generat = null;
        String actual = new String(pw); // convertim char[] a String

        // generem el hash segons l'algorisme
        if (alg.equals("SHA-512")) {
            generat = getSHA512AmbSalt(actual, salt);
        } else if (alg.equals("PBKDF2")) {
            generat = getPBKDF2AmbSalt(actual, salt);
        }
        // ho comparem amb el hash que volem trencar
        return generat.equals(hash);
    }


    // mètode per atacar els hashs amb força bruta
    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        final String charset = "abcdefABCDEF123456789!"; 
        npass = 0; // reiniciem el comptador de les contrasenyes probades

        for (int i = 1; i <= 6; i++) {
            char[] pw = new char[i]; // array q conté la pw actual
            
            for (int i0 = 0; i0 < charset.length(); i0++) {
                pw[0] = charset.charAt(i0);

                if (i == 1) {
                    if (provaPassword(pw, alg, hash, salt)) return new String(pw);
                    continue;
                }

                for (int i1 = 0; i1 < charset.length(); i1++) {
                    pw[1] = charset.charAt(i1);

                    if (i == 2) {
                        if (provaPassword(pw, alg, hash, salt)) return new String(pw);
                        continue;
                    }

                    for (int i2 = 0; i2 < charset.length(); i2++) {
                        pw[2] = charset.charAt(i2);

                        if (i == 3) {
                            if (provaPassword(pw, alg, hash, salt)) return new String(pw);
                            continue;
                        }

                        for (int i3 = 0; i3 < charset.length(); i3++) {
                            pw[3] = charset.charAt(i3);

                            if (i == 4) {
                                if (provaPassword(pw, alg, hash, salt)) return new String(pw);
                                continue;
                            }

                            for (int i4 = 0; i4 < charset.length(); i4++) {
                                pw[4] = charset.charAt(i4);

                                if (i == 5) {
                                    if (provaPassword(pw, alg, hash, salt)) return new String(pw);
                                    continue;
                                }

                                for (int i5 = 0; i5 < charset.length(); i5++) {
                                    pw[5] = charset.charAt(i5);

                                    // si la longitud és 6, probem el password
                                    if (provaPassword(pw, alg, hash, salt)) 
                                        return new String(pw);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // si no es troba la contrasenya
    }

    // mètode per calcular l'interval de temps
    public String getInterval(long t1, long t2){
        long ms = t2 - t1;

        long dies = ms /(24L*60*60*1000);
        ms %= (24L*60*60*1000);

        long hores = ms /(60L*60*1000);
        ms %= (60L*60*1000);

        long minuts = ms /(60L*1000);
        ms %= (60L*1000);

        long segons = ms/1000;
        long milisegons = ms%1000;

        return dies + " dies / " + hores + " hores / " + minuts + " minuts / "
                + segons + " segons / " + milisegons + " millis";
    }
}