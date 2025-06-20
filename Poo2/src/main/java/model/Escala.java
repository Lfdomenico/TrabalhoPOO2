package model;

import java.util.ArrayList;
import java.util.List;

public class Escala extends ElementoMusical implements IElementoSonoro {
    private List<Nota> notas = new ArrayList<>(); 
    private String estrutura; 
    private String tipo;      

    public Escala() {
        super(); 
    }
    
    public Escala(List<Nota> notas, String estrutura, String nomeOuTipo) {

      

        super(nomeOuTipo); 
        this.notas = notas;
        this.estrutura = estrutura;
        this.tipo = ""; 
    }

    public Escala(Escala escala) {
        super(escala.getId(), escala.getNome()); 
        this.notas = new ArrayList<>(escala.getNotas());
        this.estrutura = escala.getEstrutura();
        this.tipo = escala.getTipo(); 
    }

    public Escala(List<Nota> notas, String estrutura, String tipo, String nome) {
        super(nome); 
        this.notas = notas;
        this.estrutura = estrutura;
        this.tipo = tipo;
    }

    public Escala(int id, String nome, List<Nota> notas, String estrutura, String tipo) {
        super(id, nome);
        this.notas = notas;
        this.estrutura = estrutura;
        this.tipo = tipo;
    }

    public Escala(int id, String nome, String estrutura, String tipo) {
        super(id, nome);
        this.estrutura = estrutura;
        this.tipo = tipo;
        this.notas = new ArrayList<>(); 
    }

    public Escala(String tipo, String estrutura) { 
        super(null); 
        this.tipo = tipo;
        this.estrutura = estrutura;
    }
    
    public Escala(String tipo, String estrutura, String nome) { 
        super(nome);
        this.tipo = tipo;
        this.estrutura = estrutura;
    }


    @Override
    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public String getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(String estrutura) {
        this.estrutura = estrutura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String getInfoComplementar() { 

        return "Estrutura: " + this.estrutura + ", Tipo: " + this.tipo;
    }

    @Override
    public String toString() {
        return "Escala [ID=" + getId() + ", Nome='" + getNome() + "', Estrutura='" + estrutura + "', Tipo='" + tipo + "']";
    }
}