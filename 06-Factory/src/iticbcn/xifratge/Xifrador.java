package iticbcn.xifratge;

public interface Xifrador {
    //llença l'excepció si la clau és invalida per aquell algorisme
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada;
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada;
}
