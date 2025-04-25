package model;

public class CampoHarmonico extends Estrutura {
    
    private String acordes;
    private String escalaBase;
    private String relativaMenor;
    private String relativaMaior;

    public String getAcordes() {
        return acordes;
    }

    public void setAcordes(String acordes) {
        this.acordes = acordes;
    }

    public String getEscalaBase() {
        return escalaBase;
    }

    public void setEscalaBase(String escalaBase) {
        this.escalaBase = escalaBase;
    }

    public String getRelativaMenor() {
        return relativaMenor;
    }

    public void setRelativaMenor(String relativaMenor) {
        this.relativaMenor = relativaMenor;
    }

    public String getRelativaMaior() {
        return relativaMaior;
    }

    public void setRelativaMaior(String relativaMaior) {
        this.relativaMaior = relativaMaior;
    }
    
    public CampoHarmonico(int id, String nome, String tipo, String tonalidade, String som, String relativaMenor, String relativaMaior, String escalaBase, String acordes) {
        super(id, nome, tipo, tonalidade, som);
        this.relativaMenor = relativaMenor;
        this.relativaMaior = relativaMaior;
        this.acordes = acordes;
        this.escalaBase = escalaBase;
    }   
    
    
    
}
