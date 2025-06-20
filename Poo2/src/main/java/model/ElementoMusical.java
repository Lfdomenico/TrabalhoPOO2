package model;

public abstract class ElementoMusical{
    protected int id;
    protected String nome;
    protected String infoAdicional;

    public ElementoMusical() {
    }

    public ElementoMusical(String nome) {
        this.nome = nome;
    }
    
    public ElementoMusical(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }
    
    public abstract String getInfoComplementar();
}
