package model;
public class Escala extends Estrutura {
    
    private String intervalos;
    private String notas;
    private String escalaEquivalente;

    public String getIntervalos() {
        return intervalos;
    }

    public void setIntervalos(String intervalos) {
        this.intervalos = intervalos;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEscalaEquivalente() {
        return escalaEquivalente;
    }

    public void setEscalaEquivalente(String escalaEquivalente) {
        this.escalaEquivalente = escalaEquivalente;
    }
    
    public Escala(String intervalos, String notas, String escalaEquivalente, int id, String nome, String tipo, String tonalidade, String som) {
        super(id, nome, tipo, tonalidade, som);
        this.intervalos = intervalos;
        this.notas = notas;
        this.escalaEquivalente = escalaEquivalente;
    }
    

    
}
