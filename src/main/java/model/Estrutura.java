package model;

public abstract class Estrutura {
    
    private  int id;
    private String nome;
    private String tipo;
    private String tonalidade;
    private String som;
    
    public Estrutura(int id, String nome, String tipo, String tonalidade, String som) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tonalidade = tonalidade;
        this.som = som;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTonalidade() {
        return tonalidade;
    }

    public void setTonalidade(String tonalidade) {
        this.tonalidade = tonalidade;
    }

    public String getSom() {
        return som;
    }

    public void setSom(String som) {
        this.som = som;
    }

}


