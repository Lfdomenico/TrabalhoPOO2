package model;

public class Anotacao {
    private int id;
    private String texto;
    private ElementoMusical elementoSobre;

    public Anotacao() {
    }
    
    public Anotacao(String texto, ElementoMusical elementoSobre) {
        this.texto = texto;
        this.elementoSobre = elementoSobre;
    }

    public Anotacao(int id, String texto, ElementoMusical elementoSobre) {
        this.id = id;
        this.texto = texto;
        this.elementoSobre = elementoSobre;
    }
    
    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public ElementoMusical getElementoSobre() {
        return elementoSobre;
    }

    public void setId(int idAnotacao) {
        this.id = idAnotacao;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void associarElemento(ElementoMusical entidade){ 
        this.elementoSobre = entidade;
    }  

    @Override
    public String toString() {
        return "Anotacao [ID=" + id + ", Texto='" + texto + "'" +
               (elementoSobre != null ? ", Elemento Associado='" + elementoSobre.getNome() + "' (ID: " + elementoSobre.getId() + ")" : "") +
               "]";
    }
}