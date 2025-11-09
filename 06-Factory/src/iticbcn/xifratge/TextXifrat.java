package iticbcn.xifratge;

public class TextXifrat {
    private final byte[] dades;
    
    //constructor
    public TextXifrat(byte[] arraybytes){
        //copiem els bytes que hem rebut com a parametre
        this.dades = arraybytes.clone();
    }

    @Override
    public String toString(){
        return new String(dades);
    }

    public byte[] getBytes(){
        return this.dades;
    }
}

