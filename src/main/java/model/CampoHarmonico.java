package model;

import java.util.ArrayList;

public class CampoHarmonico extends Estrutura {
    
    private ArrayList<Acorde> acordes;
    private Escala escalaBase;
    private String relativaMenor;
    private String relativaMaior;

    public CampoHarmonico(ArrayList<Acorde> acordes, Escala escalaBase, String relativaMenor, String relativaMaior, int id, String nome, String tipo, String tonalidade, String som) {
        super(id, nome, tipo, tonalidade, som);
        this.acordes = acordes;
        this.escalaBase = escalaBase;
        this.relativaMenor = relativaMenor;
        this.relativaMaior = relativaMaior;
    }

    public ArrayList<Acorde> getAcordes() {
        return acordes;
    }

    public Escala getEscalaBase() {
        return escalaBase;
    }

    public String getRelativaMenor() {
        return relativaMenor;
    }

    public String getRelativaMaior() {
        return relativaMaior;
    }

    public void setAcordes(ArrayList<Acorde> acordes) {
        this.acordes = acordes;
    }

    public void setEscalaBase(Escala escalaBase) {
        this.escalaBase = escalaBase;
    }

    public void setRelativaMenor(String relativaMenor) {
        this.relativaMenor = relativaMenor;
    }

    public void setRelativaMaior(String relativaMaior) {
        this.relativaMaior = relativaMaior;
    }

    
}
