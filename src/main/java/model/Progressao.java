package model;

import java.util.ArrayList;

public class Progressao {
    private int id;
    private String nome;
    private ArrayList<Acorde> acordes;

    public Progressao(int id, String nome, ArrayList<Acorde> acordes) {
        this.id = id;
        this.nome = nome;
        this.acordes = acordes;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Acorde> getAcordes() {
        return acordes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAcordes(ArrayList<Acorde> acordes) {
        this.acordes = acordes;
    }
    
    
}
