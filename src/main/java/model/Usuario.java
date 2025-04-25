package model;

import java.util.ArrayList;

public class Usuario{
    
    private String nome;
    private ArrayList<Progressao> progressoes;
    //no futuro colocar os favoritos como atributo??
    
    public Usuario(String nome, ArrayList<Progressao> progressoes) {
        this.nome = nome;
        this.progressoes = progressoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Progressao> getProgressoes() {
        return progressoes;
    }

    public void setProgressoes(ArrayList<Progressao> progressoes) {
        this.progressoes = progressoes;
    }


}
