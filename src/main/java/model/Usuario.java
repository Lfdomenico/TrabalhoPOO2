package model;

public class Usuario{
    
    private String nome;
    private String progressoes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProgressoes() {
        return progressoes;
    }

    public void setProgressoes(String progressoes) {
        this.progressoes = progressoes;
    }

    public Usuario(String nome, String progressoes) {
        this.nome = nome;
        this.progressoes = progressoes;
    }
}
