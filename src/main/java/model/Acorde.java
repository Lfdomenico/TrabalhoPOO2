package model;

import java.util.ArrayList;

public class Acorde extends Estrutura{
    private ArrayList<String> notas;
    private String fundamental;
    private int inversao;
    private String acordeRelativo;
    private int possuiBemol;
    
    public Acorde(ArrayList<String> notas, String fundamental, int inversao, String acordeRelativo, int possuiBemol, int id, String nome, String tipo, String tonalidade, String som) {
        super(id, nome, tipo, tonalidade, som);
        this.notas = notas;
        this.fundamental = fundamental;
        this.inversao = inversao;
        this.acordeRelativo = acordeRelativo;
        this.possuiBemol = possuiBemol;
    }

    public ArrayList<String> getNotas() {
        return notas;
    }

    public String getFundamental() {
        return fundamental;
    }

    public int getInversao() {
        return inversao;
    }

    public String getAcordeRelativo() {
        return acordeRelativo;
    }

    public int getPossuiBemol() {
        return possuiBemol;
    }

    public void setNotas(ArrayList<String> notas) {
        this.notas = notas;
    }

    public void setFundamental(String fundamental) {
        this.fundamental = fundamental;
    }

    public void setInversao(int inversao) {
        this.inversao = inversao;
    }

    public void setAcordeRelativo(String acordeRelativo) {
        this.acordeRelativo = acordeRelativo;
    }

    public void setPossuiBemol(int possuiBemol) {
        this.possuiBemol = possuiBemol;
    }


}
